package io.github.sgreben.rc.cli;

import io.github.sgreben.rc.Context;
import io.github.sgreben.rc.Module;
import io.github.sgreben.rc.RuleSet;
import io.github.sgreben.rc.SolverException;
import io.github.sgreben.rc.cli.results.*;
import io.github.sgreben.rc.expressions.Expression;
import io.github.sgreben.rc.parser.ExpressionParser;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleChecker {
    public static ModuleCheckResult check(Context context, Module module, List<String> constraints, List<String> ruleSetFilter) throws SolverException {
        List<RuleSetCheckResult> results = new LinkedList<>();
        Iterable<RuleSet> ruleSetsToCheck = module;
        if (ruleSetFilter.size() > 0) {
            ruleSetsToCheck = ruleSetFilter.stream()
                    .map(module::ruleSet)
                    .collect(Collectors.toList());
        }
        for (RuleSet ruleSet : ruleSetsToCheck) {
            CompletenessResult completenessResult;
            if (ruleSet.isComplete()) {
                completenessResult = new CompletenessResult(ruleSet);
            } else {
                completenessResult = new CompletenessResult(ruleSet, ruleSet.completenessCounterExample());
            }
            RuleOverlapResult ruleOverlapResult;
            if (ruleSet.isOverlapping()) {
                ruleOverlapResult = new RuleOverlapResult(ruleSet, ruleSet.overlaps());
            } else {
                ruleOverlapResult = new RuleOverlapResult(ruleSet);
            }
            List<ConstraintCheckResult> constraintCheckResults = new LinkedList<>();
            ExpressionParser expressionParser = new ExpressionParser(context, ruleSet.getVariables());
            for (String constraintString : constraints) {
                Expression constraint = expressionParser.parse(constraintString);
                if (!ruleSet.satisfiesConstraint(constraint)) {
                    constraintCheckResults.add(new ConstraintCheckResult(ruleSet, constraintString, ruleSet.constraintCounterExamples(constraint)));
                } else {
                    constraintCheckResults.add(new ConstraintCheckResult(ruleSet, constraintString));
                }
            }
            results.add(new RuleSetCheckResult(ruleSet, ruleOverlapResult, completenessResult, constraintCheckResults));
        }
        return new ModuleCheckResult(module, results);
    }
}
