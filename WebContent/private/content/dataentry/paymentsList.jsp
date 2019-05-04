<%-- 
    Document   : Payment List
    Created on : Feb 22, 2017, 10:17:15 AM
    Author     : Yoseph Berhanu <yoseph@artisan.et>
--%>
<%@page import="et.artisan.cn.cps.util.CommonTasks"%>
<%@page import="java.util.Map"%>
<%@page import="et.artisan.cn.cps.entity.*"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="et.artisan.cn.cps.util.Constants"%>
<%@page import="et.artisan.cn.cps.util.CommonStorage"%>
<%	
	long branchId = 0;
	long projectId = 0;
	long documentId = 0;
	if (request.getParameter("b") != null) {
        branchId = Long.parseLong(request.getParameter("b"));
    }
    if (request.getParameter("p") != null) {
        projectId = Long.parseLong(request.getParameter("p"));
    }
    if (request.getParameter("d") != null) {
        documentId = Long.parseLong(request.getParameter("d"));
    }
%>
<div class="">
    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2 class="col-lg-3">Payment List</h2>
                    <ul class="col-lg-2 nav navbar-right panel_toolbox">
                        <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
                                <i class="fa fa-gear"></i> Action</a>
                            <ul class="dropdown-menu" role="menu">
                                <li><a href="<%=request.getContextPath()%>/DataEntry?a=<%=Constants.ACTION_DATAENTRY_ADD_PAYMENT + "&p=" + projectId + "&d=" + documentId%>" alt="Add New" title="Add New Payment"><i class="fa fa-plus"></i> Add New</a></li>
                                <li><a id ="viewButton" class="single" href="#" alt="View Payment" title="View the selected payment"><i class="fa fa-search"></i> View</a></li>
                                <li><a id ="editButton" class="single" href="#" alt="Edit Payment" title="Edit the selected payment"><i class="fa fa-edit"></i> Edit</a></li>
                                <li><a id ="amendButton" class="single" href="#" alt="Amend Payment" title="Amend the selected payment"><i class="fa fa-edit"></i> Amend</a></li>
                                <li><a id ="deleteButton" href="#" alt="Delete Payment(s)" title="Delete the selected payment(s)"><i class="fa fa-remove"></i> Delete</a></li>                                
                                    <%--li><a href="<%=request.getContextPath()%>/DataEntry?a=<%=Constants.ACTION_DATAENTRY_BRANCHES_CARD%>" alt="Card View" title="Show Card View of Branches"><i class="fa fa-image"></i> Card View</a></li--%>
                            </ul>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <table id="paymentsdataTable" class="table table-striped responsive-utilities jambo_table" data-viewURL="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_VIEW_PAYMENT%>">
                        <thead>
                            <tr class="headings">
                                <th>ID </th>
                                <th>Document No. </th>
                                <th>Lot #</th>
                                <th>Name</th>
                                <th>Amount</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
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
                window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_VIEW_PAYMENT + "&i="%>" + $('#paymentsdataTable tr.selected').find("td:first").text();
            });
            $("#editButton").click(function () {
                window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_EDIT_PAYMENT + "&i="%>" + $('#paymentsdataTable tr.selected').find("td:first").text();
            });
            $("#amendButton").click(function () {
                window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_ADD_AMENDMENT + "&p="%>" + $('#paymentsdataTable tr.selected').find("td:first").text();
            });
            $("#deleteButton").click(function () {
                bootbox.confirm("Are you sure you want to delete the selected payment(s)?", function (result) {
                    if (result) {
                        var selectedRowsCount = $('#paymentsdataTable tr.selected').length;
                        var param = "";
                        if (selectedRowsCount >= 1) {
                            $("#paymentsdataTable tr.selected").each(function () {
                                param = param.concat("i=").concat($(this).find("td:first").text()).concat("&");
                            });
                            param = param.substring(0, param.length - 1);
                            alert("<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_DELETE_PAYMENT%>&" + param);
                            window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_DELETE_PAYMENT%>&" + param;
                        }
                    }

                });

            });
            <%
            	String url = request.getContextPath()+"/DataEntry?a=";
            		url += Constants.ACTION_DATAENTRY_PAYMENTS + "&json=true";
            		url += branchId>0?"&b="+branchId:"";
            		url += projectId>0?"&p="+projectId:"";
            		url += documentId>0?"&d="+documentId:"";
            %>
            $("#paymentsdataTable").DataTable({
    	    	"bServerSide": true,
    			"sAjaxSource": "<%=url%>",
    	        "language": {processing: '<i class="fa fa-spinner fa-spin fa-3x fa-fw"></i><span class="sr-only">Loading...</span> '},
    	        "bProcessing": true,
    	        "paging": true,
    	        "bFilter": true,
    	        "sPaginationType": "full_numbers",
    	        "bPaginate": true,
    			"iDisplayLength" : 10,
    			"columns": [
    			    null,
    			    null,
    			    null,
    			    { "width": "30%" },
    			    null,
    			    null
    			  ],
    	        fixedColumns: true

    		});
            $('#paymentsdataTable tbody').on('click', 'tr', function () {
                $(this).toggleClass('selected');
                var selectedRowsCount = $('#paymentsdataTable tr.selected').length;
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
           $('#paymentsdataTable tbody').on('dblclick', 'tr', function () {
        	   var row = $(this);
        	    $('#projectsdataTable tbody tr').each(function () {
                    $(this).toggleClass('selected', false);
                });
                if ($('#projectsdataTable tbody .dataTables_empty').size() < 1) {
                    window.location = $("#paymentsdataTable").attr("data-viewURL") + "&i=" + row.find("td:first").text();
                }

            });
            setTitle("Payments");
        });
    </script>