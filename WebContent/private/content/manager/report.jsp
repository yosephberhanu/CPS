<%@page import="et.artisan.cn.cps.dto.ClientRegionsParamModel"%>
<%@page import="et.artisan.cn.cps.dto.ClaimParamModel"%>
<%@page import="et.artisan.cn.cps.dto.BranchesParamModel"%>
<%@page import="et.artisan.cn.cps.dto.JQueryDataTableParamModel"%>
<%@page import="et.artisan.cn.cps.dto.CurrentUserDTO"%>
<%@page import="et.artisan.cn.cps.entity.*"%>
<%@page import="et.artisan.cn.cps.util.*"%>
<%@page import="et.artisan.cn.cps.dao.MasterRepository"%>
<%@page import="et.artisan.cn.cps.util.CommonStorage"%>
<%@page import="et.artisan.cn.cps.entity.*"%>
<%@page import="java.util.ArrayList"%>
<%
    User approver = new User();
    if (request.getAttribute("approver") != null) {
        approver = (User) request.getAttribute("approver");
    }
    ReportType reportType = new ReportType();
    if (request.getAttribute("reportType") != null) {
        reportType = (ReportType) request.getAttribute("reportType");
    }
    long documentId = 0;
    if (request.getAttribute("documentId") != null) {
        documentId = (Long) request.getAttribute("documentId");
    }
    long clientId = 0;
    if (request.getAttribute("clientId") != null) {
        clientId = (Long) request.getAttribute("clientId");
    }
    long clientRegionId = 0;
    if (request.getAttribute("clientRegionId") != null) {
        clientRegionId = (Long) request.getAttribute("clientRegionId");
    }
    long branchId = 0;
    if (request.getAttribute("branchId") != null) {
        branchId = (Long) request.getAttribute("branchId");
    }
    int branchType = 0;
    if (request.getAttribute("branchType") != null) {
        branchType = (Integer) request.getAttribute("branchType");
    }
    long projectId = 0;
    if (request.getAttribute("projectId") != null) {
        projectId = (Long) request.getAttribute("projectId");
    }
    long claimId = 0;
    if (request.getAttribute("claimId") != null) {
        claimId = (Long) request.getAttribute("claimId");

    }
    String claimCNNumber = "";
    if (request.getAttribute("claimCNNumber") != null) {
        claimCNNumber = request.getAttribute("claimCNNumber").toString();

    }
    if (claimId > 0) {
        clientId = CommonStorage.getRepository().getClaim(claimId).getClient().getId();
    }
