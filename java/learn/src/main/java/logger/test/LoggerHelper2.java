package logger.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LocationAwareLogger;

/**
 * @author Jikai Zhang
 * @date 2020-03-28
 */
public class LoggerHelper2 {
    
    private final LocationAwareLogger logger;
    private static final String FQCN = LoggerHelper2.class.getName();
    
    public LoggerHelper2(Class<?> clazz) {
        this.logger = (LocationAwareLogger) LoggerFactory.getLogger(clazz);
    }
    
    public void log(String msg) {
        // 这里可以加一些额外的逻辑
        logger.log(null, FQCN, LocationAwareLogger.INFO_INT, msg, null, null);
    }
}
