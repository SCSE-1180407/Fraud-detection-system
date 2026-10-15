package com.boringco.sentrax.servlet;
import com.boringco.sentrax.model.RiskLevel;
import com.boringco.sentrax.service.FraudDetectionService;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private final FraudDetectionService service = new FraudDetectionService();
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<RiskLevel, Integer> c = service.stats();
        req.setAttribute("safe", c.getOrDefault(RiskLevel.SAFE, 0));
        req.setAttribute("suspicious", c.getOrDefault(RiskLevel.SUSPICIOUS, 0));
        req.setAttribute("fraud", c.getOrDefault(RiskLevel.FRAUD, 0));
        req.setAttribute("openAlerts", service.openAlertCount());
        req.setAttribute("topRisky", service.topRisky(5));
        req.getRequestDispatcher("/dashboard.jsp").forward(req, resp);
    }
}
