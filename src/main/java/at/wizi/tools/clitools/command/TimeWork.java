package at.wizi.tools.clitools.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.time.LocalTime;
import java.util.concurrent.Callable;

@Component(value = TimeWork.CLI_COMMAND_BEAN_TIME_WORK)
@CommandLine.Command(name = TimeWork.CLI_COMMAND_BEAN_TIME_WORK)
public class TimeWork implements Callable<Integer> {

    public static final String CLI_COMMAND_BEAN_TIME_WORK = "timeWork";

    private static final Logger LOG = LoggerFactory.getLogger(TimeWork.class);

    @CommandLine.Option(names = {"-s", "--start"}, description = "start time", showDefaultValue = CommandLine.Help.Visibility.ALWAYS)
    private String start = "07:30";
    @CommandLine.Option(names = {"-p", "--pause"}, description = "pause in minutes", showDefaultValue = CommandLine.Help.Visibility.ALWAYS)
    private int pause = 30;
    @CommandLine.Option(names = {"-w", "--work", "--working-time"}, description = "working time", showDefaultValue = CommandLine.Help.Visibility.ALWAYS)
    private String work = "08:15";

    @Override
    @SuppressWarnings("java:S2629")
    public Integer call() throws Exception {

        LOG.info("parameters: start: {}, pause: {}, work: {}", start, pause, work);

        try {
            final LocalTime startTime = LocalTime.parse(start);
            final LocalTime workingTime = LocalTime.parse(work);

            final LocalTime endTime = startTime.plusMinutes(pause)
                    .plusHours(workingTime.getHour())
                    .plusMinutes(workingTime.getMinute());

            LOG.info("end time: {}", endTime);
        } catch (Exception e) {
            LOG.error(e.getMessage()); // don't log the stacktrace in console application
            return -1;
        }

        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new TimeWork()).execute(args);
        System.exit(exitCode);
    }
}
