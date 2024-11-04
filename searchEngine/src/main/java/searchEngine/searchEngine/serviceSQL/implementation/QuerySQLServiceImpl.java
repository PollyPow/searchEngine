package searchEngine.searchEngine.serviceSQL.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchEngine.searchEngine.modelSQL.Query;
import searchEngine.searchEngine.repository.SQLRepo;
import searchEngine.searchEngine.serviceSQL.QuerySQLServiceInterface;

import java.time.LocalDate;
import java.util.List;

@Service
public class QuerySQLServiceImpl implements QuerySQLServiceInterface {
    @Autowired
    private SQLRepo sqlRepo;


    @Override
    public void create(Query query) {
        sqlRepo.saveAndFlush(query);
    }

    @Override
    public void delete(Query query) {
        sqlRepo.delete(query);
    }

    @Override
    public List<Query> getAllQueries() {
        return sortByDateAndTime(sqlRepo.findAll());
    }

    @Override
    public List<Query> getQueriesByText(String text) {
        return sqlRepo.findByText(text);
    }

    @Override
    public List<Query> getQueriesDistinctByText() {
        return sqlRepo.findDistinctByText();
    }

    @Override
    public List<Query> getQueriesByDate(LocalDate date) {
        return sqlRepo.findByDate(date);
    }

    @Override
    public List<Query> getQueriesBySortField(String sortField) {
        return sqlRepo.findBySortField(sortField);
    }

    @Override
    public List<Query> getAllQueriesFromToday() {
        return sqlRepo.findByDate(LocalDate.now());
    }

    @Override
    public List<Query> getAllQueriesFromLastSevenDays() {
        int days = 7;

        return sqlRepo.findByDateAfter(LocalDate.now()
                                                .minusDays(days + 1));
    }
 }
