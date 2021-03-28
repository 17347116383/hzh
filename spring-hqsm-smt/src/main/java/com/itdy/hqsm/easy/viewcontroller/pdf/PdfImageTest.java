
package com.itdy.hqsm.easy.viewcontroller.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import cn.afterturn.easypoi.pdf.PdfExportUtil;
import cn.afterturn.easypoi.pdf.entity.PdfExportParams;

import org.junit.Before;
import org.junit.Test;

import com.itdy.hqsm.easy.entity.StudentEntityImage;
import com.itextpdf.text.Document;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Pdf 图片的导出
 * @author
 *
 */
@RestController
@RequestMapping("/mongo/PdfImageTest")
public class PdfImageTest {

    static  List<StudentEntityImage> studentList = new ArrayList<StudentEntityImage>();


    public static void addStudent() {
        StudentEntityImage StudentEntityImage = new StudentEntityImage();
        StudentEntityImage.setId("11231");
        StudentEntityImage.setName("撒旦法司法局");
        StudentEntityImage.setBirthday(new Date());
        StudentEntityImage.setSex(1);
        StudentEntityImage.setImage("/spring-z-web/src/main/resources/static/imgs/test.png");
        studentList.add(StudentEntityImage);
        studentList.add(StudentEntityImage);
    }


    @RequestMapping("/1")
    public void imageTest() {


        PdfExportParams params = new PdfExportParams("学生信息");
        try {
            File file = new File("D:/excel//PdfImageTest.pdf");
            file.createNewFile();
            PdfExportUtil.exportPdf(params, StudentEntityImage.class, studentList,
                new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
