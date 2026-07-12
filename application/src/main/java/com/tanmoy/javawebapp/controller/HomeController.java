package com.tanmoy.javawebapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tanmoy.javawebapp.service.EmployeeService;

@Controller
public class HomeController {

    private final EmployeeService employeeService;

    public HomeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public String home(Model model) {
        var employees = employeeService.getEmployees();

        long activeEmployees = employees.stream()
                .filter(employee -> "Active".equalsIgnoreCase(employee.getStatus()))
                .count();

        long departments = employees.stream()
                .map(employee -> employee.getDepartment())
                .distinct()
                .count();

        model.addAttribute("employees", employees);
        model.addAttribute("totalEmployees", employees.size());
        model.addAttribute("activeEmployees", activeEmployees);
        model.addAttribute("departments", departments);

        return "dashboard";
    }
}