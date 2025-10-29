package ru.kpfu.itis.mukminov.controller.admin;

import ru.kpfu.itis.mukminov.enums.Role;
import ru.kpfu.itis.mukminov.service.ClientService;
import ru.kpfu.itis.mukminov.service.EmployeeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/users")
public class AdminUsersServlet extends HttpServlet {
    private ClientService clientService;
    private EmployeeService employeeService;

    @Override
    public void init() {
        this.clientService = (ClientService) getServletContext().getAttribute("clientService");
        this.employeeService = (EmployeeService) getServletContext().getAttribute("employeeService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("clients", clientService.findAll());
        req.setAttribute("employees", employeeService.findAll().stream().filter(e -> e.getRole() == Role.STAFF).toList());
        req.setAttribute("user", req.getSession(false) != null ? req.getSession(false).getAttribute("employee") : null);
        req.getRequestDispatcher("/WEB-INF/views/admin/users.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter("action");
        String userType = req.getParameter("userType");
        try {
            if ("add".equals(action)) {
                if ("employee".equals(userType)) {
                    employeeService.registerEmployee(
                            req.getParameter("name"),
                            req.getParameter("lastname"),
                            req.getParameter("email"),
                            req.getParameter("password"),
                            Role.valueOf(req.getParameter("role")),
                            req.getParameter("position")
                    );
                } else if ("client".equals(userType)) {
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
                if ("employee".equals(userType)) {
                    employeeService.updateEmployee(
                            id,
                            req.getParameter("name"),
                            req.getParameter("lastname"),
                            req.getParameter("email"),
                            Role.valueOf(req.getParameter("role")),
                            req.getParameter("position"),
                            req.getParameter("password")
                    );
                } else if ("client".equals(userType)) {
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
                if ("employee".equals(userType)) {
                    employeeService.deleteEmployee(id);
                } else if ("client".equals(userType)) {
                    clientService.deleteClient(id);
                }
            }
        } catch (Exception ex) {
            req.setAttribute("error", "Ошибка: " + ex.getMessage());
            doGet(req, resp);
            return;
        }
        resp.sendRedirect("/admin/users");
    }
}

