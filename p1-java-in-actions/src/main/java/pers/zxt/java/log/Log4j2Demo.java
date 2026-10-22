package pers.zxt.java.log;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

/**
 * 演示直接使用 Log4j2 自带的 API（org.apache.logging.log4j.*）。
 *
 * 注意：Log4j2 的 API 和 SLF4J 是两套独立 API。当前 classpath 上同时存在
 * log4j-core 和 logback-classic，但 SLF4J 只绑定到 logback-classic，
 * 所以只有这里直接用 Log4j2 API 时才走 log4j2.xml 的配置。
 */
public class Log4j2Demo {
    public static void main(String[] args) {
        Log4j2Demo logDemo = new Log4j2Demo();
        logDemo.logging();
        logDemo.withThreadContext();
        logDemo.withThrowable();
    }

    // 使用 LogManager 工厂函数 获取一个静态的 logger
    // LogManager.getLogger() 返回的是 Root Logger，也就是 log4j2.xml 中 Loggers.Root 配置的那个
    // LogManager.getLogger(name) 会在配置文件的 Loggers 中查找名为 name 的 Logger 配置，找到了就使用对应的配置，没找到，就使用 Root Logger
    static Logger logger = LogManager.getLogger(Log4j2Demo.class.getSimpleName());
    // 还有一个重载的API，直接传入类对象即可，内部会自动获取类名的字符串格式
    //static Logger logger = LogManager.getLogger(Log4j2Demo.class);
    // 如果要在记录日志时使用 String.format，则需要使用下面的 getFormatterLogger 方法创建Logger
    //static Logger logger = LogManager.getFormatterLogger(Log4j2Demo.class.getSimpleName());

    /**
     * 最常见的Log4j日志工具使用
     */
    public void logging(){
        System.out.println("Log4j2Demo.class.getName(): " + Log4j2Demo.class.getName());
        System.out.println("Log4j2Demo.class.getSimpleName(): " + Log4j2Demo.class.getSimpleName());
        logger.log(Level.INFO, "Info Level message ...");
        // 需要注意的是，如果 Root Logger 和 这里自定义的 Logger（假设 Loggers 配置中有对应配置）均设置了 Console 的 Appender，
        // 并且子 Logger 的配置里 additivity = "true"（默认配置），那么下面的信息再控制台里可能会输出两次，具体要看 Root Logger 和 当前
        // Logger 里设置的 Log Level 如何.
        logger.debug("Debug message ...");
        logger.info("Info message ...");
        logger.warn("Warn message ...");
        logger.error("Error message ...");
        logger.fatal("Fatal message ...");

        // 字符串占位符
        // 默认支持使用 {} 作为占位符（这也是官方推荐的写法，避免提前拼接字符串）
        logger.info("Info message for {}.", Log4j2Demo.class.getSimpleName());
        // 使用 String.format 的方式需要使用 LogManager.getFormatterLogger() 方法 —— 不过此时就不能使用上面的 {} 方式了
        //logger.info("Info message for %s.", Log4j2Demo.class.getSimpleName());

        // 只在会输出日志时才做昂贵计算，避免无谓开销
        if (logger.isDebugEnabled()) {
            logger.debug("Debug message with costly payload: {}", expensivePayload());
        }
    }

    /**
     * 演示 ThreadContext（等价于 SLF4J 的 MDC），可用于把 traceId 打进日志。
     * log4j2.xml 的 pattern 里用 %X{traceId} 输出。
     *
     * 注意：ThreadContext 基于 ThreadLocal，异步/线程池场景下会丢失，
     * 且使用完必须清理（clearAll），否则线程复用时会把上下文带到下一个任务。
     */
    public void withThreadContext(){
        ThreadContext.put("traceId", "trace-" + System.currentTimeMillis());
        logger.info("带有 traceId 的日志（格式里 %X{traceId} 会显示它）");
        ThreadContext.remove("traceId");
        logger.info("已移除 traceId 的日志");
    }

    /**
     * 演示异常日志：Throwable 必须作为最后一个参数传入，这样才会打印完整堆栈。
     */
    public void withThrowable(){
        try {
            int i = 1 / 0;
        } catch (ArithmeticException e) {
            // 推荐：把异常对象放在最后，会输出完整堆栈
            logger.error("计算出错, 出错的表达式={}", "1/0", e);
            // 不推荐：只拼消息，会丢掉堆栈
            // logger.error("计算出错: " + e.getMessage());
        }
    }

    private String expensivePayload(){
        return "payload-" + System.nanoTime();
    }
}
