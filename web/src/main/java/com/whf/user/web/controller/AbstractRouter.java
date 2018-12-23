package com.whf.user.web.controller;

import com.alibaba.fastjson.JSON;
import com.whf.user.common.Processor;
import com.whf.user.common.annotation.CheckLogin;
import com.whf.user.common.enums.ResponseCode;
import com.whf.user.common.exception.CommonException;
import com.whf.user.common.process.ProcessFactory;
import com.whf.user.common.req.Request;
import com.whf.user.common.req.UserBaseReq;
import com.whf.user.common.resp.Response;
import com.whf.user.common.util.AuthUtil;
import com.whf.user.common.validate.ParamsValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;
import static com.whf.user.common.Commons.SERVICE;
import static com.whf.user.common.enums.ResponseCode.ILLEGAL_SERVICE;
import static com.whf.user.common.util.AuthUtil.getLoginUserId;
import static com.whf.user.common.util.ClassUtil.getParameterizedType;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
public abstract class AbstractRouter<T extends Request> {

    private final Map<String, Class<T>> processClassCache = newHashMap();
    
    @Autowired
    protected ProcessFactory<Response, Request> processFactory;

    protected Response process(final Map<String,String> requstMap) throws Exception {
        String service = MapUtils.getString(requstMap, "service");

        final Class<T> clazz = loadProcessClass(service);
        T instantiate = JSON.parseObject(JSON.toJSONString(requstMap), clazz);
        ParamsValidateUtil.validate(instantiate);
        
        Processor<Response, Request> responseRequestProcessor = processFactory.loadProcessor(service);
        verifyLoginWrapper(requstMap, responseRequestProcessor, instantiate);
        responseRequestProcessor.check(instantiate);
        return responseRequestProcessor.process(instantiate);
    }

    protected Response process1(final Map<String,String> requstMap) throws Exception {
        String service = MapUtils.getString(requstMap, SERVICE);
        final Class<T> clazz = loadProcessClass(service);
        T instantiate = JSON.parseObject(JSON.toJSONString(requstMap), clazz);
//        ParamsValidateUtil.validate(instantiate);

        Processor<Response, Request> responseRequestProcessor = processFactory.loadProcessor(service);
//        verifyLoginWrapper(requstMap, responseRequestProcessor, instantiate);
//        responseRequestProcessor.check(instantiate);
        return responseRequestProcessor.process(instantiate);
    }
    protected Response process(final Request request) throws Exception {
        Processor<Response, Request> responseRequestProcessor = processFactory.loadProcessor(request.getService());
        responseRequestProcessor.check(request);
        return responseRequestProcessor.process(request);
    }
    @SuppressWarnings("unchecked")
	private Class<T> loadProcessClass(final String service) throws CommonException {
        if (isBlank(service)) {
        	throw new CommonException(ILLEGAL_SERVICE);
        }
        if (processClassCache.containsKey(service)) {
        	return processClassCache.get(service);
        }

        final Processor<Response, Request> processor = processFactory.loadProcessor(service);
        if (processor == null) {
        	throw new CommonException(ILLEGAL_SERVICE);
        }
        final Class<T> clazz = (Class<T>) getParameterizedType(Request.class, processor.getClass());

        if (clazz == null) {
        	throw new CommonException(ILLEGAL_SERVICE);
        }
        processClassCache.put(service, clazz);
        return clazz;
    }

    private void verifyLoginWrapper(Map<String, String> request,Processor<Response, Request> processor,T params) {
        final CheckLogin checkLogin = processor.getClass().getAnnotation(CheckLogin.class);
        Request req = Request.class.cast(params);
        if (checkLogin != null && checkLogin.isCheck()) {
            String uticket = AuthUtil.verifyLoginString(req.getUticket());
            if(StringUtils.isBlank(uticket)) {
                //会员为登陆
//            	throw new CommonException(MEMBER_NOT_LOGIN_ERROR, MEMBER_NOT_LOGIN_ERROR.getMsg());
            }
            String[] split = uticket.split(AuthUtil.SPILT_STR);
            uticket = split[0] + AuthUtil.SPILT_STR + split[1] + AuthUtil.SPILT_STR + split[2];
            if (AuthUtil.verifyLogin(req.getUticket())) {
                wrapperUser(req.getUticket(), params);
                getLoginUserId(req.getUticket());

            } else {
                log.error("用户未登录：{}",JSON.toJSONString(params));
//                throw new CommonException(MEMBER_NOT_LOGIN_ERROR, MEMBER_NOT_LOGIN_ERROR.getMsg());
            }
        }
    }

    private void wrapperUser(String uticket, Object params) {
        if(!(params instanceof UserBaseReq)) {
        	return;
        }
        UserBaseReq.class.cast(params).setMemberId(getLoginUserId(uticket));
    }
}
