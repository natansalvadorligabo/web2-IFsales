package br.com.ifsales.servlets.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

@WebFilter(urlPatterns = {"/redirect"})
public class ValidationFilter implements Filter {
    private static final String[] actions = {"home", "saveSalesPerson", "updateSalesPerson"
            , "deleteSalesPerson", "listSalesPersons", "logout"};
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);

        String action = httpRequest.getParameter("action");

        if (action != null) {
            if(Arrays.asList(actions).contains(action)
                    && (session == null || session.getAttribute("user") == null)) {
                HttpServletResponse httpResponse = (HttpServletResponse)response;
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/redirect?action=login");
            } else {
                chain.doFilter(request, response);
            }
        }
    }
}