package searchEngine.searchEngine.service.serviceHistory;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import searchEngine.searchEngine.model.SQL.Query;
import searchEngine.searchEngine.repository.SQLRepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
@Commit
public class ServiceHistoryImplTests {
    @Autowired
    private SQLRepo sqlRepo;

    @Autowired
    private ServiceHistory service;

    private static Query query;





    @AfterEach
    public void cleanUp() {
        sqlRepo.deleteAll();
    }






    @Test
    public void ServiceHistory_Create_SaveNewQueryToDB() {
        Query query = new Query("dog", "pet type");

        service.create(query);

        Long id = query.getId();
        Assertions.assertTrue(sqlRepo.existsById(id));
    }

    @Test
    public void ServiceHistory_Delete_DeleteQueryFromDB() {
        Query query = new Query("dog", "pet type");
        sqlRepo.saveAndFlush(query);

        service.delete(query);

        Long id = query.getId();
        Assertions.assertFalse(sqlRepo.existsById(id));
    }

    @Test
    public void ServiceHistory_GetAllQueries_FindAllQueries() {
        List<Query> queries;
        int total = 10;
        for(int i = 0; i < total; ++i) {
            sqlRepo.saveAndFlush(new Query("horse"));
        }

        queries = service.getAllQueries();

        Assertions.assertNotNull(queries);
        Assertions.assertEquals(total, queries.stream().distinct().toList().size(), "Size of list must match amount of queries!");
    }

    @Test
    public void ServiceHistory_GetAllQueries_EarlierMadeQueryAppearsFirst() {
        int total = 2;
        sqlRepo.saveAndFlush(new Query("mouse", LocalDateTime.now()));
        sqlRepo.saveAndFlush(new Query("cat", LocalDateTime.now().minusDays(1)));

        List<Query> queries = service.getAllQueries();

        Assertions.assertNotNull(queries);
        Assertions.assertEquals(total, queries.size(), String.format("Size of queries must be %d", total));
        Assertions.assertEquals("cat", queries.getFirst().getText());
    }

    @Test
    public void ServiceHistory_GetQueriesByText_FindAllQueriesWithGivenText() {
        String text = "cat";
        int total = 10;
        for(int i = 0; i < total; ++i) {
            sqlRepo.saveAndFlush(new Query(text));
        }

        List<Query> queries = service.getQueriesByText(text);

        Assertions.assertNotNull(queries);
        Assertions.assertEquals(total, queries.size(), "Size of list must equal amount of queries with given text!");
        Assertions.assertTrue(queries.stream().allMatch(q -> q.getText().equals(text)), "Text in all queries must match the given text!");
    }

    @Test
    public void ServiceHistory_GetQueriesDistinctByText_ReturnOneQueryWithGivenText() {
        String text = "hamster";
        int total = 10;
        int expectedSize = 1;
        for(int i = 0; i < total; ++i) {
            sqlRepo.saveAndFlush(new Query(text));
        }



        List<Query> queries = service.getQueriesDistinctByText();



        Assertions.assertNotNull(queries);

        //filtering queries by given text to check its size
        List<Query> filtered_list = queries.stream().filter(q -> q.getText().equals(text)).toList();
        int actualSize = filtered_list.size();

        Assertions.assertEquals(expectedSize, actualSize, String.format("Size of list must be %d", expectedSize));
    }

    @Test
    public void ServiceHistory_GetQueriesDistinctByText_ReturnUniqueQueries() {
        String text1 = "pig";
        String text2 = "tiger";
        int totalText1 = 15;
        int totalText2 = 5;
        for(int i = 0; i < totalText1; ++i) {
            sqlRepo.saveAndFlush(new Query(text1));
        }
        for(int i = 0; i < totalText2; ++i) {
            sqlRepo.saveAndFlush(new Query(text2));
        }



        List<Query> queries = service.getQueriesDistinctByText();



        Assertions.assertNotNull(queries);

        //adding text to another list and making it distinct
        //to check if its size matches size of queries
        List<String> text_list = new ArrayList<String>();
        for(Query q : queries) {
            text_list.add(q.getText());
        }
        List<String> distinct_text_list = text_list.stream().distinct().toList();
        int expected_size = distinct_text_list.size();
        int actual_size = queries.size();

        Assertions.assertEquals(expected_size, actual_size, String.format("Size of list must be %d", expected_size));
        Assertions.assertTrue(queries.stream().anyMatch(q -> q.getText().equals(text1)));
        Assertions.assertTrue(queries.stream().anyMatch(q -> q.getText().equals(text2)));
    }

    @Test
    public void ServiceHistory_GetQueriesByDate_FindAllQueriesMadeOnGivenDate() {
        LocalDateTime dateAndTime = LocalDateTime.of(LocalDate.of(2024, 10,17), LocalTime.of(1, 15));
        sqlRepo.saveAndFlush(new Query("sheep", dateAndTime));
        int expected = 1;

        List<Query> queries = service.getQueriesByDate(dateAndTime.toLocalDate());

        Assertions.assertNotNull(queries);
        Assertions.assertTrue(queries.stream().allMatch(q -> q.getDateAndTime().toLocalDate().isEqual(dateAndTime.toLocalDate())), "Query must have been made on given date!");
        Assertions.assertEquals(expected, queries.size(), String.format("Size of list must be %d", expected));
    }

    @Test
    public void ServiceHistory_GetQueriesBySortField_FindAllQueriesWithGivenSortField() {
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
    public void ServiceHistory_GetAllQueriesFromToday_FindAllQueriesMadeToday() {
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
    public void ServiceHistory_GetAllQueriesFromLastWeek_FindAllQueriesMadeLastWeek() {
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
