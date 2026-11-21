package org.example.demo.controller;

import org.example.demo.repository.ContactMessageRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/** Messages menu: only for logged in users (see WebSecurityConfig). Newest message first. */
@Controller
public class MessagesController {

    private final ContactMessageRepository contactMessageRepository;

    public MessagesController(ContactMessageRepository contactMessageRepository) {
        this.contactMessageRepository = contactMessageRepository;
    }

    @GetMapping("/messages")
    public String messages(Model model) {
        model.addAttribute("messages", contactMessageRepository.findAllByOrderBySentAtDesc());
        return "messages";
    }
}
