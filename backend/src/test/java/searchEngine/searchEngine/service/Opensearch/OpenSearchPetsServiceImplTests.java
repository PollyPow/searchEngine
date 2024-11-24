package searchEngine.searchEngine.service.Opensearch;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.Refresh;
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
import java.util.UUID;

@SpringBootTest
public class OpenSearchPetsServiceImplTests {

    @Autowired
    private OpenSearchClient client;
    @Autowired
    private PetsOpensearchRepo repo;
    @Autowired
    private OpenSearchPetsService service;
    private ArrayList<String> testIds;
    private final int total = 10;

    @BeforeEach
    public void setUp() {
        testIds = new ArrayList<>();
        for(int i = 0; i < total; ++i) {
            testIds.add(UUID.randomUUID().toString());
        }
    }

    @AfterEach
    public void cleanUp() {
        for(int i = 0; i < total; ++i) {
            repo.deleteById(testIds.get(i));
        }
    }

    @Test
    public void OpenSearchPetsServiceTest_SavePet_SavesNewPet() {
        ArrayList<String> parents = new ArrayList<>();
        parents.add("Jane");
        parents.add("John");
        ArrayList<String> illnesses = new ArrayList<>();
        MyPetsIndex pet = new MyPetsIndex("Buddy", 5, PetType.DOG, "Golden Retriever", parents, illnesses, "Tom Cruise", "Royal Canin");

        service.savePet(pet);

        Assertions.assertTrue(repo.existsById(pet.getId()));

        repo.delete(pet);
    }

    @Test
    public void OpenSearchPetsServiceTest_GetPetsByName_FindAllPetsByName () {
        String index = "my_pets";
        ArrayList<String> parents = new ArrayList<>();
        parents.add("Alice");
        parents.add("Bob");
        ArrayList<String> illnesses = new ArrayList<>();
        String name = "Fluffy";
        MyPetsIndex pet = new MyPetsIndex(name, 3, PetType.CAT, "Maine Coon", parents, illnesses, "Anne Hathaway", "Whiskas");
        for(int j = 0; j < total; ++j) {
            String id = testIds.get(j);
            IndexRequest<MyPetsIndex> request = IndexRequest.of(i -> i.index(index)
                                                                        .id(id)
                                                                        .document(pet)
                                                                        .refresh(Refresh.True));
            Assertions.assertDoesNotThrow(() -> { client.index(request); });
        }

        SearchResponse<MyPetsIndex> pets = service.getPetsByName(name);
        List<MyPetsIndex> listOfPets = pets.hits().hits().stream().map(Hit::source).toList();

        Assertions.assertNotNull(pets);
        Assertions.assertEquals(total, pets.hits().hits().size(), String.format("The amount of found pets should be %d", total));
        Assertions.assertTrue(listOfPets.stream().allMatch(p -> p.getName().equals(name)));
    }

    @Test
    public void OpenSearchPetsServiceTest_getPetsByPetType_FindAllPetsWithGivenType() {
        String index = "my_pets";
        ArrayList<String> parents = new ArrayList<>();
        parents.add("Alice");
        parents.add("Bob");
        ArrayList<String> illnesses = new ArrayList<>();
        PetType type = PetType.FISH;
        MyPetsIndex pet = new MyPetsIndex("Goldie", 1, PetType.FISH, "Goldfish", parents, illnesses, null, "Tetra");
        for(int j = 0; j < total; ++j) {
            String id = testIds.get(j);
            IndexRequest<MyPetsIndex> request = IndexRequest.of(i -> i.index(index)
                                                                        .id(id)
                                                                        .document(pet)
                                                                        .refresh(Refresh.True));
            Assertions.assertDoesNotThrow(() -> { client.index(request); });
        }

        List<MyPetsIndex> pets = service.getPetsByPetType(type);

        Assertions.assertNotNull(pets);
        Assertions.assertEquals(total, pets.size(), String.format("The amount of found pets should be %d", total));
        Assertions.assertTrue(pets.stream().allMatch(p -> p.getPetType().equals(type)));
    }



    @Test
    public void OpenSearchPetsServiceTest_deleteAll_deleteAllPets() {
        service.deleteAllPets();

        Assertions.assertFalse(repo.findAll().iterator().hasNext());
    }

    @Test
    public void OpenSearchPetsServiceTest_findPetsByParentsName() {
        String index = "my_pets";
        ArrayList<String> parents = new ArrayList<>();
        parents.add("Alice");
        parents.add("Bob");
        ArrayList<String> illnesses = new ArrayList<>();
        MyPetsIndex pet1 = new MyPetsIndex("Coco", 1, PetType.HAMSTER, "European", parents, illnesses, null, "Tetra");

        //indexing the 1st pet
        String id1 = testIds.get(0);
        IndexRequest<MyPetsIndex> request1 = IndexRequest.of(i -> i.index(index)
                                                                    .id(id1)
                                                                    .document(pet1)
                                                                    .refresh(Refresh.True));
        Assertions.assertDoesNotThrow(() -> { client.index(request1); });

        //indexing the 2nd pet
        parents.removeFirst();
        parents.add("Jane");
        MyPetsIndex pet2 = new MyPetsIndex("Coco", 1, PetType.HAMSTER, "European", parents, illnesses, null, "Tetra");
        String id2 = testIds.get(1);
        IndexRequest<MyPetsIndex> request2 = IndexRequest.of(i -> i.index(index)
                                                                    .id(id2)
                                                                    .document(pet2)
                                                                    .refresh(Refresh.True));
        Assertions.assertDoesNotThrow(() -> { client.index(request2); });

        SearchResponse<MyPetsIndex> pets = service.getPetsByParentsName("Jane");
        List<MyPetsIndex> listOfPets = pets.hits().hits().stream().map(Hit::source).toList();

        Assertions.assertNotNull(pets);
        Assertions.assertEquals(1, pets.hits().hits().size(), String.format("The amount of found pets should be %d", 1));
        Assertions.assertTrue(listOfPets.stream().allMatch(p -> p.getParentsNames().contains("Jane")));
    }

    @Test
    public void OpenSearchPetsServiceTest_DeletePet_DeletePetWithGivenId() {
        ArrayList<String> parents = new ArrayList<>();
        parents.add("Alice");
        parents.add("Bob");
        ArrayList<String> illnesses = new ArrayList<>();
        MyPetsIndex pet = new MyPetsIndex("Shorty", 1, PetType.GUINEA_PIG, "European", parents, illnesses, null, "Tetra");

        service.savePet(pet);
        //asserting that pet is added
        Assertions.assertTrue(repo.existsById(pet.getId()));

        service.deletePet(pet.getId());

        Assertions.assertFalse(repo.existsById(pet.getId()));
    }

}