%>
<div class="col-lg-8 col-md-8 col-sm-8 col-xs-12 col-lg-offset-2 col-md-offset-2 ">
    <div class="x_panel">
        <div class="x_title">
            <h2>Export Report</h2>
            <div class="clearfix"></div>
        </div>
        <div class="x_content">
            <br>
            <form class="form-horizontal form-label-left input_mask" novalidate action="<%=request.getContextPath() + "/Manager"%>" id="reportForm" method="POST" >
                <input type="hidden" name="a" value="<%=Constants.ACTION_MANAGER_GENERATE_REPORT%>"/>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-12" for ="reportId">Report <span class="required">*</span></label>
                    <div class="col-md-9 col-sm-9 col-xs-12">
                        <select id="reportType" name="reportType" class="form-control " data-live-search="true" data-value="<%=reportType.getId()%>">
                            <option value="0">Select Report</option>
                            <%
                                ArrayList<ReportType> reportTypes = CommonStorage.getRepository().getAllReportTypes();
                                for (int i = 0; i < reportTypes.size(); i++) {
                                    out.println("<option value='" + reportTypes.get(i).getId() + "'>"
                                            + reportTypes.get(i).getId() + " - " + reportTypes.get(i).getName() + "</option>");
                                }
                            %>
                        </select>
                    </div>
                </div>
                <%if (reportType.isBranchTypeBased()) {%>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">Branch Type<span class="required">*</span></label>
                    <div class="col-md-9 col-sm-9 col-xs-12">
                        <select id="branchType" name="branchType" class="form-control " data-live-search="true"  data-value="<%=branchType%>">
                            <option value="0">Select A Branch Type</option>

                            <%
                                ArrayList<BranchType> brancheTypes = CommonStorage.getRepository().getAllBranchTypes();
                                for (int i = 0; i < brancheTypes.size(); i++) {
                                    out.println("<option value='" + brancheTypes.get(i).getCode() + "'>"
                                            + brancheTypes.get(i).getEnglishName() + "</option>");
                                }
                            %>
                        </select>
                    </div>
                </div>
                <%}%>
                <%if (reportType.isBranchBased()) {%>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">Branch <span class="required">*</span></label>
                    <div class="col-md-9 col-sm-9 col-xs-12">
                        <select id="branch" name="branch" class="form-control " data-live-search="true"  data-value="<%=branchId%>">
                            <option value="0">Select A Branch</option>
                            <%
                                ArrayList<Branch> branches;
                                //if (branchType > 0) {
                                    ///TODO: Filter By branch type
                                //    branches = CommonStorage.getRepository().getAllBranches(new BranchesParamModel());
                                //} else {
                                    branches = CommonStorage.getRepository().getAllBranches(new BranchesParamModel());
                                //}
                                for (int i = 0; i < branches.size(); i++) {
                                    out.println("<option value='" + branches.get(i).getId() + "'>"
                                            + branches.get(i).getName() + "</option>");
                                }
                            %>
                        </select>
                    </div>
                </div>
                <%}%>
                <%if (reportType.isClaimBased()) {%>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">Claim <span class="required">*</span></label>
                    <div class="col-md-9 col-sm-9 col-xs-12">
                        <select id="claim" name="claim" class="form-control " data-live-search="true"  data-value="<%=claimId%>">
                            <option value="0">Select A Claim</option>

                            <%
                                ArrayList<Claim> claims = CommonStorage.getRepository().getAllClaims(new ClaimParamModel());
                                for (int i = 0; i < claims.size(); i++) {
                                    out.println("<option value='" + claims.get(i).getId() + "'>"
                                            + claims.get(i).getClaimNumber() + "</option>");
                                }
                            %>
                        </select>
                    </div>
                </div>
                <%}%>
                <%if (reportType.isClaimCNNoBased()) {%>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">Claim <span class="required">*</span></label>
                    <div class="col-md-9 col-sm-9 col-xs-12">
                        <select id="claimCNNumber" name="claimCNNumber" class="form-control " data-live-search="true" data-value="<%=claimCNNumber%>">
                            <option value="">Select A Claim</option>

                            <%
                                ArrayList<String> claimCNNumbers = CommonStorage.getRepository().getAllClaimCNNumbers();
                                for (int i = 0; i < claimCNNumbers.size(); i++) {
                                    out.println("<option value='" + claimCNNumbers.get(i) + "'>"
                                            + claimCNNumbers.get(i) + "</option>");
                                }
                            %>
                        </select>
                    </div>
                </div>
                <%}%>
                <%if (reportType.isProjectBased()) {%>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">Project <span class="required">*</span></label>
                    <div class="col-md-9 col-sm-9 col-xs-12">
                        <select id="project" name="project" class="form-control " data-live-search="true" data-value="<%=projectId%>">
                            <option value="0">Select A Project</option>
                            <%
                                ArrayList<Project> projects = CommonStorage.getRepository().getAllProjects(new JQueryDataTableParamModel());
                                for (int i = 0; i < projects.size(); i++) {
                                    out.println("<option value='" + projects.get(i).getId() + "'>"
                                            + projects.get(i).getName() + "</option>");
                                }
                            %>
                        </select>
                    </div>
                </div>
                <%}%>
                <%if (reportType.isClientBased()) {%>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">Client <span class="required">*</span></label>
                    <div class="col-md-9 col-sm-9 col-xs-12">
                        <select id="client" name="client" class="form-control " data-live-search="true" data-value="<%=clientId%>">
                            <option value="0">Select A Client</option>
                            <%
                                ArrayList<Client> clients = CommonStorage.getRepository().getAllClients();
                                for (int i = 0; i < clients.size(); i++) {
                                    out.println("<option value='" + clients.get(i).getId() + "'>"
                                            + clients.get(i).getName() + "</option>");
                                }
                            %>
                        </select>
                    </div>
                </div>
                <%}%>

                <%if (reportType.isDocumentBased()) {%>
                <!--div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">Document <span class="required">*</span></label>
                    <div class="col-md-9 col-sm-9 col-xs-12">
                        <select id="document" name="document" class="form-control " data-live-search="true" data-value="<%=documentId%>">
                            <option value="0">Select A Document</option>
                            <--%
                                ArrayList<Document> documents = CommonStorage.getRepository().getAllDocuments();
                                for (int i = 0; i < documents.size(); i++) {
                                    out.println("<option value='" + documents.get(i).getId() + "'>"
                                            + documents.get(i).getProject().getCode() + "/" + documents.get(i).getInComingDocumentNo() + "/" + documents.get(i).getDocumentYear() + "</option>");
                                }
                            %-->
                <!--/select>
            </div>
        </div-->
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">Document ID</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input id="document" name = "document" class="form-control" data-inputmask="'mask': '999/999/999'" type="text">
                        <span class="fa fa-user form-control-feedback right" aria-hidden="true"></span>
                    </div>
                </div>
                <%}%>
                <%if (reportType.isClientRegionBased() && clientId > 0) {%>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">Client Region <span class="required">*</span></label>
                    <div class="col-md-9 col-sm-9 col-xs-12">
                        <select id="clientRegion" name="clientRegion" class="form-control " data-live-search="true" data-value="<%=clientRegionId%>">
                            <option value="0">Select A Client Region</option>
                            <%
                            	ClientRegionsParamModel param = new ClientRegionsParamModel();
                            	param.setClientId(clientId);
                                ArrayList<ClientRegion> clientRegions = CommonStorage.getRepository().getAllClientRegions(param);
                                for (int i = 0; i < clientRegions.size(); i++) {
                                    out.println("<option value='" + clientRegions.get(i).getId() + "'>"
                                            + clientRegions.get(i).getRegionName() + "</option>");
                                }
                            %>
                        </select>
                    </div>
                </div>
                <%}%>
                <%if (reportType.isTimeBased()) {%>
                <div class="form-group">
                    <label class="control-label col-lg-3 col-md-3 col-sm-3 col-xs-12">Time span <span class="required">*</span>
                    </label>
                    <div class="col-lg-4 col-md-5 col-sm-6 col-xs-12">
                        <input class="date-picker form-control col-lg-12 col-md-5 col-xs-12" required="required" type="text" id="from" name="from"/>
                    </div>
                    <div class="col-lg-1 col-md-1 col-sm-0 col-xs-0 " ><label class="control-label"> To</label></div>
                    <div class="col-lg-4  col-md-5 col-sm-6 col-xs-12 ">
                        <input class="date-picker form-control col-lg-12  col-md-5 col-xs-12" required="required" type="text" id="to" name="to"/>
                    </div>
                </div>
                <%}%>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">Approver <span class="required">*</span></label>
                    <div class="col-md-9 col-sm-9 col-xs-12">
                        <select id="approver" name="approver" class="form-control " data-live-search="true"  data-value="<%=approver.getId()%>">
                            <option value="0">Select An Approver</option>

                            <%
                                CurrentUserDTO currentUser = CommonStorage.getCurrentUser(request);
                                ArrayList<User> approvers = CommonStorage.getRepository().getAllApproverUsers();
                                for (int i = 0; i < approvers.size(); i++) {
                                    if (approvers.get(i).getId() == currentUser.getId()) {
                                        continue;
                                    }
                                    out.println("<option value='" + approvers.get(i).getId() + "'>"
                                            + approvers.get(i).getFullName() + "</option>");
                                }
                            %>
                        </select>
                    </div>
                </div>
                <!--div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">Export Format <span class="required">*</span></label>
                    <div class="col-md-9 col-sm-9 col-xs-12">
                        <select id="exportFormat" name="exportFormat" class="form-control">
                <%--
                    ExportType[] exportTypes = CommonStorage.getExportTypes();
                    for (int i = 0; i < exportTypes.length; i++) {
                        out.println("<option value='" + exportTypes[i].getCode() + "'>"
                                + exportTypes[i].getName() + "</option>");
                    }
                --%>
            </select>
        </div>
    </div-->
                <div class="ln_solid"></div>
                <div class="form-group">
                    <div class="col-lg-5 col-md-5 col-sm-5 col-xs-12 col-md-offset-9 col-lg-offset-9">
                        <button type="submit" class="btn btn-success">Generate</button>
                    </div>
                </div>

            </form>
        </div>
    </div>
