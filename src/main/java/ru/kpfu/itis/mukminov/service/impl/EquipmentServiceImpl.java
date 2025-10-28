package ru.kpfu.itis.mukminov.service.impl;

import ru.kpfu.itis.mukminov.dao.EquipmentDao;
import ru.kpfu.itis.mukminov.entity.Equipment;
import ru.kpfu.itis.mukminov.service.EquipmentService;

import java.util.List;
import java.util.Optional;

public class EquipmentServiceImpl implements EquipmentService {
    private final EquipmentDao equipmentDao;

    public EquipmentServiceImpl(EquipmentDao equipmentDao) {
        this.equipmentDao = equipmentDao;
    }

    @Override
    public void saveEquipment(Equipment equipment) {
        equipmentDao.save(equipment);
    }

    @Override
    public void updateEquipment(Equipment equipment) {
        equipmentDao.update(equipment);
    }

    @Override
    public void deleteEquipment(Long id) {
        equipmentDao.delete(id);
    }

    @Override
    public Optional<Equipment> findById(Long id) {
        return equipmentDao.findById(id);
    }

    @Override
    public List<Equipment> findByClientId(Long clientId) {
        return equipmentDao.findByClientId(clientId);
    }

    @Override
    public List<Equipment> findAll() {
        return equipmentDao.findAll();
    }
}
