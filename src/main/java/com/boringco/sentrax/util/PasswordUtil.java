package com.boringco.sentrax.util;
import java.nio.charset.StandardCharsets;
import java.security.*;
/** SHA-256 with a random per-user salt. Stored as saltHex:hashHex. */
public final class PasswordUtil {
    private static final SecureRandom RNG = new SecureRandom();
    private PasswordUtil() { }
    public static String hash(String password) {
        byte[] salt = new byte[16]; RNG.nextBytes(salt);
        return hex(salt) + ":" + hex(digest(salt, password));
    }
    public static boolean verify(String password, String stored) {
        if (password == null || stored == null) return false;
        String[] p = stored.split(":");
        return p.length == 2 && MessageDigest.isEqual(digest(unhex(p[0]), password), unhex(p[1]));
    }
    private static byte[] digest(byte[] salt, String pw) {
        try { MessageDigest md = MessageDigest.getInstance("SHA-256"); md.update(salt); return md.digest(pw.getBytes(StandardCharsets.UTF_8)); }
        catch (NoSuchAlgorithmException e) { throw new IllegalStateException(e); }
    }
    private static String hex(byte[] b) { StringBuilder s = new StringBuilder(); for (byte x : b) s.append(String.format("%02x", x)); return s.toString(); }
    private static byte[] unhex(String s) { byte[] b = new byte[s.length() / 2]; for (int i = 0; i < b.length; i++) b[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16); return b; }
}
