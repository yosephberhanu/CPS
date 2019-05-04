<%@page import="et.artisan.cn.cps.util.*"%>
<%-- 
    Document   : Menu for Data Entry - Data Entry
    Created on : Jan 23, 2016, 10:43:30 PM
    Author     : Yoseph Berhanu<yoseph@artisan.et> 
--%>
<li>
    <a>
        <i class="fa fa-keyboard-o"></i> Data Entry <span class="fa fa-chevron-down"></span>
    </a>
    <ul class="nav child_menu" style="display: none">
        <li>
            <a href="<%=request.getContextPath()%>/DataEntry?a=<%=Constants.ACTION_DATAENTRY_BRANCHES%>"><span class="fa fa-sitemap"></span>Paying Branches</a>
        </li>
        <li>
            <a href="<%=request.getContextPath()%>/DataEntry?a=<%=Constants.ACTION_DATAENTRY_CLIENTS%>"><span class="fa fa-building"></span>Clients</a>
        </li>
        <li>
            <a href="<%=request.getContextPath()%>/DataEntry?a=<%=Constants.ACTION_DATAENTRY_CLIENT_REGIONS%>"><span class="fa fa-map"></span>Client Regions</a>
        </li>
        <li>
            <a href="<%=request.getContextPath()%>/DataEntry?a=<%=Constants.ACTION_DATAENTRY_PROJECTS%>"><span class="fa fa-road"></span>Projects</a>
        </li>
        <li>
            <a href="<%=request.getContextPath()%>/DataEntry?a=<%=Constants.ACTION_DATAENTRY_DOCUMENTS%>"><span class="fa fa-book"></span>Documents</a>
        </li>
        <%--<li>
            <a href="<%=request.getContextPath()%>/DataEntry?a=<%=Constants.ACTION_DATAENTRY_PAYMENTS%>"><span class="fa fa-money"></span>Payments</a>
        </li>--%>
        <li>
            <a href="<%=request.getContextPath()%>/DataEntry?a=<%=Constants.ACTION_DATAENTRY_CLAIMS%>"><span class="fa fa-money"></span>Claims</a>
        </li>
        <%--li>
            <a href="<%=request.getContextPath()%>/DataEntry?a=<%=Constants.ACTION_DATAENTRY_AMENDMENTS%>"><span class="fa fa-plus-circle"></span>Amendments</a>
        </li>
        <li>
            <a href="<%=request.getContextPath()%>/DataEntry?a=<%=Constants.ACTION_DATAENTRY_ADD_IMPORT%>"><span class="fa fa-plus-circle"></span>Import</a>
        </li --%>
    </ul>
</li>
