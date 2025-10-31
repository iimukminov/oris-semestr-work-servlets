package ru.kpfu.itis.mukminov.controller.user;

import ru.kpfu.itis.mukminov.dto.ClientDto;
import ru.kpfu.itis.mukminov.entity.Equipment;
import ru.kpfu.itis.mukminov.service.EquipmentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/user/equipments")
public class UserEquipmentsServlet extends HttpServlet {
    private EquipmentService equipmentService;

    @Override
    public void init() {
        equipmentService = (EquipmentService) getServletContext().getAttribute("equipmentService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        ClientDto client = (ClientDto) session.getAttribute("client");
        if (client == null) {
            resp.sendRedirect("/login");
            return;
        }
        List<Equipment> equipmentList = equipmentService.findByClientId(client.getId());
        req.setAttribute("equipmentList", equipmentList);
        req.setAttribute("user", client);
        req.getRequestDispatcher("/WEB-INF/views/user/equipments.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendRedirect("/login");
            return;
        }
        ClientDto client = (ClientDto) session.getAttribute("client");
        if (client == null) {
            resp.sendRedirect("/login");
            return;
        }

        String action = req.getParameter("action");
        try {
            if ("add".equals(action)) {
                equipmentService.saveEquipment(
                        new Equipment(
                                null,
                                client.getId(),
                                req.getParameter("type"),
                                req.getParameter("brand"),
                                req.getParameter("model"),
                                req.getParameter("serialNumber"),
                                req.getParameter("description")
                        )
                );
            } else if ("edit".equals(action)) {
                Long eqId = Long.parseLong(req.getParameter("id"));

                equipmentService.updateEquipment(
                        new Equipment(
                                eqId,
                                client.getId(),
                                req.getParameter("type"),
                                req.getParameter("brand"),
                                req.getParameter("model"),
                                req.getParameter("serialNumber"),
                                req.getParameter("description")
                        )
                );
            } else if ("delete".equals(action)) {
                Long eqId = Long.parseLong(req.getParameter("id"));

                equipmentService.deleteEquipment(eqId);
            }
        } catch (Exception e) {

            req.setAttribute("error", "Ошибка при " + (action.equals("add") ? "добавлении": action.equals("edit") ? "изменении": "удалении. Нельзя удалить, если использовалось в заявке"));
            doGet(req, resp);
            return;
        }
        resp.sendRedirect("/user/equipments");
    }
}
