package searchEngine.searchEngine.serviceOpensearch;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.core.IndexRequest;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch.core.search.Hit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import searchEngine.searchEngine.model.Opensearch.MyPetsIndex;
import searchEngine.searchEngine.model.Opensearch.PetType;
import searchEngine.searchEngine.repository.PetsOpensearchRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
public class OpenSearchPetsServiceImplTests {

    @Autowired
    private OpenSearchClient client;

    @Autowired
    private PetsOpensearchRepo repo;

    @Autowired
    private OpenSearchPetsService service;





    @Test
    public void OpenSearchPetsServiceTest_SavePet_SavesNewPet() {
        ArrayList<String> parents = new ArrayList<>();
        parents.add("Jane");
        parents.add("John");
        ArrayList<String> illnesses = new ArrayList<>();
        MyPetsIndex pet = new MyPetsIndex("Buddy", 5, PetType.DOG, "Golden Retriever", parents, illnesses, "Tom Cruise", "Royal Canin");

        service.savePet(pet);

        Assertions.assertTrue(repo.existsById(pet.getId()));
    }

    @Test
    public void OpenSearchPetsServiceTest_GetPetsByName_FindAllPetsByName () {
        String index = "my_pets";
        ArrayList<String> parents = new ArrayList<String>();
        parents.add("Alice");
        parents.add("Bob");
        ArrayList<String> illnesses = new ArrayList<String>();
        String name = "Fluffy";
        MyPetsIndex pet = new MyPetsIndex(name, 3, PetType.CAT, "Maine Coon", parents, illnesses, "Anne Hathaway", "Whiskas");
        int total = 20;
        for(int j = 0; j < total; ++j) {
            IndexRequest<MyPetsIndex> request = IndexRequest.of(i -> i.index(index).id(null).document(pet));
            Assertions.assertDoesNotThrow(() -> { client.index(request); });
        }



        SearchResponse<MyPetsIndex> pets = service.getPetsByName(name);
        List<MyPetsIndex> listOfPets = pets.hits().hits().stream().map(Hit::source).toList();




        Assertions.assertNotNull(pets);
        Assertions.assertEquals(total, pets.hits().hits().size(), String.format("The amount of found pets should be %d", total));
        Assertions.assertTrue(listOfPets.stream().allMatch(p -> p.getName().equals(name)));
    }

    /*
    @Test
    public void OpenSearchPetsServiceTest_deleteAll_deleteAllPets() {
        service.deleteAllPets();

        Assertions.assertFalse(repo.findAll().iterator().hasNext());
    }
    */
}

