package com.boringco.sentrax.rules;
import com.boringco.sentrax.model.*;
import java.time.Duration;
public class VelocityRule extends AbstractFraudRule {
    public VelocityRule() { super("Transaction velocity", 25); }
    @Override public RuleResult evaluate(Transaction t, CustomerContext ctx) {
        long total = 1 + ctx.getHistory().stream()
            .filter(h -> Math.abs(Duration.between(h.getTxnTime(), t.getTxnTime()).toMinutes()) <= 10).count();
        if (total <= 2) return result(0, "");   // more than 2 in 10 minutes triggers
        return result(Math.min(1.0, (total - 2) / 2.0), total + " transactions within 10 minutes");
    }
}
