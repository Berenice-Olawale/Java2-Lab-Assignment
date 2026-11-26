package org.example.demo.controller;

import org.example.demo.entity.Notebook;
import org.example.demo.repository.NotebookRepository;
import org.example.demo.repository.OpSystemRepository;
import org.example.demo.repository.ProcessorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/notebooks")
public class NotebookRestController {

    private final NotebookRepository notebookRepository;
    private final ProcessorRepository processorRepository;
    private final OpSystemRepository opSystemRepository;

    public NotebookRestController(NotebookRepository notebookRepository,
                                  ProcessorRepository processorRepository,
                                  OpSystemRepository opSystemRepository) {
        this.notebookRepository = notebookRepository;
        this.processorRepository = processorRepository;
        this.opSystemRepository = opSystemRepository;
    }

    // GET: return all notebooks
    @GetMapping
    public List<Notebook> getAll() {
        return notebookRepository.findAll();
    }

    // GET: return one notebook
    @GetMapping("/{id}")
    public Notebook getOne(@PathVariable Integer id) {
        return notebookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // POST: create notebook
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Notebook create(@RequestBody Notebook notebook) {
        notebook.setId(null);
        setRelations(notebook);
        return notebookRepository.save(notebook);
    }

    // PUT: update notebook
    @PutMapping("/{id}")
    public Notebook update(@PathVariable Integer id, @RequestBody Notebook notebook) {
        Notebook existing = notebookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        existing.setManufacturer(notebook.getManufacturer());
        existing.setType(notebook.getType());
        existing.setDisplay(notebook.getDisplay());
        existing.setMemory(notebook.getMemory());
        existing.setHarddisk(notebook.getHarddisk());
        existing.setVideocontroller(notebook.getVideocontroller());
        existing.setPrice(notebook.getPrice());
        existing.setPieces(notebook.getPieces());

        setRelations(existing);

        return notebookRepository.save(existing);
    }

    // DELETE: delete notebook
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        if (!notebookRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        notebookRepository.deleteById(id);
    }

    private void setRelations(Notebook notebook) {
        if (notebook.getProcessor() != null
                && notebook.getProcessor().getId() != 0) {
            notebook.setProcessor(
                    processorRepository.findById(notebook.getProcessor().getId())
                            .orElseThrow(() ->
                                    new ResponseStatusException(HttpStatus.BAD_REQUEST)));
        } else {
            notebook.setProcessor(null);
        }

        if (notebook.getOpsystem() != null
                && notebook.getOpsystem().getId() != 0) {
            notebook.setOpsystem(
                    opSystemRepository.findById(notebook.getOpsystem().getId())
                            .orElseThrow(() ->
                                    new ResponseStatusException(HttpStatus.BAD_REQUEST)));
        } else {
            notebook.setOpsystem(null);
        }
    }
}