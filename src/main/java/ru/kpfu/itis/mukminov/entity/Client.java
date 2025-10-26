package ru.kpfu.itis.mukminov.entity;

import java.sql.Timestamp;

public class Client {

    private Long id;
    private String name;
    private String lastname;
    private String phoneNumber;
    private String email;
    private String passwordHash;
    private String passwordSalt;

    public Client() {}

    public Client(String name, String lastname, String phoneNumber, String email, String passwordHash, String passwordSalt) {
        this.name = name;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;
    }

    public Client(Long id, String name, String lastname, String phoneNumber, String email, String passwordHash, String passwordSalt) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }
}
