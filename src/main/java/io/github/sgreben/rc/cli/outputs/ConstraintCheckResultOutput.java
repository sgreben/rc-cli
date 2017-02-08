package io.github.sgreben.rc.cli.outputs;

import java.util.List;
import java.util.Map;

public class ConstraintCheckResultOutput {
    private String constraint;
    private boolean isSatisfied;
    private List<Map<String, String>> counterExamples;

    public String getConstraint() {
        return constraint;
    }

    public void setConstraint(String constraint) {
        this.constraint = constraint;
    }

    public boolean isSatisfied() {
        return isSatisfied;
    }

    public void setSatisfied(boolean satisfied) {
        isSatisfied = satisfied;
    }

    public List<Map<String, String>> getCounterExamples() {
        return counterExamples;
    }

    public void setCounterExamples(List<Map<String, String>> counterExamples) {
        this.counterExamples = counterExamples;
    }
}
