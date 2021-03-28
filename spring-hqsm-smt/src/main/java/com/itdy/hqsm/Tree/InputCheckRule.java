package com.itdy.hqsm.Tree;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.poi.ss.formula.functions.T;



/**
 * 树形测试
 * @author huang 
 * @param 
 * @param
 * @return
 * @date 2019年9月6日  
 */

public class InputCheckRule  implements Serializable {
	
	private Long   id;              //
	private String 	 appId;        //逻辑系统ID
	private Long     parentId;      //PARENT_ID    父ID
	private String   ruleType;      //RULE_TYPE
	private String   ruleName;      //RULE_NAME  名称

	
	@Transient
	private List<InputCheckRule>   inputCheckRule;

	@Override
	public String toString() {
		return "InputCheckRule{" +
				"id=" + id +
				", appId='" + appId + '\'' +
				", parentId=" + parentId +
				", ruleType='" + ruleType + '\'' +
				", ruleName='" + ruleName + '\'' +
				", inputCheckRule=" + inputCheckRule +
				'}';
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public List<InputCheckRule> getInputCheckRule() {
		return inputCheckRule;
	}

	public void setInputCheckRule(List<InputCheckRule> inputCheckRule) {
		this.inputCheckRule = inputCheckRule;
	}
}
