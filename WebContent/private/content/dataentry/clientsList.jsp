<%-- 
    Document   : Clients List
    Created on : Jan 20, 2017, 12:45:26 PM
    Author     : Yoseph Berhanu <yoseph@artisan.et>
--%>
<%@page import="et.artisan.cn.cps.entity.Client"%>
<%@page import="java.util.*"%>
<%@page import="et.artisan.cn.cps.util.*"%>
<%@page pageEncoding="UTF-8" %>
<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%
    ArrayList<Client> clients = (ArrayList<Client>) request.getAttribute("clients");
%>
<div class="">
    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Clients List</h2>  
                    <ul class="nav navbar-right panel_toolbox">
                        <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
                                <i class="fa fa-gear"></i> Action</a>
                            <ul class="dropdown-menu" role="menu">
                                <li><a href="<%=request.getContextPath()%>/DataEntry?a=<%=Constants.ACTION_DATAENTRY_ADD_CLIENT%>" alt="Add New" title="Add New Client"><i class="fa fa-plus"></i> Add New</a></li>
                                <li><a id ="viewButton" class="single" href="#" alt="View Client" title="View the selected client"><i class="fa fa-search"></i> View</a></li>
                                <li><a id ="editButton" class="single" href="#" alt="Edit Client" title="Edit the selected client"><i class="fa fa-edit"></i> Edit</a></li>
                                <li><a id ="deleteButton" href="#" alt="Delete Client(s)" title="Delete the selected client(s)"><i class="fa fa-remove"></i> Delete</a></li>                                
                                <li><a id ="addRegionButton" class="single" href="#" alt="Add Client Region" title="Add a region to the selected client"><i class="fa fa-plus-square"></i> Add Region</a></li>
                                <li><a id ="ratesButton" class="single" href="#" alt="Update Client's Service Charge Rate" title="Update Client's service charge rate"><i class="fa fa-money"></i> Update Service Charge Rate</a></li>
                                <li><a id ="addProjectButton" class="single" href="#" alt="Add Project" title="Add a project to the selected client"><i class="fa fa-plus-square"></i> Add Project</a></li>
                                    <%--li><a href="<%=request.getContextPath()%>/DataEntry?a=<%=Constants.ACTION_DATAENTRY_CLIENTS_CARD%>" alt="Card View" title="Show Card View of Clients"><i class="fa fa-image"></i> Card View</a></li--%>
                            </ul>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <table id="dataTable" class="table table-striped responsive-utilities jambo_table" data-viewURL="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_VIEW_CLIENT%>" >
                        <thead>
                            <tr class="headings">
                                <th>Name </th>
                                <th>Region</th>
                                <th>ስም</th>
                                <th>Email</th>
                                <th>Phone #</th>
                                <th>Rate</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                int j = 0;
                                for (int i = 0; i < clients.size(); i++) {
                                    if (j % 2 == 0) {
                                        out.println("<tr class='even pointer' data-id='" + clients.get(i).getId() + "'>");
                                    } else {
                                        out.println("<tr class='odd pointer'  data-id='" + clients.get(i).getId() + "'>");
                                    }
                                    j++;

                                    out.println("<td class=''>" + clients.get(i).getName() + "</td>");
                                    out.println("<td class=''>" + clients.get(i).getRegion().getEnglishName() + "</td>");
                                    out.println("<td class=''>" + clients.get(i).getAmharicName()+ "</td>");
                                    out.println("<td class=''>" + clients.get(i).getEmail() + "</td>");
                                    out.println("<td class=''>" + clients.get(i).getPhoneNo() + "</td>");
                                    out.println("<td class=''>" + clients.get(i).getServiceChargeRate()+ "%</td>");
                                    out.println("</tr>");

                                }%>
                        </tbody>
                    </table>
                </div>
            </div>
            <br />
            <br />
            <br />
        </div>
        <!-- /Content-->
    </div>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#viewButton").click(function () {
                window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_VIEW_CLIENT + "&i="%>" + $('#dataTable tr.selected').attr("data-id");
            });
            $("#editButton").click(function () {
                window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_EDIT_CLIENT + "&i="%>" + $('#dataTable tr.selected').attr("data-id");
            });
            $("#deleteButton").click(function () {
                bootbox.confirm("Are you sure you want to delete the selected clients?", function (result) {
                    if (result) {
                        var selectedRowsCount = $('#dataTable tr.selected').length;
                        var param = "";
                        if (selectedRowsCount >= 1) {
                            $("#dataTable tr.selected").each(function () {
                                param = param.concat("i=").concat($(this).attr('data-id')).concat("&");
                            });
                            param = param.substring(0, param.length - 1);
                            console.log(param);
                            window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_DELETE_CLIENT%>&" + param;
                        }
                    }

                });

            });
            $("#addRegionButton").click(function () {
                window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_ADD_CLIENT_REGION + "&cl="%>" + $('#dataTable tr.selected').attr("data-id");
            });
            $("#addProjectButton").click(function () {
                window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_ADD_PROJECT + "&cl="%>" + $('#dataTable tr.selected').attr("data-id");
            });
            $("#ratesButton").click(function () {
                window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_ADD_CLIENT_RATE + "&cl="%>" + $('#dataTable tr.selected').attr("data-id");
            });
            setTitle("Clients");
        });
    </script>