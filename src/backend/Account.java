package backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.beans.property.SimpleStringProperty;

public abstract class Account implements Nameable, Transactionable {
    protected boolean isAdmin;
    protected SimpleStringProperty password;
    protected SimpleStringProperty email;
    protected SimpleStringProperty name;
    protected Transaction[] transactions; // komposisi

    public Account(String email, String name, boolean isAdmin, String password) {
        this.isAdmin = isAdmin;
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.password = new SimpleStringProperty(password);
    }
    
    public Place searchPlace(String name) throws SQLException{
        Database db = new Database();
        ResultSet res = db.query("select * from place where name='"+name+"'");
        if(res.next())
            return new Place(res.getString("name"), res.getDouble("coorX"), res.getDouble("coorY"));
        return null;
    }
    
    public boolean getAdmin(){
        return isAdmin;
    }

    public String getEmail() {
        return email.get();
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password = new SimpleStringProperty(password);
    }
}
