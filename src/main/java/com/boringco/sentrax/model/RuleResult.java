package com.boringco.sentrax.model;
public final class RuleResult {
    private final String name, reason; private final int weight, points;
    public RuleResult(String name, int weight, int points, String reason) { this.name = name; this.weight = weight; this.points = points; this.reason = reason; }
    public String getName() { return name; } public int getWeight() { return weight; }
    public int getPoints() { return points; } public String getReason() { return reason; }
}
