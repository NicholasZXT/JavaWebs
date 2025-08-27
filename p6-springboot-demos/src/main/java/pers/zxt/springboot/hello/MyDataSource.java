package pers.zxt.springboot.hello;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;

// 1.定义数据模型封装yaml文件中对应的数据
// 2.定义为spring管控的bean
@Component
// 3.指定加载的数据，prefix指定配置文件（springboot-hello/application.yml）中的前缀
@ConfigurationProperties(prefix = "datasource")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyDataSource {

    private String driver;
    private String url;
    private String username;
    private String password;

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
