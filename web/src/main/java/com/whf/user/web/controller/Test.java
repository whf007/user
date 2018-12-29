package com.whf.user.web.controller;
import static com.google.common.base.Stopwatch.createStarted;
import static com.whf.user.common.resp.Response.of;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import com.google.common.collect.Maps;
import com.whf.user.common.enums.ResponseCode;
import com.whf.user.common.exception.CommonException;
import com.whf.user.common.req.Request;
import com.whf.user.common.resp.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Stopwatch;

/**
 * Created by Raden-pc on 2018/12/15.
 */
@Slf4j
@Controller
public class Test extends AbstractRouter<Request>{
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/recv", method = RequestMethod.POST)
    public Response s (HttpServletRequest httpServletRequest, @RequestBody Map<String, String> requestMap){
        final Stopwatch stopwatch = createStarted();
        Response response = null;
        try {
            // IP
            // String ipAddress = getIPAddress(httpServletRequest);
            // requestMap.put("ipAddress",ipAddress);
            checkCommonParameter(httpServletRequest, requestMap);
            response = process(requestMap);
            return response;
        } catch (CommonException e) {
            log.error("recv error: requst:{}, CommonException:{}", requestMap, e);
            return of(e);
//        } catch (ValidateException e) {
//            log.error("recv error: requst:{}, VerifyException:{}", requestMap, e);
//            return of(ResponseCode.ILLEGAL_ARGUMENT, e.getMessage());
        } catch (Throwable t) {
            log.error("recv error: requst:{}, exception:{} ", requestMap, t);
            return of(ResponseCode.SYSTEM_ERROR);
        } finally {
            log.info("recv, request:{} ,response:{}, costs {}ms", requestMap, response, stopwatch.elapsed(MILLISECONDS));
        }
    }
    /**
     * 所有非空参数参与校验
     *
     * @param request
     * @param requestMap
     */
    private void checkCommonParameter(HttpServletRequest request, Map<String, String> requestMap) {
        Map<String, String> paramMap = Maps.newHashMap();
        for (String key : requestMap.keySet()) {
            String value = MapUtils.getString(requestMap, key);
            if (StringUtils.isEmpty(value)) {
                paramMap.put(key, value);
            }
        }
        log.info("request data filter result:{}", mapToString(requestMap));
        requestMap.put("basePath",request.getSession().getServletContext().getRealPath("/"));
    }
    private String mapToString(Map<String, String> requestMap) {
        StringBuffer sb = new StringBuffer();
        Set<Map.Entry<String, String>> entries = requestMap.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            String key = next.getKey();
            String value = next.getValue();
            if (key.equalsIgnoreCase("identityId") || key.equalsIgnoreCase("mobile")) {
                if (value.length() > 4) {
                    value = value.substring(0, value.length() - 4) + "***";
                }
            }
            sb.append(key + ":" + value + ",");
        }
        return sb.toString();
    }
    @RequestMapping(value = "/recv1", method = RequestMethod.GET)
    @ResponseBody
    public String ss(){
        return "ss";
    }


}