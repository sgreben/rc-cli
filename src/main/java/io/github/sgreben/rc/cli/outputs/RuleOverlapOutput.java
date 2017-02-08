package io.github.sgreben.rc.cli.outputs;

import java.util.Map;

public class RuleOverlapOutput {
    private IndexedRuleOutput first;
    private IndexedRuleOutput second;
    private Map<String, String> overlapExample;
    private final boolean isConsistent;

    public boolean isConsistent() {
        return isConsistent;
    }

    public RuleOverlapOutput(IndexedRuleOutput first, IndexedRuleOutput second, Map<String, String> overlapExample, boolean isConsistent) {
        this.first = first;
        this.second = second;
        this.overlapExample = overlapExample;
        this.isConsistent = isConsistent;
    }

    public static class IndexedRuleOutput {
        private String rule;
        private int index;

        public IndexedRuleOutput(String rule, int index) {
            this.rule = rule;
            this.index = index;
        }

        public String getRule() {
            return rule;
        }

        public int getIndex() {
            return index;
        }
    }

    public IndexedRuleOutput getFirst() {
        return first;
    }

    public IndexedRuleOutput getSecond() {
        return second;
    }

    public Map<String, String> getOverlapExample() {
        return overlapExample;
    }
}
