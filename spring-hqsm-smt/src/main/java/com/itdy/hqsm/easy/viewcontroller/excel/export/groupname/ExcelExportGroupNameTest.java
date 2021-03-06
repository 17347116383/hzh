package com.itdy.hqsm.easy.viewcontroller.excel.export.groupname;


import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import com.itdy.hqsm.easy.entity.groupname.GnEntity;
import com.itdy.hqsm.easy.entity.groupname.GnStudentEntity;
import com.itdy.hqsm.easy.entity.groupname.GroupExportVo;
import com.itdy.hqsm.easy.entity.groupname.GroupNameEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 
 * 
 * 
 * Created 
 */
@Controller
@RequestMapping("/ExcelExportGroupNameTest")
public class ExcelExportGroupNameTest {

	
	
	
    //@Test
    @RequestMapping("/1")
    public void base() throws Exception {
        List<GroupNameEntity> list = new ArrayList<GroupNameEntity>();
        for (int i = 0; i < 10; i++) {
            GroupNameEntity client = new GroupNameEntity();
            client.setBirthday(new Date());
            client.setCreateBy("2017-10-01");
            client.setClientName("小明" + i);
            client.setClientPhone("18797" + i);
            client.setCreateBy("JueYue");
            client.setId("1" + i);
            client.setRemark("测试" + i);
            list.add(client);
        }
        Date start = new Date();
        ExportParams params = new ExportParams("GroupName测试", "测试1111", ExcelType.XSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(params, GroupNameEntity.class, list);
        File savefile = new File("D:/excel/");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream("D:/excel/groupName.xlsx");
        workbook.write(fos);
        fos.close();
    }





    @RequestMapping("/2")
    public void entity() throws Exception {

        List<GnEntity> list = new ArrayList<GnEntity>();
        for (int i = 0; i < 10; i++) {
            GnEntity client = new GnEntity();
            client.setClientName("小明" + i);
            client.setClientPhone("18797" + i);
            GnStudentEntity studentEntity = new GnStudentEntity();
            studentEntity.setBirthday(new Date());
            studentEntity.setRegistrationDate(new Date());
            studentEntity.setName("JueYue" + i);
            studentEntity.setSex(i % 2);
            client.setStudentEntity(studentEntity);
            list.add(client);
        }
        Date start = new Date();
        ExportParams params = new ExportParams("GroupNameGnEntity测试", "测试2222", ExcelType.XSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(params, GnEntity.class, list);
        File savefile = new File("D:/excel/");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream("D:/excel/groupName_GnEntity.xlsx");
        workbook.write(fos);
        fos.close();
    }







    @RequestMapping("/3")
    public void groupSort() throws Exception {
        List<GroupExportVo> list = new ArrayList<GroupExportVo>();
        ExportParams params = new ExportParams("Group排序", "测试3333", ExcelType.XSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(params, GroupExportVo.class, list);
        File savefile = new File("D:/excel/");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream("D:/excel/groupName_groupSort.xlsx");
        workbook.write(fos);
        fos.close();
    }
}
