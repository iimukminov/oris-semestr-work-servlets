package ru.kpfu.itis.mukminov.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/dashboard")
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        String userType = (String) httpSession.getAttribute("userType");
        req.setAttribute("user", userType.equals("employee") ? req.getSession(false).getAttribute("employee") : req.getSession(false).getAttribute("client"));
        req.getRequestDispatcher("/WEB-INF/views/dashboard.ftl").forward(req, resp);
    }
}
