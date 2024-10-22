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
    @GeneratedValue
    private Long id;

    @Column(name = "text", nullable = false, updatable = false)
    private final String text;

    @Column(name = "sort_field", updatable = false)
    private String sortField;

    @Column(name = "sort_direction", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private final SortDirection sortDirection;

    @Column(name = "searched_on", nullable = false, updatable = false)
    private final LocalDate date;

    @Column(name = "searched_at", nullable = false, updatable = false)
    private final LocalTime time;





    public Query() {
        this.text = "";
        this.sortDirection = SortDirection.ASC;
        this.date = LocalDate.now();
        this.time = LocalTime.now();
    }

    public Query(String text) {
        this.text = text;
        this.sortDirection = SortDirection.ASC;
        this.date = LocalDate.now();
        this.time = LocalTime.now();
    }

    public Query(String text, String sortField) {
        this.text = text;
        this.sortField = sortField;
        this.sortDirection = SortDirection.ASC;
        this.date = LocalDate.now();
        this.time = LocalTime.now();
    }

    public Query(String text, String sortField, SortDirection sortDirection) {
        this.text = text;
        this.sortField = sortField;
        this.sortDirection = sortDirection;
        this.date = LocalDate.now();
        this.time = LocalTime.now();
    }

    public Query(String text, LocalDate date) {
        this.text = text;
        this.sortField = "";
        this.sortDirection = SortDirection.ASC;
        this.date = date;
        this.time = LocalTime.now();
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

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
