package backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.beans.property.SimpleStringProperty;

public class Admin extends Account{
    private User[] users;
    private Admin[] admins; // agregasi
    private Place[] places;
    
    public Admin (String email, String name, String password){
        super(email, name, true, password);
    }
    
    public User[] getUsers() throws SQLException{
        Database db = new Database();
        ResultSet res = db.query("select * from user where isAdmin = false;");
        int usercount = 0;
        res.last();
        int size = res.getRow();
        if(size != 0){
            users = new User[size];
            res.first();
            users[usercount] = new User(res.getString("email"), res.getString("name"), res.getString("password"), res.getDouble("budget"));
            usercount++;
            while(res.next()){
                users[usercount] =  new User(res.getString("email"),res.getString("name"),res.getString("password"), res.getDouble("budget"));
                usercount++;
            }
            return users;
        } else return null;
    }
    
    public Admin[] getAdmins() throws SQLException{
        Database db = new Database();
        ResultSet res = db.query("select * from user where isAdmin=true");
        int admincount = 0;
        res.last();
        int size = res.getRow();
        if(size != 0){
            admins = new Admin[size];
            res.first();
            admins[admincount] = new Admin(res.getString("email"), res.getString("name"), res.getString("password"));
            admincount++;
            while(res.next()){
                admins[admincount] = new Admin(res.getString("email"), res.getString("name"), res.getString("password"));
                admincount++;
            }
            return admins;
        } else return null;
    }
    
    public void addPlaces(String name,double x, double y) throws SQLException{
        Database db = new Database();
        db.update("INSERT INTO place VALUES('"+name+"',"+x+","+y+")");
    }
    
    public Place[] getPlaces() throws SQLException{
        Database db = new Database();
        ResultSet res = db.query("select * from place");
        int placecount = 0;
        res.last();
        int size = res.getRow();
        if(size != 0){
            places = new Place[size];
            res.first();
            places[placecount] = new Place(res.getString("name"), res.getDouble("coorX"), res.getDouble("coorY"));
            placecount++;
            while(res.next()){
                places[placecount] = new Place(res.getString("name"), res.getDouble("coorX"), res.getDouble("coorY"));
                placecount++;
            }
            return places;
        } else return null;
    }
    
    @Override
    public String getName() {
        return name.get();
    }

    @Override
    public void setName(String name) {
        try {
            this.name = new SimpleStringProperty(name);
            Database db = new Database();
            db.update("UPDATE user SET name='"+name+"' where email='"+this.email+"'");
        } catch (SQLException ex) {
            System.out.println("SQL ERROR!!");
        }
    }

    @Override
    public Transaction[] getTransaction(){
        try {
            Database db = new Database();
            ResultSet res = db.query("select * from transaction");
            int transactionCount = 0;
            res.last();
            int size = res.getRow();
            if(size != 0){
                transactions = new Transaction[size];
                res.first();
                transactions[transactionCount] = new Transaction(
                        res.getDate("transactionDate").toLocalDate(), res.getInt("id"), res.getString("email"), 
                        res.getDate("date").toString(), searchPlace(res.getString("from")), searchPlace(res.getString("dest")),
                        res.getString("time"),res.getInt("seat"), res.getDouble("price")
                );
                transactionCount++;
                while(res.next()){
                    transactions[transactionCount] = new Transaction(
                            res.getDate("transactionDate").toLocalDate(), res.getInt("id"), res.getString("email"), 
                            res.getDate("date").toString(), searchPlace(res.getString("from")), searchPlace(res.getString("dest")),
                            res.getString("time"),res.getInt("seat"), res.getDouble("price")
                    );
                    transactionCount++;
                }
            }else return null;
        } catch (SQLException ex) {
            System.out.println("SQL ERROR!!");
        }
        return transactions;
    }
    
    public void resetPassword(String password) throws SQLException{                         // tambahan
        Database db = new Database();
        ResultSet res = db.query("select * from user where email = '"+email+"';");
        if(res.next()){
            db.update("update user set password = '"+password+"' where email = '"+email+"';");
        }
    }
}
