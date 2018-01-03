package com.dzf.aes;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by dzf on 03/01/2018.
 */

public class AESUtils {


    private final static String HEX = "0123456789ABCDEF";
    private static final int keyLenght = 16;
    private static final String defaultV = "@";
    private static final String TYPE = "AES/CBC/PKCS5Padding";

    /**
     * 加密
     *
     * @param key
     *            密钥
     * @param src
     *            加密文本
     * @return
     * @throws Exception
     */
    public static String encrypt(String key, String src) throws Exception {
        try {
            // /src = Base64.encodeToString(src.getBytes(), Base64.DEFAULT);
            byte[] rawKey = toMakekey(key, keyLenght, defaultV).getBytes();// key.getBytes();
            byte[] result = encrypt(rawKey, src.getBytes("utf-8"));
            // result = Base64.encode(result, Base64.DEFAULT);
            return toHex(result);

        }catch (Exception e) {
            return null;
        }
    }

    /**
     * 密钥key ,默认补的数字，补全16位数
     * @param str
     * @param strLength
     * @param val
     * @return
     */
    private static String toMakekey(String str, int strLength, String val) {

        int strLen = str.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                StringBuffer buffer = new StringBuffer();
                buffer.append(str).append(val);
                str = buffer.toString();
                strLen = str.length();
            }
        }
        return str;
    }


    /**
     * 1.通过密钥得到一个密钥专用的对象SecretKeySpec
     * @param key
     * @param src
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] key, byte[] src) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance(TYPE);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        byte[] encrypted = cipher.doFinal(src);
        return encrypted;
    }

    /**
     * 二进制转字符,转成了16进制
     * 0123456789abcdefg
     * @param buf
     * @return
     */
    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }

    /**
     * 解密
     *
     * @param key
     *            密钥
     * @param encrypted
     *            待揭秘文本
     * @return
     * @throws Exception
     */
    public static String decrypt(String key, String encrypted) {
        try {
            byte[] rawKey = toMakekey(key, keyLenght, defaultV).getBytes();// key.getBytes();
            byte[] enc = toByte(encrypted);
            // enc = Base64.decode(enc, Base64.DEFAULT);
            byte[] result = decrypt(rawKey, enc);
            // /result = Base64.decode(result, Base64.DEFAULT);

            return new String(result, "utf-8");
        }catch (Exception e) {
            return null;
        }
    }

    /**
     * 解密
     *
     * @param key
     * @param encrypted
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] key, byte[] encrypted) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance(TYPE);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    /**
     * 把16进制转化为字节数组
     * @param hexString
     * @return
     */
    public static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        return result;
    }
}
