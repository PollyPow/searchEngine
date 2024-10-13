package searchEngine.searchEngine.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/query")
public class QueryController {

    @GetMapping("/list")
    public void getResponse() {
        // TODO implement output building for frontend
    }

}
