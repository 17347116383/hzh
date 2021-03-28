package com.itdy.hqsm.easy.poi.html;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.afterturn.easypoi.excel.ExcelXorHtmlUtil;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;

import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Scanner;
import java.io.File;
import java.io.FileOutputStream;

/**
 * 读取html中的值 
 * @author 
 *
 */
@Controller
@RequestMapping("/HtmlToExcelUtilTest")
public class HtmlToExcelUtilTest {

    //@Test

    @RequestMapping("/1")
    public void htmlToExcelByStr() throws Exception {
        StringBuilder html = new StringBuilder();
        //获取模板
        Scanner s = new Scanner(getClass().getResourceAsStream("exceltohtml/html/sample.html"), "utf-8");
        while (s.hasNext()) {
            html.append(s.nextLine());
        }
        s.close();
        Workbook workbook = ExcelXorHtmlUtil.htmlToExcel(html.toString(), ExcelType.XSSF);
        //创建文件路径
        File savefile = new File("D:\\home\\lemur");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        //将路数据写入文件中
        FileOutputStream fos = new FileOutputStream("D:\\home\\lemur\\htmlToExcelByStr.xlsx");
        workbook.write(fos);
        fos.close();
        workbook = ExcelXorHtmlUtil.htmlToExcel(html.toString(), ExcelType.HSSF);
        fos = new FileOutputStream("D:\\home\\lemur\\htmlToExcelByStr.xls");
        workbook.write(fos);
        fos.close();
    }

    
    
    
    
    //@Test

    @RequestMapping("/2")
    public void htmlToExcelByIs() throws Exception {
        Workbook workbook = ExcelXorHtmlUtil.htmlToExcel(getClass().getResourceAsStream("exceltohtml/html/sample.html"), ExcelType.XSSF);
        File savefile = new File("D:\\home\\lemur");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream("D:\\home\\lemur\\htmlToExcelByIs.xlsx");
        workbook.write(fos);
        fos.close();
        workbook = ExcelXorHtmlUtil.htmlToExcel(getClass().getResourceAsStream("exceltohtml/html/sample.html"), ExcelType.HSSF);
        fos = new FileOutputStream("D:\\home\\lemur\\htmlToExcelByIs.xls");
        workbook.write(fos);
        fos.close();
    }

}
