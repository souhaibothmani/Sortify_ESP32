package be.kdg.be.programming3.sortify_webapp.presentation.controller;

import be.kdg.be.programming3.sortify_webapp.domain.UltraSonicSensor;
import be.kdg.be.programming3.sortify_webapp.service.TrashServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrashController {

    @Autowired
    private TrashServiceImpl trashService;


    @GetMapping("/bin-status")
    public UltraSonicSensor getBinStatus() {
        return trashService.checkBinStatus();
    }
}
