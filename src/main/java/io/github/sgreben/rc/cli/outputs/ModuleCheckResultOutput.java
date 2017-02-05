package io.github.sgreben.rc.cli.outputs;

import java.util.List;

public class ModuleCheckResultOutput {
    private String name;
    private List<RuleSetCheckResultOutput> ruleSets;

    public ModuleCheckResultOutput(String name, List<RuleSetCheckResultOutput> ruleSets) {
        this.name = name;
        this.ruleSets = ruleSets;
    }

    public String getName() {
        return name;
    }

    public List<RuleSetCheckResultOutput> getRuleSets() {
        return ruleSets;
    }
}
