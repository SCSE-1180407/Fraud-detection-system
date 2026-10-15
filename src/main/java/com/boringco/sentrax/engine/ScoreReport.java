package com.boringco.sentrax.engine;
import com.boringco.sentrax.model.*;
import java.util.List;
import java.util.stream.Collectors;
public class ScoreReport {
    private final int score; private final RiskLevel level; private final List<RuleResult> results;
    public ScoreReport(int score, RiskLevel level, List<RuleResult> results) { this.score = score; this.level = level; this.results = results; }
    public int getScore() { return score; } public RiskLevel getLevel() { return level; } public List<RuleResult> getResults() { return results; }
    public String reasonsText() { return results.stream().map(r -> r.getName() + ": " + r.getReason()).collect(Collectors.joining("; ")); }
}
