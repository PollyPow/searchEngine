package searchEngine.searchEngine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SQLRepo extends JpaRepository<searchEngine.searchEngine.modelSQL.Query, Long> {
    List<searchEngine.searchEngine.modelSQL.Query> findByText(String text);

    @Query("SELECT q FROM Query q WHERE q.id IN (SELECT MIN(q2.id) FROM Query q2 GROUP BY q2.text)")
    List<searchEngine.searchEngine.modelSQL.Query> findDistinctByText();

    @Query("SELECT q FROM Query q WHERE FUNCTION('DATE', q.dateAndTime) = :date")
    List<searchEngine.searchEngine.modelSQL.Query> findByDate(@Param("date") LocalDate date);

    List<searchEngine.searchEngine.modelSQL.Query> findBySortField(String sortField);

    @Query("SELECT q FROM Query q WHERE FUNCTION('DATE', q.dateAndTime) > :date")
    List<searchEngine.searchEngine.modelSQL.Query> findByDateAfter(@Param("date") LocalDate date);
}
