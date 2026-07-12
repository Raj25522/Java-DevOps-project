package com.tanmoy.javawebapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tanmoy.javawebapp.model.Employee;

@Service
public class EmployeeService {

    public List<Employee> getEmployees() {
        return List.of(
                new Employee(1L, "Amit Sharma", "Engineering", "Software Engineer", "Active"),
                new Employee(2L, "Riya Sen", "Operations", "Senior Operations Analyst", "Active"),
                new Employee(3L, "Rahul Verma", "Finance", "Finance Executive", "Inactive")
        );
    }
}