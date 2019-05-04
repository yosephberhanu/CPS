package et.artisan.cn.cps.controllers;

import et.artisan.cn.cps.dto.BranchesListDTO;
import et.artisan.cn.cps.dto.BranchesParamModel;
import et.artisan.cn.cps.dto.ClaimDetailDTO;
import et.artisan.cn.cps.dto.ClaimDetailParamModel;
import et.artisan.cn.cps.dto.ClaimListDTO;
import et.artisan.cn.cps.dto.ClaimParamModel;
import et.artisan.cn.cps.dto.ClientRegionsListDTO;
import et.artisan.cn.cps.dto.ClientRegionsParamModel;
import et.artisan.cn.cps.dto.DocumentListDTO;
import et.artisan.cn.cps.dto.DocumentQueryParameter;
import et.artisan.cn.cps.dto.JQueryDataTableParamModel;
import et.artisan.cn.cps.dto.PaymentQueryParameter;
import et.artisan.cn.cps.dto.ProjectsListDTO;
import et.artisan.cn.cps.dto.PaymentListDTO;
import et.artisan.cn.cps.util.*;
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
 * Controller for the data entry user
 *
 * @author Yoseph Berhanu<yoseph@artisan.et>
 * @since 1.0
 * @version 1.0
 */
public class DataEntry extends HttpServlet {

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
	// + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
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
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
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
				CommonTasks.writeMessage(request, "Something wrong internally", Message.MESSEGE_TYPE_ERROR, e, true,
						"Something went wrong while tring to parse the multi part requset");
			}
		} else if (request.getParameter("a") != null) {
			action = (String) request.getParameter("a");
		} else {
			action = Constants.ACTION_ALL_WELCOME;
		}
		if (action.equalsIgnoreCase(Constants.ACTION_ALL_WELCOME)) {
			All.welcome(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_BRANCHES)) {
			branches(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_BRANCHES_LIST)) {
			branches(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_BRANCHES_CARD)) {
			branches(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_ADD_BRANCH)) {
			addBranch(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_SAVE_BRANCH)) {
			saveBranch(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_VIEW_BRANCH)) {
			viewBranch(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_EDIT_BRANCH)) {
			editBranch(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_UPDATE_BRANCH)) {
			updateBranch(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_DELETE_BRANCH)) {
			deleteBranchs(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_CLIENTS)) {
			clients(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_CLIENTS_CARD)) {
			clientsCard(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_ADD_CLIENT)) {
			addClient(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_SAVE_CLIENT)) {
			saveClient(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_VIEW_CLIENT)) {
			viewClient(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_EDIT_CLIENT)) {
			editClient(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_UPDATE_CLIENT)) {
			updateClient(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_DELETE_CLIENT)) {
			deleteClients(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_ADD_CLIENT_RATE)) {
			addClientServiceChargeRate(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_SAVE_CLIENT_RATE)) {
			saveClientServiceChargeRate(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_CLIENT_REGIONS)) {
			clientRegions(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_CLIENT_REGIONS_LIST)) {
			clientRegions(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_ADD_CLIENT_REGION)) {
			addClientRegion(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_SAVE_CLIENT_REGION)) {
			saveClientRegion(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_VIEW_CLIENT_REGION)) {
			viewClientRegion(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_EDIT_CLIENT_REGION)) {
			editClientRegion(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_UPDATE_CLIENT_REGION)) {
			updateClientRegion(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_DELETE_CLIENT_REGION)) {
			deleteClientRegions(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_PROJECTS)) {
			projects(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_PROJECTS_LIST)) {
			projectsList(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_PROJECTS_CARD)) {
			projectsCard(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_ADD_PROJECT)) {
			addProject(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_SAVE_PROJECT)) {
			saveProject(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_VIEW_PROJECT)) {
			viewProject(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_EDIT_PROJECT)) {
			editProject(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_UPDATE_PROJECT)) {
			updateProject(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_DELETE_PROJECT)) {
			deleteProjects(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_DOCUMENTS)) {
			documentsList(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_FIND_DOCUMENTS)) {
			findDocuments(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_DOCUMENTS_LIST)) {
			documentsList(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_DOCUMENTS_CARD)) {
			documentsCard(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_ADD_DOCUMENT)) {
			addDocument(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_SAVE_DOCUMENT)) {
			saveDocument(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_VIEW_DOCUMENT)) {
			viewDocument(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_EDIT_DOCUMENT)) {
			editDocument(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_UPDATE_DOCUMENT)) {
			updateDocument(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_DELETE_DOCUMENT)) {
			deleteDocuments(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_GET_DOCUMENT_ATTACHMENT)) {
			getDocumentAttachment(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_PAYMENTS)) {
			payments(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_PAYMENTS_LIST)) {
			payments(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_PAYMENTS_CARD)) {
			paymentsCard(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_ADD_PAYMENT)) {
			addPayment(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_SAVE_PAYMENT)) {
			savePayment(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_VIEW_PAYMENT)) {
			viewPayment(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_EDIT_PAYMENT)) {
			editPayment(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_UPDATE_PAYMENT)) {
			updatePayment(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_DELETE_PAYMENT)) {
			deletePayments(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_CLAIMS)) {
			claims(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_ADD_CLAIM)) {
			addClaim(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_SAVE_CLAIM)) {
			saveClaim(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_VIEW_CLAIM)) {
			viewClaim(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_EDIT_CLAIM)) {
			editClaim(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_UPDATE_CLAIM)) {
			updateClaim(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_DELETE_CLAIM)) {
			deleteClaims(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_GET_CLAIM_ATTACHMENT)) {
			getClaimAttachment(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_CLAIM_DETAILS)) {
			claimDetails(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_ADD_CLAIM_DETAIL)) {
			addClaimDetail(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_SAVE_CLAIM_DETAIL)) {
			saveClaimDetail(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_VIEW_CLAIM_DETAIL)) {
			viewClaimDetail(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_EDIT_CLAIM_DETAIL)) {
			editClaimDetail(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_UPDATE_CLAIM_DETAIL)) {
			updateClaimDetail(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_DELETE_CLAIM_DETAIL)) {
			deleteClaimDetails(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_AMENDMENTS)) {
			amendments(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_ADD_AMENDMENT)) {
			addAmendment(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_SAVE_AMENDMENT)) {
			saveAmendment(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_VIEW_AMENDMENT)) {
			viewAmendment(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_EDIT_AMENDMENT)) {
			editAmendment(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_UPDATE_AMENDMENT)) {
			updateAmendment(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_DELETE_AMENDMENT)) {
			deleteAmendments(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_ADD_IMPORT)) {
			addImport(request, response);
		} else if (action.equalsIgnoreCase(Constants.ACTION_DATAENTRY_SAVE_IMPORT)) {
			saveImport(request, response);
		} else {
			All.notFound(request, response);
		}
	}

	protected static void branches(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("json") == null) {
			branchesListTemplate(request, response);
		} else {
			branchesListData(request, response);
		}

	}

	protected static void branchesListTemplate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_BRANCHES_LIST));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void branchesListData(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BranchesListDTO branches = new BranchesListDTO();
		BranchesParamModel param = (BranchesParamModel)DataTablesParamUtility.getParam(request,new BranchesParamModel());
		branches.setDraw(request.getParameter("draw"));
		branches.setEcho(param.sEcho);
		branches.setJQueryDataTableParamModel(param);
		branches.setTotalNumberOfRecords(CommonStorage.getRepository().getBranchesCount(param));
		branches.setData(CommonStorage.getRepository().getAllBranches(param));
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(branches.toJSON());
	}

	protected static void addBranch(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_ADD_BRANCH));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void saveBranch(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Branch branch = new Branch();
		try {
			branch.setAddressLine(request.getParameter("addressLine"));
			branch.setEmail(request.getParameter("email"));
			branch.setName(request.getParameter("name"));
			branch.setContactPerson(request.getParameter("contactPerson"));
			branch.setCity(request.getParameter("city"));
			branch.setPrimaryPhoneNo(request.getParameter("primaryPhoneNo"));
			branch.setSecondaryPhoneNo(request.getParameter("secondaryPhoneNo"));
			if (request.getParameter("region") != null && !request.getParameter("region").trim().isEmpty()) {
				branch.setRegion(Integer.parseInt(request.getParameter("region")));
			}
			branch.setRemark(request.getParameter("remark"));
			branch.setType(Integer.parseInt(request.getParameter("type")));
			branch.setRegisteredBy(CommonStorage.getCurrentUser(request).getId());
			branch.setRegisteredOn(new Timestamp(Instant.now().toEpochMilli()));
			branch.setStatus(Constants.STATUS_ACTIVE);

			if (branch.valideForSave()) {
				CommonStorage.getRepository().save(branch);
				CommonTasks.writeMessage(request, "Sucessful", Message.MESSEGE_TYPE_SUCCESS,
						"Paying Branch Registration Successful");
				response.sendRedirect(
						request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_ADD_BRANCH);
			} else {
				CommonTasks.writeMessage(request, "Validation failed", Message.MESSEGE_TYPE_ERROR,
						branch.getValidationMessage().toArray(new String[0]));
				CommonTasks.logMessage("Validation failed", Message.MESSEGE_TYPE_ERROR,
						branch.getValidationMessage().toArray(new String[0]));
				request.setAttribute("branch", branch);
				addBranch(request, response);
			}
		} catch (NumberFormatException | IOException | ServletException ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Something went wrong while tring to register new paying branch to database from "
							+ request.getRemoteHost());
			CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR,
					"Something went wrong, the administrator is notified of the problem.",
					"Please try things again and if the problem persist, try after a while");
			request.setAttribute("branch", branch);
			addBranch(request, response);
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Something went wrong while tring to register new paying branch to database from "
							+ request.getRemoteHost());
			CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR,
					"Something went wrong, the administrator is notified of the problem.",
					"Please try things again and if the problem persist, try after a while");
			request.setAttribute("branch", branch);
			addBranch(request, response);
		}
	}

	protected static void viewBranch(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long branchId = Long.parseLong(request.getParameter("i"));
		request.setAttribute("branch", CommonStorage.getRepository().getBranch(branchId));
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_VIEW_BRANCH));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void editBranch(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long branchId = Long.parseLong(request.getParameter("i"));
		request.setAttribute("branch", CommonStorage.getRepository().getBranch(branchId));
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_EDIT_BRANCH));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void updateBranch(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long branchId = Long.parseLong(request.getParameter("i"));
		Branch branch = CommonStorage.getRepository().getBranch(branchId);
		request.setAttribute("branch", branch);
		try {
			branch.setAddressLine(request.getParameter("addressLine"));
			branch.setEmail(request.getParameter("email"));
			branch.setName(request.getParameter("name"));
			branch.setContactPerson(request.getParameter("contactPerson"));
			branch.setCity(request.getParameter("city"));
			branch.setPrimaryPhoneNo(request.getParameter("primaryPhoneNo"));
			branch.setSecondaryPhoneNo(request.getParameter("secondaryPhoneNo"));
			if (request.getParameter("region") != null && !request.getParameter("region").trim().isEmpty()) {
				branch.setRegion(Integer.parseInt(request.getParameter("region")));
			}
			branch.setRemark(request.getParameter("remark"));
			branch.setType(Integer.parseInt(request.getParameter("type")));
			if (branch.valideForUpdate()) {
				CommonStorage.getRepository().update(branch);
				CommonTasks.writeMessage(request, "Sucessful", Message.MESSEGE_TYPE_SUCCESS,
						"Paying Branch Information Updated Successfully");
				response.sendRedirect(request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_BRANCHES);
			} else {
				CommonTasks.writeMessage(request, "Validation failed", Message.MESSEGE_TYPE_ERROR,
						branch.getValidationMessage().toArray(new String[0]));
				CommonTasks.logMessage("Validation failed", Message.MESSEGE_TYPE_ERROR,
						branch.getValidationMessage().toArray(new String[0]));
				request.setAttribute("branch", branch);
				editBranch(request, response);
			}
		} catch (NumberFormatException | IOException | ServletException ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Something went wrong while tring to updating paying branch information in database from "
							+ request.getRemoteHost());
			CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR,
					"Something went wrong, the administrator is notified of the problem.",
					"Please try things again and if the problem persist, try after a while");
			request.setAttribute("branch", branch);
			editBranch(request, response);
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Something went wrong while tring to updating paying branch information in database from "
							+ request.getRemoteHost());
			CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR,
					"Something went wrong, the administrator is notified of the problem.",
					"Please try things again and if the problem persist, try after a while");
			request.setAttribute("branch", branch);
			editBranch(request, response);
		}
	}

	protected static void deleteBranchs(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] ids = request.getParameterValues("i");
		long branchId;
		String[] details = new String[0];
		if (ids == null || ids.length < 1) {
			CommonTasks.writeMessage(request, "No branch selected", Message.MESSEGE_TYPE_ERROR,
					"You have not seleceted any branch for this action");
		} else {
			details = new String[ids.length];
			for (int i = 0; i < ids.length; i++) {
				try {
					branchId = Long.parseLong(ids[i]);
				} catch (Exception ex) {
					CommonTasks.logMessage("Wrong parameter", Message.MESSEGE_TYPE_ERROR, ex,
							"Wrong paramter passed to delete branch " + ids[i]);
					continue;
				}
				String branchName = CommonStorage.getRepository().deleteBranch(branchId);
				if (branchName != null) {
					details[i] = "Branch " + branchName + " successfully removed";
				}
			}
		}
		CommonTasks.writeMessage(request, "Action Performed Successfully", Message.MESSEGE_TYPE_SUCCESS, details);
		response.sendRedirect(request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_BRANCHES);
	}

	protected static void clients(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		clientsList(request, response);
	}

	protected static void clientsList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("clients", CommonStorage.getRepository().getAllClients());
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_CLIENTS_LIST));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void clientsCard(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("clients", CommonStorage.getRepository().getAllClients());
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_CLIENTS_CARD));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void viewClient(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long id = Long.parseLong(request.getParameter("i"));
		request.setAttribute("client", CommonStorage.getRepository().getClient(id));
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_VIEW_CLIENT));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void editClient(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long id = Long.parseLong(request.getParameter("i"));
		request.setAttribute("client", CommonStorage.getRepository().getClient(id));
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_EDIT_CLIENT));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void addClient(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_ADD_CLIENT));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void saveClient(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Client client = new Client();
		try {
			client.setAddressLine(request.getParameter("addressLine"));
			client.setEmail(request.getParameter("email"));
			client.setWebsite(request.getParameter("website"));
			client.setName(request.getParameter("name"));
			client.setAmharicName(new String(request.getParameter("amharicName").getBytes("iso-8859-1"), "UTF-8"));
			client.setContactPerson(request.getParameter("contactPerson"));
			client.setCity(request.getParameter("city"));
			client.setPhoneNo(request.getParameter("phoneNumber"));
			client.setRegion(CommonStorage.getRepository().getRegion(Byte.parseByte(request.getParameter("region"))));
			client.setRemark(request.getParameter("remark"));
			client.setRegisteredBy(CommonStorage.getCurrentUser(request));
			client.setRegisteredOn(new Timestamp(Instant.now().toEpochMilli()));
			client.setStatus(Constants.STATUS_ACTIVE);

			if (client.valideForSave()) {
				CommonStorage.getRepository().save(client);
				CommonTasks.writeMessage(request, "Sucessful", Message.MESSEGE_TYPE_SUCCESS,
						"Client Registration Successful");
				response.sendRedirect(
						request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_ADD_CLIENT);
			} else {
				CommonTasks.writeMessage(request, "Validation failed", Message.MESSEGE_TYPE_ERROR,
						client.getValidationMessage().toArray(new String[0]));
				CommonTasks.logMessage("Validation failed", Message.MESSEGE_TYPE_ERROR,
						client.getValidationMessage().toArray(new String[0]));
				request.setAttribute("client", client);
				addClient(request, response);
			}
		} catch (NumberFormatException | IOException | ServletException ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Something went wrong while tring to register new client to database from "
							+ request.getRemoteHost());
			CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR,
					"Something went wrong, the administrator is notified of the problem.",
					"Please try things again and if the problem persist, try after a while");
			request.setAttribute("client", client);
			addClient(request, response);
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Something went wrong while tring to register new client to database from "
							+ request.getRemoteHost());
			CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR,
					"Something went wrong, the administrator is notified of the problem.",
					"Please try things again and if the problem persist, try after a while");
			request.setAttribute("client", client);
			addClient(request, response);
		}
	}

	protected static void updateClient(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long clientId = Long.parseLong(request.getParameter("i"));
		Client client = CommonStorage.getRepository().getClient(clientId);
		request.setAttribute("client", client);
		try {
			client.setAddressLine(request.getParameter("addressLine"));
			client.setEmail(request.getParameter("email"));
			client.setWebsite(request.getParameter("website"));
			client.setName(request.getParameter("name"));
			client.setAmharicName(new String(request.getParameter("amharicName").getBytes("iso-8859-1"), "UTF-8"));
			client.setContactPerson(request.getParameter("contactPerson"));
			client.setCity(request.getParameter("city"));
			client.setPhoneNo(request.getParameter("phoneNumber"));
			client.setRegion(CommonStorage.getRepository().getRegion(Byte.parseByte(request.getParameter("region"))));
			client.setRemark(request.getParameter("remark"));

			if (client.valideForUpdate()) {
				CommonStorage.getRepository().update(client);
				CommonTasks.writeMessage(request, "Sucessful", Message.MESSEGE_TYPE_SUCCESS,
						"Client Information Updated Successfully");
				response.sendRedirect(request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_CLIENTS);
			} else {
				CommonTasks.writeMessage(request, "Validation failed", Message.MESSEGE_TYPE_ERROR,
						client.getValidationMessage().toArray(new String[0]));
				CommonTasks.logMessage("Validation failed", Message.MESSEGE_TYPE_ERROR,
						client.getValidationMessage().toArray(new String[0]));
				request.setAttribute("client", client);
				editClient(request, response);
			}
		} catch (NumberFormatException | IOException | ServletException ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Something went wrong while tring to updating client information in database from "
							+ request.getRemoteHost());
			CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR,
					"Something went wrong, the administrator is notified of the problem.",
					"Please try things again and if the problem persist, try after a while");
			request.setAttribute("branch", client);
			editClient(request, response);
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Something went wrong while tring to updating client information in database from "
							+ request.getRemoteHost());
			CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR,
					"Something went wrong, the administrator is notified of the problem.",
					"Please try things again and if the problem persist, try after a while");
			request.setAttribute("branch", client);
			editClient(request, response);
		}
	}

	protected static void deleteClients(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] ids = request.getParameterValues("i");
		long clientId;
		String[] details = new String[0];
		if (ids == null || ids.length < 1) {
			CommonTasks.writeMessage(request, "No branch selected", Message.MESSEGE_TYPE_ERROR,
					"You have not seleceted any branch for this action");
		} else {
			details = new String[ids.length];
			for (int i = 0; i < ids.length; i++) {
				try {
					clientId = Long.parseLong(ids[i]);
				} catch (Exception ex) {
					CommonTasks.logMessage("Wrong parameter", Message.MESSEGE_TYPE_ERROR, ex,
							"Wrong paramter passed to delete branch " + ids[i]);
					continue;
				}
				String clientName = CommonStorage.getRepository().deleteClients(clientId);
				if (clientName != null) {
					details[i] = "Client " + clientName + " successfully removed";
				}
			}
		}
		CommonTasks.writeMessage(request, "Action Performed Successfully", Message.MESSEGE_TYPE_SUCCESS, details);
		response.sendRedirect(request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_CLIENTS);
	}

	protected static void addClientServiceChargeRate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("client",
				CommonStorage.getRepository().getClient(Long.parseLong(request.getParameter("cl"))));
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_ADD_CLIENT_RATE));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void saveClientServiceChargeRate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ClientServiceChargeRate scRate = new ClientServiceChargeRate();
		try {
			scRate.setClient(CommonStorage.getRepository().getClient(Long.parseLong(request.getParameter("client"))));
			scRate.setStartDate(CommonTasks.parseDate(request.getParameter("startDate")));
			scRate.setEndDate(CommonTasks.parseDate(request.getParameter("endDate")));
			scRate.setRate(Double.parseDouble(request.getParameter("rate")));
			scRate.setRegisteredBy(CommonStorage.getCurrentUser(request));
			scRate.setRegisteredOn(new Timestamp(Instant.now().toEpochMilli()));
			scRate.setStatus(Constants.STATUS_ACTIVE);

			if (scRate.valideForSave()) {
				CommonStorage.getRepository().save(scRate);
				CommonStorage.getRepository().updateClientServiceChargeRate(scRate.getClient().getId());
				CommonTasks.writeMessage(request, "Sucessful", Message.MESSEGE_TYPE_SUCCESS,
						"Client Service Charge Rate Updated Successfully");
				response.sendRedirect(request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_CLIENTS);
			} else {
				CommonTasks.writeMessage(request, "Validation failed", Message.MESSEGE_TYPE_ERROR,
						scRate.getValidationMessage().toArray(new String[0]));
				CommonTasks.logMessage("Validation failed", Message.MESSEGE_TYPE_ERROR,
						scRate.getValidationMessage().toArray(new String[0]));
				request.setAttribute("scRate", scRate);
				addClientServiceChargeRate(request, response);
			}
		} catch (NumberFormatException | IOException | ServletException ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Something went wrong while tring to register new client service charge to database from "
							+ request.getRemoteHost());
			CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR,
					"Something went wrong, the administrator is notified of the problem.",
					"Please try things again and if the problem persist, try after a while");
			request.setAttribute("scRate", scRate);
			addClientServiceChargeRate(request, response);
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Something went wrong while tring to register new client region to database from "
							+ request.getRemoteHost());
			CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR,
					"Something went wrong, the administrator is notified of the problem.",
					"Please try things again and if the problem persist, try after a while");
			request.setAttribute("scRate", scRate);
			addClientServiceChargeRate(request, response);
		}
	}

	protected static void clientRegions(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("json") == null) {
			clientRegionsListTemplate(request, response);
		} else {
			clientRegionsListData(request, response);
		}
	}

	protected static void clientRegionsListTemplate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_CLIENT_REGIONS_LIST));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void clientRegionsListData(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ClientRegionsListDTO clientRegions = new ClientRegionsListDTO();
		ClientRegionsParamModel param = (ClientRegionsParamModel)DataTablesParamUtility.getParam(request,new ClientRegionsParamModel());
		
		if (request.getParameter("c") != null) {
			param.setClientId(Long.parseLong(request.getParameter("c")));
		}
		clientRegions.setDraw(request.getParameter("draw"));
		clientRegions.setEcho(param.sEcho);
		clientRegions.setJQueryDataTableParamModel(param);
		clientRegions.setTotalNumberOfRecords(CommonStorage.getRepository().getClientRegionCount(param));
		clientRegions.setData(CommonStorage.getRepository().getAllClientRegions(param));
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(clientRegions.toJSON());
	}
	
	protected static void addClientRegion(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("cl") != null) {
			request.setAttribute("clientId", Long.parseLong(request.getParameter("cl")));
		}

		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_ADD_CLIENT_REGION));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void saveClientRegion(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ClientRegion clientRegion = new ClientRegion();
		try {
			clientRegion.setAddressLine(request.getParameter("addressLine"));
			clientRegion
					.setClient(CommonStorage.getRepository().getClient(Long.parseLong(request.getParameter("client"))));
			clientRegion.setRegionName(request.getParameter("regionName"));
			clientRegion
					.setAmharicName(new String(request.getParameter("amharicName").getBytes("iso-8859-1"), "UTF-8"));
			clientRegion.setContactPerson(request.getParameter("contactPerson"));
			clientRegion.setPhoneNumber(request.getParameter("phoneNumber"));
			clientRegion.setRemark(request.getParameter("remark"));
			clientRegion.setRegisteredBy(CommonStorage.getCurrentUser(request));
			clientRegion.setRegisteredOn(new Timestamp(Instant.now().toEpochMilli()));
			clientRegion.setStatus(Constants.STATUS_ACTIVE);

			if (clientRegion.valideForSave()) {
				CommonStorage.getRepository().save(clientRegion);
				CommonTasks.writeMessage(request, "Sucessful", Message.MESSEGE_TYPE_SUCCESS,
						"Client Region Registration Successful");
				response.sendRedirect(
						request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_CLIENT_REGIONS);
			} else {
				CommonTasks.writeMessage(request, "Validation failed", Message.MESSEGE_TYPE_ERROR,
						clientRegion.getValidationMessage().toArray(new String[0]));
				CommonTasks.logMessage("Validation failed", Message.MESSEGE_TYPE_ERROR,
						clientRegion.getValidationMessage().toArray(new String[0]));
				request.setAttribute("clientRegion", clientRegion);
				addClientRegion(request, response);
			}
		} catch (NumberFormatException | IOException | ServletException ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Something went wrong while tring to register new client region to database from "
							+ request.getRemoteHost());
			CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR,
					"Something went wrong, the administrator is notified of the problem.",
					"Please try things again and if the problem persist, try after a while");
			request.setAttribute("clientRegion", clientRegion);
			addClientRegion(request, response);
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Something went wrong while tring to register new client region to database from "
							+ request.getRemoteHost());
			CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR,
					"Something went wrong, the administrator is notified of the problem.",
					"Please try things again and if the problem persist, try after a while");
			request.setAttribute("clientRegion", clientRegion);
			addClientRegion(request, response);
		}
	}

	protected static void viewClientRegion(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long id = Long.parseLong(request.getParameter("i"));
		request.setAttribute("clientRegion", CommonStorage.getRepository().getClientRegion(id));
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_VIEW_CLIENT_REGION));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void editClientRegion(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long id = Long.parseLong(request.getParameter("i"));
		request.setAttribute("clientRegion", CommonStorage.getRepository().getClientRegion(id));
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_EDIT_CLIENT_REGION));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void updateClientRegion(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long clientRegionId = Long.parseLong(request.getParameter("i"));
		ClientRegion clientRegion = CommonStorage.getRepository().getClientRegion(clientRegionId);
		request.setAttribute("clientRegion", clientRegion);
		try {
			clientRegion.setAddressLine(request.getParameter("addressLine"));
			clientRegion
					.setClient(CommonStorage.getRepository().getClient(Long.parseLong(request.getParameter("client"))));
			clientRegion.setRegionName(request.getParameter("regionName"));
			clientRegion
					.setAmharicName(new String(request.getParameter("amharicName").getBytes("iso-8859-1"), "UTF-8"));
			clientRegion.setContactPerson(request.getParameter("contactPerson"));
			clientRegion.setPhoneNumber(request.getParameter("phoneNumber"));
			clientRegion.setRemark(request.getParameter("remark"));

			if (clientRegion.valideForUpdate()) {
				CommonStorage.getRepository().update(clientRegion);
				CommonTasks.writeMessage(request, "Sucessful", Message.MESSEGE_TYPE_SUCCESS,
						"Client Region Information Updated Successfully");
				response.sendRedirect(
						request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_CLIENT_REGIONS);
			} else {
				CommonTasks.writeMessage(request, "Validation failed", Message.MESSEGE_TYPE_ERROR,
						clientRegion.getValidationMessage().toArray(new String[0]));
				CommonTasks.logMessage("Validation failed", Message.MESSEGE_TYPE_ERROR,
						clientRegion.getValidationMessage().toArray(new String[0]));
				request.setAttribute("clientRegion", clientRegion);
				editClientRegion(request, response);
			}
		} catch (NumberFormatException | IOException | ServletException ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Something went wrong while tring to updating client region information in database from "
							+ request.getRemoteHost());
			CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR,
					"Something went wrong, the administrator is notified of the problem.",
					"Please try things again and if the problem persist, try after a while");
			request.setAttribute("clientRegion", clientRegion);
			editClientRegion(request, response);
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Something went wrong while tring to updating client region information in database from "
							+ request.getRemoteHost());
			CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR,
					"Something went wrong, the administrator is notified of the problem.",
					"Please try things again and if the problem persist, try after a while");
			request.setAttribute("clientRegion", clientRegion);
			editClientRegion(request, response);
		}
	}

	protected static void deleteClientRegions(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] ids = request.getParameterValues("i");
		long clientRegionId;
		String[] details = new String[0];
		if (ids == null || ids.length < 1) {
			CommonTasks.writeMessage(request, "No client region selected", Message.MESSEGE_TYPE_ERROR,
					"You have not seleceted any client region for this action");
		} else {
			details = new String[ids.length];
			for (int i = 0; i < ids.length; i++) {
				try {
					clientRegionId = Long.parseLong(ids[i]);
				} catch (Exception ex) {
					CommonTasks.logMessage("Wrong parameter", Message.MESSEGE_TYPE_ERROR, ex,
							"Wrong paramter passed to delete client region " + ids[i]);
					continue;
				}
				String clientRegionName = CommonStorage.getRepository().deleteClientRegions(clientRegionId);
				if (clientRegionName != null) {
					details[i] = "Client Region " + clientRegionName + " successfully removed";
				}
			}
		}
		CommonTasks.writeMessage(request, "Action Performed Successfully", Message.MESSEGE_TYPE_SUCCESS, details);
		response.sendRedirect(request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_CLIENT_REGIONS);
	}

	protected static void projects(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("json") == null) {
			projectsListTemplate(request, response);
		} else {
			projectsListData(request, response);
		}

	}

	protected static void projectsListTemplate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// request.setAttribute("projects",
		// CommonStorage.getRepository().getAllProjects());
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_PROJECTS_LIST));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void projectsListData(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ProjectsListDTO projects = new ProjectsListDTO();
		JQueryDataTableParamModel param = DataTablesParamUtility.getParam(request,new JQueryDataTableParamModel());
		projects.setDraw(request.getParameter("draw"));
		projects.setEcho(param.sEcho);
		projects.setJQueryDataTableParamModel(param);
		projects.setTotalNumberOfRecords(CommonStorage.getRepository().getProjectsCount(param));
		projects.setData(CommonStorage.getRepository().getAllProjects(param));
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(projects.toJSON());
	}

	protected static void projectsList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("projects", CommonStorage.getRepository().getAllProjects(new JQueryDataTableParamModel()));
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_PROJECTS_LIST));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void projectsCard(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("projects", CommonStorage.getRepository().getAllProjects(new JQueryDataTableParamModel()));
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_BRANCHES_CARD));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void addProject(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("cl") != null) {
			request.setAttribute("clientId", Long.parseLong(request.getParameter("cl")));
		}

		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_ADD_PROJECT));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void saveProject(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Project project = new Project();
		try {
			project.setClient(CommonStorage.getRepository().getClient(Long.parseLong(request.getParameter("client"))));
			project.setCode(request.getParameter("code"));
			project.setName(request.getParameter("name"));
			project.setAmharicName(new String(request.getParameter("amharicName").getBytes("iso-8859-1"), "UTF-8"));
			project.setRemark(request.getParameter("remark"));
			project.setRegisteredBy(CommonStorage.getCurrentUser(request));
			project.setRegisteredOn(new Timestamp(Instant.now().toEpochMilli()));
			project.setStatus(Constants.STATUS_ACTIVE);

			if (project.valideForSave()) {
				CommonStorage.getRepository().save(project);
				CommonTasks.writeMessage(request, "Sucessful", Message.MESSEGE_TYPE_SUCCESS,
						"Project Registration Successful");
				response.sendRedirect(request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_PROJECTS);
			} else {
				CommonTasks.writeMessage(request, "Validation failed", Message.MESSEGE_TYPE_ERROR,
						project.getValidationMessage().toArray(new String[0]));
				CommonTasks.logMessage("Validation failed", Message.MESSEGE_TYPE_ERROR,
						project.getValidationMessage().toArray(new String[0]));
				request.setAttribute("project", project);
				addProject(request, response);
			}
		} catch (NumberFormatException | IOException | ServletException ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Something went wrong while tring to register new project to database from "
							+ request.getRemoteHost());
			CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR,
					"Something went wrong, the administrator is notified of the problem.",
					"Please try things again and if the problem persist, try after a while");
			request.setAttribute("project", project);
			addProject(request, response);
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Something went wrong while tring to register new project to database from "
							+ request.getRemoteHost());
			CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR,
					"Something went wrong, the administrator is notified of the problem.",
					"Please try things again and if the problem persist, try after a while");
			request.setAttribute("project", project);
			addProject(request, response);
		}
	}

	protected static void viewProject(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long id = Long.parseLong(request.getParameter("i"));
		request.setAttribute("project", CommonStorage.getRepository().getProject(id));
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_VIEW_PROJECT));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void editProject(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long id = Long.parseLong(request.getParameter("i"));
		request.setAttribute("project", CommonStorage.getRepository().getProject(id));
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_EDIT_PROJECT));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void updateProject(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long projectId = Long.parseLong(request.getParameter("i"));
		Project project = CommonStorage.getRepository().getProject(projectId);
		try {
			project.setClientId(Long.parseLong(request.getParameter("client")));
			project.setCode(request.getParameter("code"));
			project.setName(request.getParameter("name"));
			project.setAmharicName(new String(request.getParameter("amharicName").getBytes("iso-8859-1"), "UTF-8"));
			project.setRemark(request.getParameter("remark"));
			if (project.valideForUpdate()) {
				CommonStorage.getRepository().update(project);
				CommonTasks.writeMessage(request, "Sucessful", Message.MESSEGE_TYPE_SUCCESS,
						"Project Detailes Updated Successful");
				response.sendRedirect(request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_PROJECTS);
			} else {
				CommonTasks.writeMessage(request, "Validation failed", Message.MESSEGE_TYPE_ERROR,
						project.getValidationMessage().toArray(new String[0]));
				CommonTasks.logMessage("Validation failed", Message.MESSEGE_TYPE_ERROR,
						project.getValidationMessage().toArray(new String[0]));
				request.setAttribute("project", project);
				editProject(request, response);
			}
		} catch (NumberFormatException | IOException | ServletException ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Something went wrong while tring to update project detail in the database from "
							+ request.getRemoteHost());
			CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR,
					"Something went wrong, the administrator is notified of the problem.",
					"Please try things again and if the problem persist, try after a while");
			request.setAttribute("project", project);
			editProject(request, response);
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Something went wrong while tring to update project detail in the database from "
							+ request.getRemoteHost());
			CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR,
					"Something went wrong, the administrator is notified of the problem.",
					"Please try things again and if the problem persist, try after a while");
			request.setAttribute("project", project);
			editProject(request, response);
		}
	}

	protected static void deleteProjects(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] ids = request.getParameterValues("i");
		long projectId;
		String[] details = new String[0];
		if (ids == null || ids.length < 1) {
			CommonTasks.writeMessage(request, "No project selected", Message.MESSEGE_TYPE_ERROR,
					"You have not seleceted any project for this action");
		} else {
			details = new String[ids.length];
			for (int i = 0; i < ids.length; i++) {
				try {
					projectId = Long.parseLong(ids[i]);
				} catch (Exception ex) {
					CommonTasks.logMessage("Wrong parameter", Message.MESSEGE_TYPE_ERROR, ex,
							"Wrong paramter passed to delete project " + ids[i]);
					continue;
				}
				String projectName = CommonStorage.getRepository().deleteProject(projectId);
				if (projectName != null) {
					details[i] = "Project " + projectName + " successfully removed";
				}
			}
		}
		CommonTasks.writeMessage(request, "Action Performed Successfully", Message.MESSEGE_TYPE_SUCCESS, details);
		response.sendRedirect(request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_PROJECTS);
	}

	protected static void documents(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_DOCUMENTS));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void documentsList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("json") == null) {
			request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_DOCUMENTS_LIST));
			RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
			rd.forward(request, response);
		} else {
			documentsListData(request, response);
		}
		//request.setAttribute("documents", CommonStorage.getRepository().getAllDocuments());
		
	}
	
	protected static void documentsListData(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		DocumentListDTO documents = new DocumentListDTO();
		DocumentQueryParameter param = (DocumentQueryParameter)DataTablesParamUtility.getParam(request, new DocumentQueryParameter());

		if (request.getParameter("amount") != null && !request.getParameter("amount").contains("undefined")
				&& !request.getParameter("amount").trim().isEmpty()) {
			param.setAmount(Double.parseDouble(request.getParameter("amount"))); 
		} 
		if (request.getParameter("cnnumber") != null && !request.getParameter("cnnumber").contains("undefined")
				&& !request.getParameter("cnnumber").trim().isEmpty()) {
			param.setOutgoingdocumentno(request.getParameter("cnnumber"));
		}
		
		if (request.getParameter("id") != null && !request.getParameter("id").contains("undefined")
				&& !request.getParameter("id").trim().isEmpty()) {
			param.setDocumentId(request.getParameter("id"));
		}
	
		documents.setDraw(request.getParameter("draw"));
		documents.setEcho(param.sEcho);
		documents.setDocumentQueryParameter(param);
		documents.setTotalNumberOfRecords(CommonStorage.getRepository().getDocumentCount(param));
		documents.setData(CommonStorage.getRepository().getAllDocuments(param));
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(documents.toJSON());
	}

	protected static void documentsCard(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("documents", CommonStorage.getRepository().getAllDocuments());
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_DOCUMENTS_CARD));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void addDocument(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("cl") != null) {
			request.setAttribute("clientId", Long.parseLong(request.getParameter("cl")));
		}
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_ADD_DOCUMENT));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void saveDocument(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Document document = new Document();
		try {
			List<FileItem> list = (List<FileItem>) request.getAttribute("items");
			for (int i = 0; i < list.size(); i++) {
				FileItem thisItem = list.get(i);
				if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("incomingDate")) {
					document.setInComingDate(
							CommonTasks.parseDate(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8")));
				} else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("clientRegion")) {
					long regionId = Long.parseLong(thisItem.getString());
					if (regionId > 0) {
						document.setClientRegion(CommonStorage.getRepository().getClientRegion(regionId));
					}
				} else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("project")) {
					document.setProject(CommonStorage.getRepository().getProject(
							Long.parseLong(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"))));
				} else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("payingBranch")) {
					document.setBranch(CommonStorage.getRepository().getBranch(
							Long.parseLong(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"))));
				} else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("documentYear")) {
					document.setDocumentYear(
							Integer.parseInt(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8")));
				} else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("inComingDocumentNo")) {
					document.setInComingDocumentNo(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"));
				} else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("outGoingDocumentNo")) {
					document.setOutGoingDocumentNo(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"));
				} else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("outGoingDate")) {
					document.setOutGoingDate(
							CommonTasks.parseDate(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8")));
				} else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("paymentDue")) {
					document.setPaymentDue(
							CommonTasks.parseDate(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8")));
				} else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("totalAmount")) {
					document.setTotalAmount(
							Double.parseDouble(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8")));
				} else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("remark")) {
					document.setRemark(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"));
				} else if (thisItem.getFieldName().equalsIgnoreCase("scannedDocument")
						&& thisItem.getName().contains(".")) {
					String fileExtension = thisItem.getName().substring(thisItem.getName().lastIndexOf('.'));
					document.setScannedDocument(thisItem.get());
					document.setScannedDocumentFormat(fileExtension);
				}
			}
			document.setRegisteredBy(CommonStorage.getCurrentUser(request));
			document.setRegisteredOn(new Timestamp(Instant.now().toEpochMilli()));
			document.setAssignedTo(CommonStorage.getCurrentUser(request));
			document.setAssignedOn(new Timestamp(Instant.now().toEpochMilli()));
			document.setStatus(Constants.STATUS_ACTIVE);

			if (document.valideForSave()) {
				CommonStorage.getRepository().save(document);
				CommonTasks.writeMessage(request, "Sucessful", Message.MESSEGE_TYPE_SUCCESS,
						"Document registration successful");
				request.setAttribute("document", document);
				response.getOutputStream().println("Docunment Saved Successfully");
			} else {
				response.getOutputStream().println(document.getValidationMessage().toArray(new String[0])[0]);
			}
			/*
			 * } else { addDocument(request, response); CommonTasks.writeMessage(request,
			 * "Validation failed", Message.MESSEGE_TYPE_ERROR,
			 * document.getValidationMessage().toArray(new String[0]));
			 * CommonTasks.logMessage("Validation failed", Message.MESSEGE_TYPE_ERROR,
			 * document.getValidationMessage().toArray(new String[0]));
			 * request.setAttribute("document", document); addDocument(request, response); }
			 */
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Something went wrong while tring to register new document to database from "
							+ request.getRemoteHost());
			CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR,
					"Something went wrong, the administrator is notified of the problem.",
					"Please try things again and if the problem persist, try after a while");
			request.setAttribute("document", document);
			addDocument(request, response);
		}
	}

	protected static void viewDocument(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long id = Long.parseLong(request.getParameter("i"));
		request.setAttribute("document", CommonStorage.getRepository().getDocument(id));
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_VIEW_DOCUMENT));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void findDocuments(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			ArrayList<Document> documents = new ArrayList<>();
			if (request.getParameter("id") != null && !request.getParameter("id").contains("undefined")
					&& request.getParameter("id").contains("/")) {
				String id = request.getParameter("id");
				String projectCode = id.substring(0, id.indexOf("/"));
				String documentNo = id.substring(id.indexOf("/") + 1, id.lastIndexOf("/"));
				int documentYear = Integer.parseInt(id.substring(id.lastIndexOf("/") + 1));
				documents = CommonStorage.getRepository().findDocument(projectCode, documentNo, documentYear);
			} else if (request.getParameter("amount") != null && !request.getParameter("amount").contains("undefined")
					&& !request.getParameter("amount").trim().isEmpty()) {
				double amount = Double.parseDouble(request.getParameter("amount"));
				documents = CommonStorage.getRepository().findDocument(amount);
			} else if (request.getParameter("cnnumber") != null && !request.getParameter("cnnumber").contains("undefined")
					&& !request.getParameter("cnnumber").trim().isEmpty()) {
				//double amount = Double.parseDouble(request.getParameter("amount"));
				documents = CommonStorage.getRepository().findDocument(request.getParameter("amount"));
			}
			if (documents.size() > 1) {
				request.setAttribute("documents", documents);
				request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_DOCUMENTS_LIST));
			} else if (documents.size() == 1) {
				request.setAttribute("document", CommonStorage.getRepository().getDocument(documents.get(0).getId()));
				request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_VIEW_DOCUMENT));
			} else {
				CommonTasks.writeMessage(request, "Not Found", Message.MESSEGE_TYPE_ERROR, "The document you searched for is not available");
				request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_DOCUMENTS));
			}
			RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
			rd.forward(request, response);
		} catch (IOException | NumberFormatException | ServletException e) {
			CommonTasks.logMessage("Document not found", Message.MESSEGE_TYPE_ERROR, e,
					"The document you are looking for does not exist " + request.getRemoteHost());
			CommonTasks.writeMessage(request, "Document Not Found", Message.MESSEGE_TYPE_ERROR,
					"The document you are looking for does not exist .",
					"The document you are looking for does not exist.");
			documents(request, response);
		}
	}

	protected static void editDocument(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long id = Long.parseLong(request.getParameter("i"));
		request.setAttribute("document", CommonStorage.getRepository().getDocument(id));
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_EDIT_DOCUMENT));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void updateDocument(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long id = Long.parseLong((String) request.getAttribute("i").toString());
		Document document = CommonStorage.getRepository().getDocument(id);
		try {
			List<FileItem> list = (List<FileItem>) request.getAttribute("items");
			for (int i = 0; i < list.size(); i++) {
				FileItem thisItem = list.get(i);
				if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("incomingDate")) {
					document.setInComingDate(
							CommonTasks.parseDate(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8")));
				} else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("clientRegion")) {
					document.setClientRegion(CommonStorage.getRepository().getClientRegion(
							Long.parseLong(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"))));
				} else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("project")) {
					document.setProject(CommonStorage.getRepository().getProject(
							Long.parseLong(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"))));
				} else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("payingBranch")) {
					document.setBranch(CommonStorage.getRepository().getBranch(
							Long.parseLong(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"))));
				} else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("documentYear")) {
					document.setDocumentYear(
							Integer.parseInt(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8")));
				} else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("inComingDocumentNo")) {
					document.setInComingDocumentNo(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"));
				} else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("outGoingDocumentNo")) {
					document.setOutGoingDocumentNo(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"));
				} else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("outGoingDate")) {
					document.setOutGoingDate(
							CommonTasks.parseDate(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8")));
				} else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("paymentDue")) {
					document.setPaymentDue(
							CommonTasks.parseDate(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8")));
				} else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("totalAmount")) {
					document.setTotalAmount(
							Double.parseDouble(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8")));
				} else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("remark")) {
					document.setRemark(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"));
				} else if (thisItem.getFieldName().equalsIgnoreCase("scannedDocument")
						&& thisItem.getName().contains(".")) {
					String fileExtension = thisItem.getName().substring(thisItem.getName().lastIndexOf('.'));
					document.setScannedDocument(thisItem.get());
					document.setScannedDocumentFormat(fileExtension);
				}
			}
			document.setRegisteredBy(CommonStorage.getCurrentUser(request));
			document.setRegisteredOn(new Timestamp(Instant.now().toEpochMilli()));
			document.setAssignedTo(CommonStorage.getCurrentUser(request));
			document.setAssignedOn(new Timestamp(Instant.now().toEpochMilli()));
			if (document.valideForUpdate()) {
				CommonStorage.getRepository().update(document);
				CommonTasks.writeMessage(request, "Sucessful", Message.MESSEGE_TYPE_SUCCESS,
						"Document updated successful");
				response.sendRedirect(
						request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_DOCUMENTS);
			} else {
				CommonTasks.writeMessage(request, "Validation failed", Message.MESSEGE_TYPE_ERROR,
						document.getValidationMessage().toArray(new String[0]));
				CommonTasks.logMessage("Validation failed", Message.MESSEGE_TYPE_ERROR,
						document.getValidationMessage().toArray(new String[0]));
				request.setAttribute("user", document);
				editDocument(request, response);
			}
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Something went wrong while tring to register new document to database from "
							+ request.getRemoteHost());
			CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR,
					"Something went wrong, the administrator is notified of the problem.",
					"Please try things again and if the problem persist, try after a while");
			request.setAttribute("document", document);
			editDocument(request, response);
		}
	}

	protected static void deleteDocuments(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] ids = request.getParameterValues("i");
		long documentId;
		String[] details = new String[0];
		if (ids == null || ids.length < 1) {
			CommonTasks.writeMessage(request, "No document selected", Message.MESSEGE_TYPE_ERROR,
					"You have not seleceted any document for this action");
		} else {
			details = new String[ids.length];
			for (int i = 0; i < ids.length; i++) {
				try {
					documentId = Long.parseLong(ids[i]);
				} catch (Exception ex) {
					CommonTasks.logMessage("Wrong parameter", Message.MESSEGE_TYPE_ERROR, ex,
							"Wrong paramter passed to delete document " + ids[i]);
					continue;
				}
				String documentNo = CommonStorage.getRepository().deleteDocument(documentId);
				if (documentNo != null) {
					details[i] = "Document " + documentNo + " successfully removed";
				}
			}
		}
		CommonTasks.writeMessage(request, "Action Performed Successfully", Message.MESSEGE_TYPE_SUCCESS, details);
		response.sendRedirect(request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_DOCUMENTS);
	}

	protected static void getDocumentAttachment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		OutputStream out = response.getOutputStream();
		long id = Long.parseLong(request.getParameter("i"));
		Document document = CommonStorage.getRepository().getDocument(id);
		if (document.getScannedDocument() == null) {
			// response.sendRedirect(request.getContextPath() + "/" +
			// CommonStorage.getDefaultDocue());
		} else {
			// response.setContentType("pdf/*");
			response.setHeader("Content-Disposition", "attachment; filename=scanned-document-" + document.getId() + "."
					+ document.getScannedDocumentFormat());
			out.write(document.getScannedDocument());
			out.flush();
			out.close();
		}
	}

	protected static void payments(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("json") == null) {
			paymentsListTemplate(request, response);
		} else {
			paymentsListData(request, response);
		}

	}
	protected static void paymentsListTemplate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_PAYMENTS_LIST));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void paymentsListData(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PaymentListDTO payments = new PaymentListDTO();
		PaymentQueryParameter param = (PaymentQueryParameter)DataTablesParamUtility.getParam(request, new PaymentQueryParameter());

		if (request.getParameter("b") != null) {
			param.setBranchId(Long.parseLong(request.getParameter("b")));
		}
		if (request.getParameter("p") != null) {
			param.setProjectId(Long.parseLong(request.getParameter("p")));
		}
		if (request.getParameter("d") != null) {
			param.setDocumentId(Long.parseLong(request.getParameter("d")));
		}

		payments.setDraw(request.getParameter("draw"));
		payments.setEcho(param.sEcho);
		payments.setJQueryDataTableParamModel(param);
		payments.setTotalNumberOfRecords(CommonStorage.getRepository().getPaymentsCount(param));
		payments.setData(CommonStorage.getRepository().getAllPayments(param));
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(payments.toJSON());
	}

	protected static void paymentsCard(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_PAYMENTS_CARD));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void addPayment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("d") != null) {
			request.setAttribute("documentId", Long.parseLong(request.getParameter("d")));
		}
		if (request.getParameter("p") != null) {
			request.setAttribute("projectId", Long.parseLong(request.getParameter("p")));
		}
		if (request.getParameter("l") != null) {
			request.setAttribute("lotNo", Integer.parseInt(request.getParameter("l")));
		} else {
			request.setAttribute("lotNo", 1);
		}

		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_ADD_PAYMENT));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void savePayment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Payment payment = new Payment();
		try {
			payment.setDocument(
					CommonStorage.getRepository().getDocument(Long.parseLong(request.getParameter("document"))));
			payment.setLotNo(Integer.parseInt(request.getParameter("lotNo")));
			payment.setName(request.getParameter("name"));
			payment.setAmount(Double.parseDouble(request.getParameter("amount")));
			payment.isRestricted(false);
			if (request.getParameter("restricted") != null
					&& request.getParameter("restricted").trim().equalsIgnoreCase("yes")) {
				payment.isRestricted(true);
			}
			payment.setRemark(request.getParameter("remark"));
			payment.setRegisteredBy(CommonStorage.getCurrentUser(request));
			payment.setRegisteredOn(new Timestamp(Instant.now().toEpochMilli()));
			payment.setStatus(Constants.STATUS_ACTIVE);

			if (payment.valideForSave()) {
				CommonStorage.getRepository().save(payment);
				CommonTasks.writeMessage(request, "Sucessful", Message.MESSEGE_TYPE_SUCCESS,
						"Payment Registration Successful");
				response.sendRedirect(request.getContextPath() + "/DataEntry?a="
						+ Constants.ACTION_DATAENTRY_ADD_PAYMENT + "&p=" + payment.getDocument().getProject().getId()
						+ "&d=" + payment.getDocument().getId() + "&l=" + (payment.getLotNo() + 1));
			} else {
				CommonTasks.writeMessage(request, "Validation failed", Message.MESSEGE_TYPE_ERROR,
						payment.getValidationMessage().toArray(new String[0]));
				CommonTasks.logMessage("Validation failed", Message.MESSEGE_TYPE_ERROR,
						payment.getValidationMessage().toArray(new String[0]));
				request.setAttribute("payment", payment);
				addPayment(request, response);
			}
		} catch (NumberFormatException | IOException | ServletException ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Something went wrong while tring to register new payment to database from "
							+ request.getRemoteHost());
			CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR,
					"Something went wrong, the administrator is notified of the problem.",
					"Please try things again and if the problem persist, try after a while");
			request.setAttribute("payment", payment);
			addPayment(request, response);
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Something went wrong while tring to register new payment to database from "
							+ request.getRemoteHost());
			CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR,
					"Something went wrong, the administrator is notified of the problem.",
					"Please try things again and if the problem persist, try after a while");
			request.setAttribute("payment", payment);
			addPayment(request, response);
		}
	}

	protected static void viewPayment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("payment",
				CommonStorage.getRepository().getPayment(Long.parseLong(request.getParameter("i"))));
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_VIEW_PAYMENT));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void editPayment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("d") != null) {
			request.setAttribute("document",
					CommonStorage.getRepository().getDocument(Long.parseLong(request.getParameter("d"))));
		}
		if (request.getParameter("p") != null) {
			request.setAttribute("project",
					CommonStorage.getRepository().getProject(Long.parseLong(request.getParameter("p"))));
		}
		request.setAttribute("payment",
				CommonStorage.getRepository().getPayment(Long.parseLong(request.getParameter("i"))));
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_EDIT_PAYMENT));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void updatePayment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Payment payment = CommonStorage.getRepository().getPayment(Long.parseLong(request.getParameter("i")));
		try {
			payment.setDocument(
					CommonStorage.getRepository().getDocument(Long.parseLong(request.getParameter("document"))));
			payment.setLotNo(Integer.parseInt(request.getParameter("lotNo")));
			payment.setName(request.getParameter("name"));
			payment.setAmount(Double.parseDouble(request.getParameter("amount")));
			payment.isRestricted(false);
			if (request.getParameter("restricted") != null
					&& request.getParameter("restricted").trim().equalsIgnoreCase("yes")) {
				payment.isRestricted(true);
			}
			payment.setRemark(request.getParameter("remark"));
			payment.setRegisteredBy(CommonStorage.getCurrentUser(request));
			payment.setRegisteredOn(new Timestamp(Instant.now().toEpochMilli()));
			if (payment.valideForUpdate()) {
				CommonStorage.getRepository().update(payment);
				CommonTasks.writeMessage(request, "Sucessful", Message.MESSEGE_TYPE_SUCCESS,
						"Payment Updated Successful");
				response.sendRedirect(request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_PAYMENTS
						+ "&d=" + payment.getDocument().getId());
			} else {
				CommonTasks.writeMessage(request, "Validation failed", Message.MESSEGE_TYPE_ERROR,
						payment.getValidationMessage().toArray(new String[0]));
				CommonTasks.logMessage("Validation failed", Message.MESSEGE_TYPE_ERROR,
						payment.getValidationMessage().toArray(new String[0]));
				request.setAttribute("payment", payment);
				editPayment(request, response);
			}
		} catch (NumberFormatException | IOException | ServletException ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Something went wrong while tring to register new payment to database from "
							+ request.getRemoteHost());
			CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR,
					"Something went wrong, the administrator is notified of the problem.",
					"Please try things again and if the problem persist, try after a while");
			request.setAttribute("payment", payment);
			editPayment(request, response);
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Something went wrong while tring to register new payment to database from "
							+ request.getRemoteHost());
			CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR,
					"Something went wrong, the administrator is notified of the problem.",
					"Please try things again and if the problem persist, try after a while");
			request.setAttribute("payment", payment);
			editPayment(request, response);
		}
	}

	protected static void deletePayments(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] ids = request.getParameterValues("i");
		long paymentId;
		String[] details = new String[0];
		if (ids == null || ids.length < 1) {
			CommonTasks.writeMessage(request, "No payment selected", Message.MESSEGE_TYPE_ERROR,
					"You have not seleceted any payment for this action");
		} else {
			details = new String[ids.length];
			for (int i = 0; i < ids.length; i++) {
				try {
					paymentId = Long.parseLong(ids[i]);
				} catch (Exception ex) {
					CommonTasks.logMessage("Wrong parameter", Message.MESSEGE_TYPE_ERROR, ex,
							"Wrong paramter passed to delete payment " + ids[i]);
					continue;
				}
				String name = CommonStorage.getRepository().deletePayment(paymentId);
				if (name != null) {
					details[i] = "Payment for " + name + " successfully removed";
				}
			}
		}
		CommonTasks.writeMessage(request, "Action Performed Successfully", Message.MESSEGE_TYPE_SUCCESS, details);
		response.sendRedirect(request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_PAYMENTS);
	}
	
	protected static void claims(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("json") == null) {
			claimsListTemplate(request, response);
		} else {
			claimsListData(request, response);
		}
	}

	protected static void claimsListTemplate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_CLAIMS_LIST));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void claimsListData(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ClaimListDTO claims = new ClaimListDTO();
		ClaimParamModel param = (ClaimParamModel)DataTablesParamUtility.getParam(request,new ClaimParamModel());
		
		claims.setDraw(request.getParameter("draw"));
		claims.setEcho(param.sEcho);
		claims.setJQueryDataTableParamModel(param);
		claims.setTotalNumberOfRecords(CommonStorage.getRepository().getClaimCount(param)%1000);
		claims.setData(CommonStorage.getRepository().getAllClaims(param));
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(claims.toJSON());
		
	}
	
	protected static void addClaim(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_ADD_CLAIM));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void saveClaim(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Claim claim = new Claim();
		try {
			List<FileItem> list = (List<FileItem>) request.getAttribute("items");
			for (int i = 0; i < list.size(); i++) {
				FileItem thisItem = list.get(i);
				if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("arrivalDate")) {
					claim.setArrivalDate(
							CommonTasks.parseDate(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8")));
				} else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("claimNumber")) {
					claim.setClaimNumber(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"));
				} else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("claimCNNumber")) {
					claim.setClaimCNNumber(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"));
				} else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("payingBranch")) {
					claim.setPayingBranch(CommonStorage.getRepository().getBranch(
							Long.parseLong(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"))));
				} else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("client")) {
					claim.setClient(CommonStorage.getRepository().getClient(
							Long.parseLong(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"))));
				} else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("amount")) {
					claim.setAmount(
							Double.parseDouble(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8")));
				} else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("claimCount")) {
					claim.setCount(Integer.parseInt(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8")));
				} else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("claimDate")) {
					claim.setClaimDate(
							CommonTasks.parseDate(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8")));
				} else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("remark")) {
					claim.setRemark(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"));
				} else if (thisItem.getFieldName().equalsIgnoreCase("scannedDocument")
						&& thisItem.getName().contains(".")) {
					String fileExtension = thisItem.getName().substring(thisItem.getName().lastIndexOf('.'));
					claim.setScannedDocument(thisItem.get());
					claim.setScannedDocumentFormat(fileExtension);
				}
			}
			claim.setRegisteredBy(CommonStorage.getCurrentUser(request));
			claim.setRegisteredOn(new Timestamp(Instant.now().toEpochMilli()));
			claim.setStatus(Constants.STATUS_ACTIVE);

			if (claim.valideForSave()) {
				CommonStorage.getRepository().save(claim);
				CommonTasks.writeMessage(request, "Sucessful", Message.MESSEGE_TYPE_SUCCESS,
						"Claim Document registration successful");
				request.setAttribute("document", claim);
				response.getOutputStream().println("Docunment Saved Successfully");
			} else {
				response.getOutputStream().println(claim.getValidationMessage().toArray(new String[0])[0]);
			}

			/*
			 * if (claim.valideForSave()) { CommonStorage.getRepository().save(claim);
			 * CommonTasks.writeMessage(request, "Sucessful", Message.MESSEGE_TYPE_SUCCESS,
			 * "Claim registration successful");
			 * response.sendRedirect(request.getContextPath() + "/DataEntry?a=" +
			 * Constants.ACTION_DATAENTRY_ADD_CLAIM); } else {
			 * CommonTasks.writeMessage(request, "Validation failed",
			 * Message.MESSEGE_TYPE_ERROR, claim.getValidationMessage().toArray(new
			 * String[0])); CommonTasks.logMessage("Validation failed",
			 * Message.MESSEGE_TYPE_ERROR, claim.getValidationMessage().toArray(new
			 * String[0])); request.setAttribute("claim", claim); addClaim(request,
			 * response); }
			 */
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Something went wrong while tring to register new claim to database from "
							+ request.getRemoteHost());
			CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR,
					"Something went wrong, the administrator is notified of the problem.",
					"Please try things again and if the problem persist, try after a while");
			request.setAttribute("claim", claim);
			addClaim(request, response);
		}
	}

	protected static void viewClaim(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("claim",
				CommonStorage.getRepository().getClaim(Long.parseLong(request.getParameter("i"))));
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_VIEW_CLAIM));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void editClaim(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("claim",
				CommonStorage.getRepository().getClaim(Long.parseLong(request.getParameter("i"))));
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_EDIT_CLAIM));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void updateClaim(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Claim claim = CommonStorage.getRepository().getClaim(Long.parseLong(request.getAttribute("i").toString()));
		try {
			List<FileItem> list = (List<FileItem>) request.getAttribute("items");
			for (int i = 0; i < list.size(); i++) {
				FileItem thisItem = list.get(i);
				if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("arrivalDate")) {
					claim.setArrivalDate(
							CommonTasks.parseDate(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8")));
				} else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("claimNumber")) {
					claim.setClaimNumber(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"));
				} else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("claimCNNumber")) {
					claim.setClaimCNNumber(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"));
				} else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("payingBranch")) {
					claim.setPayingBranch(CommonStorage.getRepository().getBranch(
							Long.parseLong(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"))));
				} else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("client")) {
					claim.setClient(CommonStorage.getRepository().getClient(
							Long.parseLong(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"))));
				} else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("amount")) {
					claim.setAmount(
							Double.parseDouble(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8")));
				} else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("claimCount")) {
					claim.setCount(Integer.parseInt(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8")));
				} else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("claimDate")) {
					claim.setClaimDate(
							CommonTasks.parseDate(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8")));
				} else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("remark")) {
					claim.setRemark(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8"));
				} else if (thisItem.getFieldName().equalsIgnoreCase("scannedDocument")
						&& thisItem.getName().contains(".")) {
					String fileExtension = thisItem.getName().substring(thisItem.getName().lastIndexOf('.'));
					claim.setScannedDocument(thisItem.get());
					claim.setScannedDocumentFormat(fileExtension);
				}
			}
			if (claim.valideForUpdate()) {
				CommonStorage.getRepository().update(claim);
				CommonTasks.writeMessage(request, "Sucessful", Message.MESSEGE_TYPE_SUCCESS,
						"Updating Claim Successful");
				response.sendRedirect(request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_CLAIMS);
			} else {
				CommonTasks.writeMessage(request, "Validation failed", Message.MESSEGE_TYPE_ERROR,
						claim.getValidationMessage().toArray(new String[0]));
				CommonTasks.logMessage("Validation failed", Message.MESSEGE_TYPE_ERROR,
						claim.getValidationMessage().toArray(new String[0]));
				request.setAttribute("claim", claim);
				editClaim(request, response);
			}
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Something went wrong while tring to updating claim to database from " + request.getRemoteHost());
			CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR,
					"Something went wrong, the administrator is notified of the problem.",
					"Please try things again and if the problem persist, try after a while");
			request.setAttribute("claim", claim);
			editClaim(request, response);
		}
	}

	protected static void deleteClaims(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] ids = request.getParameterValues("i");
		long claimId;
		String[] details = new String[0];
		if (ids == null || ids.length < 1) {
			CommonTasks.writeMessage(request, "No claim selected", Message.MESSEGE_TYPE_ERROR,
					"You have not seleceted any claim for this action");
		} else {
			details = new String[ids.length];
			for (int i = 0; i < ids.length; i++) {
				try {
					claimId = Long.parseLong(ids[i]);
				} catch (Exception ex) {
					CommonTasks.logMessage("Wrong parameter", Message.MESSEGE_TYPE_ERROR, ex,
							"Wrong paramter passed to delete claim " + ids[i]);
					continue;
				}
				String docNumber = CommonStorage.getRepository().deleteClaim(claimId);
				if (docNumber != null) {
					details[i] = "Claim for " + docNumber + " successfully removed";
				}
			}
		}
		CommonTasks.writeMessage(request, "Action Performed Successfully", Message.MESSEGE_TYPE_SUCCESS, details);
		response.sendRedirect(request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_CLAIMS);
	}

	protected static void getClaimAttachment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		OutputStream out = response.getOutputStream();
		long id = Long.parseLong(request.getParameter("i"));
		Claim claim = CommonStorage.getRepository().getClaim(id);
		if (claim.getScannedDocument() == null) {
			// response.sendRedirect(request.getContextPath() + "/" +
			// CommonStorage.getDefaultDocue());
		} else {
			// response.setContentType("pdf/*");
			response.setHeader("Content-Disposition",
					"attachment; filename=scanned-document-" + claim.getId() + "." + claim.getScannedDocumentFormat());
			out.write(claim.getScannedDocument());
			out.flush();
			out.close();
		}
	}

	protected static void claimDetails(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("json") == null) {
			claimDetailsTemplate(request, response);
		} else {
			claimDetailsData(request, response);
		}
		
	}

	protected static void claimDetailsTemplate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_CLAIM_DETAILS));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void claimDetailsData(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ClaimDetailDTO claimDetails = new ClaimDetailDTO();
		ClaimDetailParamModel param = (ClaimDetailParamModel)DataTablesParamUtility.getParam(request,new ClaimDetailParamModel());
		
		if (request.getParameter("i") != null) {
			param.setClaimId(Long.parseLong(request.getParameter("i")));
		}
		if (request.getParameter("c") != null) {
			param.setClaimId(Long.parseLong(request.getParameter("c")));
		}
		if (request.getParameter("cl") != null) {
			param.setClaimId(Long.parseLong(request.getParameter("cl")));
		}

		claimDetails.setDraw(request.getParameter("draw"));
		claimDetails.setEcho(param.sEcho);
		claimDetails.setClaimDetailParamModel(param);
		claimDetails.setTotalNumberOfRecords(CommonStorage.getRepository().getClaimDetailCount(param)%1000);
		claimDetails.setData(CommonStorage.getRepository().getAllClaimDetails(param));
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(claimDetails.toJSON());
		
	}
	protected static void addClaimDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("data") == null) {
		PaymentQueryParameter paymentParam = new PaymentQueryParameter();
		Claim claim = new Claim();
		if (request.getParameter("cl") != null) {
			request.setAttribute("claimId", Long.parseLong(request.getParameter("cl")));
			claim = CommonStorage.getRepository().getClaim(Long.parseLong(request.getParameter("cl")));
			request.setAttribute("claim", claim);
		}
		if (request.getParameter("p") != null) {
			request.setAttribute("payment",
			CommonStorage.getRepository().getPayment(Long.parseLong(request.getParameter("p"))));
		}
		paymentParam.setBranchId(claim.getPayingBranchId());
		paymentParam.addStatus(Constants.STATUS_ACTIVE);
		paymentParam.addStatus(Constants.STATUS_PARTIALLY_PAID);
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_ADD_CLAIM_DETAIL));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
		}else {
			paymentsForClaimDetail(request,response);
		}
	}
	
	protected static void paymentsForClaimDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PaymentQueryParameter paymentParam = new PaymentQueryParameter();
		Claim claim = new Claim();
		if (request.getParameter("cl") != null) {
			claim = CommonStorage.getRepository().getClaim(Long.parseLong(request.getParameter("cl")));
		}
		paymentParam.setBranchId(claim.getPayingBranchId());
		paymentParam.addStatus(Constants.STATUS_ACTIVE);
		paymentParam.addStatus(Constants.STATUS_PARTIALLY_PAID);
		ArrayList<Payment> payments = CommonStorage.getRepository().getAllPayments(paymentParam);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(CommonTasks.toJson(payments));
		
	}
	protected static void saveClaimDetailX(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ClaimDetail claimDetail = new ClaimDetail();
		Payment payment = CommonStorage.getRepository().getPayment(Long.parseLong(request.getParameter("payment")));
		Claim claim = CommonStorage.getRepository().getClaim(Long.parseLong(request.getParameter("document")));
		try {
			claimDetail.setClaim(claim);
			claimDetail.setAmount(Double.parseDouble(request.getParameter("amount")));
			claimDetail.setPaidBy(request.getParameter("paidBy"));
			claimDetail.setPaidOn(CommonTasks.parseDate(request.getParameter("paidOn")));
			claimDetail.setPayment(payment);
			claimDetail.setRemark(request.getParameter("remark"));
			claimDetail.setRegisteredBy(CommonStorage.getCurrentUser(request));
			claimDetail.setRegisteredOn(new Timestamp(Instant.now().toEpochMilli()));
			claimDetail.setStatus(Constants.STATUS_ACTIVE);

			if (claimDetail.valideForSave()) {
				CommonStorage.getRepository().save(claimDetail);
				CommonTasks.writeMessage(request, "Sucessful", Message.MESSEGE_TYPE_SUCCESS,
						"Claim Detail Posting Successful");
				payment = CommonStorage.getRepository().getPayment(Long.parseLong(request.getParameter("payment")));
				if (payment.getRemainingAmount() == 0) {
					payment.setStatus(Constants.STATUS_FULLY_PAID);
					CommonStorage.getRepository().update(payment);
				} else if (payment.getAmount() == payment.getRemainingAmount()) {
					payment.setStatus(Constants.STATUS_ACTIVE);
					CommonStorage.getRepository().update(payment);
				} else {
					payment.setStatus(Constants.STATUS_PARTIALLY_PAID);
					CommonStorage.getRepository().update(payment);
				}
				if (request.getParameter("finish") != null
						&& request.getParameter("finish").trim().equalsIgnoreCase("yes")) {
					response.sendRedirect(request.getContextPath() + "/DataEntry?a="
							+ Constants.ACTION_DATAENTRY_CLAIM_DETAILS + "&i=" + claim.getId());
				} else {
					response.sendRedirect(request.getContextPath() + "/DataEntry?a="
							+ Constants.ACTION_DATAENTRY_ADD_CLAIM_DETAIL + "&cl=" + claim.getId());
				}

			} else {
				CommonTasks.writeMessage(request, "Validation failed", Message.MESSEGE_TYPE_ERROR,
						claimDetail.getValidationMessage().toArray(new String[0]));
				CommonTasks.logMessage("Validation failed", Message.MESSEGE_TYPE_ERROR,
						claimDetail.getValidationMessage().toArray(new String[0]));
				request.setAttribute("claimDetail", claimDetail);
				addClaimDetail(request, response);
			}
		} catch (NumberFormatException | IOException | ServletException ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Something went wrong while tring to post new claim to database from " + request.getRemoteHost());
			CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR,
					"Something went wrong, the administrator is notified of the problem.",
					"Please try things again and if the problem persist, try after a while");
			request.setAttribute("claimDetail", claimDetail);
			addClaimDetail(request, response);
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Something went wrong while tring to post new claim to database from " + request.getRemoteHost());
			CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR,
					"Something went wrong, the administrator is notified of the problem.",
					"Please try things again and if the problem persist, try after a while");
			request.setAttribute("claimDetail", claimDetail);
			addClaimDetail(request, response);
		}
	}
	protected static void saveClaimDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ClaimDetail claimDetail = new ClaimDetail();
		Payment payment = CommonStorage.getRepository().getPayment(Long.parseLong(request.getParameter("payment")));
		Claim claim = CommonStorage.getRepository().getClaim(Long.parseLong(request.getParameter("document")));
		try {
			claimDetail.setClaim(claim);
			claimDetail.setAmount(Double.parseDouble(request.getParameter("amount")));
			claimDetail.setPaidBy(request.getParameter("paidBy"));
			claimDetail.setPaidOn(CommonTasks.parseDate(request.getParameter("paidOn")));
			claimDetail.setPayment(payment);
			claimDetail.setRemark(request.getParameter("remark"));
			claimDetail.setRegisteredBy(CommonStorage.getCurrentUser(request));
			claimDetail.setRegisteredOn(new Timestamp(Instant.now().toEpochMilli()));
			claimDetail.setStatus(Constants.STATUS_ACTIVE);

			if (claimDetail.valideForSave()) {
				CommonStorage.getRepository().save(claimDetail);
				payment = CommonStorage.getRepository().getPayment(Long.parseLong(request.getParameter("payment")));
				if (payment.getRemainingAmount() == 0) {
					payment.setStatus(Constants.STATUS_FULLY_PAID);
					CommonStorage.getRepository().update(payment);
				} else if (payment.getAmount() == payment.getRemainingAmount()) {
					payment.setStatus(Constants.STATUS_ACTIVE);
					CommonStorage.getRepository().update(payment);
				} else {
					payment.setStatus(Constants.STATUS_PARTIALLY_PAID);
					CommonStorage.getRepository().update(payment);
				}
				response.getOutputStream().println("Claim Detail Posting Successful");
			} else {
				response.getOutputStream().println(claimDetail.getValidationMessage().toArray(new String[0])[0]);
			}
		} catch (NumberFormatException | IOException | ServletException ex) {
			response.getOutputStream().println(
					"Something went wrong, the administrator is notified of the problem, Please try things again and if the problem persist, try after a while");
		} catch (Exception ex) {
			response.getOutputStream().println(
					"Something went wrong, the administrator is notified of the problem, Please try things again and if the problem persist, try after a while");
		}
	}

	protected static void viewClaimDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("claimDetail",
				CommonStorage.getRepository().getClaimDetail(Long.parseLong(request.getParameter("i"))));
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_VIEW_CLAIM_DETAIL));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void editClaimDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*ClaimDetail claimDetail = CommonStorage.getRepository()
				.getClaimDetail(Long.parseLong(request.getParameter("i")));
		Claim claim = claimDetail.getClaim();
		request.setAttribute("claimDetail", claimDetail);
		PaymentQueryParameter parameter = new PaymentQueryParameter();
		parameter.filterByRestriction(true);
		parameter.isRestricted(false);
		parameter.addStatus(Constants.STATUS_FULLY_PAID);
		parameter.addStatus(Constants.STATUS_ACTIVE);
		parameter.addStatus(Constants.STATUS_PARTIALLY_PAID);
		CommonStorage.getRepository().setQueryParamters(parameter);
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_EDIT_CLAIM_DETAIL));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);*/
	}

	protected static void updateClaimDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ClaimDetail claimDetail = CommonStorage.getRepository()
				.getClaimDetail(Long.parseLong(request.getParameter("i")));
		Claim claim = claimDetail.getClaim();
		try {
			claimDetail.setClaim(claim);
			claimDetail.setAmount(Double.parseDouble(request.getParameter("amount")));
			claimDetail.setPaidBy(request.getParameter("paidBy"));
			claimDetail.setPaidOn(CommonTasks.parseDate(request.getParameter("paidOn")));
			claimDetail.setPayment(
					CommonStorage.getRepository().getPayment(Long.parseLong(request.getParameter("payment"))));
			claimDetail.setRemark(request.getParameter("remark"));
			if (claimDetail.valideForUpdate()) {
				CommonStorage.getRepository().update(claimDetail);
				CommonTasks.writeMessage(request, "Sucessful", Message.MESSEGE_TYPE_SUCCESS,
						"Claim Detail Updated Successful");
				Payment payment = CommonStorage.getRepository().getPayment(claimDetail.getPayment().getId());
				if (payment.getRemainingAmount() == 0) {
					payment.setStatus(Constants.STATUS_FULLY_PAID);
				} else if (payment.getAmount() == payment.getRemainingAmount()) {
					payment.setStatus(Constants.STATUS_ACTIVE);
				} else {
					payment.setStatus(Constants.STATUS_PARTIALLY_PAID);
				}
				CommonStorage.getRepository().update(payment);
				response.sendRedirect(request.getContextPath() + "/DataEntry?a="
						+ Constants.ACTION_DATAENTRY_VIEW_CLAIM_DETAIL + "&i=" + claimDetail.getId());
			} else {
				CommonTasks.writeMessage(request, "Validation failed", Message.MESSEGE_TYPE_ERROR,
						claimDetail.getValidationMessage().toArray(new String[0]));
				CommonTasks.logMessage("Validation failed", Message.MESSEGE_TYPE_ERROR,
						claimDetail.getValidationMessage().toArray(new String[0]));
				request.setAttribute("claimDetail", claimDetail);
				editClaimDetail(request, response);
			}
		} catch (NumberFormatException | IOException | ServletException ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Something went wrong while tring to update claim detail to database from "
							+ request.getRemoteHost());
			CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR,
					"Something went wrong, the administrator is notified of the problem.",
					"Please try things again and if the problem persist, try after a while");
			request.setAttribute("claimDetail", claimDetail);
			editClaimDetail(request, response);
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Something went wrong while tring to update claim detail to database from "
							+ request.getRemoteHost());
			CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR,
					"Something went wrong, the administrator is notified of the problem.",
					"Please try things again and if the problem persist, try after a while");
			request.setAttribute("claimDetail", claimDetail);
			editClaimDetail(request, response);
		}
	}

	protected static void deleteClaimDetails(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] ids = request.getParameterValues("i");
		long claimDetailId;
		ClaimDetail claimDetail = new ClaimDetail();
		String[] details = new String[0];
		if (ids == null || ids.length < 1) {
			CommonTasks.writeMessage(request, "No claim selected", Message.MESSEGE_TYPE_ERROR,
					"You have not seleceted any claim details for this action");
		} else {
			claimDetail = CommonStorage.getRepository().getClaimDetail(Long.parseLong(ids[0]));
			details = new String[ids.length];
			for (int i = 0; i < ids.length; i++) {
				try {
					claimDetailId = Long.parseLong(ids[i]);
					claimDetail = CommonStorage.getRepository().getClaimDetail(claimDetailId);
				} catch (Exception ex) {
					CommonTasks.logMessage("Wrong parameter", Message.MESSEGE_TYPE_ERROR, ex,
							"Wrong paramter passed to delete claim details" + ids[i]);
					continue;
				}

				String amount = CommonStorage.getRepository().deleteClaimDetail(claimDetailId);
				try {
					if (amount != null) {
						Payment payment = CommonStorage.getRepository().getPayment(claimDetail.getPayment().getId());
						if (payment.getRemainingAmount() == 0) {
							payment.setStatus(Constants.STATUS_FULLY_PAID);
							CommonStorage.getRepository().update(payment);
						} else if (payment.getAmount() == payment.getRemainingAmount()) {
							payment.setStatus(Constants.STATUS_ACTIVE);
							CommonStorage.getRepository().update(payment);
						} else {
							payment.setStatus(Constants.STATUS_PARTIALLY_PAID);
							CommonStorage.getRepository().update(payment);
						}
						details[i] = "Claim Details for " + amount + " successfully removed";

					}
				} catch (Exception e) {
					CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, e,
							"Something went wrong while tring delete the claim detail from database from client "
									+ request.getRemoteHost());
					CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR,
							"Something went wrong, the administrator is notified of the problem.",
							"Please try things again and if the problem persist, try after a while");
				}
			}
		}
		CommonTasks.writeMessage(request, "Action Performed Successfully", Message.MESSEGE_TYPE_SUCCESS, details);
		response.sendRedirect(request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_CLAIM_DETAILS
				+ "&cl=" + claimDetail.getClaim().getId());
	}

	protected static void amendments(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("amendments", CommonStorage.getRepository().getAllAmendments());
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_AMENDMENTS));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void addAmendment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("p") != null) {
			request.setAttribute("payment",
					CommonStorage.getRepository().getPayment(Long.parseLong(request.getParameter("p"))));
		}
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_ADD_AMENDMENT));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void saveAmendment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Amendment amendment = new Amendment();
		Payment payment = CommonStorage.getRepository().getPayment(Long.parseLong(request.getParameter("payment")));
		try {
			amendment.setAmount(Double.parseDouble(request.getParameter("amount")));
			amendment.setIncomingDate(CommonTasks.parseDate(request.getParameter("incomingDate")));
			amendment.setIncomingDocumentNo(request.getParameter("incomingDocumentNo"));
			amendment.setOutgoingDate(CommonTasks.parseDate(request.getParameter("outgoingDate")));
			amendment.setOutgoingDocumentNo(request.getParameter("outgoingDocumentNo"));
			amendment.setPayment(payment);
			amendment.setRemark(request.getParameter("remark"));
			amendment.setRegisteredBy(CommonStorage.getCurrentUser(request));
			amendment.setRegisteredOn(new Timestamp(Instant.now().toEpochMilli()));
			amendment.setStatus(Constants.STATUS_ACTIVE);

			if (amendment.valideForSave()) {
				CommonStorage.getRepository().save(amendment);
				CommonTasks.writeMessage(request, "Sucessful", Message.MESSEGE_TYPE_SUCCESS,
						"Amendment Saved Successfully");
				payment.setAmount(payment.getAmount() + amendment.getAmount());
				payment.setRemark(payment.getRemark() + " ; " + "Amended with" + amendment.getIncomingDocumentNo()
						+ " on " + amendment.getRegisteredOn() + " for amount of " + amendment.getAmount());
				CommonStorage.getRepository().update(payment);
				response.sendRedirect(
						request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_AMENDMENTS);
			} else {
				CommonTasks.writeMessage(request, "Validation failed", Message.MESSEGE_TYPE_ERROR,
						amendment.getValidationMessage().toArray(new String[0]));
				CommonTasks.logMessage("Validation failed", Message.MESSEGE_TYPE_ERROR,
						amendment.getValidationMessage().toArray(new String[0]));
				request.setAttribute("amendment", amendment);
				addAmendment(request, response);
			}
		} catch (NumberFormatException | IOException | ServletException ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Something went wrong while tring save the new amendment to database from "
							+ request.getRemoteHost());
			CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR,
					"Something went wrong, the administrator is notified of the problem.",
					"Please try things again and if the problem persist, try after a while");
			request.setAttribute("amendment", amendment);
			addAmendment(request, response);
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Something went wrong while tring save the new amendment to database from "
							+ request.getRemoteHost());
			CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR,
					"Something went wrong, the administrator is notified of the problem.",
					"Please try things again and if the problem persist, try after a while");
			request.setAttribute("amendment", amendment);
			addAmendment(request, response);
		}
	}

	protected static void viewAmendment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("claimDetail",
				CommonStorage.getRepository().getClaimDetail(Long.parseLong(request.getParameter("i"))));
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_VIEW_CLAIM_DETAIL));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void editAmendment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("claimDetail",
				CommonStorage.getRepository().getClaimDetail(Long.parseLong(request.getParameter("i"))));
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_EDIT_CLAIM_DETAIL));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void updateAmendment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ClaimDetail claimDetail = CommonStorage.getRepository()
				.getClaimDetail(Long.parseLong(request.getParameter("i")));
		Claim claim = claimDetail.getClaim();
		try {
			claimDetail.setClaim(claim);
			claimDetail.setAmount(Double.parseDouble(request.getParameter("amount")));
			claimDetail.setPaidBy(request.getParameter("paidBy"));
			claimDetail.setPaidOn(CommonTasks.parseDate(request.getParameter("paidOn")));
			claimDetail.setPayment(
					CommonStorage.getRepository().getPayment(Long.parseLong(request.getParameter("payment"))));
			claimDetail.setRemark(request.getParameter("remark"));
			if (claimDetail.valideForUpdate()) {
				CommonStorage.getRepository().update(claimDetail);
				CommonTasks.writeMessage(request, "Sucessful", Message.MESSEGE_TYPE_SUCCESS,
						"Claim Detail Updated Successful");
				response.sendRedirect(request.getContextPath() + "/DataEntry?a="
						+ Constants.ACTION_DATAENTRY_VIEW_CLAIM_DETAIL + "&i=" + claim.getId());
			} else {
				CommonTasks.writeMessage(request, "Validation failed", Message.MESSEGE_TYPE_ERROR,
						claimDetail.getValidationMessage().toArray(new String[0]));
				CommonTasks.logMessage("Validation failed", Message.MESSEGE_TYPE_ERROR,
						claimDetail.getValidationMessage().toArray(new String[0]));
				request.setAttribute("claimDetail", claimDetail);
				editClaimDetail(request, response);
			}
		} catch (NumberFormatException | IOException | ServletException ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Something went wrong while tring to update claim detail to database from "
							+ request.getRemoteHost());
			CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR,
					"Something went wrong, the administrator is notified of the problem.",
					"Please try things again and if the problem persist, try after a while");
			request.setAttribute("claimDetail", claimDetail);
			editClaimDetail(request, response);
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Something went wrong while tring to update claim detail to database from "
							+ request.getRemoteHost());
			CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR,
					"Something went wrong, the administrator is notified of the problem.",
					"Please try things again and if the problem persist, try after a while");
			request.setAttribute("claimDetail", claimDetail);
			editClaimDetail(request, response);
		}
	}

	protected static void deleteAmendments(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] ids = request.getParameterValues("i");
		long claimDetailId;
		String[] details = new String[0];
		if (ids == null || ids.length < 1) {
			CommonTasks.writeMessage(request, "No claim selected", Message.MESSEGE_TYPE_ERROR,
					"You have not seleceted any claim details for this action");
		} else {
			details = new String[ids.length];
			for (int i = 0; i < ids.length; i++) {
				try {
					claimDetailId = Long.parseLong(ids[i]);
				} catch (Exception ex) {
					CommonTasks.logMessage("Wrong parameter", Message.MESSEGE_TYPE_ERROR, ex,
							"Wrong paramter passed to delete claim details" + ids[i]);
					continue;
				}
				String amount = CommonStorage.getRepository().deleteClaimDetail(claimDetailId);
				if (amount != null) {
					details[i] = "Claim Details for " + amount + " successfully removed";
				}
			}
		}
		CommonTasks.writeMessage(request, "Action Performed Successfully", Message.MESSEGE_TYPE_SUCCESS, details);
		response.sendRedirect(request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_CLAIMS);
	}

	protected static void addImport(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("c") != null) {
			request.setAttribute("clientId", Long.parseLong(request.getParameter("c")));
		}
		request.setAttribute("page", CommonStorage.getPage(Constants.INDEX_DATAENTRY_ADD_IMPORT));
		RequestDispatcher rd = request.getRequestDispatcher(CommonStorage.LOGGED_IN_INDEX_PAGE);
		rd.forward(request, response);
	}

	protected static void saveImport(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ClientRegion clientRegion = new ClientRegion();
		Branch branch = new Branch();
		byte[] document = new byte[0];
		try {
			List<FileItem> list = (List<FileItem>) request.getAttribute("items");
			for (int i = 0; i < list.size(); i++) {
				FileItem thisItem = list.get(i);
				if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("payingBranch")) {
					branch = CommonStorage.getRepository().getBranch(
							Long.parseLong(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8")));
				} else if (thisItem.isFormField() && thisItem.getFieldName().equalsIgnoreCase("clientRegion")) {
					clientRegion = CommonStorage.getRepository().getClientRegion(
							Long.parseLong(new String(thisItem.getString().getBytes("iso-8859-1"), "UTF-8")));
				} else if (thisItem.getFieldName().equalsIgnoreCase("document") && thisItem.getName().contains(".")) {
					document = thisItem.get();
				}
			}
			int count = ImportUtil.importData(request, clientRegion, branch, document);
			CommonTasks.writeMessage(request, "Sucessful", Message.MESSEGE_TYPE_SUCCESS,
					"Document imported successfully with " + count + " payments");
			response.sendRedirect(request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_ADD_IMPORT);
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Something went wrong while tring to register new document to database from "
							+ request.getRemoteHost());
			CommonTasks.writeMessage(request, "Internal Error", Message.MESSEGE_TYPE_ERROR,
					"Something went wrong, the administrator is notified of the problem.",
					"Please try things again and if the problem persist, try after a while");
			request.setAttribute("document", document);
			addDocument(request, response);
		}
	}

}
