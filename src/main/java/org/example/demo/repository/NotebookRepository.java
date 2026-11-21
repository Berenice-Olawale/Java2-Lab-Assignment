package org.example.demo.repository;

import org.example.demo.entity.Notebook;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotebookRepository extends JpaRepository<Notebook, Integer> {

    /** Case-insensitive, because the data contains both "ASUS" and "Asus". */
    List<Notebook> findByManufacturerIgnoreCase(String manufacturer, Sort sort);

}
