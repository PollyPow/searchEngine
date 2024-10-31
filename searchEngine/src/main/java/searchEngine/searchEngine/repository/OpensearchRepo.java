package searchEngine.searchEngine.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import searchEngine.searchEngine.model.MyPetsIndex;

import java.util.List;

public interface OpensearchRepo extends ElasticsearchRepository<MyPetsIndex, String> {
    public List<MyPetsIndex> findByNameLike(String name);
}
