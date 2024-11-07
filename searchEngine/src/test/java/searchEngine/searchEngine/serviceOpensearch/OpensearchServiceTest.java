package searchEngine.searchEngine.serviceOpensearch;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import searchEngine.searchEngine.modelOpensearch.MyPetsIndex;
import searchEngine.searchEngine.repository.PetsOpensearchRepo;

import java.util.stream.Stream;

@SpringBootTest
public class OpensearchServiceTest {

    @Autowired
    private OpenSearchClient client;

    @Autowired
    private PetsOpensearchRepo petsRepo;

    @Autowired
    private OpenSearchPetsService petsService;





    private static Stream<Arguments> provideDocParameters() {
        return Stream.of(
                Arguments.of(new MyPetsIndex())
        );
    }

    /*
    @AfterEach
    public void cleanUp() {
        petsRepo.deleteAll();
    }

     */





    @ParameterizedTest
    @MethodSource("provideDocParameters")
    public void OpensearchServiceTest_SaveDoc_SavesNewDoc(MyPetsIndex index1) {
        petsService.saveDoc(index1);

        Assertions.assertTrue(petsRepo.existsById(index1.getId()));
    }
}
