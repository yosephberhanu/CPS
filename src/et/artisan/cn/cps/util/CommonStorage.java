package et.artisan.cn.cps.util;

import et.artisan.cn.cps.util.logging.ConsolLogger;
import et.artisan.cn.cps.util.logging.Logger;
import et.artisan.cn.cps.dao.MasterRepository;
import et.artisan.cn.cps.dto.CurrentUserDTO;
import et.artisan.cn.cps.entity.Message;
import et.artisan.cn.cps.entity.Notification;
import java.sql.*;
import java.text.*;
import java.time.*;

import javax.sql.*;
import javax.naming.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Yoseph Berhanu <yoseph@tiyake.et>
 * @version 1.0
 * @since 1.0
 */
public abstract class CommonStorage {

    // General Variables
    private static final HashMap<Integer, String> pages = new HashMap<>();

    private static Logger logger = new ConsolLogger();
    private static String jdbcResourceName = "jdbc/cps";
    private static String databaseType = "postgresql";
    private static MasterRepository repository;
    private static ReportUtil reportUtil;
    private static Connection connection;
    private static MessageDigest md5;
    public static final int MAX_LOGIN_ATTEMPT = 5;
    public static final String LOGGED_IN_INDEX_PAGE = "private/master_page.jsp";
    public static final String NOT_FOUND = "private/page_404.jsp";
    public static final String LOGIN_PAGE = "public/login.jsp";

    // File Upload Variables
    public static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;  // 3MB
    public static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
    public static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

    // App Specific Variables
    private static final String CURRENT_USER_KEY = "currentUser";

