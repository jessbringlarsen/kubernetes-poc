package dk.commentor.kubernetes.api;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import io.kubernetes.client.openapi.apis.BatchV1Api;
import io.kubernetes.client.openapi.models.V1Job;
import io.kubernetes.client.openapi.models.V1JobList;
import io.kubernetes.client.util.Config;

public class BatchApi {

    public V1JobList getJobList() throws Exception {
        return getAPI().listNamespacedJob("default", "true", null, null, null, null, null, null, null, null, null);
    }

    public List<V1Job> getJobItems() throws Exception {
        return getJobList().getItems();
    }

    public Optional<V1Job> getJobWithName(String name) throws Exception {
        return getJobItems().stream()
            .filter(v1Job -> v1Job.getMetadata().getName().equals(name))
            .findAny();
    }

    private BatchV1Api getAPI() throws IOException {
        return new BatchV1Api(Config.defaultClient());
    }
}
