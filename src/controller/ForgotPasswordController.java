/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Database;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import static controller.LRController.validate;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Jauzy
 */
public class ForgotPasswordController implements Initializable {

    @FXML
    private JFXTextField forgotEmail;
    @FXML
    private JFXPasswordField forgotPassword2,forgotPassword1;
    @FXML
    private Label warningForgotEmail;
    @FXML
    private JFXButton forgotCheck;
    @FXML
    private JFXButton forgotResetPassword;
    private String forgotEmailInput;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    void forgotCheckOnClick(ActionEvent event) {
        try {
            Database db = new Database();
            forgotEmailInput = forgotEmail.getText();
            if(!validate(forgotEmailInput)){
                warningForgotEmail.setText("Email not valid");
            }
            else if(db.query("SELECT * FROM user WHERE email='"+forgotEmailInput+"'").next()){
                forgotResetPassword.setDisable(false);
                forgotPassword1.setDisable(false);
                forgotPassword2.setDisable(false);
                forgotEmail.setDisable(true);
                forgotCheck.setDisable(true);
            } else {
                warningForgotEmail.setText("Email Not Found!!");
            }
        } catch (SQLException ex) {
            System.out.println("SQL ERROR");
        }
    }

    @FXML
    void forgotResetPasswordOnClick(ActionEvent event) {
        try {
            Database db = new Database();
            String pass1 = forgotPassword1.getText();
            String pass2 = forgotPassword2.getText();
            if(pass1.equals(pass2)){
                db.update("UPDATE user SET password='"+pass1+"' where email='"+forgotEmailInput+"'");
                forgotResetPassword.setDisable(true);
                forgotPassword1.setDisable(true); forgotPassword1.setText("");
                forgotPassword2.setDisable(true); forgotPassword2.setText("");
                forgotEmail.setDisable(false); forgotEmail.setText("");
                forgotCheck.setDisable(false); 
            } else {
                warningForgotEmail.setText("Password Doesnt Match");
            }
        } catch (SQLException ex) {
            System.out.println("SQL ERROR!!");
        }
    }
    
}
