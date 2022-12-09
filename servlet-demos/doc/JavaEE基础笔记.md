# 关于直播

1. 什么时间直播？
   - 晚上8:00到10:00
2. 每周直播几天？
   - 3天（周一、周三、周五）
   - 本周比较特殊：周四周五周六三天直播，从下周开始就是一三五直播。
3. 直播什么内容？
   - 从JavaWEB开始。（Servlet为核心，从Servlet开始学习。）
   - JSP（JSP使用较少了，但是还有用，所以时间上少一些。快速地学习一下JSP。）
   - AJAX（异步通信技术。）
   - jQuery（JavaScript库。）
   - MyBatis
   - Spring
   - SpringMVC
   - SpringBoot
   - SpringCloud
   - ....

# 需要提前准备了哪些技术，接下来的课才能听懂？

- JavaSE（Java语言的标准版，Java提供的最基本的类库）
  - Java的开发环境搭建
  - Java的基础语法
  - Java的面向对象
  - 数组
  - 常用类
  - 异常
  - 集合
  - 多线程
  - IO流
  - 反射机制
  - 注解Annotation
  - .....
- MySQL（数据库）
  - 最基本的要求是：能够编写增删改查等简单的SQL语句即可。
- JDBC（Java语言链接数据库）
  - 这是一套Java语言链接数据库的接口。
- WEB前端（会一些）
  - HTML（网页）
  - CSS（层叠样式表语言，修饰HTML）
  - JavaScript（一种脚本语言，运行在浏览器当中，让浏览器中的元素可以增删改。让网页产生更强的交互效果）
- WEB后端
  - Servlet（Server Applet：服务器端的Java小程序）
  - JSP
  - AJAX
  - jQuery
  - MyBatis
  - Spring
  - SpringMVC
  - SpringBoot
  - SpringCloud
  - ......

# Typora软件介绍

- Markdown文本编辑器（可以编辑xxx.md文件）

  ```java
  public class Test{
      public static void main(String[] args){
          System.out.println("Test code!");
      }
  }
  ```

# Servlet

## 关于系统架构

1. 系统架构包括什么形式？
   - C/S架构
   - B/S架构
2. C/S架构？
   - Client / Server（客户端 / 服务器）
   - C/S架构的软件或者说系统有哪些呢？
     - QQ（先去腾讯官网下载一个QQ软件，几十MB，然后把这个客户端软件安装上去，然后输入QQ号以及密码，登录之后，就可以和你的朋友聊天了，就可以使用这个软件了。）
   - C/S架构的特点：需要安装特定的客户端软件。
   - C/S架构的系统优点和缺点分别是什么？
     - 优点：
       - 速度快（软件中的数据大部分都是集成到客户端软件当中的，很少量的数据从服务器端传送过来，所以C/S结构的系统速度快）
       - 体验好（速度又快，界面又酷炫，当然体验好了。）
       - 界面酷炫（专门的语言去实现界面的，更加灵活。）
       - 服务器压力小（因为大量的数据都是集成在客户端软件当中，所以服务器只需要传送很少的数据量，当然服务器压力小。）
       - 安全（因为大量的数据是集成在客户端软件当中的，并且客户端有很多个，服务器虽然只有一个，就算服务器那边地震了，火灾了，服务器受损了，问题也不大，因为大量的数据在多个客户端上有缓存，有存储，所以从这个方面来说，C/S结构的系统比较安全。）
       - .....
     - 缺点：
       - 升级维护比较差劲。（升级维护比较麻烦。成本比较高。每一个客户端软件都需要升级。有一些软件不是那么容易安装的。）
3. B/S架构？
   - B/S（Browser / Server，浏览器 / 服务器）
   - http://www.baidu.com
   - http://www.jd.com
   - http://www.126.com
   - B/S结构的系统是不是一个特殊的C/S系统？
     - 实际上B/S结构的系统还是一个C/S，只不过这个C比较特殊，这个Client是一个固定不变浏览器软件。
   - B/S结构的系统优点和缺点是：
     - 优点：
       - 升级维护方便，成本比较低。（只需要升级服务器端即可。）
       - 不需要安装特定的客户端软件，用户操作极其方便。只需要打开浏览器，输入网址即可。
     - 缺点：
       - 速度慢（不是因为带宽低的问题，是因为所有的数据都是在服务器上，用户发送的每一个请求都是需要服务器全身心的响应数据，所以B/S结构的系统在网络中传送的数据量比较大。）
       - 体验差（界面不是那么酷炫，因为浏览器只支持三个语言HTML CSS JavaScript。在加上速度慢。）
       - 不安全（所有的数据都在服务器上，只要服务器发生火灾，地震等不可抗力，最终数据全部丢失。）
       - ....
4. C/S和B/S结构的系统，哪个好，哪个不好？
   - 这个问题问的没有水平。并不是哪个好，哪个不好。不同结构的系统在不同的业务场景下有不同的适用场景。
   - 娱乐性软件建议使用？
     - C/S 结构
   - 公司内部使用的一些业务软件建议使用？
     - 公司内部使用的系统，需要维护成本低。
     - 公司内部使用的系统，不需要很酷炫。
     - 公司内部使用的企业级系统主要是能够进行数据的维护即可。
     - B/S 结构。
