package logger.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jikai Zhang
 * @date 2020-03-28
 */
public class LoggerHelper {
    private static final Logger logger = LoggerFactory.getLogger(LoggerHelper.class);
    
    public static void log(String msg) {
        // 这里可以加一些额外的逻辑
        logger.info("msg: {}", msg);
    }
}
