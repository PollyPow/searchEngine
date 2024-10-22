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
    @GeneratedValue
    private Long id;

    @Column(name = "text", nullable = false, updatable = false)
    private String text;

    @Column(name = "sort_field", updatable = false)
    private String sortField;

    @Column(name = "sort_direction", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private SortDirection sortDirection;

    @Column(name = "searched_on", nullable = false, updatable = false)
    private LocalDateTime dateAndTime;





    public Query() {
        this.text = "";
        this.sortDirection = SortDirection.ASC;
        this.dateAndTime = LocalDateTime.now();
    }

    public Query(String text) {
        this.text = text;
        this.sortDirection = SortDirection.ASC;
        this.dateAndTime = LocalDateTime.now();
    }

    public Query(String text, String sortField) {
        this.text = text;
        this.sortField = sortField;
        this.sortDirection = SortDirection.ASC;
        this.dateAndTime = LocalDateTime.now();
    }

    public Query(String text, String sortField, SortDirection sortDirection) {
        this.text = text;
        this.sortField = sortField;
        this.sortDirection = sortDirection;
        this.dateAndTime = LocalDateTime.now();
    }

    public Query(String text, LocalDateTime dateAndTime) {
        this.text = text;
        this.sortField = "";
        this.sortDirection = SortDirection.ASC;
        this.dateAndTime = dateAndTime;
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

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public void setSortDirection(SortDirection sortDirection) {
        this.sortDirection = sortDirection;
    }

    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }
}
