package io.github.sgreben.rc.cli;

import io.github.sgreben.rc.Context;
import io.github.sgreben.rc.Module;
import io.github.sgreben.rc.RuleSet;
import io.github.sgreben.rc.SolverException;
import io.github.sgreben.rc.cli.results.CompletenessResult;
import io.github.sgreben.rc.cli.results.ModuleCheckResult;
import io.github.sgreben.rc.cli.results.RuleOverlapResult;
import io.github.sgreben.rc.cli.results.RuleSetCheckResult;

import java.util.LinkedList;
import java.util.List;

public class ModuleChecker {
    public static ModuleCheckResult check(Module module) throws SolverException {
        List<RuleSetCheckResult> results = new LinkedList<>();
        for (RuleSet ruleSet: module) {
            CompletenessResult completenessResult;
            if(ruleSet.isComplete()) {
                completenessResult = new CompletenessResult(ruleSet);
            } else {
                completenessResult = new CompletenessResult(ruleSet, ruleSet.completenessCounterExample());
            }
            RuleOverlapResult ruleOverlapResult;
            if(ruleSet.isOverlapping()) {
                ruleOverlapResult = new RuleOverlapResult(ruleSet, ruleSet.overlaps());
            } else {
                ruleOverlapResult = new RuleOverlapResult(ruleSet);
            }
            results.add(new RuleSetCheckResult(ruleSet, ruleOverlapResult, completenessResult));
        }
        return new ModuleCheckResult(module, results);
    }
}
