package org.example.demo;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.example.demo.dto.ContactForm;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Tests the server-side validation rules of the Contact form (no database needed). */
class ContactFormValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    private ContactForm validForm() {
        ContactForm form = new ContactForm();
        form.setName("Jane Doe");
        form.setEmail("jane@example.com");
        form.setSubject("Hello");
        form.setMessage("This is a valid message text.");
        return form;
    }

    @Test
    void validFormHasNoViolations() {
        assertTrue(validator.validate(validForm()).isEmpty());
    }

    @Test
    void emptyFormIsRejected() {
        assertFalse(validator.validate(new ContactForm()).isEmpty());
    }

    @Test
    void invalidEmailIsRejected() {
        ContactForm form = validForm();
        form.setEmail("not-an-email");
        assertEquals(1, validator.validate(form).size());
    }

    @Test
    void tooShortMessageIsRejected() {
        ContactForm form = validForm();
        form.setMessage("short");
        assertEquals(1, validator.validate(form).size());
    }
}
