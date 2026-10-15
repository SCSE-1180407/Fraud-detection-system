package com.boringco.sentrax.servlet;
import com.boringco.sentrax.model.AppUser;
import com.boringco.sentrax.service.AuthService;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final AuthService auth = new AuthService();
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<AppUser> user = auth.login(req.getParameter("username"), req.getParameter("password"));
        if (user.isPresent()) {
            req.changeSessionId();
            req.getSession().setAttribute("user", user.get());
            resp.sendRedirect(req.getContextPath() + "/dashboard");
        } else {
            req.setAttribute("error", "Wrong username or password.");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
