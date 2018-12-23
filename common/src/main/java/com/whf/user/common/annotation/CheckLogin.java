/*
 * @author caojiayao 2017年10月7日 下午1:03:15
 */
package com.whf.user.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(TYPE)
/**
 * <p>验证登录<p>
 * @author caojiayao 
 * @version $Id: CheckLogin.java, v 0.1 2017年10月7日 下午1:03:15 caojiayao Exp $
 */
public @interface CheckLogin {

    boolean isCheck() default true;
}
