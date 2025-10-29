package ru.kpfu.itis.mukminov.service;

import ru.kpfu.itis.mukminov.dto.ClientDto;
import java.util.List;
import java.util.Optional;

public interface ClientService {

    void registerClient(String name, String lastname, String phoneNumber, String email, String password);

    boolean authenticate(String email, String password);

    Optional<ClientDto> findById(Long id);

    Optional<ClientDto> findByEmail(String email);

    List<ClientDto> findAll();

    void updateClient(Long id, String name, String lastname, String phoneNumber, String email, String password);

    void deleteClient(Long id);

    boolean isEmailTaken(String email);
}
