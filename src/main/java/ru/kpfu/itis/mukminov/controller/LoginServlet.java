package ru.kpfu.itis.mukminov.controller;

import ru.kpfu.itis.mukminov.dto.ClientDto;
import ru.kpfu.itis.mukminov.dto.EmployeeDto;
import ru.kpfu.itis.mukminov.service.ClientService;
import ru.kpfu.itis.mukminov.service.EmployeeService;
import ru.kpfu.itis.mukminov.util.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private ClientService clientService;
    private EmployeeService employeeService;

    @Override
    public void init() throws ServletException {
        clientService = (ClientService) getServletContext().getAttribute("clientService");
        employeeService = (EmployeeService) getServletContext().getAttribute("employeeService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/ftl/login.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            req.setAttribute("error", "Введите email и пароль");
            req.getRequestDispatcher("/WEB-INF/ftl/login.ftl").forward(req, resp);
            return;
        }


        if (employeeService.authenticate(email, password)) {
            Optional<EmployeeDto> employeeOpt = employeeService.findByEmail(email);
            if (employeeOpt.isPresent()) {
                HttpSession session = req.getSession();
                session.setAttribute("employee", employeeOpt.get());
                session.setAttribute("userType", "employee");
                session.setMaxInactiveInterval(30 * 60);
                resp.sendRedirect(req.getContextPath() + "/admin");
                return;
            }
        }


        if (clientService.authenticate(email, password)) {
            Optional<ClientDto> clientOpt = clientService.findByEmail(email);
            if (clientOpt.isPresent()) {
                HttpSession session = req.getSession();
                session.setAttribute("client", clientOpt.get());
                session.setAttribute("userType", "client");
                session.setMaxInactiveInterval(30 * 60);
                resp.sendRedirect(req.getContextPath() + "/home");
                return;
            }
        }


        req.setAttribute("error", "Неверный email или пароль");
        req.getRequestDispatcher("/WEB-INF/ftl/login.ftl").forward(req, resp);
    }
}
