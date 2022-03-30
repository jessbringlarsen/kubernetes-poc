package dk.commentor.kubernetes.poc.command;


import picocli.CommandLine.Command;

import java.util.concurrent.Callable;


@Command(name = "pods", description = "Display pods created", mixinStandardHelpOptions = true)
public class PodsCommand implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        /*ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        CoreV1Api coreApi = new CoreV1Api(client);

        PodLogs logs = new PodLogs();
        V1Pod pod = coreApi.listNamespacedPod(
                    "default", "false", null, null, null, null, null, null, null, null, null)
                .getItems()
                .get(0);

        InputStream is = logs.streamNamespacedPodLog(pod);
        Streams.copy(is, System.out);*/
        System.out.println("pods");
        return 0;
    }
}
