
package controller.user;

import animatefx.animation.*;
import model.Database;
import backend.Transaction;
import backend.User;
import controller.admin.AdminsController;
import tableView.TransactionView;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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

public class TransactionHistoryController implements Initializable {

    @FXML private AnchorPane header; 
    @FXML private TableView<TransactionView> table;
    @FXML private TableColumn<TransactionView, String> transactionDate, date, id, from, dest, time; 
    @FXML private TableColumn<TransactionView, Integer> seat;
    @FXML private TableColumn<TransactionView, Double> price;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new Pulse(header).play();
                
        //add automatic getvalue from transaction
        transactionDate.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        from.setCellValueFactory(new PropertyValueFactory<>("from"));
        dest.setCellValueFactory(new PropertyValueFactory<>("dest"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        seat.setCellValueFactory(new PropertyValueFactory<>("seat"));
                
        try {
            Database db = new Database();
            String sql = "SELECT * FROM user WHERE email='"+Database.email+"'";
            ResultSet res = db.query(sql);
            if(res.next()){
                User user = new User(res.getString("email"), res.getString("name"), res.getString("password"), res.getDouble("budget"));
                
                if(user.getTransaction() != null){
                    ObservableList<TransactionView> list = FXCollections.observableArrayList();
                    for (Transaction transactionItm : user.getTransaction()) {
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
