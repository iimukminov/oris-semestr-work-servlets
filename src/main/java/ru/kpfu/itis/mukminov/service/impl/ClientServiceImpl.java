package ru.kpfu.itis.mukminov.service.impl;

import ru.kpfu.itis.mukminov.dao.ClientDao;
import ru.kpfu.itis.mukminov.dao.EquipmentDao;
import ru.kpfu.itis.mukminov.dao.OrderDao;
import ru.kpfu.itis.mukminov.dao.impl.EquipmentDaoImpl;
import ru.kpfu.itis.mukminov.dao.impl.OrderDaoImpl;
import ru.kpfu.itis.mukminov.dto.ClientDto;
import ru.kpfu.itis.mukminov.entity.Client;
import ru.kpfu.itis.mukminov.entity.Equipment;
import ru.kpfu.itis.mukminov.entity.Order;
import ru.kpfu.itis.mukminov.service.ClientService;
import ru.kpfu.itis.mukminov.util.PasswordUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClientServiceImpl implements ClientService {

    private final ClientDao clientDao;
    private final EquipmentDao equipmentDao;
    private final OrderDao orderDao;

    public ClientServiceImpl(ClientDao clientDao, EquipmentDao equipmentDao,  OrderDao orderDao) {
        this.clientDao = clientDao;
        this.equipmentDao = equipmentDao;
        this.orderDao = orderDao;
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
    public List<ClientDto> findAll() {
        return clientDao.findAll().stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public void updateClient(Long id, String name, String lastname, String phoneNumber, String email, String password) {
        Optional<Client> existingClient = clientDao.findById(id);

        if (existingClient.isEmpty()) {
            throw new IllegalArgumentException("Client not found");
        }

        Optional<Client> clientOpt = clientDao.findByEmail(email);
        if (clientOpt.isPresent() && !clientOpt.get().getId().equals(id)) {
            throw new IllegalArgumentException("Email already exists");
        }

        Client client = existingClient.get();

        String salt = "";
        String hashedPassword = "";
        if (password == null || password.isEmpty()) {
            salt = client.getPasswordSalt();
            hashedPassword = client.getPasswordHash();
        } else {
            salt = PasswordUtil.generateSalt();
            hashedPassword = PasswordUtil.hashPassword(password, salt);
        }
        Client updatedClient = new Client(
                client.getId(),
                name,
                lastname,
                phoneNumber,
                email,
                hashedPassword,
                salt
        );

        clientDao.update(updatedClient);
    }

    @Override
    public void deleteClient(Long id) {
        for (Order order: orderDao.findByClientId(id)) {
            orderDao.delete(order.getId());
        }
        for (Equipment equipment: equipmentDao.findByClientId(id)) {
            equipmentDao.delete(equipment.getId());
        }
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
