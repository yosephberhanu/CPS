package et.artisan.cn.cps.util.filters;

import et.artisan.cn.cps.entity.Message;
import et.artisan.cn.cps.util.CommonStorage;
import et.artisan.cn.cps.util.CommonTasks;
import et.artisan.cn.cps.util.Constants;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Yoseph Berhanu <yoseph@artisan.et>
 * @version 1.0
 * @since 1.0
 *
 * Will filter requests to all controllers except controllers.All
 */
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Do Nothing 
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        if (CommonStorage.getCurrentUser(httpRequest) == null) {
            CommonTasks.writeMessage(httpRequest, "Login Required", Message.MESSEGE_TYPE_ERROR, "Please login before performing the requested action");
            CommonTasks.setReturnURL(httpRequest);
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/All?a=" + Constants.ACTION_ALL_LOGIN_FORM);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        // Do Nothing 
    }

}
