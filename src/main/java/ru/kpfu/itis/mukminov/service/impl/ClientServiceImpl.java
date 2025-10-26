package ru.kpfu.itis.mukminov.service.impl;

import ru.kpfu.itis.mukminov.dao.ClientDao;
import ru.kpfu.itis.mukminov.dto.ClientDto;
import ru.kpfu.itis.mukminov.entity.Client;
import ru.kpfu.itis.mukminov.service.ClientService;
import ru.kpfu.itis.mukminov.util.PasswordUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClientServiceImpl implements ClientService {

    private final ClientDao clientDao;

    public ClientServiceImpl(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public void registerClient(String name, String lastname, String phoneNumber, String email, String password) {
        if (isEmailTaken(email)) {
            throw new IllegalArgumentException("Email already exists");
        }

        String salt = PasswordUtil.generateSalt();
        String passwordHash = PasswordUtil.hashPassword(password, salt);

        Client client = new Client(null, name, lastname, phoneNumber, email, passwordHash, salt);

        clientDao.save(client);
    }

    @Override
    public boolean authenticate(String email, String password) {
        Optional<Client> clientOpt = clientDao.findByEmail(email);

        if (clientOpt.isEmpty()) {
            return false;
        }

        Client client = clientOpt.get();
        String hashedPassword = PasswordUtil.hashPassword(password, client.getPasswordSalt());

        return hashedPassword.equals(client.getPasswordHash());
    }

    @Override
    public Optional<ClientDto> findById(Long id) {
        return clientDao.findById(id).map(this::convertToDto);
    }

    @Override
    public Optional<ClientDto> findByEmail(String email) {
        return clientDao.findByEmail(email).map(this::convertToDto);
    }

    @Override
    public List<ClientDto> getAllClients() {
        return clientDao.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateClient(Long id, String name, String lastname, String phoneNumber, String email) {
        Optional<Client> existingClient = clientDao.findById(id);

        if (existingClient.isEmpty()) {
            throw new IllegalArgumentException("Client not found");
        }

        Client client = existingClient.get();
        Client updatedClient = new Client(
                client.getId(),
                name,
                lastname,
                phoneNumber,
                email,
                client.getPasswordHash(),
                client.getPasswordSalt()
        );

        clientDao.update(updatedClient);
    }

    @Override
    public void deleteClient(Long id) {
        clientDao.deleteById(id);
    }

    @Override
    public boolean isEmailTaken(String email) {
        return clientDao.existsByEmail(email);
    }

    private ClientDto convertToDto(Client client) {
        return new ClientDto(
                client.getId(),
                client.getName(),
                client.getLastname(),
                client.getPhoneNumber(),
                client.getEmail()
        );
    }
}
