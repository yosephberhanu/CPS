<%-- 
    Document   : editProject - DataEntry
    Created on : May 3, 2017, 03:19:55 PM
    Author     : Yoseph Berhanu<yoseph@artisan.et> 
--%>
<%@page import="et.artisan.cn.cps.entity.*"%>
<%@page import="java.util.HashMap"%>
<%@page import="et.artisan.cn.cps.entity.User"%>
<%@page import="java.util.ArrayList"%>
<%@page import="et.artisan.cn.cps.util.*"%>
<%@page pageEncoding="UTF-8" %>
<%@page language="java" contentType="text/html; charset=UTF-8"%>

<%
    Project project = (Project) request.getAttribute("project");
%>
<div class="">

    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Edit Project</h2>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <form class="form-horizontal form-label-left" novalidate action="<%=request.getContextPath() + "/DataEntry"%>" id="editProjectForm" method="POST" >
                        <input type="hidden" name="a" value="<%=Constants.ACTION_DATAENTRY_UPDATE_PROJECT%>" />
                        <input type="hidden" name="i" value="<%=project.getId()%>" />
                        <div class="col-md-8 col-lg-8">
                            <div class="col-lg-10 col-md-10 col-sm-12">
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="client">Client <span class="required">*</span>
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <select class="form-control selectpicker" id="client" name="client" data-value="<%=project.getClient().getId()%>">
                                            <option value="">Select A Client</option>
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
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="code">Code
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="text" name="code" id="code" class="form-control col-md-7 col-xs-12" placeholder="Project Code" value="<%=project.getCode()%>" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for=name">Name
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="text" name="name" id="name" class="form-control col-md-7 col-xs-12" placeholder="Name of Project" value="<%=project.getName()%>" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for=amharicName">Amharic Name
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="text" name="amharicName" id="amharicName" class="form-control col-md-7 col-xs-12" placeholder="Name of Project in Amharic " value="<%=project.getAmharicName()%>" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="remark">Remark
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <textarea class="form-control" id='remark' name='remark' rows="3" placeholder="Additional Remark"><%=project.getRemark()%></textarea>
                                    </div>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="col-md-4 col-lg-4 col-sm-0">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>Help - Edit Project</h2>
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
                            <div class="col-lg-3 col-md-3 col-sm-3 col-lg-offset-1 col-md-offset-1 col-sm-offset-1">
                                <a href="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_VIEW_PROJECT + "&i=" + project.getId()%>" class="btn btn-primary">Cancel</a>
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
        setTitle("Edit Project");
    });
</script>