package searchEngine.searchEngine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import searchEngine.searchEngine.model.Query;
import searchEngine.searchEngine.model.SortDirection;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SQLRepo extends JpaRepository<Query, Long> {
    List<Query> findByText(String text);
    List<Query> findDistinctByText(String text);
    List<Query> findDistinctByTextAndSortField(String text, String sortField);
    List<Query> findDistinctByTextAndSortFieldAndSortDirection(String text, String sortField, SortDirection sortDirection);
    List<Query> findDistinctByTextAndDate(String text, LocalDate date);
}
