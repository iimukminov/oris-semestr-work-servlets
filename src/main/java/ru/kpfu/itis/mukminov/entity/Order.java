package ru.kpfu.itis.mukminov.entity;

import ru.kpfu.itis.mukminov.enums.Status;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Order {
    private Long id;
    private Long equipmentId;
    private Long technicianId;
    private Status status;
    private String description;
    private Timestamp createdAt;
    private Timestamp completedAt;
    private BigDecimal price;

    public Order() {
    }

    public Order(Long id, Long equipmentId, Long technicianId, Status status, String description, Timestamp createdAt, Timestamp completedAt) {
        this.id = id;
        this.equipmentId = equipmentId;
        this.technicianId = technicianId;
        this.status = status;
        this.description = description;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
    }

    public Order(Long id, Long equipmentId, Long technicianId, Status status, String description, Timestamp createdAt, Timestamp completedAt, BigDecimal price) {
        this.id = id;
        this.equipmentId = equipmentId;
        this.technicianId = technicianId;
        this.status = status;
        this.description = description;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
        this.price = price;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getEquipmentId() {
        return equipmentId;
    }
    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Long getTechnicianId() {
        return technicianId;
    }
    public void setTechnicianId(Long technicianId) {
        this.technicianId = technicianId;
    }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getCompletedAt() {
        return completedAt;
    }
    public void setCompletedAt(Timestamp completedAt) {
        this.completedAt = completedAt;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