    static {
        try {
            md5 = MessageDigest.getInstance("MD5");

            pages.put(Constants.INDEX_ALL_NOT_FOUND, "include/notFoundPage");
            pages.put(Constants.INDEX_ALL_WELCOME, "include/welcomePage");
            pages.put(Constants.INDEX_ALL_VIEW_PROFILE, "content/all/viewProfile");
            pages.put(Constants.INDEX_ALL_EDIT_PROFILE, "content/all/editProfile");

            pages.put(Constants.INDEX_ALL_VIEW_NOTIFICATIONS, "content/all/notifications");

            pages.put(Constants.INDEX_ADMINISTRATOR_DASHBOARD, "content/administrator/dashboard");

            pages.put(Constants.INDEX_ADMINISTRATOR_USERS_LIST, "content/administrator/usersList");
            pages.put(Constants.INDEX_ADMINISTRATOR_USERS_CARD, "content/administrator/usersCard");
            pages.put(Constants.INDEX_ADMINISTRATOR_ADD_USER, "content/administrator/addUser");
            pages.put(Constants.INDEX_ADMINISTRATOR_VIEW_USER, "content/administrator/viewUser");
            pages.put(Constants.INDEX_ADMINISTRATOR_EDIT_USER, "content/administrator/editUser");

            pages.put(Constants.INDEX_ADMINISTRATOR_LOOKUP, "content/administrator/lookups");
            pages.put(Constants.INDEX_ADMINISTRATOR_LOG, "content/administrator/log");
            pages.put(Constants.INDEX_ADMINISTRATOR_SETTINGS, "content/administrator/settings");
            pages.put(Constants.INDEX_ADMINISTRATOR_SYSTEM, "content/administrator/system");

            pages.put(Constants.INDEX_DATAENTRY_BRANCHES_LIST, "content/dataentry/branchesList");
            pages.put(Constants.INDEX_DATAENTRY_BRANCHES_CARD, "content/dataentry/branchesCard");
            pages.put(Constants.INDEX_DATAENTRY_ADD_BRANCH, "content/dataentry/addBranch");
            pages.put(Constants.INDEX_DATAENTRY_VIEW_BRANCH, "content/dataentry/viewBranch");
            pages.put(Constants.INDEX_DATAENTRY_EDIT_BRANCH, "content/dataentry/editBranch");

            pages.put(Constants.INDEX_DATAENTRY_CLIENTS_LIST, "content/dataentry/clientsList");
            pages.put(Constants.INDEX_DATAENTRY_CLIENTS_CARD, "content/dataentry/clientsCard");
            pages.put(Constants.INDEX_DATAENTRY_ADD_CLIENT, "content/dataentry/addClient");
            pages.put(Constants.INDEX_DATAENTRY_VIEW_CLIENT, "content/dataentry/viewClient");
            pages.put(Constants.INDEX_DATAENTRY_EDIT_CLIENT, "content/dataentry/editClient");
            pages.put(Constants.INDEX_DATAENTRY_ADD_CLIENT_RATE, "content/dataentry/addServiceChargeRate");

            pages.put(Constants.INDEX_DATAENTRY_CLIENT_REGIONS_LIST, "content/dataentry/clientRegionsList");
            pages.put(Constants.INDEX_DATAENTRY_ADD_CLIENT_REGION, "content/dataentry/addClientRegion");
            pages.put(Constants.INDEX_DATAENTRY_VIEW_CLIENT_REGION, "content/dataentry/viewClientRegion");
            pages.put(Constants.INDEX_DATAENTRY_EDIT_CLIENT_REGION, "content/dataentry/editClientRegion");

            pages.put(Constants.INDEX_DATAENTRY_PROJECTS_LIST, "content/dataentry/projectList");
            pages.put(Constants.INDEX_DATAENTRY_ADD_PROJECT, "content/dataentry/addProject");
            pages.put(Constants.INDEX_DATAENTRY_VIEW_PROJECT, "content/dataentry/viewProject");
            pages.put(Constants.INDEX_DATAENTRY_EDIT_PROJECT, "content/dataentry/editProject");

            pages.put(Constants.INDEX_DATAENTRY_DOCUMENTS, "content/dataentry/documents");
            pages.put(Constants.INDEX_DATAENTRY_DOCUMENTS_LIST, "content/dataentry/documentsList");
            pages.put(Constants.INDEX_DATAENTRY_DOCUMENTS_CARD, "content/dataentry/documentsCard");
            pages.put(Constants.INDEX_DATAENTRY_ADD_DOCUMENT, "content/dataentry/addDocument");
            pages.put(Constants.INDEX_DATAENTRY_VIEW_DOCUMENT, "content/dataentry/viewDocument");
            pages.put(Constants.INDEX_DATAENTRY_EDIT_DOCUMENT, "content/dataentry/editDocument");

            pages.put(Constants.INDEX_DATAENTRY_PAYMENTS_LIST, "content/dataentry/paymentsList");
            pages.put(Constants.INDEX_DATAENTRY_PAYMENTS_CARD, "content/dataentry/paymentsCard");
            pages.put(Constants.INDEX_DATAENTRY_ADD_PAYMENT, "content/dataentry/addPayment");
            pages.put(Constants.INDEX_DATAENTRY_VIEW_PAYMENT, "content/dataentry/viewPayment");
            pages.put(Constants.INDEX_DATAENTRY_EDIT_PAYMENT, "content/dataentry/editPayment");

            pages.put(Constants.INDEX_DATAENTRY_CLAIMS, "content/dataentry/claims");
            pages.put(Constants.INDEX_DATAENTRY_CLAIMS_LIST, "content/dataentry/claimsList");
            pages.put(Constants.INDEX_DATAENTRY_CLAIMS_CARD, "content/dataentry/claimsCard");
            pages.put(Constants.INDEX_DATAENTRY_ADD_CLAIM, "content/dataentry/addClaim");
            pages.put(Constants.INDEX_DATAENTRY_VIEW_CLAIM, "content/dataentry/viewClaim");
            pages.put(Constants.INDEX_DATAENTRY_EDIT_CLAIM, "content/dataentry/editClaim");

            pages.put(Constants.INDEX_DATAENTRY_CLAIM_DETAILS, "content/dataentry/claimDetails");
            pages.put(Constants.INDEX_DATAENTRY_ADD_CLAIM_DETAIL, "content/dataentry/addClaimDetail");
            pages.put(Constants.INDEX_DATAENTRY_VIEW_CLAIM_DETAIL, "content/dataentry/viewClaimDetail");
            pages.put(Constants.INDEX_DATAENTRY_EDIT_CLAIM_DETAIL, "content/dataentry/editClaimDetail");

            pages.put(Constants.INDEX_DATAENTRY_AMENDMENTS, "content/dataentry/amendments");
            pages.put(Constants.INDEX_DATAENTRY_ADD_AMENDMENT, "content/dataentry/addAmendment");
            pages.put(Constants.INDEX_DATAENTRY_VIEW_AMENDMENT, "content/dataentry/viewAmendment");
            pages.put(Constants.INDEX_DATAENTRY_EDIT_AMENDMENT, "content/dataentry/editAmendment");

            pages.put(Constants.INDEX_DATAENTRY_ADD_IMPORT, "content/dataentry/import");

            pages.put(Constants.INDEX_MANAGER_DASHBOARD, "content/manager/dashboard");
            pages.put(Constants.INDEX_MANAGER_REPORT, "content/manager/report");

        } catch (NoSuchAlgorithmException ex) {
            CommonTasks.logMessage("Internal Error", Message.MESSEGE_TYPE_ERROR, ex, "Error while setting up page IOC");
        }
    }

