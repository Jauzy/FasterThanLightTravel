/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jauzy
 */
public class InitializationController implements Initializable {

    Utility utility = new Utility(); 
    
    @FXML
    private ImageView loading;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new Wait().start();
    }    
    
    class Wait extends Thread {
        @Override
        public void run(){
            try {
                Thread.sleep(3000);
                Platform.runLater(() -> {
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                        Stage stage = (Stage) loading.getScene().getWindow();
                        utility.changeWindow(stage, root);
                    } catch (IOException ex) {
                        Logger.getLogger(InitializationController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });             
            } catch (InterruptedException ex) {
                Logger.getLogger(InitializationController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
}
