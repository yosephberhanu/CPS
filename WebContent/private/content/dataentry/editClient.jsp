<%-- 
    Document   : editClient - DataEntry
    Created on : Apr 24, 2017, 11:37:25 AM
    Author     : Yoseph Berhanu<yoseph@artisan.et> 
--%>
<%@page import="java.util.*"%>
<%@page import="et.artisan.cn.cps.util.*"%>
<%@page import="et.artisan.cn.cps.entity.*"%>
<%@page pageEncoding="UTF-8" %>
<%@page language="java" contentType="text/html; charset=UTF-8"%>

<%
    Client client = (Client) request.getAttribute("client");

%>
<div class="">

    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Edit Client</h2>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <form class="form-horizontal form-label-left" novalidate action="<%=request.getContextPath() + "/DataEntry"%>" id="editClientForm" method="POST" >
                        <input type="hidden" name="a" value="<%=Constants.ACTION_DATAENTRY_UPDATE_CLIENT%>" />
                        <input type="hidden" name="i" value="<%=client.getId()%>" />
                        <div class="col-md-7 col-lg-7">
                            <div class="form-group">
                                <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="name">Name <span class="required">*</span>
                                </label>
                                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                    <input type="text" name="name" id="name" required="required" class="form-control col-md-7 col-xs-12" placeholder="Client Name" value="<%=client.getName()%>" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for=amharicName">Amharic Name
                                </label>
                                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                    <input type="text" name="amharicName" id="amharicName" class="form-control col-md-7 col-xs-12" placeholder="Client Name In Amharic " value="<%=client.getAmharicName()%>" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="contactPerson">Contact Person
                                </label>
                                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                    <input type="text" name="contactPerson" id="contactPerson" class="form-control col-md-7 col-xs-12" placeholder="Name of Client Contact Person" value="<%=client.getContactPerson()%>" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="phoneNumber">Phone Number
                                </label>
                                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                    <input type="text" name="phoneNumber" id="phoneNumber" class="form-control col-md-7 col-xs-12" placeholder="Client's Phone Number" value="<%=client.getPhoneNo()%>" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="email">E-mail
                                </label>
                                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                    <input type="email" name="email" id="email" class="form-control col-md-7 col-xs-12" placeholder="Client's E-mail Address" value="<%=client.getEmail()%>" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="website">Web Site
                                </label>
                                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                    <input type="url" name="website" id="website" class="form-control col-md-7 col-xs-12" placeholder="Client's Website Address" value="<%=client.getWebsite()%>" />
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="addressLine">Address Line
                                </label>
                                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                    <input type="text" name="addressLine" id="addressLine" class="form-control col-md-7 col-xs-12" placeholder="Client's Address" value="<%=client.getAddressLine()%>" >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="city">City
                                </label>
                                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                    <input type="text" name="city" id="city" class="form-control col-md-7 col-xs-12" placeholder="Client's City" value="<%=client.getCity()%>" >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="region">Region
                                </label>
                                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                    <select class="form-control selectpicker has-feedback-right" id="region" name="region"  data-value="<%=client.getRegion().getCode()%>" required="required">
                                        <option value="">Select A Region</option>
                                        <%
                                            ArrayList<BranchRegion> brancheRegionsList = CommonStorage.getRepository().getAllBranchRegions();
                                            for (int i = 0; i < brancheRegionsList.size(); i++) {
                                                out.println("<option value='" + brancheRegionsList.get(i).getCode()+ "'>" + brancheRegionsList.get(i).getEnglishName() + "</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="remark">Remark
                                </label>
                                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                    <textarea class="form-control" id='remark' name='remark' rows="3" placeholder="Additional Remark"><%=client.getRemark()%></textarea>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4 col-lg-4 col-sm-12 col-lg-offset-1 col-md-offset-1">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>Help - Edit Client</h2>
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
                            <div class="col-lg-3 col-md-3 col-sm-3 col-lg-offset-1 col-md-offset-1 col-sm-offset-1">
                                <a href="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_VIEW_CLIENT + "&i=" + client.getId()%>" class="btn btn-primary">Cancel</a>                            
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
    })


    $(document).ready(function () {
        setTitle("Edit Client");
    });
</script>