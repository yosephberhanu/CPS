<%-- 
    Document   : Documents List
    Created on : Feb 15, 2017, 03:22:15 PM
    Author     : Yoseph Berhanu <yoseph@artisan.et>
--%>
<%@page import="et.artisan.cn.cps.util.CommonTasks"%>
<%@page import="et.artisan.cn.cps.entity.*"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="et.artisan.cn.cps.util.Constants"%>
<%@page import="et.artisan.cn.cps.util.CommonStorage"%>
<%
	double amount = 0;
	String cnnumber = null;
	String id = null;
	if (request.getParameter("amount") != null) {
		amount = Double.parseDouble(request.getParameter("amount"));
	}
	if (request.getParameter("cnnumber") != null) {
		cnnumber = request.getParameter("cnnumber");
	}
	if (request.getParameter("id") != null) {
		id = request.getParameter("id");
	}
%>
<div class="">
	<div class="clearfix"></div>
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="x_title">
					<h2>Documents List</h2>
					<ul class="nav navbar-right panel_toolbox">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown" role="button" aria-expanded="false"> <i
								class="fa fa-gear"></i> Action
						</a>
							<ul class="dropdown-menu" role="menu">
								<li><a
									href="<%=request.getContextPath()%>/DataEntry?a=<%=Constants.ACTION_DATAENTRY_ADD_DOCUMENT%>"
									alt="Add New" title="Add New Document"><i
										class="fa fa-plus"></i> Add New</a></li>
								<li><a id="viewButton" class="single" href="#"
									alt="View Document" title="View the selected document"><i
										class="fa fa-search"></i> View</a></li>
								<li><a id="editButton" class="single" href="#"
									alt="Edit Document" title="Edit the selected document"><i
										class="fa fa-edit"></i> Edit</a></li>
								<li><a id="paymentButton" class="single" href="#"
									alt="Payments" title="Payments for the selected document"><i
										class="fa fa-money"></i> Payment</a></li>
								<li><a id="deleteButton" href="#" alt="Delete Document(s)"
									title="Delete the selected document(s)"><i
										class="fa fa-remove"></i> Delete</a></li>
								<%--li><a href="<%=request.getContextPath()%>/DataEntry?a=<%=Constants.ACTION_DATAENTRY_BRANCHES_CARD%>" alt="Card View" title="Show Card View of Branches"><i class="fa fa-image"></i> Card View</a></li--%>
							</ul></li>
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<table id="documentsdataTable"
						class="table table-striped responsive-utilities jambo_table"
						data-viewURL="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_VIEW_DOCUMENT%>">
						<thead>
							<tr class="headings">
								<th>Id</th>
								<th>Document No</th>
								<th>Project</th>
								<th>Client</th>
								<th>Client Region</th>
								<th>Amount / Registered / Paid</th>
								<th>Status</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
			<br /> <br /> <br />
		</div>
		<!-- /Content-->
	</div>
	<script type="text/javascript">
    $(document).ready(function () {
        <%String url = request.getContextPath() + "/DataEntry?a=";
				url += Constants.ACTION_DATAENTRY_DOCUMENTS + "&json=true";
				url += amount > 0 ? "&amount=" + amount : "";
				url += cnnumber != null ? "&cnnumber=" + cnnumber : "";
				url += id != null ? "&id=" + id : "";%>
        
		$("#documentsdataTable").DataTable({
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
				{ "width": "5%" },
				{ "width": "15%" },
				{ "width": "25%" },
				{ "width": "5%" },
				{ "width": "10%" },
				{ "width": "30%" },
				{ "width": "5%" }
			  ],
	        fixedColumns: true

		});
        $('#documentsdataTable tbody').on('click', 'tr', function () {
            $(this).toggleClass('selected');
            var selectedRowsCount = $('#documentsdataTable tr.selected').length;
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
       $('#documentsdataTable tbody').on('dblclick', 'tr', function () {
    	   var row = $(this);
    	    $('#documentsdataTable tbody tr').each(function () {
                $(this).toggleClass('selected', false);
            });
            if ($('#documentsdataTable tbody .dataTables_empty').size() < 1) {
                window.location = $("#documentsdataTable").attr("data-viewURL") + "&i=" + row.find("td:first").text();
            }

        });
       
       setTitle("Documents");
       $("#viewButton").click(function () {
           window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_VIEW_DOCUMENT + "&i="%>" + $('#documentsdataTable tr.selected').find("td:first").text();
       });
       $("#editButton").click(function () {
           window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_EDIT_DOCUMENT + "&i="%>" + $('#documentsdataTable tr.selected').find("td:first").text();
       });
       $("#deleteButton").click(function () {
           bootbox.confirm("Are you sure you want to delete the selected document(s)?", function (result) {
               if (result) {
                   var selectedRowsCount = $('#documentsdataTable tr.selected').length;
                   var param = "";
                   if (selectedRowsCount >= 1) {
                       $("#documentsdataTable tr.selected").each(function () {
                           param = param.concat("i=").concat($(this).find("td:first").text()).concat("&");
                       });
                       param = param.substring(0, param.length - 1);
                       alert("<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_DELETE_DOCUMENT%>&" + param);
                       window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_DELETE_DOCUMENT%>&" + param;
                   }
               }

           });
        });
    });
    </script>