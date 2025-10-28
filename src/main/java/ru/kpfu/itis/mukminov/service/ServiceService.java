package ru.kpfu.itis.mukminov.service;

import ru.kpfu.itis.mukminov.entity.Service;

import java.util.List;
import java.util.Optional;

public interface ServiceService {
    void saveService(Service service);
    void updateService(Service service);
    void deleteService(Integer id);

    Optional<Service> findById(Integer id);
    List<Service> findAll();
}
