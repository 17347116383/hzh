
package com.itdy.hqsm.easy.viewcontroller.excel.read;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;



import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.afterturn.easypoi.util.PoiPublicUtil;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itdy.hqsm.easy.entity.ExcelVerifyEntity;
import com.itdy.hqsm.easy.entity.ExcelVerifyEntityOfMode;
import com.itdy.hqsm.easy.hanlder.ViliGroupOne;
import com.itdy.hqsm.easy.viewcontroller.excel.read.hanlder.ExcelVerifyHandlerImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Excel 导入校验
 * @author
 *
 */
@RestController
@RequestMapping("/ExcelVerifyTest")
public class ExcelVerifyTest {
    
    private final static  Logger  LOGGER = LoggerFactory.getLogger(ExcelVerifyTest.class);

    @RequestMapping("/1")
    public void basetest() {
        try {
            ImportParams params = new ImportParams();
            params.setNeedVerfiy(true);
            params.setVerfiyGroup(new Class[]{ViliGroupOne.class});
            ExcelImportResult<ExcelVerifyEntity> result = ExcelImportUtil.importExcelMore(
                new File(FileUtilTest.getWebRootPath("exceltohtml/import/verfiy.xlsx")),
                ExcelVerifyEntity.class, params);
            FileOutputStream fos = new FileOutputStream("D:\\excel\\临时文件.xlsx");
            result.getWorkbook().write(fos);
            fos.close();
            for (int i = 0; i < result.getList().size(); i++) {
                System.out.println("-------->"+ReflectionToStringBuilder.toString(result.getList().get(i)));
            }
            Assert.assertTrue(result.getList().size() == 2);
            Assert.assertTrue(result.isVerfiyFail());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
        }
    }

    @RequestMapping("/2")
    public void basetestonlyFail() {
        try {
            ImportParams params = new ImportParams();
            params.setNeedVerfiy(true);
            params.setVerfiyGroup(new Class[]{ViliGroupOne.class});
            ExcelImportResult<ExcelVerifyEntity> result = ExcelImportUtil.importExcelMore(
                    new File(FileUtilTest.getWebRootPath("exceltohtml/import/verfiy.xlsx")),
                    ExcelVerifyEntity.class, params);
            FileOutputStream fos = new FileOutputStream("D:\\excel\\临时文件(1).xlsx");
            result.getWorkbook().write(fos);
            fos.close();
            for (int i = 0; i < result.getList().size(); i++) {
                System.out.println(ReflectionToStringBuilder.toString(result.getList().get(i)));
            }
            System.out.println("---------------------- fail -------------------");
            fos = new FileOutputStream("D:\\excel\\临时文件.xlsx");
            result.getFailWorkbook().write(fos);
            fos.close();
            //失败的数据
            for (int i = 0; i < result.getFailList().size(); i++) {
                System.out.println(ReflectionToStringBuilder.toString(result.getFailList().get(i)));
            }
            Assert.assertTrue(result.getList().size() == 2);
            Assert.assertTrue(result.isVerfiyFail());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
        }
    }

    @RequestMapping("/3")
    public void baseModetest() {
        try {
            ImportParams params = new ImportParams();
            params.setNeedVerfiy(true);
            ExcelImportResult<ExcelVerifyEntityOfMode> result = ExcelImportUtil.importExcelMore(
                    new FileInputStream(new File(FileUtilTest.getWebRootPath("exceltohtml/import/verfiy.xlsx"))),
                ExcelVerifyEntityOfMode.class, params);
            FileOutputStream fos = new FileOutputStream("D:\\excel\\临时文件.xlsx");
            result.getWorkbook().write(fos);
            fos.close();
            fos = new FileOutputStream("D:\\excel\\临时文件(1).xlsx");
            result.getFailWorkbook().write(fos);
            fos.close();
            for (int i = 0; i < result.getList().size(); i++) {
                System.out.println(ReflectionToStringBuilder.toString(result.getList().get(i)));
            }
            //失败的数据
            for (int i = 0; i < result.getFailList().size(); i++) {
                System.out.println(ReflectionToStringBuilder.toString(result.getFailList().get(i)));
            }
            Assert.assertTrue(result.getList().size() == 1);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
        }
    }

    @RequestMapping("/4")
    public void baseHandlertest() {
        try {
            ImportParams params = new ImportParams();
            params.setVerifyHandler(new ExcelVerifyHandlerImpl());
            ExcelImportResult<ExcelVerifyEntityOfMode> result = ExcelImportUtil.importExcelMore(
                new File(FileUtilTest.getWebRootPath("exceltohtml/import/verfiy.xlsx")),
                ExcelVerifyEntityOfMode.class, params);
            FileOutputStream fos = new FileOutputStream("D:\\excel\\临时文件(1).xlsx");
            result.getWorkbook().write(fos);
            fos.close();
            for (int i = 0; i < result.getList().size(); i++) {
                System.out.println(ReflectionToStringBuilder.toString(result.getList().get(i)));
            }
            Assert.assertTrue(result.getList().size() == 0);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
        }
    }

    @RequestMapping("/5")
    public void combinedVerificationTest() {
        try {
            ImportParams params = new ImportParams();
            params.setVerifyHandler(new ExcelVerifyHandlerImpl());
            params.setNeedVerfiy(true);
            ExcelImportResult<ExcelVerifyEntityOfMode> result = ExcelImportUtil.importExcelMore(
                new File(FileUtilTest.getWebRootPath("exceltohtml/import/verfiy.xlsx")),
                ExcelVerifyEntityOfMode.class, params);
            FileOutputStream fos = new FileOutputStream("D:\\excel\\临时文件.xlsx");
            result.getWorkbook().write(fos);
            fos.close();
            for (int i = 0; i < result.getList().size(); i++) {
                System.out.println(ReflectionToStringBuilder.toString(result.getList().get(i)));
            }
            Assert.assertTrue(result.getList().size() == 0);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
        }
    }

}
