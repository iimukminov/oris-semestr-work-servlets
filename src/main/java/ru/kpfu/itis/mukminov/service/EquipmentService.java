package ru.kpfu.itis.mukminov.service;

import ru.kpfu.itis.mukminov.entity.Equipment;

import java.util.List;
import java.util.Optional;

public interface EquipmentService {
    void saveEquipment(Equipment equipment);
    void updateEquipment(Equipment equipment);
    void deleteEquipment(Long id);
    Optional<Equipment> findById(Long id);
    List<Equipment> findByClientId(Long clientId);
    List<Equipment> findAll();
}


