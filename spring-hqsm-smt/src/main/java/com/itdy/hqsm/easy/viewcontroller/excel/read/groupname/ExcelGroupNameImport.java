package com.itdy.hqsm.easy.viewcontroller.excel.read.groupname;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.Assert;
import org.junit.Test;

import com.itdy.hqsm.easy.entity.groupname.GnEntity;
import com.itdy.hqsm.easy.entity.groupname.GroupNameEntity;
import com.itdy.hqsm.easy.viewcontroller.excel.read.FileUtilTest;

import java.io.File;
import java.util.Date;
import java.util.List;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;

import cn.afterturn.easypoi.util.PoiPublicUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther
 */
@RestController
@RequestMapping("/ExcelGroupNameImport")
public class ExcelGroupNameImport {


    @RequestMapping("/1")
    @Test
    public void groupNameTest() {
        ImportParams params = new ImportParams();
        params.setHeadRows(2);
        long start = new Date().getTime();
        List<GroupNameEntity> list = ExcelImportUtil.importExcel(
                new File(FileUtilTest.getWebRootPath("/exceltohtml/import/groupName.xlsx")), GroupNameEntity.class, params);
        System.out.println(new Date().getTime() - start);
        Assert.assertEquals(10,list.size());
        Assert.assertEquals("187970",list.get(0).getClientPhone());
        Assert.assertEquals("小明0",list.get(0).getClientName());
        System.out.println("---------------"+ReflectionToStringBuilder.toString(list.get(0)));

    }


    @RequestMapping("/2")
    public void groupNameEntityTest() {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(2);
        long start = new Date().getTime();
        List<GnEntity> list = ExcelImportUtil.importExcel(
                new File(FileUtilTest.getWebRootPath("exceltohtml/import/groupName_GnEntity.xlsx")), GnEntity.class, params);
        System.out.println(new Date().getTime() - start);
        Assert.assertEquals(10,list.size());
        System.out.println(ReflectionToStringBuilder.toString(list.get(0)));
        Assert.assertEquals("999999",list.get(0).getClientPhone());
        Assert.assertEquals("小明0",list.get(0).getClientName());
        Assert.assertEquals("JueYue0",list.get(0).getStudentEntity().getName());
        Assert.assertEquals(0,list.get(0).getStudentEntity().getSex());

    }
}
