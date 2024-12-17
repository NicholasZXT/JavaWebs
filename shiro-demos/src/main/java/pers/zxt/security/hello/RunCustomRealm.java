package pers.zxt.security.hello;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

/**
 * 演示自定义 Realm 使用
 */
public class RunCustomRealm {
    public static void main(String[] args) {
        // 1. 实例化自定义的 Realm
        MyAuthorizingRealm myAuthorizingRealm = new MyAuthorizingRealm();
        // 2. 配置环境
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(myAuthorizingRealm);
        // 3. 设置上下文对象
        SecurityUtils.setSecurityManager(securityManager);
        Subject currentUser = SecurityUtils.getSubject();
        // 4. 登录用户
        UsernamePasswordToken token = new UsernamePasswordToken("Jane", "2019");
        try {
            currentUser.login( token );
            //if no exception, that's it, we're done!
        } catch ( UnknownAccountException uae ) {
            //username wasn't in the system, show them an error message?
            uae.printStackTrace();
            System.out.println("用户不存在");
        } catch ( IncorrectCredentialsException ice ) {
            //password didn't match, try again?
            ice.printStackTrace();
            System.out.println("密码错误");
        } catch ( LockedAccountException lae ) {
            //account for that username is locked - can't login.  Show them a message?
            lae.printStackTrace();
            System.out.println("用户已被锁定");
        } catch (AuthenticationException e) {
            e.printStackTrace();
            System.out.println("登录失败");
        }
        // 5. 检查用户权限
        // 6. 登出
        currentUser.logout();
    }
}
