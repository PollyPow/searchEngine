package searchEngine.searchEngine.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.core.IndexRequest;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch.core.search.Hit;
import org.opensearch.spring.boot.autoconfigure.test.DataOpenSearchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import searchEngine.searchEngine.model.MyPetsIndex;

import java.util.List;

@SpringBootTest
public class OpenSearchPetsServiceTests {

    @Autowired
    private OpenSearchClient client;

    @Autowired
    private OpenSearchPetsService service;

    @Test
    public void OpenSearchService_GetPetsByName_FindAllPetsByName () {
        String index = "my_pets";
        String testName = "TestName";
        MyPetsIndex pet = new MyPetsIndex();
        pet.setName(testName);
        int total = 10;
        for(int j = 0; j < total; ++j) {
            IndexRequest<MyPetsIndex> request = IndexRequest.of(i -> i.index(index)
                                                                        .id(null)
                                                                        .document(pet));
            Assertions.assertDoesNotThrow(() -> { client.index(request); });
        }



        SearchResponse<MyPetsIndex> pets = service.getPetsByName(testName);
        List<MyPetsIndex> listOfPets = pets.hits().hits().stream().map(Hit::source).toList();



        Assertions.assertNotNull(pets);
        Assertions.assertEquals(total, pets.hits().hits().size(), String.format("The amount of found pets should be %d", total));
        Assertions.assertTrue(listOfPets.stream().allMatch(p -> p.getName().equals(testName)));
    }
}
