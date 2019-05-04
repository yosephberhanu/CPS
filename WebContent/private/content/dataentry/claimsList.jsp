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

<div class="">
	<div class="clearfix"></div>
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="x_title">
					<h2>Claim List</h2>
					<ul class="nav navbar-right panel_toolbox">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown" role="button" aria-expanded="false"> <i
								class="fa fa-gear"></i> Action </a>
							<ul class="dropdown-menu" role="menu">
								<li><a
									href="<%=request.getContextPath()%>/DataEntry?a=<%=Constants.ACTION_DATAENTRY_ADD_CLAIM%>"
									alt="Add New" title="Add New Claim"><i class="fa fa-plus"></i>
										Add New</a></li>
								<li><a id="viewButton" class="single" href="#"
									alt="View Claim" title="View the selected claim"><i
										class="fa fa-search"></i> View</a></li>
								<li><a id="editButton" class="single" href="#"
									alt="Edit Claim" title="Edit the selected claim"><i
										class="fa fa-edit"></i> Edit</a></li>
								<li><a id="deleteButton" href="#" alt="Delete Claim(s)"
									title="Delete the selected claim(s)"><i
										class="fa fa-remove"></i> Delete</a></li>
							</ul></li>
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<table id="claimsDataTable"
						class="table table-striped responsive-utilities jambo_table"
						data-viewURL="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_VIEW_CLAIM%>">
						<thead>
							<tr class="headings">
								<th>Id</th>
								<th>Claim Doc#</th>
								<th>Claim CN No.</th>
								<th>Arrival Date</th>
								<th>Branch</th>
								<th>Amount/Registered</th>
								<th>Client</th>
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
        	var exact=false;
        	$("#claimsDataTable").DataTable({
    	    	"bServerSide": true,
    			"sAjaxSource": "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_CLAIMS + "&json=true"%>",
    	        "language": {processing: '<i class="fa fa-spinner fa-spin fa-3x fa-fw"></i><span class="sr-only">Loading...</span> '},
    	        "bProcessing": true,
    	        "fnServerParams": function ( aoData ) {
    	            aoData.push( { "name": "exact", "value": exact } );
    	        },
    	        "paging": true,
    	        "bFilter": true,
    	        "sPaginationType": "full_numbers",
    	        "bPaginate": true,
    	        "ordering": false,
    			"iDisplayLength" : 30,
    	        "fixedColumns": true,
    	        "aoColumns": [
                    { "width": "5%"},
                    { "bSortable": false, "width": "10%"},
                    { "bSortable": false, "width": "10%"},
                    { "bSortable": false, "width": "15%"},
                    { "bSortable": false, "width": "20%"},
                    { "bSortable": false, "width": "35%"},
                    { "bSortable": false, "width": "5%"}
                ],
                "dom": 'Blfrtip',
                "buttons": [
                    {
                        text: 'Exact',
                        className:'btn btn-default',
                        action: function ( e, dt, node, config ) {
                        	if(exact){
                        		exact=false;
                        		e.currentTarget.attributes.class.nodeValue='dt-button btn btn-default';
                        	}else{
                        		exact=true;
                        		e.currentTarget.attributes.class.nodeValue='dt-button btn btn-danger';
                        	}
                        	dt.ajax.reload( null, false );
                        }
                    }
                ]
    		});
            $('#claimsDataTable tbody').on('click', 'tr', function () {
                $(this).toggleClass('selected');
                var selectedRowsCount = $('#claimsDataTable tr.selected').length;
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
           $('#claimsDataTable tbody').on('dblclick', 'tr', function () {
        	   var row = $(this);
        	    $('#claimsDataTable tbody tr').each(function () {
                    $(this).toggleClass('selected', false);
                });
                if ($('#claimsDataTable tbody .dataTables_empty').size() < 1) {
                    window.location = $("#claimsDataTable").attr("data-viewURL") + "&i=" + row.find("td:first").text();
                }

            });
           $("#viewButton").click(function () {
                window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_VIEW_CLAIM + "&i="%>" + $('#claimsDataTable tr.selected').find("td:first").text();
            });
            $("#editButton").click(function () {
                window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_EDIT_CLAIM + "&i="%>" + $('#claimsDataTable tr.selected').find("td:first").text();
            });
            $("#deleteButton").click(function () {
                bootbox.confirm("Are you sure you want to delete the selected claim(s)?", function (result) {
                    if (result) {
                        var selectedRowsCount = $('#claimsDataTable tr.selected').length;
                        var param = "";
                        if (selectedRowsCount >= 1) {
                            $("#claimsDataTable tr.selected").each(function () {
                                param = param.concat("i=").concat($(this).find("td:first").text()).concat("&");
                            });
                            param = param.substring(0, param.length - 1);
                            console.log(param);
                            window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_DELETE_CLAIM%>&"+ param;
						}
					}
				});
             });
			setTitle("Claims");
		});
	</script>