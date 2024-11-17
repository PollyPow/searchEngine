package searchEngine.searchEngine.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import searchEngine.searchEngine.model.Opensearch.MyPetsIndex;

public interface PetsOpensearchRepo extends ElasticsearchRepository<MyPetsIndex, String> {
}
