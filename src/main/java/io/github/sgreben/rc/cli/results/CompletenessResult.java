package io.github.sgreben.rc.cli.results;

import io.github.sgreben.rc.RuleSet;
import io.github.sgreben.rc.Variable;
import io.github.sgreben.rc.cli.outputs.CompletenessResultOutput;
import io.github.sgreben.rc.values.Value;

import java.util.HashMap;
import java.util.Map;

public class CompletenessResult {
    private final Map<Variable, Value> counterExample;
    private final RuleSet ruleSet;

    public CompletenessResult(RuleSet ruleSet, Map<Variable, Value> counterExample) {
        this.counterExample = counterExample;
        this.ruleSet = ruleSet;
    }

    public CompletenessResult(RuleSet ruleSet) {
        this.ruleSet = ruleSet;
        this.counterExample = null;
    }

    public boolean hasCounterExample() {
        return counterExample != null;
    }

    public Map<Variable, Value> getCounterExample() {
        return counterExample;
    }

    public RuleSet getRuleSet() {
        return ruleSet;
    }

    public CompletenessResultOutput compile() {
        if (hasCounterExample()) {
            Map<String, String> counterExample = new HashMap<>();
            for (Map.Entry<Variable, Value> entry : getCounterExample().entrySet()) {
                counterExample.put(entry.getKey().getName(), entry.getValue().toString());
            }
            return new CompletenessResultOutput(false, counterExample);
        } else {
            return new CompletenessResultOutput(true, null);
        }
    }
}
