
package com.itdy.hqsm.easy.viewcontroller.excel.read.hanlder;

import org.apache.commons.lang3.StringUtils;

import com.itdy.hqsm.easy.entity.ExcelVerifyEntity;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
/**
 * 
 * 
 * 
 * @author Administrator
 *
 */
public class ExcelVerifyHandlerImpl implements IExcelVerifyHandler<ExcelVerifyEntity> {

    @Override
    public ExcelVerifyHandlerResult verifyHandler(ExcelVerifyEntity obj) {
        StringBuilder builder = new StringBuilder();
        if (StringUtils.isNotEmpty(obj.getEmail())) {
            builder.append("Email must null;");
        }
        if (obj.getMax() > 15) {
            builder.append("max must lt 15;");
        }
        return new ExcelVerifyHandlerResult(false, builder.toString());
    }

}
