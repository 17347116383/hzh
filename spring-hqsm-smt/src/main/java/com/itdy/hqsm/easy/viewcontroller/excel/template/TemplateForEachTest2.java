package com.itdy.hqsm.easy.viewcontroller.excel.template;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.excel.export.styler.ExcelExportStylerColorImpl;
import cn.afterturn.easypoi.util.PoiMergeCellUtil;

import org.junit.Test;

import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * 导出
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/mongo/TemplateForEachTest2")
public class TemplateForEachTest2 {


    @RequestMapping("/mo")
    public void test() throws Exception {
        TemplateExportParams params = new TemplateExportParams(
            "exceltohtml/doc/foreach_many.xlsx");
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 400; i++) {
            Map<String, Object> testMap = new HashMap<String, Object>();

            testMap.put("id", "080101" + i);
            testMap.put("name", "大学" + i + "班");
            testMap.put("a1", getDeatil());
            testMap.put("a2", getDeatil());
            testMap.put("a3", getDeatil());
            testMap.put("sum1", "30" + i);
            testMap.put("sum2", "40" + i);
            mapList.add(testMap);
        }
        map.put("list", mapList);
        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        File savefile = new File("D:/excel/");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream("D:/excel/foreach_many_exprot.xlsx");
        workbook.write(fos);
        fos.close();
    }

    private Map<String, Object> getDeatil() {
        Map<String, Object> testMap = new HashMap<String, Object>();
        testMap.put("zero", (int) (Math.random() * 100));
        testMap.put("sixty", (int) (Math.random() * 100));
        testMap.put("eighty", (int) (Math.random() * 100));
        return testMap;
    }

}
