
package com.itdy.hqsm.easy.poi.cache;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

import cn.afterturn.easypoi.cache.manager.POICacheManager;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;

import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 
 * 
 * 将数据写入模板文件中 保存到某个路径下
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/POICacheManager")
public class POICacheManagerTest {

    //@Test
	@RequestMapping("/poic")
    public void test() throws Exception {
        //设置成为自己的文件加载
        //POICacheManager.setFileLoder(new MyFileLoader());
        
        //会吧这个对象ThreadLocal中,线程结束就不起作用了
        //POICacheManager.setFileLoderOnce(new MyFileLoader());
        
        
        TemplateExportParams params = new TemplateExportParams(
                "exceltohtml/doc/merge_test.xls");
            Map<String, Object> map = new HashMap<String, Object>();
    
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();

            for (int i = 0; i < 8; i++) {
                Map<String, String> m = new HashMap<String, String>();
                m.put("id", "id" + "1");
                m.put("uname", "name" + "1");
                m.put("amount", i+"");
                list.add(m);
            }
            map.put("list", list);
            Workbook workbook = ExcelExportUtil.exportExcel(params, map);
            File savefile = new File("D:/excel/");
            if (!savefile.exists()) {
                savefile.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream("D:/excel/tt.xls");
            workbook.write(fos);
            fos.close();
    }

}
