
`pers.zxt.springboot.hello` 包下是springboot的入门体验，分为两部分：
1. `SpringConfig` 展示了使用 JavaConfig 来作为spring配置文件的方式，它的调试见`test`目录下的`SpringConfigTest`测试用例。  
  与之配套的内容如下：
  + resource下的`springconf`里是对应的配置文件
  + `domain`包下是3个普通的 Java Bean 对象，分别通过3种方式被引入spring容器。
2. `MyApplication`展示 SpringBoot 的使用，与之配和的内容如下：
  + resource下的`springboot-hello`里存放对应的配置文件
  + 配合 `controller` 包里的 `BootController` 使用

