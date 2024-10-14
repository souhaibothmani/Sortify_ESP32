package be.kdg.be.programming3.sortify_webapp.domain;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class Trash {
    private TrashType trashType;
    private LocalDateTime timeThrown;
    private double weight;
    private int id;
    //private String trashName;

    public Trash(TrashType trashType, LocalDateTime timeThrown, double weight) {
        this.trashType = trashType;
        this.timeThrown = timeThrown;
        this.weight = weight;
    }

    public void setId(int id) {
        this.id = id;
    }
}
