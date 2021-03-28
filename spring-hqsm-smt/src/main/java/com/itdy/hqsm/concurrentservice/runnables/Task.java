package com.itdy.hqsm.concurrentservice.runnables;

/**
 * * 分而治之不算一种模式, 而是一种思想.
 *  * 它可以将一个大任务拆解为若干个小任务并行执行, 提高系统吞吐量.
 */
public class Task {

     private int id;
     private String name;
     private int price;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    public Task(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
