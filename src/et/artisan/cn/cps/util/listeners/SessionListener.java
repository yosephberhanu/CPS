package et.artisan.cn.cps.util.listeners;

import et.artisan.cn.cps.dto.CurrentUserDTO;
import et.artisan.cn.cps.entity.Message;
import et.artisan.cn.cps.util.CommonStorage;
import et.artisan.cn.cps.util.CommonTasks;
import et.artisan.cn.cps.util.Constants;
import java.sql.Timestamp;
import java.time.Instant;
import javax.servlet.Filter;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author Yoseph Berhanu <yoseph@artisan.et>
 * @version 1.0
 * @since 1.0
 */
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // TODO
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        /// Log session end if the user is logged in
        CurrentUserDTO currentUser = CommonStorage.getCurrentUser(se.getSession());
        if (currentUser != null) {
            currentUser.setLoggedOutAt(Timestamp.from(Instant.now()));
            CommonStorage.getRepository().logSessionEnd(currentUser);
        }
    }

}
