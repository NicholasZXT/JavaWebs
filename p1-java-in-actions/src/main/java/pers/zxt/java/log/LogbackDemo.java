package pers.zxt.java.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用 SLF4J API + Logback 后端（用代码里的 LoggerFactory 获取 Logger）。
 *
 * 生效的配置文件是 logback.xml，其中：
 *   - name="pers.zxt.java.log.LogbackDemo" 的 logger 配了 file 和 roll（含滚动/压缩）两个 appender；
 *   - root 配了 console，且该 logger 的 additivity=true，所以控制台仍会输出 info 及以上。
 * 若 logger 名称与类全限定名不一致，则这段配置不会生效（会全部走 root 的 console）。
 *
 * 循环输出是为了演示 logback.xml 里 roll appender 的轮换（按时间 + 大小）效果，
 * 默认只跑 3 次，避免像原版那样 sleep 100 秒。
 */
public class LogbackDemo {
    public static void main(String[] args) throws InterruptedException {
        Logger logger = LoggerFactory.getLogger(LogbackDemo.class);
        logger.debug("Debug message ...");
        logger.info("Info message ...");
        logger.warn("Warn message ...");
        logger.error("Error message ...");
        int max = 3;
        for (int i = 0; i < max; i++) {
            // SLF4J 的参数化写法：{} 占位符，只有真的输出时才会做字符串拼接
            logger.debug("[{}] Debug message ...", i);
            logger.info("[{}] Info message ...", i);
            logger.warn("[{}] Warn message ...", i);
            logger.info("Sleeping 1 second ...");
            Thread.sleep(1000);
        }
        // 异常日志：Throwable 放最后一个参数，会打印完整堆栈
        try {
            int i = 1 / 0;
        } catch (ArithmeticException e) {
            logger.error("计算出错, 表达式={}", "1/0", e);
        }
    }
}
