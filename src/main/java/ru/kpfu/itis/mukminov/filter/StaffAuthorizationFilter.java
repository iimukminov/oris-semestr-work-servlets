package ru.kpfu.itis.mukminov.filter;

import ru.kpfu.itis.mukminov.dto.EmployeeDto;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/staff/*")
public class StaffAuthorizationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            String role = ((EmployeeDto) session.getAttribute("employee")).getRole().toString();
            if ("STAFF".equals(role) || "ADMIN".equals(role)) {
                chain.doFilter(req, res);
            } else {
                response.sendError(403, "У вас недостаточно прав для доступа к этому ресурсу");
            }
        }

    }
}

