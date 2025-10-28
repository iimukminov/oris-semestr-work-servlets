package ru.kpfu.itis.mukminov.service.impl;

import ru.kpfu.itis.mukminov.dao.OrderDao;
import ru.kpfu.itis.mukminov.entity.Order;
import ru.kpfu.itis.mukminov.entity.Part;
import ru.kpfu.itis.mukminov.entity.Service;
import ru.kpfu.itis.mukminov.service.OrderService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;

    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
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
    public void addServiceToOrder(Long orderId, Integer serviceId) {
        orderDao.addServiceToOrder(orderId, serviceId);
    }

    @Override
    public void removeServiceFromOrder(Long orderId, Integer serviceId) {
        orderDao.removeServiceFromOrder(orderId, serviceId);
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
    public void updatePartQuantityInOrder(Long orderId, Long partId, int newQuantity) {
        orderDao.updatePartQuantityInOrder(orderId, partId, newQuantity);
    }

    @Override
    public List<Service> getServicesByOrder(Long orderId) {
        return orderDao.getServicesByOrder(orderId);
    }

    @Override
    public Map<Part, Integer> getPartsByOrder(Long orderId) {
        return orderDao.getPartsByOrder(orderId);
    }

    @Override
    public BigDecimal calculateTotalCost(Long orderId) {
        return orderDao.calculateTotalCost(orderId);
    }
}
