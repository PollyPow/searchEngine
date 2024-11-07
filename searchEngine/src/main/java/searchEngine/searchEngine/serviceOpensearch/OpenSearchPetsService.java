package searchEngine.searchEngine.serviceOpensearch;

import org.opensearch.client.opensearch.core.SearchResponse;
import searchEngine.searchEngine.modelOpensearch.MyPetsIndex;

public interface OpenSearchPetsService<MyPetsIndex> extends OpensearchService<MyPetsIndex>{
    SearchResponse<MyPetsIndex> getPetsByName(String name);
}
