package pers.zxt.springboot.hello;

import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;

// 1.定义数据模型封装yaml文件中对应的数据
// 2.定义为spring管控的bean
@Component
// 3.指定加载的数据，prefix指定配置文件（springboot-hello/application.yml）中的前缀
@ConfigurationProperties(prefix = "datasource")
public class MyDataSource {
    private String driver;
    private String url;
    private String username;
    private String password;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "MyDataSource{" +
                "driver='" + driver + '\'' +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
