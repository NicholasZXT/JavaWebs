package pers.zxt.java.log;

import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;


public class JulLogDemo {
    public static void main(String[] args) {
        JulLogDemo logDemo = new JulLogDemo();
        logDemo.utilLogger();
        logDemo.levelMapping();
        logDemo.handlerLevelAndFormatter();
        logDemo.setLevelByPackage();
    }

    /**
     * Java标准库内置的日志工具（java.util.logging，简称 JUL），实际中用的不多，仅供了解。
     *
     * 注意 JUL 有一道很坑的"双重级别过滤"：
     *   1. Logger 自身的级别（默认继承父级，根 logger 默认是 INFO）
     *   2. Handler 自身的级别（默认 ConsoleHandler 也是 INFO）
     * 两者都通过，日志才会输出。所以只调 logger.setLevel() 往往看不到 FINE(DEBUG) 级别的日志。
     */
    public void utilLogger(){
        Logger logger = Logger.getLogger("LogDemo");
        logger.setLevel(Level.INFO);
        logger.info("start processing ...");
        logger.warning("some warning ...");
        // FINE 相当于 SLF4J 的 DEBUG，默认不会输出（因为 Logger/Handler 级别是 INFO）
        logger.fine("some fine ...");
        // 注意，它的 error 对应的是 severe
        logger.severe("some severe ...");
    }

    /**
     * JUL 的级别名称和 SLF4J/Log4j 不一样，容易混淆：
     *   OFF > SEVERE > WARNING > INFO > CONFIG > FINE > FINER > FINEST > ALL
     * 映射关系（近似）：ERROR->SEVERE, WARN->WARNING, INFO->INFO, DEBUG->FINE, TRACE->FINER/FINEST
     */
    public void levelMapping(){
        Logger logger = Logger.getLogger("LogDemo.LevelMapping");
        // 列出所有内置级别及其数值
        Level[] levels = new Level[]{
                Level.OFF, Level.SEVERE, Level.WARNING, Level.INFO,
                Level.CONFIG, Level.FINE, Level.FINER, Level.FINEST, Level.ALL
        };
        for (Level level : levels) {
            System.out.println("JUL Level: " + level.getName() + " = " + level.intValue());
        }
        // 用 log(Level, msg) 这种通用写法可以避免"方法名与级别名对不上"的困惑
        logger.log(Level.INFO, "使用 log(Level, msg) 记录一条 INFO 日志");
        logger.log(Level.WARNING, "使用 log(Level, msg) 记录一条 WARNING 日志");
    }

    /**
     * 演示：要让 FINE 级别输出，必须同时放开 Logger 和 Handler 的级别，
     * 并自定义 Formatter 控制输出格式。
     */
    public void handlerLevelAndFormatter(){
        Logger logger = Logger.getLogger("LogDemo.Handler");
        // JUL 默认会继承父级 Handler，为避免重复输出，先清掉
        logger.setUseParentHandlers(false);

        ConsoleHandler handler = new ConsoleHandler();
        // 关键：Handler 的级别也要放开，否则 FINE 依旧出不来
        handler.setLevel(Level.FINE);
        handler.setFormatter(new SimpleFormatterWithMillis());
        logger.addHandler(handler);

        // 关键：Logger 的级别也要放开
        logger.setLevel(Level.FINE);

        logger.info("这条 INFO 会输出");
        logger.fine("这条 FINE(等价 DEBUG) 现在也能输出了");
        logger.finer("这条 FINER(等价 TRACE) 依然不会输出，因为 logger/handler 级别是 FINE");
    }

    /**
     * 演示：JUL 的 logger 名称是以 '.' 分隔的包名/类名，天然构成父子层次，
     * 可以像 Logback/Log4j 一样按包统一调级别。
     */
    public void setLevelByPackage(){
        // 设置以 pers.zxt 开头的所有 logger 的级别为 WARNING
        Logger.getLogger("pers.zxt").setLevel(Level.WARNING);

        Logger foo = Logger.getLogger("pers.zxt.java.log.SomeClass");
        // 这条 INFO 不会输出，因为 pers.zxt 这一级被调到了 WARNING
        foo.info("按包设置级别后，这条 INFO 不会输出（pers.zxt 级别为 WARNING）");
        foo.warning("这条 WARNING 会输出");
    }

    /**
     * 自定义 Formatter，给默认输出加上毫秒时间戳，方便和 Logback/Log4j 的输出效果对比
     */
    static class SimpleFormatterWithMillis extends Formatter {
        @Override
        public String format(LogRecord record) {
            return String.format("[%1$tF %1$tT.%1$tL][%2$s][%3$s] %4$s%n",
                    record.getMillis(),
                    record.getLevel().getName(),
                    record.getLoggerName(),
                    formatMessage(record));
        }
    }
}
