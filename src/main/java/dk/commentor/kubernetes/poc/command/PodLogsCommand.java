package dk.commentor.kubernetes.poc.command;


import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.util.Config;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

@Command(name = "logs", description = "Prints logs from a pod")
public class PodLogsCommand implements Callable<Integer> {

    @CommandLine.Option(names = "--pod", description = "Name of pod. Default is the first one..")
    String podName;

    @Override
    public Integer call() throws Exception {
       /* Optional<V1Pod> pod = getPod();

        if (pod.isEmpty()) {
            System.out.println("Pod not found:" + podName);
            return 1;
        }

        InputStream is =  new PodLogs().streamNamespacedPodLog(pod.get());
        Streams.copy(is, System.out);*/
        System.out.println("logs: " + podName);
        return 0;
    }

    private Optional<V1Pod> getPod() throws Exception {
        List<V1Pod> pods = getApi().listNamespacedPod(
                "default", "false", null, null, null, null, null, null, null, null, null).getItems();
        if (podName == null) {
            return Optional.ofNullable(pods.get(0));
        }
        return pods.stream()
                .filter(pod -> pod.getMetadata().getName().equals(podName))
                .findFirst();
    }

    private CoreV1Api getApi() throws IOException {
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        return new CoreV1Api(client);
    }
}
