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

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, SolverException {
        File file = new File(args[0]);
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
