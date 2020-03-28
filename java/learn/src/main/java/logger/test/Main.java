package logger.test;

/**
 * @author Jikai Zhang
 * @date 2020-03-28
 */
public class Main {
    public static void main(String[] args) {
        LoggerHelper2 loggerHelper2 = new LoggerHelper2(Main.class);
        loggerHelper2.log("123");
    }
}
