/*
 * @author caojiayao 2017年6月29日 上午11:12:24
 */
package com.whf.user.common.util;

import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * <p>
 * <P>
 * 
 * @author caojiayao
 * @version $Id: ClassUtils.java, v 0.1 2017年6月29日 上午11:12:24 caojiayao Exp $
 */
public class ClassUtil {

	/**
	 * 获得泛型参数
	 * 
	 * @param processClass
	 * @param superClass
	 * @return
	 */
	public static Class<?> getParameterizedType(Class<?> superClass, Class<?> processClass) {
		Class<?> resultClass = null; 
		
		// 实现接口查找
		Type[] interfaceTypes = processClass.getGenericInterfaces();
		if (ArrayUtils.isNotEmpty(interfaceTypes)) {
			resultClass = getActualTypeArgumentClass(superClass, interfaceTypes);
		} 
		
		// 继承父类查找 
		if(resultClass == null) {
			resultClass = getActualTypeArgumentClass(superClass, processClass.getGenericSuperclass());
		}
		
		return resultClass;
	}

	/**
	 * 获得泛型参数
	 * 
	 * @param superClass
	 * @param types
	 * @return
	 */
	private static Class<?> getActualTypeArgumentClass(Class<?> superClass, Type... types) {
		for (Type t : types)
			// 匹配泛型参数
			if (t instanceof ParameterizedType) {
				Type[] pts = ((ParameterizedType) t).getActualTypeArguments();
				for (Type pt : pts) {
					if (superClass.isAssignableFrom((Class<?>) pt)) {
						return (Class<?>) pt;
					}
				}
			}
			// Class类型递归查找
			else if (t instanceof Class && t != Object.class) {
				return getParameterizedType(superClass, Class.class.cast(t));
			}

		return null;
	}
}
