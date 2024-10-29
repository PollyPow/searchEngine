package searchEngine.searchEngine;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import searchEngine.searchEngine.model.Query;
import searchEngine.searchEngine.repository.SQLRepo;
import searchEngine.searchEngine.service.QuerySQLService;

@SpringBootApplication(exclude = ElasticsearchDataAutoConfiguration.class)
//@PropertySource("classpath:env.properties")
public class SearchEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchEngineApplication.class, args);
	}

}
