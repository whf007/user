package com.whf.user.common.resp;

import com.whf.user.common.enums.ResponseCode;
import com.whf.user.common.exception.CommonException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

import static com.whf.user.common.Commons.sdf;

/**
 * Created by Raden-pc on 2018/12/23.
 */
@Getter
@Setter
@ToString
public class Response implements Serializable {

    /**  **/
    private static final long serialVersionUID = 8038498242104218313L;

    /** 响应时间 **/
    private String            responseTime     = sdf.format(new Date());

    private String            currencyTime     = new Date().getTime()+"";
    /** 响应CODE **/
    private Integer            code;

    /** 响应信息 **/
    private String            message;

    /** 签名 **/
    private String            sign;

    /** 加签类型 **/
    private String            signType;

    /** 描述 **/
    private String            memo;
    /** 描述 **/
    private String            type;
    public Response() {
        this.code = 0;
        this.message = "成功";
    }
    public Response(ResponseCode code,String message) {
        this.code = code.getCode();
        this.message = message;
    }
    public static Response of(CommonException exception) {
        return new Response(exception.getCode(), StringUtils.isNotBlank(exception.getMemo()) ? exception.getMemo() : exception.getMessage());
    }
    public static Response of(ResponseCode code) {
        return new Response(code,code.getMsg());
    }
}