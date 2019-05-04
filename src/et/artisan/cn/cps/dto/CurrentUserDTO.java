package et.artisan.cn.cps.dto;

import et.artisan.cn.cps.entity.User;
import java.sql.Timestamp;

/**
 * @author Yoseph Berhanu<yoseph@artisan.et>
 * @since 1.0
 * @version 1.0
 */
public class CurrentUserDTO extends User {

    private String sessionLogId;
    private Timestamp loggedInAt;
    private Timestamp loggedOutAt;
    private String loggedInFrom;
    private String userAgent;

    public Timestamp getLoggedInAt() {
        return loggedInAt;
    }

    public void setLoggedInAt(Timestamp loggedInAt) {
        this.loggedInAt = loggedInAt;
    }

    public String getLoggedInFrom() {
        return loggedInFrom;
    }

    public void setLoggedInFrom(String loggedInFrom) {
        this.loggedInFrom = loggedInFrom;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public void setLoggedOutAt(Timestamp loggedOutAt) {
        this.loggedOutAt = loggedOutAt;
    }

    public String getSessionLogId() {
        return sessionLogId;
    }

    public void setSessionLogId(String sessionLogId) {
        this.sessionLogId = sessionLogId;
    }

    public Timestamp getLoggedOutAt() {
        return loggedOutAt;
    }
    
}
