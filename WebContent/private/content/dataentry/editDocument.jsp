<%-- 
    Document   : editDocument - DataEntry
    Created on : May 6, 2017, 3:16:45 PM
    Author     : Yoseph Berhanu<yoseph@artisan.et> 
--%>
<%@page import="et.artisan.cn.cps.dto.BranchesParamModel"%>
<%@page import="et.artisan.cn.cps.dto.ClientRegionsParamModel"%>
<%@page import="et.artisan.cn.cps.dto.JQueryDataTableParamModel"%>
<%@page import="et.artisan.cn.cps.entity.*"%>
<%@page import="java.util.HashMap"%>
<%@page import="et.artisan.cn.cps.util.*"%>
<%@page import="java.util.ArrayList"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Document document = (Document) request.getAttribute("document");
    long clientId = document.getClientRegion().getId();
    if (request.getAttribute("clientId") != null) {
        clientId = (Long) request.getAttribute("clientId");
    }
%>
<div class="">

    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Edit Document Details</h2>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <form class="form-horizontal form-label-left" novalidate action="<%=request.getContextPath() + "/DataEntry"%>" id="editDocumentForm" method="POST" enctype="multipart/form-data" >
                        <input type="hidden" name="a" value="<%=Constants.ACTION_DATAENTRY_UPDATE_DOCUMENT%>" />
                        <input type="hidden" name="i" value="<%=document.getId()%>" />
                        <span class="section">Basic Information</span>
                        <div class="col-md-8 col-lg-8">
                            <div class="col-lg-10 col-md-10 col-sm-12">
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="client">Client <span class="required">*</span>
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <select class="form-control" id="client" name="client" data-value="<%=document.getClientRegion().getClient().getId()%>">
                                            <option value="0">Select A Client</option>
                                            <%
                                                ArrayList<Client> clients = CommonStorage.getRepository().getAllClients();
                                                for (int i = 0; i < clients.size(); i++) {
                                                    out.println("<option value='" + clients.get(i).getId() + "'>" + clients.get(i).getName() + "</option>");
                                                }
                                            %>
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="clientRegion">Client Region </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <select class="form-control selectpicker" id="clientRegion" name="clientRegion" data-value="<%=document.getClientRegion().getClient().getId()%>">
                                            <option value="0">Select A Client Region</option>
                                            <%
                                                ArrayList<ClientRegion> clientRegionList = CommonStorage.getRepository().getAllClientRegions(new ClientRegionsParamModel());
                                                for (int i = 0; i < clientRegionList.size(); i++) {
                                                    out.println("<option value='" + clientRegionList.get(i).getId() + "'>" + clientRegionList.get(i).getRegionName() + "</option>");
                                                }
                                            %>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="project">Project </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <select class="form-control selectpicker" id="project" name="project" data-value="<%=document.getProject().getId()%>">
                                            <option value="0">Select A Project</option>
                                            <%
                                                ArrayList<Project> projectsList = CommonStorage.getRepository().getAllProjects(new JQueryDataTableParamModel());
                                                for (int i = 0; i < projectsList.size(); i++) {
                                                    out.println("<option value='" + projectsList.get(i).getId() + "'>" + projectsList.get(i).getCode()+ "  " + projectsList.get(i).getName() + "</option>");
                                                }
                                            %>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="payingBranch">Paying Branch </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <select class="form-control selectpicker" id="project" name="payingBranch" data-value="<%=document.getBranch().getId()%>">>
                                            <option value="0">Select A Paying Branch</option>
                                            <%
                                                ArrayList<Branch> branchsList = CommonStorage.getRepository().getAllBranches(new BranchesParamModel());
                                                for (int i = 0; i < branchsList.size(); i++) {
                                                    out.println("<option value='" + branchsList.get(i).getId() + "'>" + branchsList.get(i).getName() + "</option>");
                                                }
                                            %>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="documentYear">Document Year </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <select class="form-control selectpicker" id="documentYear" name="documentYear" data-value="<%=document.getDocumentYear()%>">
                                            <option value="0">Select A Year</option>
                                            <%
                                                int currentYear = CommonStorage.getCurrentYear();
                                                for (int i = currentYear - 20; i < currentYear + 2; i++) {
                                                    out.println("<option value='" + i + "'>" + i + "</option>");
                                                }
                                            %>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="inComingDocumentNo">In Coming Document #
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="text" name="inComingDocumentNo" id="inComingDocumentNo" class="form-control col-md-7 col-xs-12" placeholder="In Coming Document Refernce Number" value="<%=document.getInComingDocumentNo()%>" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="incomingDate">Incoming Date
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="text" name="incomingDate" id="incomingDate" class="form-control col-md-7 col-xs-12" placeholder="Date of In Coming Letter" value="<%=document.getInComingDate()%>" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="outGoingDocumentNo">Out Going Document #
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="text" name="outGoingDocumentNo" id="outGoingDocumentNo" class="form-control col-md-7 col-xs-12" placeholder="In Outgoing Document Refernce Number" value="<%=document.getOutGoingDocumentNo()%>" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="outGoingDate">Out Going Date
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="text" name="outGoingDate" id="outGoingDate" class="form-control col-md-7 col-xs-12" placeholder="Date of The Outgoing Letter" value="<%=document.getOutGoingDate()%>" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="paymentDue">Payment Due Date
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="text" name="paymentDue" id="paymentDue" class="form-control col-md-7 col-xs-12" placeholder="Last Date the Payment Should be Made" value="<%=document.getPaymentDue()%>" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="totalAmount">Total Amount
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="number" name="totalAmount" id="totalAmount" class="form-control col-md-7 col-xs-12" placeholder="Total Payment Amount" value="<%=document.getTotalAmount()%>" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="scannedDocument">Scanned Document
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <div class="x_content">
                                            <canvas id="previewDiv" style="border-style: outset;border-width: 0.2em"></canvas>
                                            <div class="clearfix"></div>
                                        </div>
                                        <div class="x_footer">
                                            <a id="openDoc" href="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_GET_CLAIM_ATTACHMENT + "&i=" + document.getId()%>" class="btn btn-link col-md-offset-10" target="_blank">Open</a>
                                            <div class="clearfix"></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" ></label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="file" name="scannedDocument" id="scannedDocument" class="form-control col-md-7 col-xs-12" placeholder="Scanned Version of The Document" title="Change"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="remark">Remark
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <textarea class="form-control" id='remark' name='remark' rows="3" placeholder="Additional Remark"></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4 col-lg-4 col-sm-0">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>Help - Document Registration</h2>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="x_content">
                                    <p class="text-justify"> Quid securi etiam tamquam eu fugiat nulla pariatur. Morbi fringilla convallis sapien, id pulvinar odio volutpat. At nos hinc posthac, sitientis piros Afros. Inmensae subtilitatis, obscuris et malesuada fames. Morbi odio eros, volutpat ut pharetra vitae, lobortis sed nibh.</p>
                                    <p class="text-justify"> Morbi fringilla convallis sapien, id pulvinar odio volutpat. Inmensae subtilitatis, obscuris et malesuada fames. Quid securi etiam tamquam eu fugiat nulla pariatur. At nos hinc posthac, sitientis piros Afros. Inmensae subtilitatis, obscuris et malesuada fames. Morbi odio eros, volutpat ut pharetra vitae, lobortis sed nibh.</p>
                                    <p class="text-justify"> Inmensae subtilitatis, obscuris et malesuada fames. Morbi fringilla convallis sapien, id pulvinar odio volutpat.  Morbi fringilla convallis sapien, id pulvinar odio volutpat. Quid securi etiam tamquam eu fugiat nulla pariatur. At nos hinc posthac, sitientis piros Afros. Morbi odio eros, volutpat ut pharetra vitae, lobortis sed nibh.</p>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                        </div>
                        <div class='clearfix'></div>
                        <div class="ln_solid"></div>
                        <div class="form-group row">
                            <div class="col-lg-3 col-md-3 col-sm-3 col-lg-offset-1 col-md-offset-1 col-sm-offset-1">
                                <a href="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_DOCUMENTS%>" class="btn btn-primary">Cancel</a>
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-3 col-lg-offset-3 col-md-offset-3 col-sm-offset-3">
                                <button id="submitBtn" type="submit" class="btn btn-success">Update</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="<%=request.getContextPath()%>/assets/js/validator/validator.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/select/select2.full.js"></script>
