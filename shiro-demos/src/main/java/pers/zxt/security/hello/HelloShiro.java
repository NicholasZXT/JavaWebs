package pers.zxt.security.hello;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.util.Factory;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.env.Environment;
import org.apache.shiro.env.BasicIniEnvironment;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresPermissions;

/**
 * Shiro基本使用流程
 * 参考官方教程：https://shiro.apache.org/tutorial.html
 * StackOverflow问答：https://stackoverflow.com/questions/45265622/apache-shiro-1-4-0-initialization
 */
public class HelloShiro {

    public static void main(String[] args) {
        // 1. Shiro的环境上下文是 SecurityManager，有多种方式创建该对象
        // 这里使用的是基于 INI 配置文件 + 工厂方法 来获取该对象并设置——不过下面的方式已经被标记为废弃
        //Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        //SecurityManager securityManager = factory.getInstance();
        // Shiro 1.5 之后，推荐使用下面的方式了
        Environment env = new BasicIniEnvironment("classpath:shiro.ini");
        SecurityManager securityManager = env.getSecurityManager();
        // 设置上下文对象
        SecurityUtils.setSecurityManager(securityManager);

        // 2. Shiro 中使用 Subject 对象来代表当前用户
        // 下面先获得一个用户对象，但是还没有赋予任何用户身份信息 —— 也就是匿名用户状态
        Subject currentUser = SecurityUtils.getSubject();

        // 3. 执行用户登录步骤，这里的用户名和密码可以通过请求得到，也可以在GUI界面得到，使用 UsernamePasswordToken 类进行封装
        UsernamePasswordToken token = new UsernamePasswordToken("admin", "admin123");
        token.setRememberMe(true);
        // 尝试登录用户
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

        // 4. 已登录用户可以执行的操作
        System.out.println(currentUser.isAuthenticated());
        System.out.println("User [" + currentUser.getPrincipal() + "] logged in successfully.");
        // 检查角色
        if ( currentUser.hasRole( "admin" ) ) {
            System.out.println("admin role check passed" );
        } else {
            System.out.println( "admin role check failed" );
        }
        // 检查权限
        if ( currentUser.isPermitted( "user:select" ) ) {
            System.out.println("user:select check passed");
        } else {
            System.out.println("user:select check failed");
        }

        // 5. 注销用户
        currentUser.logout();
    }

    // 上面是通过代码的方式检查权限，Shiro 还提供了如下注解的方式执行检查
    @RequiresAuthentication
    @RequiresUser
    @RequiresRoles("admin")
    @RequiresPermissions("user:update")
    public static void check(){
        System.out.println("check shiro");
    }
}
