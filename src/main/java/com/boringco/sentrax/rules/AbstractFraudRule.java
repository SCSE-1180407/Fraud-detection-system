package com.boringco.sentrax.rules;
import com.boringco.sentrax.model.RuleResult;
public abstract class AbstractFraudRule implements FraudRule {
    private final String name; private final int weight;
    protected AbstractFraudRule(String name, int weight) { this.name = name; this.weight = weight; }
    @Override public String getName() { return name; }
    @Override public int getWeight() { return weight; }
    /** severity is 0.0 to 1.0; points = weight x severity. */
    protected RuleResult result(double severity, String reason) {
        return new RuleResult(name, weight, (int) Math.round(weight * severity), reason);
    }
}