5. 注意了：开发B/S结构的系统，其实就是开发网站，其实就是开发一个WEB系统。
   - 开发一个WEB系统你需要会哪些技术？
     - WEB前端（运行在浏览器上的程序。）
       - HTML
       - CSS
       - JavaScript
     - WEB后端（WEB服务器端的程序。）
       - Java可以（Java做WEB开发我们称为JavaWEB开发。JavaWEB开发最核心的规范：Servlet【Server Applet服务器端的Java小程序。】）
       - C语言也可以
       - C++也可以
       - Python也行
       - PHP也可以
       - ....
6. JavaEE是什么？
   - Java包括三大块：
     - JavaSE
       - Java标准版（一套类库：别人写好的一套类库，只不过这个类库是标准类库，走EE，或者走ME，这个SE一定是基础，先学。）
     - JavaEE（WEB方向，WEB系统。）
       - Java企业版（也是一套类库：也是别人写好的一套类库，只不过这套类库可以帮助我们完成企业级项目的开发，专门为企业内部提供解决方案的一套（多套）类库。）
       - 别人写好的，你用就行了，用它可以开发企业级项目。
       - 可以开发web系统。
       - Java比较火爆的就是这个JavaEE方向。
     - JavaME
       - Java微型版（还是一套类库，只不过这套类库帮助我们进行电子微型设备内核程序的开发）
       - 机顶盒内核程序，吸尘器内核程序，电冰箱内核程序，电饭煲内核程序。。。。。
   - JavaEE实际上包括很多种规范，13种规范，其中Servlet就是JavaEE规范之一。学Servlet还是Java语言。

## B/S结构的系统通信原理（没有涉及到Java小程序）

- WEB系统的访问过程
  - 第一步：打开浏览器
  - 第二步：找到地址栏
  - 第三步：输入一个合法的网址
  - 第四步：回车
  - 第五步：在浏览器上会展示响应的结果。
- 关于域名：
  - https://www.baidu.com/ （网址）
  - www.baidu.com 是一个域名
  - 在浏览器地址栏上输入域名，回车之后，域名解析器会将域名解析出来一个具体的IP地址和端口号等。
  - 解析结果也许是：http://110.242.68.3:80/index.html
- IP地址是啥？
  - 计算机在网络当中的一个身份证号。在同一个网络当中，IP地址是唯一的。
  - A计算机要想和B计算机通信，首先你需要知道B计算机的IP地址，有了IP地址才能建立连接。
- 端口号是啥？
  - 一个端口代表一个软件（一个端口代表一个应用，一个端口仅代表一个服务）。
  - 一个计算机当中有很多软件，每一个软件启动之后都有一个端口号。
  - 在同一个计算机上，端口号具有唯一性。
- 一个WEB系统的通信原理？通信步骤：
  - 第一步：用户输入网址（URL）
  - 第二步：域名解析器进行域名解析：http://110.242.68.3:80/index.html
  - 第三步：浏览器软件在网络中搜索110.242.68.3这一台主机，直到找到这台主机。
  - 第四步：定位110.242.68.3这台主机上的服务器软件，因为是80端口，可以很轻松的定位到80端口对应的服务器软件。
  - 第五步：80端口对应的服务器软件得知浏览器想要的资源名是：index.html
  - 第六步：服务器软件找到index.html文件，并且将index.html文件中的内容直接输出响应到浏览器上。
  - 第七步：浏览器接收到来自服务器的代码（HTML CSS JS）
  - 第八步：浏览器渲染，执行HTML CSS JS代码，展示效果。
- 什么是URL？
  - 统一资源定位符（http://www.baidu.com）
- 什么是请求，什么是响应？
  - 请求和响应实际上说的是数据的流向不同。
  - 从Browser端发送数据到Server端，我们称为请求。英语单词：request
  - 从Server端向浏览器Browser端发送数据，我们称为响应。英语单词：response
  - B --> S （请求request）
  - S --> B （响应response）

## 关于WEB服务器软件

- WEB服务器软件都有哪些呢？（这些软件都是提前开发好的。）
  - Tomcat（WEB服务器）
  - jetty（WEB服务器）
  - JBOSS（应用服务器）
  - WebLogic（应用服务器）
  - WebSphere（应用服务器）
- 应用服务器和WEB服务器的关系？
  - 应用服务器实现了JavaEE的所有规范。(JavaEE有13个不同的规范。)
  - WEB服务器只实现了JavaEE中的Servlet + JSP两个核心的规范。
  - 通过这个讲解说明了：应用服务器是包含WEB服务器的。
  - 用过JBOSS服务器的同学应该很清楚，JBOSS中内嵌了一个Tomcat服务器。
- Tomcat下载
  - apache官网地址：https://www.apache.org/
  - tomcat官网地址：https://tomcat.apache.org
  - tomcat开源免费的轻量级WEB服务器。
  - tomcat还有另外一个名字：catalina（catalina是美国的一个岛屿，风景秀丽，据说作者是在这个风景秀丽的小岛上开发了一个轻量级的WEB服务器，体积小，运行速度快，因此tomcat又被称为catalina）
  - tomcat的logo是一只公猫（寓意表示Tomcat服务器是轻巧的，小巧的，果然，体积小，运行速度快，只实现了Servlet+JSP规范）
  - tomcat是java语言写的。
  - tomcat服务器要想运行，必须先又jre（Java的运行时环境）
