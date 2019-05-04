<%@page import="et.artisan.cn.cps.util.*"%>
<%-- 
    Document   : Menu for administrator - Administrator
    Created on : Dec 26, 2016, 09:26:16 PM
    Author     : Yoseph Berhanu<yoseph@artisan.et> 
--%>
<li>
    <a>
        <i class="fa fa-users"></i> Administrator <span class="fa fa-chevron-down"></span>
    </a>
    <ul class="nav child_menu" style="display: none">
        <!--li>
            <a href="< % =request.getContextPath()+"/Administrator?a="+Constants.ACTION_ADMINISTRATOR_DASHBOARD%>"><span class="fa fa-dashboard"></span>Dashboard</a>
        </li-->
        <li>
            <a href="<%=request.getContextPath()+"/Administrator?a="+Constants.ACTION_ADMINISTRATOR_USERS%>"><span class="fa fa-users"></span>User Accounts</a>
        </li>
        <!-- li>
            <a href="< %=request.getContextPath()+"/Administrator?a="+Constants.ACTION_ADMINISTRATOR_LOOKUP%>"><span class="fa fa-table"></span>Lookups</a>
        </li>
        <li>
            <a href="< %=request.getContextPath()+"/Administrator?a="+Constants.ACTION_ADMINISTRATOR_LOG%>"><span class="fa fa-bar-chart"></span>Reports & Logs</a>
        </li>
        <li>
            <a href="< %=request.getContextPath()+"/Administrator?a="+Constants.ACTION_ADMINISTRATOR_SETTINGS%>"><span class="fa fa-wrench"></span>System Settings</a>
        </li -->
    </ul>
</li>
