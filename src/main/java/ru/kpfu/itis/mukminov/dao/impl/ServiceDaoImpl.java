package ru.kpfu.itis.mukminov.dao.impl;

import ru.kpfu.itis.mukminov.dao.ServiceDao;
import ru.kpfu.itis.mukminov.dao.exceptions.DaoException;
import ru.kpfu.itis.mukminov.entity.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServiceDaoImpl implements ServiceDao {
    private final DataSource dataSource;

    public ServiceDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Service mapRowToService(ResultSet row) throws SQLException {
        Service service = new Service();
        service.setId(row.getInt("id"));
        service.setName(row.getString("name"));
        service.setPrice(row.getBigDecimal("price"));
        service.setDescription(row.getString("description"));
        return service;
    }

    @Override
    public void save(Service service) {
        String sql = "INSERT INTO services(name, price, description) VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, service.getName());
            preparedStatement.setBigDecimal(2, service.getPrice());
            preparedStatement.setString(3, service.getDescription());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Service service) {
        String sql = "UPDATE services SET name = ?, price = ?, description = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, service.getName());
            preparedStatement.setBigDecimal(2, service.getPrice());
            preparedStatement.setString(3, service.getDescription());
            preparedStatement.setInt(4, service.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM services WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Service> findById(Integer id) {
        String sql = "SELECT * FROM services WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapRowToService(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Service> findAll() {
        String sql = "SELECT * FROM services";
        List<Service> services = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    services.add(mapRowToService(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return services;
    }
}
