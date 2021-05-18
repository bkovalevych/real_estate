package com.course.work.buy_and_sale_house.filter;

import com.course.work.buy_and_sale_house.constant.Constant;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Configuration
public class AuthorizationFilter implements Filter {

    private List<String> adminAccess = new ArrayList<>();
    private List<String> commons = new ArrayList<>();
    private List<String> userAccess = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        userAccess = Arrays.asList(Constant.CLIENT);
        adminAccess = Arrays.asList(Constant.ADMIN);
        commons = Arrays.asList(Constant.COMMON);
    }



    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (accessAllowed(servletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            servletRequest.getRequestDispatcher("/")
                    .forward(servletRequest, servletResponse);
        }

    }

    private boolean accessAllowed(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String commandName = ((HttpServletRequest) request).getRequestURI();

        if (commandName == null || commandName.isEmpty()) {
            return false;
        }
        if (commandName.endsWith("/")) {
            commandName = commandName.substring(0, commandName.length() - 1);
        }
        if (commons.contains(commandName)) {
            return true;
        }
        HttpSession session = httpRequest.getSession(false);
        if (session == null) {
            return false;
        }
        String role = (String) session.getAttribute(Constant.ROLE);
        if (role == null) {
            role = Constant.ROLE_USER;
        }
        boolean isAdminAndHasAccess = role.equals(Constant.ROLE_ADMIN);
        if (commandName.startsWith("/admin")) {
            commandName = commandName.substring(6);
            isAdminAndHasAccess = isAdminAndHasAccess && adminAccess.contains(commandName);
        }
        boolean userHasAccess = false;
        for (String comm : userAccess) {
            userHasAccess = userHasAccess || commandName.startsWith(comm);
        }
        boolean isUserAndHasAccess = role.equals(Constant.ROLE_USER) && userHasAccess;
        return isUserAndHasAccess || isAdminAndHasAccess;
    }

}
