
package frontend.user;

import animatefx.animation.*;
import frontend.Utility;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

public class MakeTransactionController implements Initializable {

    Utility utility = new Utility();
    
    @FXML private AnchorPane contentPane;
    @FXML private AnchorPane header;
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            new Bounce(header).play();
            Parent form = FXMLLoader.load(getClass().getResource("MakeTransactionForm.fxml"));
            utility.changeChildren(contentPane, form);
        } catch (IOException ex) {
            System.out.println("ERROR HERE!!");
        }
    }    
    
}
