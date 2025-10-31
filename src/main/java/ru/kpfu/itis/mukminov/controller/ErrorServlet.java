package ru.kpfu.itis.mukminov.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/error")
public class ErrorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer statusCode = (Integer) req.getAttribute("javax.servlet.error.status_code");
        String errorMessage = (String) req.getAttribute("javax.servlet.error.message");
        String requestUri = (String) req.getAttribute("javax.servlet.error.request_uri");
        Throwable throwable = (Throwable) req.getAttribute("javax.servlet.error.exception");

        req.setAttribute("statusCode", statusCode);
        req.setAttribute("errorMessage", errorMessage);
        req.setAttribute("requestUri", requestUri);
        req.setAttribute("throwable", throwable);

        String view;
        if (statusCode != null && statusCode == 404) {
            view = "/WEB-INF/views/404.ftl";
        } else {
            view = "/WEB-INF/views/error.ftl";
        }
        req.getRequestDispatcher(view).forward(req, resp);
    }
}
