
package controller.user;

import model.Database;
import backend.Place;
import backend.User;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import backend.Utility;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MakeTransactionFormController implements Initializable, MapComponentInitializedListener {
    Utility utility = new Utility();
    
    @FXML private DatePicker date;
    @FXML private Button checkoutBtn;
    @FXML private ComboBox<String> from;
    @FXML private ComboBox<String> dest, time;
    @FXML private TextField seat;
    @FXML private Label price;
    @FXML private Label warning;
    @FXML private GoogleMapView mapView;
    private GoogleMap map;
    private boolean GMapToggle = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapView.setVisible(false);
        try {
            mapView.setKey("AIzaSyAZT4BTDagvDaXOI3PETfqh77bLyEaU4CI");
            mapView.addMapInializedListener(this);
        } catch(Exception e){
            System.out.println("Something Wrong");
        }
        try {
            checkoutBtn.setDisable(true);
            
            Database db = new Database();
            ResultSet res = db.query("select * from place");
                        
            if(res.next()){
                res.last();
                int size = res.getRow();
                String places[] = new String[size];
                int placeSize = 0;
                res.first();
                places[placeSize] = res.getString("name");
                placeSize++;
                while(res.next()){
                    places[placeSize] = res.getString("name");
                    placeSize++;
                }

                //setting items 
                from.setItems(FXCollections.observableArrayList(places));
                dest.setItems(FXCollections.observableArrayList(places));               
  
            }
  
            String[] times = {"06.00:AM","07.00:AM","08.00:AM","09.00:AM","10.00:AM","11.00:AM","00.00:PM",
                    "01.00:PM","02.00:PM","03.00:PM","04.00:PM","05.00:PM","06.00:PM","07.00:PM"
                };
            
            //setting items and adding event listener when onChange
            time.setItems(FXCollections.observableArrayList(times));
            
            utility.forceNumeric(seat);
        } catch (SQLException ex) {
            System.out.println("SQL ERROR!!");
        }
    }    

    @FXML
    void checkBtnOnClick(ActionEvent event) {
        String fromT = from.getValue(), destT = dest.getValue(), timeT = time.getValue();
        String seatT = seat.getText(); LocalDate dateT = date.getValue();
        if(!"".equals(fromT) && !"".equals(destT) && !"".equals(seatT) && !"".equals(timeT) && dateT != null){
            Database db;
            try {
                db = new Database();
                ResultSet res = db.query("SELECT * FROM user WHERE email='"+Database.email+"'");
                if(res.next()){
                    User user = new User(res.getString("email"), res.getString("name"), res.getString("password"), res.getDouble("budget"));
                    Place fromP = user.searchPlace(fromT), destP = user.searchPlace(destT);
                    int priceTag = (int) Math.ceil((fromP.comparePlace(destP)/2000) * 1000);
                    priceTag *= Integer.parseInt(seatT);
                    price.setText(String.format("IDR %dK",priceTag/1000));
                    checkoutBtn.setDisable(false);
                    setMap(new LatLong(
                            fromP.getCoordinate().getY(),fromP.getCoordinate().getX()),new LatLong(destP.getCoordinate().getY(),destP.getCoordinate().getX()
                            ),fromP.getName(), destP.getName(), priceTag
                    );
                    mapView.setVisible(true);
                    GMapToggle = true;
                }
            } catch (SQLException ex) {
                System.out.println("SQL ERROR!!");
            }
        }else {
            warning.setText("Please fill in all the form");
        }
    }

    @FXML
    void checkoutBtnOnClick(ActionEvent event) {
        String fromT = from.getValue(), destT = dest.getValue(), timeT = time.getValue();
        String seatT = seat.getText(); LocalDate dateT = date.getValue();
        Database db;
        try {
            db = new Database();
            ResultSet res = db.query("SELECT * FROM user WHERE email='"+Database.email+"'");
            if(res.next()){
                User user = new User(res.getString("email"), res.getString("name"), res.getString("password"), res.getDouble("budget"));
                Place fromP = user.searchPlace(fromT), destP = user.searchPlace(destT);
                int priceTag = (int) Math.ceil((fromP.comparePlace(destP)/2000) * 1000);
                priceTag *= Integer.parseInt(seatT);
                double budget = user.getBudget();
                if(dateT.isAfter(LocalDate.now()) || dateT.equals(LocalDate.now())){
                    if(budget - priceTag > 0){
                        user.addTransactions(LocalDate.now(), user.getEmail(), dateT, fromT, destT, timeT, Integer.parseInt(seatT),priceTag, budget);
                        from.setValue(""); dest.setValue(""); time.setValue(""); seat.setText(""); date.setValue(null);
                        warning.setText("Transaction Success");
                    } else warning.setText("You dont have enough budget, please topup.");
                } else {
                    warning.setText("You can't order ticket for the day before today");
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQL ERROR!!");
        }
    }
    
    @FXML void toggleGMAP(ActionEvent event){
        GMapToggle = !GMapToggle;
        mapView.setVisible(GMapToggle);
    }
    
    public void setMap(LatLong start, LatLong destination, String from, String dest, double price){
        try {
            //Set the initial properties of the map.
            MapOptions mapOptions = new MapOptions();

            mapOptions.center(new LatLong((start.getLatitude()+destination.getLatitude())/2,(start.getLongitude()+destination.getLongitude())/2))
                    .mapType(MapTypeIdEnum.ROADMAP)
                    .overviewMapControl(true)
                    .panControl(true)
                    .rotateControl(true)
                    .scaleControl(true)
                    .streetViewControl(false)
                    .zoomControl(true)
                    .zoom(8);

            map = mapView.createMap(mapOptions);

            //Add markers to the map
            MarkerOptions startMarkerOpt = new MarkerOptions();
            startMarkerOpt.position(start);

            MarkerOptions destinationMarkerOpt = new MarkerOptions();
            destinationMarkerOpt.position(destination);


            Marker startMarker = new Marker(startMarkerOpt);
            Marker destinationMarker = new Marker(destinationMarkerOpt);
            map.addMarker( startMarker );
            map.addMarker( destinationMarker );

            InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
            infoWindowOptions.content(String.format("<Strong>%s - %s</Strong><br>Distance : %.1f KM<br>Price : IDR %s", 
                    from,dest,Math.ceil(start.distanceFrom(destination)/1000),String.valueOf(NumberFormat.getCurrencyInstance(Locale.US).format(price)).substring(1)));

            InfoWindow popup = new InfoWindow(infoWindowOptions);
            popup.open(map, startMarker);
        } catch(Exception e){
            System.out.println("Something wrong");
        }
    }
    
    @Override
    public void mapInitialized() {
        
    }   
    
}
