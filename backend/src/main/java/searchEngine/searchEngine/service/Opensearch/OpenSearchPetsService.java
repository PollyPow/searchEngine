package searchEngine.searchEngine.service.Opensearch;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.opensearch.client.opensearch.core.SearchResponse;
import searchEngine.searchEngine.model.Opensearch.MyPetsIndex;
import searchEngine.searchEngine.model.Opensearch.PetType;

import java.io.IOException;
import java.util.List;

public interface OpenSearchPetsService{
    void savePet(MyPetsIndex index);
    void deletePet(String id);
    void deleteAllPets();
    SearchResponse<MyPetsIndex> getPetsByName(String name);
    List<MyPetsIndex> getPetsByPetType(PetType type);
    SearchResponse<MyPetsIndex> getPetsByParentsName(String name);
    void indexBulkData(List<MyPetsIndex> pets) throws IOException;
}