- Tomcat服务器要想运行，需要先有jre，所以要先安装JDK，配置java运行环境。
  - JAVA_HOME=C:\Program Files\Java\jdk-17.0.1
  - PATH=%JAVA_HOME%\bin
  - 目前JAVA_HOME没有配置，思考一个问题，这样行不行呢？目前只运行java程序是没问题的。真的没问题吗？
- Tomcat服务器的安装：
  - 绿色版本的安装很简单，直接zip包解压即可。解压就是安装。
  - 我有一个好习惯，在C盘的根目录下新建一个dev目录，java开发所有相关的工具都安装到dev目录下，这样比较方便管理。（你随意）
  - 启动Tomcat
    - bin目录下有一个文件：startup.bat,通过它可以启动Tomcat服务器。
      - xxx.bat文件是个什么文件？bat文件是windows操作系统专用的，bat文件是批处理文件，这种文件中可以编写大量的windows的dos命令，然后执行bat文件就相当于批量的执行dos命令。
      - startup.sh，这个文件在windows当中无法执行，在Linux环境当中可以使用。在Linux环境下能够执行的是shell命令，大量的shell命令编写在shell文件当中，然后执行这个shell文件可以批量的执行shell命令。
      - tomcat服务器提供了bat和sh文件，说明了这个tomcat服务器的通用性。
      - 分析startup.bat文件得出，执行这个命令，实际上最后是执行：catalina.bat文件。
      - catalina.bat文件中有这样一行配置：`MAINCLASS=org.apache.catalina.startup.Bootstrap` （这个类就是main方法所在的类。）
      - tomcat服务器就是Java语言写的，既然是java语言写的，那么启动Tomcat服务器就是执行main方法。
    - 我们尝试打开dos命令窗口，在dos命令窗口中输入startup.bat来启动tomcat服务器。
    - **启动Tomcat服务器只配置path对应的bin目录是不行的**，有两个环境变量需要配置：
      - JAVA_HOME=JDK的根
      - CATALINA_HOME=Tomcat服务器的根
- 关于Tomcat服务器的目录
  - bin ： 这个目录是Tomcat服务器的命令文件存放的目录，比如：启动Tomcat，关闭Tomcat等。
  - conf： 这个目录是Tomcat服务器的配置文件存放目录。（server.xml文件中可以配置端口号，默认Tomcat端口是8080）
  - lib ：这个目录是Tomcat服务器的核心程序目录，因为Tomcat服务器是Java语言编写的，这里的jar包里面都是class文件。
  - logs: Tomcat服务器的日志目录，Tomcat服务器启动等信息都会在这个目录下生成日志文件。
  - temp：Tomcat服务器的临时目录。存储临时文件。
  - webapps：这个目录当中就是用来存放大量的webapp（web application：web应用）
  - work：这个目录是用来存放JSP文件翻译之后的java文件以及编译之后的class文件。

- 配置Tomcat服务器需要哪些环境变量？
  - JAVA_HOME=JDK的根
  - CATALINA_HOME=Tomcat服务器的根
  - PATH=%JAVA_HOME%\bin;%CATALINA_HOME%\bin
- 启动Tomcat： startup
- 关闭Tomcat：stop （shutdown.bat文件重命名为stop.bat，为什么？原因是shutdown命令和windows中的关机命令冲突。所以修改一下。）

- 怎么测试Tomcat服务器有没有启动成功呢？
  - 打开浏览器，在浏览器的地址栏上输入URL即可：
    - http://ip地址:端口号
    - ip地址是什么？端口号我知道，是8080
    - 本机的IP地址是：127.0.0.1，或者是localhost，都行。

## 实现一个最基本的web应用（这个web应用中没有java小程序）

- 第一步：找到CATALINA_HOME\webapps目录
  - 因为所有的webapp要放到webapps目录下。（没有为什么，这是Tomcat服务器的要求。如果不放到这里，Tomcat服务器找不到你的应用。）
- 第二步：在CATALINA_HOME\webapps目录下新建一个子目录，起名：oa
  - 这个目录名oa就是你这个webapp的名字。
- 第三步：在oa目录下新建资源文件，例如：index.html
  - 编写index.html文件的内容。
- 第四步：启动Tomcat服务器
- 第五步：打开浏览器，在浏览器地址栏上输入这样的URL：
  - http://127.0.0.1:8080/oa/index.html

- 思考一个问题：

  - 我们在浏览器上直接输入一个URL，然后回车。这个动作和超链接一样吗？既然是一样的，我们完全可以使用超链接。

    ```html
    <!--注意以下的路径，以/开始，带项目名，是一个绝对路径。不需要添加：http://127.0.0.1:8080-->
    <a href="/oa/login.html">user login2</a>
    
    <!--多个层级也没有关系，正常访问即可。-->
    <!--注意：我们目前前端上的路径都以“/”开始的，都是加项目名的。-->
    <a href="/oa/test/debug/d.html">d page</a>
    ```


