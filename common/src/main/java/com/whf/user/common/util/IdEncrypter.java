package com.whf.user.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * <p>id 加密<p>
 * @author caojiayao 
 * @version $Id: IdEncrypter.java, v 0.1 2017年10月7日 下午12:35:39 caojiayao Exp $
 */
public class IdEncrypter {
    private static final Logger logger             = LoggerFactory.getLogger(IdEncrypter.class);
    private static final byte[] idCrypterSaltBytes = "N0thing_gReat_wAs_eveR_achieved_with0ut_enthusiasm, N0_do_n0_die."
                                                       .getBytes(Charset.forName("ISO-8859-1"));
    private static Blowfish     blowfish;

    static {
        blowfish = new Blowfish(Arrays.copyOf(idCrypterSaltBytes, 32), Arrays.copyOfRange(
            idCrypterSaltBytes, 32, 40));
    }

    public static String encodeId(long id) {
        try {
            byte[] sourceBytes = new byte[8];
            sourceBytes[0] = (byte) ((id >> 56) & 0xFF);
            sourceBytes[1] = (byte) ((id >> 48) & 0xFF);
            sourceBytes[2] = (byte) ((id >> 40) & 0xFF);
            sourceBytes[3] = (byte) ((id >> 32) & 0xFF);
            sourceBytes[4] = (byte) ((id >> 24) & 0xFF);
            sourceBytes[5] = (byte) ((id >> 16) & 0xFF);
            sourceBytes[6] = (byte) ((id >> 8) & 0xFF);
            sourceBytes[7] = (byte) ((id) & 0xFF);
            byte[] encryptedBytes = blowfish.encrypt(sourceBytes);
            char[] base64Chars = Base64.encode(encryptedBytes);
            int i;
            for (i = 0; i < base64Chars.length; i++) {
                if (base64Chars[i] == '=') {
                    break; // = only appear at end, so i is the ending
                } else if (base64Chars[i] == '+') {
                    base64Chars[i] = '-';
                } else if (base64Chars[i] == '/') {
                    base64Chars[i] = '_';
                }
            }
            return new String(base64Chars, 0, i);
        } catch (Exception e) {
            logger.error("encode error, id=" + id, e);
        }
        return null;
    }

    public static long decodeId(String code) {
        try {
          return  decode(code);
        } catch (Exception e) {
            logger.error("decode error, code=" + code, e);
        }
        return 0;
    }

    public static long decodeIdNew(String code) throws Exception{
        return decode(code);
    }

    public static long decode(String code) throws Exception{
        if (code == null)
            return 0;
        StringBuilder sb = new StringBuilder(code);
        int i = code.length();
        while (i % 4 != 0) {
            sb.append('=');
            i++;
        }
        for (i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == '-') {
                sb.setCharAt(i, '+');
            } else if (sb.charAt(i) == '_') {
                sb.setCharAt(i, '/');
            }
        }
        byte[] sourceBytes = Base64.decode(sb.toString());
        byte[] decryptedBytes = blowfish.decrypt(sourceBytes);

        ByteBuffer bf = ByteBuffer.wrap(decryptedBytes);
        return bf.getLong();
    }

}