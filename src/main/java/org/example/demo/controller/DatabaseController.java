package org.example.demo.controller;

import org.example.demo.entity.Notebook;
import org.example.demo.repository.NotebookRepository;
import org.example.demo.repository.OpSystemRepository;
import org.example.demo.repository.ProcessorRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.TreeSet;

/** Database menu: shows the data of 3 tables (notebook, processor, opsystem) read through repositories. */
@Controller
public class DatabaseController {

    private final NotebookRepository notebookRepository;
    private final ProcessorRepository processorRepository;
    private final OpSystemRepository opSystemRepository;

    public DatabaseController(NotebookRepository notebookRepository,
                              ProcessorRepository processorRepository,
                              OpSystemRepository opSystemRepository) {
        this.notebookRepository = notebookRepository;
        this.processorRepository = processorRepository;
        this.opSystemRepository = opSystemRepository;
    }

    @GetMapping("/database")
    public String database(@RequestParam(required = false) String manufacturer, Model model) {
        Sort sort = Sort.by("manufacturer", "type");

        List<Notebook> notebooks;
        if (manufacturer == null || manufacturer.isBlank()) {
            notebooks = notebookRepository.findAll(sort);
        } else {
            notebooks = notebookRepository.findByManufacturerIgnoreCase(manufacturer, sort);
        }

        // manufacturers for the filter drop-down (upper case, so "ASUS" and "Asus" are one item)
        TreeSet<String> manufacturers = new TreeSet<>();
        for (Notebook n : notebookRepository.findAll()) {
            manufacturers.add(n.getManufacturer().trim().toUpperCase());
        }

        model.addAttribute("notebooks", notebooks);
        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("selected", manufacturer == null ? "" : manufacturer.trim().toUpperCase());
        model.addAttribute("processors", processorRepository.findAll(Sort.by("manufacturer", "type")));
        model.addAttribute("opsystems", opSystemRepository.findAll(Sort.by("osname")));
        return "database";
    }
}
