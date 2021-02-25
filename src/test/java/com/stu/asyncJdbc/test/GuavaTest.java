package com.stu.asyncJdbc.test;

import com.google.common.base.Charsets;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/2/25 23:13
 * @Description:
 */
public class GuavaTest {
    @Test
    public void mainTest() {
        verify("123456", "A0kO(xE1d%'4pYNXPi?=");
        trueVerify("123456", "A0kO(xE1d%'4pYNXPi?=");
    }

    public void trueVerify(String password, String randomCode) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        byte[] passwordHashStage1 = md.digest(password.getBytes());
        md.reset();

        byte[] passwordHashStage2 = md.digest(passwordHashStage1);
        md.reset();

        md.update(randomCode.getBytes());
        md.update(passwordHashStage2);

        byte[] toBeXord = md.digest();
        printBytes(toBeXord);
        int numToXor = toBeXord.length;
        for (int i = 0; i < numToXor; i++) {
            toBeXord[i] = (byte) (toBeXord[i] ^ passwordHashStage1[i]);
        }

        printBytes(passwordHashStage2);
        printBytes(toBeXord);

    }

    public byte[] verify(String password, String randomCode) {

        Hasher hasher = Hashing.sha1().newHasher();
        byte[] left = hasher.putBytes(password.getBytes()).hash().asBytes();

        Hasher rightHasher1 = Hashing.sha1().newHasher();
        byte[] rightHash1 = rightHasher1.putBytes(left).hash().asBytes();

        Hasher rightHasher2 = Hashing.sha1().newHasher();
        byte[] rightHash2 = rightHasher2.putBytes(randomCode.getBytes()).putBytes(rightHash1).hash().asBytes();

        for (int i = 0; i < left.length; i++) {
            left[i] = (byte) (left[i] ^ rightHash2[i]);
        }

        return left;
    }

    public String withAscii(byte[] value) {
        return new String(value, 0, value.length, Charsets.US_ASCII);
    }


    public void printBytes(byte[] value) {
        for (int i = 0; i < value.length; i++) {
            System.out.print(Integer.toHexString(value[i]) + " ");
        }
        System.out.println();
    }
}
