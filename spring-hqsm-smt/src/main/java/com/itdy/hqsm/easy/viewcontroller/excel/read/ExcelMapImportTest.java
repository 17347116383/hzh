package com.itdy.hqsm.easy.viewcontroller.excel.read;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.util.PoiPublicUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Map导入测试
 * @author
 *
 */
@RestController
@RequestMapping("/ExcelMapImportTest")
public class ExcelMapImportTest {

    @RequestMapping("/1")
    public void mapTest() {

        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        params.setDataHandler(new MapImportHandler());
        long start = new Date().getTime();
        List<Map<String, Object>> list = ExcelImportUtil.importExcel(
            new File(FileUtilTest.getWebRootPath("exceltohtml/import/check.xls")), Map.class, params);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        System.out.println(new Date().getTime() - start);
        Assert.assertEquals(20,list.size());


    }

}
