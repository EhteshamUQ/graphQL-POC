package com.example.graphqlPOC.controllers;


import com.example.graphqlPOC.entities.Author;
import com.example.graphqlPOC.entities.Book;
import com.example.graphqlPOC.repository.AuthorRepository;
import com.example.graphqlPOC.repository.BookRepository;
import graphql.schema.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class graphQLController {

    @Autowired
    private  AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @QueryMapping
    Iterable<Author> authors(){
        return   authorRepository.findAll();
    }

    @QueryMapping
    Optional<Author> authorById(@Argument Long id){
        return authorRepository.findById(id);
    }

    @MutationMapping
    Book addBook(@Argument BookInput book){
        Author author = authorRepository.findById(book.authorId).orElseThrow(() -> new IllegalArgumentException("Invalid ID"));
        return bookRepository.save(new Book(book.title , book.publisher , author));
    }

    @SchemaMapping
    // Pass Parent Type as Argument for this method to work
    List<Book> books(Author author){
        return bookRepository.findAll();
    }

    @QueryMapping
    Optional<Book> book(@Argument Long id){
        return bookRepository.findById(id);
    }

    record BookInput(String title , String publisher , Long authorId){}




}
