package searchEngine.searchEngine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SQLRepo extends JpaRepository<searchEngine.searchEngine.model.Query, Long> {
    List<searchEngine.searchEngine.model.Query> findByText(String text);

    @Query("SELECT q FROM Query q WHERE q.id IN (SELECT MIN(q2.id) FROM Query q2 GROUP BY q2.text)")
    List<searchEngine.searchEngine.model.Query> findDistinctByText();

    List<searchEngine.searchEngine.model.Query> findByDate(LocalDate date);
    List<searchEngine.searchEngine.model.Query> findBySortField(String sortField);
    List<searchEngine.searchEngine.model.Query> findByDateAfter(LocalDate date);
}
