package com.itdy.hqsm.easy.viewcontroller.excel.export;

import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import com.itdy.hqsm.easy.entity.MsgClient;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Excel 换行导出
 *
 */
@Controller
@RequestMapping("/ExcelExportUtilTest")
public class ExcelExportWarp {

    @RequestMapping("/1")
    public void test03() throws Exception {

        Field[] fields =  MsgClient.class.getFields();
        for (int i = 0; i < fields.length; i++) {
            Excel excel = fields[i].getAnnotation(Excel.class);
            System.out.println(excel);
        }

        List<MsgClient> list = new ArrayList<MsgClient>();
        for (int i = 0; i < 100; i++) {
            MsgClient client = new MsgClient();
            client.setBirthday(new Date());
            client.setClientName("名称:\n完美\n小明" + i);
            client.setClientPhone("18797" + i);
            client.setCreateBy("用户:\nJueYue");
            client.setId("1" + i);
            client.setRemark("测试" + i);
            list.add(client);
        }
        Date start = new Date();
        ExportParams params = new ExportParams("换行测试", "换行测试", ExcelType.HSSF);
        params.setFreezeCol(2);
        params.setHeight((short) -1);
        Workbook workbook = ExcelExportUtil.exportExcel(params, MsgClient.class, list);
        System.out.println(new Date().getTime() - start.getTime());
        File savefile = new File("D:/excel/");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream("D:/excel/ExcelExportWarp.xls");
        workbook.write(fos);
        fos.close();
    }

    @RequestMapping("/2")
    public void test07() throws Exception {

        Field[] fields =  MsgClient.class.getFields();
        for (int i = 0; i < fields.length; i++) {
            Excel excel = fields[i].getAnnotation(Excel.class);
            System.out.println(excel);
        }

        List<MsgClient> list = new ArrayList<MsgClient>();
        for (int i = 0; i < 100; i++) {
            MsgClient client = new MsgClient();
            client.setBirthday(new Date());
            client.setClientName("名称:\n完美\n小明" + i);
            client.setClientPhone("18797" + i);
            client.setCreateBy("用户:\nJueYue");
            client.setId("1" + i);
            client.setRemark("测试" + i);
            list.add(client);
        }
        Date start = new Date();
        ExportParams params = new ExportParams("换行测试", "换行测试", ExcelType.XSSF);
        params.setFreezeCol(2);
        Workbook workbook = ExcelExportUtil.exportExcel(params, MsgClient.class, list);
        System.out.println(new Date().getTime() - start.getTime());
        File savefile = new File("D:/excel/");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream("D:/excel/ExcelExportWarp.xlsx");
        workbook.write(fos);
        fos.close();
    }


    @RequestMapping("/3")
    public void testHeight03() throws Exception {

        Field[] fields =  MsgClient.class.getFields();
        for (int i = 0; i < fields.length; i++) {
            Excel excel = fields[i].getAnnotation(Excel.class);
            System.out.println(excel);
        }

        List<MsgClient> list = new ArrayList<MsgClient>();
        for (int i = 0; i < 100; i++) {
            MsgClient client = new MsgClient();
            client.setBirthday(new Date());
            client.setClientName("名称:完美小明sdsakjdaslkjdkjlsj kjsadlkjsadlkja jsaldkjsa lkjsadlkjsad " + i);
            client.setClientPhone("18797" + i);
            client.setCreateBy("用户:\nJueYue");
            client.setId("1" + i);
            client.setRemark("测试" + i);
            list.add(client);
        }
        Date start = new Date();
        ExportParams params = new ExportParams("换行测试", "换行测试", ExcelType.HSSF);
        params.setFreezeCol(2);
        params.setHeight((short) -1);
        Workbook workbook = ExcelExportUtil.exportExcel(params, MsgClient.class, list);
        System.out.println(new Date().getTime() - start.getTime());
        File savefile = new File("D:/excel/");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream("D:/excel/ExcelExportAutoHeight.xls");
        workbook.write(fos);
        fos.close();
    }
}
