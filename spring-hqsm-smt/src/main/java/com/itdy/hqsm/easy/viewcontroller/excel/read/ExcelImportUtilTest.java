package com.itdy.hqsm.easy.viewcontroller.excel.read;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.util.PoiPublicUtil;

import org.junit.Assert;
import org.junit.Test;

import com.itdy.hqsm.easy.entity.MsgClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * 导入测试
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/ExcelImportUtilTest")
public class ExcelImportUtilTest {

    ///ExcelExportMsgClient 测试是这个到处的数据

    @RequestMapping("/1")
    public void test() {
        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            long start = new Date().getTime();
            List<MsgClient> list = ExcelImportUtil.importExcelBySax(
                new FileInputStream(
                    new File(FileUtilTest.getWebRootPath("exceltohtml/import/ExcelExportMsgClient.xlsx"))),
                    MsgClient.class, params);
            for (int i = 0; i < list.size(); i++) {
                System.out.println("------->"+ReflectionToStringBuilder.toString(list.get(i)));
            }
            Assert.assertEquals(100,list.size());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    @RequestMapping("/2")
    public void test2() {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        long start = new Date().getTime();
        List<MsgClient> list = ExcelImportUtil.importExcel(
           new File(FileUtilTest.getWebRootPath("exceltohtml/import/ExcelExportMsgClient.xlsx")),
            MsgClient.class, params);                   
        System.out.println(new Date().getTime() - start);
        Assert.assertEquals(100,list.size());
        System.out.println("------------->"+ReflectionToStringBuilder.toString(list.get(0)));
    }

    @RequestMapping("/3")
    public void testMapImport() {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        long start = new Date().getTime();
        List<Map<String, Object>> list = ExcelImportUtil.importExcel(
            new File(FileUtilTest.getWebRootPath("exceltohtml/import/ExcelExportMsgClient.xlsx")), Map.class,
            params);
        System.out.println(new Date().getTime() - start);
        Assert.assertEquals(100,list.size());
        System.out.println("------------->"+list.get(0));
    }
}
