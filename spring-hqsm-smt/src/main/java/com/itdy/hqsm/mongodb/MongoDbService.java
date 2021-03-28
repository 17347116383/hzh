package com.itdy.hqsm.mongodb;

//import com.itdy.hqsm.security.shiro.dao.entity.SysUser;
//import com.itdy.hqsm.security.shiro.service.impl.UserServiceImpls;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 描述:
 * mongo
 *
 * @author
 * @date 2019/8/9
 */
@Service
public class MongoDbService {
    private static final Logger logger = LoggerFactory.getLogger(MongoDbService.class);

    @Autowired
    private MongoTemplate mongoTemplate;
    //注入新的CURD操作类
    @Autowired
    private BookMongoDbDao bookMongoDbDao;
    //@Autowired
   // private UserServiceImpls userServiceImpls;
    /**
     * 保存对象
     *
     * @param book
     * @return
     */
    public String saveObj(Book book) {
      //  List<Book> list = new ArrayList<Book>();
        for (int i=0 ;i<100000 ;i++){
            Book  books = new Book();
            books.setId( UUID.randomUUID().toString().replaceAll("-", ""));
            books.setName("NAME"+i);
            books.setInfo("info"+i);
            books.setPrice(i);
        //    list.add(books);
            bookMongoDbDao.save(books);
        }

        logger.info("--------------------->[MongoDB save start]");
        book.setCreateTime(new Date());
        book.setUpdateTime(new Date());
        bookMongoDbDao.save(book);
        return "添加成功";
    }


    /**
     * 查询所有
     *
     * @return
     */
    public List<Book> findAll() {
        logger.info("--------------------->[MongoDB find start]");
        return mongoTemplate.findAll(Book.class);
    }


    /***
     * 根据id查询
     * @param id
     * @return
     */
    public Book getBookById(String id) {
        logger.info("--------------------->[MongoDB find start]");
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, Book.class);
    }

    /**
     * 根据名称查询
     *
     * @param name
     * @return
     */
    public Book getBookByName(String name) {
        logger.info("--------------------->[MongoDB find start]");
        Query query = new Query(Criteria.where("name").is(name));
        return mongoTemplate.findOne(query, Book.class);
    }

    /**
     * 更新对象
     *
     * @param book
     * @return
     */
    public String updateBook(Book book) {
        logger.info("--------------------->[MongoDB update start]");
        Query query = new Query(Criteria.where("_id").is(book.getId()));
        Update update = new Update().set("publish", book.getPublish())
                .set("info", book.getInfo())
                .set("name","huang")
                .set("updateTime", new Date());
        //updateFirst 更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query, update, Book.class);
        //updateMulti 更新查询返回结果集的全部
//        mongoTemplate.updateMulti(query,update,Book.class);
        //upsert 更新对象不存在则去添加
//        mongoTemplate.upsert(query,update,Book.class);
        return "success-----------------";
    }

    /***
     * 删除对象
     * @param book
     * @return
     */
    public String deleteBook(Book book) {
        logger.info("--------------------->[MongoDB delete start]");
        mongoTemplate.remove(book);
        return "success";
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    public String deleteBookById(String id) {
        logger.info("--------------------->[MongoDB delete start]");
        //findOne
        Book book = getBookById(id);
        //delete
        deleteBook(book);
        return "success";
    }
}
