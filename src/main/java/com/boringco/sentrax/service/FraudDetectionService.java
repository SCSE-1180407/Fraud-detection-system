package com.boringco.sentrax.service;
import com.boringco.sentrax.dao.*;
import com.boringco.sentrax.engine.*;
import com.boringco.sentrax.exception.InvalidTransactionException;
import com.boringco.sentrax.model.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
public class FraudDetectionService {
    private final CustomerDAO customerDao = new CustomerDAO();
    private final TransactionDAO txnDao = new TransactionDAO();
    private final RiskScoringEngine engine = new RiskScoringEngine();
    private final AlertService alertService = new AlertService();

    public static void validate(Transaction t) throws InvalidTransactionException {
        if (t == null) throw new InvalidTransactionException("No transaction supplied.");
        if (t.getAmount() == null || t.getAmount().signum() <= 0) throw new InvalidTransactionException("Amount must be greater than zero.");
        if (t.getCustomerId() <= 0) throw new InvalidTransactionException("Choose a customer.");
        if (blank(t.getMerchant()) || blank(t.getLocation()) || blank(t.getDeviceId()))
            throw new InvalidTransactionException("Merchant, location and device ID are required.");
        if (t.getTxnTime() == null) t.setTxnTime(LocalDateTime.now());
    }
    private static boolean blank(String s) { return s == null || s.trim().isEmpty(); }

    public ScoreReport process(Transaction t) throws InvalidTransactionException {
        validate(t);
        Customer c = customerDao.findById(t.getCustomerId()).orElseThrow(() -> new InvalidTransactionException("Customer does not exist."));
        CustomerContext ctx = new CustomerContext(c, txnDao.findRecentByCustomer(c.getId(), 200));
        ScoreReport report = engine.score(t, ctx);
        t.setRiskScore(report.getScore()); t.setRiskLevel(report.getLevel()); t.setReasons(report.reasonsText());
        txnDao.save(t);
        if (report.getLevel() == RiskLevel.FRAUD) alertService.raiseAsync(t.getId());
        return report;
    }
    public List<Customer> customers() { return customerDao.findAll(); }
    public Map<RiskLevel, Integer> stats() { return txnDao.countByLevel(); }
    public int openAlertCount() { return alertService.openCount(); }
    /** Uses Comparable on Transaction (highest score first) plus a stream filter. */
    public List<Transaction> topRisky(int n) {
        List<Transaction> all = txnDao.findAll().stream().filter(t -> t.getRiskLevel() != RiskLevel.SAFE).collect(Collectors.toList());
        Collections.sort(all);
        return all.stream().limit(n).collect(Collectors.toList());
    }
}
