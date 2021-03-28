package com.itdy.hqsm.easy.viewcontroller.excel.read;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;

import cn.afterturn.easypoi.util.PoiPublicUtil;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.Assert;
import org.junit.Test;

import com.itdy.hqsm.easy.en.EnumDataEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * 
 * 
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/ExcelImportEnumUtilTest")
public class ExcelImportEnumUtilTest {

    ///ExcelExportMsgClient 测试是这个到处的数据

    @RequestMapping("/1")
    public void test() {
        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            long start = new Date().getTime();
            List<EnumDataEntity> list = ExcelImportUtil.importExcel(
                    new FileInputStream(
                            new File(FileUtilTest.getWebRootPath("exceltohtml/import/EnumDataEntity.xlsx"))),
                    EnumDataEntity.class, params);

            Assert.assertEquals(6, list.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
