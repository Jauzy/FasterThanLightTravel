
package controller.user;

import animatefx.animation.*;
import model.Database;
import com.jfoenix.controls.JFXButton;
import backend.Utility;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ProfileController implements Initializable {
    Utility utility = new Utility();
    
    @FXML
    private AnchorPane header;
    @FXML
    private Label name;
    @FXML
    private Label email;
    @FXML
    private Label budget;
    @FXML
    private Label abbrevitiation;
    
    @FXML
    private JFXButton changePasswordBtn;

    @FXML
    void changePasswordBtnOnClick(ActionEvent event) {
        try {
            Parent popup = FXMLLoader.load(getClass().getResource("/view/user/ProfilePasswordReset.fxml"));
            utility.createPopUp(popup);
        } catch (IOException ex) {
            System.out.println("FIle not found");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            new Wobble(header).play();
            Database db = new Database();
            ResultSet res = db.query("SELECT * FROM USER where email='"+Database.email+"'");
            if(res.next()){
                name.setText(res.getString("name"));
                email.setText(res.getString("email"));
                budget.setText("IDR. "+String.valueOf(NumberFormat.getCurrencyInstance(Locale.US).format(res.getDouble("budget"))).substring(1));
                abbrevitiation.setText(String.valueOf(res.getString("name").charAt(0)));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }    
    
}
