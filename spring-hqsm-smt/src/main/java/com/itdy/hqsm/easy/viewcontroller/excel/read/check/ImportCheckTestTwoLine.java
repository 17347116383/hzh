
package com.itdy.hqsm.easy.viewcontroller.excel.read.check;

import java.io.File;
import java.util.List;


import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;

import cn.afterturn.easypoi.util.PoiPublicUtil;

import org.junit.Assert;
import org.junit.Test;

import com.itdy.hqsm.easy.entity.CourseEntity;
import com.itdy.hqsm.easy.entity.CourseEntityNoAnn;
import com.itdy.hqsm.easy.viewcontroller.excel.read.FileUtilTest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 输入模板校验
 * 双行版本
 * @author
 *
 */
@Controller
@RequestMapping("/mongo/ImportCheckTestTwoLine")
public class ImportCheckTestTwoLine {

    @RequestMapping("/1")
    public void testTwoLine() {
        boolean isOK = true;
        try {
            ImportParams params = new ImportParams();
            params.setHeadRows(2);
            List<CourseEntity> list = ExcelImportUtil.importExcel(
                new File(FileUtilTest.getWebRootPath("exceltohtml/import/checkTwo.xls")),
                CourseEntity.class, params);
        } catch (Exception e) {
            e.printStackTrace();
            isOK = false;
        }
        Assert.assertEquals(false,isOK);
    }

    @RequestMapping("/132")
    public void testTwoLineError() {
        boolean isOK = true;
        try {
            ImportParams params = new ImportParams();
            params.setHeadRows(2);
            params.setStartSheetIndex(1);
            List<CourseEntity> list = ExcelImportUtil.importExcel(
                new File(FileUtilTest.getWebRootPath("exceltohtml/import/checkTwo.xls")),
                CourseEntity.class, params);
        } catch (Exception e) {
            e.printStackTrace();
            isOK = false;
        }
        Assert.assertTrue(!isOK);
    }

    @RequestMapping("/13223")
    public void testTwoLineByParams() {
        boolean isOK = true;
        try {
            ImportParams params = new ImportParams();
            params.setHeadRows(2);
            params.setImportFields(new String[]{"课程名称","老师姓名","学生_学生姓名","学生_学生性别","学生_出生日期"});
            List<CourseEntityNoAnn> list = ExcelImportUtil.importExcel(
                new File(FileUtilTest.getWebRootPath("exceltohtml/import/checkTwo.xls")),
                CourseEntityNoAnn.class, params);
        } catch (Exception e) {
            e.printStackTrace();
            isOK = false;
        }
        Assert.assertTrue(isOK);
    }

    @RequestMapping("/1322378")
    public void testTwoLineErrorByParams() {
        boolean isOK = true;
        try {
            ImportParams params = new ImportParams();
            params.setHeadRows(2);
            params.setStartSheetIndex(1);
            params.setImportFields(new String[]{"课程名称","老师姓名","学生_学生姓名","学生_学生性别","学生_出生日期","学生_进校日期"});
            List<CourseEntityNoAnn> list = ExcelImportUtil.importExcel(
                new File(FileUtilTest.getWebRootPath("exceltohtml/import/checkTwo.xls")),
                CourseEntityNoAnn.class, params);
        } catch (Exception e) {
            e.printStackTrace();
            isOK = false;
        }
        Assert.assertTrue(!isOK);
    }

}
