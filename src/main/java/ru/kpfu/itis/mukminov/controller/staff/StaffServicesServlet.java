package ru.kpfu.itis.mukminov.controller.staff;

import ru.kpfu.itis.mukminov.entity.Service;
import ru.kpfu.itis.mukminov.service.ServiceService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/staff/services")
public class StaffServicesServlet extends HttpServlet {
    private ServiceService serviceService;

    @Override
    public void init() {
        this.serviceService = (ServiceService) getServletContext().getAttribute("serviceService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Service> services = serviceService.findAll();
        req.setAttribute("services", services);
        req.setAttribute("user", req.getSession(false) != null ? req.getSession(false).getAttribute("employee") : null);
        req.getRequestDispatcher("/WEB-INF/views/staff/services.ftl").forward(req, resp);
    }
}

