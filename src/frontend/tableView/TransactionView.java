package frontend.tableView;

import backend.Transaction;
import java.time.LocalDate;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TransactionView {
    
    private LocalDate transactionDate;
    private SimpleIntegerProperty id;
    private SimpleStringProperty email;
    private SimpleStringProperty date;
    private SimpleStringProperty from;
    private SimpleStringProperty dest;
    private SimpleStringProperty time;
    private SimpleIntegerProperty seat;
    private SimpleDoubleProperty price;

    public TransactionView(Transaction t) {
        this.transactionDate = t.getTransactionDate();
        this.id = new SimpleIntegerProperty(t.getId());
        this.email = new SimpleStringProperty(t.getEmail());
        this.date = new SimpleStringProperty(t.getDate());
        this.from = new SimpleStringProperty(t.getFrom().getName());
        this.dest = new SimpleStringProperty(t.getDest().getName());
        this.time = new SimpleStringProperty(t.getTime());
        this.seat = new SimpleIntegerProperty(t.getSeat());
        this.price = new SimpleDoubleProperty(t.getPrice());
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public String getId() {
        return "TRAN"+id.get();
    }

    public String getDate() {
        return date.get();
    }

    public String getEmail(){
        return email.get();
    }
    
    public String getFrom() {
        return from.get();
    }

    public String getDest() {
        return dest.get();
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
