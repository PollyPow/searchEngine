package searchEngine.searchEngine.service.Opensearch;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.Refresh;
import org.opensearch.client.opensearch.core.IndexRequest;
import org.opensearch.client.opensearch.core.IndexResponse;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch.core.search.Hit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import searchEngine.searchEngine.model.Opensearch.MyPetsIndex;
import searchEngine.searchEngine.model.Opensearch.PetType;
import searchEngine.searchEngine.repository.PetsOpensearchRepo;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class OpenSearchPetsServiceImplTests {

    @Autowired
    private OpenSearchClient client;

    @Autowired
    private PetsOpensearchRepo repo;

    @Autowired
    private OpenSearchPetsService service;




    @AfterEach
    public void cleanUp() {
        repo.deleteAll();
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
            IndexRequest<MyPetsIndex> request = IndexRequest.of(i -> i.index(index).id(null).document(pet).refresh(Refresh.True));
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
        ArrayList<String> parents = new ArrayList<String>();
        parents.add("Alice");
        parents.add("Bob");
        ArrayList<String> illnesses = new ArrayList<String>();
        PetType type = PetType.FISH;
        MyPetsIndex pet = new MyPetsIndex("Goldie", 1, PetType.FISH, "Goldfish", parents, illnesses, null, "Tetra");
        int total = 20;
        for(int j = 0; j < total; ++j) {
            IndexRequest<MyPetsIndex> request = IndexRequest.of(i -> i.index(index).id(null).document(pet).refresh(Refresh.True));
            Assertions.assertDoesNotThrow(() -> { client.index(request); });
        }

        SearchResponse<MyPetsIndex> pets = service.getPetsByPetType(type);
        List<MyPetsIndex> listOfPets = pets.hits().hits().stream().map(Hit::source).toList();

        Assertions.assertNotNull(pets);
        Assertions.assertEquals(total, pets.hits().hits().size(), String.format("The amount of found pets should be %d", total));
        Assertions.assertTrue(listOfPets.stream().allMatch(p -> p.getPetType().equals(type)));
    }



    @Test
    public void OpenSearchPetsServiceTest_deleteAll_deleteAllPets() {
        service.deleteAllPets();

        Assertions.assertFalse(repo.findAll().iterator().hasNext());
    }

    @Test
    public void OpenSearchPetsServiceTest_findPetsByParentsName() {
        String index = "my_pets";
        ArrayList<String> parents = new ArrayList<String>();
        parents.add("Alice");
        parents.add("Bob");
        ArrayList<String> illnesses = new ArrayList<String>();
        PetType type = PetType.HAMSTER;
        MyPetsIndex pet1 = new MyPetsIndex("Coco", 1, PetType.HAMSTER, "European", parents, illnesses, null, "Tetra");

        //indexing the 1st pet
        IndexRequest<MyPetsIndex> request1 = IndexRequest.of(i -> i.index(index).id(pet1.getId()).document(pet1).refresh(Refresh.True));
        Assertions.assertDoesNotThrow(() -> { client.index(request1); });

        //indexing the 2nd pet
        parents.removeFirst();
        parents.add("Jane");
        MyPetsIndex pet2 = new MyPetsIndex("Coco", 1, PetType.HAMSTER, "European", parents, illnesses, null, "Tetra");
        IndexRequest<MyPetsIndex> request2 = IndexRequest.of(i -> i.index(index).id(pet2.getId()).document(pet2).refresh(Refresh.True));
        Assertions.assertDoesNotThrow(() -> { client.index(request2); });



        SearchResponse<MyPetsIndex> pets = service.getPetsByParentsName("Jane");
        List<MyPetsIndex> listOfPets = pets.hits().hits().stream().map(Hit::source).toList();



        Assertions.assertNotNull(pets);
        Assertions.assertEquals(1, pets.hits().hits().size(), String.format("The amount of found pets should be %d", 1));
        Assertions.assertTrue(listOfPets.stream().allMatch(p -> p.getParentsNames().contains("Jane")));
    }

    @Test
    public void OpenSearchPetsServiceTest_DeletePet_DeletePetWithGivenId() {
        String index = "my_pets";
        ArrayList<String> parents = new ArrayList<String>();
        parents.add("Alice");
        parents.add("Bob");
        ArrayList<String> illnesses = new ArrayList<String>();
        PetType type = PetType.GUINEA_PIG;
        MyPetsIndex pet = new MyPetsIndex("Shorty", 1, PetType.GUINEA_PIG, "European", parents, illnesses, null, "Tetra");

        service.savePet(pet);
        //asserting that pet is added
        Assertions.assertTrue(repo.existsById(pet.getId()));

        service.deletePet(pet.getId());

        Assertions.assertFalse(repo.existsById(pet.getId()));
    }

}

