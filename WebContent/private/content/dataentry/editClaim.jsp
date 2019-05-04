<%-- 
    Document   : editPayment - DataEntry
    Created on : May 7, 2017, 4:30:21 PM
    Author     : Yoseph Berhanu<yoseph@artisan.et> 
--%>
<%@page import="et.artisan.cn.cps.dto.BranchesParamModel"%>
<%@page import="et.artisan.cn.cps.entity.*"%>
<%@page import="java.util.*"%>
<%@page import="et.artisan.cn.cps.util.*"%>
<%@page import="et.artisan.cn.cps.entity.*"%>
<%@page import="et.artisan.cn.cps.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Claim claim = (Claim) request.getAttribute("claim");
%>
<div class="">

    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Edit Claim</h2>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <form class="form-horizontal form-label-left" novalidate action="<%=request.getContextPath() + "/DataEntry"%>" id="editClaimForm" method="POST" enctype="multipart/form-data" >
                        <input type="hidden" name="a" value="<%=Constants.ACTION_DATAENTRY_UPDATE_CLAIM%>" />
                        <input type="hidden" name="i" value="<%=claim.getId()%>" />
                        <div class="col-md-8 col-lg-8">
                            <div class="col-lg-10 col-md-10 col-sm-12">
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="claimNumber">Claim Document #
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="text" name="claimNumber" id="claimNumber" class="form-control col-md-7 col-xs-12" placeholder="Claim Document Reference Number" value="<%=claim.getClaimNumber()%>" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="claimCNNumber">Claim CN #
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="text" name="claimCNNumber" id="claimCNNumber" class="form-control col-md-7 col-xs-12" placeholder="Claim CN Number" value="<%=claim.getClaimCNNumber()%>" >
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="claimDate">Claim Date
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="text" name="claimDate" id="claimDate" class="form-control col-md-7 col-xs-12" placeholder="Date the claim was made" value="<%=claim.getClaimDate()%>" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="arrivalDate">Arrival Date
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="text" name="arrivalDate" id="arrivalDate" class="form-control col-md-7 col-xs-12" placeholder="Date the claim arrived" value="<%=claim.getArrivalDate()%>" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="payingBranch">Paying Branch </label>
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <select class="form-control selectpicker" id="payingBranch" name="payingBranch" data-value="<%=claim.getPayingBranch().getId()%>">
                                            <option value="0">Select A Branch</option>
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
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="amount">Amount
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="number" name="amount" id="amount" class="form-control col-md-7 col-xs-12" placeholder="Total Amount of Claim" value="<%=claim.getAmount()%>" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="claimCount">Count
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="number" name="claimCount" id="claimCount" class="form-control col-md-7 col-xs-12" placeholder="Total Number of Claims" value="<%=claim.getCount()%>" >
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="client">Client <span class="required">*</span>
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <%if (request.getAttribute("client") == null) {%>
                                        <select class="form-control" id="client" name="client" data-value="<%=claim.getClient().getId()%>">
                                            <option value="">Select A Client</option>
                                            <%
                                                ArrayList<Client> clients = CommonStorage.getRepository().getAllClients();
                                                for (int i = 0; i < clients.size(); i++) {
                                                    out.println("<option value='" + clients.get(i).getId() + "'>" + clients.get(i).getName() + "</option>");
                                                }
                                            %>
                                        </select>
                                        <%} else {
                                            Client client = (Client) request.getAttribute("client");
                                        %>
                                        <input type="hidden" name="client" id="client" class="form-control col-md-7 col-xs-12" placeholder="Client" value="<%=client.getId()%>" >
                                        <input type="text" readonly="readonly" name="clientName" id="clientName" class="form-control col-md-7 col-xs-12" placeholder="Client" value="<%=client.getName()%>" >
                                        <%}%>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="scannedDocument">Scanned Document
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="file" name="scannedDocument" id="scannedDocument" class="form-control col-md-7 col-xs-12" placeholder="Scanned Version of The Document">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="remark">Remark
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <textarea class="form-control" id='remark' name='remark' rows="3" placeholder="Additional Remark"><%=claim.getRemark()%></textarea>
                                    </div>
                                </div>
                            </div>
                            <div class='clearfix'></div>
                        </div>
                        <div class="col-md-4 col-lg-4 col-sm-0">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>Help - Claim Registration</h2>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="x_content">
                                    <p class="text-justify"> Quid securi etiam tamquam eu fugiat nulla pariatur. Morbi fringilla convallis sapien, id pulvinar odio volutpat. At nos hinc posthac, sitientis piros Afros. Inmensae subtilitatis, obscuris et malesuada fames. Morbi odio eros, volutpat ut pharetra vitae, lobortis sed nibh.</p>
                                    <p class="text-justify"> Morbi fringilla convallis sapien, id pulvinar odio volutpat. Inmensae subtilitatis, obscuris et malesuada fames. Quid securi etiam tamquam eu fugiat nulla pariatur. At nos hinc posthac, sitientis piros Afros. Inmensae subtilitatis, obscuris et malesuada fames. Morbi odio eros, volutpat ut pharetra vitae, lobortis sed nibh.</p>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                        </div>
                        <div class='clearfix'></div>
                        <div class="ln_solid"></div> 
                        <div class="form-group row">
                            <div class="col-lg-1 col-md-1 col-sm-1 col-lg-offset-2 col-md-offset-2 col-sm-offset-2">
                                <a href="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_CLAIMS%>" class="btn btn-primary">Cancel</a>
                            </div>
                            <div class="col-lg-1 col-md-1 col-sm-1 col-lg-offset-2 col-md-offset-2 col-sm-offset-2">
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
        setTitle("Claim Regsitration");
        thecal = new EthiopianDualCalendar("arrivalDate");
        thecal = new EthiopianDualCalendar("claimDate");
    });
</script>