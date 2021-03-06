package com.itdy.hqsm.security.myshiro.utils;



import com.itdy.hqsm.security.myshiro.entity.User;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName:
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2019/10/13
 * @Author Administrator
 */
@Component
@Transactional
public class ShiroUserUtils {

    //取得当前用户
    public static User getShiroUser() {
        //(AuthRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        Object key = SecurityUtils.getSubject().getPrincipal();
        User shiroUser = new User();
        try {
            BeanUtils.copyProperties(shiroUser, key);
        } catch (Exception e) {
        }
        return shiroUser;
    }


    //取得当前用户的id
    public static Long getUserId() {
//        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
//        return shiroUser.getUid();
        return getShiroUser().getUid();
    }

    //取得当前用户名
    public static String getName() {
        User shiroUser = (User) SecurityUtils.getSubject().getPrincipal();
        return shiroUser.getUserName();
    }

    //取得当前用户的登录名
    public static String getLoginName() {
        User shiroUser = (User) SecurityUtils.getSubject().getPrincipal();
        return shiroUser.getUserAccount();
    }

    public static void encryptPassword(User user) {
        RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
        user.setSalt(randomNumberGenerator.nextBytes().toHex());
        String credentialsSalt = user.getUserAccount() + user.getSalt();
        String newPassword = new SimpleHash(Constant.HASH_ALGORITHM, user.getUserPassword(),
                ByteSource.Util.bytes(credentialsSalt), Constant.HASH_INTERATIONS).toHex();
        user.setUserPassword(newPassword);
    }

    public static void checkPassword(User user) {
        String credentialsSalt = user.getUserAccount() + user.getSalt();
        String newPassword = new SimpleHash(Constant.HASH_ALGORITHM, user.getUserPassword(),
                ByteSource.Util.bytes(credentialsSalt), Constant.HASH_INTERATIONS).toHex();
        user.setUserPassword(newPassword);
    }

//    /**
//     *
//     *根据盐，账号，原始密码生成加密后的密码
//     */
//    public static String encryptionPasswordBySalt(String userAccount,String salt,String password) {
//        String credentialsSalt = userAccount + salt;
//        return new SimpleHash(Constant.HASH_ALGORITHM, password,
//                ByteSource.Util.bytes(credentialsSalt), Constant.HASH_INTERATIONS).toHex();
//
//    }

    /**
     * user 根据账号从数据查询出来的用户
     * password 用于判断的密码
     */
    public static Boolean checkPasswordByMeixiang(User userTemp, String oldpassword) {
        String credentialsSalt = userTemp.getUserAccount() + userTemp.getSalt();
        String oldPassword = new SimpleHash(Constant.HASH_ALGORITHM, oldpassword,
                ByteSource.Util.bytes(credentialsSalt), Constant.HASH_INTERATIONS).toHex();
        if (oldPassword.equals(userTemp.getUserPassword())) {
            return true;
        }
        return false;
    }
}
