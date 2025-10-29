package ru.kpfu.itis.mukminov.dao;

import ru.kpfu.itis.mukminov.dto.OrderClientDto;
import ru.kpfu.itis.mukminov.dto.OrderDto;
import ru.kpfu.itis.mukminov.dto.PartQuantityDto;
import ru.kpfu.itis.mukminov.entity.Order;
import ru.kpfu.itis.mukminov.entity.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderDao {
    void save(Order order);
    void update(Order order);
    void delete(Long id);

    Optional<Order> findById(Long id);
    List<Order> findAll();
    List<Order> findByClientId(Long clientId);

    // m2m для услуг
    void addServiceToOrder(Long orderId, Integer serviceId);
    void removeServiceFromOrder(Long orderId, Integer serviceId);
    void removeAllServicesFromOrder(Long orderId);
    List<Service> getServicesByOrder(Long orderId);

    // m2m для запчастей
    void addPartToOrder(Long orderId, Long partId, int quantity);
    void removePartFromOrder(Long orderId, Long partId);
    void removeAllPartsFromOrder(Long orderId);
    List<PartQuantityDto> getPartsByOrder(Long orderId);
    void updatePartQuantityInOrder(Long orderId, Long partId, int newQuantity);

    BigDecimal calculateTotalCost(Long orderId);
}
