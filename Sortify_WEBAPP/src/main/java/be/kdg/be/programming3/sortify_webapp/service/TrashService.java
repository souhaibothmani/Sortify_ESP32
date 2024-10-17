package be.kdg.be.programming3.sortify_webapp.service;

import be.kdg.be.programming3.sortify_webapp.domain.Trash;
import be.kdg.be.programming3.sortify_webapp.domain.UltraSonicSensor;
import java.time.LocalDateTime;
import java.util.List;

public interface TrashService {
    List<Trash> getTrashes();
    Trash addTrash(Trash trash);
    UltraSonicSensor checkBinStatus();
}
