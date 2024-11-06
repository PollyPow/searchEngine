package searchEngine.searchEngine.config;

import org.opensearch.data.client.osc.OpenSearchConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;


@Configuration
public class OpenSearchConfig extends OpenSearchConfiguration{

    @Value("${opensearch.hostAndPort}")
    private String hostAndPort;

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder().connectedTo(hostAndPort).build();
    }
}
