package tableView;

import java.time.LocalDate;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class UserView {
    private SimpleStringProperty email;
    private SimpleStringProperty name;
    private SimpleDoubleProperty budget;

    public UserView(String email, String name, double budget) {
        this.email = new SimpleStringProperty(email);
        this.name = new SimpleStringProperty(name);
        this.budget = new SimpleDoubleProperty(budget);
    }

    public String getEmail() {
        return email.get();
    }

    public String getName() {
        return name.get();
    }

    public Double getBudget() {
        return budget.get();
    }
    
}
