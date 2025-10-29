package ru.kpfu.itis.mukminov.service.impl;

import ru.kpfu.itis.mukminov.dao.PartDao;
import ru.kpfu.itis.mukminov.entity.Part;
import ru.kpfu.itis.mukminov.service.PartService;

import java.util.List;
import java.util.Optional;

public class PartServiceImpl implements PartService {
    private final PartDao partDao;

    public PartServiceImpl(PartDao partDao) {
        this.partDao = partDao;
    }

    @Override
    public void savePart(Part part) {
        partDao.save(part);
    }

    @Override
    public void updatePart(Part part) {
        partDao.update(part);
    }

    @Override
    public void deletePart(Long id) {
        partDao.delete(id);
    }

    @Override
    public Optional<Part> findById(Long id) {
        return partDao.findById(id);
    }

    @Override
    public List<Part> findAll() {
        return partDao.findAll();
    }

    @Override
    public void adjustStockQuantity(Long partId, int quantityDiff) {
        Part part = findById(partId).orElseThrow(() -> new RuntimeException("Запчасть не найдена: " + partId));
        int newQuantity = part.getQuantity() + quantityDiff;
        if (newQuantity < 0) {
            newQuantity = 0;
        }
        part.setQuantity(newQuantity);
        updatePart(part);
    }
}
