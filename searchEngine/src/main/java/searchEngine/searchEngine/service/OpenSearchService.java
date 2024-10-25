package searchEngine.searchEngine.service;

import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.FieldValue;
import org.opensearch.client.opensearch.core.SearchRequest;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import searchEngine.searchEngine.model.MyPetsIndex;

import java.io.IOException;

public class OpenSearchService {

    @Autowired
    private OpenSearchClient openSearchClient;

    public SearchResponse<MyPetsIndex> findPet(String name) {
        SearchRequest request = new SearchRequest.Builder()
                .index("my_pets")
                .query(q -> q.match(m -> m.field("name").query(FieldValue.of(name))))
                .build();

        try {
            return openSearchClient.search(request, MyPetsIndex.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
