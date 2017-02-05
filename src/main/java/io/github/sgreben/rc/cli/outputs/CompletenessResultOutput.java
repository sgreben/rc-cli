package io.github.sgreben.rc.cli.outputs;

import java.util.Map;

public class CompletenessResultOutput {
    private boolean complete;
    private Map<String, String> completenessCounterExample;

    public CompletenessResultOutput(boolean complete, Map<String, String> completenessCounterExample) {
        this.complete = complete;
        this.completenessCounterExample = completenessCounterExample;
    }

    public boolean isComplete() {
        return complete;
    }

    public Map<String, String> getCompletenessCounterExample() {
        return completenessCounterExample;
    }
}
