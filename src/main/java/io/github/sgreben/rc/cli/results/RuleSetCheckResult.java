package io.github.sgreben.rc.cli.results;

import io.github.sgreben.rc.RuleSet;
import io.github.sgreben.rc.cli.outputs.CompletenessResultOutput;
import io.github.sgreben.rc.cli.outputs.RuleOverlapResultOutput;
import io.github.sgreben.rc.cli.outputs.RuleSetCheckResultOutput;

public class RuleSetCheckResult {
    private final CompletenessResult completenessResult;
    private final RuleOverlapResult ruleOverlapResult;
    private final RuleSet ruleSet;

    public RuleSetCheckResult(RuleSet ruleSet, RuleOverlapResult ruleOverlapResult, CompletenessResult completenessResult) {
        this.completenessResult = completenessResult;
        this.ruleOverlapResult = ruleOverlapResult;
        this.ruleSet = ruleSet;
    }

    public RuleSetCheckResultOutput compile() {
        RuleSetCheckResultOutput result = new RuleSetCheckResultOutput();
        RuleOverlapResultOutput ruleOverlapResultOutput = ruleOverlapResult.compile();
        CompletenessResultOutput completenessResultOutput = completenessResult.compile();
        result.setName(ruleSet.getName());
        result.setComplete(completenessResultOutput.isComplete());
        result.setCompletenessCounterExample(completenessResultOutput.getCompletenessCounterExample());
        result.setRuleOverlaps(ruleOverlapResultOutput.getRuleOverlaps());
        return result;
    }
}
