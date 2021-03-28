package com.itdy.hqsm.modes.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;


/**
 * 文件上传,下载类
 */
//@CrossOrigin(origins = "http://domain2.com", maxAge = 3600)
//@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping(value ="/file")

public class FileUploadController {

    private static final Logger log = LoggerFactory.getLogger(FileUploadController.class);


    private final static String filePath = "D://data/";

    @RequestMapping(value ="/q")
    public ModelAndView  getFile(ModelAndView  modelAndView ,HttpServletResponse response  ,HttpServletRequest request ){
        modelAndView.setViewName("userhtt");
        //modelAndView.addObject("name","123");
        //return  "userhtt";
        return modelAndView;
    }


    /**
     * 文件上传
     *
     * @param file    /file/fileUpload
     * @return
     */
    @RequestMapping(value = "/fileUpload")
    public String fileUpload(@RequestParam("fileName") MultipartFile file) {
        if (file.isEmpty()) {
            return "请选择一个文上传";
        }
        String filename = file.getOriginalFilename();
        long fileSize = file.getSize();
        System.out.println("文件名称" + filename + "-------文件大小" + fileSize);
        String path = filePath;
        File dest = new File(path + "/" + filename);
        if (!dest.getParentFile().exists()) {
            //父目录不存在就创建一个
            dest.getParentFile().mkdir();
        }
        //保存文件
        try {
            file.transferTo(dest);
            return "文件上传成功";
        } catch (IOException e) {
            e.printStackTrace();
            return "文件上传失败";
        }
    }

    /**
     * 文件下载
     *
     * @param response
     * @return
     */
    @RequestMapping(value = "/fileDownload")
    @ResponseBody
    //@ResponseStatus(code=HttpStatus.OK,reason="server成功")
    public String download(HttpServletResponse response) {
        String fileName = "Cloud总结.docx";
        String filepath = filePath;
        File file = new File(filepath + "/" + fileName);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(file));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "文件上传失败";
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "文件上传成功";
    }






    /**
     *    文件上传
     * @param file      /file/upload
     * @param request
     * @return
     */
    @RequestMapping("/upload")
   // @ResponseStatus(code=HttpStatus.OK,reason="server成功")
    public JSONObject upload(@RequestParam("file") MultipartFile file, HttpServletRequest request){

        JSONObject result = new JSONObject();
        // 姓名
        String name = request.getParameter("name");
        System.out.println("姓名：" + name);
        // 文件名
        String fileName = file.getOriginalFilename();
        System.out.println("文件名： " + fileName);
        // 文件后缀
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        System.out.println("文件后缀名： " + suffixName);
        // 重新生成唯一文件名，用于存储数据库
        String newFileName = UUID.randomUUID().toString()+suffixName;
        System.out.println("新的文件名： " + newFileName);
        //创建文件
        File dest = new File(filePath + newFileName);
        Map map = new HashMap();
        map.put("filePath", dest.getAbsolutePath());
        map.put("name", name);
        try {
            file.transferTo(dest);
            result.put("success", true);
            result.put("data", map);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (JSONObject) result.put("success", false);
    }



    @RequestMapping(value ="/qq")
    public ModelAndView index( ModelAndView modelAndView,HttpServletResponse response  ,HttpServletRequest request) {
        modelAndView.setViewName("index");
        return modelAndView;
    }


    @RequestMapping("/upload1")
    @ResponseBody
    public Map<String, String> upload1(@RequestParam("file") MultipartFile file) throws IOException {
        log.info("[文件类型] - [{}]", file.getContentType());
        log.info("[文件名称] - [{}]", file.getOriginalFilename());
        log.info("[文件大小] - [{}]", file.getSize());
        // TODO 将文件写入到指定目录（具体开发中有可能是将文件写入到云存储/或者指定目录通过 Nginx 进行 gzip 压缩和反向代理，此处只是为了演示故将地址写成本地电脑指定目录）
        file.transferTo(new File(filePath + file.getOriginalFilename()));
        Map<String, String> result = new HashMap<>(16);
        result.put("contentType", file.getContentType());
        result.put("fileName", file.getOriginalFilename());
        result.put("fileSize", file.getSize() + "");
        return result;
    }

    @RequestMapping("/upload2")
    @ResponseBody
    public List<Map<String, String>> upload2(@RequestParam("file") MultipartFile[] files) throws IOException {
        if (files == null || files.length == 0) {
            return null;
        }
        List<Map<String, String>> results = new ArrayList<>();
        for (MultipartFile file : files) {
            // TODO Spring Mvc 提供的写入方式
            file.transferTo(new File(filePath + file.getOriginalFilename()));
            Map<String, String> map = new HashMap<>(16);
            map.put("contentType", file.getContentType());
            map.put("fileName", file.getOriginalFilename());
            map.put("fileSize", file.getSize() + "");
            results.add(map);
        }
        return results;
    }

    @PostMapping("/upload3")
    @ResponseBody
    public void upload2(String base64) throws IOException {
        // TODO BASE64 方式的 格式和名字需要自己控制（如 png 图片编码后前缀就会是 data:image/png;base64,）
        final File tempFile = new File("F:\\app\\chapter16\\test.jpg");
        // TODO 防止有的传了 data:image/png;base64, 有的没传的情况
        String[] d = base64.split("base64,");
        final byte[] bytes = Base64Utils.decodeFromString(d.length > 1 ? d[1] : d[0]);
        FileCopyUtils.copy(bytes, tempFile);
    }







    @RequestMapping(value ="/ss")
    public ModelAndView im( ModelAndView modelAndView,HttpServletResponse response  ,HttpServletRequest request) {
        modelAndView.setViewName("imge");
        return modelAndView;
    }

    /**
     * 文件上传相关代码
     * @param file
     * @return
     */
    @RequestMapping(value = "sc")
    @ResponseBody
    public String upload(@RequestParam("test") MultipartFile file) {
        if (file.isEmpty()) {
            return "文件为空";
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        log.info("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        log.info("上传的后缀名为：" + suffixName);
        // 文件上传后的路径

        // 解决中文问题，liunx下中文路径，图片显示问题
        // fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            return "上传成功";
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }

    /**
     * 文件下载相关代码
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/lod")
    public String downloadFile(HttpServletRequest request, HttpServletResponse response){
        String fileName = "22.jpg.png";
        if (fileName != null) {
            //当前是从该工程的WEB-INF//File//下获取文件(该目录可以在下面一行代码配置)然后下载到C:\\users\\downloads即本机的默认下载的目录
            String realPath = filePath;
            File file = new File(realPath, fileName);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition",
                        "attachment;fileName=" + fileName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("success----------->");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }
    /**
     *    多文件上传
     */

  /*  @RequestMapping(value = "/d", method = RequestMethod.POST)
    @ResponseBody
    public String handleFileUpload(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request)
                .getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream(
                            new File(file.getOriginalFilename())));
                    stream.write(bytes);
                    stream.close();

                } catch (Exception e) {
                    stream = null;
                    return "You failed to upload " + i + " => "
                            + e.getMessage();
                }
            } else {
                return "You failed to upload " + i
                        + " because the file was empty.";
            }
        }
        return "upload successful";
    }*/




}
