package org.example.demo.controller;

import org.example.demo.entity.Notebook;
import org.example.demo.repository.NotebookRepository;
import org.example.demo.repository.OpSystemRepository;
import org.example.demo.repository.ProcessorRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/crud")
public class CrudController {

    private final NotebookRepository notebookRepository;
    private final ProcessorRepository processorRepository;
    private final OpSystemRepository opSystemRepository;

    public CrudController(NotebookRepository notebookRepository,
                          ProcessorRepository processorRepository,
                          OpSystemRepository opSystemRepository) {
        this.notebookRepository = notebookRepository;
        this.processorRepository = processorRepository;
        this.opSystemRepository = opSystemRepository;
    }

    // READ
    @GetMapping
    public String list(Model model) {
        model.addAttribute("notebooks", notebookRepository.findAll(Sort.by("id")));
        return "crud";
    }

    // CREATE form
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("notebook", new Notebook());
        addFormData(model);
        return "crud-form";
    }

    // UPDATE form
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        Notebook notebook = notebookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        model.addAttribute("notebook", notebook);
        addFormData(model);
        return "crud-form";
    }

    // CREATE and UPDATE
    @PostMapping("/save")
    public String save(@ModelAttribute Notebook notebook,
                       @RequestParam int processorId,
                       @RequestParam int opsystemId,
                       RedirectAttributes redirectAttributes) {

        boolean isNew = notebook.getId() == null;

        notebook.setProcessor(processorRepository.findById(processorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST)));

        notebook.setOpsystem(opSystemRepository.findById(opsystemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST)));

        notebookRepository.save(notebook);

        redirectAttributes.addFlashAttribute("message",
                (isNew ? "Notebook created" : "Notebook updated")
                        + " (ID=" + notebook.getId() + ")");

        return "redirect:/crud";
    }

    // DELETE
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {

        notebookRepository.deleteById(id);

        redirectAttributes.addFlashAttribute("message",
                "Notebook deleted (ID=" + id + ")");

        return "redirect:/crud";
    }

    // Load dropdown options for the form
    private void addFormData(Model model) {
        model.addAttribute("processors",
                processorRepository.findAll(Sort.by("manufacturer", "type")));

        model.addAttribute("opsystems",
                opSystemRepository.findAll(Sort.by("osname")));
    }
}