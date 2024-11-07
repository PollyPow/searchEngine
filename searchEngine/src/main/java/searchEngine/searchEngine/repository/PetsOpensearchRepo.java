package searchEngine.searchEngine.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import searchEngine.searchEngine.modelOpensearch.MyPetsIndex;

public interface PetsOpensearchRepo extends ElasticsearchRepository<MyPetsIndex, String> {
}
