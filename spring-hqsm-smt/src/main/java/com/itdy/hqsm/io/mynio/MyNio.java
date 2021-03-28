package com.itdy.hqsm.io.mynio;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MyNio  {


    /*
     * 创建文件
     */
    public void create(String path, String fileName) throws IOException {
        String uri = path + "/" + fileName;
        AsynchronousFileChannel.open(Paths.get(uri),
                StandardOpenOption.CREATE, StandardOpenOption.WRITE);
    }


    /**
     * Future 读取文件
     * @param path
     * @param fileName
     * @throws IOException
     */
    public void readWithFuture(String path, String fileName) throws IOException {
        String uri = path + "/" + fileName;
        System.out.println(uri);
        AsynchronousFileChannel channel
                = AsynchronousFileChannel.open(Paths.get(uri), StandardOpenOption.READ);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        long position = 0;

        Future<Integer> operation = channel.read(buffer, position);
        while(!operation.isDone());
        buffer.flip();
        byte[] data = new byte[buffer.limit()];
        buffer.get(data);
        System.out.println("------------>"+new String(data));
        buffer.clear();
    }

    /**
     * CompletionHandler 读取文件
     * @param path
     * @param fileName
     * @throws IOException
     */
    public void readWithCompletionHandler(String path, String fileName) throws IOException {
        String uri = path + "/" + fileName;
        System.out.println(uri);

        AsynchronousFileChannel fileChannel =
                AsynchronousFileChannel.open(Paths.get(uri), StandardOpenOption.READ);
        ByteBuffer buffer = ByteBuffer.allocate(1024*1024);
        long position = 0;
        fileChannel.read(buffer, position, buffer,
                new CompletionHandler<Integer, ByteBuffer>() {

                    @Override
                    public void completed(Integer result, ByteBuffer buffer) {
                        System.out.println("--------Read Done--------");
                        System.out.println("result ----- " + result);

                        buffer.flip();
                        byte[] data = new byte[buffer.limit()];
                        buffer.get(data);
                        System.out.println(new String(data));
                        buffer.clear();
                    }

                    @Override
                    public void failed(Throwable exc, ByteBuffer attachment) {
                        System.out.println("Read failed");
                        exc.printStackTrace();
                    }
                });

        // 给终端显示留一些时间，实际项目可以删除
        int cTime = 0;
        while(cTime < 5) {
            try {
                Thread.sleep(500);
                ++cTime;
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

   /* public static  void  main(String [] args){
        try {
            //new  MyNio().create("C:\\Users\\Administrator\\Desktop\\hqsm","aa.txt");
            //new  MyNio().readWithFuture("C:\\Users\\Administrator\\Desktop\\hqsm","aa.txt");
            //new  MyNio().readWithCompletionHandler("C:\\Users\\Administrator\\Desktop\\hqsm","aa.txt");
            //new  MyNio().readWithFutures("C:\\Users\\Administrator\\Desktop\\hqsm","aa.txt");
           // new  MyNio().writeWithCompletionHandler("C:\\Users\\Administrator\\Desktop\\hqsm","aa.txt","21321312");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/


    /**
     *  Future 写入文件
     * @param path
     * @param fileName
     * @throws IOException
     */
    public void readWithFutures(String path, String fileName) throws IOException {
        String uri = path + "/" + fileName;
        System.out.println("------------"+uri);

        AsynchronousFileChannel channel =
                AsynchronousFileChannel.open(Paths.get(uri), StandardOpenOption.READ);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        long position = 0;

        Future<Integer> operation = channel.read(buffer, position);
        while(!operation.isDone());

        buffer.flip();
        byte[] data = new byte[buffer.limit()];
        buffer.get(data);
        System.out.println(new String(data));
        buffer.clear();
    }




    /**
     *  CompletionHandler 写入文件
     * @param path
     * @param fileName
     * @param message
     * @throws IOException
     */
    public void writeWithCompletionHandler(String path, String fileName, String message) throws IOException {
        String uri = path + "/" + fileName;
        System.out.println("------->"+uri);

        final AsynchronousFileChannel channel =
                AsynchronousFileChannel.open(Paths.get(uri),
                        StandardOpenOption.CREATE, StandardOpenOption.WRITE);

        byte[] byteArray = message.getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(byteArray);

        channel.write(buffer, 0, null, new CompletionHandler<Integer, ByteBuffer>(){
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println("--------Write done------");
            }
            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                System.out.println("-----Write failed--------");
                exc.printStackTrace();
            }

        });

    }
//*************************************************************************************
//*************************************************************************************


    static Thread current;

    public  void mains(String[] args) throws IOException, InterruptedException, ExecutionException {
        if (args == null || args.length == 0) {
            System.out.println("-----------Please input file path");
          //  return;
        }

       // Path filePath = Paths.get(args[0]);
        Path filePath = Paths.get("C:\\Users\\Administrator\\Desktop\\hqsm\\aa.txt");
        AsynchronousFileChannel afc = AsynchronousFileChannel.open(filePath);
        ByteBuffer byteBuffer = ByteBuffer.allocate(16 * 1024);
        //使用FutureDemo时，请注释掉completionHandlerDemo，反之亦然
        //futureDemo(afc, byteBuffer);
        completionHandlerDemo(afc, byteBuffer);
    }

    private static void completionHandlerDemo(AsynchronousFileChannel afc, ByteBuffer byteBuffer) throws IOException {
        current = Thread.currentThread();
        afc.read(byteBuffer, 0, null, new CompletionHandler<Integer, Object>() {
            @Override
            public void completed(Integer result, Object attachment) {
                System.out.println("--------Bytes Read = " + result);
                current.interrupt();
            }
            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println(exc.getCause());
                current.interrupt();
            }
        });
        System.out.println("--------Waiting for completion...");
        try {
            current.join();
        } catch (InterruptedException e) {
        }
        System.out.println("结束");
        afc.close();
    }




    private static void futureDemo(AsynchronousFileChannel afc, ByteBuffer byteBuffer) throws InterruptedException, ExecutionException, IOException {
        Future<Integer> result = afc.read(byteBuffer, 0);
        while (!result.isDone()) {
            System.out.println("Waiting file channel finished....");
            Thread.sleep(1);
        }
        System.out.println("Finished? = " + result.isDone());
        System.out.println("byteBuffer = " + result.get());
        System.out.println(byteBuffer);
        afc.close();
    }

   /* public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
         String [] sd = new String[2] ;
        new MyNio().mains(sd);
    }*/

}
