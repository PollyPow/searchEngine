package searchEngine.searchEngine.model.Opensearch;

public enum PetType {
    DOG("Dog"),
    CAT("Cat"),
    HAMSTER("Hamster"),
    GUINEA_PIG("Guinea Pig"),
    FISH("Fish"),
    PARROT("Parrot"),
    HORSE("Horse"),
    COW("Cow"),
    PIG("Pig"),
    SHEEP("Sheep");

    private String type;

    PetType(String type) {
        this.type = type;
    }
}
