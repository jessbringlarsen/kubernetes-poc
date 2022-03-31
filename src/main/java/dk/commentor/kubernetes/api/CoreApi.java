package dk.commentor.kubernetes.api;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.util.Config;

public class CoreApi {

    public List<V1Pod> getPods() throws Exception {
        return getCoreApi().listNamespacedPod("default", "true", null, null, null, null, null, null, null, null, null).getItems();
    }

    public Optional<V1Pod> getPodWithName(String pod) throws Exception {
        return getPods().stream()
            .filter(p -> p.getMetadata().getName().equals(pod))
            .findFirst();
    }

    private CoreV1Api getCoreApi() throws IOException {
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        return new CoreV1Api(client);
    }
}
