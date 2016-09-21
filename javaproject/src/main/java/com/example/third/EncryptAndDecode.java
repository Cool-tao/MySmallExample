package com.example.third;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 通过DES加密解密实现一个String字符串的加密和解密.
 * Created  on 2016/9/21.
 */
public class EncryptAndDecode {
    public static final String TAG = "EncryptAndDecode";

    public static final void main(String[] args) throws NoSuchAlgorithmException,
            IllegalBlockSizeException, NoSuchPaddingException,
            BadPaddingException, InvalidKeySpecException,
            InvalidKeyException {

        // 1.1 >>> 首先要创建一个密匙
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        //选择的DES算法生成一个KeyGenerator对象
        KeyGenerator kg = KeyGenerator.getInstance("DES");
        kg.init(sr);
        //生成密匙
        SecretKey key = kg.generateKey();
        //获取密匙数据
        byte[] rawKeyData = key.getEncoded();
        System.out.println("密匙：" + rawKeyData);

        String str = "www.baidu.com";//待加密数据
        //加密方法
        byte[] encrypt = encrypt(rawKeyData, str);
        //解密方法
        decrypt(rawKeyData,encrypt);


    }

    /**
     * 加密方法
     *
     * @param rawKeyData
     * @param str
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws NoSuchPaddingException
     * @throws InvalidKeySpecException
     */
    public static byte[] encrypt(byte[] rawKeyData, String str) throws InvalidKeyException,
            NoSuchAlgorithmException, InvalidKeySpecException,
            NoSuchPaddingException, BadPaddingException,
            IllegalBlockSizeException {

        //DES算法要求一个可信任的随机数源
        SecureRandom secureRandom = new SecureRandom();
        //从原始密匙数据创建一个DESKeySpec对象
        DESKeySpec desKeySpec = new DESKeySpec(rawKeyData);
        //创建一个密匙工厂，然后用它将DESKeySec转化成一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(desKeySpec);

        //Cliher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance("DES");
        //用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, key, secureRandom);

        //获取数据加密
        byte data[] = str.getBytes();
        byte[] bytes = cipher.doFinal(data);

        System.out.println("加密后：" + bytes);
        return bytes;
    }

    /**
     * 解密方法
     *
     * @param rawKeyData
     * @param encryptedData
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeySpecException
     */
    public static String decrypt(byte[] rawKeyData, byte[] encryptedData) throws InvalidKeyException,
            NoSuchAlgorithmException, InvalidKeySpecException,
            NoSuchPaddingException, BadPaddingException,
            IllegalBlockSizeException {
        //DES算法要求有一个可信任的随机数源
        SecureRandom secureRandom = new SecureRandom();
        //从原始密匙数据创建一个DESKeySpec对象
        DESKeySpec desKeySpec = new DESKeySpec(rawKeyData);
        //创建一个密匙工厂，把DESKeySpec对象转化成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(desKeySpec);
        //Cipher对象完成解密操作
        Cipher cipher = Cipher.getInstance("DES");
        //用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, key, secureRandom);
        //解密
        byte[] bytes = cipher.doFinal(encryptedData);

        System.out.println("解密后：" + (new String(bytes)));


        return new String(bytes);
    }

}
