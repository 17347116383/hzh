package com.itdy.hqsm.easy.viewcontroller.excel.read;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.afterturn.easypoi.util.PoiPublicUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**导入测试
 *
 */
@RestController
@RequestMapping("/ExcelSingleTest")
public class ExcelSingleTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExcelSingleTest.class);

    @RequestMapping("/1")
    public void test() {
        try {
            ImportParams params = new ImportParams();
            params.setKeyMark("：");
            params.setReadSingleCell(true);
            params.setTitleRows(7);
            params.setLastOfInvalidRow(9);
            ExcelImportResult<Map> result = ExcelImportUtil.importExcelMore(
                    new File(FileUtilTest.getWebRootPath("exceltohtml/import/业务委托单.xlsx")),
                    Map.class, params);
            for (int i = 0; i < result.getList().size(); i++) {
                System.out.println(result.getList().get(i));
            }
            Assert.assertTrue(result.getList().size() == 10);
            System.out.println(result.getMap());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
        }
    }
}
