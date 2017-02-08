package io.github.sgreben.rc.cli.outputs;

import io.github.sgreben.rc.cli.results.ConstraintCheckResult;

import java.util.List;
import java.util.Map;

public class RuleSetCheckResultOutput {
    private String name;
    private boolean complete;
    private Map<String, String> completenessCounterExample;
    private List<RuleOverlapOutput> ruleOverlaps;
    private List<ConstraintCheckResultOutput> constraints;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RuleOverlapOutput> getRuleOverlaps() {
        return ruleOverlaps;
    }

    public void setRuleOverlaps(List<RuleOverlapOutput> ruleOverlaps) {
        this.ruleOverlaps = ruleOverlaps;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public Map<String, String> getCompletenessCounterExample() {
        return completenessCounterExample;
    }

    public void setCompletenessCounterExample(Map<String, String> completenessCounterExample) {
        this.completenessCounterExample = completenessCounterExample;
    }

    public List<ConstraintCheckResultOutput> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<ConstraintCheckResultOutput> constraints) {
        this.constraints = constraints;
    }
}
