package ru.kpfu.itis.mukminov.dao.impl;

import ru.kpfu.itis.mukminov.dao.EquipmentDao;
import ru.kpfu.itis.mukminov.dao.exceptions.DaoException;
import ru.kpfu.itis.mukminov.entity.Equipment;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EquipmentDaoImpl implements EquipmentDao {
    private final DataSource dataSource;

    public EquipmentDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Equipment mapRowToEquipment(ResultSet row) throws SQLException {
        Equipment equipment = new Equipment();
        equipment.setId(row.getLong("id"));
        equipment.setClientId(row.getLong("client_id"));
        equipment.setType(row.getString("type"));
        equipment.setBrand(row.getString("brand"));
        equipment.setModel(row.getString("model"));
        equipment.setSerialNumber(row.getString("serial_number"));
        equipment.setDescription(row.getString("description"));
        return equipment;
    }

    @Override
    public void save(Equipment equipment) {
        String sql = "INSERT INTO equipments(client_id, type, brand, model, serial_number, description) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, equipment.getClientId());
            preparedStatement.setString(2, equipment.getType());
            preparedStatement.setString(3, equipment.getBrand());
            preparedStatement.setString(4, equipment.getModel());
            preparedStatement.setString(5, equipment.getSerialNumber());
            preparedStatement.setString(6, equipment.getDescription());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Equipment equipment) {
        String sql = "UPDATE equipments SET client_id = ?, type = ?, brand = ?, model = ?, serial_number = ?, description = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, equipment.getClientId());
            preparedStatement.setString(2, equipment.getType());
            preparedStatement.setString(3, equipment.getBrand());
            preparedStatement.setString(4, equipment.getModel());
            preparedStatement.setString(5, equipment.getSerialNumber());
            preparedStatement.setString(6, equipment.getDescription());
            preparedStatement.setLong(7, equipment.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM equipments WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Equipment> findById(Long id) {
        String sql = "SELECT * FROM equipments WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapRowToEquipment(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Equipment> findByClientId(Long clientId) {
        String sql = "SELECT * FROM equipments WHERE client_id = ?";
        List<Equipment> equipments = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, clientId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    equipments.add(mapRowToEquipment(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return equipments;
    }

    @Override
    public List<Equipment> findAll() {
        String sql = "SELECT * FROM equipments";
        List<Equipment> equipments = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    equipments.add(mapRowToEquipment(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return equipments;
    }
}
