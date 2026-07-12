package com.tanmoy.javawebapp.model;

public class Employee {

    private Long id;
    private String name;
    private String department;
    private String role;
    private String status;

    public Employee(Long id, String name, String department, String role, String status) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.role = role;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public String getRole() {
        return role;
    }


public String getStatus() {
    return status;
}
}