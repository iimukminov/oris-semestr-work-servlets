package ru.kpfu.itis.mukminov.service;

import ru.kpfu.itis.mukminov.entity.Part;

import java.util.List;
import java.util.Optional;

public interface PartService {
    void savePart(Part part);
    void updatePart(Part part);
    void deletePart(Long id);

    Optional<Part> findById(Long id);
    List<Part> findAll();
}