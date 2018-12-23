package com.whf.user.common.util;

import com.google.common.hash.Hashing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <p>md5 util<p>
 * @author caojiayao 
 * @version $Id: Md5Utils.java, v 0.1 2017年10月7日 下午12:35:50 caojiayao Exp $
 */
public class Md5Utils {
    public static final Logger log = LoggerFactory.getLogger(Md5Utils.class);
    public static final String md5(String data) {
        MessageDigest digest = null;
        if (digest == null) {
            try {
                digest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                log.error("Failed to load the MD5 MessageDigest.");
                e.printStackTrace();
            }
        }
        digest.update(data.getBytes());
        return encodeHex(digest.digest());
    }

    public static final String encodeHex(byte[] bytes) {
        StringBuffer buf = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            if (((int) bytes[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString((int) bytes[i] & 0xff, 16));
        }
        return buf.toString();
    }

    /**
     * 获得MD5 (16位)
     * @param bytes
     * @return
     */
    public static final String getMd5(byte[] bytes) {
        return Hashing.md5().hashBytes(bytes).toString();
    }

}