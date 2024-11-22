package searchEngine.searchEngine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import searchEngine.searchEngine.model.History.Query;
import searchEngine.searchEngine.service.History.implementation.HistoryServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/query")
public class QueryController {
    @Autowired
    private HistoryServiceImpl service;

    @GetMapping("/history")
    public List<Query> getHistory() {
        return service.getAllQueries();
    }

}
