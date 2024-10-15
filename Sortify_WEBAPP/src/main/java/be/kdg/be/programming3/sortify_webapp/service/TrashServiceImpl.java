package be.kdg.be.programming3.sortify_webapp.service;

import be.kdg.be.programming3.sortify_webapp.domain.Trash;
import be.kdg.be.programming3.sortify_webapp.domain.TrashType;
import be.kdg.be.programming3.sortify_webapp.domain.UltraSonicSensor;
import be.kdg.be.programming3.sortify_webapp.repository.TrashRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TrashServiceImpl implements TrashService {
    private Logger logger = LoggerFactory.getLogger(TrashServiceImpl.class);
    private TrashRepository trashRepository;
    private ArduinoCommunicationService arduinoCommunicationService; // injection

    public TrashServiceImpl(TrashRepository trashRepository, ArduinoCommunicationService arduinoCommunicationService) {
        logger.info("Creating TrashRepository");
        this.trashRepository = trashRepository;
        this.arduinoCommunicationService = arduinoCommunicationService;
    }

    @Override
    public List<Trash> getTrashes() {
        logger.info("Getting trashes...");
        return trashRepository.readTrashes();
    }

    @Override
    public Trash addTrash(TrashType trashType, LocalDateTime timeThrown, double weight) {
        logger.info("Adding trash with type {} at time {} of weight {}", trashType, timeThrown, weight);
        return trashRepository.createTrash(new Trash(trashType, timeThrown, weight));
    }

    public UltraSonicSensor checkBinStatus() {
        boolean isOpen = arduinoCommunicationService.isBinOpen();
        return new UltraSonicSensor(isOpen);
    }
}
