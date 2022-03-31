package dk.commentor.kubernetes.api;

import java.io.IOException;
import java.util.List;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.EventsV1Api;
import io.kubernetes.client.openapi.models.EventsV1Event;
import io.kubernetes.client.openapi.models.EventsV1EventList;
import io.kubernetes.client.util.Config;

public class EventApi {

    public List<EventsV1Event> list() throws Exception {
        EventsV1EventList eventsV1EventList =
            getApi().listNamespacedEvent("default", "true", null, null, null, null, null, null, null, null, null);
        return eventsV1EventList.getItems();
    }

    private EventsV1Api getApi() throws IOException {
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        return new EventsV1Api(client);
    }
}
