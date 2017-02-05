package io.github.sgreben.rc.cli.results;

import io.github.sgreben.rc.cli.outputs.ModuleCheckResultOutput;
import io.github.sgreben.rc.cli.outputs.RuleSetCheckResultOutput;
import io.github.sgreben.rc.Module;

import java.util.LinkedList;
import java.util.List;

public class ModuleCheckResult {
    private final List<RuleSetCheckResult> results;
    private final Module module;

    public ModuleCheckResult(Module module, List<RuleSetCheckResult> results) {
        this.module = module;
        this.results = results;
    }

    public ModuleCheckResultOutput compile() {
        List<RuleSetCheckResultOutput> ruleSetResults = new LinkedList<>();
        for (RuleSetCheckResult ruleSetCheckResult : results) {
            ruleSetResults.add(ruleSetCheckResult.compile());
        }
        return new ModuleCheckResultOutput(module.getName(), ruleSetResults);
    }
}
