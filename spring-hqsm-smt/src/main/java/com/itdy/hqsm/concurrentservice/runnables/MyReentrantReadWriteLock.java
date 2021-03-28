package com.itdy.hqsm.concurrentservice.runnables;
/*
ReentrantReadWriteLock是Lock的另一种实现方式，我们已经知道了ReentrantLock是一个排他锁，
同一时间只允许一个线程访问，而ReentrantReadWriteLock允许多个读线程同时访问，
但不允许写线程和读线程、写线程和写线程同时访问。相对于排他锁，提高了并发性。
在实际应用中，大部分情况下对共享数据（如缓存）的访问都是读操作远多于写操作，
这时ReentrantReadWriteLock能够提供比排他锁更好的并发性和吞吐量。
　　读写锁内部维护了两个锁，一个用于读操作，一个用于写操作。所有 ReadWriteLock实现都必须保证
  writeLock操作的内存同步效果也要保持与相关 readLock的联系。也就是说，
  成功获取读锁的线程会看到写入锁之前版本所做的所有更新
 */
public class MyReentrantReadWriteLock {
}
