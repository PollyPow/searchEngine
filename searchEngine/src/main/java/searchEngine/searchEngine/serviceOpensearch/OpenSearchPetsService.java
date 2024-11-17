package searchEngine.searchEngine.serviceOpensearch;

import org.opensearch.client.opensearch.core.SearchResponse;
import searchEngine.searchEngine.model.Opensearch.MyPetsIndex;
import searchEngine.searchEngine.model.Opensearch.PetType;

public interface OpenSearchPetsService{
    void savePet(MyPetsIndex index);
    void deletePet(String id);
    void deleteAllPets();
    SearchResponse<MyPetsIndex> getPetsByName(String name);
    SearchResponse<MyPetsIndex> getPetsByPetType(PetType type);
}
