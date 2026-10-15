package com.boringco.sentrax.rules;
import com.boringco.sentrax.model.*;
public class NewDeviceRule extends AbstractFraudRule {
    public NewDeviceRule() { super("New device", 15); }
    @Override public RuleResult evaluate(Transaction t, CustomerContext ctx) {
        return ctx.getKnownDevices().contains(t.getDeviceId()) ? result(0, "")
            : result(1.0, "Device " + t.getDeviceId() + " has not been used by this customer before");
    }
}
