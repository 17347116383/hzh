package com.itdy.hqsm.concurrentservice.executors;

import java.util.concurrent.Callable;

public class MyCallable implements Callable {

    private  String   llable ;

    public MyCallable(String llable) {
        this.llable = llable;
    }

    @Override
    public Object call() throws Exception {
        return llable;
    }

    @Override
    public String toString() {
        return "MyCallable{" +
                "llable='" + llable + '\'' +
                '}';
    }
}
