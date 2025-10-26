package ru.kpfu.itis.mukminov.listener;

import javax.servlet.ServletContextListener;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.kpfu.itis.mukminov.dao.ClientDao;
import ru.kpfu.itis.mukminov.dao.EmployeeDao;
import ru.kpfu.itis.mukminov.dao.impl.ClientDaoImpl;
import ru.kpfu.itis.mukminov.dao.impl.EmployeeDaoImpl;
import ru.kpfu.itis.mukminov.service.ClientService;
import ru.kpfu.itis.mukminov.service.EmployeeService;
import ru.kpfu.itis.mukminov.service.impl.ClientServiceImpl;
import ru.kpfu.itis.mukminov.service.impl.EmployeeServiceImpl;

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

            ClientService clientService = new ClientServiceImpl(clientDao);
            EmployeeService employeeService = new EmployeeServiceImpl(employeeDao);

            sce.getServletContext().setAttribute(DATASOURCE_ATTR, dataSource);

            sce.getServletContext().setAttribute("clientService", clientService);
            sce.getServletContext().setAttribute("employeeService", employeeService);


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
