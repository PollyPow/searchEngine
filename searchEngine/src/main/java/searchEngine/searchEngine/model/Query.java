package searchEngine.searchEngine.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Query {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private final String text;

    public Query(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
