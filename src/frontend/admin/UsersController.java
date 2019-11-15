
package frontend.admin;

import animatefx.animation.*;
import backend.Admin;
import backend.Database;
import backend.User;
import frontend.tableView.UserView;
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

public class UsersController implements Initializable {

    @FXML
    private AnchorPane header;
    
    @FXML
    private TableView<UserView> table;

    @FXML
    private TableColumn<UserView, String> email, name;

    @FXML
    private TableColumn<UserView, Double> budget;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new Shake(header).play();
        
        //add automatic getvalue from transaction
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        budget.setCellValueFactory(new PropertyValueFactory<>("budget"));
        
        try {
            Database db = new Database();
            ResultSet res = db.query("SELECT * FROM user WHERE email='"+Database.email+"'");
            if(res.next()){
                Admin admin = new Admin(res.getString("email"), res.getString("name"), res.getString("password"));
                if(admin.getUsers() != null){
                    ObservableList<UserView> list = FXCollections.observableArrayList();
                    for (User userItm : admin.getUsers()) {
                        list.add(new UserView(userItm.getEmail(), userItm.getName(), userItm.getBudget()));
                    }
                    table.setItems(list);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
