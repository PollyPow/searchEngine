package searchEngine.searchEngine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchEngine.searchEngine.modelSQL.Query;
import searchEngine.searchEngine.repository.SQLRepo;

import java.time.LocalDate;
import java.util.List;

@Service
public class QuerySQLService {
    @Autowired
    private SQLRepo sqlRepo;




    private List<Query> sortByDateAndTime(List<Query> queries) {
        return queries.stream().sorted((q1, q2) -> {
            int dateComparison = q1.getDateAndTime().toLocalDate()
                                        .compareTo(q2.getDateAndTime().toLocalDate());

            if(dateComparison != 0) {
                return dateComparison;
            } else {
                return q1.getDateAndTime().toLocalTime()
                                    .compareTo(q2.getDateAndTime().toLocalTime());
            }
        }).toList();
    }

    public void create(Query query) {
        sqlRepo.saveAndFlush(query);
    }

    public void delete(Query query) {
        sqlRepo.delete(query);
    }

    public List<Query> getAllQueries() {
        return sortByDateAndTime(sqlRepo.findAll());
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
