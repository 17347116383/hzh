package com.itdy.hqsm.easy.viewcontroller.excel.read.img;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.Assert;
import org.junit.Test;

import com.itdy.hqsm.easy.entity.img.CompanyHasImgModel;
import com.itdy.hqsm.easy.viewcontroller.excel.read.FileUtilTest;

import java.io.File;
import java.util.List;
import java.util.Map;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;

import cn.afterturn.easypoi.util.PoiPublicUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping("/ExcelImportImg")
public class ExcelImportImg {

    @RequestMapping("/1")
    public void test() {
        try {
            ImportParams params = new ImportParams();
            params.setNeedSave(true);
            List<CompanyHasImgModel> result = ExcelImportUtil.importExcel(
                    new File(FileUtilTest.getWebRootPath("exceltohtml/import/imgexcel.xls")),
                    CompanyHasImgModel.class, params);
            for (int i = 0; i < result.size(); i++) {
                System.out.println("---------->"+ReflectionToStringBuilder.toString(result.get(i)));
            }
            Assert.assertTrue(result.size() == 4);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
