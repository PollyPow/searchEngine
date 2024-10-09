package searchEngine.searchEngine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import searchEngine.searchEngine.model.Query;

@Repository
public interface SQLRepo extends JpaRepository<Query, Long> {
}
