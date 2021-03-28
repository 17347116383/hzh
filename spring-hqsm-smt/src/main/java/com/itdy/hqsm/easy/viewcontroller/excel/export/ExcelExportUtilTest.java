package com.itdy.hqsm.easy.viewcontroller.excel.export;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;

import org.junit.Before;
import org.junit.Test;

import com.itdy.hqsm.easy.entity.CourseEntity;
import com.itdy.hqsm.easy.entity.StudentEntity;
import com.itdy.hqsm.easy.entity.TeacherEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 */
@Controller
@RequestMapping("/mogo/ExcelExportUtilTest")
public class ExcelExportUtilTest {

   static List<CourseEntity> list = new ArrayList<CourseEntity>();
    static CourseEntity       courseEntity;

    /**
     * 25W行导出测试
     * 
     * @throws Exception
     */
    @RequestMapping("/1")
    public void oneHundredThousandRowTest() throws Exception {

        ExportParams params = new ExportParams("2412312", "测试", ExcelType.XSSF);
        Date start = new Date();
        Workbook workbook = ExcelExportUtil.exportExcel(params, CourseEntity.class, list);
        System.out.println(new Date().getTime() - start.getTime());
        File savefile = new File("D:/excel/");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream("D:/excel/oneHundredThousandRowTest.xlsx");
        workbook.write(fos);
        fos.close();
        //        savefile = new File("D:/excel/1");
        //        if (!savefile.exists()) {
        //            savefile.setWritable(true, false);
        //            savefile.mkdirs();
        //        }
        //        fos = new FileOutputStream("D:/excel/1/tt3.xlsx");
        //        workbook.write(fos);
        //        fos.close();
    }


    public static  void testBefore() {

        for (int i = 0; i < 2; i++) {
            courseEntity = new CourseEntity();
            courseEntity.setId("1131");
            courseEntity.setName("海贼王必修(" + (i + 1) + ")");

            TeacherEntity teacherEntity = new TeacherEntity();
            teacherEntity.setId("12131231");
            teacherEntity.setName("路飞");
            courseEntity.setChineseTeacher(teacherEntity);

            teacherEntity = new TeacherEntity();
            teacherEntity.setId("121312314312421131");
            teacherEntity.setName("老王" +i);
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

    /**
     * 基本导出测试
     *
     * @throws Exception
     */
    @RequestMapping("/1879")
    public void testStudentList() throws Exception {
        Date start = new Date();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生","学生"),
                StudentEntity.class, list.get(0).getStudents());
        System.out.println(new Date().getTime() - start.getTime());
        File savefile = new File("D:/excel/");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream("D:/excel/testStudentList.xls");
        workbook.write(fos);
        fos.close();
    }

    /**
     * 基本导出测试
     * 
     * @throws Exception
     */
    @RequestMapping("/2")
    public void testExportExcel() throws Exception {
        Date start = new Date();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("2412312", "测试", "测试"),
            CourseEntity.class, list);
        System.out.println(new Date().getTime() - start.getTime());
        File savefile = new File("D:/excel/");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream("D:/excel/基本导出测试.xls");
        workbook.write(fos);
        fos.close();
    }

    /**
     * 单行表头测试
     * 
     * @throws Exception
     */
    @RequestMapping("/3")
    public void testExportTitleExcel() throws Exception {
        Date start = new Date();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("2412312", "测试"),
            CourseEntity.class, list);
        System.out.println(new Date().getTime() - start.getTime());
        File savefile = new File("D:/excel/");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream("D:/excel/testExportTitleExcel.xls");
        workbook.write(fos);
        fos.close();
    }

}
