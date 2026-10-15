package com.boringco.sentrax.model;
public enum RiskLevel {
    SAFE, SUSPICIOUS, FRAUD;
    public static RiskLevel fromScore(int s) { return s >= 70 ? FRAUD : s >= 35 ? SUSPICIOUS : SAFE; }
}
