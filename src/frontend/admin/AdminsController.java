
package frontend.admin;

import animatefx.animation.*;
import backend.Admin;
import backend.Database;
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

public class AdminsController implements Initializable {

    @FXML
    private AnchorPane header;
    
    @FXML
    private TableView<UserView> table;

    @FXML
    private TableColumn<UserView, String> email, name;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new Tada(header).play();
        
        //add automatic getvalue from transaction
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        try {
            Database db = new Database();
            ResultSet res = db.query("SELECT * FROM user WHERE email='"+Database.email+"'");
            if(res.next()){
                Admin admin = new Admin(res.getString("email"), res.getString("name"), res.getString("password"));
                if(admin.getAdmins() != null){
                    ObservableList<UserView> list = FXCollections.observableArrayList();
                    for (Admin adminItm : admin.getAdmins()) {
                        list.add(new UserView(adminItm.getEmail(), adminItm.getName(),0));
                    }
                    table.setItems(list);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
}
