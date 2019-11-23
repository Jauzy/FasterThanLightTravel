package backend;

import backend.Utility;
import backend.Point;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Lauch extends Application {
    //variables
    double xLoc, yLoc;
    
    Utility utility = new Utility();
    
    @Override
    public void start(Stage stage) throws Exception {
        Stage window = stage;
        Point p = new Point(0,0);
        
        //scenes and FXMLs
        Parent initFXML = FXMLLoader.load(getClass().getResource("/view/Initialization.fxml"));
        utility.windowDraggingEvent(initFXML, stage, p);        
        Scene initScene = new Scene(initFXML);
        
        //Hide the title bar
        initScene.setFill(Color.TRANSPARENT);
        window.initStyle(StageStyle.TRANSPARENT);
        
        window.setScene(initScene);
        stage.setTitle("Train Ticket Application");
        stage.setScene(initScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
