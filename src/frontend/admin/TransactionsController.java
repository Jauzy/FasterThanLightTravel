
package frontend.admin;

import animatefx.animation.*;
import backend.Admin;
import backend.Database;
import backend.Transaction;
import frontend.tableView.TransactionView;
import frontend.tableView.UserView;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class TransactionsController implements Initializable {

    @FXML
    private AnchorPane header;
    @FXML
    private TableView<TransactionView> table;
    @FXML
    private TableColumn<TransactionView, String> tranID, tranDate, email, date, from, dest, time, seat;
 
    @FXML
    private TableColumn<TransactionView, Double> price;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new RubberBand(header).play();
        
        //add automatic getvalue from transaction
        tranID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tranDate.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        from.setCellValueFactory(new PropertyValueFactory<>("from"));
        dest.setCellValueFactory(new PropertyValueFactory<>("dest"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        seat.setCellValueFactory(new PropertyValueFactory<>("seat"));
        
        try {
            Database db = new Database();
            ResultSet res = db.query("SELECT * FROM user WHERE email='"+Database.email+"'");
            if(res.next()){
                Admin admin = new Admin(res.getString("email"), res.getString("name"), res.getString("password"));
                if(admin.getTransaction() != null){
                    ObservableList<TransactionView> list = FXCollections.observableArrayList();
                    for (Transaction transactionItm : admin.getTransaction()) {
                        list.add(new TransactionView(transactionItm));
                    }
                    table.setItems(list);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
