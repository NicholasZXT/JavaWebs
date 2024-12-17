package pers.zxt.security.hello;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;

public class MyAuthorizingRealm extends AuthorizingRealm {
    Map<String, String> userInfo = new HashMap<>(16);
    Map<String, List<String>> userRoles = new HashMap<>(16);
    Map<String, List<String>> userPermissions = new HashMap<>(16);
    {
        super.setName("MyAuthorizingRealm");
        userInfo.put("Jane", "2021");
        userInfo.put("Daniel", "2019");
        userRoles.put("Jane", Arrays.asList("root", "admin"));
        userRoles.put("Daniel", Arrays.asList("root", "admin"));
        userPermissions.put("Jane", Arrays.asList("user:add", "user:update"));
        userPermissions.put("Daniel", Arrays.asList("user:add", "user:delete"));
    }

    /**
     * 身份认证
     * @param token the authentication token containing the user's principal and credentials.
     * @return
     * @throws AuthenticationException
     */
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

    /**
     * 鉴权
     * @param principals the primary identifying principals of the AuthorizationInfo that should be retrieved.
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userName = principals.getPrimaryPrincipal().toString();
        // 获取用户的角色和权限
        List<String> roles = userRoles.getOrDefault(userName, null);
        List<String> permissions = userPermissions.getOrDefault(userName, null);
        // 设置用户的角色和权限
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(roles);
        info.addStringPermissions(permissions);
        return info;
    }
}
