package searchEngine.searchEngine.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchEngine.searchEngine.model.Query;
import searchEngine.searchEngine.repository.SQLRepo;
import searchEngine.searchEngine.service.QuerySQLService;

@Service
public class QuerySQLServiceImplementation implements QuerySQLService {
    private final SQLRepo sqlRepo;

    public QuerySQLServiceImplementation(SQLRepo sqlRepo) {
        this.sqlRepo = sqlRepo;
    }

    @Override
    public Query create(Query query) {
        return sqlRepo.saveAndFlush(query);
    }
}
