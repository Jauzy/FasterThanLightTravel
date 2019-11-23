
package controller.admin;

import model.Database;
import com.jfoenix.controls.JFXButton;
import backend.Utility;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AdminDashboardController implements Initializable {
    Stage stage = null; //base class dari scene
    Utility utility = new Utility(); // tambahan

    @FXML
    private JFXButton logoutBtn;
  
    @FXML
    private JFXButton adminsBtn, usersBtn, placesBtn, transactionBtn;
    
    @FXML
    private AnchorPane ContentPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Parent child = FXMLLoader.load(getClass().getResource("/view/admin/Admins.fxml")); //load file
            utility.changeChildren(ContentPane, child);
        } catch (IOException ex) {
            Logger.getLogger(AdminDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void logoutBtnOnClick(ActionEvent event) throws IOException {
        Database.email = ""; //delete simulated session
        stage = (Stage) logoutBtn.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml")); //load file
        utility.changeWindow(stage, root);
    }
    
    @FXML
    void adminsBtnOnClick(ActionEvent event) throws IOException {
        Parent child = FXMLLoader.load(getClass().getResource("/view/admin/Admins.fxml")); //load file
        utility.changeChildren(ContentPane, child);
    }

    @FXML
    void placesBtnOnClick(ActionEvent event) throws IOException {
        Parent child = FXMLLoader.load(getClass().getResource("/view/admin/Places.fxml")); //load file
        utility.changeChildren(ContentPane, child);
    }

    @FXML
    void transactionBtnOnClick(ActionEvent event) throws IOException {
        Parent child = FXMLLoader.load(getClass().getResource("/view/admin/Transactions.fxml")); //load file
        utility.changeChildren(ContentPane, child);
    }

    @FXML
    void usersBtnOnClick(ActionEvent event) throws IOException {
        Parent child = FXMLLoader.load(getClass().getResource("/view/admin/Users.fxml")); //load file
        utility.changeChildren(ContentPane, child);
    }
    
}
