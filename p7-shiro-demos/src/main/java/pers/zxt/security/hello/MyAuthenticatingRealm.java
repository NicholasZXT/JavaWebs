package pers.zxt.security.hello;

import java.util.Map;
import java.util.HashMap;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;

/**
 * 自定义 Realm
 * Shiro框架里，Realm对应的就是存储具体用户/权限信息的对象，同时此对象还需要负责检查用户身份是否存在.
 * Shiro里的 Realm 都需要实现 org.apache.shiro.realm.Realm 接口，该接口有两个方法：
 *  + boolean supports(AuthenticationToken token): 判断是否支持对传入的 token 进行验证
 *  + AuthenticationInfo getAuthenticationInfo(AuthenticationToken token): 如果支持，则使用此方法进行验证
 * 从头开始实现 Realm 接口比较费时费力，所以 Shiro 提供了一些抽象类/实现类：
 *  实现类：
 *   + org.apache.shiro.realm.text.IniRealm: 从INI文件读取配置的 Realm
 *   + org.apache.shiro.realm.jdbc.JdbcRealm: 从数据库读取配置的 Realm
 *  抽象类：
 *   + org.apache.shiro.realm.AuthenticatingRealm: 实现 身份认证 的Realm抽象类
 *     需要实现的抽象方法 doGetAuthenticationInfo(AuthenticationToken token)
 *   + org.apache.shiro.realm.AuthorizingRealm: 实现 身份认证 + 鉴权 的Realm抽象类，它继承了上面的 AuthenticatingRealm
 *     需要实现的抽象方法 doGetAuthenticationInfo(AuthenticationToken token) + doGetAuthorizationInfo(PrincipalCollection principals)
 *  一般自定义 Realm 只需要从上面两个 Realm 抽象类出发即可。
 */
public class MyAuthenticatingRealm extends AuthenticatingRealm {

    // 使用一个 Map + 静态初始化块的方式来模拟数据库中存放的用户信息
    Map<String, String> userInfo = new HashMap<>(16);
    {
        super.setName("MyAuthenticatingRealm");
        userInfo.put("Jane", "2021");
        userInfo.put("Daniel", "2019");
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 1. 获取身份信息
        String principal = token.getPrincipal().toString();
        // 2. 获取凭证信息
        String password = new String((char[])token.getCredentials());
        System.out.println("认证用户信息：" + principal + ":" + password);
        // 3. 验证身份
        if (!userInfo.containsKey(principal)){
            // 返回 null 就表示身份验证不通过
            return null;
        }
        String passwdReal = userInfo.get(principal);
        if (!password.equals(passwdReal)){
            return null;
        }
        // 4. 身份校验通过
        AuthenticationInfo info = new SimpleAuthenticationInfo(
            principal,
            token.getCredentials(),
            this.getName()
        );
        return info;
    }
}
