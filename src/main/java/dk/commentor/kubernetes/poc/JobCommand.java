package dk.commentor.kubernetes.poc;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@Command(name = "job", description = "Show Kubernetes jobs", mixinStandardHelpOptions = true)
public class JobCommand implements Callable<Integer> {

    private String job;
    private String pod;

    @Override
    public Integer call() {
        System.out.println("jobs...");
        return 0;
    }

    @Command(name = "show", description = "Show stats about a Kubernetes jobs")
    public void show(@Option(names = {"--job"}, description = "Name of job", required = true) String job) {
        this.job = job;
        System.out.println(job + " stats...");
    }

    @Command(name = "logs", description = "Prints logs from a pod")
    public void logs(@Option(names = "--pod", description = "Name of pod", required = true) String pod) {
        this.pod = pod;
        System.out.println(String.format("%s logs: %s", pod, "logs..."));
    }

    @Command(name = "pods", description = "Prints pods in job")
    public void pods() {
        System.out.println("pods...");
    }
}
