package searchEngine.searchEngine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import searchEngine.searchEngine.modelSQL.Query;
import searchEngine.searchEngine.serviceSQL.implementation.QuerySQLServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/query")
public class QueryController {
    @Autowired
    private QuerySQLServiceImpl service;

    @GetMapping("/history")
    public List<Query> getHistory() {
        return service.getAllQueries();
    }

}
