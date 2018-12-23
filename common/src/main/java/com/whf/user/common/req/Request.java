package com.whf.user.common.req;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Raden-pc on 2018/12/23.
 */
@Getter
@Setter
public class Request implements Serializable {
    private String service ;
    private String uticket;
}