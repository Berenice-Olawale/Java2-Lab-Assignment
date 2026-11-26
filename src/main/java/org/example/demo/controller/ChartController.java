package org.example.demo.controller;

import org.example.demo.repository.NotebookRepository;
import org.example.demo.repository.NotebookRepository.ChartPoint;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ChartController {

    private final NotebookRepository notebookRepository;

    public ChartController(NotebookRepository notebookRepository) {
        this.notebookRepository = notebookRepository;
    }

    @GetMapping("/chart")
    public String chart(Model model) {

        List<ChartPoint> byManufacturer = notebookRepository.countByManufacturer();
        List<ChartPoint> byOs = notebookRepository.stockByOpSystem();

        model.addAttribute("mLabels", byManufacturer.stream().map(ChartPoint::getLabel).toList());
        model.addAttribute("mValues", byManufacturer.stream().map(ChartPoint::getTotal).toList());

        model.addAttribute("oLabels", byOs.stream().map(ChartPoint::getLabel).toList());
        model.addAttribute("oValues", byOs.stream().map(ChartPoint::getTotal).toList());

        return "chart";
    }
}