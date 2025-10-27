package ru.kpfu.itis.mukminov.dao;

import ru.kpfu.itis.mukminov.entity.Service;
import java.util.List;
import java.util.Optional;

public interface ServiceDao {
    void save(Service service);
    void update(Service service);
    void delete(Integer id);

    Optional<Service> findById(Integer id);
    List<Service> findAll();
}
