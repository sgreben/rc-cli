import io.github.sgreben.rc.Context;
import io.github.sgreben.rc.Module;
import io.github.sgreben.rc.SolverException;
import io.github.sgreben.rc.cli.ModuleChecker;
import io.github.sgreben.rc.cli.results.ModuleCheckResult;
import io.github.sgreben.rc.declarations.ModuleDeclaration;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class ModuleCheckerShould {
    private Context context;

    @Before public void
    setUp() {
        context = new Context();
    }

    @Test public void
    check_the_example_module() throws IOException, SolverException {
        File file = new File(getClass().getClassLoader().getResource("module1.yaml").getFile());
        ModuleDeclaration moduleDeclaration = ModuleDeclaration.load(file);
        Module module = moduleDeclaration.compile(context);
        ModuleCheckResult result = ModuleChecker.check(context, module, new LinkedList<>(), new LinkedList<>());
    }
}
