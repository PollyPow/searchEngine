package searchEngine.searchEngine.service;

import org.springframework.stereotype.Service;
import searchEngine.searchEngine.model.Query;
import searchEngine.searchEngine.repository.SQLRepo;

@Service
public class QuerySQLServiceImplementation {
    private final SQLRepo sqlRepo;

    public QuerySQLServiceImplementation(SQLRepo sqlRepo) {
        this.sqlRepo = sqlRepo;
    }

    public Query create(Query query) {
        return sqlRepo.saveAndFlush(query);
    }
}
