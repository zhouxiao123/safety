package com.safety.util;

import java.security.MessageDigest;

/**
 * 对密码进行md5加密
 */
public class MD5 {
    public static String md5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (byte aB : b) {
                i = aB;
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            str = buf.toString();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return str;
    }
    /*public static void main(String[] args) {
        //System.out.println(md5("31111234@&%)3459@qq.com"));
        //System.out.println(md5("abcd123"));
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        System.out.println(bc.encode("111111"));

    }*/
}