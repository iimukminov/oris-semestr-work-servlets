package ru.kpfu.itis.mukminov.service;

import ru.kpfu.itis.mukminov.entity.Order;
import ru.kpfu.itis.mukminov.entity.Part;
import ru.kpfu.itis.mukminov.entity.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderService {
    void saveOrder(Order order);
    void updateOrder(Order order);
    void deleteOrder(Long id);

    Optional<Order> findById(Long id);
    List<Order> findAll();
    List<Order> findByClientId(Long clientId);

    void addServiceToOrder(Long orderId, Integer serviceId);
    void removeServiceFromOrder(Long orderId, Integer serviceId);

    void addPartToOrder(Long orderId, Long partId, int quantity);
    void removePartFromOrder(Long orderId, Long partId);
    void updatePartQuantityInOrder(Long orderId, Long partId, int newQuantity);
    
    List<Service> getServicesByOrder(Long orderId);
    Map<Part, Integer> getPartsByOrder(Long orderId);
    BigDecimal calculateTotalCost(Long orderId);
}
