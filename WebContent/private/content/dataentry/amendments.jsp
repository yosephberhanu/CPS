<%-- 
    Document   : Amendments List
    Created on : Jul 14, 2017, 01:00:15 PM
    Author     : Yoseph Berhanu <yoseph@artisan.et>
--%>
<%@page import="et.artisan.cn.cps.entity.*"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="et.artisan.cn.cps.util.Constants"%>
<%@page import="et.artisan.cn.cps.util.CommonStorage"%>
<%
    ArrayList<Amendment> amendments = (ArrayList<Amendment>) request.getAttribute("amendments");
%>
<div class="">
    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Amendments </h2>  
                    <ul class="nav navbar-right panel_toolbox">
                        <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
                                <i class="fa fa-gear"></i> Action</a>
                            <ul class="dropdown-menu" role="menu">
                                <li><a href="<%=request.getContextPath()%>/DataEntry?a=<%=Constants.ACTION_DATAENTRY_ADD_AMENDMENT%>" alt="Post New" title="Add New Amendment"><i class="fa fa-plus"></i> Add New</a></li>
                                <li><a id ="viewButton" class="single" href="#" alt="View Amendment" title="View the selected amendment"><i class="fa fa-search"></i> View</a></li>
                                <li><a id ="deleteButton" href="#" alt="Delete Amendment(s)" title="Delete the selected amendment(s)"><i class="fa fa-remove"></i> Delete</a></li>
                            </ul>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <table id="dataTable" class="table table-striped responsive-utilities jambo_table" data-viewURL="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_VIEW_AMENDMENT%>">
                        <thead>
                            <tr class="headings">
                                <th>Payment</th>
                                <th>Total Amount</th>
                                <th>Amendment Amount</th>
                                <th>Amendment On</th>
                                <th>Remark</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                int j = 0;
                                for (int i = 0; i < amendments.size(); i++) {
                                    if (j % 2 == 0) {
                                        out.println("<tr class='even pointer' data-id='" + amendments.get(i).getId() + "'>");
                                    } else {
                                        out.println("<tr class='odd pointer'  data-id='" + amendments.get(i).getId() + "'>");
                                    }
                                    j++;
                                    out.println("<td class=''>" + amendments.get(i).getPayment().getName() + " - " + amendments.get(i).getPayment().getAmount() + "</td>");
                                    out.println("<td class=''>" + amendments.get(i).getPayment().getAmount()+ "</td>");
                                    out.println("<td class=''>" + amendments.get(i).getAmount() + "</td>");
                                    out.println("<td class=''>" + amendments.get(i).getRegisteredOn()+ "</td>");
                                    out.println("<td class=''>" + amendments.get(i).getRemark() + "</td>");
                                    out.println("</tr>");

                                }%>
                        </tbody>
                    </table>
                </div>
            </div>
            <br />
            <br />
            <br />
        </div>
        <!-- /Content-->
    </div>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#viewButton").click(function () {
                window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_VIEW_AMENDMENT + "&i="%>" + $('#dataTable tr.selected').attr("data-id");
            });
            $("#deleteButton").click(function () {
                bootbox.confirm("Are you sure you want to delete the selected amendment (s)?", function (result) {
                    if (result) {
                        var selectedRowsCount = $('#dataTable tr.selected').length;
                        var param = "";
                        if (selectedRowsCount >= 1) {
                            $("#dataTable tr.selected").each(function () {
                                param = param.concat("i=").concat($(this).attr('data-id')).concat("&");
                            });
                            param = param.substring(0, param.length - 1);
                            console.log(param);
                            window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_DELETE_AMENDMENT%>&" + param;
                        }
                    }
                });

            });
            setTitle("Amendments");
        });
    </script>