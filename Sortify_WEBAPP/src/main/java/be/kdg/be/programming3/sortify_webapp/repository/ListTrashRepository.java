package be.kdg.be.programming3.sortify_webapp.repository;

import be.kdg.be.programming3.sortify_webapp.domain.Trash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ListTrashRepository implements TrashRepository {
    private Logger logger = LoggerFactory.getLogger(ListTrashRepository.class);
    private static List<Trash> trashes = new ArrayList<>();
    @Override
    public List<Trash> readTrashes() {
        logger.info("Reading trashes from database...");
        return trashes;
    }

    @Override
    public Trash createTrash(Trash trash) {
        logger.info("Creating trash {}", trash);
        trash.setId(trashes.size());
        trashes.add(trash);
        return trash;
    }
}
