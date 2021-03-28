package com.itdy.hqsm.security.myshiro.entity;


import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author Sara
 * @since 2019-05-29
 */

public class Collections implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer cid;
    /**
     * 收藏地址保存的名字
     */
    private String cname;
    /**
     * 收藏地址
     */
    private String url;
    /**
     * 用户id
     */
    private String userId;





	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public String toString() {
		return "Collections{" +
				"cid=" + cid +
				", cname='" + cname + '\'' +
				", url='" + url + '\'' +
				", userId='" + userId + '\'' +
				'}';
	}
}
