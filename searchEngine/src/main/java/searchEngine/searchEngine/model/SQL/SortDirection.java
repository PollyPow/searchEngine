package searchEngine.searchEngine.model.SQL;

public enum SortDirection {
    ASC("ASC"),
    DESC("DESC");

    private String description;

    SortDirection(String description) {
        this.description = description;
    }
}
