<%-- 
    Document   : Administrator - View User ACCOUNT
    Created on : Sep 26, 2016, 10:48:28 AM
    Author     : Yoseph Berhanu<yoseph@artisan.et> 
--%>
<%@page import="et.artisan.cn.cps.entity.User"%>
<%@page import="et.artisan.cn.cps.util.Constants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    User user = (User) request.getAttribute("user");
%>

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3><%=user.getFullName()%>'s Profile</h3>
        </div>

        <!--div class="title_right">
            <div class="col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search">
                <div class="input-group">
                    <input class="form-control" placeholder="Search for..." type="text">
                    <span class="input-group-btn">
                        <button class="btn btn-default" type="button">Go!</button>
                    </span>
                </div>
            </div>
        </div-->
    </div>
    <div class="clearfix"></div>

    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>User Report <small>Activity report</small></h2>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <div class="col-md-3 col-sm-3 col-xs-12 profile_left">

                        <div class="profile_img">

                            <!-- end of image cropping -->
                            <div id="crop-avatar">
                                <!-- Current avatar -->
                                <div data-original-title="Change the avatar" class="avatar-view" title="">
                                    <img src="<%=request.getContextPath() + "/Administrator?a=" + Constants.ACTION_ADMINISTRATOR_GET_USER_PHOTO + "&i=" + user.getId()%>" alt="Avatar">
                                </div>
                                <!-- Loading state -->
                                <div class="loading" aria-label="Loading" role="img" tabindex="-1"></div>
                            </div>
                            <!-- end of image cropping -->

                        </div>
                        <h3><%=user.getFullName()%>(<%=user.getSexText()%>)</h3>

                        <ul class="list-unstyled user_data">
                            <li><i class="fa fa-at user-profile-icon"></i><%=user.getUsername()%>
                            </li>
                            <%if (user.getAddress() != "") {%>
                            <li><i class="fa fa-map-marker user-profile-icon"></i><%=user.getAddress()%>
                            </li>
                            <%}%>
                            <%if (user.getWebsite() != null) {%>
                            <li class="m-top-xs">
                                <i class="fa fa-external-link user-profile-icon"></i>
                                <a href="<%=user.getWebsite()%>" target="_blank"><%=user.getWebsite()%></a>
                            </li>
                            <%}%>
                            <%if (user.getEmail() != null) {%>
                            <li class="m-top-xs">
                                <i class="fa fa-envelope-square user-profile-icon"></i>
                                <a href="mailto:<%=user.getEmail()%>" ><%=user.getEmailHidden()%></a>
                            </li>
                            <%}%>
                            <%if (user.getPrimaryPhoneNo() != null) {%>
                            <li>
                                <i class="fa fa-phone user-profile-icon"></i> <%=user.getPrimaryPhoneNo()%>
                            </li>
                            <%}%>
                        </ul>



                        <!-- start skills -->
                        <h4>Roles</h4>
                        <ul class="list-unstyled user_data">
                            <%for (int i = 0; i < user.getRoles().size(); i++) {%><li>
                                <p><%=user.getRoles().get(i).getEnglishName()%></p>
                                <div class="progress progress_sm">
                                    <div aria-valuenow="49" style="width: 100%;" class="progress-bar bg-<%=user.getRoles().get(i).getColor()%>" role="progressbar" data-transitiongoal="100"></div>
                                </div>
                            </li> 
                            <%}%>
                        </ul>
                        <!-- end of skills -->
                        <hr/>
                        <button title="Print" data-placement="top" data-toggle="tooltip" type="button" data-original-title="Print" class="btn  btn-sm tooltips"><i class="fa fa-print"></i> </button>
                        <button title="Export" data-placement="top" data-toggle="tooltip" type="button" data-original-title="Export" class="btn  btn-sm tooltips"><i class="fa fa-download"></i> </button>
                        <hr/>
                        <%if (user.getStatus().trim().equalsIgnoreCase(Constants.STATUS_INACTIVE)) {%>
                        <a class="btn btn-warning" href="<%=request.getContextPath() + "/Administrator?a=" + Constants.ACTION_ADMINISTRATOR_REACTIVATE_USER + "&i=" + user.getId()%>" style="width: 90%;padding-left: 5%"><i class="fa fa-play m-right-xs"></i> Re-activate</a>
                        <br>
                        <%} else if (user.getStatus().trim().equalsIgnoreCase(Constants.STATUS_ACTIVE)) {%>
                        <a class="btn btn-warning" href="<%=request.getContextPath() + "/Administrator?a=" + Constants.ACTION_ADMINISTRATOR_DEACTIVATE_USER + "&i=" + user.getId()%>" style="width: 40%;padding-left: 5%;padding-right: 10%"><i class="fa fa-pause m-right-xs"></i> Suspend</a>
                        <a class="btn btn-success" href="<%=request.getContextPath() + "/Administrator?a=" + Constants.ACTION_ADMINISTRATOR_EDIT_USER + "&i=" + user.getId()%>" style="width: 40%;padding-left: 5%"><i class="fa fa-edit m-right-xs"></i> Edit</a>
                        <br>
                        <a id = 'deleteButton' class="btn btn-danger btn" href="<%=request.getContextPath() + "/Administrator?a=" + Constants.ACTION_ADMINISTRATOR_DELETE_USER + "&i=" + user.getId()%>"  style="width: 85%;padding-left: 5%"><i class="fa fa-trash m-right-xs"></i> Remove Account</a>
                        <%}%>
                    </div>

                    <div class="col-md-9 col-sm-9 col-xs-12">
                        <div class="col-lg-5">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>Profile Completion</h2>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="x_content">
                                    <div class="sidebar-widget">
                                        <h4></h4>
                                        <canvas width="250" height="160" id="foo2" class="" style="width: 180px; height: 80px;"></canvas>
                                        <div class="goal-wrapper">
                                            <span id="gauge-text2" class="gauge-value pull-left">3,200</span>
                                            <span id="goal-text2" class="goal-value pull-right">5,000</span>
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                    <hr />
                                    <a class="btn btn-success" href="#" style="width: 60%;margin-left: 20%"><i class="fa fa-edit m-right-xs"></i> Details</a>
                                </div>
                            </div>
                        </div>

                    </div>
                    <hr/>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="<%=request.getContextPath()%>/assets/js/gauge/gauge.min.js"></script>
<script>
    $(document).ready(function () {
        $("#deleteButton").click(function () {
            bootbox.confirm("Are you sure you want to delete this user?", function (result) {
                if (result) {
                    return true;
                } else {
                    return false;
                }
            });
        });
    });
</script>
<script>

    var opts = {
        lines: 12, // The number of lines to draw
        angle: 0, // The length of each line
        lineWidth: 0.4, // The line thickness
        pointer: {
            length: 0.75, // The radius of the inner circle
            strokeWidth: 0.042, // The rotation offset
            color: '#1D212A' // Fill color
        },
        limitMax: 'false', // If true, the pointer will not go past the end of the gauge
        colorStart: '#1ABC9C', // Colors
        colorStop: '#1ABC9C', // just experiment with them
        strokeColor: '#F0F3F3', // to see which ones work best for you
        generateGradient: true
    };
    var target = document.getElementById('foo2'); // your canvas element
    var gauge = new Gauge(target).setOptions(opts); // create sexy gauge!
    gauge.maxValue = 5000; // set max gauge value
    gauge.animationSpeed = 32; // set animation speed (32 is default value)
    gauge.set(3200); // set actual value
    gauge.setTextField(document.getElementById("gauge-text2"));
    $(document).ready(function () {
        setTitle("View User");
    });
</script>