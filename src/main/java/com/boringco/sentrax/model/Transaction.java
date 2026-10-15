package com.boringco.sentrax.model;
import java.math.BigDecimal;
import java.time.LocalDateTime;
public class Transaction implements Comparable<Transaction> {
    private long id; private int customerId, riskScore; private String customerName, merchant, location, deviceId, reasons;
    private BigDecimal amount; private LocalDateTime txnTime; private RiskLevel riskLevel;
    public long getId() { return id; } public void setId(long id) { this.id = id; }
    public int getCustomerId() { return customerId; } public void setCustomerId(int c) { this.customerId = c; }
    public int getRiskScore() { return riskScore; } public void setRiskScore(int r) { this.riskScore = r; }
    public String getCustomerName() { return customerName; } public void setCustomerName(String n) { this.customerName = n; }
    public String getMerchant() { return merchant; } public void setMerchant(String m) { this.merchant = m; }
    public String getLocation() { return location; } public void setLocation(String l) { this.location = l; }
    public String getDeviceId() { return deviceId; } public void setDeviceId(String d) { this.deviceId = d; }
    public String getReasons() { return reasons; } public void setReasons(String r) { this.reasons = r; }
    public BigDecimal getAmount() { return amount; } public void setAmount(BigDecimal a) { this.amount = a; }
    public LocalDateTime getTxnTime() { return txnTime; } public void setTxnTime(LocalDateTime t) { this.txnTime = t; }
    public RiskLevel getRiskLevel() { return riskLevel; } public void setRiskLevel(RiskLevel r) { this.riskLevel = r; }
    /** Highest risk score first. */
    @Override public int compareTo(Transaction o) { return Integer.compare(o.riskScore, this.riskScore); }
}
