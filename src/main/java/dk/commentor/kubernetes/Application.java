package dk.commentor.kubernetes;

import picocli.CommandLine;

class Application {

    public static void main(String... args) {
        CommandLine commandLine = new CommandLine(new JobCommand());
        if (args.length == 0) {
            commandLine.usage(System.out);
            System.exit(0);
        }
        int exitCode = commandLine.execute(args);
        System.exit(exitCode);
    }
}
