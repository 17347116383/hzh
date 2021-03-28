
package com.itdy.hqsm.easy.viewcontroller.word.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * 纳税管理
 * @author JueYue
 *   2015年11月24日 下午10:52:11
 */
public class TaxManagement {

    @Excel(name = "税种")
    private String type;
    @Excel(name = "2014年")
    private String presum;
    @Excel(name = "2015年")
    private String thissum;
    @Excel(name = "当年所属期间")
    private String curmonth;
    @Excel(name = "采集截止日期")
    private String now;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPresum() {
        return presum;
    }

    public void setPresum(String presum) {
        this.presum = presum;
    }

    public String getThissum() {
        return thissum;
    }

    public void setThissum(String thissum) {
        this.thissum = thissum;
    }

    public String getCurmonth() {
        return curmonth;
    }

    public void setCurmonth(String curmonth) {
        this.curmonth = curmonth;
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

}
