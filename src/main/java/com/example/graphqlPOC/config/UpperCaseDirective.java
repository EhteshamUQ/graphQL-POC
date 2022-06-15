package com.example.graphqlPOC.config;

import graphql.schema.*;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;

public class UpperCaseDirective implements SchemaDirectiveWiring {


    @Override
    public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {
        var field = environment.getElement();
        // GET Parent
        GraphQLFieldsContainer parentType = environment.getFieldsContainer();
        DataFetcher fetcher = environment.getCodeRegistry().getDataFetcher(parentType , field);
        // Wrap Old Data Fetcher with new One
        DataFetcher newFetcher = DataFetcherFactories.wrapDataFetcher(fetcher , (dataFetchingEnvironment ,
                                                                                 value)->{
            if(value instanceof String)
                return ((String)value).toUpperCase();
            return value;
        });
        environment.getCodeRegistry().dataFetcher(parentType, field , newFetcher);
        return field;
    }


}
