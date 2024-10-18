package searchEngine.searchEngine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchEngine.searchEngine.model.Query;
import searchEngine.searchEngine.repository.SQLRepo;

import java.time.LocalDate;
import java.util.List;

@Service
public class QuerySQLService {
    @Autowired
    private SQLRepo sqlRepo;

    public void create(Query query) {
        sqlRepo.saveAndFlush(query);
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

    public List<Query> getQueriesDistinctByText() {
        return sqlRepo.findDistinctByText();
    }

    public List<Query> getQueriesByDate(LocalDate date) {
        return sqlRepo.findByDate(date);
    }

    public List<Query> getQueriesBySortField(String sortField) {
        return sqlRepo.findBySortField(sortField);
    }

    public List<Query> getAllQueriesFromToday() {
        return sqlRepo.findByDate(LocalDate.now());
    }

    public List<Query> getAllQueriesFromLastSevenDays() {
        int days = 7;

        return sqlRepo.findByDateAfter(LocalDate.now()
                                                .minusDays(days + 1));
    }
 }
