package searchEngine.searchEngine.service;

import org.springframework.stereotype.Service;
import searchEngine.searchEngine.model.Query;
import searchEngine.searchEngine.repository.SQLRepo;

import java.time.LocalDate;
import java.util.List;

@Service
public class QuerySQLService {
    private final SQLRepo sqlRepo;

    public QuerySQLService(SQLRepo sqlRepo) {
        this.sqlRepo = sqlRepo;
    }

    public Query create(Query query) {
        return sqlRepo.saveAndFlush(query);
    }

    public void delete(Query query) {
        sqlRepo.delete(query);
    }

    public List<Query> getAllQueries() {
        return sqlRepo.findAll();
    }

    public List<Query> getQueriesByText(String text) {
        return sqlRepo.findByText(text);
    }

    public List<Query> getQueriesDistinctByText(String text) {
        return sqlRepo.findDistinctByText(text);
    }

    public List<Query> getQueriesByDate(LocalDate date) {
        return sqlRepo.findByDate(date);
    }

    public List<Query> getQueriesBySortField(String sortField) {
        return sqlRepo.findBySortField(sortField);
    }

    public List<Query> getAllQueriesFromToday() {
        return sqlRepo.findDistinctByDateAfter(LocalDate.now());
    }

    public List<Query> getAllQueriesFromLastWeek() {
        return sqlRepo.findDistinctByDateAfter(LocalDate.now()
                                                .minusWeeks(1));
    }
 }
