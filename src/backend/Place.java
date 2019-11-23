package backend;

import com.lynden.gmapsfx.javascript.object.LatLong;
import javafx.beans.property.SimpleStringProperty;

public class Place implements Nameable{
    private Point coordinate;
    private SimpleStringProperty name;
    
    public Place(){
        this.name = new SimpleStringProperty("");
        coordinate = new Point(0,0);
    }
    
    public Place(String name,double x,double y){
        this.name = new SimpleStringProperty(name);
        coordinate = new Point(x,y);
    }

    public Point getCoordinate() {
        return coordinate;
    }
    
    @Override
    public void setName(String name){
        this.name = new SimpleStringProperty(name);
    }
    
    @Override
    public String getName(){
        return name.get();
    }
    
    public float comparePlace(Place p){
        LatLong x = new LatLong(coordinate.getY(),coordinate.getX());
        LatLong y = new LatLong(p.getCoordinate().getY(),p.getCoordinate().getX());
        
        return (float) Math.ceil(x.distanceFrom(y));
    }
    
}
