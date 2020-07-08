package org.jeecg.modules.zzj.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.omg.IOP.Encoding;

import javax.xml.crypto.Data;
import java.security.MessageDigest;
import java.util.Date;

public class SignUtils {

    public static String CreateSign(String ticket, String ts) throws Exception {
        if (!StringUtils.isNullOrEmpty(ticket)) {
            ticket = ticket.replace("%2F", "/").replace(" ", "+");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(ts);
        sb.append(ticket);
        String sign = SignUtils.SHA1HashStringForUTF8String(sb.toString()).toUpperCase();
        return sign;
    }

    public static String SHA1HashStringForUTF8String(String s) throws Exception {
        String sha1 =DigestUtils.sha1Hex(s);
        return sha1;
    }


    /**
     * @Comment SHA1实现
     * @Author Ron
     * @Date 2017年9月13日 下午3:30:36
     * @return
     */
    public static String shaEncode(String inStr) throws Exception {
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = sha.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

}

