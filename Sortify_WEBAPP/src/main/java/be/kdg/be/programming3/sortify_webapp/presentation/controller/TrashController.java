package be.kdg.be.programming3.sortify_webapp.presentation.controller;

import be.kdg.be.programming3.sortify_webapp.domain.Trash;
import be.kdg.be.programming3.sortify_webapp.domain.UltraSonicSensor;
import be.kdg.be.programming3.sortify_webapp.service.TrashServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TrashController {

    @Autowired
    private TrashServiceImpl trashService;

    @GetMapping("/bin-status")
    public String getBinStatus(Model model) {
        // Fetch trashes from the database
        List<Trash> trashes = trashService.getTrashes();

        // Fetch static sensor data (for now)
        UltraSonicSensor sensor = trashService.checkBinStatus();

        model.addAttribute("trashes", trashes);
        model.addAttribute("sensor", sensor);

        return "bin-status";  // Return Thymeleaf template
    }
}

