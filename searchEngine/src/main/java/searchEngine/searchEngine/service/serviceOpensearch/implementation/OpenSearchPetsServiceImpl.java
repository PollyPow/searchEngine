package searchEngine.searchEngine.service.serviceOpensearch.implementation;

import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.FieldValue;
import org.opensearch.client.opensearch.core.SearchRequest;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchEngine.searchEngine.model.Opensearch.MyPetsIndex;
import searchEngine.searchEngine.model.Opensearch.PetType;
import searchEngine.searchEngine.repository.PetsOpensearchRepo;
import searchEngine.searchEngine.service.serviceOpensearch.OpenSearchPetsService;

import java.io.IOException;

@Service
public class OpenSearchPetsServiceImpl implements OpenSearchPetsService {

    @Autowired
    private OpenSearchClient openSearchClient;

    @Autowired
    private PetsOpensearchRepo repo;

    private final String index = "my_pets";






    @Override
    public void savePet(MyPetsIndex index) {
        repo.save(index);
    }

    @Override
    public void deletePet(String id) {
        repo.deleteById(id);
    }

    @Override
    public void deleteAllPets() {
        repo.deleteAll();
    }

    @Override
    public SearchResponse<MyPetsIndex> getPetsByName(String name) {
        int resultSize = 1000;

        SearchRequest request = new SearchRequest.Builder()
                .index(index)
                .query(q -> q.match(m -> m.field("name").query(FieldValue.of(name))))
                .size(resultSize)
                .build();

        try {
            return openSearchClient.search(request, MyPetsIndex.class);
        } catch (IOException e) {
            e.getMessage();
            e.getStackTrace();
            return null;
        }
    }

    @Override
    public SearchResponse<MyPetsIndex> getPetsByPetType(PetType type) {
        int resultSize = 1000;

        SearchRequest request = new SearchRequest.Builder()
                .index(index)
                .query(q -> q.match(m -> m.field("petType").query(FieldValue.of(type.toString()))))
                .size(resultSize)
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
