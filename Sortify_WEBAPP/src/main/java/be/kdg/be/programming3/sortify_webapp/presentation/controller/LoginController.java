package be.kdg.be.programming3.sortify_webapp.controller;

import be.kdg.be.programming3.sortify_webapp.domain.User;
import be.kdg.be.programming3.sortify_webapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public LoginController(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Show the login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    // Handle the login form submission
    @PostMapping("/login")
    public String login(String username, String password, Model model) {
        var user = userRepository.findByUsername(username);

        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return "redirect:/"; // Redirect to home page on successful login
        }

        model.addAttribute("error", "Invalid username or password");
        return "login";
    }

    // Show the registration page
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    // Handle the registration form submission
    @PostMapping("/register")
    public String register(String username, String password, String confirmPassword, Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match!");
            return "register";
        }

        if (userRepository.findByUsername(username).isPresent()) {
            model.addAttribute("error", "Username already exists!");
            return "register";
        }

        // Save the user to the database
        User newUser = new User(username, passwordEncoder.encode(password), "USER");
        userRepository.save(newUser);

        return "redirect:/login?success";
    }
}
