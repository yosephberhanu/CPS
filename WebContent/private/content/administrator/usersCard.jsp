<%-- 
    Document   : User Account Card View
    Created on : Sep 27, 2016, 10:13:26 AM
    Author     : Yoseph Berhanu <yoseph@artisan.et>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="et.artisan.cn.cps.util.Constants"%>
<%@page import="et.artisan.cn.cps.entity.Role"%>
<%@page import="et.artisan.cn.cps.entity.User"%>
<%@page import="et.artisan.cn.cps.util.CommonStorage"%>
<%
    ArrayList<User> users = (ArrayList<User>) request.getAttribute("users");
%>
<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>User Accounts</h3>
        </div>
    </div>
    <div class="clearfix"></div>

    <div class="row">
        <div class="col-md-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>User Accounts</h2>  
                    <ul class="nav navbar-right panel_toolbox">
                        <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
                                <i class="fa fa-gear"></i> Action</a>
                            <ul class="dropdown-menu" role="menu">
                                <li><a href="<%=request.getContextPath()%>/Administrator?a=<%=Constants.ACTION_ADMINISTRATOR_ADD_USER%>" alt="Add New" title="Add New User Accounts"><i class="fa fa-plus"></i> Add New</a></li>
                                <li><a href="<%=request.getContextPath()%>/Administrator?a=<%=Constants.ACTION_ADMINISTRATOR_USERS_LIST%>" alt="List View" title="Show List View of User Accounts"><i class="fa fa-table"></i> List View</a></li>
                            </ul>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">

                    <div class="row">

                        <div class="clearfix"></div>
                        <%
                            int j = 0;
                            for (int i = 0; i < users.size(); i++) {
                                if (users.get(i).getUsername().equalsIgnoreCase(CommonStorage.getCurrentUser(request).getUsername())) {
                                    continue;
                                }
                                if (j % 2 == 0) {
                                    out.println("<tr class='even pointer' data-id='" + users.get(i).getId() + "'>");
                                } else {
                                    out.println("<tr class='odd pointer'  data-id='" + users.get(i).getId() + "'>");
                                }
                                j++;
                        %>
                        <div class="col-md-4 col-sm-4 col-xs-12 animated fadeInDown">
                            <div class="well profile_view">
                                <div class="col-sm-12">
                                    <h4 class="brief"><i><%=users.get(i).getFullName()%></i></h4>
                                    <div class="left col-xs-7">
                                        <h2></h2>
                                        <p><strong>Roles: </strong> <%
                                            int r = 0, c = users.get(i).getRoles().size();
                                            for (Role role : users.get(i).getRoles()) {
                                                r++;
                                                out.print(role.getEnglishName());
                                                if (r < c) {
                                                    out.print(", ");
                                                }

                                            }
                                            %>  </p>
                                        <ul class="list-unstyled">
                                            <li><i class="fa fa-at user-profile-icon"></i><%=users.get(i).getUsername()%>
                                            </li>
                                            <%if (users.get(i).getAddress() != "") {%>
                                            <li><i class="fa fa-map-marker user-profile-icon"></i><%=users.get(i).getAddress()%>
                                            </li>
                                            <%}%>
                                            <%if (users.get(i).getWebsite() != null) {%>
                                            <li class="m-top-xs">
                                                <i class="fa fa-external-link user-profile-icon"></i>
                                                <a href="<%=users.get(i).getWebsite()%>" target="_blank"><%=users.get(i).getWebsite()%></a>
                                            </li>
                                            <%}%>
                                            <%if (users.get(i).getEmail() != null) {%>
                                            <li class="m-top-xs">
                                                <i class="fa fa-envelope-square user-profile-icon"></i>
                                                <a href="mailto:<%=users.get(i).getEmail()%>" ><%=users.get(i).getEmailHidden()%></a>
                                            </li>
                                            <%}%>
                                            <%if (users.get(i).getPrimaryPhoneNo() != null) {%>
                                            <li>
                                                <i class="fa fa-phone user-profile-icon"></i> <%=users.get(i).getPrimaryPhoneNo()%>
                                            </li>
                                            <%}%>
                                        </ul>
                                    </div>
                                    <div class="right col-xs-5 text-center">
                                        <img src="<%=request.getContextPath() + "/Administrator?a=" + Constants.ACTION_ADMINISTRATOR_GET_USER_PHOTO + "&i=" + users.get(i).getId()%>" alt="" class="img-circle img-responsive">
                                    </div>
                                </div>
                                <div class="clearfix"></div>
                                <br/>
                                <div class="col-xs-12 bottom text-center">

                                    <div class="col-xs-12 col-sm-6 emphasis">
                                        <a href="<%=request.getContextPath()%>/Administrator?a=<%=Constants.ACTION_ADMINISTRATOR_EDIT_USER%>&i=<%=users.get(i).getId()%>" class="btn btn-success btn-xs"> <i class="fa fa-edit">
                                            </i>Edit </a>
                                        <a href="<%=request.getContextPath()%>/Administrator?a=<%=Constants.ACTION_ADMINISTRATOR_VIEW_USER%>&i=<%=users.get(i).getId()%>" class="btn btn-primary btn-xs"> <i class="fa fa-search">
                                            </i> View </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%}%>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        setTitle("Users");
    });
</script>