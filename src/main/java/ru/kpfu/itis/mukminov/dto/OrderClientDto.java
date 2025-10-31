package ru.kpfu.itis.mukminov.dto;

import ru.kpfu.itis.mukminov.entity.Employee;
import ru.kpfu.itis.mukminov.entity.Equipment;
import ru.kpfu.itis.mukminov.entity.Part;
import ru.kpfu.itis.mukminov.entity.Service;
import ru.kpfu.itis.mukminov.enums.Status;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class OrderClientDto implements Comparable<OrderClientDto> {
    private Long id;
    private Equipment equipment;
    private Status status;
    private String description;
    private Timestamp createdAt;
    private Timestamp completedAt;
    private BigDecimal totalCost;
    private List<PartQuantityDto> parts;
    private List<Service> services;

    @Override
    public int compareTo(OrderClientDto other) {
        int statusCompare = Integer.compare(this.getStatus().getPriority(), other.getStatus().getPriority());
        if (statusCompare != 0) {
            return statusCompare;
        }
        return other.getCreatedAt().compareTo(this.getCreatedAt());
    }

    public OrderClientDto() {
    }

    public OrderClientDto(Long id, Equipment equipment, Status status, String description, Timestamp createdAt, Timestamp completedAt, BigDecimal totalCost , List<PartQuantityDto> parts, List<Service> services) {
        this.id = id;
        this.equipment = equipment;
        this.status = status;
        this.description = description;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
        this.totalCost = totalCost;
        this.parts = parts;
        this.services = services;
    }

    public Long getId() {
        return id;
    }

    public Equipment getEquipment() {
        return equipment;
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

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public List<PartQuantityDto> getParts() {
        return parts;
    }

    public List<Service> getServices() {
        return services;
    }
}
