package ru.kpfu.itis.mukminov.dao;

import ru.kpfu.itis.mukminov.entity.Equipment;
import java.util.List;
import java.util.Optional;

public interface EquipmentDao {
    void save(Equipment equipment);
    void update(Equipment equipment);
    void delete(Long id);

    Optional<Equipment> findById(Long id);
    List<Equipment> findByClientId(Long clientId);
    List<Equipment> findAll();
}
