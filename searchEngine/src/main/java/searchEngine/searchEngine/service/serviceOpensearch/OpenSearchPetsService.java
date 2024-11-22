package searchEngine.searchEngine.service.serviceOpensearch;

import org.opensearch.client.opensearch.core.SearchResponse;
import org.springframework.data.elasticsearch.annotations.Query;
import searchEngine.searchEngine.model.Opensearch.MyPetsIndex;
import searchEngine.searchEngine.model.Opensearch.PetType;

public interface OpenSearchPetsService{
    void savePet(MyPetsIndex index);
    void deletePet(String id);
    void deleteAllPets();
    SearchResponse<MyPetsIndex> getPetsByName(String name);
    SearchResponse<MyPetsIndex> getPetsByPetType(PetType type);
    SearchResponse<MyPetsIndex> getPetsByParentsName(String name);
}
