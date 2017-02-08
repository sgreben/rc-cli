package io.github.sgreben.rc.cli.results;

import io.github.sgreben.rc.cli.outputs.RuleOverlapOutput;
import io.github.sgreben.rc.cli.outputs.RuleOverlapResultOutput;
import io.github.sgreben.rc.RuleOverlap;
import io.github.sgreben.rc.RuleSet;
import io.github.sgreben.rc.values.Value;
import io.github.sgreben.rc.Variable;

import java.util.*;
import java.util.Map.Entry;

public class RuleOverlapResult {
    private final RuleSet ruleSet;
    private final List<RuleOverlap> overlaps;

    public RuleOverlapResult(RuleSet ruleSet) {
        this.ruleSet = ruleSet;
        this.overlaps = Collections.emptyList();
    }

    public RuleOverlapResult(RuleSet ruleSet, List<RuleOverlap> overlaps) {
        this.ruleSet = ruleSet;
        this.overlaps = overlaps;
    }

    public RuleSet getRuleSet() {
        return ruleSet;
    }

    public RuleOverlapResultOutput compile() {
        List<RuleOverlapOutput> overlapsOutput = new LinkedList<>();
        for (RuleOverlap ruleOverlap : overlaps) {
            Map<String, String> overlapExample = new HashMap<>();
            for (Entry<Variable, Value> entry : ruleOverlap.getExample().entrySet()) {
                overlapExample.put(entry.getKey().getName(), entry.getValue().toString());
            }
            RuleOverlapOutput.IndexedRuleOutput firstOutput =
                    new RuleOverlapOutput.IndexedRuleOutput(
                            ruleOverlap.getFirst().rule.getSourceString(),
                            ruleOverlap.getFirst().index
                    );
            RuleOverlapOutput.IndexedRuleOutput secondOutput =
                    new RuleOverlapOutput.IndexedRuleOutput(
                            ruleOverlap.getSecond().rule.getSourceString(),
                            ruleOverlap.getSecond().index
                    );
            RuleOverlapOutput ruleOverlapOutput = new RuleOverlapOutput(firstOutput, secondOutput, overlapExample, ruleOverlap.isConsistent());
            overlapsOutput.add(ruleOverlapOutput);
        }
        return new RuleOverlapResultOutput(overlapsOutput);
    }
}
