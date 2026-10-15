package com.boringco.sentrax.rules;
import com.boringco.sentrax.model.*;
import java.time.Duration;
import java.util.*;
public class LocationRule extends AbstractFraudRule {
    private static final Map<String, double[]> CITIES = new HashMap<>();
    static {
        CITIES.put("delhi", new double[]{28.61, 77.21}); CITIES.put("ghaziabad", new double[]{28.67, 77.45});
        CITIES.put("mumbai", new double[]{19.08, 72.88}); CITIES.put("pune", new double[]{18.52, 73.86});
        CITIES.put("bengaluru", new double[]{12.97, 77.59}); CITIES.put("chennai", new double[]{13.08, 80.27});
        CITIES.put("hyderabad", new double[]{17.38, 78.48}); CITIES.put("kolkata", new double[]{22.57, 88.36});
        CITIES.put("jaipur", new double[]{26.91, 75.79}); CITIES.put("lucknow", new double[]{26.85, 80.95});
    }
    public LocationRule() { super("Location mismatch", 20); }
    @Override public RuleResult evaluate(Transaction t, CustomerContext ctx) {
        String home = ctx.getCustomer().getHomeCity(), loc = t.getLocation();
        if (home != null && home.equalsIgnoreCase(loc)) return result(0, "");
        Optional<Transaction> last = ctx.getLastTransaction();
        if (last.isPresent() && !loc.equalsIgnoreCase(last.get().getLocation())) {
            long mins = Math.abs(Duration.between(last.get().getTxnTime(), t.getTxnTime()).toMinutes());
            if (mins <= 60) return result(1.0, "Impossible travel: " + last.get().getLocation() + " then " + loc + " within " + mins + " min");
        }
        double km = distanceKm(home, loc);
        if (km < 0) return result(0.6, loc + " is away from home city " + home);
        return result(Math.min(1.0, km / 1000.0), String.format("%s is about %.0f km from home city %s", loc, km, home));
    }
    /** Haversine distance, or -1 when a city is unknown. */
    static double distanceKm(String a, String b) {
        double[] p = a == null ? null : CITIES.get(a.toLowerCase()), q = b == null ? null : CITIES.get(b.toLowerCase());
        if (p == null || q == null) return -1;
        double dLat = Math.toRadians(q[0] - p[0]), dLon = Math.toRadians(q[1] - p[1]);
        double h = Math.pow(Math.sin(dLat / 2), 2) + Math.cos(Math.toRadians(p[0])) * Math.cos(Math.toRadians(q[0])) * Math.pow(Math.sin(dLon / 2), 2);
        return 6371 * 2 * Math.asin(Math.sqrt(h));
    }
}
