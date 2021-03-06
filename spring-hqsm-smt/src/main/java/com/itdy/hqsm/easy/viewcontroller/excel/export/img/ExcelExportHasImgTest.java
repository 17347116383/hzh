package com.itdy.hqsm.easy.viewcontroller.excel.export.img;

import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Before;
import org.junit.Test;

import com.itdy.hqsm.easy.entity.img.CompanyHasImgModel;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 带有图片的测试服务
 *
 *
 */
@Controller
@RequestMapping("/mongo/ExcelExportHasImgTest")
public class ExcelExportHasImgTest {

    List<CompanyHasImgModel> list;

    @Before
    public void initData() {
        list = new ArrayList<CompanyHasImgModel>();
        list.add(new CompanyHasImgModel("百度", "exceltohtml/imgs/company/baidu.png", "北京市海淀区西北旺东路10号院百度科技园1号楼"));
        list.add(new CompanyHasImgModel("阿里巴巴", "exceltohtml/imgs/company/ali.png", "北京市海淀区西北旺东路10号院百度科技园1号楼"));
        list.add(new CompanyHasImgModel("Lemur", "exceltohtml/imgs/company/lemur.png", "亚马逊热带雨林"));
        list.add(new CompanyHasImgModel("一众", "exceltohtml/imgs/company/one.png", "山东济宁俺家"));


    }

    @Test
    public void exportCompanyImg() throws Exception {

        File savefile = new File("D:/excel/");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), CompanyHasImgModel.class, list);
        FileOutputStream fos = new FileOutputStream("D:/excel/ExcelExportHasImgTest.exportCompanyImg.xls");
        workbook.write(fos);
        fos.close();
    }

}
