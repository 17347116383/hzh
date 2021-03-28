package com.itdy.hqsm.easy.poi.cache;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 *  把数据导入到指定的文件目录下
 * Created 
 */

@Controller
@RequestMapping("/ExcelLombokExport")
public class ExcelLombokExport {

	@RequestMapping("/excel")
    public void test() throws Exception {
        List<MsgClient> list = new ArrayList<MsgClient>();
        for (int i = 0; i < 100; i++) {
            MsgClient client = new MsgClient();
			
			  client.setBirthday(new Date()); 
			  client.setClientName("小明" + i);
			  client.setClientPhone("18797" + i);
			  client.setRemark("测试" + i);
			 
            list.add(client);
        }
        Date start = new Date();
        ExportParams params = new ExportParams("2412312", "测试", ExcelType.XSSF);
        params.setFreezeCol(2);
        Workbook workbook = ExcelExportUtil.exportExcel(params, MsgClient.class, list);
        System.out.println(000000000000+new Date().getTime() - start.getTime());
        File savefile = new File("D:/excel/");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream("D:/excel/ExcelLombokExport.xlsx");
        workbook.write(fos);
        fos.close();
    }
}
