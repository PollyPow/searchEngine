package searchEngine.searchEngine.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents search query on a website.
 */

@Entity
public class Query {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "text", updatable = false)
    private final String text;

    @Column(name = "sort_field", updatable = false)
    private final String sortField;

    @Column(name = "sort_direction", updatable = false)
    private final SortDirection sortDirection;

    @Column(name = "searched_on", nullable = false, updatable = false)
    private final LocalDate searchedOn = LocalDate.now();

    @Column(name = "searched_at", nullable = false, updatable = false)
    private final LocalTime searchedAt = LocalTime.now();





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

    public LocalDate getSearchedOn() {
        return searchedOn;
    }

    public LocalTime getSearchedAt() {
        return searchedAt;
    }
}
