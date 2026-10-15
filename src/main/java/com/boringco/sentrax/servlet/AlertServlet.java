package com.boringco.sentrax.servlet;
import com.boringco.sentrax.service.AlertService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
@WebServlet("/alerts")
public class AlertServlet extends HttpServlet {
    private final AlertService alerts = new AlertService();
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("alerts", alerts.openAlerts());
        req.getRequestDispatcher("/alerts.jsp").forward(req, resp);
    }
}
