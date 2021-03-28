package com.itdy.hqsm.easy.viewcontroller.excel.export;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;

import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import com.itdy.hqsm.easy.en.EnumDataEntity;
import com.itdy.hqsm.easy.en.Sex;
import com.itdy.hqsm.easy.en.StatusEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author
 */
@Controller
@RequestMapping("/ExcelExportEnumTest")
public class ExcelExportEnumTest {

    @RequestMapping("/1")
    public void test() throws Exception {

        List<EnumDataEntity> list = new ArrayList<EnumDataEntity>();
        for (int i = 0; i < 100; i++) {
            EnumDataEntity client = new EnumDataEntity();
            client.setName("小明" + i);
            client.setSex(Sex.MAN);
            client.setStatus(StatusEnum.Init);
            client.setBaseStatus(StatusEnum.Ready);
            list.add(client);
        }
        Date start = new Date();
        ExportParams params = new ExportParams("枚举测试", "测试", ExcelType.XSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(params, EnumDataEntity.class, list);
        System.out.println(new Date().getTime() - start.getTime());
        FileOutputStream fos = new FileOutputStream("D:/excel/EnumDataEntity.xlsx");
        workbook.write(fos);
        fos.close();
    }
}
