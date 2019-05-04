<%-- 
    Document   : Login Page - All Users
    Created on : Dec 26, 2016, 09:33:56 PM
    Author     : Yoseph Berhanu<yoseph@artisan.et> 
--%>
<%@page import="et.artisan.cn.cps.entity.Message"%>
<%@page import="et.artisan.cn.cps.controllers.All"%>
<%@page import="et.artisan.cn.cps.util.Constants"%>
<%@page import="et.artisan.cn.cps.util.*"%>
<%
    if (CommonStorage.getCurrentUser(request) != null) {
        All.logout(request);
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- Meta, title, CSS, favicons, etc. -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>CN  - CPS</title>

        <!-- Bootstrap core CSS -->
        <link href="<%=request.getContextPath()%>/assets/images/artisan_icon.png" rel="icon" type="image/png" >

        <link href="<%=request.getContextPath()%>/assets/css/bootstrap.min.css" rel="stylesheet" />

        <link href="<%=request.getContextPath()%>/assets/fonts/css/font-awesome.min.css" rel="stylesheet" />
        <link href="<%=request.getContextPath()%>/assets/css/animate.min.css" rel="stylesheet" />

        <!-- Custom styling plus plugins -->
        <link href="<%=request.getContextPath()%>/assets/css/custom.css" rel="stylesheet" />
        <link href="<%=request.getContextPath()%>/assets/css/icheck/flat/green.css" rel="stylesheet" />
        <link href="<%=request.getContextPath()%>/assets/images/maps.png" rel="icon" type="image/png" >


        <script src="<%=request.getContextPath()%>/assets/js/jquery.min.js"></script>

        <script type="text/javascript" src="<%=request.getContextPath()%>/assets/js/notify/pnotify.core.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/assets/js/notify/pnotify.buttons.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/assets/js/notify/pnotify.nonblock.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/assets/js/bootbox.js"></script>
        <!--[if lt IE 9]>
            <script src="../assets/js/ie8-responsive-file-warning.js"></script>
            <![endif]-->

        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
              <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
              <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
            <![endif]-->

    </head>

    <body style="background:#F7F7F7;">

        <div class="">
            <a class="hiddenanchor" id="toregister"></a>
            <a class="hiddenanchor" id="tologin"></a>

            <div id="wrapper">
                <div id="login" class="animate form">
                    <section class="login_content">
                        <img src="<%=request.getContextPath()%>/assets/images/cn.png" alt="Log" class="img-rounded" style="width:20em"/>
                        <form class="loginForm" action="<%=request.getContextPath() + "/All"%>" method="POST">
                            <input type="hidden" name="a" value="<%=Constants.ACTION_ALL_LOGIN%>"/>
                            <h1>Login</h1>
                            <div>
                                <input type="text" class="form-control" placeholder="User Name" required="" name="j_username"/>
                            </div>
                            <div>
                                <input type="password" class="form-control" placeholder="Password" required="" name="j_password"/>
                            </div>
                            <div>
                                <input type="submit" class="btn btn-default submit" value="Log In" />
                                <input type="button" class="btn btn-default submit" value="Help" />
                            </div>
                            <div class="clearfix"></div>
                            <div class="separator">
                                <div class="clearfix"></div>
                                <br />
                                <div>
                                    <h1>Commission Payment System</h1>
                                    <p>©2017 All Rights Reserved. Artisan Technologies</p>
                                </div>
                            </div>
                        </form>
                        <!-- form -->
                    </section>
                    <!-- content -->
                </div>
            </div>
        </div>
    </body>
</html>
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