    public synchronized static Connection getConnection() {

        try {
            if (connection == null || connection.isClosed()) {
                synchronized (Connection.class) {
                    if (connection == null || connection.isClosed()) {
                        try {
                        	Class.forName("org.postgresql.Driver");  
                        	connection=DriverManager.getConnection(
                        			"jdbc:postgresql://localhost:5432/cps","postgres","1q2w3e4r");
                        	//"jdbc:postgresql://localhost:5432/payment","postgres","cn@123456789");  

                        } catch (SQLException ex) {
                            CommonTasks.logMessage("Failed to get database connection", Message.MESSEGE_TYPE_ERROR, ex);
                        }
                    }
                }
            }
        } catch (Exception e) {
            CommonTasks.logMessage("Failed to get database connection", Message.MESSEGE_TYPE_ERROR, e);
        }
        return connection;
    }
    public synchronized static Connection getConnectionOld() {
        try {
            if (connection == null || connection.isClosed()) {
                synchronized (Connection.class) {
                    if (connection == null || connection.isClosed()) {
                        try {
                            DataSource ds = (DataSource) new InitialContext().lookup(getJDBCResourceName());
                            connection = ds.getConnection();
                        } catch (NamingException | SQLException ex) {
                            CommonTasks.logMessage("Failed to get database connection", Message.MESSEGE_TYPE_ERROR, ex);
                        }
                    }
                }
            }
        } catch (Exception e) {
            CommonTasks.logMessage("Failed to get database connection", Message.MESSEGE_TYPE_ERROR, e);
        }
        return connection;
    }

    public static String getJDBCResourceName() {
        return jdbcResourceName;
    }

    public static void setJDBCResourceName(String jdbcResourceName) {
        CommonStorage.jdbcResourceName = jdbcResourceName;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        CommonStorage.logger = logger;
    }

    public static ReportUtil getReportUtil() {
        if (reportUtil == null) {
            reportUtil = new ReportUtil();
        }
        return reportUtil;
    }

    public static MasterRepository getRepository() {
        return repository;
    }

    public static void setRepository(MasterRepository repository) {
        CommonStorage.repository = repository;
    }

    public static void readSettings() {
        ///TODO:
    }

    public static String getDatabaseType() {
        return databaseType;
    }

    public static String md5(String input) {
        String returnValue;
        md5.reset();
        md5.update(input.getBytes());
        byte[] array = md5.digest();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
        }
        returnValue = sb.toString();
        return returnValue.substring(0, 5);
    }

    public static String getCurrentDate() {
        return getCurrentDate("yyyy-MM-dd");
    }

    public static String getCurrentDate(String format) {
        return new SimpleDateFormat(format).format(java.util.Date.from(Instant.now()));
    }

    public static CurrentUserDTO getCurrentUser(HttpServletRequest request) {
        CurrentUserDTO returnValue = null;
        if (request.getSession().getAttribute(CURRENT_USER_KEY) != null) {
            returnValue = (CurrentUserDTO) request.getSession().getAttribute(CURRENT_USER_KEY);
        }
        return returnValue;
    }

    public static CurrentUserDTO getCurrentUser(HttpSession session) {
        CurrentUserDTO returnValue = null;
        if (session.getAttribute(CURRENT_USER_KEY) != null) {
            returnValue = (CurrentUserDTO) session.getAttribute(CURRENT_USER_KEY);
        }
        return returnValue;
    }

    public static void setCurrentUser(HttpServletRequest request, CurrentUserDTO currentUser) {
        request.getSession().setAttribute(CURRENT_USER_KEY, currentUser);
    }

    public static String getDefaultProfilePicture() {
        return "assets/images/defaultProfile.png";
    }

    public static String getDefaultSignature() {
        return "assets/images/defaultSignature.png";
    }

    public static String getPage(int id) {
        return pages.get(id) + ".jsp";
    }

    public static String getDatabaseVersion() {
        String returnValue = "";
        return returnValue;
    }

    public static void writeNotifications(HttpServletRequest request, ArrayList<Notification> notifications) {
        request.getSession().setAttribute("notification", notifications);
    }

    public static ArrayList<Notification> getNotifications(HttpServletRequest request) {
        ArrayList<Notification> returnValue = new ArrayList<Notification>();
        if (request.getSession().getAttribute("notification") != null) {
            returnValue = (ArrayList<Notification>) request.getSession().getAttribute("notification");
        }
        return returnValue;
    }

    public static int getUnreadNotificationsCount(HttpServletRequest request) {
        int returnValue = 0;
        if (request.getSession().getAttribute("notificationCount") != null) {
            returnValue = Integer.parseInt(request.getSession().getAttribute("notificationCount").toString());
        }
        return returnValue;
    }

    public static void setUnreadNotificationsCount(HttpServletRequest request, int count) {
        request.getSession().setAttribute("notificationCount", count);
    }

    public static int getCurrentYear() {
        int returnValue = 0;
        returnValue = Calendar.getInstance().get(Calendar.YEAR);
        return returnValue;
    }

    public static HashMap<String, String> getAllPaymentStatus() {
        return new HashMap<String, String>();
    }
}
