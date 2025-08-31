package pers.zxt.springboot.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 配置类
 */
@Configuration
@EnableWebSecurity // 启用 Spring Security 的 Web 安全支持
public class SecurityConfig {

    /**
     * 定义 SecurityFilterChain Bean
     * 这是 Spring Security 5.7+ 推荐的方式（替代了继承 WebSecurityConfigurerAdapter）
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 配置授权规则 (Authorization)
            .authorizeHttpRequests(authz -> authz
                .antMatchers("/public/**").permitAll() // 公共路径，无需认证
                .antMatchers("/admin/**").hasRole("ADMIN") // /admin/** 路径需要 ADMIN 角色
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN") // /user/** 路径需要 USER 或 ADMIN 角色
                .antMatchers("/").permitAll() // 根路径公开
                // Spring Security 6.0+ 新增的 requestMatchers() 方法
                //.requestMatchers("/public/**").permitAll() // 公共路径，无需认证
                //.requestMatchers("/admin/**").hasRole("ADMIN") // /admin/** 路径需要 ADMIN 角色
                //.requestMatchers("/user/**").hasAnyRole("USER", "ADMIN") // /user/** 路径需要 USER 或 ADMIN 角色
                //.requestMatchers("/").permitAll() // 根路径公开
                .anyRequest().authenticated() // 其他所有请求都需要认证
            )
            // 配置表单登录 (Form Login)
            .formLogin(form -> form
                .loginPage("/login") // 自定义登录页面的 URL
                .loginProcessingUrl("/perform_login") // 处理登录请求的 URL (form action)
                .defaultSuccessUrl("/home", true) // 登录成功后的默认跳转 URL
                .failureUrl("/login?error=true") // 登录失败后跳转的 URL
                .permitAll() // 登录页面和登录处理 URL 允许所有人访问
            )
            // 配置注销 (Logout)
            .logout(logout -> logout
                .logoutUrl("/logout") // 注销请求的 URL
                .logoutSuccessUrl("/login?logout") // 注销成功后跳转的 URL
                .permitAll() // 注销 URL 允许所有人访问
            )
            // 禁用 CSRF (在开发和简单示例中常禁用，生产环境应启用并妥善处理)
            // 注意：如果启用 CSRF，登录表单必须包含 CSRF token
            .csrf(csrf -> csrf.disable());

        return http.build();
    }

    /**
     * 定义 UserDetailsService Bean
     * 这里使用内存存储，生产环境应使用数据库或 LDAP
     */
    @Bean
    public UserDetailsService userDetailsService() {
        // 创建用户
        UserDetails user = User.withDefaultPasswordEncoder() // 注意：仅用于演示！生产环境用 BCrypt
            .username("user")
            .password("password") // 密码会被上面的 DefaultPasswordEncoder 编码
            .roles("USER")
            .build();

        UserDetails admin = User.withDefaultPasswordEncoder()
            .username("admin")
            .password("adminpass")
            .roles("ADMIN")
            .build();

        // 将用户存储在内存中
        return new InMemoryUserDetailsManager(user, admin);
    }
}
