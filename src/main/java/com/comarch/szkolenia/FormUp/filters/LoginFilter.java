package com.comarch.szkolenia.FormUp.filters;

import com.comarch.szkolenia.FormUp.model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        User user = (session != null) ? (User) session.getAttribute("user") : null;
        String uri = req.getRequestURI();
        if (user != null && uri.equals("/login")) {
            res.sendRedirect("/dashboard");
        } else if (user == null && !uri.equals("/login") && !uri.equals("/register")) {
            res.sendRedirect("/login");
        } else {
            chain.doFilter(request, response);
        }
    }
}