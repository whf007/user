package com.whf.user.common.annotation;

import jodd.vtor.ValidationConstraint;
import jodd.vtor.ValidationConstraintContext;

import static org.apache.commons.lang3.StringUtils.isNumeric;

/**
 * <p>数值类型<P>
 * @author caojiayao 
 * @version $Id: NumericConstraint.java, v 0.1 2017年7月14日 下午5:40:40 caojiayao Exp $
 */
public class NumericConstraint implements ValidationConstraint<Numeric> {

	public void configure(Numeric annotation) {
	}

	public boolean isValid(ValidationConstraintContext vcc, Object value) {
		return validate(value);
	}

	public static boolean validate(Object value) {
		if (value == null) {
			return true;
		}
		return isNumeric(value.toString());
	}

}
