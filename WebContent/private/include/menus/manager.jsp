<%@page import="et.artisan.cn.cps.util.*"%>
<%-- 
    Document   : Menu for manager - Manager
    Created on : Jan 23, 2016, 10:45:19 PM
    Author     : Yoseph Berhanu<yoseph@artisan.et> 
--%>
<li>
    <a>
        <i class="fa fa-bar-chart"></i> Manager <span class="fa fa-chevron-down"></span>
    </a>
    <ul class="nav child_menu" style="display: none">
        <!--li>
            <a href=" < %=request.getContextPath()+"/Manager?a="+Constants.ACTION_MANAGER_DASHBOARD%>"><span class="fa fa-dashboard"></span>Dashboard</a>
        </li-->
        <li>
            <a href="<%=request.getContextPath()+"/Manager?a="+Constants.ACTION_MANAGER_REPORT%>"><span class="fa fa-bar-chart"></span>Report</a>
        </li>
    </ul>
</li>
