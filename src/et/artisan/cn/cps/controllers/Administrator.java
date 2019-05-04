package et.artisan.cn.cps.controllers;

import et.artisan.cn.cps.util.*;
import et.artisan.cn.cps.dto.JQueryDataTableParamModel;
import et.artisan.cn.cps.dto.PaymentQueryParameter;
import et.artisan.cn.cps.dto.UsersListDTO;
import et.artisan.cn.cps.dto.UsersQueryParameter;
import et.artisan.cn.cps.entity.*;
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.Timestamp;
import java.time.Instant;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;

/**
 * Controller for the system administrator user
 *
 * @author Yoseph Berhanu<yoseph@artisan.et>
 * @since 1.0
 * @version 1.0
 */
public class Administrator extends HttpServlet {

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
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = Constants.ACTION_ALL_WELCOME;

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
        if (action.equalsIgnoreCase(Constants.ACTION_ALL_WELCOME)) {
            All.welcome(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_ADMINISTRATOR_DASHBOARD)) {
            dashboard(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_ADMINISTRATOR_USERS)) {
            users(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_ADMINISTRATOR_USERS_LIST)) {
        	users(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_ADMINISTRATOR_USERS_CARD)) {
        	users(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_ADMINISTRATOR_ADD_USER)) {
            addUser(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_ADMINISTRATOR_SAVE_USER)) {
            saveUser(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_ADMINISTRATOR_VIEW_USER)) {
            viewUser(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_ADMINISTRATOR_EDIT_USER)) {
            editUser(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_ADMINISTRATOR_UPDATE_USER)) {
            updateUser(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_ADMINISTRATOR_DELETE_USER)) {
            deleteUsers(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_ADMINISTRATOR_GET_USER_PHOTO)) {
            getUserPhoto(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_ADMINISTRATOR_GET_SIGNATURE)) {
            getUserSignature(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_ADMINISTRATOR_LOOKUP)) {
            lookup(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_ADMINISTRATOR_LOG)) {
            log(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_ADMINISTRATOR_SETTINGS)) {
            settings(request, response);
        } else {
            All.notFound(request, response);
        }
    }

    protected static void dashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_ADMINISTRATOR_DASHBOARD));
        RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
        rd.forward(request, response);
    }

    protected static void users(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		if (request.getParameter("json") == null) {
			usersListTemplate(request, response);
		} else {
			usersListData(request, response);
		}
    }

    protected static void usersListTemplate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //request.setAttribute("users", CommonStorage.getRepository().getAllUsers());
        request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_ADMINISTRATOR_USERS_LIST));
        RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
        rd.forward(request, response);
    }
    
    protected static void usersListData(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

		UsersListDTO users = new UsersListDTO();
		UsersQueryParameter param = (UsersQueryParameter)DataTablesParamUtility.getParam(request, new UsersQueryParameter());
		users.setDraw(request.getParameter("draw"));
		users.setEcho(param.sEcho);
		users.setJQueryDataTableParamModel(param);
		users.setTotalNumberOfRecords(CommonStorage.getRepository().getUsersCount(param));
		users.setData(CommonStorage.getRepository().getAllUsers(param));
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(users.toJSON());
    }

    
    protected static void viewUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long userId = Long.parseLong(request.getParameter("i"));
        request.setAttribute("user", CommonStorage.getRepository().getUser(userId));
        request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_ADMINISTRATOR_VIEW_USER));
        RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
        rd.forward(request, response);
    }

    protected static void editUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long userId = Long.parseLong(request.getParameter("i"));
        request.setAttribute("user", CommonStorage.getRepository().getUser(userId));
        request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_ADMINISTRATOR_EDIT_USER));
        RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
        rd.forward(request, response);
    }

    protected static void addUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_ADMINISTRATOR_ADD_USER));
        RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
        rd.forward(request, response);
    }

    protected static void saveUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = new User();
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
                } else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("password")) {
                    user.setPassword(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"));
                } else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("roles")) {
                    user.addRole(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"));
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
            user.setRegisteredBy(CommonStorage.getCurrentUser(request).getId());
            user.setRegisteredOn(new Timestamp(Instant.now().toEpochMilli()));
            user.setStatus(Constants.STATUS_ACTIVE);

            if (user.valideForSave()) {
                CommonStorage.getRepository().save(user);
                CommonTasks.writeMessage(request, "Sucessful", Message.MESSEGE_TYPE_SUCCESS, "User registration successful");
                response.sendRedirect(request.getContextPath() + "/Administrator?a=" + Constants.ACTION_ADMINISTRATOR_USERS);
            } else {
                CommonTasks.writeMessage(request, "Validation failed", Message.MESSEGE_TYPE_ERROR, user.getValidationMessage().toArray(new String[0]));
                CommonTasks.logMessage("Validation failed", Message.MESSEGE_TYPE_ERROR, user.getValidationMessage().toArray(new String[0]));
                request.setAttribute("user", user);
                addUser(request, response);
            }
        } catch (Exception ex) {
            CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex, "Something went wrong while tring to register new user to database from " + request.getRemoteHost());
            CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR, "Something went wrong, the administrator is notified of the problem.", "Please try things again and if the problem persist, try after a while");
            request.setAttribute("user", user);
            addUser(request, response);
        }
    }

    protected static void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long userId = Long.parseLong(request.getAttribute("i").toString());
        User user = CommonStorage.getRepository().getUser(userId);
        user.setRoles(new ArrayList<>());
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
                } else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("roles")) {
                    user.addRole(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"));
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
                CommonTasks.writeMessage(request, "Sucessful", Message.MESSEGE_TYPE_SUCCESS, "User account updated successfully");
                response.sendRedirect(request.getContextPath() + "/Administrator?a=" + Constants.ACTION_ADMINISTRATOR_VIEW_USER + "&i=" + userId);
            } else {
                CommonTasks.writeMessage(request, "Validation failed", Message.MESSEGE_TYPE_ERROR, user.getValidationMessage().toArray(new String[0]));
                CommonTasks.logMessage("Validation failed", Message.MESSEGE_TYPE_ERROR, user.getValidationMessage().toArray(new String[0]));
                request.setAttribute("user", user);
                viewUser(request, response);
            }
        } catch (Exception ex) {
            CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex, "Something went wrong while tring to register new user to database from " + request.getRemoteHost());
            CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR, "Something went wrong, the administrator is notified of the problem.", "Please try things again and if the problem persist, try after a while");
            request.setAttribute("user", user);
            addUser(request, response);
        }
    }

    protected static void deleteUsers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] ids = request.getParameterValues("i");
        long userId;
        String[] details = new String[0];
        if (ids == null || ids.length < 1) {
            CommonTasks.writeMessage(request, "No User Selected", Message.MESSEGE_TYPE_ERROR, "You have not seleceted any user for this action");
        } else {
            details = new String[ids.length];
            for (int i = 0; i < ids.length; i++) {
                try {
                    userId = Long.parseLong(ids[i]);
                } catch (Exception ex) {
                    CommonTasks.logMessage("Wrong parameter", Message.MESSEGE_TYPE_ERROR, ex, "Wrong paramter passed to delete user " + ids[i]);
                    continue;
                }
                String userName = CommonStorage.getRepository().deleteUser(userId);
                if (userName != null) {
                    details[i] = "User account " + userName + " successfully Removed";
                }
            }
        }
        CommonTasks.writeMessage(request, "Action Performed Successfully", Message.MESSEGE_TYPE_SUCCESS, details);
        response.sendRedirect(request.getContextPath() + "/Administrator?a=" + Constants.ACTION_ADMINISTRATOR_USERS);
    }

    protected static void getUserPhoto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        OutputStream out = response.getOutputStream();
        long userId = Long.parseLong(request.getParameter("i"));
        User user = CommonStorage.getRepository().getUser(userId);
        if (user.getPhoto() == null) {
            response.sendRedirect(request.getContextPath() + "/" + CommonStorage.getDefaultProfilePicture());
        } else {
            response.setContentType("image/*");
            response.setHeader("Content-Disposition", "attachment; filename=photo-" + user.getUsername() + "." + user.getPhotoFormat());
            out.write(user.getPhoto());
            out.flush();
            out.close();
        }
    }

    protected static void getUserSignature(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        OutputStream out = response.getOutputStream();
        long userId = Long.parseLong(request.getParameter("i"));
        User user = CommonStorage.getRepository().getUser(userId);
        if (user.getSignature() == null) {
            response.sendRedirect(request.getContextPath() + "/" + CommonStorage.getDefaultSignature());
        } else {
            response.setContentType("image/*");
            response.setHeader("Content-Disposition", "attachment; filename=signature-" + user.getUsername() + "." + user.getSignatureFormat());
            out.write(user.getSignature());
            out.flush();
            out.close();
        }
    }

    protected static void settings(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_ADMINISTRATOR_SETTINGS));
        RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
        rd.forward(request, response);
    }

    protected static void lookup(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("branchTypes", CommonStorage.getRepository().getAllBranchTypes());
        request.setAttribute("branchRegions", CommonStorage.getRepository().getAllBranchRegions());

        request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_ADMINISTRATOR_LOOKUP));
        RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
        rd.forward(request, response);
    }

    protected static void log(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_ADMINISTRATOR_LOG));
        RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
        rd.forward(request, response);
    }
    
    
}
