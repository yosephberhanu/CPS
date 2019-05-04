package et.artisan.cn.cps.controllers;

import et.artisan.cn.cps.util.Constants;
import et.artisan.cn.cps.util.CommonTasks;
import et.artisan.cn.cps.util.CommonStorage;
import et.artisan.cn.cps.dto.CurrentUserDTO;
import et.artisan.cn.cps.entity.Message;
import et.artisan.cn.cps.entity.User;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * A controller class to handle generic non-role specific requests
 *
 * @author Yoseph Berhanu<yoseph@artisan.et>
 * @since 1.0
 * @version 1.0
 */
public class All extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.It dispatches requests to handler methods based on the action
     * selection parameter 'a'
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = Constants.ACTION_ALL_WELCOME;        // Set the default action

        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                DiskFileItemFactory factory = new DiskFileItemFactory();
                factory.setSizeThreshold(CommonStorage.MEMORY_THRESHOLD);
                factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
                ServletFileUpload upload = new ServletFileUpload(factory);
                upload.setFileSizeMax(CommonStorage.MAX_FILE_SIZE);
                upload.setSizeMax(CommonStorage.MAX_REQUEST_SIZE);
                List<FileItem> list = upload.parseRequest(request);
                Iterator<FileItem> items = list.iterator();
                while (items.hasNext()) {
                    FileItem thisItem = (FileItem) items.next();
                    if (thisItem.isFormField()) {
                        if (thisItem.getFieldName().equalsIgnoreCase("a")) {
                            action = thisItem.getString();
                        }
                        if (thisItem.getFieldName().equalsIgnoreCase("i")) {
                            request.setAttribute("i", thisItem.getString());
                        }
                        // If multipart transfer the request paramters to attributes
                        request.setAttribute("items", list);
                    }
                }
            } catch (Exception e) {
                CommonTasks.writeMessage(request, "Something wrong internally", Message.MESSEGE_TYPE_ERROR, e, true, "Something went wrong while tring to parse the multi part requset");
            }
        } else if (request.getParameter("a") != null) {
            action = (String) request.getParameter("a");
        } else {
            action = Constants.ACTION_ALL_WELCOME;
        }

        // Choose the proper action
        if (action.equalsIgnoreCase(Constants.ACTION_ALL_LOGIN_FORM)) {
            loginForm(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_ALL_LOGIN)) {
            login(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_ALL_WELCOME)) {
            welcome(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_ALL_LOGOUT)) {
            logout(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_ALL_GET_SIGNATURE)) {
            getSignature(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_ALL_GET_PROFILE_PICTURE)) {
            getProfilePicture(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_ALL_VIEW_PROFILE)) {
            viewProfile(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_ALL_EDIT_PROFILE)) {
            editProfile(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_ALL_UPDATE_PROFILE)) {
            updateProfile(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_ALL_VIEW_NOTIFICATIONS)) {
            viewNotifications(request, response);
        } else {
            notFound(request, response);
        }
    }

    /**
     * Action handler for welcome action, will forward to login if the user is
     * not logged in
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected static void welcome(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (CommonStorage.getCurrentUser(request) == null) {
            loginForm(request, response);
        } else {
            request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_ALL_WELCOME));
            RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
            rd.forward(request, response);
        }
    }

    protected static void loginForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (CommonStorage.getCurrentUser(request) != null) {
            welcome(request, response);
        } else {
            RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGIN_PAGE);
            rd.forward(request, response);
        }
    }

    protected static void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ///TODO: Make login only via HTTPS
        ///TODO: Add a remember me option
        ///TODO: Slider Captcha 
        String username = request.getParameter("j_username");
        String password = request.getParameter("j_password");
        CurrentUserDTO currentUser = CommonStorage.getRepository().tryLogin(username, password, request.getRemoteHost());
        String redirectPage = "";
        if (currentUser == null) { // Login Failed 
            //1. Count attempts
            if (request.getSession().getAttribute("loginAttempt") == null) {
                request.getSession().setAttribute("loginAttempt", 1);
            } else {
                int count = (int) request.getSession().getAttribute("loginAttempt");
                if (count >= CommonStorage.MAX_LOGIN_ATTEMPT) {
                    ///TODO: if more than permitted lock the account/user machine
                } else {
                    request.getSession().setAttribute("loginAttempt", count + 1);
                    ///TODO: Send captach.
                }
            }
            //3. Log/Write Error
            CommonTasks.writeMessage(request, "Invalid Credentials", Message.MESSEGE_TYPE_ERROR, "You provided invalid username and/or passwords please try again");
            CommonTasks.logMessage("Wrong log in attempt", Message.MESSEGE_TYPE_ERROR, "Wrong login attempt with the username " + username + " from " + request.getRemoteHost());
            //4. Redirect to login page
            redirectPage = request.getContextPath() + "/All?a=" + Constants.ACTION_ALL_LOGIN_FORM;
        } else { //Login Successful 

            //1. Log login to DB
            ///TODO: Add check for setting on logging sessions 
            currentUser.setUserAgent(request.getHeader("User-Agent"));
            currentUser.setSessionLogId(request.getSession().getId());
            CommonStorage.getRepository().logSessionStart(currentUser);
            //2. Setup session
            CommonStorage.setCurrentUser(request, currentUser);
            CommonTasks.writeMessage(request, "Welcome", Message.MESSEGE_TYPE_SUCCESS, "Welcome " + currentUser.getFullName() + ", You are logged in successfully!");
            //3. Redirect to Welcome Page
            redirectPage = CommonTasks.getReturnURL(request);
        }
        response.sendRedirect(redirectPage);
    }

    protected static void logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ///TODO: Add check for setting on logging sessions 
        CurrentUserDTO currentUser = CommonStorage.getCurrentUser(request);
        if (currentUser != null) {
            // Write logout to DB
            currentUser.setLoggedOutAt(Timestamp.from(Instant.now()));
            CommonStorage.getRepository().logSessionEnd(currentUser);

            // Remove current user from session
            CommonStorage.setCurrentUser(request, null);

            // Kill the session
            request.getSession().invalidate();
        }
        // Redirect to Login Page
        CommonTasks.writeMessage(request, "Good bye", Message.MESSEGE_TYPE_NOTICE, "You are logged out successfully! See you soon.");
        response.sendRedirect(request.getContextPath() + "/All?a=" + Constants.ACTION_ALL_LOGIN_FORM);
    }

    public static void logout(HttpServletRequest request)
            throws ServletException, IOException {

        ///TODO: Add check for setting on logging sessions 
        CurrentUserDTO currentUser = CommonStorage.getCurrentUser(request);
        if (currentUser != null) {
            // Write logout to DB
            currentUser.setLoggedOutAt(Timestamp.from(Instant.now()));
            CommonStorage.getRepository().logSessionEnd(currentUser);

            // Remove current user from session
            CommonStorage.setCurrentUser(request, null);

            // Kill the session
            request.getSession().invalidate();
        }
    }

    protected static void getProfilePicture(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        OutputStream out = response.getOutputStream();
        User user = CommonStorage.getCurrentUser(request);
        if (request.getParameter("i") != null && !request.getParameter("i").trim().isEmpty()) {
            long id = Long.parseLong(request.getParameter("i"));
            user = CommonStorage.getRepository().getUser(id);
        }
        if (user.getPhoto() == null) {
            response.sendRedirect(request.getContextPath() + "/" + CommonStorage.getDefaultProfilePicture());
        } else {
            response.setContentType("image/*");
            response.setHeader("Content-Disposition", "attachment; filename=photo" + user.getId() + "." + user.getPhotoFormat());
            out.write(user.getPhoto());
            out.flush();
            out.close();
        }
    }

    protected static void getSignature(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        OutputStream out = response.getOutputStream();
        CurrentUserDTO currentUser = CommonStorage.getCurrentUser(request);
        if (currentUser.getPhoto() == null) {
            response.sendRedirect(request.getContextPath() + "/" + CommonStorage.getDefaultSignature());
        } else {
            response.setContentType("image/*");
            response.setHeader("Content-Disposition", "attachment; filename=signature" + currentUser.getId() + "." + currentUser.getSignatureFormat());
            out.write(currentUser.getSignature());
            out.flush();
            out.close();
        }
    }

    protected static void viewProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (CommonStorage.getCurrentUser(request) == null) {
            loginForm(request, response);
        } else {
            request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_ALL_VIEW_PROFILE));
            RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
            rd.forward(request, response);
        }
    }

    protected static void editProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (CommonStorage.getCurrentUser(request) == null) {
            loginForm(request, response);
        } else {
            request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_ALL_EDIT_PROFILE));
            RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
            rd.forward(request, response);
        }
    }

    protected static void updateProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = CommonStorage.getCurrentUser(request);
        boolean changePassword = false;
        try {
            List<FileItem> list = (List<FileItem>) request.getAttribute("items");
            for (int i = 0; i < list.size(); i++) {
                FileItem thisItem = list.get(i);
                if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("firstName")) {
                    user.setFirstName(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"));
                } else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("fathersName")) {
                    user.setFathersName(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"));
                } else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("grandfathersName")) {
                    user.setGrandfathersName(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"));
                } else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("sex")) {
                    user.setSex(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"));
                } else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("primaryPhoneNo")) {
                    user.setPrimaryPhoneNo(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"));
                } else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("secondaryPhoneNo")) {
                    user.setSecondaryPhoneNo(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"));
                } else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("email")) {
                    user.setEmail(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"));
                } else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("website")) {
                    user.setWebsite(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"));
                } else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("addressLine")) {
                    user.setAddressLine(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"));
                } else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("city")) {
                    user.setCity(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"));
                } else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("state")) {
                    user.setState(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"));
                } else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("country")) {
                    user.setCountry(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"));
                } else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("username")) {
                    user.setUsername(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"));
                } else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("changePassword")) {
                    if ((new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8")).equalsIgnoreCase("true")) {
                        changePassword = true;
                    }
                } else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("password")) {
                    user.setPassword(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"));
                } else if (thisItem.getFieldName().equalsIgnoreCase("signature") && thisItem.getName().contains(".")) {
                    String fileExtension = thisItem.getName().substring(thisItem.getName().lastIndexOf('.'));
                    user.setSignature(thisItem.get());
                    user.setSignatureFormat(fileExtension);
                } else if (thisItem.getFieldName().equalsIgnoreCase("photo") && thisItem.getName().contains(".")) {
                    String fileExtension = thisItem.getName().substring(thisItem.getName().lastIndexOf('.'));
                    user.setPhoto(thisItem.get());
                    user.setPhotoFormat(fileExtension);
                }
            }
            if (user.valideForUpdate()) {
                CommonStorage.getRepository().update(user, changePassword);
                CommonTasks.writeMessage(request, "Sucessful", Message.MESSEGE_TYPE_SUCCESS, "Your user profile is updated successfully");
                response.sendRedirect(request.getContextPath() + "/All?a=" + Constants.ACTION_ALL_VIEW_PROFILE);
            } else {
                CommonTasks.writeMessage(request, "Validation failed", Message.MESSEGE_TYPE_ERROR, user.getValidationMessage().toArray(new String[0]));
                CommonTasks.logMessage("Validation failed", Message.MESSEGE_TYPE_ERROR, user.getValidationMessage().toArray(new String[0]));
                request.setAttribute("user", user);
                editProfile(request, response);
            }
        } catch (Exception ex) {
            CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex, "Something went wrong while tring to register new user to database from " + request.getRemoteHost());
            CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR, "Something went wrong, the administrator is notified of the problem.", "Please try things again and if the problem persist, try after a while");
            request.setAttribute("user", user);
            editProfile(request, response);
        }
    }

    protected static void viewNotifications(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_ALL_VIEW_NOTIFICATIONS));
        RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
        rd.forward(request, response);
    }

    /**
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void notFound(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.NOT_FOUND);
        rd.forward(request, response);
    }

}
