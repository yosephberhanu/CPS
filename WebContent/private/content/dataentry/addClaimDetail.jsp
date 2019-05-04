<%-- 
    Document   : addClaimDetail - DataEntry
    Created on : Feb 26, 2017, 1:16:00 PM
    Author     : Yoseph Berhanu<yoseph@artisan.et> 
--%>
<%@page import="et.artisan.cn.cps.entity.*"%>
<%@page import="java.util.*"%>
<%@page import="et.artisan.cn.cps.util.*"%>
<%@page import="et.artisan.cn.cps.entity.*"%>
<%@page import="et.artisan.cn.cps.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    long claimId = 0;
    if (request.getAttribute("claimId") != null) {
        claimId = (Long) request.getAttribute("claimId");
    }
    ClaimDetail claimDetail = new ClaimDetail();
    if (request.getAttribute("claimDetail") != null) {
        claimDetail = (ClaimDetail) request.getAttribute("claimDetail");
        claimId = claimDetail.getClaim().getId();
    }
    double amount = claimDetail.getAmount();
    Payment payment = new Payment();
    if (request.getAttribute("payment") != null) {
        payment = (Payment) request.getAttribute("payment");
        amount = payment.getAmount();
    }
%>
<div class="">

    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Post Claim Detail</h2>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <form class="form-horizontal form-label-left" novalidate action="<%=request.getContextPath() + "/DataEntry"%>" id="addclaimDetailForm" method="POST" >
                        <div class="hidden-submit"><input type="submit" tabindex="-1"/></div>

                        <input type="hidden" name="a" value="<%=Constants.ACTION_DATAENTRY_SAVE_CLAIM_DETAIL%>" />
                        <div class="col-md-8 col-lg-8">
                            <div class="col-lg-10 col-md-10 col-sm-12">
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="document">Claim <span class="required">*</span>
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <select class="form-control selectpicker" id="document" name="document" data-value="<%=claimId%>" required="required">
                                            <option value="0" tabindex="0">Select A Claim</option>
                                            <%
                                                //ArrayList<Claim> claims = CommonStorage.getRepository().getAllClaims();
                                                Claim claim = CommonStorage.getRepository().getClaim(claimId);
                                                //for (int i = 0; i < claims.size(); i++) {
                                                out.println("<option id = 'payment" + claim.getId() + "' value='" + claim.getId() + "'>" + claim.getClaimNumber() + "</option>");
                                                //}
%>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="payment">Payment <span class="required">*</span>
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <select class="form-control selectpicker" data-live-search="true" id="payment" name="payment" data-value="<%=payment.getId()%>" tabindex="1">
                                            <option value="0">Select A Payment</option>
                                            <%
                                            //    for (int i = 0; i < payments.size(); i++) {
                                             //       out.println("<option data-remaining = '" + payments.get(i).getRemainingAmount() + "' value='" + payments.get(i).getId() + "'>" + payments.get(i).getDocumentNo() + " - (" + payments.get(i).getLotNo() + ") " + payments.get(i).getName() + " - " + CommonTasks.moneyFormat(payments.get(i).getAmount()) + "</option>");
                                             //   }
                                            %>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="paidBy">Paid By
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="text" name="paidBy" id="paidBy" class="form-control col-md-7 col-xs-12" placeholder="Name of the person who made the payment" value="<%=claimDetail.getPaidBy()%>" tabindex="2">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="amount">Amount
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="number" name="amount" id="amount" class="form-control col-md-7 col-xs-12" placeholder="Claim Amount" value="<%=amount%>" tabindex="3">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="paidOn">Paid On
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="text" name="paidOn" id="paidOn" class="form-control col-md-7 col-xs-12" placeholder="Date of Payment" value="<%=claimDetail.getPaidOn()%>" tabindex="4">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="remark" >Remark
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <textarea class="form-control" id='remark' name='remark' rows="3" placeholder="Additional Remark" tabindex="5"></textarea>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4" for="finish" >Finish <span class="required">*</span>
                                    </label>
                                    <div class="item radio col-lg-4 col-md-4 col-sm-4 ">
                                        <label class="">
                                            <div class="iradio_flat-green " style="position: relative;"><input class="flat" name="finish" value='yes'  style="position: absolute; opacity: 0;" type="radio"><ins class="iCheck-helper" style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255) none repeat scroll 0% 0%; border: 0px none; opacity: 0;"></ins></div> Yes
                                        </label>
                                    </div>
                                    <div class="item radio col-lg-4 col-md-4 col-sm-4">
                                        <label class="">
                                            <div class="iradio_flat-green checked" style="position: relative;"><input class="flat"  name="finish" value='no' style="position: absolute; opacity: 0;" type="radio"><ins class="iCheck-helper" style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255) none repeat scroll 0% 0%; border: 0px none; opacity: 0;"></ins></div> No
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class='clearfix'></div>
                        </div>
                        <div class="col-md-4 col-lg-4 col-sm-0">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>Help - Post Claim Details</h2>
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
                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-3">
                                <a href="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_CLAIM_DETAILS + "&i=" + claimId%>" class="btn btn-primary">Cancel</a>
                                <button name="submitBtn" id="submitBtn" type="submit" class="btn btn-success">Submit</button>
                            </div>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>
</div>
<div class="loader"></div>

<script src="<%=request.getContextPath()%>/assets/js/validator/validator.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/select/select2.full.js"></script>
<script>
    $(document).ready(function () {
    	$("#x_content").html('<img src="<%=request.getContextPath()+"/assets/images/spinner.webp"%>" alt="Wait" />');

    	//"<option data-remaining = '" + payments.get(i).getRemainingAmount() + "' value='" + payments.get(i).getId() + "'>" + payments.get(i).getDocumentNo() + " - (" + payments.get(i).getLotNo() + ") " + payments.get(i).getName() + " - " + CommonTasks.moneyFormat(payments.get(i).getAmount()) + "</option>"
        $.ajax({
		    url: "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_ADD_CLAIM_DETAIL+"&data=true&cl="+claimId%>",
		    dataType: 'json',
		    success: function( json ) {
		        $.each(json, function(i, obj) {
		            $('#payment').append($('<option>')
		            		.text(obj.text)
		            		.attr('value', obj.id)
		            		.attr('data-remaining', obj.remaining));
		        });
		        $("#x_content").html("");
		    }
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
                //Ajax code
                //SHow busy
                $.ajax({type: "POST",
                    url: "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_SAVE_CLAIM_DETAIL%>",
                    data: $("#addclaimDetailForm").serialize(),
                    success: function (data) {
                        $("#payment option:selected").remove();
                        $('#payment').selectpicker('refresh');
                        $('#payment').focus();
                        new PNotify({
                            title: 'Success',
                            text: data,
                            type: 'success'});

                    },
                    error: function (data) {
                        new PNotify({
                            title: 'Something went wrong',
                            text: data,
                            type: 'error'
                        });
                    }
                });
            }
            return false;
        });
        $('#payment').on('change', function () {
            $("#amount").val($('#payment>option:selected').attr('data-remaining'));
            $("#addclaimDetailForm").find("#submitBtn").removeAttr('disabled');
        });
        $('#payment').focus();
        setTitle("Post Claim Details");
        thecal = new EthiopianDualCalendar("paidOn");
    });
</script>