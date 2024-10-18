package searchEngine.searchEngine.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import searchEngine.searchEngine.model.Query;
import searchEngine.searchEngine.repository.SQLRepo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
@Commit
public class QuerySQLServiceTests {
    @Autowired
    private SQLRepo sqlRepo;

    @Autowired
    private QuerySQLService service;

    private static Query query;





    @AfterEach
    public void cleanUp() {
        sqlRepo.deleteAll();
    }






    @Test
    public void QuerySQLService_Create_SaveNewQueryToDB() {
        Query query = new Query("dog", "pet type");
        Long id = query.getId();

        service.create(query);

        Assertions.assertTrue(sqlRepo.existsById(id));
    }

    @Test
    public void QuerySQLService_Delete_DeleteQueryFromDB() {
        Query query = new Query("dog", "pet type");
        Long id = query.getId();

        service.delete(query);

        Assertions.assertFalse(sqlRepo.existsById(id));
    }

    @Test
    public void QuerySQLService_GetAllQueries_FindAllQueries() {
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
    public void QuerySQLService_GetQueriesByText_FindAllQueriesWithGivenText() {
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
    public void QuerySQLService_GetQueriesDistinctByText_ReturnOneQueryWithGivenText() {
        String text = "hamster";
        int total = 10;
        int expected_size = 1;
        for(int i = 0; i < total; ++i) {
            sqlRepo.saveAndFlush(new Query(text));
        }



        List<Query> queries = service.getQueriesDistinctByText();



        Assertions.assertNotNull(queries);

        //filtering queries by given text to check its size
        List<Query> filtered_list = queries.stream().filter(q -> q.getText().equals(text)).toList();
        int actual_size = filtered_list.size();

        Assertions.assertEquals(expected_size, actual_size, String.format("Size of list must be %d", expected_size));
    }

    @Test
    public void QuerySQLService_GetQueriesDistinctByText_ReturnUniqueQueries() {
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
    public void QuerySQLService_GetQueriesByDate_FindAllQueriesMadeOnGivenDate() {
        LocalDate date = LocalDate.of(2024, 10, 17);
        sqlRepo.saveAndFlush(new Query("sheep", date));
        int expected = 1;

        List<Query> queries = service.getQueriesByDate(date);

        Assertions.assertNotNull(queries);
        Assertions.assertTrue(queries.stream().allMatch(q -> q.getDate().isEqual(date)), "Query must have been made on given date!");
        Assertions.assertEquals(expected, queries.size(), String.format("Size of list must be %d", expected));
    }

    @Test
    public void QuerySQLService_GetQueriesBySortField_FindAllQueriesWithGivenSortField() {
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
    public void QuerySQLService_GetAllQueriesFromToday_FindAllQueriesMadeToday() {
        LocalDate today = LocalDate.now();
        String text = "fish";
        int total = 10;
        for(int i = 0; i < total; ++i) {
            sqlRepo.saveAndFlush(new Query(text));
        }

        List<Query> queries = service.getAllQueriesFromToday();

        Assertions.assertNotNull(queries);
        Assertions.assertEquals(total, queries.size(), String.format("Size of the list must equal %d", total));
        Assertions.assertTrue(queries.stream().allMatch(q -> q.getDate().isEqual(today)), "Date must be today!");
    }

    @Test
    public void QuerySQLService_GetAllQueriesFromLastWeek_FindAllQueriesMadeLastWeek() {
        LocalDate date = LocalDate.now().minusWeeks(1);
        sqlRepo.saveAndFlush(new Query("cow", date));
        sqlRepo.saveAndFlush(new Query("cow", LocalDate.now()));
        int expected = 2;

        List<Query> queries = service.getAllQueriesFromLastSevenDays();

        Assertions.assertNotNull(queries);
        Assertions.assertTrue(queries.stream().allMatch(q -> q.getDate().isEqual(date) || q.getDate().isAfter(date)), "All queries must have been made last week!");
        Assertions.assertEquals(expected, queries.size(), String.format("Size of list must be %d", expected));
    }
}
