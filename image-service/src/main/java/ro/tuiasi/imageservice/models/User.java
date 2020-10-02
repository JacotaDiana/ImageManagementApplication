package ro.tuiasi.imageservice.models;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
