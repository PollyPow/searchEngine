package searchEngine.searchEngine.model.Opensearch;

public enum PetType {
    DOG("DOG"),
    CAT("CAT"),
    HAMSTER("HAMSTER"),
    GUINEA_PIG("GUINEA PIG"),
    FISH("FISH"),
    PARROT("PARROT"),
    HORSE("HORSE"),
    COW("COW"),
    PIG("PIG"),
    SHEEP("SHEEP");

    private String type;

    PetType(String type) {
        this.type = type;
    }
}
