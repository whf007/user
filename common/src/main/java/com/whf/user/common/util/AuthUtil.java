package com.whf.user.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static java.lang.Long.valueOf;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * <p>权限工具<p>
 * @author caojiayao 
 * @version $Id: AuthUtil.java, v 0.1 2017年10月7日 下午12:34:35 caojiayao Exp $
 */
public class AuthUtil {
    private static Logger       logger                  = LoggerFactory.getLogger(AuthUtil.class);
    // 间隔字符串
    public static final String SPILT_STR               = "%@%";
    // 密钥字符串
    private static final String IW_ENCRYPT_STRING       = "dfsafeDdfgDFDEU&(^GHD&)*#djdsaf";

    public static final String  IW_APP_LOGIN_TICKET_KEY = "uticket";
    
    /**
     * 获取uticket
     * @param memberId
     * @return
     */
    public static String getLoginTicket(String memberId,String partnerId) {
        if (isEmpty(memberId)) {
            return null;
        }
        String userCode = IdEncrypter.encodeId(valueOf(memberId));
        String md5Str = Md5Utils.md5(userCode + SPILT_STR + IW_ENCRYPT_STRING);
//        String value = Base64.encodeString(userCode + SPILT_STR + md5Str+ SPILT_STR + partnerId);
//        return value;
        return userCode + SPILT_STR + md5Str+ SPILT_STR + partnerId;
    }


    /**
     * 验证登录
     * @param loginTicketValue
     * @return
     */
    public static boolean verifyLogin(String loginTicketValue) {
        if (StringUtils.isBlank(loginTicketValue)) {
            return false;
        }
        try {
            String result = Base64.decodeString(loginTicketValue);
            String[] arr = result.split(SPILT_STR);
            if (arr.length < 2) {
                return false;
            }
            String userIdStr = arr[0];
            String md5Str = arr[1];
            String tmp = Md5Utils.md5(userIdStr + SPILT_STR + IW_ENCRYPT_STRING);
            if (!tmp.equals(md5Str)) {
                return false;
            }
            return true;
        } catch (Exception ex) {
            logger.error("verifyLogin Exception. message = " + ex.getMessage());
        }
        return false;
    }

    /**
     * 验证登录
     * @param loginTicketValue
     * @return
     */
    public static String verifyLoginString(String loginTicketValue,String partnerId) {
        if (StringUtils.isBlank(loginTicketValue)) {
            return null;
        }
        try {
            String result = Base64.decodeString(loginTicketValue);
            String[] arr = result.split(SPILT_STR);
            if (arr.length < 2) {
                return null;
            }
            String userIdStr = arr[0];
            String md5Str = arr[1];
//            if(arr.length == 4 ) {
//                String date = arr[3];
//                return userIdStr + SPILT_STR + md5Str+ SPILT_STR + partnerId+ SPILT_STR +date;
//            }
//            if(arr.length == 5 ) {
//                String date = arr[3];
//                return userIdStr + SPILT_STR + md5Str+ SPILT_STR + partnerId+ SPILT_STR +date + SPILT_STR + arr[4];
//            }
            return userIdStr + SPILT_STR + md5Str+ SPILT_STR + partnerId;
        } catch (Exception ex) {
            logger.error("verifyLogin Exception. message = " + ex.getMessage());
        }
        return null;
    }
    /**
     * 获取登录信息
     * @param loginTicketValue
     * @return
     */
    public static String verifyLoginString(String loginTicketValue) {
        if (StringUtils.isBlank(loginTicketValue)) {
            return null;
        }
        try {
            String result = Base64.decodeString(loginTicketValue);
            String[] arr = result.split(SPILT_STR);
            if (arr.length < 2) {
                return null;
            }
            String userIdStr = arr[0];
            String md5Str = arr[1];
            String partnerId = arr[2];
            if(arr.length == 4 ) {
                String date = arr[3];
                return userIdStr + SPILT_STR + md5Str+ SPILT_STR + partnerId+ SPILT_STR +date;
            }
            if(arr.length == 5 ) {
                String date = arr[3];
                return userIdStr + SPILT_STR + md5Str+ SPILT_STR + partnerId+ SPILT_STR +date + SPILT_STR + arr[4];
            }
            return userIdStr + SPILT_STR + md5Str+ SPILT_STR + partnerId;
        } catch (Exception ex) {
            logger.error("verifyLogin Exception. message = " + ex.getMessage());
        }
        return null;
    }
    /**
     * 获得登录用户ID
     * @param loginTicketValue
     * @return
     */
    public static String getLoginUserId(String loginTicketValue) {
        if (StringUtils.isBlank(loginTicketValue)) {
            return EMPTY;
        }
        try {
            String result = Base64.decodeString(loginTicketValue);
            String[] arr = result.split(SPILT_STR);
            if (arr.length < 2) {
                return EMPTY;
            }
            return String.valueOf(IdEncrypter.decodeId(arr[0]));
        } catch (Exception ex) {
            logger.error("verifyLogin Exception", ex);
        }
        return EMPTY;
    }
    public static void main(String[]  args) {
        String loginTicket = getLoginTicket("100017100001", "188888888888");
        String value ="dHROMFdnX1EyN00lQCU0YzI2ZGU4MjVlMmYyM2RiZDFiMzdjN2JjMDNjZDEyMyVAJTE4ODg4ODg4ODg4ODE1NDAzNTEyMDg4NzM=";// Base64.encodeString(loginTicket + new Date().getTime());
        String s = getLoginUserId(value);
//        System.out.println("loginTicket:" + loginTicket);
//        System.out.println("value:" + value);
        System.out.println("s:" + s);
    }
}
