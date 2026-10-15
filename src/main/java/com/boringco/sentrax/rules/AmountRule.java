package com.boringco.sentrax.rules;
import com.boringco.sentrax.model.*;
public class AmountRule extends AbstractFraudRule {
    public AmountRule() { super("Amount threshold", 30); }
    @Override public RuleResult evaluate(Transaction t, CustomerContext ctx) {
        double avg = ctx.getCustomer().getAvgAmount(), amt = t.getAmount().doubleValue();
        if (avg <= 0 || amt / avg < 2) return result(0, "");
        double ratio = amt / avg;   // 2x -> 25%, 3x -> 50%, 5x or more -> 100%
        return result(Math.min(1.0, (ratio - 1) / 4), String.format("Amount Rs %.0f is %.1fx the customer's average of Rs %.0f", amt, ratio, avg));
    }
}
