package pers.zxt.springmvc.hello;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *  @Controller: 创建次级处理器对象，该对象会被放入 springmvc容器 中。
 *    位置：在类的上面，和 Spring中的 @Service, @Component 注解类似
 *    能处理请求的都是控制器（处理器）： MyController能处理请求，叫做后端控制器（back controller）
 *    没有注解之前，需要实现各种不同的接口才能做控制器使用
 *  @RequestMapping: URL路由
 *    位置：放在类的上面，此时 value 表示的是此类内部请求地址的公共部分，叫做模块名称
 */
@Controller
@RequestMapping("/hello")
public class HelloController {
    // 处理用户提交的请求，springmvc中是使用方法来处理的。
    // 方法是自定义的，可以有多种返回值，多种参数，方法名称自定义
    /**
     * 准备使用doSome方法处理some.do请求。
     * @RequestMapping: 请求映射，作用是把一个请求地址和一个方法绑定在一起，一个请求指定一个方法处理。
     *  位置：1.在方法的上面，比较常用；2.在类的上面
     *  属性：
     *    1. value：是一个String，表示请求的uri地址的（some.do），value的值必须是唯一的，不能重复。在使用时，推荐地址以“/”开始.
     *    2. method：表示请求的方式，它的值为RequestMethod类枚举值。
     *  说明：使用RequestMapping修饰的方法叫做处理器方法或者控制器方法，使用@RequestMapping修饰的方法可以处理请求，类似Servlet中的doGet,doPost
     *  返回值：ModelAndView 表示本次请求的处理结果
     *   Model: 数据，请求处理完成后，要显示给用户的数据
     *   View: 视图， 比如jsp等等。
     */
    // method 值用于指定请求方式，只能填写框架定义好的枚举类型，不指定时，请求方式没有限制
    @RequestMapping(value={"/some.do","/first.do"}, method=RequestMethod.GET)
    public ModelAndView doSome() {
        // 此方法相当于 HttpServlet实现类中的 doGet() 方法 --> 调用业务相关的 service 类来处理请求
        // 这里省略了调用 service 处理some.do请求的过程，假设service调用处理完成了，只需要处理返回结果
        ModelAndView mv  = new ModelAndView();
        //添加数据， 框架在请求的最后把数据放入到request作用域。
        //request.setAttribute("msg","欢迎使用springmvc做web开发");
        mv.addObject("msg","欢迎使用springmvc做web开发");
        mv.addObject("fun","执行的是doSome方法");

        // 指定视图, 指定视图的完整路径
        // 框架对视图执行的forward操作，request.getRequestDispather("/show.jsp).forward(...)
        //mv.setViewName("/show.jsp");
        // 上面的 show.jsp 是放在 webapp 目录下的，和WEB-INF平级，这样用户可以直接通过输入URL的方式访问，绕过了 dispatcherController
        // 如果不希望这样，就应当把 show.jsp 放到 WEB-INF 目录里面，如下所示
        //mv.setViewName("/WEB-INF/view/show.jsp");
        //mv.setViewName("/WEB-INF/view/other.jsp");
        // 放到 WEB-INF 目录里面之后，每次使用视图，都需要写一串公共前缀，可以通过配置视图解析器来抽离出这部分公共前缀
        // 视图解析器在 DispatcherServlet 使用的 springmvc.xml 中进行配置
        // 当配置了视图解析器后，可以使用逻辑名称（文件名），指定视图，框架会使用视图解析器的前缀 + 逻辑名称 + 后缀 组成完成路径， 这里就是字符连接操作
        // /WEB-INF/view/ + show + .jsp
        mv.setViewName("show");

        // 重定向操作
        //mv.setView( new RedirectView("/a.jsp"));

        //返回mv
        return mv;
    }

    @RequestMapping(value={"/other.do","/second.do"}, method=RequestMethod.GET)
    public ModelAndView doOther(){
        ModelAndView mv  = new ModelAndView();
        mv.addObject("msg","====欢迎使用springmvc做web开发====");
        mv.addObject("fun","执行的是doOther方法");
        mv.setViewName("other");
        return mv;
    }

    /**
     * Controller方法的参数签名，非常灵活，框架提供的3个参数有：
     * 1. HttpServletRequest
     * 2. HttpServletResponse
     * 3. HttpSession
     * 此外，还可以提供其他自定义的参数
     */
    @RequestMapping(value={"/show_req.do"}, method=RequestMethod.GET)
    public ModelAndView showRequest(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        System.out.println(request.getRequestURL());
        System.out.println(request.getMethod());
        System.out.println(response.getContentType());
        ModelAndView mv  = new ModelAndView();
        mv.addObject("request_getRequestURL",request.getRequestURL());
        mv.addObject("request_getMethod",request.getMethod());
        mv.addObject("response_getContentType",response.getContentType());
        mv.setViewName("show_req");
        return mv;
    }

