package com.example.graphqlPOC.config;

import com.example.graphqlPOC.repository.BookRepository;
import graphql.language.StringValue;
import graphql.schema.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Configuration(proxyBeanMethods = false)
public class GraphQLConfigurer {

    @Autowired
    private BookRepository bookRepository;
    public GraphQLScalarType getScalar(){
        return GraphQLScalarType.newScalar()
                .name("Date")
                .description("Java 8 LocalDate as scalar.")
                .coercing(new Coercing<LocalDate, String>() {
                    @Override
                    public String serialize(final Object dataFetcherResult) {
                        if (dataFetcherResult instanceof LocalDate) {
                            return dataFetcherResult.toString();
                        } else {
                            throw new CoercingSerializeException("Expected a LocalDate object.");
                        }
                    }

                    @Override
                    public LocalDate parseValue(final Object input) {
                        try {
                            if (input instanceof String) {
                                return LocalDate.parse((String) input);
                            } else {
                                throw new CoercingParseValueException("Expected a String");
                            }
                        } catch (DateTimeParseException e) {
                            throw new CoercingParseValueException(String.format("Not a valid date: '%s'.", input), e
                            );
                        }
                    }

                    @Override
                    public LocalDate parseLiteral(final Object input) {
                        if (input instanceof StringValue) {
                            try {
                                return LocalDate.parse(((StringValue) input).getValue());
                            } catch (DateTimeParseException e) {
                                throw new CoercingParseLiteralException(e);
                            }
                        } else {
                            throw new CoercingParseLiteralException("Expected a StringValue.");
                        }
                    }
                }).build();
    }

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer(){
        GraphQLScalarType date = getScalar();
        return wiringBuilder -> wiringBuilder.scalar(date).directive("uppercase" , new UpperCaseDirective())
                .type("Query" , builder ->
                    builder.dataFetcher("allBooks", enivronment -> bookRepository.findAll())
                );
    }
// Can even Attach DataFetchers

}
