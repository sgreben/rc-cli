package io.github.sgreben.rc.cli;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.github.sgreben.rc.Context;
import io.github.sgreben.rc.Module;
import io.github.sgreben.rc.SolverException;
import io.github.sgreben.rc.cli.outputs.ModuleCheckResultOutput;
import io.github.sgreben.rc.cli.results.ModuleCheckResult;
import io.github.sgreben.rc.declarations.ModuleDeclaration;
import io.github.sgreben.rc.expressions.Expression;
import io.github.sgreben.rc.parser.ExpressionParser;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Main {
    private static DefaultParser optionsParser = new DefaultParser();
    private static Options options = new Options();

    static {
        options.addOption("c", "constraint", true, "A constraint to check for the given rules.");
        options.addOption("rs", "rule-set", true, "Check only the given rule set.");
        options.addOption("h", "help", false, "Print this help message.");
    }

    private static void printHelp() {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("rc [module.yaml]", options);
    }

    public static void main(String[] args) throws IOException, SolverException, ParseException {
        try {
            CommandLine commandLine = optionsParser.parse(options, args);

            if (commandLine.getArgs().length < 1) {
                printHelp();
                return;
            }

            if (commandLine.hasOption('h')) {
                printHelp();
                return;
            }

            File moduleDeclarationFile = new File(commandLine.getArgs()[0]);

            List<String> ruleSetFilter = new LinkedList<>();
            if (commandLine.hasOption("rs")) {
                for(String ruleSet : commandLine.getOptionValues('c')) {
                    ruleSetFilter.add(ruleSet);
                }
            }

            List<String> constraints = new LinkedList<>();
            if (commandLine.hasOption("c")) {
                for(String constraint : commandLine.getOptionValues('c')) {
                    constraints.add(constraint);
                }
            }


            ModuleCheckResult checkResult = checkModuleDeclarationFile(moduleDeclarationFile, constraints, ruleSetFilter);

            ModuleCheckResultOutput checkResultYaml = checkResult.compile();
            printResult(checkResultYaml);

        } catch (ParseException parseException) {
            System.err.println( "Could not parse command line. Reason: " + parseException.getMessage() );
        }
    }

    private static void printResult(ModuleCheckResultOutput checkResultYaml) throws IOException {
        new ObjectMapper(new YAMLFactory())
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .writeValue(System.out, checkResultYaml);
    }

    private static ModuleCheckResult checkModuleDeclarationFile(File moduleDeclarationFile, List<String> constraints, List<String> ruleSetFilter) throws IOException, SolverException {
        Context context = new Context();
        ModuleDeclaration moduleDeclaration = ModuleDeclaration.load(moduleDeclarationFile);
        Module module = moduleDeclaration.compile(context);
        return ModuleChecker.check(context, module, constraints, ruleSetFilter);
    }
}
