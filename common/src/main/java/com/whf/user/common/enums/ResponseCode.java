package com.whf.user.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Raden-pc on 2018/12/23.
 */
@Getter
@AllArgsConstructor
public enum ResponseCode {
    SUCCESS(0, "操作成功"),
    FAIL(-1, "操作失败"),
    ILLEGAL_SERVICE(001,"服务不存在"),
    ILLEGAL_ARGUMENT(002,"参数错误"),
    MOBILE_FORMAT_ERROR(003,"手机号格式错误"),
    SYSTEM_ERROR(004,"系统错误"),;
    final private Integer code;
    final private String msg;

    public static ResponseCode get(Integer code) {
        for (ResponseCode responseCode : values())
            if (responseCode.getCode().equals(code))
                return responseCode;

        return null;
    }
}