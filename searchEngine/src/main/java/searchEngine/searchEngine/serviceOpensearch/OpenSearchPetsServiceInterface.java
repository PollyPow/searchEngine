package searchEngine.searchEngine.serviceOpensearch;

import org.opensearch.client.opensearch.core.SearchResponse;
import searchEngine.searchEngine.modelOpensearch.MyPetsIndex;

public interface OpenSearchPetsServiceInterface {
    SearchResponse<MyPetsIndex> getPetsByName(String name);
}
