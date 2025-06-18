package com.hz.online.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESEncryptionUtil {
    // 定义AES加密算法
    private static final String ALGORITHM = "AES";
    // 定义密钥的大小为128位
    private static final int KEY_SIZE = 128;

    private static final String SECRET_KEY = "9aCAG+VKLl4aimDFbS7z7Q==";  // 密钥

    /**
     * 使用AES算法加密数据
     *
     * @param data 需要加密的明文数据
     * //@param base64Key  用于加密的密钥（字符串形式）
     * @return 加密后的数据，经过Base64编码
     * @throws Exception 加密过程中的任何异常
     */
    public static String encrypt(String data) throws Exception {
        String base64Key = SECRET_KEY;
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        // 将字符串形式的密钥转换为字节数组，并生成SecretKeySpec对象
        SecretKeySpec secretKey = new SecretKeySpec(decodedKey, ALGORITHM);
        // 获取AES算法的加密器实例
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // 初始化加密器，设置为加密模式并传入密钥
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        // 对数据进行加密，返回加密后的字节数组
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        // 将加密后的字节数组进行Base64编码，返回编码后的字符串
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    /**
     * 使用AES算法解密数据
     *
     * @param encryptedData 加密后的数据，经过Base64编码
     * //@param base64Key          用于解密的密钥（字符串形式）
     * @return 解密后的明文数据
     * @throws Exception 解密过程中的任何异常
     */
    public static String decrypt(String encryptedData) throws Exception {
        String base64Key = SECRET_KEY;
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        // 将字符串形式的密钥转换为字节数组，并生成SecretKeySpec对象
        SecretKeySpec secretKey = new SecretKeySpec(decodedKey, ALGORITHM);
        // 获取AES算法的解密器实例
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // 初始化解密器，设置为解密模式并传入密钥
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        // 对加密数据进行Base64解码，并对解码后的数据进行解密，返回解密后的字节数组
        byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        // 将解密后的字节数组转换为字符串，返回明文数据
        return new String(decryptedData);
    }

    /**
     * 生成一个AES算法的密钥
     *
     * @return 生成的密钥，经过Base64编码
     * @throws Exception 生成密钥过程中的任何异常
     */
    public static String generateKey() throws Exception {
        // 获取AES算法的密钥生成器实例
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        // 初始化密钥生成器，设置密钥大小
        keyGen.init(KEY_SIZE);
        // 生成密钥，返回SecretKey对象
        SecretKey secretKey = keyGen.generateKey();
        // 将生成的密钥进行Base64编码，返回编码后的字符串
        //printKey(secretKey);
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

//    private static void printKey(SecretKey secretKey) {
//        // 打印原始字节数组（不易读）
//        System.out.println("Raw Key Bytes: " + new String(secretKey.getEncoded()));
//
//        // 将密钥转换为十六进制字符串
//        StringBuilder hexString = new StringBuilder();
//        for (byte b : secretKey.getEncoded()) {
//            hexString.append(String.format("%02X", b));
//        }
//        System.out.println("Hex Key: " + hexString.toString());
//
//        // 打印Base64编码的密钥
//        String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
//        System.out.println("Base64 Key: " + base64Key);
//    }

    public static void main(String[] args) throws Exception {
        String key = generateKey();// 256 位密钥
        System.out.println("测试  Generated AES Key: " + key);

        String encrypt = encrypt("测试加密接口算法");
        System.out.println(encrypt);
        String decrypt = decrypt(encrypt);
        System.out.println(decrypt);
    }
}
