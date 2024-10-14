package be.kdg.be.programming3.sortify_webapp.service;


import be.kdg.be.programming3.sortify_webapp.domain.Trash;
import be.kdg.be.programming3.sortify_webapp.domain.TrashType;

import java.time.LocalDateTime;
import java.util.List;

public interface TrashService {
    List<Trash> getTrashes();
    Trash addTrash(TrashType trashType, LocalDateTime timeThrown, double weight);
}
