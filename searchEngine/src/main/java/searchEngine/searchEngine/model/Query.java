package searchEngine.searchEngine.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Represents search query on a website.
 */

@Entity
public class Query {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "text")
    private final String text;

    @Column(name = "sort_field")
    private final String sortField;

    @Column(name = "sort_direction")
    private final SortDirection sortDirection;

    @CreationTimestamp
    @Column(name = "searched_at", updatable = false)
    private LocalDateTime searchedAt;





    public Query(String text) {
        this.text = text;
        this.sortField = "";
        this.sortDirection = SortDirection.ASC;
    }

    public Query(String text, String sortField, SortDirection sortDirection) {
        this.text = text;
        this.sortField = sortField;
        this.sortDirection = sortDirection;
    }




    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getSortField() {
        return sortField;
    }

    public SortDirection getSortDirection() {
        return sortDirection;
    }

    public LocalDateTime getSearchedAt() {
        return searchedAt;
    }
}
