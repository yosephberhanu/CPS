package et.artisan.cn.cps.controllers;

import et.artisan.cn.cps.util.*;
import et.artisan.cn.cps.entity.*;
import java.io.*;
import java.text.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;

/**
 * Controller for the data entry user
 *
 * @author Yoseph Berhanu<yoseph@artisan.et>
 * @since 1.0
 * @version 1.0
 */
public class Manager extends HttpServlet {

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
        } else if (action.equalsIgnoreCase(Constants.ACTION_MANAGER_DASHBOARD)) {
            dashboard(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_MANAGER_REPORT)) {
            report(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_MANAGER_GENERATE_REPORT)) {
            downloadReport(request, response);
        } else {
            All.notFound(request, response);
        }
    }

    protected static void dashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_MANAGER_DASHBOARD));
        RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
        rd.forward(request, response);
    }

    protected static void report(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("r") != null && !request.getParameter("r").contains("undefined")) {
            request.setAttribute("reportType", CommonStorage.getRepository().getReportType(Byte.parseByte(request.getParameter("r"))));
        }
        if (request.getParameter("p") != null && !request.getParameter("p").contains("undefined")) {
            request.setAttribute("projectId", Long.parseLong(request.getParameter("p")));
        }
        if (request.getParameter("c") != null && !request.getParameter("c").contains("undefined")) {
            request.setAttribute("clientId", Long.parseLong(request.getParameter("c")));
        }
        if (request.getParameter("b") != null && !request.getParameter("b").contains("undefined")) {
            request.setAttribute("branchId", Long.parseLong(request.getParameter("b")));
        }
        if (request.getParameter("bt") != null && !request.getParameter("bt").contains("undefined")) {
            request.setAttribute("branchType", Integer.parseInt(request.getParameter("bt")));
        }
        if (request.getParameter("cl") != null && !request.getParameter("cl").contains("undefined")) {
            request.setAttribute("claimId", Long.parseLong(request.getParameter("cl")));
        }
        if (request.getParameter("claimCNNumber") != null && !request.getParameter("claimCNNumber").contains("undefined")) {
            request.setAttribute("claimCNNumber", request.getParameter("claimCNNumber"));
        }
        request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_MANAGER_REPORT));
        RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
        rd.forward(request, response);
    }

    protected static void downloadReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int reportId = Integer.parseInt(request.getParameter("reportType"));
            Branch branch = null;
            Project project = null;
            Document document = null;
            BranchType branchType = null;
            ClientRegion clientRegion = null;
            Client client = null;
            Claim claim = null;
            java.sql.Date from = null, to = null;
            ArrayList<String> message = new ArrayList<>();
            String fileName = null;
            User approver = null;
            String claimCNNumber = null;
            if (request.getParameter("approver") != null && !request.getParameter("approver").isEmpty()) {
                approver = CommonStorage.getRepository().getUser(Long.parseLong(request.getParameter("approver")));
            }
            if (request.getParameter("from") != null && !request.getParameter("from").isEmpty()) {
                from = CommonTasks.parseDate(request.getParameter("from"));
            }
            if (request.getParameter("to") != null && !request.getParameter("to").isEmpty()) {
                to = CommonTasks.parseDate(request.getParameter("to"));
            }
            if (request.getParameter("clientRegion") != null && !request.getParameter("clientRegion").isEmpty()) {
                clientRegion = CommonStorage.getRepository().getClientRegion(Long.parseLong(request.getParameter("clientRegion")));
            }
            if (request.getParameter("client") != null && !request.getParameter("client").isEmpty()) {
                client = CommonStorage.getRepository().getClient(Long.parseLong(request.getParameter("client")));
            }
            if (request.getParameter("claim") != null && !request.getParameter("claim").isEmpty()) {
                claim = CommonStorage.getRepository().getClaim(Long.parseLong(request.getParameter("claim")));
            }
            if (request.getParameter("branch") != null && !request.getParameter("branch").isEmpty()) {
                branch = CommonStorage.getRepository().getBranch(Long.parseLong(request.getParameter("branch")));
            }
            if (request.getParameter("branchType") != null && !request.getParameter("branchType").isEmpty()) {
                branchType = CommonStorage.getRepository().getBranchType(Byte.parseByte(request.getParameter("branchType")));
            }
            if (request.getParameter("document") != null && !request.getParameter("document").isEmpty()) {
                String id = request.getParameter("document");
                String projectCode = id.substring(0, id.indexOf("/"));
                String documentNo = id.substring(id.indexOf("/") + 1, id.lastIndexOf("/"));
                int documentYear = Integer.parseInt(id.substring(id.lastIndexOf("/") + 1));
                ArrayList<Document> documents = CommonStorage.getRepository().findDocument(projectCode, documentNo, documentYear);
                if (documents.size() > 0) {
                    document = documents.get(0);
                    request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_DOCUMENTS_LIST));
                }
            }
            if (request.getParameter("claimCNNumber") != null && !request.getParameter("claimCNNumber").isEmpty()) {
                claimCNNumber = request.getParameter("claimCNNumber");
            }

            if (approver == null && reportId != 12) {
                throw new IllegalArgumentException("Approver is not provided");
            }
            switch (reportId) {
                case 1:
                    if (from == null) {
                        throw new IllegalArgumentException("Starting Date required for the selected report type");
                    }
                    if (to == null) {
                        throw new IllegalArgumentException("Ending Date required for the selected report type");
                    }
                    if (clientRegion == null) {
                        throw new IllegalArgumentException("Client region required for the selected report type");
                    }
                    fileName = clientRegion.getClient().getName() + " " + clientRegion.getRegionName() + ".xlsx";
                    CommonStorage.getReportUtil().generateReport(request, approver, fileName, clientRegion, from, to);
                    break;
                case 2:
                    if (client == null) {
                        throw new IllegalArgumentException("Client required for the selected report type");
                    }
                    fileName = client.getName() + ".xlsx";
                    CommonStorage.getReportUtil().generateReport(request, approver, fileName, client, from, to);
                    break;
                case 3:
                    if (clientRegion == null) {
                        throw new IllegalArgumentException("Client region required for the selected report type");
                    }
                    if (claim == null) {
                        throw new IllegalArgumentException("Claim required for the selected report type");
                    }
                    fileName = claim.getClaimNumber() + ".xlsx";
                    CommonStorage.getReportUtil().generateReport(request, approver, fileName, claim, clientRegion);
                    break;
                case 4:
                    if (claim == null) {
                        throw new IllegalArgumentException("Claim required for the selected report type");
                    }
                    fileName = claim.getClaimNumber() + ".xlsx";
                    CommonStorage.getReportUtil().generateReport(request, approver, fileName, claim);
                    break;
                case 5:
                    if (from == null) {
                        throw new IllegalArgumentException("Starting Date required for the selected report type");
                    }
                    if (to == null) {
                        throw new IllegalArgumentException("Ending Date required for the selected report type");
                    }
                    if (client == null) {
                        throw new IllegalArgumentException("Client required for the selected report type");
                    }
                    fileName = client.getName() + ".xlsx";
                    CommonStorage.getReportUtil().generateReport(request, approver, fileName, client, from, to, true);
                    break;
                case 9:
                    if (claimCNNumber == null) {
                        throw new IllegalArgumentException("Claim CN Number required for the selected report type");
                    }

                    fileName = claimCNNumber.replace("/", "-") + " Summary.xlsx";
                    CommonStorage.getReportUtil().generateReport(request, approver, fileName, CommonStorage.getRepository().getClientFromCNClaimNo(claimCNNumber), claimCNNumber);
                    break;
                case 6:
                    if (from == null) {
                        throw new IllegalArgumentException("Starting Date required for the selected report type");
                    }
                    if (to == null) {
                        throw new IllegalArgumentException("Ending Date required for the selected report type");
                    }
                    if (client == null) {
                        throw new IllegalArgumentException("Client required for the selected report type");
                    }
                    fileName = client.getName().replace("/", "-") + ".xlsx";
                    CommonStorage.getReportUtil().generatePaidUnpadiSummary(request, approver, fileName, client, from, to);
                    break;
                case 7:
                    if (from == null) {
                        throw new IllegalArgumentException("Starting Date required for the selected report type");
                    }
                    if (to == null) {
                        throw new IllegalArgumentException("Ending Date required for the selected report type");
                    }
                    if (clientRegion == null) {
                        throw new IllegalArgumentException("Client Region required for the selected report type");
                    }
                    fileName = clientRegion.getRegionName() + ".xlsx";
                    CommonStorage.getReportUtil().generatePaidUnpadiSummary(request, approver, fileName, clientRegion, from, to);
                    break;
                case 8:
                    if (from == null) {
                        throw new IllegalArgumentException("Starting Date required for the selected report type");
                    }
                    if (to == null) {
                        throw new IllegalArgumentException("Ending Date required for the selected report type");
                    }
                    if (client == null) {
                        throw new IllegalArgumentException("Client required for the selected report type");
                    }
                    if (branch == null) {
                        throw new IllegalArgumentException("Paying Branch required for the selected report type");
                    }
                    fileName = client.getName() + " Paid-Unpaid Detail.xlsx";
                    CommonStorage.getReportUtil().generatePaidUnpadiSummary(request, approver, fileName, client, branch, from, to);
                    break;
                case 10:
                    if (claimCNNumber == null) {
                        throw new IllegalArgumentException("Claim CN Number required for the selected report type");
                    }
                    if (clientRegion == null) {
                        throw new IllegalArgumentException("Client Region required for the selected report type");
                    }
                    fileName = claimCNNumber.replace("/", "-") + "-" + clientRegion.getRegionName() + " Summary.xlsx";
                    CommonStorage.getReportUtil().generateReport(request, approver, fileName, clientRegion, claimCNNumber);
                    break;
                case 11:
                    if (claimCNNumber == null) {
                        throw new IllegalArgumentException("Claim CN Number required for the selected report type");
                    }
                    if (branch == null) {
                        throw new IllegalArgumentException("Branch required for the selected report type");
                    }
                    fileName = claimCNNumber.replace("/", "-") + "-" + branch.getName() + " Summary.xlsx";
                    CommonStorage.getReportUtil().generateReport(request, approver, fileName, CommonStorage.getRepository().getClientFromCNClaimNo(claimCNNumber), branch, claimCNNumber);
                    break;
                case 12:
                    if (document == null) {
                        throw new IllegalArgumentException("Document required for the selected report type");
                    }
                    fileName = document.getInComingDocumentNo().replace("/", "-") + " Payments.xlsx";
                    CommonStorage.getReportUtil().generateReport(request, fileName, document);
                    break;
                case 13:
                    if (claimCNNumber == null) {
                        throw new IllegalArgumentException("Claim CN Number required for the selected report type");
                    }
                    if (client == null) {
                        throw new IllegalArgumentException("Client required for the selected report type");
                    }
                    fileName = claimCNNumber.replace("/", "-") + " Summary.xlsx";
                    CommonStorage.getReportUtil().generateReport(request, approver, fileName, CommonStorage.getRepository().getClientFromCNClaimNo(claimCNNumber), claimCNNumber, true);
                    break;
            }
            if (fileName != null) {
                File f = new File(fileName);
                FileInputStream in = new FileInputStream(f);
                byte[] data = new byte[(int) f.length()];
                in.read(data);
                in.close();
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
                response.setHeader("Cache-Control", "no cache");
                OutputStream out = response.getOutputStream();
                out.write(data);
                out.flush();
                out.close();
            } else {
                CommonTasks.writeMessage(request, "Report Not Found", Message.MESSEGE_TYPE_ERROR, true, "Could not find the report requested");
                report(request, response);
            }
        } catch (NumberFormatException | ParseException | IOException ex) {
            CommonTasks.writeMessage(request, "Download Error", Message.MESSEGE_TYPE_ERROR, ex, true, "Error while downloading report");
            report(request, response);
        } catch (Exception ex) {
            CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex, "Something went wrong while tring to updating claim to database from " + request.getRemoteHost());
            CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR, "Something went wrong, the administrator is notified of the problem.", "Please try things again and if the problem persist, try after a while");
            report(request, response);
        }
    }
}
