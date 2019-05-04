<%-- 
    Document   : viewPayment - DataEntry
    Created on : Feb 25, 2017, 3:50:35 PM
    Author     : Yoseph Berhanu<yoseph@artisan.et> 
--%>
<%@page import="java.util.*"%>
<%@page import="et.artisan.cn.cps.dto.*"%>
<%@page import="et.artisan.cn.cps.util.*"%>
<%@page import="et.artisan.cn.cps.entity.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    CurrentUserDTO user = CommonStorage.getCurrentUser(request);
    Payment payment = (Payment) request.getAttribute("payment");
%>
<div class="">

    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>View Payment</h2>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <form class="form-horizontal form-label-left" novalidate action="#" >
                        <div class="col-md-8 col-lg-8">
                            <div class="col-lg-10 col-md-10 col-sm-12">
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="documentRefNo">Document <span class="required">*</span>
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <%Document document = payment.getDocument();%>
                                        <input type="text" readonly="readonly" name="documentRefNo" id="documentRefNo" class="form-control col-md-7 col-xs-12" placeholder="Document" value="<%= document.getProject().getCode() + "/" + document.getInComingDocumentNo() + "/" + document.getDocumentYear()%>" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="lotNo">Lot No
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="number" name="lotNo" readonly="readonly" id="lotNo" class="form-control col-md-7 col-xs-12" placeholder="Lot #" value="<%=payment.getLotNo()%>" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="name">Name
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="text" name="name" readonly="readonly" id="name" class="form-control col-md-7 col-xs-12" placeholder="Full Name" value="<%=payment.getName()%>" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="amount">Amount
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <p name="amount" readonly="readonly" id="amount" class="form-control col-md-7 col-xs-12" placeholder="Amount" ><%=payment.getAmount()%></p>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4" for="restricted" >Restricted <span class="required">*</span>
                                    </label>

                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <%if (payment.isRestricted()) {%>
                                        <input type="text" name="restricted" readonly="readonly" id="restricted" class="form-control col-md-7 col-xs-12" placeholder="Is this payment restricted ?" value="Yes" />
                                        <%} else {%>
                                        <input type="number" name="restricted" readonly="readonly" id="restricted" class="form-control col-md-7 col-xs-12" placeholder="Is this payment restricted ?" value="No" />
                                        <%}%>
                                    </div>
                                </div>
                                <%if (payment.getRemark() != null && !payment.getRemark().trim().isEmpty()) {%>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="remark">Remark
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <p id='remark' name='remark'><%=payment.getRemark()%></p>
                                    </div>
                                </div>
                                <%}%>
                            </div>
                            <div class='clearfix'></div>
                        </div>
                        <div class="col-md-4 col-lg-4 col-sm-0">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>Help - View Payment</h2>
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
                                <a href="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_PAYMENTS + "&p=" + document.getProject().getId() + "&d=" + document.getId()%>" class="btn btn-primary">Back</a>
                            </div>
                            <div class="col-lg-2 col-md-2 col-sm-2 col-lg-offset-1 col-md-offset-1 col-sm-offset-1">
                                <a href="#" class="btn btn-danger" id="deleteButton">Delete</a>
                            </div>
                            <%if (CommonTasks.actionPermitted(Constants.ACTION_DATAENTRY_EDIT_PAYMENT, user)) {%>
                            <div class="col-lg-3 col-md-3 col-sm-3 col-lg-offset-1 col-md-offset-2 col-sm-offset-2">
                                <a href="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_EDIT_PAYMENT + "&i=" + payment.getId()%>" class="btn btn-success">Edit</a>
                            </div>
                            <%}%>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
        setTitle("View Payment");
        var val = parseInt($('#amount').text());
        $('#amount').text(numberWithCommas(val));
        $("#deleteButton").click(function () {
            bootbox.confirm("Are you sure you want to delete this payment?", function (result) {
                if (result) {
                	window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_DELETE_PAYMENT + "&i=" + payment.getId()%>";
                }
            });

        });
    });
</script>