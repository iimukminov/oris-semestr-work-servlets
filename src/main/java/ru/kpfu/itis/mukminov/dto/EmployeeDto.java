package ru.kpfu.itis.mukminov.dto;

import ru.kpfu.itis.mukminov.enums.Role;

public class EmployeeDto {
    private Long id;
    private String name;
    private String lastname;
    private String email;
    private Role role;
    private String position;

    public EmployeeDto() {
    }

    public EmployeeDto(Long id, String name, String lastname, String email, Role role, String position) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.role = role;
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public String getPosition() {
        return position;
    }
}
