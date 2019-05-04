<%-- 
    Document   : viewClientRegion - DataEntry
    Created on : Apr 25, 2017, 12:19:35 PM
    Author     : Yoseph Berhanu<yoseph@artisan.et> 
--%>
<%@page import="et.artisan.cn.cps.dto.*"%>
<%@page import="et.artisan.cn.cps.entity.*"%>
<%@page import="java.util.HashMap"%>
<%@page import="et.artisan.cn.cps.util.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="et.artisan.cn.cps.util.CommonStorage"%>
<%@page pageEncoding="UTF-8" %>
<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%
    CurrentUserDTO user = CommonStorage.getCurrentUser(request);
    ClientRegion clientRegion = (ClientRegion) request.getAttribute("clientRegion");
%>
<div class="">

    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Client Region Details</h2>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <form class="form-horizontal form-label-left" novalidate action="<%=request.getContextPath() + "/DataEntry"%>" id="viewClientRegionForm" method="POST" >
                        <input type="hidden" name="a" value="<%=Constants.ACTION_DATAENTRY_SAVE_CLIENT_REGION%>" />
                        <input type="hidden" name="i" value="<%=clientRegion.getId()%>" />
                        <div class="col-md-8 col-lg-8">
                            <div class="form-group">
                                <label class="control-label col-lg-3 col-md-3 col-sm-3 col-xs-12" for="client">Client <span class="required">*</span>
                                </label>
                                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                    <input type="text" name="client" id="client" required="required" class="form-control col-md-7 col-xs-12" placeholder="Client Name" value="<%=clientRegion.getClient().getName()%>" readonly="readonly">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-lg-3 col-md-3 col-sm-3 col-sm-3 col-xs-12" for="regionName">Name
                                </label>
                                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                    <input type="text" name="regionName" id="regionName" class="form-control col-md-7 col-xs-12" placeholder="Name of Region" value="<%=clientRegion.getRegionName()%>" readonly="readonly" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-lg-3 col-md-3 col-sm-3 col-sm-3 col-xs-12" for="amharicName">Amharic Name
                                </label>
                                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                    <input type="text" name="amharicName" id="amharicName" class="form-control col-md-7 col-xs-12" placeholder="Name of Region in Amharic" value="<%=clientRegion.getAmharicName()%>" readonly="readonly" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-lg-3 col-md-3 col-sm-3 col-sm-3 col-xs-12" for="contactPerson">Contact Person
                                </label>
                                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                    <input type="text" name="contactPerson" id="contactPerson" class="form-control col-md-7 col-xs-12" placeholder="Name of Region Contact Person" value="<%=clientRegion.getContactPerson()%>" readonly="readonly" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-lg-3 col-md-3 col-sm-3 col-sm-3 col-xs-12" for="phoneNumber">Phone Number
                                </label>
                                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                    <input type="text" name="phoneNumber" id="phoneNumber" class="form-control col-md-7 col-xs-12" placeholder="Phone Number " value="<%=clientRegion.getPhoneNumber()%>" readonly="readonly" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-lg-3 col-md-3 col-sm-3 col-sm-3 col-xs-12" for="addressLine">Address
                                </label>
                                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                    <input type="text" name="addressLine" id="addressLine" class="form-control col-md-7 col-xs-12" placeholder="Address" value="<%=clientRegion.getPhoneNumber()%>" readonly="readonly" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-lg-3 col-md-3 col-sm-3 col-sm-4 col-xs-12" for="remark">Remark
                                </label>
                                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                    <textarea class="form-control" id='remark' name='remark' rows="3" placeholder="Additional Remark" readonly="readonly" ><%=clientRegion.getRemark()%></textarea>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="col-md-4 col-lg-4 col-sm-0">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>Help - Client Region Detail</h2>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="x_content">
                                    <p class="text-justify"> Quid securi etiam tamquam eu fugiat nulla pariatur. Morbi fringilla convallis sapien, id pulvinar odio volutpat. At nos hinc posthac, sitientis piros Afros. Inmensae subtilitatis, obscuris et malesuada fames. Morbi odio eros, volutpat ut pharetra vitae, lobortis sed nibh.</p>
                                    <p class="text-justify"> Inmensae subtilitatis, obscuris et malesuada fames. Morbi fringilla convallis sapien, id pulvinar odio volutpat.  Morbi fringilla convallis sapien, id pulvinar odio volutpat. Quid securi etiam tamquam eu fugiat nulla pariatur. At nos hinc posthac, sitientis piros Afros. Morbi odio eros, volutpat ut pharetra vitae, lobortis sed nibh.</p>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                        </div>
                        <div class='clearfix'></div>
                        <div class="ln_solid"></div>
                        <div class="form-group row">
                            <div class="col-lg-2 col-md-2 col-sm-2 col-lg-offset-1 col-md-offset-2 col-sm-offset-2">
                                <a href="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_CLIENT_REGIONS%>" class="btn btn-primary">Cancel</a>
                            </div>
                            <div class="col-lg-2 col-md-2 col-sm-2 col-lg-offset-1 col-md-offset-1 col-sm-offset-1">
                                <a href="#" class="btn btn-danger" id="deleteButton">Delete</a>
                            </div>
                            <%if (CommonTasks.actionPermitted(Constants.ACTION_DATAENTRY_EDIT_CLIENT_REGION, user)) {%>
                            <div class="col-lg-3 col-md-3 col-sm-3 col-lg-offset-1 col-md-offset-2 col-sm-offset-2">
                                <a href="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_EDIT_CLIENT_REGION + "&i=" + clientRegion.getId()%>" class="btn btn-success">Edit</a>
                            </div>
                            <%}%>
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
        setTitle("Client Region Details");
        $("#deleteButton").click(function () {
            bootbox.confirm("Are you sure you want to delete this client region?", function (result) {
                if (result) {
                	window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_DELETE_CLIENT_REGION+"&i="+clientRegion.getId()%>";
                }
            });

        });
    });
</script>