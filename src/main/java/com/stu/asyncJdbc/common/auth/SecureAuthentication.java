package com.stu.asyncJdbc.common.auth;

import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/2/25 23:02
 * @Description:
 */
@SuppressWarnings("all")
public class SecureAuthentication implements IAuthPlugin {

    @Override
    public byte[] verify(byte[] password, byte[] randomCode) {
        Hasher hasher = Hashing.sha1().newHasher();
        byte[] left = hasher.putBytes(password).hash().asBytes();

        Hasher rightHasher1 = Hashing.sha1().newHasher();
        byte[] rightHash1 = rightHasher1.putBytes(left).hash().asBytes();

        Hasher rightHasher2 = Hashing.sha1().newHasher();
        byte[] rightHash2 = rightHasher2.putBytes(randomCode).putBytes(rightHash1).hash().asBytes();

        for (int i = 0; i < left.length; i++) {
            left[i] = (byte) (left[i] ^ rightHash2[i]);
        }

        return left;
    }

    @Override
    public String getPluginName() {
        return "mysql_native_password";
    }

}
