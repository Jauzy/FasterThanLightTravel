package frontend;

import backend.Point;
import java.io.IOException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Utility {
    public void windowDraggingEvent(Parent FXML, Stage stage, Point p){
        //Dragging Event
        //when mouse get clicked get current mouse xy coordinate in window
        //getScene will return xy coordinate in window
        
        //when window dragged set xLoc,yLoc to current xy substract xLoc,yLoc. why ?
        //because if we didnt substract with xLoc,yLoc the window position will jump to current mouse xy position and that is kinda wierd
        //if we do, window will positioned at screensize - xy coordinate of mouse in window.
        //the calculation happens in every pixel hovered so window will jump over 1 pixel every time this method called.
        //ex = xy : 400,300; screensize : 1920,1080; result : 1520,780.  
        //getScreen will return pc screen size.
        FXML.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                p.setX(event.getSceneX());
                p.setY(event.getSceneY());
            }
        });
        
        FXML.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                stage.setX(event.getScreenX() - p.getX());
                stage.setY(event.getScreenY() - p.getY());
            }
        });
    }
    
    public void changeChildren(AnchorPane ContentPane, Parent child){
        ContentPane.getChildren().removeAll();
        ContentPane.getChildren().setAll(child);
    }
    
    public void changeWindow(Stage stage, Parent root) throws IOException{
        Utility utility = new Utility(); // tambahan
        Point p = new Point(0,0); // koordinat mouse
        
        utility.windowDraggingEvent(root, stage, p); // untuk window drag
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void forceNumeric(TextField textField){
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("([\\d,.,-])*")) {
                    textField.setText(newValue.replaceAll("[^[\\d,.,-]]", ""));
                }
            }
        });
    }
    
    public void createPopUp(Parent root){
        Stage popup = new Stage();
        windowDraggingEvent(root, popup, new Point(100,100));
        Scene popupScene = new Scene(root);
        popupScene.setFill(Color.TRANSPARENT);
        popup.initStyle(StageStyle.TRANSPARENT);
        popup.setScene(popupScene);
        popup.show();
    }
    
}