- http://127.0.0.1:8080/oa/userList.html 
  - 访问这个地址，可以展示一个用户列表页面。但是这个用户列表页面是写死在HTML文件当中的。这种资源我们称为静态资源。怎么能变成动态资源。显然需要连接数据库。
  - 连接数据库需要JDBC程序，也就是说需要编写Java程序连接数据库，数据库中有多少条记录，页面上就显示多少条记录，这种技术被称为动态网页技术。（动态网页技术并不是说页面中有flash动画。动态网页技术是说页面中的数据是动态的，根据数据库中数据的变化而变化。）

## 对于一个动态的web应用来说，一个请求和响应的过程有多少个角色参与，角色和角色之间有多少个协议

![BS结构系统的通信原理2](BS结构系统的通信原理2.png)

- 有哪些角色（在整个BS结构的系统当中，有哪些人参与进去了）
  - 浏览器软件的开发团队（浏览器软件太多了：谷歌浏览器、火狐浏览器、IE浏览器....）
  - WEB Server的开发团队（WEB Server这个软件也是太多了：Tomcat、Jetty、WebLogic、JBOSS、WebSphere....）
  - DB Server的开发团队（DB Server这个软件也是太多了：Oracle、MySQL.....）
  - webapp的开发团队（WEB应用是我们做为JavaWEB程序员开发的）
- 角色和角色之间需要遵守哪些规范，哪些协议
  - ==webapp开发团队==  和 ==WEB Server开发团队==  之间有一套规范：**JavaEE规范之一Servlet规范**
    - Servlet规范的作用是什么？
      - WEB Server  和 webapp 解耦合
  - Browser 和 WebServer 之间有一套传输协议：HTTP协议（超文本传输协议）
  - webapp开发团队  和  DB Server开发团队 之间有一套规范：JDBC规范

![BS结构系统的角色和协议](BS结构系统的角色和协议.png)

- Servlet规范是一个什么规范？
  - 遵循Servlet规范的webapp，这个webapp就可以放在不同的WEB服务器中运行。（因为这个webapp是遵循Servlet规范的。）
  - Servlet规范包括什么呢？
    - 规范了哪些接口
    - 规范了哪些类
    - 规范了一个web应用中应该有哪些配置文件
    - 规范了一个web应用中配置文件的名字
    - 规范了一个web应用中配置文件存放的路径
    - 规范了一个web应用中配置文件的内容
    - 规范了一个合法有效的web应用它的目录结构应该是怎样的。
    - .....

## 开发一个带有Servlet（Java小程序）的webapp（重点）

