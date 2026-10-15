package com.boringco.sentrax.rules;
import com.boringco.sentrax.model.*;
public interface FraudRule {
    String getName();
    int getWeight();
    RuleResult evaluate(Transaction txn, CustomerContext ctx);
}
