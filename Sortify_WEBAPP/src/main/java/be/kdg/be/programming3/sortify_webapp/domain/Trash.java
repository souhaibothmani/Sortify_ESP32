package be.kdg.be.programming3.sortify_webapp.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Trash {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime time;

    private String material;

    // Constructors, Getters, and Setters

    public Trash() { }

    public Trash(LocalDateTime time, String material) {
        this.time = time;
        this.material = material;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
