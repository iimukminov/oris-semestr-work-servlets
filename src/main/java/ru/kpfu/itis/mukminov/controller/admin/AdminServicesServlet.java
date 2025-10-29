package ru.kpfu.itis.mukminov.controller.admin;

import ru.kpfu.itis.mukminov.entity.Service;
import ru.kpfu.itis.mukminov.service.ServiceService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/admin/services")
public class AdminServicesServlet extends HttpServlet {
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
        req.getRequestDispatcher("/WEB-INF/views/admin/services.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter("action");
        try {
            if ("add".equals(action)) {
                Service service = new Service();

                service.setName(req.getParameter("name"));
                service.setDescription(req.getParameter("description"));
                service.setPrice(new BigDecimal(req.getParameter("price")));

                serviceService.saveService(service);
            } else if ("edit".equals(action)) {
                Integer id = Integer.valueOf(req.getParameter("id"));

                Service service = serviceService.findById(id).orElseThrow();

                service.setName(req.getParameter("name"));
                service.setDescription(req.getParameter("description"));
                service.setPrice(new BigDecimal(req.getParameter("price")));

                serviceService.updateService(service);
            } else if ("delete".equals(action)) {
                Integer id = Integer.valueOf(req.getParameter("id"));

                serviceService.deleteService(id);
            }
        } catch (Exception ex) {
            req.setAttribute("error", "Ошибка: " + ex.getMessage());
            doGet(req, resp);
            return;
        }
        resp.sendRedirect("/admin/services");
    }
}

