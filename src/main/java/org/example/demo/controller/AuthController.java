package org.example.demo.controller;

import jakarta.validation.Valid;
import org.example.demo.dto.RegisterForm;
import org.example.demo.entity.User;
import org.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/** Login page + Registration. (Logout is handled by Spring Security: POST /logout.) */
@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerForm") RegisterForm form,
                           BindingResult bindingResult) {

        if (!bindingResult.hasFieldErrors("password") && !bindingResult.hasFieldErrors("confirmPassword")
                && !form.getPassword().equals(form.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "mismatch", "The two passwords do not match");
        }
        if (!bindingResult.hasFieldErrors("username")
                && userRepository.findByUsername(form.getUsername()).isPresent()) {
            bindingResult.rejectValue("username", "taken", "This username is already taken!");
        }
        if (bindingResult.hasErrors()) {
            return "register";
        }

        User user = new User();
        user.setUsername(form.getUsername());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setRole("USER");   // every self-registered account is a "registered visitor"
        userRepository.save(user);
        return "redirect:/login?registered";
    }
}
