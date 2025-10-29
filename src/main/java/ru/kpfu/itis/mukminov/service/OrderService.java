package ru.kpfu.itis.mukminov.service;

import ru.kpfu.itis.mukminov.dto.OrderClientDto;
import ru.kpfu.itis.mukminov.dto.OrderDto;
import ru.kpfu.itis.mukminov.dto.PartQuantityDto;
import ru.kpfu.itis.mukminov.entity.Order;
import ru.kpfu.itis.mukminov.entity.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    void saveOrder(Order order);
    void updateOrder(Order order);
    void deleteOrder(Long id);

    Optional<Order> findById(Long id);
    List<Order> findAll();
    List<Order> findByClientId(Long clientId);

    List<OrderDto> findAllOrderDto();
    List<OrderClientDto> findAllOrderDtoByClientId(Long clientId);

    void addServiceToOrder(Long orderId, Integer serviceId);
    void removeServiceFromOrder(Long orderId, Integer serviceId);
    void removeAllServicesFromOrder(Long orderId);

    void addPartToOrder(Long orderId, Long partId, int quantity);
    void removePartFromOrder(Long orderId, Long partId);
    void removeAllPartsFromOrder(Long orderId);
    void updatePartQuantityInOrder(Long orderId, Long partId, int newQuantity);
    
    List<Service> getServicesByOrder(Long orderId);
    List<PartQuantityDto> getPartsByOrder(Long orderId);

    BigDecimal calculateTotalCost(Long orderId);

}
