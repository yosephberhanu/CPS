<%-- 
    Document   : Master Page - All Logged In Users
    Created on : Dec 26, 2016, 07:59:28 PM
    Author     : Yoseph Berhanu<yoseph@artisan.et> 
--%>
<%@page import="et.artisan.cn.cps.util.*"%>
<%@page import="et.artisan.cn.cps.dto.*"%>
<%@page import="et.artisan.cn.cps.dao.*"%>
<%@page import="et.artisan.cn.cps.entity.*"%>

<%@page import="java.util.*"%>
<%@page pageEncoding="UTF-8"%>
<%@page language="java" contentType="text/html; charset=UTF-8"%>

<%
	CurrentUserDTO currentUser = CommonStorage.getCurrentUser(request);
%>
<!DOCTYPE html>
<html lang="">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- link href="< % = request.getContextPath()%>/assets/images/maps.png" rel="icon" type="image/png" -->
<link
	href="<%=request.getContextPath()%>/assets/fonts/css/font-awesome.min.css"
	rel="stylesheet">

<link href="<%=request.getContextPath()%>/assets/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="<%=request.getContextPath()%>/assets/css/icheck/flat/green.css"
	rel="stylesheet">
<link
	href="<%=request.getContextPath()%>/assets/css/datatables/tools/css/dataTables.tableTools.css"
	rel="stylesheet">

<link href="<%=request.getContextPath()%>/assets/css/custom.css"
	rel="stylesheet">
<link href="<%=request.getContextPath()%>/assets/css/animate.min.css"
	rel="stylesheet">
<link
	href="<%=request.getContextPath()%>/assets/css/bootstrap-switch.css"
	rel="stylesheet">
<link
	href="<%=request.getContextPath()%>/assets/css/select/select2.min.css"
	rel="stylesheet">
<link
	href="<%=request.getContextPath()%>/assets/css/bootstrap-select/bootstrap-select.min.css"
	rel="stylesheet">

<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/js/pdf.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/js/pdf.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/js/ethiopian_cal/eth_dual_cal.jsp"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/js/ethiopian_cal/string.js"></script>

<title>CN - CPS</title>

<!--[if lt IE 9]>
            <script src="../assets/js/ie8-responsive-file-warning.js"></script>
            <![endif]-->

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
              <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
              <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
            <![endif]-->

