
package com.itdy.hqsm.easy.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
/**
 * 
 * 
 * 
 * @author Administrator
 *
 */
public class StudentEntityImage extends StudentEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Excel(height = 40, width = 60, type = 2, name = "相片")
    private String            image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
