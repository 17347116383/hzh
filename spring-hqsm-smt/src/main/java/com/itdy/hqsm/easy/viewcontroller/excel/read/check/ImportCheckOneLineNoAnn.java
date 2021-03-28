
package com.itdy.hqsm.easy.viewcontroller.excel.read.check;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class ImportCheckOneLineNoAnn {

    @Excel(name = "姓名")
    private String name;
    @Excel(name = "性别")
    private String sex;
    @Excel(name = "年纪")
    private String age;
    @Excel(name = "爱好")
    private String hobby;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

}