- 开发步骤是怎样的？

  - 第一步：在webapps目录下新建一个目录，起名crm（这个crm就是webapp的名字）。当然，也可以是其它项目，比如银行项目，可以创建一个目录bank，办公系统可以创建一个oa。

    - 注意：crm就是这个webapp的根

  - 第二步：在webapp的根下新建一个目录：WEB-INF

    - 注意：这个目录的名字是Servlet规范中规定的，必须全部大写，必须一模一样。必须的必须。

  - 第三步：在WEB-INF目录下新建一个目录：classes

    - 注意：这个目录的名字必须是全部小写的classes。这也是Servlet规范中规定的。另外这个目录下一定存放的是Java程序编译之后的class文件（这里存放的是字节码文件）。

  - 第四步：在WEB-INF目录下新建一个目录：lib

    - 注意：这个目录不是必须的。但如果一个webapp需要第三方的jar包的话，这个jar包要放到这个lib目录下，这个目录的名字也不能随意编写，必须是全部小写的lib。例如java语言连接数据库需要数据库的驱动jar包。那么这个jar包就一定要放到lib目录下。这Servlet规范中规定的。

  - 第五步：在WEB-INF目录下新建一个文件：web.xml

    - 注意：这个文件是必须的，这个文件名必须叫做web.xml。这个文件必须放在这里。一个合法的webapp，web.xml文件是必须的，这个web.xml文件就是一个配置文件，在这个配置文件中描述了请求路径和Servlet类之间的对照关系。

    - 这个文件最好从其他的webapp中拷贝，最好别手写。没必要。复制粘贴

    - ```xml
      <?xml version="1.0" encoding="UTF-8"?>
      
      <web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee
                            https://jakarta.ee/xml/ns/jakartaee/web-app_5_0.xsd"
        version="5.0"
        metadata-complete="true">
      
      
      </web-app>
      
      ```

  - 第六步：编写一个Java程序，这个小Java程序也不能随意开发，这个小java程序必须实现Servlet接口。

    - 这个Servlet接口不在JDK当中。（因为Servlet不是JavaSE了。Servlet属于JavaEE，是另外的一套类库。）
    - Servlet接口（Servlet.class文件）是Oracle提供的。（最原始的是sun公司提供的。）
    - Servlet接口是JavaEE的规范中的一员。
    - Tomcat服务器实现了Servlet规范，所以Tomcat服务器也需要使用Servlet接口。Tomcat服务器中应该有这个接口，Tomcat服务器的CATALINA_HOME\lib目录下有一个servlet-api.jar，解压这个servlet-api.jar之后，你会看到里面有一个Servlet.class文件。
    - 重点：从JakartaEE9开始，Servlet接口的全名变了：jakarta.servlet.Servlet
    - 注意：编写这个Java小程序的时候，java源代码你愿意在哪里就在哪里，位置无所谓，你只需要将java源代码编译之后的class文件放到classes目录下即可。

  - 第七步：编译我们编写的HelloServlet

    - 重点：你怎么能让你的HelloServlet编译通过呢？配置环境变量CLASSPATH

      CLASSPATH=.;C:\dev\apache-tomcat-10.0.12\lib\servlet-api.jar

    - 思考问题：以上配置的CLASSPATH和Tomcat服务器运行有没有关系？

      - 没有任何关系，以上配置这个环境变量只是为了让你的HelloServlet能够正常编译生成class文件。

  - 第八步：将以上编译之后的HelloServlet.class文件拷贝到WEB-INF\classes目录下。

  - 第九步：在web.xml文件中编写配置信息，让“请求路径”和“Servlet类名”关联在一起。

    - 这一步用专业术语描述：在web.xml文件中注册Servlet类。

    - ```xml
      <?xml version="1.0" encoding="UTF-8"?>
      
      <web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee
                            https://jakarta.ee/xml/ns/jakartaee/web-app_5_0.xsd"
        version="5.0"
        metadata-complete="true">
      
      	<!--servlet描述信息-->
      	<!--任何一个servlet都对应一个servlet-mapping -->
      	<servlet>
      		<servlet-name>fdsafdsagfdsafdsa</servlet-name>
      		<!--这个位置必须是带有包名的全限定类名-->
      		<servlet-class>com.bjpowernode.servlet.HelloServlet</servlet-class>
      	</servlet>
      
      	<!--servlet映射信息-->
      	<servlet-mapping>
      		<!--这个也是随便的，不过这里写的内容要和上面的一样。-->
      		<servlet-name>fdsafdsagfdsafdsa</servlet-name>
      		<!--这里需要一个路径-->
      		<!--这个路径唯一的要求是必须以 / 开始-->
      		<!--当前这个路径可以随便写-->
      		<url-pattern>/fdsa/fd/saf/d/sa/fd/sa/fd</url-pattern>
      	</servlet-mapping>
      	
      </web-app>
      
      ```

      

  - 第十步：启动Tomcat服务器

  - 第十一步：打开浏览器，在浏览器地址栏上输入一个url，这个URL必须是：

    - http://127.0.0.1:8080/crm/fdsa/fd/saf/d/sa/fd/sa/fd   
    - 非常重要的一件事：浏览器上的请求路径不能随便写，这个请求路径必须和web.xml文件中的url-pattern一致。
    - 注意：浏览器上的请求路径和web.xml文件中的url-pattern的唯一区别就是：浏览器上的请求路径带项目名：/crm

  - 浏览器上编写的路径太复杂，可以使用超链接。（**非常重要：html页面只能放到WEB-INF目录外面。**）

  - 以后不需要我们编写main方法了。tomcat服务器负责调用main方法，Tomcat服务器启动的时候执行的就是main方法。我们javaweb程序员只需要编写Servlet接口的实现类，然后将其注册到web.xml文件中，即可。

  - 总结一下：一个合法的webapp目录结构应该是怎样的？

    ```
    webapproot
         |------WEB-INF
         		  |------classes(存放字节码)
         		  |------lib(第三方jar包)
         		  |------web.xml(注册Servlet)
         |------html
         |------css
         |------javascript
         |------image
         ....
    ```

  - 浏览器发送请求，到最终服务器调用Servlet中的方法，是怎样的一个过程？（以下这个过程描述的很粗糙。其中还有很多步骤我省略了。）

    - 用户输入URL，或者直接点击超链接：http://127.0.0.1:8080/crm/fdsa/fd/saf/d/sa/fd/sa/fd  
    - 然后Tomcat服务器接收到请求，截取路径：/crm/fdsa/fd/saf/d/sa/fd/sa/fd  
    - Tomcat服务器找到crm项目
    - Tomcat服务器在web.xml文件中查找/fdsa/fd/saf/d/sa/fd/sa/fd  对应的Servlet是：com.bjpowernode.servlet.HelloServlet
    - Tomcat服务器通过反射机制，创建com.bjpowernode.servlet.HelloServlet的对象。
    - Tomcat服务器调用com.bjpowernode.servlet.HelloServlet对象的service方法。

## 关于JavaEE的版本

- JavaEE目前最高版本是 JavaEE8
- JavaEE被Oracle捐献了，Oracle将JavaEE规范捐献给Apache了。
- Apache把JavaEE换名了，以后不叫JavaEE了，以后叫做 jakarta EE。
- 以后没有JavaEE了。以后都叫做Jakarta EE。
- JavaEE8版本升级之后的"JavaEE 9"，不再是"JavaEE9"这个名字了，叫做JakartaEE9
- JavaEE8的时候对应的Servlet类名是：javax.servlet.Servlet
- JakartaEE9的时候对应的Servlet类名是：jakarta.servlet.Servlet （包名都换了）
- 如果你之前的项目还是在使用javax.servlet.Servlet，那么你的项目无法直接部署到Tomcat10+版本上。你只能部署到Tomcat9-版本上。在Tomcat9以及Tomcat9之前的版本中还是能够识别javax.servlet这个包。



