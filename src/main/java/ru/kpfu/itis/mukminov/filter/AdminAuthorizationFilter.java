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

@WebFilter("/admin/*")
public class AdminAuthorizationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect("/login");
        } else {
            String role = ((EmployeeDto) session.getAttribute("employee")).getRole().toString();
            if ("ADMIN".equals(role)) {
                chain.doFilter(req, res);
            } else {
                response.sendRedirect("/forbidden");
            }
        }

        chain.doFilter(req, res);
    }
}