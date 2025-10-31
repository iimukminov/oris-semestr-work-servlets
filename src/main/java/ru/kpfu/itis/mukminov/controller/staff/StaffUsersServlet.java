package ru.kpfu.itis.mukminov.controller.staff;

import ru.kpfu.itis.mukminov.enums.Role;
import ru.kpfu.itis.mukminov.service.ClientService;
import ru.kpfu.itis.mukminov.service.EmployeeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/staff/users")
public class StaffUsersServlet extends HttpServlet {
    private ClientService clientService;

    @Override
    public void init() {
        this.clientService = (ClientService) getServletContext().getAttribute("clientService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("clients", clientService.findAll());
        req.setAttribute("user", req.getSession(false) != null ? req.getSession(false).getAttribute("employee") : null);
        req.getRequestDispatcher("/WEB-INF/views/staff/users.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter("action");
        String userType = req.getParameter("userType");
        try {
            if ("add".equals(action)) {
                if ("client".equals(userType)) {
                    clientService.registerClient(
                            req.getParameter("name"),
                            req.getParameter("lastname"),
                            req.getParameter("phoneNumber"),
                            req.getParameter("email"),
                            req.getParameter("password")
                    );
                }
            } else if ("edit".equals(action)) {
                Long id = Long.valueOf(req.getParameter("id"));
                if ("client".equals(userType)) {
                    clientService.updateClient(
                            id,
                            req.getParameter("name"),
                            req.getParameter("lastname"),
                            req.getParameter("phoneNumber"),
                            req.getParameter("email"),
                            req.getParameter("password")
                    );
                }
            } else if ("delete".equals(action)) {
                Long id = Long.valueOf(req.getParameter("id"));
                if ("client".equals(userType)) {
                    clientService.deleteClient(id);
                }
            }
        } catch (Exception ex) {
            req.setAttribute("error", "Ошибка: " + ex.getMessage());
            doGet(req, resp);
            return;
        }
        resp.sendRedirect("/staff/users");
    }
}

