
package com.itdy.hqsm.easy.viewcontroller.excel.read.check;

import java.io.File;
import java.util.List;



import org.junit.Assert;
import org.junit.Test;

import com.itdy.hqsm.easy.viewcontroller.excel.read.FileUtilTest;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 输入模板校验
 * 单行版本
 * @author
 *
 */
@Controller
@RequestMapping("/mongo/ImportCheckTestOneLine")
public class ImportCheckTestOneLine {


    @RequestMapping("/11")
    public void testOneLine() {
        boolean isOK = true;
        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            List<ImportCheckOneLine> list = ExcelImportUtil.importExcel(
                new File(FileUtilTest.getWebRootPath("exceltohtml/import/check.xls")),
                ImportCheckOneLine.class, params);
        } catch (Exception e) {
            e.printStackTrace();
            isOK = false;
        }
        Assert.assertTrue(!isOK);
    }

    @RequestMapping("/1122")
    public void testOneLineError() {
        boolean isOK = true;
        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setStartSheetIndex(1);
            List<ImportCheckOneLine> list = ExcelImportUtil.importExcel(
                new File(FileUtilTest.getWebRootPath("exceltohtml/import/check.xls")),
                ImportCheckOneLine.class, params);
        } catch (Exception e) {
            e.printStackTrace();
            isOK = false;
        }
        Assert.assertTrue(!isOK);
    }

    @RequestMapping("/1176")
    public void testOneLineByParams() {
        boolean isOK = true;
        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setImportFields(new String[] { "姓名", "性别", "年纪", "爱好" });
            List<ImportCheckOneLineNoAnn> list = ExcelImportUtil.importExcel(
                new File(FileUtilTest.getWebRootPath("exceltohtml/import/check.xls")),
                ImportCheckOneLineNoAnn.class, params);
        } catch (Exception e) {
            e.printStackTrace();
            isOK = false;
        }
        Assert.assertTrue(!isOK);
    }

    @RequestMapping("/23423")
    public void testOneLineErrorByParams() {
        boolean isOK = true;
        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setStartSheetIndex(1);
            params.setImportFields(new String[] { "姓名", "性别", "年纪", "爱好" });
            List<ImportCheckOneLineNoAnn> list = ExcelImportUtil.importExcel(
                new File(FileUtilTest.getWebRootPath("exceltohtml/import/check.xls")),
                ImportCheckOneLineNoAnn.class, params);
        } catch (Exception e) {
            e.printStackTrace();
            isOK = false;
        }
        Assert.assertTrue(!isOK);
    }

}
