package org.djflying.apache.zookeeper.javaclient.auth;

import java.security.NoSuchAlgorithmException;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

/**
 * 对“username:password”进行编码
 *
 * @author dj4817
 * @version $Id: DigestAuthenticationProviderUsage.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class DigestAuthenticationProviderUsage {

    /**
     * 测试对“username:password”进行编码
     *
     * @param args
     * @throws NoSuchAlgorithmException
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        // generateDigest方法会对password部分先进行SHA-1算法加密，然后再进行BASE64编码。
        System.out.println(DigestAuthenticationProvider.generateDigest("super:zk-book"));
    }
}