package controller.user;

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

public class UserDashboardController implements Initializable {
    Stage stage = null; //base class dari scene
    Utility utility = new Utility(); // tambahan
    
    @FXML
    private JFXButton logoutBtn, profileBtn, makeTransactionBtn, transactionHistoryBtn, topupBtn;
    
    @FXML
    private AnchorPane ContentPane;
    
    @FXML
    void logoutBtnOnClick(ActionEvent event) throws IOException {
        Database.email = ""; //delete simulated session
        stage = (Stage) logoutBtn.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml")); //load file
        utility.changeWindow(stage, root);
    }
    
    @FXML
    void makeTransactionBtnOnClick(ActionEvent event) throws IOException {
        Parent child = FXMLLoader.load(getClass().getResource("/view/user/MakeTransaction.fxml")); //load file
        utility.changeChildren(ContentPane, child);
    }

    @FXML
    void profileBtnOnClick(ActionEvent event) throws IOException {
        Parent child = FXMLLoader.load(getClass().getResource("/view/user/Profile.fxml")); //load file
        utility.changeChildren(ContentPane, child);
    }

    @FXML
    void topupBtnOnClick(ActionEvent event) throws IOException {
        Parent child = FXMLLoader.load(getClass().getResource("/view/user/TopUp.fxml")); //load file
        utility.changeChildren(ContentPane, child);
    }

    @FXML
    void transactionHistoryBtnOnClick(ActionEvent event) throws IOException {
        Parent child = FXMLLoader.load(getClass().getResource("/view/user/TransactionHistory.fxml")); //load file
        utility.changeChildren(ContentPane, child);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Parent child = FXMLLoader.load(getClass().getResource("/view/user/Profile.fxml")); //load file
            utility.changeChildren(ContentPane, child);
        } catch (IOException ex) {
            Logger.getLogger(UserDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
