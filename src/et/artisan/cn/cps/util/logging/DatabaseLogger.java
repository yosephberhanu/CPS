package et.artisan.cn.cps.util.logging;

import et.artisan.cn.cps.entity.Message;

/**
 * @author Yoseph Berhanu <yoseph@artisan.et>
 * @version 1.0
 * @since 1.0
 */
public class DatabaseLogger implements Logger {

    @Override
    public void logError(Message message) {
        System.err.println("Method Not Implemented:" + DatabaseLogger.class.getCanonicalName() + " - logError");
    }

}
