package com.boringco.sentrax.model;
public class Customer {
    private int id; private String name, homeCity; private double avgAmount;
    public int getId() { return id; } public void setId(int id) { this.id = id; }
    public String getName() { return name; } public void setName(String n) { this.name = n; }
    public String getHomeCity() { return homeCity; } public void setHomeCity(String c) { this.homeCity = c; }
    public double getAvgAmount() { return avgAmount; } public void setAvgAmount(double a) { this.avgAmount = a; }
}
