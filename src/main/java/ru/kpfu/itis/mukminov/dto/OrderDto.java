package ru.kpfu.itis.mukminov.dto;

import ru.kpfu.itis.mukminov.entity.Employee;
import ru.kpfu.itis.mukminov.entity.Equipment;
import ru.kpfu.itis.mukminov.entity.Part;
import ru.kpfu.itis.mukminov.entity.Service;
import ru.kpfu.itis.mukminov.enums.Status;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class OrderDto implements Comparable<OrderDto> {
    private Long id;
    private Equipment equipment;
    private EmployeeDto technician;
    private Status status;
    private String description;
    private Timestamp createdAt;
    private Timestamp completedAt;
    private BigDecimal price;
    private ClientDto client;
    private List<PartQuantityDto> parts;
    private List<Service> services;

    @Override
    public int compareTo(OrderDto other) {
        int statusCompare = Integer.compare(this.getStatus().getPriority(), other.getStatus().getPriority());
        if (statusCompare != 0) {
            return statusCompare;
        }
        return other.getCreatedAt().compareTo(this.getCreatedAt());
    }

    public OrderDto() {
    }

    public OrderDto(Long id, Equipment equipment, EmployeeDto technician, Status status, String description, Timestamp createdAt, Timestamp completedAt, BigDecimal price, ClientDto client, List<PartQuantityDto> parts, List<Service> services) {
        this.id = id;
        this.equipment = equipment;
        this.technician = technician;
        this.status = status;
        this.description = description;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
        this.price = price;
        this.client = client;
        this.parts = parts;
        this.services = services;
    }

    public Long getId() {
        return id;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public EmployeeDto getTechnician() {
        return technician;
    }

    public Status getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getCompletedAt() {
        return completedAt;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ClientDto getClient() {
        return client;
    }

    public List<PartQuantityDto> getParts() {
        return parts;
    }

    public List<Service> getServices() {
        return services;
    }
}
