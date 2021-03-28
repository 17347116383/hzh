package com.itdy.hqsm.Tree;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;



/**
 * @author huang 
 * @param 
 * @param 
 * @return
 * @date 2019年9月10日
 */
public class Tree {

	
	      private List<InputCheckRule> menuList = new ArrayList<InputCheckRule>();
	      public Tree(List<InputCheckRule> menuList) {
	          this.menuList=menuList;
	      }
	  
	     /**
	      * 建立树形结构
	      * @return
	      * @Function: 
	      * @Description: 该函数的功能描述
	      * @return
	      * @date 2019年9月11日
	      */
	     public List<InputCheckRule> builTree(){
	         List<InputCheckRule> treeMenus =new  ArrayList<InputCheckRule>();
	         for(InputCheckRule menuNode : getRootNode()) {
	             menuNode=buildChilTree(menuNode);
	             treeMenus.add(menuNode);
	         }
	         return treeMenus;
	     }
	 
	     /**
	      * 递归，建立子树形结构
	      * @param pNode
	      * @return
	      * @Function: 
	      * @Description: 该函数的功能描述
	      * @return
	      * @date 2019年9月11日
	      */
	     private InputCheckRule buildChilTree(InputCheckRule pNode){
	         List<InputCheckRule> chilMenus =new  ArrayList<InputCheckRule>();
	         for(InputCheckRule menuNode : menuList) {
	        	 Long parentId = menuNode.getParentId();
	        	 Long id = pNode.getId();
	             if(parentId.equals(id)) {
	                 chilMenus.add(buildChilTree(menuNode));
	             }
	         }
	         pNode.setInputCheckRule(chilMenus);
	         return pNode;
	     }
	 
	   /**
	    * 获取根节点
	    * @return
	    * @Function: 
	    * @Description: 该函数的功能描述
	    * @return
	    * @date 2019年9月11日
	    */
	     private List<InputCheckRule> getRootNode() {         
	         List<InputCheckRule> rootMenuLists =new  ArrayList<InputCheckRule>();
	         for(InputCheckRule menuNode : menuList) {
	        	 Long parentId = menuNode.getParentId();
	             if(parentId.equals(1L)) {
	                 rootMenuLists.add(menuNode);
	             }
	         }
	         return rootMenuLists;
	     }

}
