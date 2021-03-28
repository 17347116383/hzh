package com.itdy.hqsm.codecs;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

import org.apache.commons.codec.binary.Base64;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.itdy.hqsm.codecs
 * @ClassName: MyDH
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2020/4/14 22:16
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class MyDH {

 //-Djdk.crypto.KeyAgreement.legacyKDF=true
    private static String src = "面向对象编程，object-oriented！@#*5"; // 需要加密的原始字符串

    public static void main(String[] args) throws Exception {
        System.out.println("初始字符串：" + src);
        jdkDH();
    }

    /** JDK实现密钥交换算法 */
    public static void jdkDH() throws Exception{

        //1.初始化发送方密钥
        KeyPairGenerator senderKeyPairGenerator = KeyPairGenerator.getInstance("dh");
        senderKeyPairGenerator.initialize(512);//密钥长度512
        KeyPair senderKeyPair = senderKeyPairGenerator.generateKeyPair();
        byte[] senderPublicKeyEnc = senderKeyPair.getPublic().getEncoded();//发送方公钥（需要把这个发送给）

        //2.初始化接收方密钥
        KeyFactory receiverkeyFactory = KeyFactory.getInstance("dh");
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(senderPublicKeyEnc);
        PublicKey receiverPublicKey = receiverkeyFactory.generatePublic(x509EncodedKeySpec);
        DHParameterSpec dhParameterSpec = ((DHPublicKey)receiverPublicKey).getParams();
        KeyPairGenerator receiverKeyPairGenerator = KeyPairGenerator.getInstance("dh");
        receiverKeyPairGenerator.initialize(dhParameterSpec);
        KeyPair receiverKeyPair = receiverKeyPairGenerator.generateKeyPair();
        PrivateKey receiverPrivateKey = receiverKeyPair.getPrivate();

        //3.密钥构建
        KeyAgreement receiverkeyAgreement = KeyAgreement.getInstance("dh");
        receiverkeyAgreement.init(receiverPrivateKey);
        receiverkeyAgreement.doPhase(receiverPublicKey, true);
        SecretKey receiverDesKey = receiverkeyAgreement.generateSecret("des");
        byte[] receiverPublicKeyEnc = receiverKeyPair.getPublic().getEncoded();

        KeyFactory senderKeyFactory = KeyFactory.getInstance("dh");
        x509EncodedKeySpec = new X509EncodedKeySpec(receiverPublicKeyEnc);
        PublicKey senderPublicKey = senderKeyFactory.generatePublic(x509EncodedKeySpec);
        KeyAgreement senderKeyAgreement = KeyAgreement.getInstance("dh");
        senderKeyAgreement.init(senderKeyPair.getPrivate());
        senderKeyAgreement.doPhase(senderPublicKey, true);
        SecretKey senderDesKey = senderKeyAgreement.generateSecret("des");
        if (Objects.equals(senderDesKey,receiverDesKey)) {
            System.out.println("双方密钥相同");
        }

        //4.加密
        Cipher cipher = Cipher.getInstance("des");
        cipher.init(Cipher.ENCRYPT_MODE, senderDesKey);
        byte[] result = cipher.doFinal(src.getBytes());
        System.out.println("密钥交换算法加密：" + Base64.encodeBase64String(result));

        //5.解密
        cipher.init(cipher.DECRYPT_MODE, receiverDesKey);
        result = cipher.doFinal(result);
        System.out.println("密钥交换算法解密：" + new String(result));
    }

/*
    private static String src = "面向对象编程，object-oriented！@#*5"; // 需要加密的原始字符串

    public static void main(String[] args) throws Exception {

        System.out.println("初始字符串：" + src);
        jdkRSA();

    }

    public static void jdkRSA() throws Exception{
        //1.初始化密钥
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(512);//密钥长度为64的整数倍，最大是65536
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        System.out.println("RSA公钥：-------" + Base64.encodeBase64String(rsaPublicKey.getEncoded()));
        System.out.println("RSA私钥：-------" + Base64.encodeBase64String(rsaPrivateKey.getEncoded()));

        //2.1私钥加密，公钥解密【加密】
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(src.getBytes());
        System.out.println("JDK RSA私钥加密：--------" + Base64.encodeBase64String(result));

        //2.2私钥加密，公钥解密【解密】
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
        keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        result = cipher.doFinal(result);
        System.out.println("JDK RSA公钥解密：-------" + new String(result));

        //3.1公钥加密，私钥解密【加密】
        x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
        keyFactory = KeyFactory.getInstance("RSA");
        publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        result = cipher.doFinal(src.getBytes());
        System.out.println("JDK RSA公钥加密：---------" + Base64.encodeBase64String(result));

        //3.2公约加密，私钥解密【解密】
        pkcs8EncodedKeySpec =  new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
        keyFactory = KeyFactory.getInstance("RSA");
        privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        result = cipher.doFinal(result);
        System.out.println("JDK RSA私钥解密：-------" + new String(result));
    }*/
}
