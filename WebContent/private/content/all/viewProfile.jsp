<%-- 
    Document   : All - View Profile
    Created on : Sep 26, 2016, 10:48:28 AM
    Author     : Yoseph Berhanu<yoseph@artisan.et> 
--%>
<%@page import="et.artisan.cn.cps.dto.CurrentUserDTO"%>
<%@page import="et.artisan.cn.cps.util.Constants"%>
<%@page import="et.artisan.cn.cps.util.CommonStorage"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    CurrentUserDTO currentUser = CommonStorage.getCurrentUser(request);

%>
<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3><%=currentUser.getFullName()%>'s Profile</h3>
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
                    <h2>Account Information</h2>
                    <ul class="nav navbar-right panel_toolbox">
                        <li><a href="#"><i class="fa fa-chevron-up"></i></a>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
                            <ul class="dropdown-menu" role="menu">
                                <li><a href="#">Settings 1</a>
                                </li>
                                <li><a href="#">Settings 2</a>
                                </li>
                            </ul>
                        </li>
                        <li><a href="#"><i class="fa fa-close"></i></a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">

                    <div class="col-md-3 col-sm-3 col-xs-12 profile_left">

                        <div class="profile_img">

                            <!-- end of image cropping -->
                            <div id="crop-avatar">
                                <!-- Current avatar -->
                                <div data-original-title="Change the avatar" class="avatar-view" title="">
                                    <img src="<%=request.getContextPath() + "/All?a=" + Constants.ACTION_ALL_GET_PROFILE_PICTURE%>" alt="Avatar">
                                </div>
                                <!-- Loading state -->
                                <div class="loading" aria-label="Loading" role="img" tabindex="-1"></div>
                            </div>
                            <!-- end of image cropping -->

                        </div>
                        <h3><%=currentUser.getFullName()%>(<%=currentUser.getSexText()%>)</h3>

                        <ul class="list-unstyled user_data">
                            <li><i class="fa fa-at user-profile-icon"></i><%=currentUser.getUsername()%>
                            </li>
                            <%if (currentUser.getAddress() != "") {%>
                            <li><i class="fa fa-map-marker user-profile-icon"></i><%=currentUser.getAddress()%>
                            </li>
                            <%}%>
                            <%if (currentUser.getWebsite() != null) {%>
                            <li class="m-top-xs">
                                <i class="fa fa-external-link user-profile-icon"></i>
                                <a href="<%=currentUser.getWebsite()%>" target="_blank"><%=currentUser.getWebsite()%></a>
                            </li>
                            <%}%>
                            <%if (currentUser.getEmail() != null) {%>
                            <li class="m-top-xs">
                                <i class="fa fa-envelope-square user-profile-icon"></i>
                                <a href="mailto:<%=currentUser.getEmail()%>" ><%=currentUser.getEmailHidden()%></a>
                            </li>
                            <%}%>
                            <%if (currentUser.getPrimaryPhoneNo() != null) {%>
                            <li>
                                <i class="fa fa-phone user-profile-icon"></i> <%=currentUser.getPrimaryPhoneNo()%>
                            </li>
                            <%}%>
                        </ul>

                        <a class="btn btn-success" href="<%=request.getContextPath() + "/All?a=" + Constants.ACTION_ALL_EDIT_PROFILE%>"><i class="fa fa-edit m-right-xs"></i>Edit Profile</a>
                        <br>

                        <!-- start skills -->
                        <h4>Roles</h4>
                        <ul class="list-unstyled user_data">
                            <%for (int i = 0; i < currentUser.getRoles().size(); i++) {

                            %><li>
                                <p><%=currentUser.getRoles().get(i).getEnglishName()%></p>
                                <div class="progress progress_sm">
                                    <div aria-valuenow="49" style="width: 100%;" class="progress-bar bg-<%=currentUser.getRoles().get(i).getColor()%>" role="progressbar" data-transitiongoal="100"></div>
                                </div>
                            </li> 
                            <%}%>
                        </ul>
                        <!-- end of skills -->

                    </div>
                    <div class="col-md-9 col-sm-9 col-xs-12">

                        <div class="profile_title">
                            <div class="col-md-6">
                                <h2>User Activity Report</h2>
                            </div>
                            <div class="col-md-6">
                                <div id="reportrange" class="pull-right" style="margin-top: 5px; background: #fff; cursor: pointer; padding: 5px 10px; border: 1px solid #E6E9ED">
                                    <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                                    <span>August 28, 2016 - September 26, 2016</span> <b class="caret"></b>
                                </div>
                            </div>
                        </div>
                        <!-- start of user-activity-graph -->
                        <div id="graph_bar" style="width: 100%; height: 280px; position: relative;"><svg style="overflow: hidden; position: relative; left: -0.75px; top: -0.75px;" xmlns="http://www.w3.org/2000/svg" width="756" version="1.1" height="280"><desc>Created with Raphaël 2.0.1</desc><defs></defs><text font-weight="normal" font-family="sans-serif" font-size="12px" fill="#888888" stroke="none" font="10px &quot;Arial&quot;" text-anchor="end" y="202.27330221745842" x="33.5" style="text-anchor: end; font: 12px sans-serif;"><tspan dy="4.000002046559985">0</tspan></text><path stroke-width="0.5" d="M46,202.27330221745842H731" stroke="#aaaaaa" fill="none" style=""></path><text font-weight="normal" font-family="sans-serif" font-size="12px" fill="#888888" stroke="none" font="10px &quot;Arial&quot;" text-anchor="end" y="157.95497666309382" x="33.5" style="text-anchor: end; font: 12px sans-serif;"><tspan dy="4.000005349617254">100</tspan></text><path stroke-width="0.5" d="M46,157.95497666309382H731" stroke="#aaaaaa" fill="none" style=""></path><text font-weight="normal" font-family="sans-serif" font-size="12px" fill="#888888" stroke="none" font="10px &quot;Arial&quot;" text-anchor="end" y="113.63665110872921" x="33.5" style="text-anchor: end; font: 12px sans-serif;"><tspan dy="4.000001023279992">200</tspan></text><path stroke-width="0.5" d="M46,113.63665110872921H731" stroke="#aaaaaa" fill="none" style=""></path><text font-weight="normal" font-family="sans-serif" font-size="12px" fill="#888888" stroke="none" font="10px &quot;Arial&quot;" text-anchor="end" y="69.3183255543646" x="33.5" style="text-anchor: end; font: 12px sans-serif;"><tspan dy="3.9999966969427305">300</tspan></text><path stroke-width="0.5" d="M46,69.3183255543646H731" stroke="#aaaaaa" fill="none" style=""></path><text font-weight="normal" font-family="sans-serif" font-size="12px" fill="#888888" stroke="none" font="10px &quot;Arial&quot;" text-anchor="end" y="25" x="33.5" style="text-anchor: end; font: 12px sans-serif;"><tspan dy="4">400</tspan></text><path stroke-width="0.5" d="M46,25H731" stroke="#aaaaaa" fill="none" style=""></path><text transform="matrix(0.5,-0.866,0.866,0.5,156.8097,722.8058)" font-weight="normal" font-family="sans-serif" font-size="12px" fill="#888888" stroke="none" font="10px &quot;Arial&quot;" text-anchor="middle" y="214.77330221745842" x="696.75" style="text-anchor: middle; font: 12px sans-serif;"><tspan dy="4.000002046559985">Oct</tspan></text><text transform="matrix(0.5,-0.866,0.866,0.5,121.893,664.6377)" font-weight="normal" font-family="sans-serif" font-size="12px" fill="#888888" stroke="none" font="10px &quot;Arial&quot;" text-anchor="middle" y="214.77330221745842" x="628.25" style="text-anchor: middle; font: 12px sans-serif;"><tspan dy="4.000002046559985">Sep</tspan></text><text transform="matrix(0.5,-0.866,0.866,0.5,87.643,605.315)" font-weight="normal" font-family="sans-serif" font-size="12px" fill="#888888" stroke="none" font="10px &quot;Arial&quot;" text-anchor="middle" y="214.77330221745842" x="559.75" style="text-anchor: middle; font: 12px sans-serif;"><tspan dy="4.000002046559985">Aug</tspan></text><text transform="matrix(0.5,-0.866,0.866,0.5,54.8972,543.387)" font-weight="normal" font-family="sans-serif" font-size="12px" fill="#888888" stroke="none" font="10px &quot;Arial&quot;" text-anchor="middle" y="214.77330221745842" x="491.25" style="text-anchor: middle; font: 12px sans-serif;"><tspan dy="4.000002046559985">Jul</tspan></text><text transform="matrix(0.5,-0.866,0.866,0.5,19.643,485.8035)" font-weight="normal" font-family="sans-serif" font-size="12px" fill="#888888" stroke="none" font="10px &quot;Arial&quot;" text-anchor="middle" y="214.77330221745842" x="422.75" style="text-anchor: middle; font: 12px sans-serif;"><tspan dy="4.000002046559985">Jun</tspan></text><text transform="matrix(0.5,-0.866,0.866,0.5,-15.4362,427.9169)" font-weight="normal" font-family="sans-serif" font-size="12px" fill="#888888" stroke="none" font="10px &quot;Arial&quot;" text-anchor="middle" y="214.77330221745842" x="354.25" style="text-anchor: middle; font: 12px sans-serif;"><tspan dy="4.000002046559985">May</tspan></text><text transform="matrix(0.5,-0.866,0.866,0.5,-48.6862,366.8621)" font-weight="normal" font-family="sans-serif" font-size="12px" fill="#888888" stroke="none" font="10px &quot;Arial&quot;" text-anchor="middle" y="214.77330221745842" x="285.75" style="text-anchor: middle; font: 12px sans-serif;"><tspan dy="4.000002046559985">Apr</tspan></text><text transform="matrix(0.5,-0.866,0.866,0.5,-83.432,308.3982)" font-weight="normal" font-family="sans-serif" font-size="12px" fill="#888888" stroke="none" font="10px &quot;Arial&quot;" text-anchor="middle" y="214.77330221745842" x="217.25" style="text-anchor: middle; font: 12px sans-serif;"><tspan dy="4.000002046559985">Mar</tspan></text><text transform="matrix(0.5,-0.866,0.866,0.5,-117.6862,249.0827)" font-weight="normal" font-family="sans-serif" font-size="12px" fill="#888888" stroke="none" font="10px &quot;Arial&quot;" text-anchor="middle" y="214.77330221745842" x="148.75" style="text-anchor: middle; font: 12px sans-serif;"><tspan dy="4.000002046559985">Feb</tspan></text><text transform="matrix(0.5,-0.866,0.866,0.5,-151.607,189.1898)" font-weight="normal" font-family="sans-serif" font-size="12px" fill="#888888" stroke="none" font="10px &quot;Arial&quot;" text-anchor="middle" y="214.77330221745842" x="80.25" style="text-anchor: middle; font: 12px sans-serif;"><tspan dy="4.000002046559985">Jan</tspan></text><rect stroke-width="0" style="" stroke="#000" fill="#26b99a" ry="0" rx="0" r="0" height="35.45466044349169" width="24.1875" y="166.81864177396673" x="54.5625"></rect><rect stroke-width="0" style="" stroke="#000" fill="#26b99a" ry="0" rx="0" r="0" height="55.39790694295576" width="24.1875" y="146.87539527450267" x="123.0625"></rect><rect stroke-width="0" style="" stroke="#000" fill="#26b99a" ry="0" rx="0" r="0" height="78.0002529756817" width="24.1875" y="124.27304924177672" x="191.5625"></rect><rect stroke-width="0" style="" stroke="#000" fill="#26b99a" ry="0" rx="0" r="0" height="99.27304924177672" width="24.1875" y="103.0002529756817" x="260.0625"></rect><rect stroke-width="0" style="" stroke="#000" fill="#26b99a" ry="0" rx="0" r="0" height="117.44356271906621" width="24.1875" y="84.82973949839221" x="328.5625"></rect><rect stroke-width="0" style="" stroke="#000" fill="#26b99a" ry="0" rx="0" r="0" height="139.15954224070487" width="24.1875" y="63.113759976753556" x="397.0625"></rect><rect stroke-width="0" style="" stroke="#000" fill="#26b99a" ry="0" rx="0" r="0" height="153.7845896736452" width="24.1875" y="48.48871254381322" x="465.5625"></rect><rect stroke-width="0" style="" stroke="#000" fill="#26b99a" ry="0" rx="0" r="0" height="127.19359434102643" width="24.1875" y="75.079707876432" x="534.0625"></rect><rect stroke-width="0" style="" stroke="#000" fill="#26b99a" ry="0" rx="0" r="0" height="106.36398133047506" width="24.1875" y="95.90932088698337" x="602.5625"></rect><rect stroke-width="0" style="" stroke="#000" fill="#26b99a" ry="0" rx="0" r="0" height="93.51166691970933" width="24.1875" y="108.7616352977491" x="671.0625"></rect></svg><div style="left: 158.75px; top: 101px; display: none;" class="morris-hover morris-default-style"><div class="morris-hover-row-label">Mar</div><div class="morris-hover-point" style="color: #26B99A">
                                    Hours worked:
                                    176
                                </div><div class="morris-hover-point" style="color: #34495E">
                                    SORN:
                                    -
                                </div></div></div>
                        <!-- end of user-activity-graph -->

                        <div class="" role="tabpanel" data-example-id="togglable-tabs">
                            <ul id="myTab" class="nav nav-tabs bar_tabs" role="tablist">
                                <li role="presentation" class=""><a href="#tab_content1" id="home-tab" role="tab" data-toggle="tab" aria-expanded="false">Recent Activity</a>
                                </li>
                                <li role="presentation" class="active"><a href="#tab_content2" role="tab" id="profile-tab" data-toggle="tab" aria-expanded="true">Projects Worked on</a>
                                </li>
                                <li role="presentation" class=""><a href="#tab_content3" role="tab" id="profile-tab2" data-toggle="tab" aria-expanded="false">Profile</a>
                                </li>
                            </ul>
                            <div id="myTabContent" class="tab-content">
                                <div role="tabpanel" class="tab-pane fade" id="tab_content1" aria-labelledby="home-tab">

                                    <!-- start recent activity -->
                                    <ul class="messages">
                                        <li>
                                            <img src="<%=request.getContextPath()%>/assets/images/img.jpg" class="avatar" alt="Avatar">
                                            <div class="message_date">
                                                <h3 class="date text-info">24</h3>
                                                <p class="month">May</p>
                                            </div>
                                            <div class="message_wrapper">
                                                <h4 class="heading">Desmond Davison</h4>
                                                <blockquote class="message">Raw denim you probably haven't heard of them jean shorts Austin. Nesciunt tofu stumptown aliqua butcher retro keffiyeh dreamcatcher synth.</blockquote>
                                                <br>
                                                <p class="url">
                                                    <span class="fs1 text-info" aria-hidden="true" data-icon=""></span>
                                                    <a href="#"><i class="fa fa-paperclip"></i> User Acceptance Test.doc </a>
                                                </p>
                                            </div>
                                        </li>
                                        <li>
                                            <img src="<%=request.getContextPath()%>/assets/images/img.jpg" class="avatar" alt="Avatar">
                                            <div class="message_date">
                                                <h3 class="date text-error">21</h3>
                                                <p class="month">May</p>
                                            </div>
                                            <div class="message_wrapper">
                                                <h4 class="heading">Brian Michaels</h4>
                                                <blockquote class="message">Raw denim you probably haven't heard of them jean shorts Austin. Nesciunt tofu stumptown aliqua butcher retro keffiyeh dreamcatcher synth.</blockquote>
                                                <br>
                                                <p class="url">
                                                    <span class="fs1" aria-hidden="true" data-icon=""></span>
                                                    <a href="#" data-original-title="">Download</a>
                                                </p>
                                            </div>
                                        </li>
                                        <li>
                                            <img src="<%=request.getContextPath()%>/assets/images/img.jpg" class="avatar" alt="Avatar">
                                            <div class="message_date">
                                                <h3 class="date text-info">24</h3>
                                                <p class="month">May</p>
                                            </div>
                                            <div class="message_wrapper">
                                                <h4 class="heading">Desmond Davison</h4>
                                                <blockquote class="message">Raw denim you probably haven't heard of them jean shorts Austin. Nesciunt tofu stumptown aliqua butcher retro keffiyeh dreamcatcher synth.</blockquote>
                                                <br>
                                                <p class="url">
                                                    <span class="fs1 text-info" aria-hidden="true" data-icon=""></span>
                                                    <a href="#"><i class="fa fa-paperclip"></i> User Acceptance Test.doc </a>
                                                </p>
                                            </div>
                                        </li>
                                        <li>
                                            <img src="<%=request.getContextPath()%>/assets/images/img.jpg" class="avatar" alt="Avatar">
                                            <div class="message_date">
                                                <h3 class="date text-error">21</h3>
                                                <p class="month">May</p>
                                            </div>
                                            <div class="message_wrapper">
                                                <h4 class="heading">Brian Michaels</h4>
                                                <blockquote class="message">Raw denim you probably haven't heard of them jean shorts Austin. Nesciunt tofu stumptown aliqua butcher retro keffiyeh dreamcatcher synth.</blockquote>
                                                <br>
                                                <p class="url">
                                                    <span class="fs1" aria-hidden="true" data-icon=""></span>
                                                    <a href="#" data-original-title="">Download</a>
                                                </p>
                                            </div>
                                        </li>

                                    </ul>
                                    <!-- end recent activity -->

                                </div>
                                <div role="tabpanel" class="tab-pane fade active in" id="tab_content2" aria-labelledby="profile-tab">

                                    <!-- start user projects -->
                                    <table class="data table table-striped no-margin">
                                        <thead>
                                            <tr>
                                                <th>#</th>
                                                <th>Project Name</th>
                                                <th>Client Company</th>
                                                <th class="hidden-phone">Hours Spent</th>
                                                <th>Contribution</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>1</td>
                                                <td>New Company Takeover Review</td>
                                                <td>Deveint Inc</td>
                                                <td class="hidden-phone">18</td>
                                                <td class="vertical-align-mid">
                                                    <div class="progress">
                                                        <div aria-valuenow="35" style="width: 35%;" class="progress-bar progress-bar-success" data-transitiongoal="35"></div>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>2</td>
                                                <td>New Partner Contracts Consultanci</td>
                                                <td>Deveint Inc</td>
                                                <td class="hidden-phone">13</td>
                                                <td class="vertical-align-mid">
                                                    <div class="progress">
                                                        <div aria-valuenow="15" style="width: 15%;" class="progress-bar progress-bar-danger" data-transitiongoal="15"></div>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>3</td>
                                                <td>Partners and Inverstors report</td>
                                                <td>Deveint Inc</td>
                                                <td class="hidden-phone">30</td>
                                                <td class="vertical-align-mid">
                                                    <div class="progress">
                                                        <div aria-valuenow="45" style="width: 45%;" class="progress-bar progress-bar-success" data-transitiongoal="45"></div>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>4</td>
                                                <td>New Company Takeover Review</td>
                                                <td>Deveint Inc</td>
                                                <td class="hidden-phone">28</td>
                                                <td class="vertical-align-mid">
                                                    <div class="progress">
                                                        <div aria-valuenow="75" style="width: 75%;" class="progress-bar progress-bar-success" data-transitiongoal="75"></div>
                                                    </div>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    <!-- end user projects -->

                                </div>
                                <div role="tabpanel" class="tab-pane fade" id="tab_content3" aria-labelledby="profile-tab">
                                    <p>xxFood truck fixie locavore, accusamus mcsweeney's marfa nulla single-origin coffee squid. Exercitation +1 labore velit, blog sartorial PBR leggings next level wes anderson artisan four loko farm-to-table craft beer twee. Qui photo booth letterpress, commodo enim craft beer mlkshk </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        setTitle("View Profile");
    });
</script>