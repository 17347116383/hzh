package com.itdy.hqsm.easy.viewcontroller;




import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.afterturn.easypoi.entity.vo.MapExcelConstants;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.view.PoiBaseView;

/**
 * 
 * 
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/mongo/EasypoiMapExcelViewTest")
public class EasypoiMapExcelViewTest {

    
    @RequestMapping()
    public String download(ModelMap modelMap) {
        List<ExcelExportEntity> entity = new ArrayList<ExcelExportEntity>();
        ExcelExportEntity excelentity = new ExcelExportEntity("姓名", "name");
        excelentity.setNeedMerge(true);
        entity.add(excelentity);
        entity.add(new ExcelExportEntity("分隔符性别问", "sex"));
        excelentity = new ExcelExportEntity(null, "students");
        List<ExcelExportEntity> temp = new ArrayList<ExcelExportEntity>();
        temp.add(new ExcelExportEntity("姓名千万不同意", "name"));
        temp.add(new ExcelExportEntity("性别一体化", "sex"));
        excelentity.setList(temp);
        entity.add(excelentity);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        for (int i = 0; i < 10; i++) {
            map = new HashMap<String, Object>();
            map.put("name", "1沃尔沃若" + i);
            map.put("sex", "2二位" + i);

            List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
            tempList.add(map);
            tempList.add(map);
            map.put("students", tempList);

            list.add(map);
        }

        ExportParams params = new ExportParams("标题头", "页签", ExcelType.XSSF);
        params.setFreezeCol(2);
        modelMap.put(MapExcelConstants.MAP_LIST, list);
        modelMap.put(MapExcelConstants.ENTITY_LIST, entity);
        modelMap.put(MapExcelConstants.PARAMS, params);
        modelMap.put(MapExcelConstants.FILE_NAME, "EasypoiMapExcelViewTest");
        return MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW;

    }
 

    /**如果上面的方法不行,可以使用下面的用法
     * 同样的效果,只不过是直接问输出了,不经过view了
     * @param map
     * @param request
     * @param response
     */

    @RequestMapping("load")
    public void downloadByPoiBaseView(
    		ModelMap modelMap,
    		HttpServletRequest request,
            HttpServletResponse response) {
    	
        List<ExcelExportEntity> entity = new ArrayList<ExcelExportEntity>();
        
        ExcelExportEntity excelentity = new ExcelExportEntity("姓名", "name");
        
        excelentity.setNeedMerge(true);
        entity.add(excelentity);
        entity.add(new ExcelExportEntity("项目阿萨德", "sex"));
        excelentity = new ExcelExportEntity(null, "students");
        List<ExcelExportEntity> temp = new ArrayList<ExcelExportEntity>();
        temp.add(new ExcelExportEntity("姓名请问", "name"));
        temp.add(new ExcelExportEntity("性别按乡镇村", "sex"));
        excelentity.setList(temp);
        entity.add(excelentity);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        for (int i = 0; i < 10; i++) {
            map = new HashMap<String, Object>();
            map.put("name", "方法" + i);
            map.put("sex", "干活" + i);

            List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
            tempList.add(map);
            tempList.add(map);
            map.put("students", tempList);
            list.add(map);
        }

        ExportParams params = new ExportParams("标题头", "页签", ExcelType.XSSF);
        params.setFreezeCol(2);
        modelMap.put(MapExcelConstants.MAP_LIST, list);
        modelMap.put(MapExcelConstants.ENTITY_LIST, entity);
        modelMap.put(MapExcelConstants.PARAMS, params);
        modelMap.put(MapExcelConstants.FILE_NAME, "EasypoiMapExcelViewTest");
        PoiBaseView.render(modelMap, request, response, MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW);

    }

}
