package com.whf.user.common.annotation;

import jodd.vtor.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 数值类型
 * <P>
 * 
 * @author caojiayao
 * @version $Id: Numeric.java, v 0.1 2017年7月14日 下午5:35:19 caojiayao Exp $
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
@Constraint(ValidateTimeConstraint.class)
public @interface ValidateTime {

	String format() default "yyyyMMddHHmmss";

	String[] profiles() default {};

	int severity() default 0;

	String message() default "请输入正确的日期格式";
}
