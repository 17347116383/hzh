package com.itdy.hqsm.easy.viewcontroller.excel.read;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itdy.hqsm.easy.entity.MsgClient;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;

import cn.afterturn.easypoi.util.PoiPublicUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 */
@RestController
@RequestMapping("/BigDataRead")
public class BigDataRead {

    private static final Logger LOGGER = LoggerFactory.getLogger(BigDataRead.class);

    @RequestMapping("/1")
    public void test() {
        try {
            Date start = new Date();
            LOGGER.debug("start");
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            List<MsgClient> result = ExcelImportUtil.importExcel(
                    new File(FileUtilTest.getWebRootPath("exceltohtml/import/BigDataExport.xlsx")),
                    MsgClient.class, params);
            LOGGER.debug("end,time is {}", ((new Date().getTime() - start.getTime()) / 1000));
            Assert.assertTrue(result.size() == 200000);
        } catch (Exception e) {
        }
    }
    @RequestMapping("/11")
    public void test2000() {
        try {
            Date start = new Date();
            LOGGER.debug("start");
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            List<MsgClient> result = ExcelImportUtil.importExcel(
                    new File(FileUtilTest.getWebRootPath("exceltohtml/import/BigDataExport20000.xlsx")),
                    MsgClient.class, params);
            LOGGER.debug("end,time is {}", ((new Date().getTime() - start.getTime()) / 1000));
            Assert.assertTrue(result.size() == 20000);
        } catch (Exception e) {
        }
    }

    @RequestMapping("/111")
    public void test5000() {
        try {
            Date start = new Date();
            LOGGER.debug("start");
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            List<MsgClient> result = ExcelImportUtil.importExcel(
                    new File(FileUtilTest.getWebRootPath("exceltohtml/import/BigDataExport50000.xlsx")),
                    MsgClient.class, params);
            LOGGER.debug("end,time is {}", ((new Date().getTime() - start.getTime()) / 1000));
            Assert.assertTrue(result.size() == 50000);
        } catch (Exception e) {
        }
    }
}
