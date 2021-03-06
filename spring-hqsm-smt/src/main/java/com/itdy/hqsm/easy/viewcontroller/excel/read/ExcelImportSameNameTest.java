package com.itdy.hqsm.easy.viewcontroller.excel.read;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.poi.ss.usermodel.Workbook;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.util.PoiPublicUtil;

import org.junit.Test;

import com.itdy.hqsm.easy.entity.StudentEntity;
import com.itdy.hqsm.easy.entity.temp.ClassName;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 同名列导入测试
 * @author
 *
 */
@RestController
@RequestMapping("/ExcelImportSameNameTest")
public class ExcelImportSameNameTest {

    @RequestMapping("/1")
    public void exportTest() {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId("11231");
        studentEntity.setName("撒旦法司法局");
        studentEntity.setBirthday(new Date());
        studentEntity.setRegistrationDate(new java.sql.Time(new Date().getTime()));
        studentEntity.setSex(1);
        List<StudentEntity> studentList = new ArrayList<StudentEntity>();
        studentList.add(studentEntity);
        studentList.add(studentEntity);

        List<ClassName> list = new ArrayList<ClassName>();
        ClassName classes = new ClassName();
        classes.setName("班级1");
        classes.setArrA(studentList);
        classes.setArrB(studentList);
        list.add(classes);
        classes = new ClassName();
        classes.setName("班级2");
        classes.setArrA(studentList);
        classes.setArrB(studentList);
        list.add(classes);
        ExportParams params = new ExportParams();
        try {
            Workbook workbook = ExcelExportUtil.exportExcel(params, ClassName.class, list);
            FileOutputStream fos = new FileOutputStream(
                FileUtilTest.getWebRootPath("exceltohtml/import/sameName.xls"));
            workbook.write(fos);
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @RequestMapping("/2")
    public void importTest() {
        ImportParams params = new ImportParams();
        params.setHeadRows(2);
        long start = new Date().getTime();
        List<ClassName> list = ExcelImportUtil.importExcel(
            new File(FileUtilTest.getWebRootPath("exceltohtml/import/sameName.xls")), ClassName.class, params);
        System.out.println(new Date().getTime() - start);
        System.out.println(list.size());
        System.out.println(ReflectionToStringBuilder.toString(list.get(0)));
        System.out.println(ReflectionToStringBuilder.toString(list.get(0).getArrA().get(0)));

    }

}
