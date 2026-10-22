package pers.zxt.java.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 演示 JCL(Commons Logging，即 Jakarta Commons Logging) 的使用。
 *
 * 需要依赖：commons-logging:commons-logging（见 pom.xml）。
 *
 * 关键点：JCL 是"门面"，它会在运行时自动挑一个后端，挑选顺序为：
 *   1. classpath 上有 org.apache.log4j.Logger（log4j 1.x） → 用 Log4JLogger
 *   2. 否则有 JUL（JDK 1.4+ 必然有）               → 用 Jdk14Logger
 *   3. 再否则                                  → 用内置 SimpleLog（输出到 System.err）
 *
 * 因为本模块没有 log4j 1.x，所以本 Demo 的日志实际会走 **JUL**，
 * 与 logback.xml / log4j2.xml **完全无关**（JCL 不认识 Logback/Log4j2）。
 *
 * 如果想让 JCL 的日志走 SLF4J/Logback：
 *   把 commons-logging 换成 org.slf4j:jcl-over-slf4j（同名同包，是"替换"而不是"叠加"）。
 * 如果想让 JCL 的日志走 Log4j2：
 *   保留 commons-logging，另外引入 org.apache.logging.log4j:log4j-jcl。
 */
public class CommonsLogDemo {

    // 惯用法：使用当前类的 Class 对象获取 Log，logger 名称即类全限定名
    private static final Log log = LogFactory.getLog(CommonsLogDemo.class);
    // 也可以传入任意字符串作为名称（对应配置文件中按名称设置的级别）
    //private static final Log namedLog = LogFactory.getLog("my.custom.logger");

    public static void main(String[] args) {
        // JCL 的级别方法名与 Log4j 1.x 一致：trace/debug/info/warn/error/fatal
        // 注意：JCL 的 API 里没有 trace 之前的更细级别，fatal 是它特有的（SLF4J 没有 fatal）
        log.trace("Trace message ...");
        log.debug("Debug message ...");
        log.info("Info message ...");
        log.warn("Warn message ...");
        log.error("Error message ...");
        log.fatal("Fatal message ...");

        // 也支持把异常作为最后一个参数传入，会打印完整堆栈
        try {
            int i = 1 / 0;
        } catch (ArithmeticException e) {
            // ⚠ 重点：JCL 的 Log API **不支持 {} 参数化占位符**（这是 SLF4J 诞生的原因之一）。
            // JCL 只有 error(Object) 和 error(Object, Throwable) 两个重载，
            // 想带上下文只能用字符串拼接（因此即使不输出日志也会发生拼接开销）。
            log.error("计算出错, 表达式=1/0", e);
            // 下面这种写法在 JCL 中编译不过（没有对应的重载方法）：
            // log.error("计算出错, 表达式={}", e);
        }

        // 判断级别是否开启（JCL 提供了对应的 isXxxEnabled）
        if (log.isDebugEnabled()) {
            log.debug("Debug 级别已开启");
        }
    }
}
