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
import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;

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
        CommandLine commandLine = optionsParser.parse(options, args);

        boolean printHelp = commandLine.hasOption("h") || commandLine.getArgs().length < 1;
        if (printHelp) {
            printHelp();
            return;
        }

        File file = new File(commandLine.getArgs()[0]);
        Context context = new Context();
        ModuleDeclaration moduleDeclaration = ModuleDeclaration.load(file);
        Module module = moduleDeclaration.compile(context);
        ModuleCheckResult checkResult = ModuleChecker.check(module);
        ModuleCheckResultOutput checkResultYaml = checkResult.compile();
        new ObjectMapper(new YAMLFactory())
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .writeValue(System.out, checkResultYaml);
    }
}
