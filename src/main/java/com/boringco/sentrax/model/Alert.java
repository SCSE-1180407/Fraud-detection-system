package com.boringco.sentrax.model;
import java.math.BigDecimal;
import java.time.LocalDateTime;
public class Alert {
    private long id, transactionId; private String status = "OPEN", customerName, reasons; private int riskScore;
    private BigDecimal amount; private RiskLevel riskLevel; private LocalDateTime createdAt, resolvedAt;
    public Alert() { }
    public Alert(long transactionId) { this.transactionId = transactionId; }
    public long getId() { return id; } public void setId(long id) { this.id = id; }
    public long getTransactionId() { return transactionId; } public void setTransactionId(long t) { this.transactionId = t; }
    public String getStatus() { return status; } public void setStatus(String s) { this.status = s; }
    public String getCustomerName() { return customerName; } public void setCustomerName(String n) { this.customerName = n; }
    public String getReasons() { return reasons; } public void setReasons(String r) { this.reasons = r; }
    public int getRiskScore() { return riskScore; } public void setRiskScore(int r) { this.riskScore = r; }
    public BigDecimal getAmount() { return amount; } public void setAmount(BigDecimal a) { this.amount = a; }
    public RiskLevel getRiskLevel() { return riskLevel; } public void setRiskLevel(RiskLevel r) { this.riskLevel = r; }
    public LocalDateTime getCreatedAt() { return createdAt; } public void setCreatedAt(LocalDateTime c) { this.createdAt = c; }
    public LocalDateTime getResolvedAt() { return resolvedAt; } public void setResolvedAt(LocalDateTime r) { this.resolvedAt = r; }
}
