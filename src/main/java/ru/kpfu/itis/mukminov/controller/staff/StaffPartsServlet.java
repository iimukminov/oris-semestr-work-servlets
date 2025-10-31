package ru.kpfu.itis.mukminov.controller.staff;

import ru.kpfu.itis.mukminov.entity.Part;
import ru.kpfu.itis.mukminov.service.PartService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/staff/parts")
public class StaffPartsServlet extends HttpServlet {
    private PartService partService;

    @Override
    public void init() {
        this.partService = (PartService) getServletContext().getAttribute("partService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Part> parts = partService.findAll();
        req.setAttribute("parts", parts);
        req.setAttribute("user", req.getSession(false) != null ? req.getSession(false).getAttribute("employee") : null);
        req.getRequestDispatcher("/WEB-INF/views/staff/parts.ftl").forward(req, resp);
    }
}
