<%-- 
    Document   : Claim List
    Created on : Feb 22, 2017, 10:17:15 AM
    Author     : Yoseph Berhanu <yoseph@artisan.et>
--%>
<%@page import="et.artisan.cn.cps.util.CommonTasks"%>
<%@page import="et.artisan.cn.cps.entity.*"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="et.artisan.cn.cps.util.Constants"%>
<%@page import="et.artisan.cn.cps.util.CommonStorage"%>
<%
	long claimId = 0;
	if (request.getParameter("c") != null) {
		claimId = Long.parseLong(request.getParameter("c"));
	}
	if (request.getParameter("i") != null) {
		claimId = Long.parseLong(request.getParameter("i"));
	}
	if (request.getParameter("cl") != null) {
		claimId = Long.parseLong(request.getParameter("cl"));
	}
   
%>
<div class="">
    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Claim Details</h2>  
                    <ul class="nav navbar-right panel_toolbox">
                        <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
                                <i class="fa fa-gear"></i> Action</a>
                            <ul class="dropdown-menu" role="menu">
                                <li><a href="<%=request.getContextPath()%>/DataEntry?a=<%=Constants.ACTION_DATAENTRY_ADD_CLAIM_DETAIL + "&cl=" + claimId%>" alt="Post New" title="Post New Claim Detail"><i class="fa fa-plus"></i> Post New</a></li>
                                <li><a id ="viewButton" class="single" href="#" alt="View Claim Detail" title="View the selected claim detail"><i class="fa fa-search"></i> View</a></li>
                                <li><a id ="deleteButton" href="#" alt="Delete Claim Detail(s)" title="Delete the selected claim detail(s)"><i class="fa fa-remove"></i> Delete</a></li>
                            </ul>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <table id="claimDetailDataTable" class="table table-striped responsive-utilities jambo_table" data-viewURL="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_VIEW_CLAIM_DETAIL%>">
                        <thead>
                            <tr class="headings">
                                <th>ID</th>
                                <th>Document</th>
                                <th>(Lot)Name</th>
                                <th>Amount</th>
                                <th>Payed On</th>
                                <th>Posted By</th>
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
    	<%
    	String url = request.getContextPath()+"/DataEntry?a=" + Constants.ACTION_DATAENTRY_CLAIM_DETAILS + "&json=true";
    	if(claimId>0){
    		url += "&c="+claimId;
    	}
    	%>
    	$("#claimDetailDataTable").DataTable({
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
				{ "width": "10%" },
				{ "width": "15%" },
				{ "width": "30%" },
			    { "width": "15%" },
			    { "width": "15%" },
			    { "width": "15%" }
			  ],     
			"fixedColumns": true
		});
        $('#claimDetailDataTable tbody').on('click', 'tr', function () {
            $(this).toggleClass('selected');
            var selectedRowsCount = $('#claimDetailDataTable tr.selected').length;
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
       $('#claimDetailDataTable tbody').on('dblclick', 'tr', function () {
    	   var row = $(this);
    	    $('#claimDetailDataTable tbody tr').each(function () {
                $(this).toggleClass('selected', false);
            });
            if ($('#claimDetailDataTable tbody .dataTables_empty').size() < 1) {
                window.location = $("#claimDetailDataTable").attr("data-viewURL") + "&i=" + row.find("td:first").text();
            }

        });
       $("#viewButton").click(function () {
            window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_VIEW_CLAIM_DETAIL + "&i="%>" + $('#claimDetailDataTable tr.selected').find("td:first").text();
        });
        $("#editButton").click(function () {
            window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_VIEW_CLAIM_DETAIL + "&i="%>" + $('#claimDetailDataTable tr.selected').find("td:first").text();
        });
        $("#deleteButton").click(function () {
            bootbox.confirm("Are you sure you want to delete the selected claim detail(s)?", function (result) {
                if (result) {
                    var selectedRowsCount = $('#claimDetailDataTable tr.selected').length;
                    var param = "";
                    if (selectedRowsCount >= 1) {
                        $("#claimDetailDataTable tr.selected").each(function () {
                            param = param.concat("i=").concat($(this).find("td:first").text()).concat("&");
                        });
                        param = param.substring(0, param.length - 1);
                        console.log(param);
                        window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_DELETE_CLAIM_DETAIL%>&" + param;
                    }
                }

            });

        });
        setTitle("Claim Detail");
        });
    
    </script>