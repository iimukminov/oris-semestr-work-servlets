package ru.kpfu.itis.mukminov.controller.user;

import ru.kpfu.itis.mukminov.dto.ClientDto;
import ru.kpfu.itis.mukminov.dto.OrderClientDto;
import ru.kpfu.itis.mukminov.entity.Equipment;
import ru.kpfu.itis.mukminov.entity.Order;
import ru.kpfu.itis.mukminov.enums.Status;
import ru.kpfu.itis.mukminov.service.EquipmentService;
import ru.kpfu.itis.mukminov.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/user/orders")
public class UserOrdersServlet extends HttpServlet {

    private OrderService orderService;
    private EquipmentService equipmentService;

    @Override
    public void init() {
        orderService = (OrderService) getServletContext().getAttribute("orderService");
        equipmentService = (EquipmentService) getServletContext().getAttribute("equipmentService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        ClientDto client = (ClientDto) session.getAttribute("client");
        List<OrderClientDto> orders = orderService.findAllOrderDtoByClientId(client.getId());
        orders.sort(null);
        List<Equipment> userEquipments = equipmentService.findByClientId(client.getId());
        req.setAttribute("orders", orders);
        req.setAttribute("userEquipments", userEquipments);
        req.setAttribute("user", client);
        req.getRequestDispatcher("/WEB-INF/views/user/orders.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ClientDto client = (ClientDto) req.getAttribute("client");
        String action = req.getParameter("action");

        try {
            if ("add".equals(action)) {
                Long equipmentId = Long.parseLong(req.getParameter("equipmentId"));
                String description = req.getParameter("description");
                orderService.saveOrder(new Order(equipmentId, Status.NEW, description));

            } else if ("editDescription".equals(action)) {
                Long orderId = Long.parseLong(req.getParameter("orderId"));
                String newDescription = req.getParameter("description");
                Optional<Order> order = orderService.findById(orderId);
                if (order.isPresent() && order.get().getStatus() == Status.NEW) {
                    orderService.updateOrder(new Order(orderId, order.get().getEquipmentId(), Status.NEW, newDescription));
                } else {
                    req.setAttribute("error", "Редактирование доступно только для новых заявок");
                    doGet(req, resp);
                    return;
                }
            } else if ("delete".equals(action)) {
                Long orderId = Long.parseLong(req.getParameter("orderId"));
                Optional<Order> order = orderService.findById(orderId);
                if (order.isPresent() && order.get().getStatus() == Status.NEW) {
                    orderService.deleteOrder(orderId);
                } else {
                    req.setAttribute("error", "Удаление доступно только для новых заявок");
                    doGet(req, resp);
                    return;
                }
            }
        } catch (Exception e) {
            req.setAttribute("error", "Ошибка при " + (action.equals("add") ? "добавлении": action.equals("editDescription") ? "изменении": "удалении. Нельзя удалить, если внесены какие-то услуги"));
            doGet(req, resp);
            return;
        }
        resp.sendRedirect("/user/orders");
    }
}
