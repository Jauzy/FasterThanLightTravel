    package controller;

import backend.Utility;
import model.Database;
import backend.Point;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LRController implements Initializable {
    //inisialisasi window
    Stage stage = null; //base class dari scene
    Parent root = null; //base class dalam scene
    Utility utility = new Utility(); // tambahan
    Point p = new Point(0,0); // koordinat mouse
    String forgotEmailInput;
    
    @FXML private JFXTextField forgotEmail, forgotPassword1, forgotPassword2;
    @FXML private VBox rightPane;
    @FXML private TextField emailLogin, passwordLogin, nameRegister, emailRegister, passwordRegister, rePasswordRegister; //input fields
    @FXML private JFXButton loginBtn, registerBtn, changeToRegister, changeToLogin, forgotPasswordBtn, forgotCheck, forgotResetPassword   ; //buttons
    @FXML private Label dangerRegister, dangerLogin, warningForgotEmail, warningResetPassword ; //labels

    @FXML
    void loginOnClick(ActionEvent event) throws SQLException, IOException {
        Database db = new Database(); // init new DB
        String email = emailLogin.getText();
        String pass = passwordLogin.getText();
        ResultSet user = db.query("SELECT * FROM USER where email='"+email+"' and password='"+pass+"'"); //get user
        Database.email = email; //session ketika login
        if(user.next() && email != ""){
            stage = (Stage) loginBtn.getScene().getWindow();
            if(!user.getBoolean("isAdmin")) root = FXMLLoader.load(getClass().getResource("/view/user/UserDashboard.fxml")); //load file if user
            else root = FXMLLoader.load(getClass().getResource("/view/admin/AdminDashboard.fxml")); // load file if admin
            utility.changeWindow(stage, root);
        }
        else {
            dangerLogin.setText("Email or Password is Incorrect.");
        }
    }
    
    @FXML
    void registerOnClick(ActionEvent event) throws SQLException, IOException, Exception{
        Database db = new Database(); //init new DB
        String name = nameRegister.getText();
        String email = emailRegister.getText();
        String pass = passwordRegister.getText();
        String pass2 = rePasswordRegister.getText();
        //check apakah password sama dan tidak ada email serupa dalam db
        if(!validate(email)){
            dangerRegister.setText("Email is not valid.");
        } else if(pass.equals(pass2) && !db.query("SELECT * FROM USER where email='"+email+"'").next()){
            //jika valid, insert ke db
            String sql = "INSERT INTO user(name,isAdmin,budget,email,password) "
                + "VALUES('"+name+"',0,0,'"+email+"','"+pass+"')";
            try{ 
                db.update(sql);
                //ganti window ke login
                stage = (Stage) registerBtn.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("Login.fxml")); //load file
                utility.changeWindow(stage, root);
            } catch(SQLException e){
                System.out.println("SQL ERROR!!");
            } 
        }
        else {
            dangerRegister.setText("Password Inconsistent or Email has been taken.");
        }
    }
    
    @FXML
    void changeScene(ActionEvent event) throws Exception{
        //will change scene
        if(event.getSource() == changeToRegister){
            stage = (Stage) changeToRegister.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/Register.fxml"));
        }
        else if(event.getSource() == changeToLogin){
            stage = (Stage) changeToLogin.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        }
        utility.changeWindow(stage, root);
    }
    
    @FXML
    public void exit(MouseEvent event) {
        System.exit(0); // exit app
    }
    
    //regular expression untuk validasi email
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    //regex func
    public static boolean validate(String emailStr) {
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
            return matcher.find();
    }
    
    @FXML
    void forgotPasswordBtnOnClick(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/ForgotPassword.fxml"));
            rightPane.getChildren().removeAll();
            rightPane.getChildren().setAll(root);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }        
}
