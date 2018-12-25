package com.whf.user.web.controller;/**
 * Created by Administrator on 2018/12/25.
 */

import com.whf.user.web.health.MyHealthChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: user
 * @description:
 * @author: whf
 * @create: on 2018/12/25.
 **/
@RestController
public class Health {
    @Autowired
    MyHealthChecker myHealthChecker;
    @RequestMapping("/up")
    @ResponseBody
    public String up(@RequestParam("up") Boolean up) {
        myHealthChecker.setUp(up);
        return up.toString();
    }
}
