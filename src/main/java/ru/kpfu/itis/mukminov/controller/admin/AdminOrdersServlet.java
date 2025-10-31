package ru.kpfu.itis.mukminov.controller.admin;

import ru.kpfu.itis.mukminov.dto.EmployeeDto;
import ru.kpfu.itis.mukminov.dto.OrderDto;
import ru.kpfu.itis.mukminov.dto.PartQuantityDto;
import ru.kpfu.itis.mukminov.entity.Order;
import ru.kpfu.itis.mukminov.enums.Role;
import ru.kpfu.itis.mukminov.enums.Status;
import ru.kpfu.itis.mukminov.service.EmployeeService;
import ru.kpfu.itis.mukminov.service.OrderService;
import ru.kpfu.itis.mukminov.service.PartService;
import ru.kpfu.itis.mukminov.service.ServiceService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/admin/orders")
public class AdminOrdersServlet extends HttpServlet {
    private OrderService orderService;
    private EmployeeService employeeService;
    private ServiceService serviceService;
    private PartService partService;

    @Override
    public void init() {
        this.orderService = (OrderService) getServletContext().getAttribute("orderService");
        this.employeeService = (EmployeeService) getServletContext().getAttribute("employeeService");
        this.serviceService = (ServiceService) getServletContext().getAttribute("serviceService");
        this.partService = (PartService) getServletContext().getAttribute("partService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<OrderDto> orders = orderService.findAllOrderDto();
        orders.sort(null);


        List<EmployeeDto> employees = employeeService.findAll();
        req.setAttribute("orders", orders);
        req.setAttribute("employees", employees);
        req.setAttribute("services", serviceService.findAll());
        req.setAttribute("parts", partService.findAll());
        req.setAttribute("user", req.getSession(false) != null ? req.getSession(false).getAttribute("employee") : null);
        req.getRequestDispatcher("/WEB-INF/views/admin/orders.ftl").forward(req, resp);
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("edit".equals(action)) {
                Long orderId = Long.valueOf(req.getParameter("id"));
                Order order = orderService.findById(orderId).orElseThrow();

                Long technicianId = req.getParameter("employeeId") != null && !req.getParameter("employeeId").isEmpty()
                        ? Long.valueOf(req.getParameter("employeeId"))
                        : null;
                order.setEmployeeId(technicianId);

                String statusStr = req.getParameter("status");
                if (statusStr != null && !statusStr.isEmpty()) {
                    order.setStatus(Status.valueOf(statusStr));
                }

                order.setDescription(req.getParameter("description"));

                String completedAtStr = req.getParameter("completedAt");
                if (completedAtStr != null && !completedAtStr.isEmpty()) {
                    order.setCompletedAt(Timestamp.valueOf(completedAtStr.replace("T", " ") + ":00"));
                } else {
                    order.setCompletedAt(null);
                }

                orderService.updateOrder(order);


                String servicesParam = req.getParameter("services");
                orderService.removeAllServicesFromOrder(orderId);
                if (servicesParam != null && !servicesParam.isEmpty()) {
                    String[] serviceIds = servicesParam.split(",");
                    for (String sid : serviceIds) {
                        Integer serviceId = Integer.parseInt(sid.trim());
                        orderService.addServiceToOrder(orderId, serviceId);
                    }
                }



                String partsParam = req.getParameter("parts");
                List<PartQuantityDto> currentParts = orderService.getPartsByOrder(orderId);
                Map<Long, Integer> currentPartsMap = currentParts.stream()
                        .collect(Collectors.toMap(p -> p.getPart().getId(), PartQuantityDto::getQuantity));

                orderService.removeAllPartsFromOrder(orderId);

                if (partsParam != null && !partsParam.isEmpty()) {
                    String[] partEntries = partsParam.split(",");
                    for (String entry : partEntries) {
                        String[] partsSplit = entry.split(":");
                        if (partsSplit.length == 2) {
                            Long partId = Long.parseLong(partsSplit[0].trim());
                            int newQuantity = Integer.parseInt(partsSplit[1].trim());

                            int oldQuantity = currentPartsMap.getOrDefault(partId, 0);
                            int diff = newQuantity - oldQuantity;

                            if (newQuantity > 0) {
                                orderService.addPartToOrder(orderId, partId, newQuantity);
                            }

                            if (diff != 0) {
                                partService.adjustStockQuantity(partId, -diff);
                            }
                        }
                    }
                }
            } else if ("delete".equals(action)) {
                Long id = Long.valueOf(req.getParameter("id"));
                orderService.deleteOrder(id);
            }
        } catch (Exception e) {
            req.setAttribute("error", "Ошибка: " + e.getMessage());
            doGet(req, resp);
            return;
        }
        resp.sendRedirect("/admin/orders");
    }
}
