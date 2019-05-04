<%-- 
    Document   : applications
    Created on : Feb 14, 2016, 3:28:26 PM
    Author     : Yoseph Berhanu <yoseph@artisan.et>
--%>
<%@page import="et.artisan.cn.cps.entity.User"%>
<%@page import="java.util.*"%>

<%@page import="et.artisan.cn.cps.dto.*"%>
<%@page import="et.artisan.cn.cps.util.*"%>
<%
    ArrayList<User> users = new ArrayList<User>();
%>
<div class="">
    <!--div class="page-title">
        <div class="title_left">
            <h3>User <small> of iWORLAIS at your woreda </small>
            </h3>
        </div>
    </div-->
    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title row">
                    <div class="col-lg-3">
                        <h2> CPS <small> Activity Log</small></h2> &nbsp;
                    </div>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <table id="example" class="table table-striped responsive-utilities jambo_table">
                        <thead>
                            <tr class="headings">
                                <th>Id </th>
                                <th>Name</th>
                                <th>Username </th>
                                <th>Description</th>
                                <th class=" no-link last"><span class="nobr">Action</span>
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                int j = 0;
                                for (int i = 0; i < users.size(); i++) {
                                    if (j % 2 == 0) {
                                        out.println("<tr class='even pointer'>");
                                    } else {
                                        out.println("<tr class='odd pointer'>");
                                    }
                                    j++;
                                    out.println("<td class=''>" + users.get(i).getId()+ "</td>");
                                    out.println("<td class=''>" + users.get(i).getFullName()+ "</td>");
                                    out.println("<td class=''>" + users.get(i).getSexText()+ "</td>");
                                    out.println("<td class=''>" + users.get(i).getUsername()+ "</td>");
                                    out.println("<td class=''>" + users.get(i).getStatus() + "</td>");
                                    out.print("<td class='last'>");
                                    out.println("</td>");
                                    out.println("</tr>");
                                }
                            %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <br />
        <br />
        <br />
    </div>
    <!-- /Content-->
</div>
<script>
    $(".deleteBtn").on("click", function (event) {
        var link = $(this).attr("href");
        event.preventDefault();
        bootbox.confirm("Are you sure you want cancel this Application ? ", function (result) {
            if (result) {
                window.location = link;
            }
        });
        return false;
    });
</script>