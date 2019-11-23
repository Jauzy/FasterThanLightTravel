package tableView;

import backend.Place;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class PlaceView {
    private SimpleStringProperty name;
    private SimpleDoubleProperty x, y;
    public PlaceView(Place place){
        this.name = new SimpleStringProperty(place.getName());
        this.x = new SimpleDoubleProperty(place.getCoordinate().getX());
        this.y = new SimpleDoubleProperty(place.getCoordinate().getY());
    }

    public String getName() {
        return name.get();
    }

    public Double getX() {
        return x.get();
    }

    public Double getY() {
        return y.get();
    }
    
}
