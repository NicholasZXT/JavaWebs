package pers.zxt.java.log;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

/**
 * 配合 Lombok 的 @Slf4j 使用 SLF4J + Logback。
 *
 * @Slf4j 会在这个类里注入一个名为 log 的静态字段：
 *     private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Slf4jDemo.class);
 * 所以 logger 名称就是类的全限定名 pers.zxt.java.log.Slf4jDemo，
 * 与 logback.xml 里 <logger name="pers.zxt.java.log.Slf4jDemo"> 对应。
 *
 * 另外还演示了 MDC（Mapped Diagnostic Context），logback.xml 的 pattern 用 %X{traceId} 输出它。
 * Lombok 版本较旧时（如 1.16.22）在新 JDK 上可能出问题，建议升到 1.18.x。
 */
@Slf4j
public class Slf4jDemo {
    public static void main(String[] args) throws InterruptedException {
        log.debug("Debug message ...");
        log.info("Info message ...");
        log.warn("Warn message ...");
        log.error("Error message ...");

        // 演示 MDC：相当于给当前线程打上一个标签，日志格式里用 %X{traceId} 取出
        MDC.put("traceId", "trace-demo-001");
        try {
            int max = 3;
            for (int i = 0; i < max; i++) {
                log.debug("[{}] Debug message ...", i);
                log.info("[{}] Info message ...", i);
                log.warn("[{}] Warn message ...", i);
                log.info("Sleeping 1 second ...");
                Thread.sleep(1000);
            }
        } finally {
            // 必须清理，否则线程池复用线程时会把 traceId 带到下一个任务
            MDC.clear();
        }

        // 异常日志：Throwable 放最后一个参数，会打印完整堆栈
        try {
            int i = 1 / 0;
        } catch (ArithmeticException e) {
            log.error("计算出错, 表达式={}", "1/0", e);
        }
    }
}
