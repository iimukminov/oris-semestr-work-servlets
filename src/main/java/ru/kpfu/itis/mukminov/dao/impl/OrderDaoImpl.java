package ru.kpfu.itis.mukminov.dao.impl;

import ru.kpfu.itis.mukminov.dao.OrderDao;
import ru.kpfu.itis.mukminov.dao.exceptions.DaoException;
import ru.kpfu.itis.mukminov.dto.PartQuantityDto;
import ru.kpfu.itis.mukminov.entity.Client;
import ru.kpfu.itis.mukminov.entity.Employee;
import ru.kpfu.itis.mukminov.entity.Order;
import ru.kpfu.itis.mukminov.entity.Part;
import ru.kpfu.itis.mukminov.entity.Service;
import ru.kpfu.itis.mukminov.enums.Role;
import ru.kpfu.itis.mukminov.enums.Status;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDaoImpl implements OrderDao {
    private final DataSource dataSource;

    public OrderDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Order order) {
        String sql = "INSERT INTO repair_orders (equipment_id, technician_id, status, problem_description) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, order.getEquipmentId());
            if (order.getEmployeeId() != null) {
                preparedStatement.setLong(2, order.getEmployeeId());
            } else {
                preparedStatement.setNull(2, java.sql.Types.INTEGER);
            }
            preparedStatement.setString(3, order.getStatus().name());
            preparedStatement.setString(4, order.getDescription());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    @Override
    public void update(Order order) {
        String sql = "UPDATE repair_orders SET equipment_id = ?, technician_id = ?, status = ?, problem_description = ?, completed_at = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, order.getEquipmentId());
            if (order.getEmployeeId() != null) {
                preparedStatement.setLong(2, order.getEmployeeId());
            } else {
                preparedStatement.setNull(2, java.sql.Types.INTEGER);
            }
            preparedStatement.setString(3, order.getStatus().name());
            preparedStatement.setString(4, order.getDescription());
            if (order.getCompletedAt() != null) {
                preparedStatement.setTimestamp(5, order.getCompletedAt());
            } else {
                preparedStatement.setNull(5, java.sql.Types.TIMESTAMP);
            }
            preparedStatement.setLong(6, order.getId());
            preparedStatement.executeUpdate();

            updateOrderTotalCost(order.getId());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM repair_orders WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Order> findById(Long id) {
        String sql = "SELECT * FROM repair_orders WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapRowToOrder(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Order> findAll() {
        String sql = "SELECT * FROM repair_orders";
        List<Order> orders = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    orders.add(mapRowToOrder(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return orders;
    }

    @Override
    public List<Order> findByClientId(Long clientId) {
        String sql = "SELECT repair_orders.* FROM repair_orders INNER JOIN equipments ON repair_orders.equipment_id = equipments.id WHERE equipments.client_id = ?";
        List<Order> orders = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, clientId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    orders.add(mapRowToOrder(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return orders;
    }

    @Override
    public void addServiceToOrder(Long orderId, Integer serviceId) {
        String sql = "INSERT INTO order_services (order_id, service_id) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, orderId);
            preparedStatement.setLong(2, serviceId);
            preparedStatement.executeUpdate();

            updateOrderTotalCost(orderId);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void removeServiceFromOrder(Long orderId, Integer serviceId) {
        String sql = "DELETE FROM order_services WHERE order_id = ? AND service_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, orderId);
            preparedStatement.setLong(2, serviceId);
            preparedStatement.executeUpdate();

            updateOrderTotalCost(orderId);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void removeAllServicesFromOrder(Long orderId) {
        String sql = "DELETE FROM order_services WHERE order_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, orderId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    @Override
    public List<Service> getServicesByOrder(Long orderId) {
        String sql = "SELECT services.* FROM order_services INNER JOIN services ON order_services.service_id = services.id WHERE order_id = ?";

        List<Service> services = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, orderId);

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


    @Override
    public void addPartToOrder(Long orderId, Long partId, int quantity) {
        String sql = "INSERT INTO order_parts (order_id, part_id, quantity) VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, orderId);
            preparedStatement.setLong(2, partId);
            preparedStatement.setInt(3, quantity);
            preparedStatement.executeUpdate();

            updateOrderTotalCost(orderId);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void removePartFromOrder(Long orderId, Long partId) {
        String sql = "DELETE FROM order_parts WHERE order_id = ? AND part_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, orderId);
            preparedStatement.setLong(2, partId);
            preparedStatement.executeUpdate();

            updateOrderTotalCost(orderId);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void removeAllPartsFromOrder(Long orderId) {
        String sql = "DELETE FROM order_parts WHERE order_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, orderId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<PartQuantityDto> getPartsByOrder(Long orderId) {
        String sql = "SELECT parts.*, order_parts.quantity FROM order_parts INNER JOIN parts ON order_parts.part_id = parts.id WHERE order_id = ?";

        List<PartQuantityDto> parts = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, orderId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    parts.add(new PartQuantityDto(mapRowToPart(resultSet), resultSet.getInt("quantity")));
                }
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return parts;
    }

    @Override
    public void updatePartQuantityInOrder(Long orderId, Long partId, int newQuantity) {
        String sql = "UPDATE order_parts SET quantity = ? WHERE order_id = ? AND part_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, newQuantity);
            ps.setLong(2, orderId);
            ps.setLong(3, partId);
            ps.executeUpdate();

            updateOrderTotalCost(orderId);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public BigDecimal calculateTotalCost(Long orderId) {
        BigDecimal totalCost = getPartsByOrder(orderId).stream()
                .map(part -> part.getPart().getPrice().multiply(BigDecimal.valueOf(part.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .add(getServicesByOrder(orderId).stream()
                        .map(service -> service.getPrice())
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                );
        return totalCost;
    }

    private void updateOrderTotalCost(Long orderId) {
        BigDecimal totalCost = calculateTotalCost(orderId);
        String sql = "UPDATE repair_orders SET total_cost = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBigDecimal(1, totalCost);
            ps.setLong(2, orderId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Client mapRowToClient(ResultSet row) throws SQLException {
        Client client = new Client();
        client.setId(row.getLong("id"));
        client.setName(row.getString("name"));
        client.setLastname(row.getString("lastname"));
        client.setPhoneNumber(row.getString("phone_number"));
        client.setEmail(row.getString("email"));
        return client;
    }

    private Employee mapRowToEmployee(ResultSet row) throws SQLException {
        Employee employee = new Employee();
        employee.setId(row.getLong("id"));
        employee.setName(row.getString("name"));
        employee.setLastname(row.getString("lastname"));
        employee.setEmail(row.getString("email"));
        employee.setRole(Role.valueOf(row.getString("role")));
        employee.setPosition(row.getString("position"));
        return employee;
    }

    private Order mapRowToOrder(ResultSet row) throws SQLException {
        Order order = new Order();
        order.setId(row.getLong("id"));
        order.setEquipmentId(row.getLong("equipment_id"));
        order.setEmployeeId(row.getLong("technician_id"));
        order.setStatus(Status.valueOf(row.getString("status")));
        order.setDescription(row.getString("problem_description"));
        order.setCreatedAt(row.getTimestamp("created_at"));
        order.setCompletedAt(row.getTimestamp("completed_at"));
        order.setPrice(row.getBigDecimal("total_cost"));
        return order;
    }

    private Service mapRowToService(ResultSet row) throws SQLException {
        Service service = new Service();
        service.setId(row.getInt("id"));
        service.setName(row.getString("name"));
        service.setPrice(row.getBigDecimal("price"));
        service.setDescription(row.getString("description"));
        return service;
    }

    private Part mapRowToPart(ResultSet row) throws SQLException {
        Part part = new Part();
        part.setId(row.getLong("id"));
        part.setName(row.getString("name"));
        part.setQuantity(row.getInt("quantity_in_stock"));
        part.setPrice(row.getBigDecimal("price"));
        return part;
    }
}
