package org.example.demo.repository;

import org.example.demo.entity.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {

    /** Newest message first (Messages menu). */
    List<ContactMessage> findAllByOrderBySentAtDesc();

}