</div>
<div class="clearfix"></div>
<div class="ln_solid"></div>
<script type="text/javascript">
    $(document).ready(function () {
        $('#reportType').on('change', function () {
            window.location = "<%=request.getContextPath() + "/Manager?a=" + Constants.ACTION_MANAGER_REPORT + "&r="%>" + this.value;
        });
        $('#client').on('change', function () {
            window.location = "<%=request.getContextPath() + "/Manager?a=" + Constants.ACTION_MANAGER_REPORT + "&r="%>" + $("#reportType").val() + "&p=" + $("#project").val() + "&cl=" + $("#claim").val() + "&b=" + $("#branch").val() + "&bt=" + $("#branchType").val() + "&claimCNNumber=" + $("#claimCNNumber").val() + "&c=" + this.value;
        });
        $('#claim').on('change', function () {
            window.location = "<%=request.getContextPath() + "/Manager?a=" + Constants.ACTION_MANAGER_REPORT + "&r="%>" + $("#reportType").val() + "&p=" + $("#project").val() + "&c=" + $("#client").val() + "&b=" + $("#branch").val() + "&bt=" + $("#branchType").val() + "&claimCNNumber=" + $("#claimCNNumber").val() + "&cl=" + this.value;
        });

        thecal = new EthiopianDualCalendar("from");
        thecal = new EthiopianDualCalendar("to");
    });
</script>