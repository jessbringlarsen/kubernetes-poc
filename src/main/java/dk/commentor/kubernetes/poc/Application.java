package dk.commentor.kubernetes.poc;

import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.io.File;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.concurrent.Callable;


class Application {

    public static void main(String... args) {
        int exitCode = new CommandLine(new JobCommand()).execute(args);
        System.exit(exitCode);
    }
}
