package com.itdy.hqsm.mongodb;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/***
 * @author
 * @date 2019/8
 */
@RestController
public class BaseController {

    @Autowired
    private MongoDbService mongoDbService;

    @RequestMapping("/mongo/save")
    public String saveObj(@RequestBody Book book) {
        return mongoDbService.saveObj(book);
    }

    @GetMapping("/mongo/findAll")
    public List<Book> findAll() {
        return mongoDbService.findAll();}

    @RequestMapping("/mongo/findOne")
    public Book findOne(@RequestParam String id) {
        return mongoDbService.getBookById(id);}

    @RequestMapping("/mongo/findOneByName")
    public Book findOneByName(@RequestParam String name) {
        return mongoDbService.getBookByName(name);}

    @PostMapping("/mongo/update")
    public String update(@RequestBody Book book) {
        return mongoDbService.updateBook(book);}

    @PostMapping("/mongo/delOne")
    public String delOne(@RequestBody Book book) {
        return mongoDbService.deleteBook(book);}

    @RequestMapping("/mongo/delById")
    public String delById(@RequestParam String id) {
        return mongoDbService.deleteBookById(id);}
}

