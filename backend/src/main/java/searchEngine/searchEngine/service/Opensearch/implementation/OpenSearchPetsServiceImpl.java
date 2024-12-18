package searchEngine.searchEngine.service.Opensearch.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.json.JsonData;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.FieldValue;
import org.opensearch.client.opensearch._types.aggregations.RangeAggregate;
import org.opensearch.client.opensearch._types.query_dsl.*;
import org.opensearch.client.opensearch.core.IndexRequest;
import org.opensearch.client.opensearch.core.SearchRequest;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch.core.search.Hit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchEngine.searchEngine.model.History.Query;
import searchEngine.searchEngine.model.Opensearch.MyPetsIndex;
import searchEngine.searchEngine.model.Opensearch.PetType;
import searchEngine.searchEngine.repository.PetsOpensearchRepo;
import searchEngine.searchEngine.service.History.HistoryService;
import searchEngine.searchEngine.service.Opensearch.OpenSearchPetsService;

import java.io.IOException;
import java.util.List;

@Service
public class OpenSearchPetsServiceImpl implements OpenSearchPetsService {

    @Autowired
    private OpenSearchClient openSearchClient;

    @Autowired
    private PetsOpensearchRepo repo;

    @Autowired
    private HistoryService historyService;

    private final String index = "my_pets";






    @Override
    public void savePet(MyPetsIndex index) {
        repo.save(index);
    }

    @Override
    public void deletePet(String id) {
        repo.deleteById(id);
    }

    @Override
    public void deleteAllPets() {
        repo.deleteAll();
    }

    @Override
    public SearchResponse<MyPetsIndex> getPetsByName(String name) {
        int resultSize = 1000;

        SearchRequest request = new SearchRequest.Builder()
                .index(index)
                .query(q -> q.match(m -> m.field("name").query(FieldValue.of(name))))
                .size(resultSize)
                .build();

        historyService.create(new Query(name));

        try {
            return openSearchClient.search(request, MyPetsIndex.class);
        } catch (IOException e) {
            e.getMessage();
            e.getStackTrace();
            return null;
        }
    }

    @Override
    public List<MyPetsIndex> getPetsByPetType(PetType type) {
        int resultSize = 1000;

        SearchRequest request = new SearchRequest.Builder()
                .index(index)
                .query(q -> q.match(m -> m.field("petType").query(FieldValue.of(type.toString()))))
                .size(resultSize)
                .build();

        historyService.create(new Query(type.toString()));

        try {
            SearchResponse<MyPetsIndex> response = openSearchClient.search(request, MyPetsIndex.class);

            return response.hits().hits().stream().map(Hit::source).toList();
        } catch (IOException e) {
            e.getMessage();
            e.getStackTrace();
            return null;
        }
    }

    @Override
    public SearchResponse<MyPetsIndex> getPetsByParentsName(String name) {
        int resultSize = 1000;

        QueryStringQuery queryString = new QueryStringQuery.Builder()
                .query("parentsNames:" + name)
                .build();

        SearchRequest request = new SearchRequest.Builder()
                .index(index)
                .query(queryString.toQuery())
                .size(resultSize)
                .build();

        historyService.create(new Query(name));

        try {
            return openSearchClient.search(request, MyPetsIndex.class);
        } catch (IOException e) {
            e.getMessage();
            e.getStackTrace();
            return null;
        }
    }

    @Override
    public void indexBulkData(List<MyPetsIndex> pets) throws IOException {
        for(MyPetsIndex pet : pets) {
            IndexRequest<MyPetsIndex> request = IndexRequest.of(i -> i.index("my_pets")
                                                                        .id(null)
                                                                        .document(pet));
            openSearchClient.index(request);
        }
    }

    @Override
    public List<MyPetsIndex> getPetsFromBoolQuery(String input) {
        int resultSize = 1000;
        String queryStringQueryInput = "";
        String[] rangeQueryInput = new String[] {"age", "0", "100"};

        String[] inputParts = input.split(" ");
        for(String part : inputParts) {
            if(part.startsWith("age")) {
                rangeQueryInput = part.split(":");
            }
            else {
                queryStringQueryInput += part;
            }
        }

        if(queryStringQueryInput.isEmpty()) {
            queryStringQueryInput = "*";
        }

        QueryStringQuery queryStringQuery = new QueryStringQuery.Builder()
                .query("*:" + queryStringQueryInput)
                .build();

        RangeQuery rangeQuery = new RangeQuery.Builder()
                    .field("ageYears")
                    .gte(JsonData.of(rangeQueryInput[1]))
                    .lte(JsonData.of(rangeQueryInput[2]))
                    .build();

        BoolQuery boolQuery = new BoolQuery.Builder()
                .must(queryStringQuery.toQuery())
                .filter(rangeQuery.toQuery())
                .build();

        SearchRequest request = new SearchRequest.Builder()
                .index(index)
                .query(boolQuery.toQuery())
                .size(resultSize)
                .build();

        historyService.create(new Query(input));

        try {
            SearchResponse<MyPetsIndex> response = openSearchClient.search(request, MyPetsIndex.class);

            return response.hits().hits().stream().map(Hit::source).toList();
        } catch (IOException e) {
            e.getMessage();
            e.getStackTrace();
            return null;
        }
    }

}