<script>
    $(document).ready(function () {
        PDFJS.getDocument('<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_GET_DOCUMENT_ATTACHMENT + "&i=" + document.getId()%>').then(function (pdf) {
            pdf.getPage(1).then(function (page) {
                var scale = 0.4;
                var viewport = page.getViewport(scale);

                var canvas = document.getElementById('previewDiv');
                var context = canvas.getContext('2d');
                canvas.height = viewport.height;
                canvas.width = viewport.width;

                var renderContext = {
                    canvasContext: context,
                    viewport: viewport
                };
                page.render(renderContext);
            });
        });
        message = {
            invalid: 'invalid input',
            empty: 'Please put something here',
            min: 'input is too short',
            max: 'input is too long',
            number_min: 'too low',
            number_max: 'too high',
            url: 'invalid URL',
            number: 'not a number',
            email: 'email address is invalid',
            email_repeat: 'emails do not match',
            password_repeat: 'passwords do not match',
            no_match: 'no match',
            complete: 'input is not complete',
            select: 'Please select an option'
        };
        $('form').submit(function (e) {
            e.preventDefault();
            var submit = true;
            // you can put your own custom validations below

            // check all the required fields
            if (validator.checkAll($(this))) {
                //if ($("#roles :selected").length < 1) {
                //validator.mark($("#roles"), "At least one role is expected");
                //} else if (!usernameValid) {
                //validator.mark($("#username"), "Username already taken");
                //} else {
                this.submit();
                //}

            }
            return false;
        });
        setTitle("Edit Document Detail");
        $('#client').on('change', function () {
            window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_ADD_DOCUMENT + "&cl="%>" + this.value;
        });
        thecal=new EthiopianDualCalendar("paymentDue");
        thecal=new EthiopianDualCalendar("incomingDate");
        thecal=new EthiopianDualCalendar("outGoingDate");
        thecal=new EthiopianDualCalendar("incomingDate");
    });
</script>