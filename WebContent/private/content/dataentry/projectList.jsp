<%-- 
    Document   : Projects List
    Created on : Feb 11, 2017, 08:22:03 AM
    Author     : Yoseph Berhanu <yoseph@artisan.et>
--%>
<%@page pageEncoding="UTF-8" %>
<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="et.artisan.cn.cps.entity.*"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="et.artisan.cn.cps.util.*"%>
<%
    //ArrayList<Project> projects = (ArrayList<Project>) request.getAttribute("projects");
%>
<div class="">
    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Projects List</h2>  
                    <ul class="nav navbar-right panel_toolbox">
                        <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
                                <i class="fa fa-gear"></i> Action</a>
                            <ul class="dropdown-menu" role="menu">
                                <li><a href="<%=request.getContextPath()%>/DataEntry?a=<%=Constants.ACTION_DATAENTRY_ADD_PROJECT%>" alt="Add New" title="Add New Project"><i class="fa fa-plus"></i> Add New</a></li>
                                <li><a id ="editButton" class="single" href="#" alt="Edit Project" title="Edit the selected project"><i class="fa fa-edit"></i> Edit</a></li>
                                <li><a id ="deleteButton" href="#" alt="Delete Project(s)" title="Delete the selected project(s)"><i class="fa fa-remove"></i> Delete</a></li>
                                <li><a id ="viewButton" class="single" href="#" alt="View Project" title="View the selected project"><i class="fa fa-search"></i> View</a></li>
                                    <%--li><a href="<%=request.getContextPath()%>/DataEntry?a=<%=Constants.ACTION_DATAENTRY_BRANCHES_CARD%>" alt="Card View" title="Show Card View of Branches"><i class="fa fa-image"></i> Card View</a></li--%>
                            </ul>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <table id="projectsdataTable" class="table table-striped responsive-utilities jambo_table" data-viewURL="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_VIEW_PROJECT%>">
                        <thead>
                            <tr class="headings">
                                <th>ID</th>
                                <th>Code</th>
                                <th>Name</th>
                                <th>ስም</th>
                                <th>Client</th>
                                <th>Registered On</th>
                            </tr>
                        </thead>
                        
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
        	
        	$("#projectsdataTable").DataTable({
    	    	"bServerSide": true,
    			"sAjaxSource": "<%=request.getContextPath()+"/DataEntry?a=" + Constants.ACTION_DATAENTRY_PROJECTS + "&json=true"%>",
    	        "language": {processing: '<i class="fa fa-spinner fa-spin fa-3x fa-fw"></i><span class="sr-only">Loading...</span> '},
    	        "bProcessing": true,
    	        "paging": true,
    	        "bFilter": true,
    	        "sPaginationType": "full_numbers",
    	        "bPaginate": true,
    			"iDisplayLength" : 10,
    			"columns": [
    				{ "width": "5%" },
    				{ "width": "10%" },
    				{ "width": "25%" },
    			    { "width": "25%" },
    			    { "width": "10%" },
    			    { "width": "20%" }
    			  ],
    	        fixedColumns: true

    		});
            $('#projectsdataTable tbody').on('click', 'tr', function () {
                $(this).toggleClass('selected');
                var selectedRowsCount = $('#projectsdataTable tr.selected').length;
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
           $('#projectsdataTable tbody').on('dblclick', 'tr', function () {
        	   var row = $(this);
        	    $('#projectsdataTable tbody tr').each(function () {
                    $(this).toggleClass('selected', false);
                });
                if ($('#projectsdataTable tbody .dataTables_empty').size() < 1) {
                    window.location = $("#projectsdataTable").attr("data-viewURL") + "&i=" + row.find("td:first").text();
                }

            });
           $("#viewButton").click(function () {
                window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_VIEW_PROJECT + "&i="%>" + $('#paymentsdataTable tr.selected').find("td:first").text();
            });
            $("#editButton").click(function () {
                window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_EDIT_PROJECT + "&i="%>" + $('#paymentsdataTable tr.selected').find("td:first").text();
            });
            $("#deleteButton").click(function () {
                bootbox.confirm("Are you sure you want to delete the selected project(s)?", function (result) {
                    if (result) {
                        var selectedRowsCount = $('#projectsdataTable tr.selected').length;
                        var param = "";
                        if (selectedRowsCount >= 1) {
                            $("#projectsdataTable tr.selected").each(function () {
                                param = param.concat("i=").concat($(this).find("td:first").text()).concat("&");
                            });
                            param = param.substring(0, param.length - 1);
                            console.log(param);
                            window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_DELETE_PROJECT%>&" + param;
                        }
                    }

                });

            });
            setTitle("Projects");
        });
    </script>