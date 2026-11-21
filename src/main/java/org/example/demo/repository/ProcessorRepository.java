package org.example.demo.repository;

import org.example.demo.entity.Processor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessorRepository extends JpaRepository<Processor, Integer> {
}
