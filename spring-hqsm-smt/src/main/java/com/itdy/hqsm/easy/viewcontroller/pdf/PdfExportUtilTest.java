
package com.itdy.hqsm.easy.viewcontroller.pdf;


import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.pdf.PdfExportUtil;
import cn.afterturn.easypoi.pdf.entity.PdfExportParams;

import org.junit.Test;

import com.itdy.hqsm.easy.entity.MsgClient;
import com.itdy.hqsm.easy.entity.MsgClientGroup;
import com.itextpdf.text.Document;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * 
 * PDF  导出
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/mongo/PdfExportUtilTest")
public class PdfExportUtilTest {


    @RequestMapping("/1")
    public void testExportPdf() {
        
        Field[] fields =  MsgClient.class.getFields();
        for (int i = 0; i < fields.length; i++) {
            Excel excel = fields[i].getAnnotation(Excel.class);
            System.out.println(excel);
        }
        
        List<MsgClient> list = new ArrayList<MsgClient>();
        for (int i = 0; i < 10; i++) {
            MsgClient client = new MsgClient();
            client.setBirthday(new Date());
            client.setClientName("小明" + i);
            client.setClientPhone("18797" + i);
            client.setCreateBy("JueYue");
            client.setId("1" + i);
            client.setRemark("测试" + i);
            MsgClientGroup group = new MsgClientGroup();
            group.setGroupName("测试" + i);
            client.setGroup(group);
            list.add(client);
        }
        Date start = new Date();
        PdfExportParams params = new PdfExportParams("2412312",null);
        try {
            File file = new File("D:/excel//PdfExportUtilTest.testExportPdf.pdf");
            file.createNewFile();
            Document document =  PdfExportUtil.exportPdf(params, MsgClient.class, list,new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }  
    }


    public void testExportPdfExportParamsListOfExcelExportEntityCollectionOfQextendsMapOfQQ() {
    }

}
