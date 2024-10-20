package be.kdg.be.programming3.sortify_webapp.service;

import be.kdg.be.programming3.sortify_webapp.domain.Trash;
import be.kdg.be.programming3.sortify_webapp.domain.UltraSonicSensor;
import be.kdg.be.programming3.sortify_webapp.repository.TrashRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrashServiceImpl implements TrashService {
    private Logger logger = LoggerFactory.getLogger(TrashServiceImpl.class);
    private final TrashRepository trashRepository;

    public TrashServiceImpl(TrashRepository trashRepository) {
        this.trashRepository = trashRepository;
    }

    @Override
    public List<Trash> getTrashes() {
        logger.info("Fetching all trashes from the database...");
        return trashRepository.findAll(); // Using JPA's findAll()
    }

    @Override
    public Trash addTrash(Trash trash) {
        logger.info("Saving a new trash record to the database...");
        return trashRepository.save(trash);  // Using JPA's save()
    }


    // Temporarily returning a static UltraSonicSensor value
    @Override
    public UltraSonicSensor checkBinStatus() {
        return new UltraSonicSensor(false); // Return a default closed bin status
    }
}
