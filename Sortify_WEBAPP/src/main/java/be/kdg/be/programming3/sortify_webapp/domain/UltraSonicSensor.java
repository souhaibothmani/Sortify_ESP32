package be.kdg.be.programming3.sortify_webapp.domain;

import java.time.LocalDateTime;

public class UltraSonicSensor {
    private LocalDateTime localDateTime;
    private boolean isOpen;

    public UltraSonicSensor(boolean isOpen) {
        this.isOpen = isOpen;
    }



}
