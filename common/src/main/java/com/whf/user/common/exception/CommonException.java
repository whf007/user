package com.whf.user.common.exception;

import com.whf.user.common.enums.ResponseCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Raden-pc on 2018/12/23.
 */
@Setter
@Getter
public class CommonException extends RuntimeException {
    private ResponseCode code;
    private String memo;
    public CommonException(ResponseCode responseCode) {
        super();
        this.code = responseCode;
        this.memo = responseCode.getMsg();
    }
    public CommonException(ResponseCode responseCode,String memo) {
        super();
        this.code = responseCode;
        this.memo = memo;
    }
}