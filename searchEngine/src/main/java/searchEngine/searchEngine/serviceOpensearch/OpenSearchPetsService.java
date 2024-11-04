package searchEngine.searchEngine.service;

import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.FieldValue;
import org.opensearch.client.opensearch.core.SearchRequest;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchEngine.searchEngine.modelOpensearch.MyPetsIndex;

import java.io.IOException;

@Service
public class OpenSearchPetsService {


    @Autowired
    private OpenSearchClient openSearchClient;
    private final String index = "my_pets";

    public SearchResponse<MyPetsIndex> getPetsByName(String name) {
        SearchRequest request = new SearchRequest.Builder()
                .index(index)
                .query(q -> q.match(m -> m.field("name").query(FieldValue.of(name))))
                .build();

        try {
            return openSearchClient.search(request, MyPetsIndex.class);
        } catch (IOException e) {
            e.getMessage();
            e.getStackTrace();
            return null;
        }
    }

}
