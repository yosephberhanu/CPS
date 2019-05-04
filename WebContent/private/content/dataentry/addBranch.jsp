<%-- 
    Document   : addBranch - DataEntry
    Created on : Feb 1, 2017, 3:50:35 PM
    Author     : Yoseph Berhanu<yoseph@artisan.et> 
--%>
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
    Branch branch = new Branch();
    if (request.getAttribute("branch") != null) {
        branch = (Branch) request.getAttribute("branch");
    }
%>
<div class="">

    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Paying Branch Registration</h2>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <form class="form-horizontal form-label-left" novalidate action="<%=request.getContextPath() + "/DataEntry"%>" id="addBranchForm" method="POST" >
                        <input type="hidden" name="a" value="<%=Constants.ACTION_DATAENTRY_SAVE_BRANCH%>" />
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
                                        <select class="form-control " id="type" name="type" data-value="1">
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
                                <input class="form-control has-feedback-left" id="primaryPhoneNo" placeholder="Primary Phone Number *" type="text" name="primaryPhoneNo" pattern="phone">
                                <span class="fa fa-phone form-control-feedback left" aria-hidden="true"></span>
                            </div>
                            <div class="item col-md-6 col-sm-6 col-xs-12 form-group has-feedback">
                                <input class="form-control has-feedback-right" id="secondaryPhoneNo" placeholder="Secondary Phone Number (Optional)" type="text" name="secondaryPhoneNo" >
                                <span class="fa fa-phone form-control-feedback right" aria-hidden="true"></span>
                            </div>
                            <div class="item col-md-6 col-sm-6 col-xs-12 form-group has-feedback">
                                <input class="form-control has-feedback-left" id="email" placeholder="Email * " type="email" name="email" />
                                <span class="fa fa-envelope form-control-feedback left" aria-hidden="true"></span>
                            </div>
                            <div class="item col-md-6 col-sm-6 col-xs-12 form-group has-feedback">
                                <input class="form-control has-feedback-right" id="addressLine" placeholder="Address Line" type="text" name="addressLine" >
                                <span class="fa fa-map-marker form-control-feedback right" aria-hidden="true"></span>
                            </div>
                            <div class="item col-md-6 col-sm-6 col-xs-12 form-group has-feedback">
                                <input class="form-control has-feedback-left" id="city" placeholder="City" type="text" name="city" >
                                <span class="fa fa-map-marker form-control-feedback left" aria-hidden="true"></span>
                            </div>
                            <div class="item col-md-6 col-sm-6 col-xs-12 form-group has-feedback">
                                <select class="form-control has-feedback-right" id="region" name="region" required="required">
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
                                    <p class="text-justify"> Inmensae subtilitatis, obscuris et malesuada fames. Morbi fringilla convallis sapien, id pulvinar odio volutpat.  Morbi fringilla convallis sapien, id pulvinar odio volutpat. Quid securi etiam tamquam eu fugiat nulla pariatur. At nos hinc posthac, sitientis piros Afros. Morbi odio eros, volutpat ut pharetra vitae, lobortis sed nibh.</p>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                        </div>
                        <div class='clearfix'></div>
                        <div class="ln_solid"></div>   
                        <div class="form-group row">
                            <div class="col-lg-1 col-md-1 col-sm-1 col-lg-offset-2 col-md-offset-2 col-sm-offset-2">
                                <a href="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_BRANCHES%>" class="btn btn-primary">Cancel</a>
                            </div>
                            <div class="col-lg-1 col-md-1 col-sm-1 col-lg-offset-2 col-md-offset-2 col-sm-offset-2">
                                <button id="submitBtn" type="submit" class="btn btn-success">Submit</button>
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
        $('form').submit(function (e) {
        	//e.preventDefault();
            var submit = true;
            // you can put your own custom validations below
            // check all the required fields
            
            if (validator.checkAll($(this))) {
            	
            	alert("1. Validation");
                validator.unmark($("#type"));
                if ($("#type").val()=== "") {
                    validator.mark($("#type"), "Branch type must be selected");
                    $('form').find("#submitBtn").removeattr('disabled');
                } else {
                    this.submit();
                }
            } else {
            	 $(function () {
            	        new PNotify({
            	            title: 'Validation Error',
            	            text: 'Please check all the required fields',
            	            type: 'error'
            			});
            		});
            	 $('form').find("#submitBtn").removeattr('disabled');
            }
            return false;
        });
        setTitle("Paying Branch Regsitration");
    });
</script>