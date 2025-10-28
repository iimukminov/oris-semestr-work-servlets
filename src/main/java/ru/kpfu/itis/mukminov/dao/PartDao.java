package ru.kpfu.itis.mukminov.dao;

import ru.kpfu.itis.mukminov.entity.Part;
import java.util.List;
import java.util.Optional;

public interface PartDao {
    void save(Part part);
    void update(Part part);
    void delete(Long id);

    Optional<Part> findById(Long id);
    List<Part> findAll();

}
