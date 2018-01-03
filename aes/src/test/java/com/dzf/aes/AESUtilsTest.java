package com.dzf.aes;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class AESUtilsTest {
    @Test
    public void encrypt() throws Exception {

        String strEncrypt = AESUtils.encrypt("1234567890", "hello world");
        assertNotNull(strEncrypt);

        String strDecrypt = AESUtils.decrypt("123456789", strEncrypt);
        assertEquals("hello world", strDecrypt);
    }
}