</head>
<body class="nav-md">
	<div class="container body">
		<div class="main_container">
			<div class="col-md-3 left_col">
				<div class="left_col scroll-view">
					<div class="navbar nav_title" style="border: 0;">
						<a href="<%=request.getContextPath()%>" class="site_title"><i
							class="fa fa-percent"></i> <span>CPS</span></a>
					</div>
					<div class="clearfix"></div>
					<!-- menu profile quick info -->
					<div class="profile">
						<div class="profile_pic">
							<img
								src="<%=request.getContextPath() + "/All?a=" + Constants.ACTION_ALL_GET_PROFILE_PICTURE%>"
								alt="Profile Picture for <%=currentUser.getFullName()%>"
								class="img-circle profile_img" />
							<!--img src="< %=request.getContextPath() + "/All?a=" + Constants.ACTION_ALL_GET_USER_PHOTO + "&i=" + currentUser.getId()%>" alt="..." class="img-circle profile_img"-->
						</div>
						<div class="profile_info">
							<span>Welcome,</span>
							<h2><%=currentUser.getFullName()%></h2>
						</div>
					</div>
					<!-- /menu profile quick info -->
					<br />
					<!-- sidebar menu -->
					<div id="sidebar-menu"
						class="main_menu_side hidden-print main_menu">
						<div class="menu_section">
							<h3>
								You have
								<%=currentUser.getRoles().size()%>
								role(s)
							</h3>
							<ul class="nav side-menu">
								<%
									if (currentUser.hasRole("administrator")) {
								%>
								<jsp:include page="include/menus/administrator.jsp" flush="true" />
								<%
									}
									if (currentUser.hasRole("dataentry")) {
								%>
								<jsp:include page='include/menus/dataentry.jsp' flush="true" />
								<%
									}
									if (currentUser.hasRole("manager")) {
								%>
								<jsp:include page='include/menus/manager.jsp' flush="true" />
								<%
									}
								%>

							</ul>
						</div>
					</div>
					<!-- /sidebar menu -->
				</div>
			</div>
			<!-- top navigation -->

			<div class="top_nav">
				<div class="nav_menu">
					<nav class="" role="navigation">
						<div class="nav toggle">
							<a id="menu_toggle"><i class="fa fa-bars"></i></a>
						</div>
						<ul class="nav navbar-nav navbar-right">
							<li class=""><a href="javascript:;"
								class="user-profile dropdown-toggle" data-toggle="dropdown"
								aria-expanded="false"> <img
									src="<%=request.getContextPath() + "/All?a=" + Constants.ACTION_ALL_GET_PROFILE_PICTURE%>"
									alt="Profile Picture for <%=currentUser.getFullName()%>" /><%=currentUser.getFullName()%>
									<span class=" fa fa-angle-down"></span>
							</a>
								<ul
									class="dropdown-menu dropdown-usermenu animated fadeInDown pull-right">
									<li><a
										href="<%=request.getContextPath() + "/All?a=" + Constants.ACTION_ALL_VIEW_PROFILE%>">
											Profile </a></li>
									<li><a
										href="<%=request.getContextPath()%>/All?a=<%=Constants.ACTION_ALL_HELP%>">Help</a>
									</li>
									<li><a
										href="<%=request.getContextPath()%>/All?a=<%=Constants.ACTION_ALL_LOGOUT%>"><i
											class="fa fa-sign-out pull-right"></i>Log Out</a></li>
								</ul></li>
							<%
								ArrayList<Notification> notifications = CommonStorage.getNotifications(request);
								int notificationsCount = CommonStorage.getUnreadNotificationsCount(request);
							%>
							<li role="presentation" class="dropdown"><a
								href="javascript:;" class="dropdown-toggle info-number"
								data-toggle="dropdown" aria-expanded="false"> <i
									class="fa fa-envelope-o"></i> <%
 	if (notificationsCount > 0) {
 %> <span class="badge bg-green"><%=notificationsCount%></span> <%
 	}
 %>
							</a>
								<ul id="menu1"
									class="dropdown-menu list-unstyled msg_list animated fadeInDown"
									role="menu">
									<%
										for (int i = 0; i < 6 && i < notifications.size(); i++) {
									%>
									<li><a> <span class="image"> <img
												src="<%=request.getContextPath() + "/All?a=" + Constants.ACTION_ALL_GET_PROFILE_PICTURE + "&i="
						+ notifications.get(i).getMessageFrom().getId()%>"
												alt="<%=notifications.get(i).getMessageFrom().getFullName()%>'s Profile Image" />
										</span> <span> <span><%=notifications.get(i).getMessageFrom().getFullName()%></span>
												<span class="time"><%=notifications.get(i).getSentOn()%></span>
										</span> <span class="message"> <%=notifications.get(i).getSubjectShort()%>
										</span>
									</a></li>
									<%
										}
									%>

									<li>
										<div class="text-center">
											<a
												href="<%=request.getContextPath() + "/All?a=" + Constants.ACTION_ALL_VIEW_NOTIFICATIONS%>">
												<strong>See All Alerts</strong> <i class="fa fa-angle-right"></i>
											</a>
										</div>
									</li>
								</ul></li>

						</ul>
					</nav>
				</div>
			</div>
			<!-- /top navigation -->
			<!-- page content -->

			<div class="right_col" role="main">
				<!-- Content-->
				<jsp:include page='<%=request.getAttribute("page").toString()%>'
					flush="true" />
				<!-- /Content-->
				<!-- footer content -->
				<footer>
					<div class="">
						<!--img src='< %=request.getContextPath()%>/assets/images/artisan.png' style="height:35px; vertical-alignment:center;padding-left:5px"  !-->
						<p class="pull-right">
							A Software System by <a href='http://www.artisan.et'
								target="_blank"> Artisan Technologies </a> &copy; <%=CommonStorage.getCurrentYear() %> | <span
								class="lead"> <i class="fa fa-money"></i> Commission
								Payment System
							</span>
						</p>
					</div>
					<div class="clearfix"></div>
				</footer>
				<!-- /footer content -->
			</div>
			<!-- /page content -->
		</div>
	</div>

	<div id="custom_notifications" class="custom-notifications dsp_none">
		<ul class="list-unstyled notifications clearfix"
			data-tabbed_notifications="notif-group">
		</ul>
		<div class="clearfix"></div>
		<div id="notif-group" class="tabbed_notifications"></div>
	</div>
</body>
<script type="text/javascript"	src="<%=request.getContextPath()%>/assets/js/datatables/js/jquery.dataTables.min.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/assets/js/datatables/tools/js/dataTables.tableTools.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/assets/js/datatables/dataTables.buttons.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/assets/js/notify/pnotify.core.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/assets/js/notify/pnotify.buttons.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/assets/js/notify/pnotify.nonblock.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/assets/js/bootbox.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/assets/js/chartjs/chart.min.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/assets/js/progressbar/bootstrap-progressbar.min.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/assets/js/nicescroll/jquery.nicescroll.min.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/assets/js/icheck/icheck.min.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/assets/js/custom.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/assets/js/bootstrap.min.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/assets/js/chartjs/chart.min.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/assets/js/datepicker/daterangepicker.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/assets/js/icheck/icheck.min.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/assets/js/nicescroll/jquery.nicescroll.min.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/assets/js/progressbar/bootstrap-progressbar.min.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/assets/js/moment.min2.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/assets/js/sparkline/jquery.sparkline.min.js"></script>
<!--[if lte IE 8]><script type="text/javascript" src="js/excanvas.min.js"></script><![endif]-->

