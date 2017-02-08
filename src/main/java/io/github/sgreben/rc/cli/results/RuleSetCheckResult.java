package io.github.sgreben.rc.cli.results;

import io.github.sgreben.rc.RuleSet;
import io.github.sgreben.rc.cli.outputs.CompletenessResultOutput;
import io.github.sgreben.rc.cli.outputs.ConstraintCheckResultOutput;
import io.github.sgreben.rc.cli.outputs.RuleOverlapResultOutput;
import io.github.sgreben.rc.cli.outputs.RuleSetCheckResultOutput;

import java.util.LinkedList;
import java.util.List;

public class RuleSetCheckResult {
    private final CompletenessResult completenessResult;
    private final RuleOverlapResult ruleOverlapResult;
    private final RuleSet ruleSet;
    private final List<ConstraintCheckResult> constraintCheckResults;

    public RuleSetCheckResult(RuleSet ruleSet, RuleOverlapResult ruleOverlapResult, CompletenessResult completenessResult, List<ConstraintCheckResult> constraintCheckResults) {
        this.completenessResult = completenessResult;
        this.ruleOverlapResult = ruleOverlapResult;
        this.ruleSet = ruleSet;
        this.constraintCheckResults = constraintCheckResults;
    }

    public RuleSetCheckResultOutput compile() {
        RuleSetCheckResultOutput result = new RuleSetCheckResultOutput();
        RuleOverlapResultOutput ruleOverlapResultOutput = ruleOverlapResult.compile();
        CompletenessResultOutput completenessResultOutput = completenessResult.compile();
        List<ConstraintCheckResultOutput> constraintCheckResultOutputs = new LinkedList<>();
        for (ConstraintCheckResult constraintCheckResult : constraintCheckResults) {
            constraintCheckResultOutputs.add(constraintCheckResult.compile());
        }
        result.setName(ruleSet.getName());
        result.setComplete(completenessResultOutput.isComplete());
        result.setCompletenessCounterExample(completenessResultOutput.getCompletenessCounterExample());
        result.setRuleOverlaps(ruleOverlapResultOutput.getRuleOverlaps());
        result.setConstraints(constraintCheckResultOutputs);
        return result;
    }
}
