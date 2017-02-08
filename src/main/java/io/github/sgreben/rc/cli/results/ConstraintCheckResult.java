package io.github.sgreben.rc.cli.results;

import io.github.sgreben.rc.RuleSet;
import io.github.sgreben.rc.Variable;
import io.github.sgreben.rc.cli.outputs.ConstraintCheckResultOutput;
import io.github.sgreben.rc.values.Value;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ConstraintCheckResult {
    private final RuleSet ruleSet;
    private final String constraintString;
    private final List<Map<Variable, Value>> constraintCounterExamples;

    public ConstraintCheckResult(RuleSet ruleSet, String constraintString, List<Map<Variable, Value>> constraintCounterExamples) {
        this.ruleSet = ruleSet;
        this.constraintString = constraintString;
        this.constraintCounterExamples = constraintCounterExamples;
    }

    public ConstraintCheckResult(RuleSet ruleSet, String constraintString) {
        this.ruleSet = ruleSet;
        this.constraintString = constraintString;
        this.constraintCounterExamples = null;
    }

    public ConstraintCheckResultOutput compile() {
        ConstraintCheckResultOutput output = new ConstraintCheckResultOutput();
        output.setConstraint(constraintString);
        output.setSatisfied(constraintCounterExamples == null);
        if (output.isSatisfied()) {
            output.setCounterExamples(null);
        } else {
            List<Map<String, String>> counterExamples = new LinkedList<>();
            for(Map<Variable, Value> counterExample: constraintCounterExamples) {
                Map<String, String> counterExampleOutput = new HashMap<>();
                for(Map.Entry<Variable, Value> entry:  counterExample.entrySet()) {
                    counterExampleOutput.put(entry.getKey().getName(), entry.getValue().toString());
                }
                counterExamples.add(counterExampleOutput);
            }
            output.setCounterExamples(counterExamples);
        }
        return output;
    }
}
