package com.boringco.sentrax.servlet;
import com.boringco.sentrax.engine.ScoreReport;
import com.boringco.sentrax.exception.InvalidTransactionException;
import com.boringco.sentrax.model.Transaction;
import com.boringco.sentrax.service.FraudDetectionService;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
@WebServlet("/submit")
public class SubmitTransactionServlet extends HttpServlet {
    private final FraudDetectionService service = new FraudDetectionService();
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object report = req.getSession().getAttribute("lastReport");
        if ("1".equals(req.getParameter("result")) && report != null) {     // Post-Redirect-Get target
            req.setAttribute("report", report);
            req.getRequestDispatcher("/result.jsp").forward(req, resp);
        } else showForm(req, resp, null);
    }
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Transaction t = new Transaction();
            t.setCustomerId(Integer.parseInt(p(req, "customerId")));
            t.setAmount(new BigDecimal(p(req, "amount")));
            t.setMerchant(p(req, "merchant")); t.setLocation(p(req, "location")); t.setDeviceId(p(req, "deviceId"));
            String tm = p(req, "txnTime");
            t.setTxnTime(tm.isEmpty() ? LocalDateTime.now() : LocalDateTime.parse(tm));
            ScoreReport report = service.process(t);
            req.getSession().setAttribute("lastReport", report);
            resp.sendRedirect(req.getContextPath() + "/submit?result=1");
        } catch (InvalidTransactionException e) {
            showForm(req, resp, e.getMessage());
        } catch (RuntimeException e) {   // bad number or date format
            showForm(req, resp, e instanceof com.boringco.sentrax.exception.FraudDetectionException
                ? "Could not process the transaction. Check the database connection." : "Enter valid values for customer, amount and time.");
        }
    }
    private static String p(HttpServletRequest r, String n) { String v = r.getParameter(n); return v == null ? "" : v.trim(); }
    private void showForm(HttpServletRequest req, HttpServletResponse resp, String error) throws ServletException, IOException {
        req.setAttribute("customers", service.customers()); req.setAttribute("error", error);
        req.getRequestDispatcher("/submit.jsp").forward(req, resp);
    }
}
