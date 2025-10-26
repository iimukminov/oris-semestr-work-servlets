package ru.kpfu.itis.mukminov.entity;


import ru.kpfu.itis.mukminov.enums.Role;

public class Employee {
    private Long id;
    private String name;
    private String lastname;
    private String email;
    private String passwordHash;
    private String passwordSalt;
    private Role role;
    private String position;

    public Employee() {}

    public Employee(Long id, String name, String lastname, String email,
                    String passwordHash, String passwordSalt, Role role, String position) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;
        this.role = role;
        this.position = position;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getPasswordSalt() { return passwordSalt; }
    public void setPasswordSalt(String passwordSalt) { this.passwordSalt = passwordSalt; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
}
