package io.github.sgreben.rc.cli.outputs;

import java.util.List;

public class RuleOverlapResultOutput {
    private List<RuleOverlapOutput> ruleOverlaps;

    public RuleOverlapResultOutput(List<RuleOverlapOutput> ruleOverlaps) {
        this.ruleOverlaps = ruleOverlaps;
        if (this.ruleOverlaps.isEmpty()) {
            this.ruleOverlaps = null;
        }
    }

    public List<RuleOverlapOutput> getRuleOverlaps() {
        return ruleOverlaps;
    }
}
