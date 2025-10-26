package ru.kpfu.itis.mukminov.dto;

public class ClientDto {
    private Long id;
    private String name;
    private String lastname;
    private String phoneNumber;
    private String email;

    public ClientDto() {
    }

    public ClientDto(Long id, String name, String lastname, String phoneNumber, String email) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.email = email;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }
}
