package com.itdy.hqsm.security.myshiro.entity;



import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.List;



/**
 * @ClassName:       部门
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2019/10/13
 * @Author Administrator
 */
public class DesktopSet implements Serializable {

    private static final long serialVersionUID = 1L;

    //@TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer sysDivId;

    private List<Long> desktopList;




	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getSysDivId() {
		return sysDivId;
	}
	public void setSysDivId(Integer sysDivId) {
		this.sysDivId = sysDivId;
	}
	@Transient
	public List<Long> getDesktopList() {
		return desktopList;
	}
	public void setDesktopList(List<Long> desktopList) {
		this.desktopList = desktopList;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "DesktopSet{" +
				"id=" + id +
				", userId=" + userId +
				", sysDivId=" + sysDivId +
				", desktopList=" + desktopList +
				'}';
	}
}
