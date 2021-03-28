package com.itdy.hqsm.easy.viewcontroller.excel.template;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;

import cn.afterturn.easypoi.util.PoiMergeCellUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 */
@RestController
@RequestMapping("/ForEachOfInsert")
public class ForEachOfInsert {

    @RequestMapping("/1")
    public void test() throws Exception {
        TemplateExportParams params = new TemplateExportParams(
                "exceltohtml/doc/foreach.xlsx",1);
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 4; i++) {
            Map<String, Object> testMap = new HashMap<String, Object>();

            testMap.put("id", "xman");
            testMap.put("name", "小明" + i);
            testMap.put("sex", "1");
            mapList.add(testMap);
        }
        map.put("maplist", mapList);
        //本来导出是专业那个
        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        File savefile = new File("D:/excel/");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream("D:/excel/ForEachOfInsert.foreach.xlsx");
        workbook.write(fos);
        fos.close();
    }
}
