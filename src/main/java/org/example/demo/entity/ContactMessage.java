package org.example.demo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** A message sent through the Contact form (table: contact_messages). */
@Entity
@Table(name = "contact_messages")
public class ContactMessage {

    private static final DateTimeFormatter DISPLAY_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String subject;

    @Column(nullable = false, length = 2000)
    private String message;

    @Column(name = "sent_at", nullable = false)
    private LocalDateTime sentAt;

    public ContactMessage() {
    }

    public ContactMessage(String name, String email, String subject, String message, LocalDateTime sentAt) {
        this.name = name;
        this.email = email;
        this.subject = subject;
        this.message = message;
        this.sentAt = sentAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    /** Sending time as text for the Messages page, e.g. 2026-11-19 17:42:10 */
    public String getSentAtDisplay() {
        return sentAt == null ? "" : sentAt.format(DISPLAY_FORMAT);
    }
}
