package et.artisan.cn.cps.dao;

import et.artisan.cn.cps.dto.CurrentUserDTO;

/**
 *
 * @author Yoseph Berhanu <yoseph@artisan.et>
 * @version 1.0
 * @since 1.0
 *
 */
public class OracleMasterRepository /*implements MasterRepository*/ {

    public OracleMasterRepository() {
    }

    /**
     *
     * @param userName - username provided by the client
     * @param password - password provided by the client
     * @param host - address of the host the client is trying to login from
     * @return if login is successful a CurrentUserDTO object representing the
     * current user else null is returned
     */
    //@Override
    public CurrentUserDTO tryLogin(String userName, String password, String host) {
        CurrentUserDTO returnValue = null;
        System.err.println("Method Not Implemented:" + OracleMasterRepository.class.getCanonicalName() + " - tryLogin()");
        return returnValue;
    }

    /**
     * Logs start of sessions at login time
     *
     * @since version 1.0
     * @param currentUser a CurrentUserDTO object representing the currently
     * logged in user
     */
    //@Override
    public void logSessionStart(CurrentUserDTO currentUser) {
        System.err.println("Method Not Implemented:" + OracleMasterRepository.class.getCanonicalName() + " - logSessionStart()");
    }

}
