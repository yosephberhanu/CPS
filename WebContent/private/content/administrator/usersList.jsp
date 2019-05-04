<%-- 
    Document   : Users List
    Created on : Sep 21, 2016, 9:03:26 AM
    Author     : Yoseph Berhanu <yoseph@artisan.et>
--%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="et.artisan.cn.cps.util.Constants"%>
<%@page import="et.artisan.cn.cps.entity.Role"%>
<%@page import="et.artisan.cn.cps.entity.User"%>
<%@page import="et.artisan.cn.cps.util.CommonStorage"%>
<%
    ArrayList<User> users = (ArrayList<User>) request.getAttribute("users");
%>
<div class="">
    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>User Accounts</h2>  
                    <ul class="nav navbar-right panel_toolbox">
                        <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
                                <i class="fa fa-gear"></i> Action</a>
                            <ul class="dropdown-menu" role="menu">
                                <li><a href="<%=request.getContextPath()%>/Administrator?a=<%=Constants.ACTION_ADMINISTRATOR_ADD_USER%>" alt="Add New" title="Add New User Accounts"><i class="fa fa-plus"></i> Add New</a></li>
                                <li><a id ="editButton" class="single" href="#" alt="Edit User Account" title="Edit the selected account"><i class="fa fa-edit"></i> Edit</a></li>
                                <li><a id ="viewButton" class="single" href="#" alt="View User Account" title="View the selected account"><i class="fa fa-search"></i> View</a></li>
                                <li><a id ="deleteButton" href="#" alt="Delete User Account" title="Delete the selected accounts"><i class="fa fa-remove"></i> Delete</a></li>
                                <li><a id ="lockButton" class="multiple" href="#" alt="Lock User Account" title="Lock the selected accounts"><i class="fa fa-lock"></i> Lock</a></li>
                                <li><a href="<%=request.getContextPath()%>/Administrator?a=<%=Constants.ACTION_ADMINISTRATOR_USERS_CARD%>" alt="Card View" title="Show Card View of User Accounts"><i class="fa fa-image"></i> Card View</a></li>
                            </ul>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <table id="usersDataTable" class="table table-striped responsive-utilities jambo_table" data-viewURL="<%=request.getContextPath() + "/Administrator?a=" + Constants.ACTION_ADMINISTRATOR_VIEW_USER%>">
                        <thead>
                            <tr class="headings">
                                <th>Id </th>
                                <th>Username </th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Primary Phone #</th>
                                <th>Roles</th>
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
        	$("#deleteButton").click(function () {
                bootbox.confirm("Are you sure you want to delete the selected user(s)?", function (result) {
                    if (result) {
                        var selectedRowsCount = $('#usersDataTable tr.selected').length;
                        var param = "";
                        if (selectedRowsCount >= 1) {
                            $("#usersDataTable tr.selected").each(function () {
                                param = param.concat("i=").concat($(this).find("td:first").text()).concat("&");
                            });
                            param = param.substring(0, param.length - 1);
                            window.location = "<%=request.getContextPath() + "/Administrator?a=" + Constants.ACTION_ADMINISTRATOR_DELETE_USER%>&" + param;
                        }
                    }

                });

            });
            $("#usersDataTable").DataTable({
    	    	"bServerSide": true,
    			"sAjaxSource": "<%=request.getContextPath()+"/Administrator?a="+Constants.ACTION_ADMINISTRATOR_USERS + "&json=true"%>",
    	        "language": {processing: '<i class="fa fa-spinner fa-spin fa-3x fa-fw"></i><span class="sr-only">Loading...</span> '},
    	        "bProcessing": true,
    	        "paging": true,
    	        "bFilter": true,
    	        "sPaginationType": "full_numbers",
    	        "bPaginate": true,
    			"iDisplayLength" : 10,"columns": [
    				{ "width": "3%" },
    				{ "width": "10%" },
    			    { "width": "20%" },
    			    { "width": "22%" },
    			    { "width": "20%" },
    			    { "width": "25%" },
    			  ],
    	        fixedColumns: true

    		});
            $('#usersDataTable tbody').on('click', 'tr', function () {
                $(this).toggleClass('selected');
                var selectedRowsCount = $('#usersDataTable tr.selected').length;
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
           $('#usersDataTable tbody').on('dblclick', 'tr', function () {
        	   var row = $(this);
        	    $('#usersDataTable tbody tr').each(function () {
                    $(this).toggleClass('selected', false);
                });
                if ($('#usersDataTable tbody .dataTables_empty').size() < 1) {
                    window.location = $("#usersDataTable").attr("data-viewURL") + "&i=" + row.find("td:first").text();
                }

            });
           
           
           $("#editButton").click(function () {
                window.location = "<%=request.getContextPath() + "/Administrator?a=" + Constants.ACTION_ADMINISTRATOR_EDIT_USER + "&i="%>" + $('#usersDataTable tr.selected').find("td:first").text();
            });
            $("#viewButton").click(function () {
                window.location = "<%=request.getContextPath() + "/Administrator?a=" + Constants.ACTION_ADMINISTRATOR_VIEW_USER + "&i="%>" + $('#usersDataTable tr.selected').find("td:first").text();
            });
            
        });
        $(document).ready(function () {
            setTitle("Users");
        });
    </script>