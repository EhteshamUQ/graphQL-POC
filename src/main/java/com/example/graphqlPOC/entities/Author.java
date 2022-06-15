package com.example.graphqlPOC.entities;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Author {
    @GeneratedValue
    @Id
    public Long id;
    public String name;

    @OneToMany(mappedBy = "author" , cascade = CascadeType.ALL)
    private List<Book> books = new ArrayList<Book>();

    public LocalDate dob;

    public Author(String name ,LocalDate dob) {
        this.name = name;
        this.dob = dob;
    }


    public Author(){}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Book> getBooks() {
        return books;
    }
}
