package et.artisan.cn.cps.util.logging;

import et.artisan.cn.cps.entity.Message;

/**
 * @author Yoseph Berhanu <yoseph@artisan.et>
 * @version 1.0
 * @since 1.0
 */
public class ConsolLogger implements Logger {

    @Override
    public void logError(Message message) {
        System.err.println("------------------------ ERROR START -------------------------------------");
        System.err.println("Name: " + message.getName());
        System.err.println("Description: " + message.getTypeText());
        System.err.println("Details: ");
        for (int i = 0; i < message.getDetails().size(); i++) {
            System.err.println("\t\t\t:>" + message.getDetails().get(i));
        }
        if (message.getException() != null) {
            System.err.println("Stack Trace :");
            message.getException().printStackTrace(System.err);
        }
        System.err.println("-------------------------- ERROR END -------------------------------------");
    }

}
