package at.wizi.tools.clitools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "timeCalc")
public class TimeCalc implements Callable<Integer> {

    private static final Logger LOG = LoggerFactory.getLogger(TimeCalc.class);

    @CommandLine.Option(names = {"-s", "--start"}, description = "start time", showDefaultValue = CommandLine.Help.Visibility.ALWAYS)
    private String start = "08:00";
    @CommandLine.Option(names = {"-e", "--end"}, description = "end time", showDefaultValue = CommandLine.Help.Visibility.ALWAYS)
    private String end = "17:00";
    @CommandLine.Option(names = {"-p", "--pause"}, description = "pause in minutes", showDefaultValue = CommandLine.Help.Visibility.ALWAYS)
    private int pause = 0;

    @Override
    public Integer call() throws Exception {

        LOG.info("parameters: start: {}, end: {}, pause: {}", start, end, pause);

        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new TimeCalc()).execute(args);
        System.exit(exitCode);
    }
}
