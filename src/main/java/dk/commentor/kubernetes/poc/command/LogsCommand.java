package dk.commentor.kubernetes.poc.command;


import java.io.InputStream;
import java.util.concurrent.Callable;

import io.kubernetes.client.PodLogs;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.Streams;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;

@Component
@Command(name = "logs", version = "checksum 4.0", description = "Prints logs from a pod")
public class LogsCommand implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        CoreV1Api coreApi = new CoreV1Api(client);

        PodLogs logs = new PodLogs();
        V1Pod pod = coreApi.listNamespacedPod(
                    "default", "false", null, null, null, null, null, null, null, null, null)
                .getItems()
                .get(0);

        InputStream is = logs.streamNamespacedPodLog(pod);
        Streams.copy(is, System.out);
        return 0;
    }
}
