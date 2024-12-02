package searchEngine.searchEngine.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import searchEngine.searchEngine.model.Opensearch.MyPetsIndex;
import searchEngine.searchEngine.service.Opensearch.implementation.OpenSearchPetsServiceImpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class DataIndexer implements CommandLineRunner {

    @Autowired
    private OpenSearchPetsServiceImpl openSearchPetsService;

    @Override
    public void run(String... args) throws Exception {
        try {
            ClassPathResource resource = new ClassPathResource("bulk_data_pets.json");
            ObjectMapper objectMapper = new ObjectMapper();
            List<MyPetsIndex> pets;

            try (InputStream inputStream = resource.getInputStream()) {
                pets = objectMapper.readValue(inputStream, objectMapper.getTypeFactory().constructCollectionType(List.class, MyPetsIndex.class));
            }
            openSearchPetsService.indexBulkData(pets);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