<script type="text/javascript"	src="<%=request.getContextPath()%>/assets/js/flot/jquery.flot.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/assets/js/flot/jquery.flot.pie.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/assets/js/flot/jquery.flot.orderBars.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/assets/js/flot/jquery.flot.time.min.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/assets/js/flot/date.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/assets/js/flot/jquery.flot.spline.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/assets/js/flot/jquery.flot.stack.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/assets/js/flot/curvedLines.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/assets/js/flot/jquery.flot.resize.js"></script>

<script type="text/javascript"	src="<%=request.getContextPath()%>/assets/js/bootstrap.min.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/assets/js/datatables/tools/js/dataTables.tableTools.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/assets/js/notify/pnotify.core.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/assets/js/notify/pnotify.buttons.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/assets/js/notify/pnotify.nonblock.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/assets/js/bootbox.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/assets/js/bootstrap-select/bootstrap-select.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $('form').submit(function () {
            $(this).find("#submitBtn").attr('disabled', 'disabled');
        });
        $('input').filter(function () {
            return $(this).attr("value") === 'null';
        }).each(function () {
            $(this).attr("value", "");
        });
        $('select').each(function () {
            value = $(this).attr("data-value");
            $(this).val(value);
        });
        var table = $("#dataTable").DataTable({
        	"language": {processing: '<i class="fa fa-spinner fa-spin fa-3x fa-fw"></i><span class="sr-only">Loading...</span> '},
	        "bProcessing": true,
	        "paging": true,
	        "bFilter": true,
	        "sPaginationType": "full_numbers",
	        "bPaginate": true,
            "iDisplayLength": <%=request.getAttribute("paymentsList") != null ? Constants.PAYMENT_ROWS_PAGE : 10%>,
            "bFilter": true
        });
        $('#dataTable tbody').on('click', 'tr', function () {
            $(this).toggleClass('selected');
            var selectedRowsCount = $('#dataTable tr.selected').length;
            if (selectedRowsCount === 0) {
                $("#editButton").closest("li").hide();
                $("#viewButton").closest("li").hide();
                $(".single").closest("li").hide();
                $(".multiple").closest("li").hide();
                $("#deleteButton").closest("li").hide();
            } else if (selectedRowsCount === 1) {
                $("#editButton").closest("li").show();
                $("#viewButton").closest("li").show();
                $(".single").closest("li").show();
                $(".multiple").closest("li").show();
                $("#deleteButton").closest("li").show();
            } else {
                $("#editButton").closest("li").hide();
                $("#viewButton").closest("li").hide();
                $(".single").closest("li").hide();
                $(".multiple").closest("li").show();
                $("#deleteButton").closest("li").show();
            }
        });
        $("#editButton").closest("li").hide();
        $("#viewButton").closest("li").hide();
        $("#deleteButton").closest("li").hide();
        $(".single").closest("li").hide();
        $(".multiple").closest("li").hide();

        $('#dataTable tbody').on('dblclick', 'tr', function () {
            var row = $(this);
            $('#dataTable tbody tr').each(function () {
                $(this).toggleClass('selected', false);
            });
            if ($('#dataTable tbody .dataTables_empty').size() < 1) {
                window.location = $("#dataTable").attr("data-viewURL") + "&i=" + row.attr("data-id");
            }

        });
    });


</script>
<%
	if (CommonTasks.readMessage(request) != null) {
		Message message = CommonTasks.readMessage(request);
		String output = "<ul>";
		for (int i = 0; i < message.getDetails().size(); i++) {
			output += "<li>" + message.getDetails().get(i) + "</li>";
		}
		output += "</ul>";
%>
<script type="text/javascript">

    $(function () {
        new PNotify({
            title: '<%=message.getName()%>',
            text: '<%=output%>',
            type: '<%=message.getTypeText()%>'
		});
	});
</script>
<%
	request.getSession().removeAttribute("message");
	}
%>
<script type="text/javascript">
	function setTitle(title) {
		document.title = "CN - CPS | " + title;
	}
	function numberWithCommas(value) {
		if (value.toString() === value.toLocaleString()) {
			var parts = value.toString().split('.');
			parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ',');
			value = parts[1] ? parts.join('.') : parts[0];
		} else {
			value = value.toLocaleString();
		}
		return value;
	}
</script>