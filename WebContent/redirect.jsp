<%-- 
    Document   : Redirect Page - All Users
    Created on : Dec 26, 2016, 09:32:28 PM
    Author     : Yoseph Berhanu<yoseph@artisan.et> 
--%>
<%@page import="et.artisan.cn.cps.util.Constants"%>
<%
    response.sendRedirect(request.getContextPath() + "/All?a=" + Constants.ACTION_ALL_WELCOME);
%>