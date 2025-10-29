package ru.kpfu.itis.mukminov.listener;

import javax.servlet.ServletContextListener;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.kpfu.itis.mukminov.dao.ClientDao;
import ru.kpfu.itis.mukminov.dao.EmployeeDao;
import ru.kpfu.itis.mukminov.dao.EquipmentDao;
import ru.kpfu.itis.mukminov.dao.OrderDao;
import ru.kpfu.itis.mukminov.dao.PartDao;
import ru.kpfu.itis.mukminov.dao.ServiceDao;
import ru.kpfu.itis.mukminov.dao.impl.ClientDaoImpl;
import ru.kpfu.itis.mukminov.dao.impl.EmployeeDaoImpl;
import ru.kpfu.itis.mukminov.dao.impl.EquipmentDaoImpl;
import ru.kpfu.itis.mukminov.dao.impl.OrderDaoImpl;
import ru.kpfu.itis.mukminov.dao.impl.PartDaoImpl;
import ru.kpfu.itis.mukminov.dao.impl.ServiceDaoImpl;
import ru.kpfu.itis.mukminov.service.ClientService;
import ru.kpfu.itis.mukminov.service.EmployeeService;
import ru.kpfu.itis.mukminov.service.EquipmentService;
import ru.kpfu.itis.mukminov.service.OrderService;
import ru.kpfu.itis.mukminov.service.PartService;
import ru.kpfu.itis.mukminov.service.ServiceService;
import ru.kpfu.itis.mukminov.service.impl.ClientServiceImpl;
import ru.kpfu.itis.mukminov.service.impl.EmployeeServiceImpl;
import ru.kpfu.itis.mukminov.service.impl.EquipmentServiceImpl;
import ru.kpfu.itis.mukminov.service.impl.OrderServiceImpl;
import ru.kpfu.itis.mukminov.service.impl.PartServiceImpl;
import ru.kpfu.itis.mukminov.service.impl.ServiceServiceImpl;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    private static final String DATASOURCE_ATTR = "dataSource";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            HikariConfig config = new HikariConfig("/db.properties");
            HikariDataSource dataSource = new HikariDataSource(config);

            ClientDao clientDao = new ClientDaoImpl(dataSource);
            EmployeeDao employeeDao = new EmployeeDaoImpl(dataSource);
            EquipmentDao equipmentDao = new EquipmentDaoImpl(dataSource);
            OrderDao orderDao = new OrderDaoImpl(dataSource);
            PartDao partDao = new PartDaoImpl(dataSource);
            ServiceDao serviceDao = new ServiceDaoImpl(dataSource);


            ClientService clientService = new ClientServiceImpl(clientDao);
            EmployeeService employeeService = new EmployeeServiceImpl(employeeDao);
            EquipmentService equipmentService = new EquipmentServiceImpl(equipmentDao);
            OrderService orderService = new OrderServiceImpl(orderDao, employeeDao, clientDao, equipmentDao);
            PartService partService = new PartServiceImpl(partDao);
            ServiceService serviceService = new ServiceServiceImpl(serviceDao);


            sce.getServletContext().setAttribute(DATASOURCE_ATTR, dataSource);

            sce.getServletContext().setAttribute("clientService", clientService);
            sce.getServletContext().setAttribute("employeeService", employeeService);
            sce.getServletContext().setAttribute("equipmentService", equipmentService);
            sce.getServletContext().setAttribute("orderService", orderService);
            sce.getServletContext().setAttribute("partService", partService);
            sce.getServletContext().setAttribute("serviceService", serviceService);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        HikariDataSource dataSource = (HikariDataSource) sce.getServletContext().getAttribute(DATASOURCE_ATTR);

        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
