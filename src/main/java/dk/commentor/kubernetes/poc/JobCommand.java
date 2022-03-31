package dk.commentor.kubernetes.poc;

import io.kubernetes.client.PodLogs;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.Streams;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.Callable;

@Command(name = "job", description = "Show Kubernetes jobs", mixinStandardHelpOptions = true)
public class JobCommand implements Callable<Integer> {

    @Override
    public Integer call() {
        System.out.println("jobs...");
        return 0;
    }

    @Command(name = "show", description = "Show stats about a Kubernetes jobs")
    public void show(@Option(names = {"--job"}, description = "Name of job", required = true) String job) {
    }

    @Command(name = "logs", description = "Prints logs from a pod")
    public void logs(@Option(names = "--pod", description = "Name of pod", required = true) String pod) throws Exception {

        PodLogs logs = new PodLogs();
        List<V1Pod> pods = getApi().listNamespacedPod("default", "false", null, null, null, null, null, null, null, null, null).getItems();
        Optional<V1Pod> v1Pod = pods.stream()
            .filter(p -> p.getMetadata().getName().equals(pod))
            .findFirst();

        if (v1Pod.isEmpty()) {
            System.out.println("Pod not found: " + pod);
            System.exit(1);
        }

        InputStream is = logs.streamNamespacedPodLog(v1Pod.get());
        Streams.copy(is, System.out);
    }

    private CoreV1Api getApi() throws IOException {
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        return new CoreV1Api(client);
    }

    @Command(name = "pods", description = "Prints pods in job")
    public void pods(@Option(names = {"--job"}, description = "Name of job", required = true) String job) throws Exception {

    }
}
