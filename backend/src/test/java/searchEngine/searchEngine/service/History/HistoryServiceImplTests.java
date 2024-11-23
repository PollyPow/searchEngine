package searchEngine.searchEngine.service.History;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import searchEngine.searchEngine.model.History.Query;
import searchEngine.searchEngine.repository.SQLRepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
public class HistoryServiceImplTests {
    @Autowired
    private SQLRepo sqlRepo;
    @Autowired
    private searchEngine.searchEngine.service.History.HistoryService service;
    private final int total = 10;

    @Test
    @Transactional
    public void HistoryService_Create_SaveNewQueryToDB() {
        Query query = new Query("dog", "pet type");

        service.create(query);

        Assertions.assertTrue(sqlRepo.existsById(query.getId()));
    }

    @Test
    @Transactional
    public void HistoryService_Delete_DeleteQueryFromDB() {
        Query query = new Query("dog", "pet type");
        sqlRepo.saveAndFlush(query);

        service.delete(query);

        Assertions.assertFalse(sqlRepo.existsById(query.getId()));
    }

    @Test
    @Transactional
    public void HistoryService_GetAllQueries_FindAllQueries() {
        List<Query> queries;
        for(int i = 0; i < total; ++i) {
            sqlRepo.saveAndFlush(new Query("horse"));
        }

        queries = service.getAllQueries();

        Assertions.assertNotNull(queries);
        Assertions.assertEquals(total, queries.stream().distinct().toList().size(), "Size of list must match amount of queries!");
    }

    @Test
    @Transactional
    public void HistoryService_GetAllQueries_EarlierMadeQueryAppearsFirst() {
        sqlRepo.saveAndFlush(new Query("mouse", LocalDateTime.now()));
        sqlRepo.saveAndFlush(new Query("cat", LocalDateTime.now().minusDays(1)));

        List<Query> queries = service.getAllQueries();

        Assertions.assertNotNull(queries);
        Assertions.assertEquals(2, queries.size(), String.format("Size of queries must be %d", 2));
        Assertions.assertEquals("cat", queries.getFirst().getText());
    }

    @Test
    @Transactional
    public void HistoryService_GetQueriesByText_FindAllQueriesWithGivenText() {
        String text = "cat";
        for(int i = 0; i < total; ++i) {
            sqlRepo.saveAndFlush(new Query(text));
        }

        List<Query> queries = service.getQueriesByText(text);

        Assertions.assertNotNull(queries);
        Assertions.assertEquals(total, queries.size(), "Size of list must equal amount of queries with given text!");
        Assertions.assertTrue(queries.stream().allMatch(q -> q.getText().equals(text)), "Text in all queries must match the given text!");
    }

    @Test
    @Transactional
    public void HistoryService_GetQueriesDistinctByText_ReturnOneQueryWithGivenText() {
        String text = "hamster";
        int expectedSize = 1;
        for(int i = 0; i < total; ++i) {
            sqlRepo.saveAndFlush(new Query(text));
        }

        List<Query> queries = service.getQueriesDistinctByText();

        Assertions.assertNotNull(queries);
        //filtering queries by given text to check its size
        List<Query> filteredList = queries.stream().filter(q -> q.getText().equals(text)).toList();
        int actualSize = filteredList.size();
        Assertions.assertEquals(expectedSize, actualSize, String.format("Size of list must be %d", expectedSize));
    }

    @Test
    @Transactional
    public void HistoryService_GetQueriesDistinctByText_ReturnUniqueQueries() {
        String text1 = "pig";
        String text2 = "tiger";
        for(int i = 0; i < total/2; ++i) {
            sqlRepo.saveAndFlush(new Query(text1));
        }
        for(int i = total/2; i < total; ++i) {
            sqlRepo.saveAndFlush(new Query(text2));
        }

        List<Query> queries = service.getQueriesDistinctByText();

        Assertions.assertNotNull(queries);
        //adding text to another list and making it distinct
        //to check if its size matches size of queries
        List<String> textList = new ArrayList<>();
        for(Query q : queries) {
            textList.add(q.getText());
        }
        List<String> distinctTextList = textList.stream().distinct().toList();
        int expectedSize = distinctTextList.size();
        int actualSize = queries.size();
        Assertions.assertEquals(expectedSize, actualSize, String.format("Size of list must be %d", expectedSize));
        Assertions.assertTrue(queries.stream().anyMatch(q -> q.getText().equals(text1)));
        Assertions.assertTrue(queries.stream().anyMatch(q -> q.getText().equals(text2)));
    }

    @Test
    @Transactional
    public void HistoryService_GetQueriesByDate_FindAllQueriesMadeOnGivenDate() {
        LocalDateTime dateAndTime = LocalDateTime.of(LocalDate.of(2024, 10,17), LocalTime.of(1, 15));
        sqlRepo.saveAndFlush(new Query("sheep", dateAndTime));
        int expected = 1;

        List<Query> queries = service.getQueriesByDate(dateAndTime.toLocalDate());

        Assertions.assertNotNull(queries);
        Assertions.assertTrue(queries.stream().allMatch(q -> q.getDateAndTime().toLocalDate().isEqual(dateAndTime.toLocalDate())), "Query must have been made on given date!");
        Assertions.assertEquals(expected, queries.size(), String.format("Size of list must be %d", expected));
    }

    @Test
    @Transactional
    public void HistoryService_GetQueriesBySortField_FindAllQueriesWithGivenSortField() {
        String text = "3";
        String sortField = "age";
        int total = 10;
        for(int i = 0; i < total; ++i) {
            sqlRepo.saveAndFlush(new Query(text, sortField));
        }

        List<Query> queries = service.getQueriesBySortField(sortField);

        Assertions.assertNotNull(queries);
        Assertions.assertEquals(total, queries.size(), "Size of list must equal amount of queries with given field!");
        Assertions.assertTrue(queries.stream().allMatch(q -> q.getSortField().equals(sortField)), "Sorting field in all queries must match the given field!");
    }

    @Test
    @Transactional
    public void HistoryService_GetAllQueriesFromToday_FindAllQueriesMadeToday() {
        LocalDate today = LocalDate.now();
        String text = "fish";
        int total = 10;
        for(int i = 0; i < total; ++i) {
            sqlRepo.saveAndFlush(new Query(text));
        }

        List<Query> queries = service.getAllQueriesFromToday();

        Assertions.assertNotNull(queries);
        Assertions.assertEquals(total, queries.size(), String.format("Size of the list must equal %d", total));
        Assertions.assertTrue(queries.stream().allMatch(q -> q.getDateAndTime().toLocalDate().isEqual(today)), "Date must be today!");
    }

    @Test
    @Transactional
    public void HistoryService_GetAllQueriesFromLastWeek_FindAllQueriesMadeLastWeek() {
        LocalDateTime dateAndTime = LocalDateTime.now().minusWeeks(1);
        sqlRepo.saveAndFlush(new Query("cow", dateAndTime));
        sqlRepo.saveAndFlush(new Query("cow", LocalDateTime.now()));
        int expected = 2;

        List<Query> queries = service.getAllQueriesFromLastSevenDays();

        Assertions.assertNotNull(queries);
        Assertions.assertTrue(queries.stream().allMatch(q -> q.getDateAndTime().toLocalDate().isEqual(dateAndTime.toLocalDate())
                                                                || q.getDateAndTime().toLocalDate().isAfter(dateAndTime.toLocalDate())),
                                                                "All queries must have been made last week!");
        Assertions.assertEquals(expected, queries.size(), String.format("Size of list must be %d", expected));
    }
}
