<%-- 
    Document   : Client Regions List
    Created on : Feb 6, 2017, 4:09:26 PM
    Author     : Yoseph Berhanu <yoseph@artisan.et>
--%>
<%@page import="et.artisan.cn.cps.entity.ClientRegion"%>
<%@page import="java.util.*"%>
<%@page import="et.artisan.cn.cps.util.*"%>
<%@page pageEncoding="UTF-8" %>
<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%
	long clientId = 0;
	
	if (request.getParameter("c") != null) {
		clientId = Long.parseLong(request.getParameter("c"));
    }
	
%>
<div class="">
    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Client Regions List</h2>  
                    <ul class="nav navbar-right panel_toolbox">
                        <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
                                <i class="fa fa-gear"></i> Action</a>
                            <ul class="dropdown-menu" role="menu">
                                <li><a href="<%=request.getContextPath()%>/DataEntry?a=<%=Constants.ACTION_DATAENTRY_ADD_CLIENT_REGION%>" alt="Add New" title="Add New Client"><i class="fa fa-plus"></i> Add New</a></li>
                                <li><a id ="viewButton" class="single" href="#" alt="View Client Region" title="View the selected client region"><i class="fa fa-search"></i> View</a></li>
                                <li><a id ="editButton" class="single" href="#" alt="Edit Client Region" title="Edit the selected client region"><i class="fa fa-edit"></i> Edit</a></li>
                                <li><a id ="deleteButton" href="#" alt="Delete Client region(s)" title="Delete the selected client region(s)"><i class="fa fa-remove"></i> Delete</a></li>                                

                            </ul>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <table id="clientRegionDataTable" class="table table-striped responsive-utilities jambo_table" data-viewURL="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_VIEW_CLIENT_REGION%>">
                        <thead>
                            <tr class="headings">
								<th>ID </th>
                                <th>Client Name </th>
                                <th>Region Name</th>
                                <th>ስም</th>
                                <th>Contact Person</th>
                                <th>Phone #</th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
            <br/>
            <br/>
            <br/>
        </div>
        <!-- /Content-->
    </div>
    <script type="text/javascript">
    $(document).ready(function () {
    	<%
    	String url = request.getContextPath()+"/DataEntry?a=" + Constants.ACTION_DATAENTRY_CLIENT_REGIONS + "&json=true";
    	if(clientId>0){
    		url += "&c="+clientId;
    	}
    	%>
    	$("#clientRegionDataTable").DataTable({
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
				{ "width": "30%" },
			    { "width": "20%" },
			    { "width": "15%" },
			    { "width": "15%" }
			  ],
	      
			"fixedColumns": true
		});
        $('#clientRegionDataTable tbody').on('click', 'tr', function () {
            $(this).toggleClass('selected');
            var selectedRowsCount = $('#clientRegionDataTable tr.selected').length;
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
       $('#clientRegionDataTable tbody').on('dblclick', 'tr', function () {
    	   var row = $(this);
    	    $('#clientRegionDataTable tbody tr').each(function () {
                $(this).toggleClass('selected', false);
            });
            if ($('#clientRegionDataTable tbody .dataTables_empty').size() < 1) {
                window.location = $("#clientRegionDataTable").attr("data-viewURL") + "&i=" + row.find("td:first").text();
            }

        });
       $("#viewButton").click(function () {
            window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_VIEW_CLIENT_REGION + "&i="%>" + $('#clientRegionDataTable tr.selected').find("td:first").text();
        });
        $("#editButton").click(function () {
            window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_EDIT_CLIENT_REGION + "&i="%>" + $('#clientRegionDataTable tr.selected').find("td:first").text();
        });
        $("#deleteButton").click(function () {
            bootbox.confirm("Are you sure you want to delete the selected client region(s)?", function (result) {
                if (result) {
                    var selectedRowsCount = $('#clientRegionDataTable tr.selected').length;
                    var param = "";
                    if (selectedRowsCount >= 1) {
                        $("#clientRegionDataTable tr.selected").each(function () {
                            param = param.concat("i=").concat($(this).find("td:first").text()).concat("&");
                        });
                        param = param.substring(0, param.length - 1);
                        console.log(param);
                        window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_DELETE_CLIENT_REGION%>&" + param;
                    }
                }

            });

        });
        setTitle("Client Regions");
        });
    </script>