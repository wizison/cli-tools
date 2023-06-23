package at.wizi.tools.clitools.command;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Callable;

@Component(value = TimeCalc.CLI_COMMAND_BEAN_TIME_CALC)
@CommandLine.Command(name = "timeCalc")
public class TimeCalc implements Callable<Integer> {

    public static final String CLI_COMMAND_BEAN_TIME_CALC = "timeCalc";

    private static final Logger LOG = LoggerFactory.getLogger(TimeCalc.class);

    @CommandLine.Option(names = {"-s", "--start"}, description = "start time", showDefaultValue = CommandLine.Help.Visibility.ALWAYS)
    private String start = "07:30";
    @CommandLine.Option(names = {"-e", "--end"}, description = "end time", showDefaultValue = CommandLine.Help.Visibility.ALWAYS)
    private String end = "16:15";
    @CommandLine.Option(names = {"-p", "--pause"}, description = "pause in minutes", showDefaultValue = CommandLine.Help.Visibility.ALWAYS)
    private int pause = 30;

    @Override
    @SuppressWarnings("java:S2629")
    public Integer call() throws Exception {

        LOG.info("parameters: start: {}, end: {}, pause: {}", start, end, pause);

        try {
            final LocalTime startTime = LocalTime.parse(start);
            final LocalTime endTime = LocalTime.parse(end);

            final long workingMinutes = startTime.until(endTime, ChronoUnit.MINUTES) - pause;

            final Duration workingDuration = Duration.of(workingMinutes, ChronoUnit.MINUTES);

            LOG.info("working duration: {}", DurationFormatUtils.formatDuration(workingDuration.toMillis(), "HH:mm"));
        } catch (Exception e) {
            LOG.error(e.getMessage()); // don't log the stacktrace in console application
            return -1;
        }

        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new TimeCalc()).execute(args);
        System.exit(exitCode);
    }
}
