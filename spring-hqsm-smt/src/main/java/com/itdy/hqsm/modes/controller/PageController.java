package com.itdy.hqsm.modes.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itdy.hqsm.common.collect.MapUtils;
//import com.itdy.hqsm.modes.entity.User;
//import com.itdy.hqsm.modes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 *    分页查询    测试案例
 * @author Administrator
 *
 */
//@CrossOrigin(origins = "http://domain2.com", maxAge = 3600)
//@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping(value = "/page")
public class PageController {

	
     // @Autowired
	 // private UserService userService;

      
      
    /** 
     *  /page/shUser         分页
     * @param request
     * @return
     */
    @RequestMapping (value = "/shUser")
    @ResponseBody
    public Map<String,Object> searchPersonList(
    		Model model,
			RedirectAttributes redirectAttributes, 
			HttpServletRequest request,
			HttpServletResponse response){
    	//其实redirectAttributes是继承了 MODEL   两者都可以使用
    	
        HashMap<String, Object> map = MapUtils.newHashMap();
        // page 为easyui分页插件默认传到后台的参数，代表当前的页码，起始页为1
        String pageNo = request.getParameter("page");
        // rows为为easyui分页插件默认传到后台的参数，代表当前设置的每页显示的记录条数
        String pageSize = request.getParameter("rows");
        // 调用service方法，获取人员记录
        //List<User> list = userService.findByPage(pageNo, pageSize);
        List list =null;
        PageInfo pageInfo = new PageInfo(list);
        // 获取总记录数
        long total = pageInfo.getTotal();
        // 定义map
        HashMap<String, Object> jsonMap = MapUtils.newHashMap();
        // total 存放总记录数
        jsonMap.put("total", total);
        // rows存放每页记录 ，这里的两个参数名是固定的，必须为 total和 rows
        jsonMap.put("rows", list);
     
        return jsonMap;
    }
    
    
    
    /**
              *   分页测试     /page/info
     * @param pn
     * @param model
     * @param request
     * @param response
     * @return    
     */
    @RequestMapping("/info")
    //使用 spring boot 定义好的状态码
    @ResponseStatus(code=HttpStatus.OK,reason="server成功")  
    public Map<String, Object> getAll(
    		@RequestParam(value="pn",defaultValue="1") Integer pn,
    		Model model,
    		HttpServletRequest request,
			HttpServletResponse response){
        //获取第1页，5条内容，默认查询总数count
        // 第一个参数是第几页；第二个参数是每页显示条数 
        PageHelper.startPage(pn, 10);
       // List<User> list = userService.getAll();
        //用PageInfo对结果进行包装        
      //  PageInfo<User> pageInfo = new PageInfo<User>(list);
        PageInfo pageInfo =null;
        model.addAttribute("pageInfo",pageInfo);
        response.setCharacterEncoding("UTF-8");
        HashMap<String, Object> jsonMap = MapUtils.newHashMap();
        jsonMap.put("pageInfo",pageInfo);
        model.addAllAttributes(jsonMap);
        return jsonMap;
    }



    /**
            *   分页测试     /page/in
     * @param pn
     * @param model
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/in")
    //使用 spring boot 定义好的状态码
    //@ResponseStatus(code=HttpStatus.OK,reason="server成功")  
    public Map<String, Object> getSysUser(
	@RequestParam(value="pn",defaultValue="1") Integer pn,
	Model model,
	HttpServletRequest request,
	HttpServletResponse response) throws IOException{
    	//获取第1页，5条内容，默认查询总数count
    	// 第一个参数是第几页；第二个参数是每页显示条数
    	PageHelper.startPage(pn, 10);
    	//List<User> list = userService.getAll();
    	//用PageInfo对结果进行包装
    	//	PageInfo<User> pageInfo = new PageInfo<User>(list);
        PageInfo pageInfo=null;
    		model.addAttribute("pageInfo",pageInfo);
    		response.setStatus(200);
    		response.getWriter().append("服务器响应成功");

        HashMap<String, Object> jsonMap = MapUtils.newHashMap();
        jsonMap.put("pageInfo",pageInfo);
        model.addAllAttributes(jsonMap);
        return jsonMap;
    		//return pageInfo;
    }

   


}
