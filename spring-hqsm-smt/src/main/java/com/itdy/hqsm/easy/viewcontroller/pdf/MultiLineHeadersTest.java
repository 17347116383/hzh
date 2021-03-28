
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

import com.itdy.hqsm.easy.entity.CourseEntity;
import com.itdy.hqsm.easy.entity.StudentEntity;
import com.itdy.hqsm.easy.entity.TeacherEntity;
import com.itextpdf.text.Document;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 多行表头导出  
 * 
 * PDF  导出
 * @author
 *
 */
@RestController
@RequestMapping("/mongo/MultiLineHeadersTest")
public class MultiLineHeadersTest {

    static List<CourseEntity> list = new ArrayList<CourseEntity>();
   static CourseEntity       courseEntity;


    public static void testBefore() {

        for (int i = 0; i < 50; i++) {
            courseEntity = new CourseEntity();
            courseEntity.setId("1131");
            courseEntity.setName("海贼王必修(" + (i + 1) + ")");

            TeacherEntity teacherEntity = new TeacherEntity();
            teacherEntity.setId("12131231");
            teacherEntity.setName("路飞");
            courseEntity.setChineseTeacher(teacherEntity);

            teacherEntity = new TeacherEntity();
            teacherEntity.setId("121312314312421131");
            teacherEntity.setName("老王");
            courseEntity.setMathTeacher(teacherEntity);

            StudentEntity studentEntity = new StudentEntity();
            studentEntity.setId("11231");
            studentEntity.setName("撒旦法司法局");
            studentEntity.setBirthday(new Date());
            studentEntity.setSex(1);
            List<StudentEntity> studentList = new ArrayList<StudentEntity>();
            studentList.add(studentEntity);
            studentList.add(studentEntity);
            courseEntity.setStudents(studentList);
            list.add(courseEntity);
        }
    }


    @RequestMapping("/m1")
    public void testExportPdf() {
        PdfExportParams params = new PdfExportParams("绝月的测试", "作者绝月");
        Document document = null;
        try {
            File file = new File("D:/excel//MultiLineHeadersTest.testExportPdf.pdf");
            file.createNewFile();
            document = PdfExportUtil.exportPdf(params, CourseEntity.class, list,
                new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

}
