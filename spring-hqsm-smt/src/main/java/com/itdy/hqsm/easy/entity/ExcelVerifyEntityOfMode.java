package com.itdy.hqsm.easy.entity;


import cn.afterturn.easypoi.handler.inter.IExcelModel;

/**
 * Excel导入校验类
 * @author 
 *   
 */
public class ExcelVerifyEntityOfMode extends ExcelVerifyEntity implements IExcelModel {

    private String errorMsg;

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
