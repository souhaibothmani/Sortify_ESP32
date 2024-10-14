package be.kdg.be.programming3.sortify_webapp.presentation.controller;

import be.kdg.programming3.dogcloud.domain.Dog;
import be.kdg.programming3.dogcloud.domain.DogType;
import be.kdg.programming3.dogcloud.service.DogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/dogs")
public class DogController {
    private DogService dogService;

    public DogController(DogService dogService) {
        this.dogService = dogService;
    }
    private static final Logger logger = LoggerFactory.getLogger(DogController.class);

    @GetMapping
    public String getDogsView(Model model) {
        logger.debug("Getting the dog view");
        List<Dog> dogs = dogService.getDogs();
        model.addAttribute("dogs",dogs);
        return "dogs";
    }
    @GetMapping("/info")
    public String getInfo(Model model) {
        logger.info("Getting the info view");
        int dogNumber = dogService.getDogs().size();
        model.addAttribute("dogsinfo", dogNumber);
        return "dogsinfo";  // Make sure you have a "dogsinfo.html" template file
    }


    @GetMapping("/add")
    public String getAddDogsForm(){
        return "adddog";
    }

    @PostMapping("/add")
    public String processAddDog(Dog dog){
        logger.debug("Received form data for a new dog:" + dog.getName());
        dogService.addDog(dog.getName(), DogType.LABRADOR);
        return "redirect:/dogs";
    }

}
