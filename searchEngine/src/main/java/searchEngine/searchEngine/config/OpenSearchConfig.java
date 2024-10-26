package searchEngine.searchEngine.config;

import org.apache.http.HttpHost;
import org.opensearch.client.RestClient;
import org.opensearch.client.json.jackson.JacksonJsonpMapper;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.transport.rest_client.RestClientTransport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenSearchConfig {

    @Value("${opensearch.url}")
    private String url;

    @Value("${opensearch.username}")
    private String username;

    @Value("${opensearch.password}")
    private String password;

    @Value("${opensearch.indexes.my_pets}")
    private String petsIndex;

    @Value("${opensearch.indexes.zoo_animals}")
    private String animalsIndex;

    @Bean
    public OpenSearchClient openSearchClient() {
        RestClient restClient = RestClient.builder(HttpHost.create(url)).build();
        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        return new OpenSearchClient(transport);
    }
}
