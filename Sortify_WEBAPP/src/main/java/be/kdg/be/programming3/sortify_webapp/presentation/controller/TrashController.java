package be.kdg.be.programming3.sortify_webapp.presentation.controller;

import be.kdg.be.programming3.sortify_webapp.domain.Trash;
import be.kdg.be.programming3.sortify_webapp.domain.UltraSonicSensor;
import be.kdg.be.programming3.sortify_webapp.service.TrashServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
    @PostMapping("/bin-open")
    public ResponseEntity<String> binOpened() {
        try {
            // Create a new Trash object
            Trash trash = new Trash(LocalDateTime.now(), null);  // You can modify the material later if needed
            trashService.addTrash(trash);  // Save the trash to the database

            // Return success response
            return ResponseEntity.ok("Bin open data saved successfully!");
        } catch (Exception e) {
            // Log error details, including the message
            System.err.println("Error processing bin open data: " + e.getMessage());
            e.printStackTrace();

            // Return an error response
            return ResponseEntity.status(500).body("Error processing bin open data.");
        }
    }


}



