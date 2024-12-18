package searchEngine.searchEngine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import searchEngine.searchEngine.model.Opensearch.MyPetsIndex;
import searchEngine.searchEngine.service.Opensearch.implementation.OpenSearchPetsServiceImpl;

import java.util.List;


@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private OpenSearchPetsServiceImpl service;

    @GetMapping("/list")
    public List<MyPetsIndex> getPets(@RequestParam String input) {
        return service.getPetsFromBoolQuery(input);
    }
}
