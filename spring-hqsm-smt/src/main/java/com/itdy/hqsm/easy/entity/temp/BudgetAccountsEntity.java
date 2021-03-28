package com.itdy.hqsm.easy.entity.temp;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * 预算
 * @author 
 *   
 */
public class BudgetAccountsEntity {

    @Excel(name = "编码")
    private String code;

    @Excel(name = "名称")
    private String name;

    
    
    
    
    
    
    
    
    
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
