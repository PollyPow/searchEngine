package searchEngine.searchEngine.service.History;

import searchEngine.searchEngine.model.History.Query;

import java.time.LocalDate;
import java.util.List;

public interface HistoryService {
    default List<Query> sortByDateAndTime(List<Query> queries) {
        return queries.stream().sorted((q1, q2) -> {
            int dateComparison = q1.getDateAndTime().toLocalDate()
                    .compareTo(q2.getDateAndTime().toLocalDate());

            if (dateComparison != 0) {
                return dateComparison;
            } else {
                return q1.getDateAndTime().toLocalTime()
                        .compareTo(q2.getDateAndTime().toLocalTime());
            }
        }).toList();
    }

    void create(Query query);

    void delete(Query query);

    List<Query> getAllQueries();

    List<Query> getQueriesByText(String text);

    List<Query> getQueriesDistinctByText();

    List<Query> getQueriesByDate(LocalDate date);

    List<Query> getQueriesBySortField(String sortField);

    List<Query> getAllQueriesFromToday();

    List<Query> getAllQueriesFromLastSevenDays();
}
