package pers.zxt.springmvc.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import javax.xml.ws.RequestWrapper;

/**
 *  @Controller:创建处理器对象，对象放在springmvc容器中。
 *  位置：在类的上面，和Spring中讲的@Service ,@Component类似
 *  能处理请求的都是控制器（处理器）： MyController能处理请求，叫做后端控制器（back controller）
 *  没有注解之前，需要实现各种不同的接口才能做控制器使用
 * @RequestMapping: 请求映射
 * 位置： 放在类的上面，此时 value 表示的是此类种请求地址的公共部分，叫做模块名称
 */
@Controller
@RequestMapping("/hello")
public class MyDispatchController {
    // 处理用户提交的请求，springmvc中是使用方法来处理的。
    // 方法是自定义的，可以有多种返回值，多种参数，方法名称自定义
    /**
     * 准备使用doSome方法处理some.do请求。
     * @RequestMapping: 请求映射，作用是把一个请求地址和一个方法绑定在一起，一个请求指定一个方法处理。
     *       属性： 1. value 是一个String，表示请求的uri地址的（some.do），value的值必须是唯一的， 不能重复。 在使用时，推荐地址以“/”.
     *       位置：1.在方法的上面，常用的。
     *            2.在类的上面
     *  说明： 使用RequestMapping修饰的方法叫做处理器方法或者控制器方法，使用@RequestMapping修饰的方法可以处理请求，类似Servlet中的doGet,doPost
     *
     *  返回值：ModelAndView 表示本次请求的处理结果
     *   Model: 数据，请求处理完成后，要显示给用户的数据
     *   View: 视图， 比如jsp等等。
     */
    // method 值用于指定请求方式，只能填写框架定义好的枚举类型，不指定时，请求方式没有限制
    @RequestMapping(value = {"/some.do","/first.do"}, method = RequestMethod.GET)
    public ModelAndView doSome(){
        // 此方法相当于 HttpServlet实现类中的 doGet() 方法 --> 调用业务相关的 service 类来处理请求
        // 这里省略了调用 service 处理some.do请求的过程，假设service调用处理完成了，只需要处理返回结果
        ModelAndView mv  = new ModelAndView();
        //添加数据， 框架在请求的最后把数据放入到request作用域。
        //request.setAttribute("msg","欢迎使用springmvc做web开发");
        mv.addObject("msg","欢迎使用springmvc做web开发");
        mv.addObject("fun","执行的是doSome方法");

        //指定视图, 指定视图的完整路径
        //框架对视图执行的forward操作， request.getRequestDispather("/show.jsp).forward(...)
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

    @RequestMapping(value = {"/other.do","/second.do"})
    public ModelAndView doOther(){
        ModelAndView mv  = new ModelAndView();
        mv.addObject("msg","====欢迎使用springmvc做web开发====");
        mv.addObject("fun","执行的是doOther方法");
        mv.setViewName("other");
        return mv;
    }
}