## 解决Tomcat服务器在DOS命令窗口中的乱码问题（控制台乱码）

将CATALINA_HOME/conf/logging.properties文件中的内容修改如下：

java.util.logging.ConsoleHandler.encoding = GBK

## 向浏览器响应一段HTML代码

```java
public void service(ServletRequest request, ServletResponse response){
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    out.print("<h1>hello servlet!</h1>");
}
```

## 在Servlet中连接数据库，怎么做？

- Servlet是Java程序，所以在Servlet中完全可以编写JDBC代码连接数据库。
- 在一个webapp中去连接数据库，需要将驱动jar包放到WEB-INF/lib目录下。（com.mysql.cj.jdbc.Driver 这个类就在驱动jar包当中。）

## 在集成开发环境当中开发Servlet程序

- 集成开发工具很多，其中目前使用比较多的是：

  - IntelliJ IDEA（这个居多，IDEA在提示功能方面要强于Eclipse，也就是说IDEA使用起来比Eclipse更加智能，更好用。JetBrain公司开发的。收费的。）
  - Eclipse（这个少一些），Eclipse目前还是有团队使用，只不过处于减少的趋势，自己从事工作之后，可能会遇到。Eclipse是IBM团队开发的。Eclipse寓意是“日食”。“日食”表示将太阳吃掉。太阳是SUN。IBM团队开发Eclipse的寓意是吞并SUN公司，但是2009年的时候SUN公司被Oracle公司并购了。IBM并没有成功并购SUN公司。

- 使用IDEA集成开发工具开发Servlet

  - 第一步：New Project（我比较习惯先创建一个Empty Project【空工程】，然后在空工程下新建Module【模块】，这不是必须的，只是一种习惯，你可以直接新建非空的Project），这个Empty Project起名为：javaweb（不是必须的，只是一个名字而已。一般情况下新建的Project的名字最好和目录的名字一致。）
  - 第二步：新建模块（File --> new --> Module...）
    - 这里新建的是一个普通的JavaSE模块（这里先不要新建Java Enterprise模块）
    - 这个Module自动会被放在javaweb的project下面。
    - 这个Module起名：servlet01
  - 第三步：让Module变成JavaEE的模块。（让Module变成webapp的模块。符合webapp规范。符合Servlet规范的Module）
    - 在Module上点击右键：Add Framework Support...（添加框架支持）
    - 在弹出的窗口中，选择Web Application（选择的是webapp的支持）
    - 选择了这个webapp的支持之后，IDEA会自动给你生成一个符合Servlet规范的webpp目录结构。
    - **重点，需要注意的：在IDEA工具中根据Web Application模板生成的目录中有一个web目录，这个目录就代表webapp的根**
  - 第四步（非必须）：根据Web Application生成的资源中有index.jsp文件，这里我选择删除这个index.jsp文件。
  - 第五步：编写Servlet（StudentServlet）
    - class StudentServlet implements Servlet
    - 这个时候发现Servlet.class文件没有。怎么办？将CATALINA_HOME/lib/servlet-api.jar和jsp-api.jar添加到classpath当中（这里的classpath说的是IDEA的classpath）
      - File --> Project Structrue --> Modules --> + 加号 --> Add JARS....
    - 实现jakarta.servlet.Servlet接口中的5个方法。
  - 第六步：在Servlet当中的service方法中编写业务代码（我们这里连接数据库了。）
  - 第七步：在WEB-INF目录下新建了一个子目录：lib（这个目录名可不能随意，必须是全部小写的lib），并且将连接数据库的驱动jar包放到lib目录下。
  - 第八步：在web.xml文件中完成StudentServlet类的注册。（请求路径和Servlet之间对应起来）

  ```java
  <?xml version="1.0" encoding="UTF-8"?>
  <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
           version="4.0">
  
      <servlet>
          <servlet-name>studentServlet</servlet-name>
          <servlet-class>com.bjpowernode.javaweb.servlet.StudentServlet</servlet-class>
      </servlet>
      <servlet-mapping>
          <servlet-name>studentServlet</servlet-name>
          <url-pattern>/servlet/student</url-pattern>
      </servlet-mapping>
      
  </web-app>
  ```

  

  - 第九步：给一个html页面，在HTML页面中编写一个超链接，用户点击这个超链接，发送请求，Tomcat执行后台的StudentServlet。

    - student.html

    - 这个文件不能放到WEB-INF目录里面，只能放到WEB-INF目录外面。

    - student.html文件的内容

    - ```html
      <!DOCTYPE html>
      <html lang="en">
      <head>
          <meta charset="UTF-8">
          <title>student page</title>
      </head>
      <body>
          <!--这里的项目名是 /xmm ，无法动态获取，先写死-->
          <a href="/xmm/servlet/student">student list</a>
      </body>
      </html>
      ```

      

  - 第十步：让IDEA工具去关联Tomcat服务器。关联的过程当中将webapp部署到Tomcat服务器当中。

    - IDEA工具右上角，绿色小锤子右边有一个：Add Configuration
    - 左上角加号，点击Tomcat Server --> local
    - 在弹出的界面中设置服务器Server的参数（基本上不用动）
    - 在当前窗口中有一个Deployment（点击这个用来部署webapp），继续点击加号，部署即可。
    - 修改 Application context为：/xmm

  - 第十一步：启动Tomcat服务器

    - 在右上角有绿色的箭头，或者绿色的小虫子，点击这个绿色的小虫子，可以采用debug的模式启动Tomcat服务器。
    - 我们开发中建议适用debug模式启动Tomcat

  - 第十二步：打开浏览器，在浏览器地址栏上输入：http://localhost:8080/xmm/student.html

