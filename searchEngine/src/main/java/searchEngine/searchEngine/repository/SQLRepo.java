package searchEngine.searchEngine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import searchEngine.searchEngine.model.Query;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SQLRepo extends JpaRepository<Query, Long> {
    List<Query> findByText(String text);
    List<Query> findDistinctByText(String text);
    List<Query> findByDate(LocalDate date);
    List<Query> findBySortField(String sortField);
    List<Query> findDistinctByDateAfter(LocalDate date);
}
