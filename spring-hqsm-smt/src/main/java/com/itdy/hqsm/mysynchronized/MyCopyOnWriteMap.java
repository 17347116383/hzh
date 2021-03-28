package com.itdy.hqsm.mysynchronized;




import java.util.Map;

/**
 * 说到了“类似于数据库中的行锁的概念”，就不得不提一下MVCC，Java中CopyOnWrite类实现了MVCC。
 * Copy On Write是这样一种机制。当我们读取共享数据的时候，直接读取，不需要同步。当我们修改数据的时候，
 * 我们就把当前数据Copy一份副本，然后在这个副本 上进行修改，完成之后，再用修改后的副本，
 * 替换掉原来的数据。这种方法就叫做Copy On Write。
 */
public class MyCopyOnWriteMap {





}
