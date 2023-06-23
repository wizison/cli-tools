package at.wizi.tools.clitools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CliToolsApplication {

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(CliToolsApplication.class, args)));
    }
}
