package com.itdy.hqsm.security.myshiro.config;


import com.itdy.hqsm.security.myshiro.utils.Constant;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;

/**
 * @ClassName:
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2019/10/13
 * @Author Administrator  @Configuration
 */

public class MySimpleCredentialsMatcher extends SimpleCredentialsMatcher {

    private static final Logger log = LoggerFactory.getLogger(SimpleCredentialsMatcher.class);


    public MySimpleCredentialsMatcher() {
        super();
    }

    /**
     *
     * @param token
     * @return
     */
    protected Object getCredentials(AuthenticationToken token) {
        return token.getCredentials();
    }

    /**
     *
     * @param info
     * @return
     */
    protected Object getCredentials(AuthenticationInfo info) {
        return info.getCredentials();
    }

    /**
     *
     * @param tokenCredentials
     * @param accountCredentials
     * @return
     */
    protected boolean equals(Object tokenCredentials, Object accountCredentials) {
        if (log.isDebugEnabled()) {
            log.debug("对类型的tokenCredentials执行凭据相等性检查 [" + tokenCredentials.getClass().getName() + " 和类型的accountCredentials[" + accountCredentials.getClass().getName() + "]");
        }
        if (this.isByteSource(tokenCredentials) && this.isByteSource(accountCredentials)) {
            if (log.isDebugEnabled()) {
                log.debug("这两个凭据参数都可以很容易地转换为字节数组。执行数组=比较");
            }
            byte[] tokenBytes = this.toBytes(tokenCredentials);
            byte[] accountBytes = this.toBytes(accountCredentials);
            // System.out.println(MessageDigest.isEqual(tokenBytes, accountBytes));
            return MessageDigest.isEqual(tokenBytes, accountBytes);
        } else {
            // System.out.println(accountCredentials.equals(tokenCredentials));
            return accountCredentials.equals(tokenCredentials);
        }
    }


    /**
     *
     * @param token
     * @param info
     * @return
     */
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String newPassword = null;
        //获取当前用户数据
        MyUsernamePasswordToken authcToken = (MyUsernamePasswordToken) token;
        //Object tokenCredentials = this.getCredentials(token);
        Object accountCredentials = this.getCredentials(info);
        //如果为PC端登录，正常密码验证
        if (authcToken.getActionType() == 1) {
            String credentialsSalt = authcToken.getUsername() + authcToken.getSalt();
            newPassword = new SimpleHash(Constant.HASH_ALGORITHM, authcToken.getPassword(),
                    ByteSource.Util.bytes(credentialsSalt), Constant.HASH_INTERATIONS).toHex();
        } else if (authcToken.getActionType() == 2) {
            //如果微信端登录，密码不需要has加密
            newPassword = authcToken.getPass();
        } else if (authcToken.getActionType() == 3) {
            //如果手机验证码登录，密码不需要has加密
            newPassword = authcToken.getPass();
        }
        Boolean a = this.equals(newPassword, accountCredentials);
        //System.out.println(this.equals(newPassword, accountCredentials));
        return this.equals(newPassword, accountCredentials);
    }
}
