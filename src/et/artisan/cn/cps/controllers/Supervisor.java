package et.artisan.cn.cps.controllers;

import et.artisan.cn.cps.util.*;
import et.artisan.cn.cps.entity.*;
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;

/**
 * Controller for the supervisor user
 * @author Yoseph Berhanu<yoseph@artisan.et>
 * @since 1.0
 * @version 1.0
 */
public class Supervisor extends HttpServlet {

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
        }/* else if (action.equalsIgnoreCase(Constants.ACTION_SUPERVISOR_BRANCHES)) {
            branches(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SUPERVISOR_BRANCHES_LIST)) {
            branches(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SUPERVISOR_BRANCHES_CARD)) {
            branchesCard(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SUPERVISOR_ADD_BRANCH)) {
            addBranch(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SUPERVISOR_SAVE_BRANCH)) {
            saveBranch(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SUPERVISOR_VIEW_BRANCH)) {
            viewBranch(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SUPERVISOR_EDIT_BRANCH)) {
            editBranch(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SUPERVISOR_UPDATE_BRANCH)) {
            updateBranch(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SUPERVISOR_DELETE_BRANCH)) {
            deleteBranch(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SUPERVISOR_CLIENTS)) {
            clients(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SUPERVISOR_CLIENTS_CARD)) {
            clientsCard(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SUPERVISOR_ADD_CLIENT)) {
            addClient(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SUPERVISOR_SAVE_CLIENT)) {
            saveClient(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SUPERVISOR_VIEW_CLIENT)) {
            viewClient(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SUPERVISOR_EDIT_CLIENT)) {
            editClient(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SUPERVISOR_UPDATE_CLIENT)) {
            updateClient(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SUPERVISOR_DELETE_CLIENT)) {
            deleteClient(request, response);
        }*/ else {
            All.notFound(request, response);
        }
    }
}
