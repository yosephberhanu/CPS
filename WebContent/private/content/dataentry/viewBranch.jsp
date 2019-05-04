<%-- 
    Document   : viewBranch - DataEntry
    Created on : Feb 1, 2017, 3:50:35 PM
    Author     : Yoseph Berhanu<yoseph@artisan.et> 
--%>
<%@page import="et.artisan.cn.cps.dto.CurrentUserDTO"%>
<%@page import="et.artisan.cn.cps.util.CommonTasks"%>
<%@page import="et.artisan.cn.cps.entity.BranchRegion"%>
<%@page import="et.artisan.cn.cps.entity.BranchType"%>
<%@page import="et.artisan.cn.cps.entity.Branch"%>
<%@page import="et.artisan.cn.cps.entity.Role"%>
<%@page import="java.util.HashMap"%>
<%@page import="et.artisan.cn.cps.util.Constants"%>
<%@page import="et.artisan.cn.cps.entity.User"%>
<%@page import="java.util.ArrayList"%>
<%@page import="et.artisan.cn.cps.util.CommonStorage"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    CurrentUserDTO user = CommonStorage.getCurrentUser(request);
    Branch  branch = (Branch) request.getAttribute("branch");
%>
<div class="">

    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>View Paying Branch</h2>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <form class="form-horizontal form-label-left" novalidate action="#" id="viewBranchForm" method="POST" >
                        <span class="section">Basic Information</span>
                        <div class="col-md-8 col-lg-8">
                            <div class="col-lg-8 col-md-8 col-sm-12">
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="name">Name
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="text" name="name" id="name" readonly="readonly" placeholder="No Name" class="form-control col-md-7 col-xs-12" value="<%=branch.getName()%>">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="contactPerson">Contact Person
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="text" name="contactPerson" id="contactPerson" readonly="readonly" placeholder="No Contact Person" class="form-control col-md-7 col-xs-12" value="<%=branch.getContactPerson()%>">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="type">Type</label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="text" name="contactPerson" id="contactPerson" readonly="readonly" placeholder="No Branch Type" class="form-control col-md-7 col-xs-12" value="<%=branch.getTypeLocalName()%>">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="remark">Remark
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <textarea class="form-control" id='remark' name='remark' readonly="readonly" rows="3" placeholder="No Additional Remark"><%=branch.getRemark()%></textarea>
                                    </div>
                                </div>
                            </div>
                            <div class='clearfix'></div>
                            <span class="section">Address Information</span>
                            <div class="item col-md-6 col-sm-6 col-xs-12 form-group has-feedback optional">
                                <input class="form-control has-feedback-left" id="primaryPhoneNo" readonly="readonly" placeholder="No Primary Phone Number" type="text" name="primaryPhoneNo" value="<%=branch.getPrimaryPhoneNo()%>">
                                <span class="fa fa-phone form-control-feedback left" aria-hidden="true"></span>
                            </div>
                            <div class="item col-md-6 col-sm-6 col-xs-12 form-group has-feedback">
                                <input class="form-control has-feedback-right" id="secondaryPhoneNo" readonly="readonly" placeholder="No Secondary Phone Number" type="text" name="secondaryPhoneNo" value="<%=branch.getSecondaryPhoneNo()%>">
                                <span class="fa fa-phone form-control-feedback right" aria-hidden="true"></span>
                            </div>
                            <div class="item col-md-6 col-sm-6 col-xs-12 form-group has-feedback">
                                <input class="form-control has-feedback-left" id="email" placeholder="No Email " readonly="readonly" type="email"  name="email" value="<%=branch.getEmail()%>">
                                <span class="fa fa-envelope form-control-feedback left" aria-hidden="true"></span>
                            </div>
                            <div class="item col-md-6 col-sm-6 col-xs-12 form-group has-feedback">
                                <input class="form-control has-feedback-right" id="addressLine" readonly="readonly" placeholder="No Address Line" type="text" name="addressLine" value="<%=branch.getAddressLine()%>">
                                <span class="fa fa-map-marker form-control-feedback right" aria-hidden="true"></span>
                            </div>
                            <div class="item col-md-6 col-sm-6 col-xs-12 form-group has-feedback">
                                <input class="form-control has-feedback-left" id="city"  type="text" readonly="readonly" name="city" placeholder="No City" value="<%=branch.getCity()%>">
                                <span class="fa fa-map-marker form-control-feedback left" aria-hidden="true"></span>
                            </div>
                            <div class="item col-md-6 col-sm-6 col-xs-12 form-group has-feedback">
                                <input class="form-control has-feedback-right" id="region" type="text" readonly="readonly" name="region" placeholder="No Region" value="<%=branch.getRegionLocalName()%>">
                                <span class="fa fa-map-marker form-control-feedback right" aria-hidden="true"></span>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="col-md-4 col-lg-4 col-sm-0">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>Help - Branch Details</h2>
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
                            <div class="col-lg-2 col-md-2 col-sm-2 col-lg-offset-1 col-md-offset-2 col-sm-offset-2">
                                <a href="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_BRANCHES%>" class="btn btn-primary">Back</a>
                            </div>
                            <div class="col-lg-2 col-md-2 col-sm-2 col-lg-offset-1 col-md-offset-1 col-sm-offset-1">
                                <a href="#" class="btn btn-danger" id="deleteButton">Delete</a>
                            </div>
                            <%if (CommonTasks.actionPermitted(Constants.ACTION_DATAENTRY_EDIT_BRANCH, user)) {%>
                            <div class="col-lg-2 col-md-2 col-sm-2 col-lg-offset-1 col-md-offset-1 col-sm-offset-1">
                                <a href="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_EDIT_BRANCH+"&i="+branch.getId()%>" class="btn btn-success">Edit</a>
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
        setTitle("View Paying Branch");
    });
    $("#deleteButton").click(function () {
        bootbox.confirm("Are you sure you want to delete this branch?", function (result) {
            if (result) {
            	window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_DELETE_BRANCH+"&i="+branch.getId()%>";
            }
        });

    });
</script>