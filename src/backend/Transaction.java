package backend;

import java.time.LocalDate;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Transaction{
    private LocalDate transactionDate;
    private SimpleIntegerProperty id;
    private SimpleStringProperty email;
    private SimpleStringProperty date;
    private Place from;
    private Place dest;
    private SimpleStringProperty time;
    private SimpleIntegerProperty seat;
    private SimpleDoubleProperty price;   

    public Transaction(LocalDate transactionDate, int id, String email, String date, Place from, Place dest, String time, int seat, double price) {
        this.transactionDate = transactionDate;
        this.id = new SimpleIntegerProperty(id);
        this.email = new SimpleStringProperty(email);
        this.date = new SimpleStringProperty(date);
        this.from = from;
        this.dest = dest;
        this.time = new SimpleStringProperty(time);
        this.seat = new SimpleIntegerProperty(seat);
        this.price = new SimpleDoubleProperty(price);
    }    
        
    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public int getId() {
        return id.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getDate() {
        return date.get();
    }

    public Place getFrom() {
        return from;
    }

    public Place getDest() {
        return dest;
    }

    public String getTime() {
        return time.get();
    }
    
    public int getSeat(){
        return seat.get();
    }

    public Double getPrice() {
        return price.get();
    }


}
