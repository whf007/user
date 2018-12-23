package com.whf.user.common;


import com.whf.user.common.req.Request;
import com.whf.user.common.resp.Response;

/**
 * <p>处理器</p>
 * @author caojiayao
 * @version $Id: Processor.java, v 0.1 2017年4月14日 下午5:11:53 caojiayao Exp $
 */
public interface Processor<RESPONSE extends Response,REQUEST extends Request> {
	public static final String SPILT_STR               = "%@%";
    /**
     * @param request
     * @return
     * @throws Exception
     */
	RESPONSE process(final REQUEST request) throws Exception;

	void check(final REQUEST request) throws Exception;
}
