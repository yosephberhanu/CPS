package et.artisan.cn.cps.util;

import et.artisan.cn.cps.dto.CurrentUserDTO;
import et.artisan.cn.cps.util.CommonStorage;
import et.artisan.cn.cps.entity.Message;
import et.artisan.cn.cps.entity.Notification;
import et.artisan.cn.cps.entity.Payment;
import et.artisan.cn.cps.entity.Role;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Yoseph Berhanu <yoseph@artisan.et>
 * @version 1.0
 * @since 1.0
 */
public abstract class CommonTasks {

    private static final String MESSAGE_SESSION_KEY = "message";

    public static void writeMessage(HttpServletRequest request, String name, int type, Exception ex, boolean log, String... details) {
        Message message = new Message();
        message.setName(name);
        message.setType(type);
        message.setException(ex);
        for (String detail : details) {
            message.addDetail(detail);
        }
        if (log) {
            logMessage(message);
        }
        request.getSession().setAttribute(MESSAGE_SESSION_KEY, message);
    }

    public static void writeMessage(HttpServletRequest request, String name, int type, boolean log, String... details) {
        writeMessage(request, name, type, null, log, details);
    }

    public static void writeMessage(HttpServletRequest request, String name, int type, String... details) {
        writeMessage(request, name, type, false, details);
    }

    public static void logMessage(String name, int type, Exception ex, String... details) {
        Message message = new Message();
        message.setName(name);
        message.setException(ex);
        message.setType(type);
        for (String detail : details) {
            message.addDetail(detail);
        }
        logMessage(message);
    }

    public static void logMessage(String name, int type, String... details) {
        logMessage(name, type, null, details);
    }

    private static void logMessage(Message message) {
        CommonStorage.getLogger().logError(message);
    }

    public static Message readMessage(HttpServletRequest request) {
        Message returnValue = null;
        if (request.getSession().getAttribute(MESSAGE_SESSION_KEY) != null);
        try {
            returnValue = (Message) request.getSession().getAttribute(MESSAGE_SESSION_KEY);
        } catch (Exception ex) {
            logMessage("Session Attribute Cast Error", 0, ex, "Error while trying to cast the session attribute " + MESSAGE_SESSION_KEY);
        }
        return returnValue;
    }

    public static void setReturnURL(HttpServletRequest request) {
        StringBuffer returnUrl = request.getRequestURL();
        if (request.getQueryString() != null) {
            returnUrl.append("?").append(request.getQueryString());
        }
        request.getSession().setAttribute("returnUrl", returnUrl);
    }

    public static String getReturnURL(HttpServletRequest request) {
        String returnValue = request.getContextPath();
        if (request.getSession().getAttribute("returnUrl") != null) {
            returnValue = request.getSession().getAttribute("returnUrl").toString();
            request.getSession().removeAttribute("returnUrl");
        }
        return returnValue;
    }

    public static ArrayList<Notification> getNotifications(HttpServletRequest request) {
        ArrayList<Notification> returnValue = new ArrayList<>();
        CurrentUserDTO user = CommonStorage.getCurrentUser(request);
        returnValue = CommonStorage.getRepository().getNotifications(user.getId());
        return returnValue;
    }

    public static int getUnreadNotificationsCount(HttpServletRequest request) {
        int returnValue = 0;
        CurrentUserDTO user = CommonStorage.getCurrentUser(request);
        returnValue = CommonStorage.getRepository().getUnreadNotificationsCount(user.getId());
        return returnValue;
    }

    public static boolean actionPermitted(String action, CurrentUserDTO user) {
        boolean returnValue = true;
        if (user.hasRole(Constants.USER_ROLES_SUPERVISOR)) {
            //if (action.equalsIgnoreCase(Constants.ACTION_SUPERVISOR_EDIT_BRANCH)) {
//                returnValue = true;
//            }
        }
        return returnValue;
    }

    public static String moneyFormat(double money) {
        String returnValue;
        returnValue = NumberFormat.getCurrencyInstance(new Locale("am", "ET")).format(money);
        return returnValue;
    }

    public static java.sql.Date parseDate(String date) throws Exception {
        java.sql.Date returnValue;
        try {
            if (date.contains("-")) {
                returnValue = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime());
            } else {
                returnValue = new java.sql.Date(new SimpleDateFormat("MM/dd/yyyy").parse(date).getTime());
            }
        } catch (ParseException ex) {
            throw new Exception("Invalid Date");
        }
        return returnValue;
    }
    
    public static String escape(String input) {
    	return input.replace("!", "!!")
    				.replace("%", "!%")
    				.replace("_", "!_")
    				.replace("[", "![").toUpperCase();
    }
    
    public static String rolesToString(ArrayList<Role> roles) {
    	String returnValue = "";
    	for (int i = 0; i < roles.size(); i++) {
			returnValue +="<span class='label label-" + roles.get(i).getLabel() + "'>" + roles.get(i).getEnglishName() + "</span> &nbsp;"; 
		}
    	
    	return returnValue;
    }
    public static String toJson(ArrayList<Payment> payments) {
    	String returnValue = "[";
    	int i=0;
    	for (Payment payment : payments) {
    		returnValue += "{\"id\":\""+payment.getId()+"\","
					+ "\"text\":\""+payment.getDocumentNo() + " - (" + payment.getLotNo() + ") " + payment.getName() + " - " + CommonTasks.moneyFormat(payment.getAmount())+"\","
					+"\"remaining\":\""+payment.getRemainingAmount()	+ "\"}";
			if(i<payments.size()-1) {
				returnValue+=",";
			}
			i++;
		}
    	return returnValue+"]";
    }
}
