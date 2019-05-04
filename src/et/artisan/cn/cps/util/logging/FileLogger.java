package et.artisan.cn.cps.util.logging;

import et.artisan.cn.cps.entity.Message;

/**
 * @author Yoseph Berhanu <yoseph@artisan.et>
 * @version 1.0
 * @since 1.0
 */
public class FileLogger implements Logger {

    @Override
    public void logError(Message message) {
        System.err.println("Method Not Implemented:" + FileLogger.class.getCanonicalName() + " - logError");
    }

}
