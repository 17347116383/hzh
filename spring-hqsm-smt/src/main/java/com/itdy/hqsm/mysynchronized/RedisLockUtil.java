package com.itdy.hqsm.mysynchronized;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.Collections;
import java.util.List;

/**
 * redis分布式锁测试1
 *
 *
 */
public final class RedisLockUtil extends  Thread {


    /*private String auctionCode;

    public RedisLockUtil(String auctionCode) {
        super(auctionCode);
        this.auctionCode = auctionCode;
    }
    private static int bidPrice = 100;
*/


   /* @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "线程运行开始 ");
        Jedis jedis=new Jedis("127.0.0.1",6379);
        try {
            if(Thread.currentThread().getName()=="B001"){
                sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //监视KEY
        jedis.watch("goodsprice");
        //A先进
        String v =  jedis.get("goodsprice");
        Integer iv = Integer.valueOf(v);
        //条件都给过
        if(bidPrice > iv){
            Transaction tx = jedis.multi();// 开启事务
            Integer bp = iv + 100;
            //出价成功，事务未提交
            tx.set("goodsprice",String.valueOf(bp));
            System.out.println("子线程-------" + auctionCode + "set成功");
            try {
                if(Thread.currentThread().getName()=="A001"){
                    sleep(2000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<Object> list = tx.exec();
            if (list == null ||list.size()==0) {
                System.out.println("子线程-----" + auctionCode + ",出价失败");
            }else{
                System.out.println("子线程------"+this.auctionCode +", 出价："+ jedis.get("goodsprice") +"，出价时间："
                        + System.nanoTime());
            }
        }else{
            System.out.println("出价低于现有价格！");
        }
        jedis.close();
        System.out.println(Thread.currentThread().getName() + "线程运行结束");
    }*/

     /*
     主线程里面开了两个子线程，首先让B001先等待1s时间，让A001先watch最高价，
     * 然后在A001事务exec之前让他等待2s时间。这个时候B001已经出价成功了，所以最后应当返回A001出价失败。
      */
    /*public static void main(String[] args) {
       // System.out.println(Thread.currentThread().getName() + "主线程运行开始!");
        //更改key为a的值
        Jedis jedis=new Jedis("127.0.0.1",6379);
        jedis.set("goodsprice","0");
        System.out.println("输出初始化值：---------"+jedis.get("goodsprice"));
        jedis.close();
        RedisLockUtil thread1 = new RedisLockUtil("A001");
        RedisLockUtil thread2  = new RedisLockUtil("B001");
        //RedisLockUtil thread3  = new RedisLockUtil("C001");
        //RedisLockUtil thread4  = new RedisLockUtil("D001");
        thread1.start();
        thread2.start();
        //thread3.start();
        //thread4.start();
        try{
            thread1.join();
            thread2.join();
            //thread3.join();
            //thread4.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
       // System.out.println(Thread.currentThread().getName() + "主线程运行结束!");
    }*/

////////////////////////////////////////////////////////////////////////////////////////////
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    private String auctionCode;
    public RedisLockUtil
            (String auctionCode) {
        super(auctionCode);
        this.auctionCode = auctionCode;
    }
    private static int bidPrice = 100;

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + "主线程运行开始!");
        //更改key为a的值
        Jedis jedis=new Jedis("127.0.0.1",6379);
        jedis.set("goodsprice","0");
        System.out.println("输出初始化值："+jedis.get("goodsprice"));
        jedis.close();
        RedisLockUtil thread1 = new RedisLockUtil("A001");
        RedisLockUtil thread2  = new RedisLockUtil("B001");
        RedisLockUtil thread3  = new RedisLockUtil("C001");
        RedisLockUtil thread4  = new RedisLockUtil("D001");
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        try{
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "主线程运行结束!");
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "线程运行开始 ");
        Jedis jedis=new Jedis("127.0.0.1",6379);
       // try {
            if(Thread.currentThread().getName()=="B001"){
                //sleep(1000);
            }
       // } catch (InterruptedException e) {
       //     e.printStackTrace();
       //  }
        //让A先拿到锁   /获取锁
        boolean isOk=  tryGetDistributedLock(jedis, "goods_lock", Thread.currentThread().getName() , 10000);
        //boolean isOk=true;
       // try {
            if(Thread.currentThread().getName()=="A001"){
            //    sleep(2000);
            }
        //} catch (InterruptedException e) {
       //     e.printStackTrace();
       // }

        if(isOk) {
            System.out.println("子线程"+this.auctionCode +"拿到锁");
            String v =  jedis.get("goodsprice");
            Integer iv = Integer.valueOf(v);
            //条件都给过
            if(bidPrice > iv){
                Integer bp = iv + 100;
                //出价成功，事务未提交
                //业务处理
                jedis.set("goodsprice",String.valueOf(bp));
                System.out.println("子线程"+this.auctionCode +", 出价："+ jedis.get("goodsprice") +"，出价时间："
                        + System.nanoTime());
            }else{
                System.out.println("出价低于现有价格！");
            }
            //释放锁
            boolean isOk1=  releaseDistributedLock(jedis, "goods_lock", Thread.currentThread().getName());
            if(isOk1){
                System.out.println("子线程"+this.auctionCode +"释放锁");
            }
        }else{
            System.out.println("子线程" + auctionCode + "未拿到锁");
        }
        jedis.close();
        System.out.println(Thread.currentThread().getName() + "线程运行结束");
    }
    /**
     * 尝试获取分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, int expireTime) {
        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        if (LOCK_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }
    private static final Long RELEASE_SUCCESS = 1L;

    /**
     * 释放分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public  boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1])else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;




    }
}