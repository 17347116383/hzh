package com.itdy.hqsm.smtmode.tree;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
 import java.util.List;
 
 public class MenuTree {
     private List<Menu> menuList = new ArrayList<Menu>();
     public MenuTree(List<Menu> menuList) {
         this.menuList=menuList;
     }
 
     //建立树形结构
     public List<Menu> builTree(){
         List<Menu> treeMenus =new  ArrayList<Menu>();
         for(Menu menuNode : getRootNode()) {
             menuNode=buildChilTree(menuNode);
             treeMenus.add(menuNode);
         }
         return treeMenus;
     }
 
     //递归，建立子树形结构
     private Menu buildChilTree(Menu pNode){
         List<Menu> chilMenus =new  ArrayList<Menu>();
         for(Menu menuNode : menuList) {
             if(menuNode.getParentId().equals(pNode.getId())) {
                 chilMenus.add(buildChilTree(menuNode));
             }
         }
         pNode.setChildren(chilMenus);
         return pNode;
     }
 
     //获取根节点
     private List<Menu> getRootNode() {         
         List<Menu> rootMenuLists =new  ArrayList<Menu>();
         for(Menu menuNode : menuList) {
             if(menuNode.getParentId().equals("0")) {
                 rootMenuLists.add(menuNode);
             }
         }
         return rootMenuLists;
     }


     public static void main(String []args) {
         List<Menu>  menuList= new ArrayList<Menu>();
         /*插入一些数据*/

         menuList.add(new Menu("11111111111", "1110001001","订阅区","/admin","Y"));
         menuList.add(new Menu("22222222222", "2220001002","未知领域","/admin","Y"));
         menuList.add(new Menu("10001", "0","系统管理","/admin","Y"));
         menuList.add(new Menu( "10002", "10001","权限管理","/admin","Y"));
         menuList.add(new Menu( "100010001", "10002","密码修改","/admin","Y"));
         menuList.add(new Menu("1110001001", "100010001","ppp","/admin","Y"));
         menuList.add(new Menu("2220001002", "100010001","ooo","/admin","Y"));
         /*
          * menuList.add(new Menu( "100010002", "10002","新加用户","/admin","Y"));
          * menuList.add(new Menu("10003", "10001","系统监控","/admin","Y"));
          * menuList.add(new Menu("GN001D21033333", "10003","在线用户","/admin","Y"));
          * menuList.add(new Menu("1110001001", "100010001","ppp","/admin","Y"));
          * menuList.add(new Menu("2220001002", "100010002","ooo","/admin","Y"));
          */
         /*让我们创建树*/
         MenuTree menuTree =new MenuTree(menuList);
         menuList=menuTree.builTree();
         /*转为json看看效果*/
         String jsonOutput= JSON.toJSONString(menuList);
         System.out.println(jsonOutput);
     }
 }