package com.example.graphqlPOC.entities;


import javax.persistence.*;

@Entity
public class Book {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String publisher;

    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;

    public Book(){}

    public Book(String title, String publisher , Author author) {
        this.id = id;
        this.title = title;
        this.publisher = publisher;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPublisher() {
        return publisher;
    }

    public Author getAuthor() {
        return author;
    }
}
