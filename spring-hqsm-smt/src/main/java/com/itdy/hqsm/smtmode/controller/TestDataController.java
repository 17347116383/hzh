
package com.itdy.hqsm.smtmode.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.github.pagehelper.Page;
import com.itdy.hqsm.common.lang.StringUtils;
import com.itdy.hqsm.smtmode.entity.TestData;
import com.itdy.hqsm.smtmode.service.Impl.TestDataServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



/**
 * 单表生成Controller
 * @author
 * @version
 */
//@CrossOrigin(origins = "http://domain2.com", maxAge = 3600)
//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/test/testData")
public class TestDataController  {

	private static Logger logger = LoggerFactory.getLogger(TestDataController.class);

	@Autowired
	private TestDataServiceImpl testDataService;
	
	@ModelAttribute
	public TestData get(@RequestParam(required=false) String id) {
		TestData entity = null;
		/*if (StringUtils.isNotBlank(id)){
			entity = testDataService.get(id);
		}
		if (entity == null){
			entity = new TestData();
		}*/
		return entity;
	}
	
	//@RequiresPermissions("test:testData:view")
	@RequestMapping(value = {"list", ""})
	public String list(TestData testData, HttpServletRequest request, HttpServletResponse response, Model model) {
		/*Page<TestData> page = testDataService.findPage(new Page<TestData>(request, response), testData);
		model.addAttribute("page", page);*/
		return "modules/test/testDataList";
	}

	//@RequiresPermissions("test:testData:view")
	@RequestMapping(value = "form")
	public String form(TestData testData, Model model) {
		model.addAttribute("testData", testData);
		return "modules/test/testDataForm";
	}

	//@RequiresPermissions("test:testData:edit")
	@RequestMapping(value = "save")
	public String save(TestData testData, Model model, RedirectAttributes redirectAttributes) {
		/*if (!beanValidator(model, testData)){
			return form(testData, model);
		}
		testDataService.save(testData);
		addMessage(redirectAttributes, "保存单表成功");
		return "redirect:"+Global.getAdminPath()+"/test/testData/?repage";*/
		return "";
	}
	
	//@RequiresPermissions("test:testData:edit")
	@RequestMapping(value = "delete")
	public String delete(TestData testData, RedirectAttributes redirectAttributes) {

		return "";
	}

}