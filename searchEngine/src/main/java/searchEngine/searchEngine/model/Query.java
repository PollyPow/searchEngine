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

    @Column(name = "date", nullable = false, updatable = false)
    private final LocalDate date = LocalDate.now();

    @Column(name = "time", nullable = false, updatable = false)
    private final LocalTime time = LocalTime.now();





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
        return date;
    }

    public LocalTime getSearchedAt() {
        return time;
    }
}