    /**
     * Controller方法的返回值只能是如下几种格式：
     * 1. ModelAndView：如上所示
     * 2. String：此时的字符串表示的是视图，既可以是视图名称，也可以是完整的视图路径，比如 show.jsp 之类的
     * 3. void：为空，此时方法参数需要使用 HttpServletResponse，调用其中的getWriter()直接在Response中写入数据，多用于Ajax响应
     * 4. 自定义的Object，REST-full风格的JSON就属于此类 —— KEY
     */

    /**
     * Controller方法返回void，响应Ajax请求。
     * 手工实现Ajax返回json数据：
     *  1. java对象转为json；
     *  2. 通过HttpServletResponse输出json数据
     */
    @RequestMapping(value = "/returnVoidAjax.do")
    public void doReturnVoidAjax(HttpServletResponse response) throws IOException {
        //处理ajax， 使用json作为返回数据，这里假设service调用完成，使用Student表示处理结果
        Student student  = new Student();
        student.setName("张飞同学");
        student.setAge(28);
        String json = "";
        //把结果的对象转为json格式的数据
        ObjectMapper om  = new ObjectMapper();
        json = om.writeValueAsString(student);
        System.out.println("student转换的json===="+json);
        //输出数据，响应Ajax的请求
        response.setContentType("application/json;charset=utf-8");
        PrintWriter pw  = response.getWriter();
        pw.println(json);
        pw.flush();
        pw.close();
    }

    /**
     * 处理器方法返回一个 Student，通过框架转为json，响应Ajax请求
     * @ResponseBody:
     *  作用：把处理器方法返回对象转为json后，通过HttpServletResponse输出给浏览器。
     *  位置：方法的定义上面，和其它注解没有顺序的关系。
     *
     * 返回对象框架的处理流程：
     *  1. 框架会把返回的 Student类型，调用框架的中 ArrayList<HttpMessageConverter> 中每个类的 canWrite() 方法
     *     检查哪个 HttpMessageConverter接口 的实现类能处理Student类型的数据——这里是 MappingJackson2HttpMessageConverter
     *  2.框架会调用实现类的 write()方法 —— 这里是 MappingJackson2HttpMessageConverter 的 write() 方法，
     *    把 Student对象 转为json，调用Jackson的ObjectMapper实现转为json
     *    contentType: application/json;charset=utf-8
     *  3.框架会调用 @ResponseBody 把 2 中的结果数据输出到浏览器，Ajax请求处理完成
     */
    @ResponseBody
    @RequestMapping(value={"/studentJson.do"}, method=RequestMethod.GET)
    public Student doStudentJsonObject(){
        Student student = new Student();
        student.setName("李四同学");
        student.setAge(20);
        return student; // 会被框架转为json
    }

    @ResponseBody
    @RequestMapping(value = "/studentsJsonArray.do")
    public List<Student> doStudentJsonObjectArray() {
        List<Student> list = new ArrayList<>();
        Student student = new Student();
        student.setName("李四同学");
        student.setAge(20);
        list.add(student);
        student = new Student();
        student.setName("张三");
        student.setAge(28);
        list.add(student);
        return list;
    }

    /**
     * 处理器方法返回的是String，但是表示的是数据，不是视图。
     * 区分返回值String是数据，还是视图，看有没有 @ResponseBody注解  --------- KEY
     * 如果有@ResponseBody注解，返回String就是数据，反之就是视图
     *
     * 默认使用“text/plain;charset=ISO-8859-1”作为contentType,导致中文有乱码，
     * 解决方案：给RequestMapping增加一个属性 produces, 使用这个属性指定新的contentType.
     *
     * 返回对象框架的处理流程：
     *  1. 框架会把返回String类型，调用框架的中 ArrayList<HttpMessageConverter> 中每个类的 canWrite() 方法
     *     检查哪个HttpMessageConverter接口的实现类能处理String类型的数据——此时应该是 StringHttpMessageConverter 实现类
     *  2. 框架会调用实现类的 write() —— 这里是 StringHttpMessageConverter.write() 方法
     *     把字符按照指定的编码处理 text/plain;charset=ISO-8859-1
     *  3. 框架会调用 @ResponseBody 把 2中 的结果数据输出到浏览器，Ajax请求处理完成
     */
    @ResponseBody
    @RequestMapping(value = "/stringData.do", produces = "text/plain;charset=utf-8")
    public String doStringData(){
        return "Hello SpringMVC 返回对象，表示数据";
    }
}