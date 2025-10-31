package ru.kpfu.itis.mukminov.controller.staff;

import ru.kpfu.itis.mukminov.dto.EmployeeDto;
import ru.kpfu.itis.mukminov.dto.OrderDto;
import ru.kpfu.itis.mukminov.dto.PartQuantityDto;
import ru.kpfu.itis.mukminov.entity.Order;
import ru.kpfu.itis.mukminov.enums.Status;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/staff/orders")
public class StaffOrdersServlet extends HttpServlet {
    private OrderService orderService;
    private PartService partService;
    private ServiceService serviceService;

    @Override
    public void init() {
        this.orderService = (OrderService) getServletContext().getAttribute("orderService");
        this.partService = (PartService) getServletContext().getAttribute("partService");
        this.serviceService = (ServiceService) getServletContext().getAttribute("serviceService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EmployeeDto staff = (EmployeeDto) req.getSession().getAttribute("employee");

        List<OrderDto> freeOrders = orderService.findAllOrderDto().stream()
                .filter(o -> o.getTechnician() == null).collect(Collectors.toList());
        freeOrders.sort(null);
        List<OrderDto> myOrders = orderService.findAllOrderDto().stream()
                .filter(o -> o.getTechnician() != null && o.getTechnician().getId().equals(staff.getId())).collect(Collectors.toList());
        myOrders.sort(null);
        req.setAttribute("freeOrders", freeOrders);
        req.setAttribute("myOrders", myOrders);
        req.setAttribute("services", serviceService.findAll());
        req.setAttribute("parts", partService.findAll());
        req.setAttribute("user", staff);
        req.getRequestDispatcher("/WEB-INF/views/staff/orders.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EmployeeDto staff = (EmployeeDto) req.getSession().getAttribute("employee");

        String action = req.getParameter("action");
        try {
            if ("take".equals(action)) {
                Long orderId = Long.valueOf(req.getParameter("id"));
                Order order = orderService.findById(orderId).orElseThrow();
                order.setEmployeeId(staff.getId());
                orderService.updateOrder(order);
            } else if ("edit".equals(action)) {
                Long orderId = Long.valueOf(req.getParameter("id"));
                Order order = orderService.findById(orderId).orElseThrow();


                String statusStr = req.getParameter("status");
                if (statusStr != null && !statusStr.isEmpty()) {
                    order.setStatus(Status.valueOf(statusStr));
                }

                String completedAtStr = req.getParameter("completedAt");
                if (completedAtStr != null && !completedAtStr.isEmpty()) {
                    order.setCompletedAt(Timestamp.valueOf(completedAtStr.replace("T", " ") + ":00"));
                } else {
                    order.setCompletedAt(null);
                }


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
                        String[] partData = entry.split(":");
                        if (partData.length == 2) {
                            Long partId = Long.parseLong(partData[0].trim());
                            int newQty = Integer.parseInt(partData[1].trim());

                            int oldQty = currentPartsMap.getOrDefault(partId, 0);
                            int diff = newQty - oldQty;

                            if (newQty > 0) {
                                orderService.addPartToOrder(orderId, partId, newQty);
                            }
                            if (diff != 0) {
                                partService.adjustStockQuantity(partId, -diff);
                            }
                        }
                    }
                }

                orderService.updateOrder(order);
            }
        } catch (Exception e) {
            req.setAttribute("error", "Ошибка: " + e.getMessage());
            doGet(req, resp);
            return;
        }
        resp.sendRedirect("/staff/orders");
    }
}
