package com.boringco.sentrax.engine;
import com.boringco.sentrax.model.*;
import com.boringco.sentrax.rules.*;
import java.util.List;
import java.util.stream.Collectors;
public class RiskScoringEngine {
    // Polymorphism: the engine only knows the FraudRule interface.
    private final List<FraudRule> rules = List.of(new AmountRule(), new VelocityRule(), new LocationRule(), new NewDeviceRule(), new OddHourRule());
    public ScoreReport score(Transaction t, CustomerContext ctx) {
        List<RuleResult> fired = rules.stream().map(r -> r.evaluate(t, ctx)).filter(r -> r.getPoints() > 0).collect(Collectors.toList());
        int total = Math.min(100, fired.stream().mapToInt(RuleResult::getPoints).sum());
        return new ScoreReport(total, RiskLevel.fromScore(total), fired);
    }
}
