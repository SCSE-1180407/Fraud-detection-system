package com.boringco.sentrax.servlet;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
@WebFilter("/*")
public class AuthFilter implements Filter {
    @Override public void doFilter(ServletRequest rq, ServletResponse rs, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) rq; HttpServletResponse resp = (HttpServletResponse) rs;
        String path = req.getRequestURI().substring(req.getContextPath().length());
        HttpSession s = req.getSession(false);
        boolean open = path.equals("/login") || path.startsWith("/assets/");
        if (open || (s != null && s.getAttribute("user") != null)) chain.doFilter(rq, rs);
        else resp.sendRedirect(req.getContextPath() + "/login");
    }
}
