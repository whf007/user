package com.whf.user.common.validate;

import com.whf.user.common.Commons;
import com.whf.user.common.annotation.ValidateTime;
import jodd.vtor.ValidationConstraint;
import jodd.vtor.ValidationConstraintContext;
import org.apache.commons.lang3.time.FastDateFormat;


/**
 * <p>
 * 数值类型
 * <P>
 * 
 * @author caojiayao
 * @version $Id: NumericConstraint.java, v 0.1 2017年7月14日 下午5:40:40 caojiayao
 *          Exp $
 */
public class ValidateTimeConstraint implements ValidationConstraint<ValidateTime> {

	/** 默认格式 **/
	private String format = Commons.DATE_FORMAT;

	public void configure(ValidateTime annotation) {
		this.format = annotation.format();
	}

	public boolean isValid(ValidationConstraintContext vcc, Object value) {
		return validate(value, this.format);
	}

	public static boolean validate(Object value, String format) {
		if (value == null) {
			return true;
		}

		try {
			FastDateFormat.getInstance(format).parse(value.toString());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
