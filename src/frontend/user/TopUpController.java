
package frontend.user;

import animatefx.animation.*;
import backend.Database;
import backend.User;
import com.jfoenix.controls.JFXTextField;
import frontend.Utility;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class TopUpController implements Initializable {

    Utility utility = new Utility();
    
    @FXML private Label name, email, budget, warning;
    @FXML private AnchorPane header;
    @FXML private JFXTextField topup;
    
    @FXML
    void submitBtnOnClick(ActionEvent event) {
        if("".equals(topup.getText())) warning.setText("Please Fill in the Form");
        else {
            Database db;
            try {
                db = new Database();
                ResultSet res = db.query("SELECT * FROM user WHERE email='"+Database.email+"'");
                if(res.next()){
                    User user = new User(res.getString("email"), res.getString("name"), res.getString("password"), res.getDouble("budget"));
                    user.topUp(res, Double.parseDouble(topup.getText()));
                    topup.setText("");
                    res = db.query("SELECT * FROM user WHERE email='"+Database.email+"'");
                    if(res.next()){
                        user = new User(res.getString("email"), res.getString("name"), res.getString("password"), res.getDouble("budget"));
                        budget.setText("IDR. "+String.valueOf(NumberFormat.getCurrencyInstance(Locale.US).format(user.getBudget())).substring(1));
                    }
                }
            } catch (SQLException ex) {
                System.out.println("SQL ERROR!!");
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new Tada(header).play();
        
        utility.forceNumeric(topup);
        
        Database db;
        try {
            db = new Database();
            ResultSet res = db.query("SELECT * FROM user WHERE email='"+Database.email+"'");
            if(res.next()){
                User user = new User(res.getString("email"), res.getString("name"), res.getString("password"), res.getDouble("budget"));
                name.setText(user.getName());
                email.setText(user.getEmail());
                budget.setText("IDR. "+String.valueOf(NumberFormat.getCurrencyInstance(Locale.US).format(user.getBudget())).substring(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TopUpController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
}
