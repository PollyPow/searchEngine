package searchEngine.searchEngine.controller;

import org.opensearch.client.opensearch.core.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import searchEngine.searchEngine.model.Opensearch.MyPetsIndex;
import searchEngine.searchEngine.model.Opensearch.PetType;
import searchEngine.searchEngine.service.Opensearch.implementation.OpenSearchPetsServiceImpl;


@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private OpenSearchPetsServiceImpl service;

    @GetMapping("/list")
    public SearchResponse<MyPetsIndex> getPets(PetType type) {
        return service.getPetsByPetType(type);
    }
}
