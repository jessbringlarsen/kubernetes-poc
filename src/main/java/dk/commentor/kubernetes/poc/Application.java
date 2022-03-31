package dk.commentor.kubernetes.poc;

import picocli.CommandLine;

class Application {

    public static void main(String... args) {
        int exitCode = new CommandLine(new JobCommand()).execute(args);
        System.exit(exitCode);
    }
}
