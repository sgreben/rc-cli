# `rc` - a rule checker

```
usage: rc [module.yaml]
 -c,--constraint <arg>   A constraint to check for the given rules.
 -h,--help               Print this help message.
 -rs,--rule-set <arg>    Check only the given rule set.
```

`rc` is a command-line interface to the [rc](https://github.com/sgrebem/rc) rule checking library.

## Example

```bash
$ cat src/test/resources/module1.yaml
name: myModule
types:
    STATE:
      values:
        - ON
        - OFF
        - STANDBY
ruleSets:
    - name: my rule set
      variables:
        temperature: int
        temperatureGoal: int
        motion: real
        brightness: real
        state: STATE
        stateOut: STATE
      rules:
        - name: rule 1
          when: temperature > 23 && (motion < 0.3 || brightness < 0.1)
          then:
            stateOut: OFF
        - name: rule 2
          when: temperature < temperatureGoal && motion >= 0.3
          then:
              stateOut: ON
        - name: rule 3
          when: temperature >= temperatureGoal && motion < 0.1
          then:
              stateOut: state
        - name: rule 4
          when: temperature >= temperatureGoal && motion > 0.1
          then:
              stateOut: STANDBY
```

```bash
$ java -jar target/rc-cli-1.0-SNAPSHOT-jar-with-dependencies.jar src/test/resources/module1.yaml
---
name: "myModule"
ruleSets:
- name: "my rule set"
  complete: false
  completenessCounterExample:
    temperatureGoal: "1"
    brightness: "0.0"
    motion: "0.0"
    temperature: "0"
    state: "OFF"
    stateOut: "OFF"
  ruleOverlaps:
---
name: "myModule"
ruleSets:
- name: "my rule set"
  complete: false
  completenessCounterExample:
    temperatureGoal: "1"
    brightness: "0.0"
    motion: "0.0"
    temperature: "0"
    state: "OFF"
    stateOut: "OFF"
  ruleOverlaps:
  - first:
      rule: "temperature > 23 && (motion < 0.3 || brightness < 0.1)"
      index: 0
    second:
      rule: "temperature < temperatureGoal && motion >= 0.3"
      index: 1
    overlapExample:
      temperatureGoal: "25"
      brightness: "0.0"
      motion: "0.3"
      temperature: "24"
      state: "OFF"
      stateOut: "OFF"
    consistent: false
  - first:
      rule: "temperature > 23 && (motion < 0.3 || brightness < 0.1)"
      index: 0
    second:
      rule: "temperature >= temperatureGoal && motion < 0.1"
      index: 2
    overlapExample:
      temperatureGoal: "0"
      brightness: "0.0"
      motion: "0.0"
      temperature: "24"
      state: "OFF"
      stateOut: "OFF"
    consistent: true
  - first:
      rule: "temperature > 23 && (motion < 0.3 || brightness < 0.1)"
      index: 0
    second:
      rule: "temperature >= temperatureGoal && motion > 0.1"
      index: 3
    overlapExample:
      temperatureGoal: "0"
      brightness: "0.0"
      motion: "0.2"
      temperature: "24"
      state: "OFF"
      stateOut: "OFF"
    consistent: false
```