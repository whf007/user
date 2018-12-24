package com.whf.user.common.process;


import com.google.common.collect.Maps;
import com.whf.user.common.Processor;
import com.whf.user.common.annotation.RegistProcessor;
import com.whf.user.common.req.Request;
import com.whf.user.common.resp.Response;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.util.Map;

import static org.apache.commons.collections.MapUtils.isNotEmpty;
import static org.springframework.core.annotation.AnnotationUtils.getAnnotation;
import static org.springframework.core.annotation.AnnotationUtils.getValue;

/**
 * <p>
 * 处理工厂
 * </p>
 * 
 * @author caojiayao
 * @version $Id: ProcessFactory.java, v 0.1 2017年4月14日 下午4:38:33 caojiayao
 *          Exp $
 */
@Component
public class ProcessFactory<RESULT extends Response, REQUEST extends Request> implements ApplicationContextAware {

	private final Map<String, Processor<RESULT, REQUEST>> processors = Maps.newHashMapWithExpectedSize(10);

	/**
	 * @param service
	 * @return
	 */
	public Processor<RESULT, REQUEST> loadProcessor(final String service) {
		return processors.get(service);
	}

	public Map<String, Processor<RESULT, REQUEST>> getProcessors() {
		return processors;
	}

	/**
	 * 服务收集
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

		Map<String, Object> processorMap = applicationContext.getBeansWithAnnotation(RegistProcessor.class);

		if (isNotEmpty(processorMap))
			for (Map.Entry<String, Object> entry : processorMap.entrySet()) {
				final String[] processNames = getProcessName(entry.getValue());
				for (String processName : processNames) {
					Assert.isTrue(!processors.containsKey(processName),
							"[ProcessFactory.setApplicationContext] duplication Processor ,processName:" + processName);

					processors.put(processName, Processor.class.cast(entry.getValue()));
				}
			}
	}

	/**
	 * 获取服务多个名
	 * 
	 * @param bean
	 * @return
	 */
	private String[] getProcessName(Object bean) {
		if (bean != null) {
			Annotation anno = getAnnotation(bean.getClass(), RegistProcessor.class);
			if (anno != null) {
				return (String[]) getValue(anno, "value");
			}
		}
		return null;
	}

}
