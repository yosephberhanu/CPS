package et.artisan.cn.cps.util.filters;

import et.artisan.cn.cps.dto.CurrentUserDTO;
import et.artisan.cn.cps.entity.Message;
import et.artisan.cn.cps.util.CommonStorage;
import et.artisan.cn.cps.util.CommonTasks;
import et.artisan.cn.cps.util.Constants;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Yoseph Berhanu <yoseph@artisan.et>
 * @version 1.0
 * @since 1.0
 *
 */
public class AuthorizationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Do Nothing 
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // For each of the known context paths check if the user has the right role
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        CurrentUserDTO currentUser = CommonStorage.getCurrentUser(httpRequest);
        if (!currentUser.hasRole(httpRequest.getServletPath().toLowerCase().substring(1))) {
            CommonTasks.writeMessage(httpRequest, "Authorization Failure", Message.MESSEGE_TYPE_ERROR, "You do not have sufficient privilege to perform the requested action please");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/All?a=" + Constants.ACTION_ALL_FORBIDDEN);
        } else {
            setUpNotification(httpRequest, httpResponse);
            chain.doFilter(request, response);
        }
    }
    @Override
    public void destroy() {
        // Do Nothing 
    }
    /**
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    private static void setUpNotification(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("notification")==null){
            CommonStorage.writeNotifications(request,CommonTasks.getNotifications(request));
            CommonStorage.setUnreadNotificationsCount(request, CommonTasks.getUnreadNotificationsCount(request));
        } 
    }

}
