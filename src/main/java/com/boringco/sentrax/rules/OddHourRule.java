package com.boringco.sentrax.rules;
import com.boringco.sentrax.model.*;
public class OddHourRule extends AbstractFraudRule {
    public OddHourRule() { super("Odd hour", 10); }
    @Override public RuleResult evaluate(Transaction t, CustomerContext ctx) {
        int h = t.getTxnTime().getHour();
        return (h >= 1 && h < 4) ? result(1.0, "Transaction at " + t.getTxnTime().toLocalTime() + ", between 1 AM and 4 AM") : result(0, "");
    }
}
