package com.tanmoy.javawebapp.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionController {

    @GetMapping("/version")
    public Map<String, String> version() {
        return Map.of(
                "application", "EmployeeHub",
                "version", "1.0.0",
                "environment", "Development"
        );
    }
}