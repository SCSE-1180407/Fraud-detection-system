package com.boringco.sentrax;
import static org.junit.Assert.*;
import com.boringco.sentrax.engine.*;
import com.boringco.sentrax.exception.InvalidTransactionException;
import com.boringco.sentrax.model.*;
import com.boringco.sentrax.rules.*;
import com.boringco.sentrax.service.FraudDetectionService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import org.junit.Test;

public class RulesAndEngineTest {
    private static Customer cust() { Customer c = new Customer(); c.setId(1); c.setName("Asha"); c.setHomeCity("Delhi"); c.setAvgAmount(1000); return c; }
    private static Transaction txn(double amt, String city, String dev, int hour, int min) {
        Transaction t = new Transaction(); t.setCustomerId(1); t.setAmount(BigDecimal.valueOf(amt)); t.setMerchant("Shop");
        t.setLocation(city); t.setDeviceId(dev); t.setTxnTime(LocalDateTime.of(2026, 10, 15, hour, min)); return t;
    }
    private static CustomerContext ctx(Transaction... history) { return new CustomerContext(cust(), Arrays.asList(history)); }

    @Test public void amountRuleScalesWithRatio() {
        AmountRule r = new AmountRule();
        assertEquals(30, r.evaluate(txn(5000, "Delhi", "D1", 14, 0), ctx()).getPoints());
        assertEquals(15, r.evaluate(txn(3000, "Delhi", "D1", 14, 0), ctx()).getPoints());
        assertEquals(0, r.evaluate(txn(1000, "Delhi", "D1", 14, 0), ctx()).getPoints());
    }
    @Test public void oddHourRule() {
        OddHourRule r = new OddHourRule();
        assertEquals(10, r.evaluate(txn(10, "Delhi", "D1", 3, 30), ctx()).getPoints());
        assertEquals(0, r.evaluate(txn(10, "Delhi", "D1", 15, 30), ctx()).getPoints());
    }
    @Test public void newDeviceRule() {
        CustomerContext c = ctx(txn(10, "Delhi", "KNOWN", 10, 0));
        assertEquals(0, new NewDeviceRule().evaluate(txn(10, "Delhi", "KNOWN", 14, 0), c).getPoints());
        assertEquals(15, new NewDeviceRule().evaluate(txn(10, "Delhi", "OTHER", 14, 0), c).getPoints());
    }
    @Test public void velocityRule() {
        CustomerContext c = ctx(txn(10, "Delhi", "D1", 14, 0), txn(10, "Delhi", "D1", 14, 2));
        assertEquals(13, new VelocityRule().evaluate(txn(10, "Delhi", "D1", 14, 5), c).getPoints());   // 3rd in 10 min
        assertEquals(0, new VelocityRule().evaluate(txn(10, "Delhi", "D1", 16, 0), c).getPoints());
    }
    @Test public void locationRule() {
        assertEquals(0, new LocationRule().evaluate(txn(10, "Delhi", "D1", 14, 0), ctx()).getPoints());
        assertTrue(new LocationRule().evaluate(txn(10, "Mumbai", "D1", 14, 0), ctx()).getPoints() > 0);
    }
    @Test public void engineNoRulesFiresIsZero() {
        ScoreReport r = new RiskScoringEngine().score(txn(500, "Delhi", "KNOWN", 14, 0), ctx(txn(10, "Delhi", "KNOWN", 9, 0)));
        assertEquals(0, r.getScore()); assertEquals(RiskLevel.SAFE, r.getLevel());
    }
    @Test public void engineAllRulesFireIsHundred() {
        CustomerContext c = ctx(txn(10, "Delhi", "OLD", 2, 25), txn(10, "Delhi", "OLD", 2, 26), txn(10, "Delhi", "OLD", 2, 27));
        ScoreReport r = new RiskScoringEngine().score(txn(10000, "Mumbai", "NEW", 2, 30), c);
        assertEquals(100, r.getScore()); assertEquals(RiskLevel.FRAUD, r.getLevel()); assertEquals(5, r.getResults().size());
    }
    @Test public void bandBoundaries() {
        assertEquals(RiskLevel.SAFE, RiskLevel.fromScore(34)); assertEquals(RiskLevel.SUSPICIOUS, RiskLevel.fromScore(35));
        assertEquals(RiskLevel.SUSPICIOUS, RiskLevel.fromScore(69)); assertEquals(RiskLevel.FRAUD, RiskLevel.fromScore(70));
    }
    @Test public void comparableSortsHighestFirst() {
        Transaction a = txn(1, "Delhi", "D", 1, 1), b = txn(1, "Delhi", "D", 1, 1); a.setRiskScore(10); b.setRiskScore(90);
        List<Transaction> l = new ArrayList<>(List.of(a, b)); Collections.sort(l); assertEquals(90, l.get(0).getRiskScore());
    }
    @Test(expected = InvalidTransactionException.class)
    public void negativeAmountIsRejected() throws Exception { FraudDetectionService.validate(txn(-5, "Delhi", "D1", 14, 0)); }
}