## Servlet对象的生命周期

- 什么是Servlet对象生命周期？

  - Servlet对象什么时候被创建。
  - Servlet对象什么时候被销毁。
  - Servlet对象创建了几个？
  - Servlet对象的生命周期表示：一个Servlet对象从出生在最后的死亡，整个过程是怎样的。

- Servlet对象是由谁来维护的？

  - Servlet对象的创建，对象上方法的调用，对象最终的销毁，Javaweb程序员是无权干预的。
  - Servlet对象的生命周期是由Tomcat服务器（WEB Server）全权负责的。
  - Tomcat服务器通常我们又称为：WEB容器。（这个叫法你要知道【WEB Container】）
  - WEB容器来管理Servlet对象的死活。

- 思考：我们自己new的Servlet对象受WEB容器的管理吗？

  - 我们自己new的Servlet对象是不受WEB容器管理的。
  - WEB容器创建的Servlet对象，这些Servlet对象都会被放到一个集合当中（HashMap），只有放到这个HashMap集合中的Servlet才能够被WEB容器管理，自己new的Servlet对象不会被WEB容器管理。（自己new的Servlet对象不在容器当中）
  - web容器底层应该有一个HashMap这样的集合，在这个集合当中存储了Servlet对象和请求路径之间的关系
  - ![WEB容器中的Map集合](WEB容器中的Map集合.png)

- 研究：服务器在启动的Servlet对象有没有被创建出来（默认情况下）？

  - 在Servlet中提供一个无参数的构造方法，启动服务器的时候看看构造方法是否执行。
  - 经过测试得出结论：默认情况下，服务器在启动的时候Servlet对象并不会被实例化。
  - 这个设计是合理的。用户没有发送请求之前，如果提前创建出来所有的Servlet对象，必然是耗费内存的，并且创建出来的Servlet如果一直没有用户访问，显然这个Servlet对象是一个废物，没必要先创建。

- 怎么让服务器启动的时候创建Servlet对象呢？

  - 在servlet标签中添加<load-on-startup>子标签，在该子标签中填写整数，越小的整数优先级越高。

  - ```xml
    <servlet>
        <servlet-name>aservlet</servlet-name>
        <servlet-class>com.bjpowernode.javaweb.servlet.AServlet</servlet-class>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>aservlet</servlet-name>
        <url-pattern>/a</url-pattern>
    </servlet-mapping>
    ```

