package be.kdg.be.programming3.sortify_webapp.repository;



import be.kdg.be.programming3.sortify_webapp.domain.Trash;

import java.util.List;

public interface TrashRepository {
    List<Trash> readTrashes();
    Trash createTrash(Trash trash);
}
