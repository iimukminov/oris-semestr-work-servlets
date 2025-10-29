package ru.kpfu.itis.mukminov.controller.admin;

import ru.kpfu.itis.mukminov.entity.Part;
import ru.kpfu.itis.mukminov.service.PartService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/admin/parts")
public class AdminPartsServlet extends HttpServlet {
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
        req.getRequestDispatcher("/WEB-INF/views/admin/parts.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter("action");
        try {
            if ("add".equals(action)) {
                Part part = new Part();

                part.setName(req.getParameter("name"));
                part.setQuantity(Integer.parseInt(req.getParameter("quantityInStock")));
                part.setPrice(new BigDecimal(req.getParameter("price")));

                partService.savePart(part);
            } else if ("edit".equals(action)) {
                Long id = Long.valueOf(req.getParameter("id"));

                Part part = partService.findById(id).orElseThrow();

                part.setName(req.getParameter("name"));
                part.setQuantity(Integer.parseInt(req.getParameter("quantityInStock")));
                part.setPrice(new BigDecimal(req.getParameter("price")));

                partService.updatePart(part);
            } else if ("delete".equals(action)) {
                Long id = Long.valueOf(req.getParameter("id"));

                partService.deletePart(id);
            }
        } catch (Exception ex) {
            req.setAttribute("error", "Ошибка: " + ex.getMessage());
            doGet(req, resp);
            return;
        }
        resp.sendRedirect("/admin/parts");
    }
}
