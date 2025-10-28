package ru.kpfu.itis.mukminov.service.impl;

import ru.kpfu.itis.mukminov.dao.ServiceDao;
import ru.kpfu.itis.mukminov.entity.Service;
import ru.kpfu.itis.mukminov.service.ServiceService;

import java.util.List;
import java.util.Optional;

public class ServiceServiceImpl implements ServiceService {
    private final ServiceDao serviceDao;

    public ServiceServiceImpl(ServiceDao serviceDao) {
        this.serviceDao = serviceDao;
    }

    @Override
    public void saveService(Service service) {
        serviceDao.save(service);
    }

    @Override
    public void updateService(Service service) {
        serviceDao.update(service);
    }

    @Override
    public void deleteService(Integer id) {
        serviceDao.delete(id);
    }

    @Override
    public Optional<Service> findById(Integer id) {
        return serviceDao.findById(id);
    }

    @Override
    public List<Service> findAll() {
        return serviceDao.findAll();
    }
}
