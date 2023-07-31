package at.wizi.tools.clitools;

import at.wizi.tools.clitools.command.TimeCalc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;


@Component
public class CliToolsCommandLineRunner implements CommandLineRunner, ExitCodeGenerator, ApplicationContextAware {

    private static final Logger LOG = LoggerFactory.getLogger(CliToolsCommandLineRunner.class);
    private static final String DEFAULT_CLI_COMMAND_BEAN = TimeCalc.CLI_COMMAND_BEAN_TIME_CALC;
    private final CommandLine.IFactory factory;
    private ApplicationContext applicationContext;
    private int exitCode;

    @Autowired
    public CliToolsCommandLineRunner(CommandLine.IFactory factory) {
        this.factory = factory;
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }

    @Override
    public void run(String... args) {

        boolean ignoreFirstParameter = false;

        String cliCommandBeanName;

        if (args.length >= 1) {
            final String firstParameter = args[0];
            if (applicationContext.containsBean(firstParameter)) {
                ignoreFirstParameter = true;
                cliCommandBeanName = firstParameter;
            } else {
                LOG.warn("No cli command bean with name '{}' found, using default '{}'.", firstParameter, DEFAULT_CLI_COMMAND_BEAN);
                LOG.info("Existing command beans are: {}", List.of(applicationContext.getBeanNamesForType(Callable.class)));
                cliCommandBeanName = DEFAULT_CLI_COMMAND_BEAN;
            }
        } else {
            LOG.warn("No cli command given, using default '{}'.", DEFAULT_CLI_COMMAND_BEAN);
            LOG.info("Existing command beans are: {}", List.of(applicationContext.getBeanNamesForType(Callable.class)));
            cliCommandBeanName = DEFAULT_CLI_COMMAND_BEAN;
        }

        exitCode = new CommandLine(applicationContext.getBean(cliCommandBeanName), factory)
                .execute(Arrays.copyOfRange(args, ignoreFirstParameter ? 1 : 0, args.length));
    }
}
