<%-- 
    Document   : Error Page - All Users
    Created on : Dec 26, 2016, 07:59:28 PM
    Author     : Yoseph Berhanu<yoseph@artisan.et> 
--%>
<%@page import="java.util.ArrayList"%>
<%
    if (request.getSession().getAttribute("message") != null && request.getSession().getAttribute("message") instanceof ArrayList) {
        ArrayList<String> message = (ArrayList<String>) request.getSession().getAttribute("message");
        String output = "<ul>";
        for (int i = 0; i < message.size(); i++) {
            output += "<li>" + message.get(i) + "</li>";
        }
        output += "</ul>";
%>
<script type="text/javascript">
    $(function () {
        new PNotify({
            title: '<%=request.getSession().getAttribute("messageTitle")%>',
            text: '<%=output%>',
            type: '<%=request.getSession().getAttribute("messageType")%>'
        });
    });

</script>
<%
        request.getSession().removeAttribute("messageType");
        request.getSession().removeAttribute("messageTitle");
        request.getSession().removeAttribute("message");
    }
%>