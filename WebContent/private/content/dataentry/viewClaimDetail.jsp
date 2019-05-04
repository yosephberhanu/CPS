<%-- 
    Document   : addClaimDetail - DataEntry
    Created on : Feb 26, 2017, 1:16:00 PM
    Author     : Yoseph Berhanu<yoseph@artisan.et> 
--%>
<%@page import="java.util.*"%>
<%@page import="et.artisan.cn.cps.dto.*"%>
<%@page import="et.artisan.cn.cps.util.*"%>
<%@page import="et.artisan.cn.cps.entity.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    CurrentUserDTO user = CommonStorage.getCurrentUser(request);
    ClaimDetail claimDetail = (ClaimDetail) request.getAttribute("claimDetail");
%>
<div class="">

    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>View Posted Claim Detail</h2>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <form class="form-horizontal form-label-left" novalidate action="#" id="viewClaimDetailForm" method="POST" >
                        <div class="col-md-8 col-lg-8">
                            <div class="col-lg-10 col-md-10 col-sm-12">
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="document">Claim <span class="required">*</span>
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <select class="form-control" id="document" name="document" data-value="<%=claimDetail.getClaim().getId()%>" disabled="disabled">
                                            <option value="0">Select A Claim</option>
                                            <%
                                                //ArrayList<Claim> claims = CommonStorage.getRepository().getAllClaims();
                                                //for (int i = 0; i < claims.size(); i++) {
                                                    out.println("<option value='" + claimDetail.getClaim().getId() + "'>" + claimDetail.getClaim().getClaimNumber() + "</option>");
                                                //}
                                            %>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="payment">Payment <span class="required">*</span>
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <select class="form-control" id="payment" name="payment" data-value="<%=claimDetail.getPayment().getId()%>" disabled="disabled">
                                            <option value="0">Select A Payment</option>
                                            <%
                                                //ArrayList<Payment> payments = CommonStorage.getRepository().getAllPayments();
                                                //for (int i = 0; i < payments.size(); i++) {
                                                    out.println("<option value='" + claimDetail.getPayment().getId() + "'>" + claimDetail.getPayment().getDocument().getInComingDocumentNo() + " - " + claimDetail.getPayment().getName() + " - " + claimDetail.getPayment().getAmount() + "</option>");
                                                //}
                                            %>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="paidBy">Paid By
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="text" name="paidBy" id="paidBy" class="form-control col-md-7 col-xs-12" placeholder="Name of the person who made the payment" value="<%=claimDetail.getPaidBy()%>" disabled="disabled"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="amount">Amount
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="number" name="amount" id="amount" class="form-control col-md-7 col-xs-12" placeholder="Claim Amount" value="<%=claimDetail.getAmount()%>" disabled="disabled"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="paidOn">Paid On
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="date" name="paidOn" id="paidOn" class="form-control col-md-7 col-xs-12" placeholder="Date of Payment " value="<%=claimDetail.getPaidOn()%>" disabled="disabled"/>
                                    </div>
                                </div>
                                <%if (claimDetail.getRemark() != null && !claimDetail.getRemark().trim().isEmpty()) {%>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="remark">Remark
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <textarea class="form-control" id='remark' name='remark' rows="3" placeholder="Additional Remark" disabled="disabled"><%=claimDetail.getRemark()%></textarea>
                                    </div>
                                </div>
                                <%}%>
                            </div>
                            <div class='clearfix'></div>
                        </div>
                        <div class="col-md-4 col-lg-4 col-sm-0">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>Help - View Posted Claim Details</h2>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="x_content">
                                    <p class="text-justify"> Quid securi etiam tamquam eu fugiat nulla pariatur. Morbi fringilla convallis sapien, id pulvinar odio volutpat. At nos hinc posthac, sitientis piros Afros. Inmensae subtilitatis, obscuris et malesuada fames. Morbi odio eros, volutpat ut pharetra vitae, lobortis sed nibh.</p>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                        </div>
                        <div class='clearfix'></div>
                        <div class="ln_solid"></div>   
                        <div class="form-group row">
                            <div class="col-lg-2 col-md-2 col-sm-2 col-lg-offset-1 col-md-offset-2 col-sm-offset-2">
                                <a href="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_CLAIM_DETAILS + "&i=" + claimDetail.getClaim().getId()%>" class="btn btn-primary">Cancel</a>
                            </div>
                            <div class="col-lg-2 col-md-2 col-sm-2 col-lg-offset-1 col-md-offset-1 col-sm-offset-1">
                                <a href="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_DELETE_CLIENT + "&i=" + claimDetail.getId()%>" class="btn btn-danger">Delete</a>
                            </div>
                            <%if (CommonTasks.actionPermitted(Constants.ACTION_DATAENTRY_EDIT_CLAIM_DETAIL, user)) {%>
                            <div class="col-lg-3 col-md-3 col-sm-3 col-lg-offset-1 col-md-offset-2 col-sm-offset-2">
                                <a href="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_EDIT_CLAIM_DETAIL + "&i=" + claimDetail.getId()%>" class="btn btn-success">Edit</a>
                            </div>
                            <%}%>
                        </div>
                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-3">


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
        setTitle("Post Claim Details");
    });
</script>