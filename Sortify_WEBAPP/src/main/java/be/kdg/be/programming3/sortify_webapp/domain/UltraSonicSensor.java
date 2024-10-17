package be.kdg.be.programming3.sortify_webapp.domain;

import java.time.LocalDateTime;

public class UltraSonicSensor {
    private LocalDateTime localDateTime;
    private boolean isOpen;

    public UltraSonicSensor(boolean isOpen) {
        this.localDateTime = LocalDateTime.now();
        this.isOpen = isOpen;
    }

    // Getters and setters
    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
