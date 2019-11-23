package controller.user;

import model.Database;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class ProfilePasswordResetController implements Initializable {

    @FXML private Label name, email;
    @FXML private PasswordField passwordOld, passwordNew1, passwordNew2;
    @FXML private JFXButton cancelBtn, submitBtn;
    @FXML private Label abbr, warningChangePassword;

    @FXML
    void cancelBtnOnClick(ActionEvent event) {
        name.getScene().getWindow().hide();
    }

    @FXML
    void submitBtnOnClick(ActionEvent event) {
        try {
            Database db = new Database();
            ResultSet res = db.query("SELECT * FROM user WHERE email='"+Database.email+"'");
            String oldPwd = passwordOld.getText(), newPwd1 = passwordNew1.getText(), newPwd2 = passwordNew2.getText();
            if(res.next()){
                if(oldPwd.equals(res.getString("password"))){
                    if(newPwd1.equals(newPwd2)){
                        db.update("UPDATE user SET password='"+newPwd1+"' where email='"+Database.email+"'");
                        name.getScene().getWindow().hide();
                    } else {
                        warningChangePassword.setText("New Password Inconsistent!!");
                    }
                } else {
                    warningChangePassword.setText("Old Password Incorrect!!");
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQL ERROR!!");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Database db = new Database();
            ResultSet res = db.query("SELECT * FROM user WHERE email='"+Database.email+"'");
            if(res.next()){
                name.setText(res.getString("name"));
                email.setText(res.getString("email"));
                abbr.setText(String.valueOf(res.getString("name").charAt(0)));
            }
        } catch (SQLException ex) {
            System.out.println("SQL ERROR!!");
        }
    }

}
