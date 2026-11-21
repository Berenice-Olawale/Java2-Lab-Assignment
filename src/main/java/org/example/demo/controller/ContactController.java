package org.example.demo.controller;

import jakarta.validation.Valid;
import org.example.demo.dto.ContactForm;
import org.example.demo.entity.ContactMessage;
import org.example.demo.repository.ContactMessageRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

/** Contact menu: form with server-side validation, the message is saved to the database. */
@Controller
public class ContactController {

    private final ContactMessageRepository contactMessageRepository;

    public ContactController(ContactMessageRepository contactMessageRepository) {
        this.contactMessageRepository = contactMessageRepository;
    }

    @GetMapping("/contact")
    public String contactForm(Model model) {
        if (!model.containsAttribute("contactForm")) {
            model.addAttribute("contactForm", new ContactForm());
        }
        return "contact";
    }

    @PostMapping("/contact")
    public String sendMessage(@Valid @ModelAttribute("contactForm") ContactForm form,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "contact";   // show the form again with the error messages
        }
        contactMessageRepository.save(new ContactMessage(
                form.getName().trim(),
                form.getEmail().trim(),
                form.getSubject().trim(),
                form.getMessage().trim(),
                LocalDateTime.now()));
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/contact";   // redirect, so a browser refresh does not send the form again
    }
}
