package dk.commentor.kubernetes.poc.command;

import java.util.Arrays;

import picocli.CommandLine;
import picocli.CommandLine.IFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner, ExitCodeGenerator {

    private final LogsCommand myCommand;

    private final IFactory factory;

    private int exitCode;

    public Runner(LogsCommand command, IFactory factory) {
        this.myCommand = command;
        this.factory = factory;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(Arrays.stream(args).findFirst());
        exitCode = new CommandLine(myCommand, factory).execute(args);
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }
}
