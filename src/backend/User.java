
package backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.beans.property.SimpleStringProperty;

public class User extends Account{
    private double budget = 0;
    
    public User (String email, String name, String password, double budget) throws SQLException{
        super(email,name,false,password);
        this.budget = budget;
    }
    
    public void addTransactions(LocalDate transDate, String email, LocalDate date, String from, String dest, String time, int seat, double price, double budget) throws SQLException{
        Database db = new Database();
        db.update("insert into transaction values ('"+transDate+"','"+email+"','"+date+"','"+from+"','"+dest+"','"+time+"',"+seat+","+price+",null);");
        db.update("update user set budget="+(budget-price)+" where email='"+Database.email+"'");
    }
    
    public void topUp(ResultSet res, double cash) throws SQLException{
        Database db = new Database();
        db.update("UPDATE user SET budget="+(res.getDouble("budget")+cash)+" WHERE email='"+Database.email+"'");
    }
    
    public double getBudget(){
        return budget;
    }

    @Override
    public String getName() {
        return name.get();
    }

    @Override
    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    @Override
    public Transaction[] getTransaction(){
        try {
            Database db = new Database();
            ResultSet res = db.query("select * from transaction where email='"+Database.email+"'");
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
            } else return null;
            
        } catch (SQLException ex) {
            System.out.println("SQL ERROR!!");
        }
        return transactions;
    }
}
