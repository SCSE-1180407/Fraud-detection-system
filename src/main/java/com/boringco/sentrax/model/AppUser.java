package com.boringco.sentrax.model;
public class AppUser {
    private int id; private String username, passwordHash, role;
    public int getId() { return id; } public void setId(int id) { this.id = id; }
    public String getUsername() { return username; } public void setUsername(String u) { this.username = u; }
    public String getPasswordHash() { return passwordHash; } public void setPasswordHash(String p) { this.passwordHash = p; }
    public String getRole() { return role; } public void setRole(String r) { this.role = r; }
}