- Servlet对象生命周期

  - 默认情况下服务器启动的时候AServlet对象并没有被实例化

  - 用户发送第一次请求的时候，控制台输出了以下内容：

    ```
    AServlet无参数构造方法执行了
    AServlet's init method execute!
    AServlet's service method execute!
    ```

  - 根据以上输出内容得出结论：

    - 用户在发送第一次请求的时候Servlet对象被实例化（AServlet的构造方法被执行了。并且执行的是无参数构造方法。）
    - AServlet对象被创建出来之后，Tomcat服务器马上调用了AServlet对象的init方法。（init方法在执行的时候，AServlet对象已经存在了。已经被创建出来了。）
    - 用户发送第一次请求的时候，init方法执行之后，Tomcat服务器马上调用AServlet对象的service方法。

  - 用户继续发送第二次请求，控制台输出了以下内容：

    ```
    AServlet's service method execute!
    ```

  - 根据以上输出结果得知，用户在发送第二次，或者第三次，或者第四次请求的时候，Servlet对象并没有新建，还是使用之前创建好的Servlet对象，直接调用该Servlet对象的service方法，这说明：

    - 第一：Servlet对象是单例的（单实例的。但是要注意：Servlet对象是单实例的，但是Servlet类并不符合单例模式。我们称之为假单例。之所以单例是因为Servlet对象的创建我们javaweb程序员管不着，这个对象的创建只能是Tomcat来说了算，Tomcat只创建了一个，所以导致了单例，但是属于假单例。真单例模式，构造方法是私有化的。）
    - 第二：无参数构造方法、init方法只在第一次用户发送请求的时候执行。也就是说无参数构造方法只执行一次。init方法也只被Tomcat服务器调用一次。
    - 第三：只要用户发送一次请求：service方法必然会被Tomcat服务器调用一次。发送100次请求，service方法会被调用100次。

  - 关闭服务器的时候，控制台输出了以下内容：

    ```
    AServlet's destroy method execute!
    ```

  - 通过以上输出内容，可以得出以下结论：

    - Servlet的destroy方法只被Tomcat服务器调用一次。
    - destroy方法是在什么时候被调用的？
      - 在服务器关闭的时候。
      - 因为服务器关闭的时候要销毁AServlet对象的内存。
      - 服务器在销毁AServlet对象内存之前，Tomcat服务器会自动调用AServlet对象的destroy方法。

  - 请问：destroy方法调用的时候，对象销毁了还是没有销毁呢？

    - destroy方法执行的时候AServlet对象还在，没有被销毁。destroy方法执行结束之后，AServlet对象的内存才会被Tomcat释放。
    
  - Servlet对象更像一个人的一生：

    - Servlet的无参数构造方法执行：标志着你出生了。
    - Servlet对象的init方法的执行：标志着你正在接受教育。
    - Servlet对象的service方法的执行：标志着你已经开始工作了，已经开始为人类提供服务了。
    - Servlet对象的destroy方法的执行：标志着临终。有什么遗言，抓紧的。要不然，来不及了。

  - 关于Servlet类中方法的调用次数？

    - 构造方法只执行一次。
    - init方法只执行一次。
    - service方法：用户发送一次请求则执行一次，发送N次请求则执行N次。
    - destroy方法只执行一次。

  - 当我们Servlet类中编写一个有参数的构造方法，如果没有手动编写无参数构造方法会出现什么问题？

    - 报错了：500错误。
    - 注意：500是一个HTTP协议的错误状态码。
    - 500一般情况下是因为服务器端的Java程序出现了异常。（服务器端的错误都是500错误：服务器内部错误。）
    - 如果没有无参数的构造方法，会导致出现500错误，无法实例化Servlet对象。
    - 所以，一定要注意：在Servlet开发当中，不建议程序员来定义构造方法，因为定义不当，一不小心就会导致无法实例化Servlet对象。

  - 思考：Servlet的无参数构造方法是在对象第一次创建的时候执行，并且只执行一次。init方法也是在对象第一次创建的时候执行，并且只执行一次。那么这个无参数构造方法可以代替掉init方法吗？

    - 不能。
    - Servlet规范中有要求，作为javaweb程序员，编写Servlet类的时候，不建议手动编写构造方法，因为编写构造方法，很容易让无参数构造方法消失，这个操作可能会导致Servlet对象无法实例化。所以init方法是有存在的必要的。

  - init、service、destroy方法中使用最多的是哪个方法？

    - 使用最多就是service方法，service方法是一定要实现的，因为service方法是处理用户请求的核心方法。
    - 什么时候使用init方法呢？
      - init方法很少用。
      - 通常在init方法当中做初始化操作，并且这个初始化操作只需要执行一次。例如：初始化数据库连接池，初始化线程池....
    - 什么时候使用destroy方法呢？
      - destroy方法也很少用。
      - 通常在destroy方法当中，进行资源的关闭。马上对象要被销毁了，还有什么没有关闭的，抓紧时间关闭资源。还有什么资源没保存的，抓紧时间保存一下。


## GenericServlet

- 我们编写一个Servlet类直接实现Servlet接口有什么缺点？

  - 我们只需要service方法，其他方法大部分情况下是不需要使用的。代码很丑陋。

- 适配器设计模式Adapter

  - 手机直接插到220V的电压上，手机直接就报废了。怎么办？可以找一个充电器。这个充电器就是一个适配器。手机连接适配器。适配器连接220V的电压。这样问题就解决了。

- 编写一个GenericServlet类，这个类是一个抽象类，其中有一个抽象方法service。

  - GenericServlet实现Servlet接口。
  - GenericServlet是一个适配器。
  - 以后编写的所有Servlet类继承GenericServlet，重写service方法即可。

- 思考：GenericServlet类是否需要改造一下？怎么改造？更利于子类程序的编写？

  - 思考第一个问题：我提供了一个GenericServlet之后，init方法还会执行吗？

    - 还会执行。会执行GenericServlet类中的init方法。

  - 思考第二个问题：init方法是谁调用的？

    - Tomcat服务器调用的。

  - 思考第三个问题：init方法中的ServletConfig对象是谁创建的？是谁传过来的？

    - 都是Tomcat干的。
    - Tomcat服务器先创建了ServletConfig对象，然后调用init方法，将ServletConfig对象传给了init方法。

  - 思考一下Tomcat服务器伪代码：

    - ```java
      public class Tomcat {
          public static void main(String[] args){
              // .....
              // Tomcat服务器伪代码
              // 创建LoginServlet对象（通过反射机制，调用无参数构造方法来实例化LoginServlet对象）
              Class clazz = Class.forName("com.bjpowernode.javaweb.servlet.LoginServlet");
              Object obj = clazz.newInstance();
              
              // 向下转型
              Servlet servlet = (Servlet)obj;
              
              // 创建ServletConfig对象
              // Tomcat服务器负责将ServletConfig对象实例化出来。
              // 多态（Tomcat服务器完全实现了Servlet规范）
              ServletConfig servletConfig = new org.apache.catalina.core.StandardWrapperFacade();
              
              // 调用Servlet的init方法
              servlet.init(servletConfig);
              
              // 调用Servlet的service方法
              // ....
              
          }
      }
      ```



## ServletConfig

## ServletContext













