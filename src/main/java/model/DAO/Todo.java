package model.DAO;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Todo")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "addingDate")
    private java.sql.Timestamp addingDate;

    @Column(name = "color")
    private char color;

    @Column(name = "description")
    private String description;

    @Column(name = "deadline")
    private java.sql.Timestamp deadline;

    @Column(name = "employee")
    private String employee;

    @Column(name = "isDone")
    private boolean isDone;

    public Todo() {
    }

    public Todo(Timestamp addingDate, char color, String description, Timestamp deadline, String employee, boolean isDone) {
        this.addingDate = addingDate;
        this.color = color;
        this.description = description;
        this.deadline = deadline;
        this.employee = employee;
        this.isDone = isDone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getAddingDate() { return addingDate; }

    public void setAddingDate(Timestamp addingDate) { this.addingDate = addingDate; }

    public char getColor() {
        return color;
    }

    public void setColor(char color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDeadline() {
        return deadline;
    }

    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", addingDate=" + addingDate +
                ", color=" + color +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", employee='" + employee + '\'' +
                ", isDone=" + isDone +
                '}';
    }
}
