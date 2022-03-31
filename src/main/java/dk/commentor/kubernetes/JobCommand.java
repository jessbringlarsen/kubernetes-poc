package dk.commentor.kubernetes;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

import dk.commentor.kubernetes.api.BatchAPI;
import dk.commentor.kubernetes.api.CoreAPI;
import io.kubernetes.client.PodLogs;
import io.kubernetes.client.openapi.models.V1Job;
import io.kubernetes.client.openapi.models.V1JobList;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.util.Streams;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "job", description = "Show Kubernetes jobs", mixinStandardHelpOptions = true)
public class JobCommand implements Callable<Integer> {

    private final CoreAPI coreAPI = new CoreAPI();
    private final BatchAPI batchApi = new BatchAPI();

    @Override
    public Integer call() {
        System.out.println("jobs...");
        return 0;
    }

    @Command(name = "list", description = "List Kubernetes jobs")
    public void list() throws Exception {
        V1JobList v1JobList = batchApi.getJobList();
        v1JobList.getItems()
            .forEach(v1Job -> System.out.printf("Job: %s, Succeeded: %s\n", v1Job.getMetadata().getName(), v1Job.getStatus().getSucceeded()));
    }

    @Command(name = "status", description = "Show stats about a Kubernetes jobs")
    public void status(@Option(names = {"--job"}, description = "Name of job", required = true) String jobName) throws Exception {
        Optional<V1Job> job = batchApi.getJobWithName(jobName);
        if (job.isEmpty()) {
            System.out.println("Job not found: " + jobName);
            System.exit(1);
        }
        System.out.println(job.get().getStatus());
    }


    @Command(name = "logs", description = "Prints logs from a pod")
    public void logs(@Option(names = "--pod", description = "Name of pod", required = true) String podName) throws Exception {
        Optional<V1Pod> v1Pod = coreAPI.getPodWithName(podName);
        if (v1Pod.isEmpty()) {
            System.out.println("Pod not found: " + podName);
            System.exit(1);
        }
        InputStream is = new PodLogs().streamNamespacedPodLog(v1Pod.get());
        Streams.copy(is, System.out);
    }

    @Command(name = "pods", description = "Show pods")
    public void pods(@Option(names = "--metadata", description = "Show metadata") boolean showMetadata) throws Exception {
        List<V1Pod> pods = coreAPI.getPods();
        if (showMetadata) {
            pods.forEach(pod -> System.out.println(pod.getMetadata()));
        } else {
            pods.forEach(pod -> System.out.println(pod.getMetadata().getName()));
        }
    }
}
