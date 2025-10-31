package ru.kpfu.itis.mukminov.controller;

import ru.kpfu.itis.mukminov.dto.ClientDto;
import ru.kpfu.itis.mukminov.dto.EmployeeDto;
import ru.kpfu.itis.mukminov.service.ClientService;
import ru.kpfu.itis.mukminov.service.EmployeeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private ClientService clientService;
    private EmployeeService employeeService;

    @Override
    public void init() {
        clientService = (ClientService) getServletContext().getAttribute("clientService");
        employeeService = (EmployeeService) getServletContext().getAttribute("employeeService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendRedirect("/login");
            return;
        }
        String userType = (String) session.getAttribute("userType");
        Object user = null;
        if ("client".equals(userType)) {
            user = session.getAttribute("client");
        } else if ("employee".equals(userType)) {
            user = session.getAttribute("employee");
        }
        if (user == null) {
            resp.sendRedirect("/login");
            return;
        }
        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/views/profile.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendRedirect("/login");
            return;
        }
        String userType = (String) session.getAttribute("userType");
        Object user = null;
        if ("client".equals(userType)) {
            user = session.getAttribute("client");
        } else if ("employee".equals(userType)) {
            user = session.getAttribute("employee");
        }
        if (user == null) {
            resp.sendRedirect("/login");
            return;
        }

        String newPassword = req.getParameter("password");

        try {
            if ("client".equals(userType)) {
                ClientDto client = (ClientDto) user;

                clientService.updateClient(
                        client.getId(),
                        req.getParameter("name"),
                        req.getParameter("lastname"),
                        req.getParameter("phoneNumber"),
                        req.getParameter("email"),
                        newPassword != null && !newPassword.isEmpty() ? newPassword : null
                );

                ClientDto updatedClient = clientService.findById(client.getId()).orElse(client);
                session.setAttribute("client", updatedClient);

            } else if ("employee".equals(userType)) {
                EmployeeDto employee = (EmployeeDto) user;

                employeeService.updateEmployee(
                        employee.getId(),
                        req.getParameter("name"),
                        req.getParameter("lastname"),
                        req.getParameter("email"),
                        employee.getRole(),
                        employee.getPosition(),
                        newPassword != null && !newPassword.isEmpty() ? newPassword : null
                );

                EmployeeDto updatedEmployee = employeeService.findById(employee.getId()).orElse(employee);
                session.setAttribute("employee", updatedEmployee);
            }
        } catch (Exception e) {
            req.setAttribute("error", "Ошибка при обновлении профиля: " + e.getMessage());
            doGet(req, resp);
            return;
        }

        resp.sendRedirect("/profile");
    }

}
