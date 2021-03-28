package com.itdy.hqsm.Tree;

import java.util.ArrayList;
import java.util.List;


/**树形
 * @author huang 
 * @param 
 * @param 
 * @return
 * @date 2019年9月8日
 */
public class InputCheckRuleTree extends InputCheckRule {

	
	

	    private List<InputCheckRuleTree> child = new ArrayList<InputCheckRuleTree>();

	    public List<InputCheckRuleTree> getChild() {
	        return child;
	    }

	    public void setChild(List<InputCheckRuleTree> child) {
	        this.child = child;
	    }

	    
}
