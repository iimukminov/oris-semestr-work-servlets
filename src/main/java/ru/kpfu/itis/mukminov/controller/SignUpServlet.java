package ru.kpfu.itis.mukminov.controller;

import ru.kpfu.itis.mukminov.service.ClientService;
import ru.kpfu.itis.mukminov.service.EmployeeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/sign-up", loadOnStartup = 1)
public class SignUpServlet extends HttpServlet {

    private ClientService clientService;
    private EmployeeService employeeService;

    @Override
    public void init() throws ServletException {
        this.clientService = (ClientService) getServletContext().getAttribute("clientService");
        this.employeeService = (EmployeeService) getServletContext().getAttribute("employeeService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/sign_up.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String name = req.getParameter("name");
        String lastname = req.getParameter("lastname");
        String phoneNumber = req.getParameter("phone_number");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String passwordConfirm = req.getParameter("password_confirm");

        if (name == null || name.trim().isEmpty() ||
                lastname == null || lastname.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {

            req.setAttribute("error", "Все обязательные поля должны быть заполнены");
            req.getRequestDispatcher("/WEB-INF/views/sign_up.ftl").forward(req, resp);
            return;
        }

        if (!password.equals(passwordConfirm)) {
            req.setAttribute("error", "Пароли не совпадают");
            req.getRequestDispatcher("/WEB-INF/views/sign_up.ftl").forward(req, resp);
            return;
        }


        if (employeeService.isEmailTaken(email) || clientService.isEmailTaken(email)) {
            req.setAttribute("error", "Этот email уже используется");
            req.getRequestDispatcher("/WEB-INF/views/sign_up.ftl").forward(req, resp);
            return;
        }

        try {
            clientService.registerClient(name, lastname, phoneNumber, email, password);
            resp.sendRedirect(req.getContextPath() + "/login");
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/sign_up.ftl").forward(req, resp);
        }
    }
}
