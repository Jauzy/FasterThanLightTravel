package frontend.admin;

import animatefx.animation.*;
import backend.Admin;
import backend.Database;
import backend.Place;
import frontend.Utility;
import frontend.tableView.PlaceView;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class PlacesController implements Initializable {

    Utility utility = new Utility();
    Database db;
    
    @FXML private AnchorPane header;
    @FXML private TableView<PlaceView> table;
    @FXML private TableColumn<PlaceView, String> name;
    @FXML private TableColumn<PlaceView, Double> coorX, coorY;
    @FXML private TextField newName, newX, newY;
    @FXML private Button submitBtn;
    @FXML private Label label;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new Wobble(header).play();
        utility.forceNumeric(newX);
        utility.forceNumeric(newY);
        
        //add automatic getvalue from transaction
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        coorX.setCellValueFactory(new PropertyValueFactory<>("x"));
        coorY.setCellValueFactory(new PropertyValueFactory<>("y"));
        
        try {
            db = new Database();
            ResultSet res = db.query("SELECT * FROM user WHERE email='"+Database.email+"'");
            if(res.next()){
                Admin admin = new Admin(res.getString("email"), res.getString("name"), res.getString("password"));
                if(admin.getPlaces() != null){
                    ObservableList<PlaceView> list = FXCollections.observableArrayList();
                    for (Place placeItm : admin.getPlaces()) {
                        list.add(new PlaceView(placeItm));
                    }
                    table.setItems(list);
                }
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlacesController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }    

    @FXML
    private void submitBtnOnClick(ActionEvent event) throws SQLException {
        String xP = newX.getText();
        String yP = newY.getText();
        String nameP = newName.getText();
        
        db = new Database();
        ResultSet res = db.query("select * from place where name='"+nameP+"'");
        
        if(res.next()){
            label.setText("Place with that name have already in db");
        }
        else if(!xP.equals("") && !yP.equals("") && !nameP.equals("")){
            
            double x = Double.parseDouble(xP), y = Double.parseDouble(yP);
            ResultSet adm = db.query("SELECT * FROM user WHERE email='"+Database.email+"'");
            if(adm.next()){
                Admin admin = new Admin(adm.getString("email"), adm.getString("name"), adm.getString("password"));
                admin.addPlaces(nameP, x, y);
            }
            
            //reset form
            newName.setText("");
            newX.setText("");
            newY.setText("");
            label.setText("Input Success.");
            
            //reload table
            res = db.query("select * from place");
            ObservableList<PlaceView> list = FXCollections.observableArrayList();
            while(res.next()){
                list.add(new PlaceView(new Place(res.getString("name"), res.getDouble("coorX"), res.getDouble("coorY"))));
            }
            table.setItems(list);
            
        } else {
            label.setText("Please Fill in the Form");
        }
    }
    
}
