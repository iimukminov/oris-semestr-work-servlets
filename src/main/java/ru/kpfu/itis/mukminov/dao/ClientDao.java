package ru.kpfu.itis.mukminov.dao;

import ru.kpfu.itis.mukminov.entity.Client;
import java.util.List;
import java.util.Optional;

public interface ClientDao {

    void save(Client client);
    void update(Client client);
    void deleteById(Long id);
    boolean existsByEmail(String email);

    Optional<Client> findById(Long id);
    Optional<Client> findByEmail(String email);
    List<Client> findAll();
}
