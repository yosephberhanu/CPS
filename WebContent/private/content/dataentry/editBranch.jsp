<%-- 
    Document   : editBranch - Supervisor
    Created on : Apr 23, 2017, 3:37:15 PM
    Author     : Yoseph Berhanu<yoseph@artisan.et> 
--%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="et.artisan.cn.cps.entity.*"%>
<%@page import="et.artisan.cn.cps.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Branch branch = (Branch) request.getAttribute("branch");
%>
<div class="">

    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Edit Paying Branch</h2>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <form class="form-horizontal form-label-left" novalidate action="<%=request.getContextPath() + "/DataEntry"%>" id="editBranchForm" method="POST" >
                        <input type="hidden" name="a" value="<%=Constants.ACTION_DATAENTRY_UPDATE_BRANCH%>" />
                        <input type="hidden" name="i" value="<%=branch.getId()%>" />
                        <span class="section">Basic Information</span>
                        <div class="col-md-8 col-lg-8">
                            <div class="col-lg-8 col-md-8 col-sm-12">
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="name">Name <span class="required">*</span>
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="text" name="name" id="name" required="required" class="form-control col-md-7 col-xs-12" placeholder="Branch Name" value="<%=branch.getName()%>" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="contactPerson">Contact Person
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="text" name="contactPerson" id="contactPerson" class="form-control col-md-7 col-xs-12" placeholder="Name of Branch Contact Person" value="<%=branch.getContactPerson()%>" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="type">Type *</label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <select class="form-control selectpicker" id="type" name="type" data-value="<%=branch.getType()%>">
                                            <option value="">Select A Branch Type</option>
                                            <%
                                                ArrayList<BranchType> branchesList = CommonStorage.getRepository().getAllBranchTypes();
                                                for (int i = 0; i < branchesList.size(); i++) {
                                                    out.println("<option value='" + branchesList.get(i).getCode() + "'>" + branchesList.get(i).getEnglishName() + "</option>");
                                                }
                                            %>
                                        </select>
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
                            <div class='clearfix'></div>
                            <span class="section">Address Information</span>
                            <div class="item col-md-6 col-sm-6 col-xs-12 form-group has-feedback optional">
                                <input class="form-control has-feedback-left" id="primaryPhoneNo" placeholder="Primary Phone Number *" type="text" name="primaryPhoneNo" pattern="phone" value="<%=branch.getPrimaryPhoneNo()%>" />
                                <span class="fa fa-phone form-control-feedback left" aria-hidden="true"></span>
                            </div>
                            <div class="item col-md-6 col-sm-6 col-xs-12 form-group has-feedback">
                                <input class="form-control has-feedback-right" id="secondaryPhoneNo" placeholder="Secondary Phone Number (Optional)" type="text" name="secondaryPhoneNo" value="<%=branch.getSecondaryPhoneNo()%>" />
                                <span class="fa fa-phone form-control-feedback right" aria-hidden="true"></span>
                            </div>
                            <div class="item col-md-6 col-sm-6 col-xs-12 form-group has-feedback">
                                <input class="form-control has-feedback-left" id="email" placeholder="Email * " type="email" name="email" value="<%=branch.getEmail()%>" />
                                <span class="fa fa-envelope form-control-feedback left" aria-hidden="true"></span>
                            </div>
                            <div class="item col-md-6 col-sm-6 col-xs-12 form-group has-feedback">
                                <input class="form-control has-feedback-right" id="addressLine" placeholder="Address Line" type="text" name="addressLine" value="<%=branch.getAddressLine()%>" />
                                <span class="fa fa-map-marker form-control-feedback right" aria-hidden="true"></span>
                            </div>
                            <div class="item col-md-6 col-sm-6 col-xs-12 form-group has-feedback">
                                <input class="form-control has-feedback-left" id="city" placeholder="City" type="text" name="city" value="<%=branch.getCity()%>" />
                                <span class="fa fa-map-marker form-control-feedback left" aria-hidden="true"></span>
                            </div>
                            <div class="item col-md-6 col-sm-6 col-xs-12 form-group has-feedback">
                                <select class="form-control has-feedback-right" id="region" name="region" data-value="<%=branch.getRegion()%>" >
                                    <option value="">Select A Region</option>
                                    <%
                                        ArrayList<BranchRegion> brancheRegionsList = CommonStorage.getRepository().getAllBranchRegions();
                                        for (int i = 0; i < brancheRegionsList.size(); i++) {
                                            out.println("<option value='" + brancheRegionsList.get(i).getCode() + "'>" + brancheRegionsList.get(i).getEnglishName() + "</option>");
                                        }
                                    %>
                                </select>
                                <span class="fa fa-map-marker form-control-feedback right" aria-hidden="true"></span>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="col-md-4 col-lg-4 col-sm-0">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>Help - Branch Registration</h2>
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
                            <div class="col-lg-3 col-md-3 col-sm-3">
                                <a href="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_VIEW_BRANCH + "&i=" + branch.getId()%>" class="btn btn-primary">Cancel</a>

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
    $('#addBranchForm').submit(function (e) {
        e.preventDefault();
        var submit = true;
        // you can put your own custom validations below
        // check all the required fields
        if (validator.checkAll($(this))) {
            if ($("#type") === "") {
                console.log("Branch type must be selected");
                validator.mark($("#type"), "Branch type must be selected");
                //} else if (!usernameValid) {
                //validator.mark($("#username"), "Username already taken");
            } else {
                this.submit();
            }
        }
        return false;
    });


    $(document).ready(function () {
        setTitle("Paying Branch Regsitration");
    });
</script>