package cn.fileserver;

import cn.fileserver.utils.RsaUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import javax.crypto.Cipher;

public class RsaTest {

    //生产公钥和私钥
    @Test
    public void genPubAndPri() throws Exception {
        RsaUtils.generateKey("E:\\mianshi\\pub.pem", "E:\\mianshi\\pri.pem", "qiyuesuo!@#mianshiti!@#");

    }

    //测试私钥加密  公钥解密
    @Test
    public void test() throws Exception {
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, RsaUtils.getPrivateKey("E:\\mianshi\\pri.pem"));
        byte[] encrypt = null;
        byte[] bytes = "444".getBytes("UTF-8");
        for (int i = 0; i < bytes.length; i += 64) {
            byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(bytes, i, i+64));
            encrypt = ArrayUtils.addAll(encrypt, doFinal);
        }
        System.out.println("outStr = " + new String(encrypt));

        //RSA解密
        Cipher cipher1 = Cipher.getInstance("RSA");
        cipher1.init(Cipher.DECRYPT_MODE, RsaUtils.getPublicKey("E:\\mianshi\\pub.pem"));
        StringBuilder sb = new StringBuilder();
        String s = new String(encrypt);
        encrypt = s.getBytes("UTF-8");
        for (int i = 0; i < encrypt.length; i += 128) {
            byte[] doFinal = cipher1.doFinal(ArrayUtils.subarray(encrypt, i, i + 128));
            sb.append(new String(doFinal));
        }
        System.out.println("string = " + sb.toString());

    }

    //测试本地加密解密
    @Test
    public void testl() throws Exception {
        String encryptStr = RsaUtils.encryptStr("121212");
        String s = RsaUtils.decryptStr(encryptStr);
        System.out.println("s = " + s);
    }
}
