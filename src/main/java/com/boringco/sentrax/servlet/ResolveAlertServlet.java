package com.boringco.sentrax.servlet;
import com.boringco.sentrax.service.AlertService;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
@WebServlet("/alerts/resolve")
public class ResolveAlertServlet extends HttpServlet {
    private final AlertService alerts = new AlertService();
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try { alerts.resolve(Long.parseLong(req.getParameter("alertId"))); } catch (NumberFormatException ignored) { }
        resp.sendRedirect(req.getContextPath() + "/alerts");
    }
}
