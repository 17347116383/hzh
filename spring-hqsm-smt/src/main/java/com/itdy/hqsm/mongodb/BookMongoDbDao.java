package com.itdy.hqsm.mongodb;


import org.springframework.stereotype.Repository;

/**
 *
 *
 */
@Repository
public class BookMongoDbDao  extends MongoDbDao<Book> {
    @Override
    protected Class<Book> getEntityClass() {
        return Book.class;
    }
}
