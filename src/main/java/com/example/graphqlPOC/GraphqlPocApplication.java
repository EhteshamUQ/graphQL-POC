package com.example.graphqlPOC;

import com.example.graphqlPOC.entities.Author;
import com.example.graphqlPOC.entities.Book;
import com.example.graphqlPOC.repository.AuthorRepository;
import com.example.graphqlPOC.repository.BookRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class GraphqlPocApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraphqlPocApplication.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner(AuthorRepository authorRepository , BookRepository bookRepository){

		return args -> {
			Author josh = authorRepository.save(new Author( "Josh Inglis" , LocalDate.now()));
			Author moroe = authorRepository.save(new Author( "Monroe Cork" , LocalDate.now()));

			bookRepository.saveAll(List.of(
					new Book("Reactive Spring" , "John Doe" , josh),
					new Book( "Reactive Java" , "Moror publishers" , moroe)
					));
		};
	}

}
