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
    public void logs(@Option(names = "--pod", description = "Name of pod", required = false) String pod) throws Exception {
        Optional<V1Pod> v1Pod = getPodWithNameOrFirst(pod, getPods());
        if (v1Pod.isEmpty()) {
            System.out.println("Pod not found: " + pod);
            System.exit(1);
        }
        InputStream is = new PodLogs().streamNamespacedPodLog(v1Pod.get());
        Streams.copy(is, System.out);
    }

    @Command(name = "pods", description = "Show pods")
    public void pods(@Option(names = "--metadata", description = "Show metadata", required = false) boolean showMetadata) throws Exception {
        List<V1Pod> pods = getPods();
        if (showMetadata) {
            pods.forEach(pod -> System.out.println(pod.getMetadata()));
        } else {
            pods.forEach(pod -> System.out.println(pod.getMetadata().getName()));
        }
    }

    private Optional<V1Pod> getPodWithNameOrFirst(String pod, List<V1Pod> pods) {
        if (pod == null) {
            return pods.stream()
                .filter(p -> p.getMetadata().getName().equals(pod))
                .findFirst();
        }
        return Optional.of(pods.get(0));
    }

    private List<V1Pod> getPods() throws Exception {
        return getApi().listNamespacedPod("default", "false", null, null, null, null, null, null, null, null, null).getItems();
    }

    private CoreV1Api getApi() throws IOException {
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        return new CoreV1Api(client);
    }
}
