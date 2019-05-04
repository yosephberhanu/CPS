<%-- 
    Document   : viewProject - DataEntry
    Created on : Apr 4, 2017, 10:56:40 AM
    Author     : Yoseph Berhanu<yoseph@artisan.et> 
--%>
<%@page pageEncoding="UTF-8" %>
<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="et.artisan.cn.cps.dto.*"%>
<%@page import="et.artisan.cn.cps.util.*"%>
<%@page import="et.artisan.cn.cps.entity.*"%>
<%
    CurrentUserDTO user = CommonStorage.getCurrentUser(request);
    Project project = (Project) request.getAttribute("project");
%>
<div class="">

    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>View Project</h2>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <form class="form-horizontal form-label-left" novalidate action="#" id="viewProjectForm" method="POST" >
                        <div class="col-md-8 col-lg-8">
                            <div class="col-lg-10 col-md-10 col-sm-12">
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="client" >Client <span class="required">*</span>
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">

                                        <select class="form-control" id="client" name="client" data-value="<%=project.getClient().getId()%>" disabled="disabled">
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
                                        <input type="text" name="code" id="code" class="form-control col-md-7 col-xs-12" placeholder="Project Code" readonly="readonly" value="<%=project.getCode()%>" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for=name">Name
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="text" name="name" id="name" class="form-control col-md-7 col-xs-12" placeholder="Name of Project" readonly="readonly" value="<%=project.getName()%>" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for=amharicName">Amharic Name
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="text" name="amharicName" id="amharicName" class="form-control col-md-7 col-xs-12" placeholder="Name of Project in Amharic" readonly="readonly" value="<%=project.getAmharicName()%>" />
                                    </div>
                                </div>
                                <%if (project.getRemark() != null && !project.getRemark().trim().isEmpty()) {%>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="remark">Remark
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <textarea class="form-control" id='remark' name='remark' rows="3" placeholder="Additional Remark" readonly="readonly"><%=project.getRemark()%></textarea>
                                    </div>
                                </div>
                                <%}%>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="col-md-4 col-lg-4 col-sm-0">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>Help - View Project</h2>
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
                                <a href="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_PROJECTS%>" class="btn btn-primary">Cancel</a>
                            </div>
                            <div class="col-lg-2 col-md-2 col-sm-2 col-lg-offset-1 col-md-offset-1 col-sm-offset-1">
                                <a href="#" class="btn btn-danger" id = "deleteButton">Delete</a>
                            </div>
                            <%if (CommonTasks.actionPermitted(Constants.ACTION_DATAENTRY_EDIT_PROJECT, user)) {%>
                            <div class="col-lg-3 col-md-3 col-sm-3 col-lg-offset-1 col-md-offset-2 col-sm-offset-2">
                                <a href="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_EDIT_PROJECT + "&i=" + project.getId()%>" class="btn btn-success">Edit</a>
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
        setTitle("View Project Details");
        $("#deleteButton").click(function () {
            bootbox.confirm("Are you sure you want to delete this project?", function (result) {
                if (result) {
                	window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_DELETE_PROJECT + "&i=" + project.getId()%>";
                }
            });

        });
    });
</script>