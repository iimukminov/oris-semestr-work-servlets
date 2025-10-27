package ru.kpfu.itis.mukminov.dao.impl;

import ru.kpfu.itis.mukminov.dao.PartDao;

import ru.kpfu.itis.mukminov.dao.exceptions.DaoException;
import ru.kpfu.itis.mukminov.entity.Part;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PartDaoImpl implements PartDao {
    private final DataSource dataSource;

    public PartDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Part mapRowToPart(ResultSet row) throws SQLException {
        Part part = new Part();
        part.setId(row.getLong("id"));
        part.setName(row.getString("name"));
        part.setQuantity(row.getInt("quantity_in_stock"));
        part.setPrice(row.getBigDecimal("price"));
        return part;
    }

    @Override
    public void save(Part part) {
        String sql = "INSERT INTO parts(name, quantity_in_stock, price) VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, part.getName());
            preparedStatement.setInt(2, part.getQuantity());
            preparedStatement.setBigDecimal(3, part.getPrice());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    @Override
    public void update(Part part) {
        String sql = "UPDATE parts SET name = ?, quantity_in_stock = ?, price = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, part.getName());
            preparedStatement.setInt(2, part.getQuantity());
            preparedStatement.setBigDecimal(3, part.getPrice());
            preparedStatement.setLong(4, part.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM parts WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Part> findById(Long id) {
        String sql = "SELECT * FROM parts WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapRowToPart(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Part> findAll() {
        String sql = "SELECT * FROM parts";
        List<Part> parts = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    parts.add(mapRowToPart(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return parts;
    }
}
