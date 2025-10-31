package ru.kpfu.itis.mukminov.service.impl;

import ru.kpfu.itis.mukminov.dao.ClientDao;
import ru.kpfu.itis.mukminov.dao.EmployeeDao;
import ru.kpfu.itis.mukminov.dao.EquipmentDao;
import ru.kpfu.itis.mukminov.dao.OrderDao;
import ru.kpfu.itis.mukminov.dto.ClientDto;
import ru.kpfu.itis.mukminov.dto.EmployeeDto;
import ru.kpfu.itis.mukminov.dto.OrderClientDto;
import ru.kpfu.itis.mukminov.dto.OrderDto;
import ru.kpfu.itis.mukminov.dto.PartQuantityDto;
import ru.kpfu.itis.mukminov.entity.Client;
import ru.kpfu.itis.mukminov.entity.Employee;
import ru.kpfu.itis.mukminov.entity.Equipment;
import ru.kpfu.itis.mukminov.entity.Order;
import ru.kpfu.itis.mukminov.entity.Service;
import ru.kpfu.itis.mukminov.service.OrderService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final EmployeeDao employeeDao;
    private final ClientDao clientDao;
    private final EquipmentDao equipmentDao;

    public OrderServiceImpl(OrderDao orderDao,  EmployeeDao employeeDao, ClientDao clientDao, EquipmentDao equipmentDao) {
        this.orderDao = orderDao;
        this.employeeDao = employeeDao;
        this.clientDao = clientDao;
        this.equipmentDao = equipmentDao;
    }

    @Override
    public void saveOrder(Order order) {
        orderDao.save(order);
    }

    @Override
    public void updateOrder(Order order) {
        orderDao.update(order);
    }

    @Override
    public void deleteOrder(Long id) {
        orderDao.delete(id);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderDao.findById(id);
    }

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    public List<Order> findByClientId(Long clientId) {
        return orderDao.findByClientId(clientId);
    }

    @Override
    public List<OrderDto> findAllOrderDto() {
        List<Order> orders = orderDao.findAll();
        List<EmployeeDto> employees = employeeDao.findAll().stream().map(this::convertEmployeeToDto).toList();
        List<ClientDto> clients = clientDao.findAll().stream().map(this::convertClientToDto).toList();

        List<OrderDto> orderDtoList = new ArrayList<>();

        for (Order order : orders) {
            Equipment equipment = equipmentDao.findById(order.getEquipmentId()).orElse(null);
            orderDtoList.add(convertOrderToDto(
                    order,
                    employees.stream().filter(empl -> empl.getId().equals(order.getEmployeeId())).findFirst().orElse(null),
                    clients.stream().filter(cl -> cl.getId().equals(equipment.getClientId())).findFirst().orElse(null),
                    equipment)
            );
        }
        return orderDtoList;
    }

    @Override
    public List<OrderClientDto> findAllOrderDtoByClientId(Long clientId) {
        List<Order> orders = orderDao.findByClientId(clientId);
        Optional<Client> client = clientDao.findById(clientId);
        ClientDto clientDto = client.map(this::convertClientToDto).orElse(null);

        List<OrderClientDto> orderDtoList = new ArrayList<>();

        for (Order order : orders) {
            Equipment equipment = equipmentDao.findById(order.getEquipmentId()).orElse(null);
            orderDtoList.add(convertOrderToClientDto(order, equipment));
        }
        return orderDtoList;
    }

    @Override
    public void addServiceToOrder(Long orderId, Integer serviceId) {
        orderDao.addServiceToOrder(orderId, serviceId);
    }

    @Override
    public void removeServiceFromOrder(Long orderId, Integer serviceId) {
        orderDao.removeServiceFromOrder(orderId, serviceId);
    }

    @Override
    public void removeAllServicesFromOrder(Long orderId) {
        orderDao.removeAllServicesFromOrder(orderId);
    }

    @Override
    public void addPartToOrder(Long orderId, Long partId, int quantity) {
        orderDao.addPartToOrder(orderId, partId, quantity);
    }

    @Override
    public void removePartFromOrder(Long orderId, Long partId) {
        orderDao.removePartFromOrder(orderId, partId);
    }

    @Override
    public void removeAllPartsFromOrder(Long orderId) {
        orderDao.removeAllPartsFromOrder(orderId);
    }

    @Override
    public void updatePartQuantityInOrder(Long orderId, Long partId, int newQuantity) {
        orderDao.updatePartQuantityInOrder(orderId, partId, newQuantity);
    }

    @Override
    public List<Service> getServicesByOrder(Long orderId) {
        return orderDao.getServicesByOrder(orderId);
    }

    @Override
    public List<PartQuantityDto> getPartsByOrder(Long orderId) {
        return orderDao.getPartsByOrder(orderId);
    }

    @Override
    public BigDecimal calculateTotalCost(Long orderId) {
        return orderDao.calculateTotalCost(orderId);
    }

    private EmployeeDto convertEmployeeToDto(Employee employee) {
        return new EmployeeDto(
                employee.getId(),
                employee.getName(),
                employee.getLastname(),
                employee.getEmail(),
                employee.getRole(),
                employee.getPosition()
        );
    }

    private ClientDto convertClientToDto(Client client) {
        return new ClientDto(
                client.getId(),
                client.getName(),
                client.getLastname(),
                client.getPhoneNumber(),
                client.getEmail()
        );
    }

    private OrderDto convertOrderToDto(Order order, EmployeeDto employeeDto,  ClientDto clientDto, Equipment equipment) {
        return new OrderDto(
                order.getId(),
                equipment,
                employeeDto,
                order.getStatus(),
                order.getDescription(),
                order.getCreatedAt(),
                order.getCompletedAt(),
                order.getPrice(),
                clientDto,
                getPartsByOrder(order.getId()),
                getServicesByOrder(order.getId())
        );
    }

    private OrderClientDto convertOrderToClientDto(Order order, Equipment equipment) {
        return new OrderClientDto(
                order.getId(),
                equipment,
                order.getStatus(),
                order.getDescription(),
                order.getCreatedAt(),
                order.getCompletedAt(),
                order.getPrice(),
                getPartsByOrder(order.getId()),
                getServicesByOrder(order.getId())
        );
    }
}
