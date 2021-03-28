package com.itdy.hqsm.tcp;
import java.io.*;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.logging.Logger;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
 
public class FTPUtils {
  public static void makeDirectory(String hostname,String username,String password,String path) throws Exception{
      FTPClient ftp = new FTPClient();
      ftp.setDefaultTimeout(10000);
      try{
         ftp.connect(hostname);
         if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
            throw new Exception("FTP服务器无响应");
            }
            if (!ftp.login(username, password)) {
               throw new Exception("FTP用户名密码错误");
            }
            ftp.makeDirectory(path);
            ftp.logout();
      }catch(Exception e){
         e.printStackTrace();
         throw e;
      }finally{
         try {
                if (ftp.isConnected()) {
                    ftp.disconnect();
                }
            } catch (Exception e) {
               throw e;
            }
      }
   }
   
   public static void getFile(String hostname,String username,String password,String remoteFilename,OutputStream out) throws Exception {
      FTPClient ftp = new FTPClient();
      ftp.setDefaultTimeout(10000);
      try{
         ftp.connect(hostname);
         if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
            throw new Exception("FTP服务器无响应");
            }
            if (!ftp.login(username, password)) {
               throw new Exception("FTP用户名密码错误");
            }
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.enterLocalPassiveMode();
            if (!ftp.retrieveFile(remoteFilename, out)) {
               throw new Exception(ftp.getReplyString()+ftp.getReply());
            }
            ftp.logout();
      }catch(Exception e){
         e.printStackTrace();
         throw e;
      }finally{
         try {
                if (ftp.isConnected()) {
                    ftp.disconnect();
                }
            } catch (Exception e) {
               throw e;
            }
      }
   }
   public static String dele(String hostname,String username,String password,String pathname) throws Exception {
      FTPClient ftp = new FTPClient();
      ftp.setDefaultTimeout(10000);
      try{
         ftp.connect(hostname);
         if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
            throw new Exception("FTP服务器无响应");
            }
            if (!ftp.login(username, password)) {
               throw new Exception("FTP用户名密码错误");
            }
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.enterLocalPassiveMode();
            ftp.removeDirectory(pathname);
            ftp.logout();
      }catch(Exception e){
         e.printStackTrace();
         throw e;
      }finally{
         try {
                if (ftp.isConnected()) {
                    ftp.disconnect();
                }
            } catch (Exception e) {
               throw e;
            }
      }
      
      return null;
   }
   
   
   public static Boolean delFile(String hostname,String username,String password,String remoteFilename) {
      Boolean flag = Boolean.TRUE;
      FTPClient ftp = new FTPClient();
      ftp.setDefaultTimeout(10000);
      try{
         ftp.connect(hostname);
         if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
            throw new Exception("FTP服务器无响应");
            }
            if (!ftp.login(username, password)) {
               throw new Exception("FTP用户名密码错误");
            }
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.enterLocalPassiveMode();
            ftp.deleteFile(remoteFilename);
            ftp.logout();
      }catch(Exception e){
         flag = Boolean.FALSE;
         e.printStackTrace();
      }finally{
         try {
                if (ftp.isConnected()) {
                    ftp.disconnect();
                }
            } catch (Exception e) {
            }
      }
      
      return flag;
   }
   
   public static void putFile(String hostname,String username,String password,String remoteFilename,InputStream is) throws Exception {
      FTPClient ftp = new FTPClient();
      ftp.setDefaultTimeout(10000);
      try{
         ftp.connect(hostname);
         if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
            throw new Exception("FTP服务器无响应");
            }
            if (!ftp.login(username, password)) {
               throw new Exception("FTP用户名密码错误");
            }
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.enterLocalPassiveMode();
            if (!ftp.storeFile(remoteFilename, is)) {
               throw new Exception(ftp.getReplyString()+ftp.getReply());
            }
            ftp.logout();
      }catch(Exception e){
         e.printStackTrace();
         throw e;
      }finally{
         try {
                if (ftp.isConnected()) {
                    ftp.disconnect();
                }
            } catch (Exception e) {
               throw e;
            }
            try {
                is.close();
            } catch (Exception e) {
               throw e;
            }
      }
   }
   
   public static Boolean putFile(String hostname,int port,String username,String password,String remoteFilename,InputStream is) throws Exception {
      Boolean flag = Boolean.TRUE;
      FTPClient ftp = new FTPClient();
      ftp.setDefaultTimeout(10000);
//    ftp.setDataTimeout(100000);
      try{
         ftp.connect(hostname,port);
         if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
            throw new Exception("FTP服务器无响应");
           }
           if (!ftp.login(username, password)) {
               throw new Exception("FTP用户名密码错误");
           }
           ftp.setFileType(FTP.BINARY_FILE_TYPE);
           ftp.enterLocalPassiveMode();
           if (!ftp.storeFile(remoteFilename, is)) {
               throw new Exception(ftp.getReplyString()+ftp.getReply());
           }
           ftp.logout();
      }catch(Exception e){
         flag = Boolean.FALSE;
         e.printStackTrace();
         throw e;
      }finally{
         try {
               if (ftp.isConnected()) {
                   ftp.disconnect();
               }
           } catch (Exception e) {
           }
           try {
               is.close();
           } catch (Exception e) {
           }
      }
      return flag;
   }
   
   public static FTPFile[]  listFiles(String hostname,String username,String password,String remotePath) throws Exception {
      FTPClient ftp = new FTPClient();
      FTPFile[] list=null;
      ftp.setDefaultTimeout(10000);
      try{
         ftp.connect(hostname);
         if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
            throw new Exception("FTP服务器无响应");
            }
            if (!ftp.login(username, password)) {
               throw new Exception("FTP用户名密码错误");
            }
            list=ftp.listFiles(remotePath);
            ftp.logout();
      }catch(Exception e){
         e.printStackTrace();
         throw e;
      }finally{
         try {
                if (ftp.isConnected()) {
                    ftp.disconnect();
                }
            } catch (Exception e) {
               throw e;
            }
      }
      return list;
   }
   
   public static FTPFile[]  listDirectories(String hostname,String username,String password,String remotePath) throws Exception {
      FTPClient ftp = new FTPClient();
      FTPFile[] list=null;
      ftp.setDefaultTimeout(10000);
      try{
         ftp.connect(hostname);
         if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
            throw new Exception("FTP服务器无响应");
            }
            if (!ftp.login(username, password)) {
               throw new Exception("FTP用户名密码错误");
            }
            list=ftp.listDirectories(remotePath);
            ftp.logout();
      }catch(Exception e){
         e.printStackTrace();
         throw e;
      }finally{
         try {
                if (ftp.isConnected()) {
                    ftp.disconnect();
                }
            } catch (Exception e) {
               throw e;
            }
      }
      return list;
   }
   
   public static Boolean isDirExist(String hostname,String username,String password,String remoteFilename) throws Exception {
      Boolean flag = Boolean.TRUE;
      FTPClient ftp = new FTPClient();
      ftp.setDefaultTimeout(10000);
//    ftp.setDataTimeout(100000);
      try{
         ftp.connect(hostname,21);
         if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
            throw new Exception("FTP服务器无响应");
           }
           if (!ftp.login(username, password)) {
               throw new Exception("FTP用户名密码错误");
           }
           ftp.setFileType(FTP.BINARY_FILE_TYPE);
           ftp.enterLocalPassiveMode();
           
           if (!ftp.changeWorkingDirectory(remoteFilename)) {
              flag = Boolean.FALSE;
           }
           ftp.logout();
      }catch(Exception e){
         flag = Boolean.FALSE;
         e.printStackTrace();
         throw e;
      }finally{
         try {
               if (ftp.isConnected()) {
                   ftp.disconnect();
               }
           } catch (Exception e) {
           }
      }
      return flag;
   }
   
   
   public static Boolean renameFile(String hostname,String username,String password,String pathName,String srcFname,String targetFname) throws Exception {
      Boolean flag = Boolean.TRUE;
      FTPClient ftp = new FTPClient();
      ftp.setDefaultTimeout(10000);
//    ftp.setDataTimeout(100000);
      try{
         ftp.connect(hostname,21);
         if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
            throw new Exception("FTP服务器无响应");
           }
           if (!ftp.login(username, password)) {
               throw new Exception("FTP用户名密码错误");
           }
           ftp.changeWorkingDirectory(pathName);
           ftp.rename(srcFname, targetFname);
           ftp.logout();
      }catch(Exception e){
         flag = Boolean.FALSE;
         e.printStackTrace();
         throw e;
      }finally{
         try {
               if (ftp.isConnected()) {
                   ftp.disconnect();
               }
           } catch (Exception e) {
           }
      }
      return flag;
   }

 ////////////////////////////////////////////------------------------------------



    //ftp服务器地址
    public String hostname = "192.168.1.249";
    //ftp服务器端口号默认为21
    public Integer port = 21 ;
    //ftp登录账号
    public String username = "root";
    //ftp登录密码
    public String password = "123";

    public FTPClient ftpClient = null;

    /**
     * 初始化ftp服务器
     */
    public void initFtpClient() {
        ftpClient = new FTPClient();
        ftpClient.setControlEncoding("utf-8");
        try {
            System.out.println("connecting...ftp服务器:"+this.hostname+":"+this.port);
            ftpClient.connect(hostname, port); //连接ftp服务器
            ftpClient.login(username, password); //登录ftp服务器
            int replyCode = ftpClient.getReplyCode(); //是否成功登录服务器
            if(!FTPReply.isPositiveCompletion(replyCode)){
                System.out.println("connect failed...ftp服务器:"+this.hostname+":"+this.port);
            }
            System.out.println("connect successfu...ftp服务器:"+this.hostname+":"+this.port);
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传文件
     * @param pathname ftp服务保存地址
     * @param fileName 上传到ftp的文件名
     * @param originfilename 待上传文件的名称（绝对地址） *
     * @return
     */
    public boolean uploadFile( String pathname, String fileName,String originfilename){
        boolean flag = false;
        InputStream inputStream = null;
        try{
            System.out.println("开始上传文件");
            inputStream = new FileInputStream(new File(originfilename));
            initFtpClient();
            ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
            CreateDirecroty(pathname);
            ftpClient.makeDirectory(pathname);
            ftpClient.changeWorkingDirectory(pathname);
            ftpClient.storeFile(fileName, inputStream);
            inputStream.close();
            ftpClient.logout();
            flag = true;
            System.out.println("上传文件成功");
        }catch (Exception e) {
            System.out.println("上传文件失败");
            e.printStackTrace();
        }finally{
            if(ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
            if(null != inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
    /**
     * 上传文件
     * @param pathname ftp服务保存地址
     * @param fileName 上传到ftp的文件名
     * @param inputStream 输入文件流
     * @return
     */
    public boolean uploadFile( String pathname, String fileName,InputStream inputStream){
        boolean flag = false;
        try{
            System.out.println("开始上传文件");
            initFtpClient();
            ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
            CreateDirecroty(pathname);
            ftpClient.makeDirectory(pathname);
            ftpClient.changeWorkingDirectory(pathname);
            ftpClient.storeFile(fileName, inputStream);
            inputStream.close();
            ftpClient.logout();
            flag = true;
            System.out.println("上传文件成功");
        }catch (Exception e) {
            System.out.println("上传文件失败");
            e.printStackTrace();
        }finally{
            if(ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
            if(null != inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
    //改变目录路径
    public boolean changeWorkingDirectory(String directory) {
        boolean flag = true;
        try {
            flag = ftpClient.changeWorkingDirectory(directory);
            if (flag) {
                System.out.println("进入文件夹" + directory + " 成功！");

            } else {
                System.out.println("进入文件夹" + directory + " 失败！开始创建文件夹");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return flag;
    }

    //创建多层目录文件，如果有ftp服务器已存在该文件，则不创建，如果无，则创建
    public boolean CreateDirecroty(String remote) throws IOException {
        boolean success = true;
        String directory = remote + "/";
        // 如果远程目录不存在，则递归创建远程服务器目录
        if (!directory.equalsIgnoreCase("/") && !changeWorkingDirectory(new String(directory))) {
            int start = 0;
            int end = 0;
            if (directory.startsWith("/")) {
                start = 1;
            } else {
                start = 0;
            }
            end = directory.indexOf("/", start);
            String path = "";
            String paths = "";
            while (true) {
                String subDirectory = new String(remote.substring(start, end).getBytes("GBK"), "iso-8859-1");
                path = path + "/" + subDirectory;
                if (!existFile(path)) {
                    if (makeDirectory(subDirectory)) {
                        changeWorkingDirectory(subDirectory);
                    } else {
                        System.out.println("创建目录[" + subDirectory + "]失败");
                        changeWorkingDirectory(subDirectory);
                    }
                } else {
                    changeWorkingDirectory(subDirectory);
                }
                paths = paths + "/" + subDirectory;
                start = end + 1;
                end = directory.indexOf("/", start);
                // 检查所有目录是否创建完毕
                if (end <= start) {
                    break;
                }
            }
        }
        return success;
    }

    //判断ftp服务器文件是否存在
    public boolean existFile(String path) throws IOException {
        boolean flag = false;
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        if (ftpFileArr.length > 0) {
            flag = true;
        }
        return flag;
    }
    //创建目录
    public boolean makeDirectory(String dir) {
        boolean flag = true;
        try {
            flag = ftpClient.makeDirectory(dir);
            if (flag) {
                System.out.println("创建文件夹" + dir + " 成功！");
            } else {
                System.out.println("创建文件夹" + dir + " 失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /** * 下载文件 *
     * @param pathname FTP服务器文件目录 *
     * @param filename 文件名称 *
     * @param localpath 下载后的文件路径 *
     * @return */
    public boolean downloadFile(String pathname, String filename, String localpath){
        boolean flag = false;
        OutputStream os=null;
        try {
            System.out.println("开始下载文件");
            initFtpClient();
            //切换FTP目录
            ftpClient.changeWorkingDirectory(pathname);
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for(FTPFile file : ftpFiles){
                if(filename.equalsIgnoreCase(file.getName())){
                    File localFile = new File(localpath + "/" + file.getName());
                    os = new FileOutputStream(localFile);
                    ftpClient.retrieveFile(file.getName(), os);
                    os.close();
                }
            }
            ftpClient.logout();
            flag = true;
            System.out.println("下载文件成功");
        } catch (Exception e) {
            System.out.println("下载文件失败");
            e.printStackTrace();
        } finally{
            if(ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
            if(null != os){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /** * 删除文件 *
     * @param pathname FTP服务器保存目录 *
     * @param filename 要删除的文件名称 *
     * @return */
    public boolean deleteFile(String pathname, String filename){
        boolean flag = false;
        try {
            System.out.println("开始删除文件");
            initFtpClient();
            //切换FTP目录
            ftpClient.changeWorkingDirectory(pathname);
            ftpClient.dele(filename);
            ftpClient.logout();
            flag = true;
            System.out.println("删除文件成功");
        } catch (Exception e) {
            System.out.println("删除文件失败");
            e.printStackTrace();
        } finally {
            if(ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

   /* public static void main(String[] args) {
        FtpUtils ftp =new FtpUtils();
        //ftp.uploadFile("ftpFile/data", "123.docx", "E://123.docx");
        //ftp.downloadFile("ftpFile/data", "123.docx", "F://");
        ftp.deleteFile("ftpFile/data", "123.docx");
        System.out.println("ok");
    }*/
//----------------------------------------------------------------------------------


    private static FTPClient ftp;


    /**
     * 获取ftp连接
     * @param f
     * @return
     * @throws Exception
     */
  /*  public static boolean connectFtp(FtpConfig f) throws Exception{
        ftp=new FTPClient();
        boolean flag=false;
        if (f.getFtpPort()==null) {
            ftp.connect(f.getFtpHost(),21);
        }else{
            ftp.connect(f.getFtpHost(),f.getFtpPort());
        }
        ftp.login(f.getFtpUser(), f.getFtpPassword());
        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            return flag;
        }
        ftp.changeWorkingDirectory(f.getFtpPath());
        flag = true;
        return flag;
    }*/

    /**
     * 关闭ftp连接
     */
    public static void closeFtp(){
        try {
            if (ftp!=null && ftp.isConnected()) {
                ftp.logout();
                ftp.disconnect();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * ftp上传文件
     * @param f
     * @throws Exception
     */
    public static void upload(File f) throws Exception{
        if (f.isDirectory()) {
            ftp.makeDirectory(f.getName());
            ftp.changeWorkingDirectory(f.getName());
            String[] files=f.list();
            for(String fstr : files){
                File file1=new File(f.getPath()+File.separator+fstr);
                if (file1.isDirectory()) {
                    upload(file1);
                    ftp.changeToParentDirectory();
                }else{
                    File file2=new File(f.getPath()+File.separator+fstr);
                    FileInputStream input=new FileInputStream(file2);
                    ftp.storeFile(file2.getName(),input);
                    input.close();
                }
            }
        }else{
            File file2=new File(f.getPath());
            FileInputStream input=new FileInputStream(file2);
            ftp.storeFile(file2.getName(),input);
            input.close();
        }
    }

    /**
     * 下载链接配置
     * @param f
     * @param localBaseDir 本地目录
     * @param remoteBaseDir 远程目录
     * @throws Exception
     */
    /*public static void startDownDir(FtpConfig f,String localBaseDir,String remoteBaseDir) throws Exception{
        if (FtpUtil.connectFtp(f)) {
            try {
                FTPFile[] files = null;
                boolean changedir = ftp.changeWorkingDirectory(remoteBaseDir);
                if (changedir) {
                    ftp.setControlEncoding("UTF-8");
                    files = ftp.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        downloadFile(files[i], localBaseDir, remoteBaseDir);
                    }
                }else{
                    System.out.println("不存在的相对路径！");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("连接失败");
        }
    }*/

   /* public static void startDownFile(FtpConfig f,String localBaseDir,String remoteFilePath) throws Exception{
        if (FtpUtil.connectFtp(f)) {
            try {
                FileOutputStream outputStream = new FileOutputStream(localBaseDir + remoteFilePath);
                ftp.retrieveFile(remoteFilePath, outputStream);
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("连接FTP服务器失败");
        }

    }*/


    /**
     *
     * 下载FTP文件
     * 当你需要下载FTP文件的时候，调用此方法
     * 根据<b>获取的文件名，本地地址，远程地址</b>进行下载
     *
     * @param ftpFile
     * @param relativeLocalPath 下载到本地的绝对路径
     * @param relativeRemotePath 要下载的远程ftp服务器相对路径
     */
    private  static void downloadFile(FTPFile ftpFile, String relativeLocalPath,String relativeRemotePath) {
        if (ftpFile.isFile()) {
            if (ftpFile.getName().indexOf("?") == -1) {
                OutputStream outputStream = null;
                try {
                    File locaFile= new File(relativeLocalPath+ ftpFile.getName());
                    //判断文件是否存在，存在则返回  or 直接覆盖
                    if(locaFile.exists()){
                        return;
                    }else{
                        outputStream = new FileOutputStream(relativeLocalPath+ ftpFile.getName());
                        ftp.retrieveFile(ftpFile.getName(), outputStream);
                        outputStream.flush();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (outputStream != null){
                            outputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            String newlocalRelatePath = relativeLocalPath + ftpFile.getName();
            String newRemote = relativeRemotePath + ftpFile.getName().toString();
            File fl = new File(newlocalRelatePath);
            if (!fl.exists()) {
                fl.mkdirs();
            }
            try {
                newlocalRelatePath = newlocalRelatePath+File.separator;
                newRemote = newRemote+File.separator;
                String currentWorkDir = ftpFile.getName().toString();
                //System.out.println(currentWorkDir);
                boolean changedir = ftp.changeWorkingDirectory(currentWorkDir);
                if (changedir) {
                    FTPFile[] files = null;
                    files = ftp.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        downloadFile(files[i], newlocalRelatePath, newRemote);
                    }
                }
                if (changedir){
                    ftp.changeToParentDirectory();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }






   /* public static void main(String[] args) throws Exception{
        FtpConfig f=new FtpConfig();
        f.setFtpHost("172.25.69.14");
        f.setFtpPort(21);
        f.setFtpUser("anyone");
        f.setFtpPassword("");
        // f.setFtpPath("/data1/");//相对路径
        FtpUtil.connectFtp(f);
        File file = new File("E:\\data1\\physics.txt");

        //FtpUtil.upload(file);//把文件上传在ftp上
        // FtpUtil.startDownFile(f, "E:/",  "physics.txt");
        FtpUtil.startDownDir(f, "E:/data1/",  "/data1/");

    }*/
}