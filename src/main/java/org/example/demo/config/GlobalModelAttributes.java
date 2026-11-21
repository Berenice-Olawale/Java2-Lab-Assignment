package org.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/** Values that every page needs (site name + creators for the footer). */
@ControllerAdvice
public class GlobalModelAttributes {

    @Value("${app.name}")
    private String appName;

    @Value("${app.creators}")
    private String creators;

    @ModelAttribute("appName")
    public String appName() {
        return appName;
    }

    @ModelAttribute("creators")
    public String creators() {
        return creators;
    }
}
