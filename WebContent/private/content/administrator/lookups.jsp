<%@page import="et.artisan.cn.cps.entity.*"%>
<%@page import="java.util.ArrayList"%>
<%
    ArrayList<BranchType> branchTypes = (ArrayList<BranchType>) request.getAttribute("branchTypes");
    ArrayList<BranchRegion> branchRegions = (ArrayList<BranchRegion>) request.getAttribute("branchRegions");
%>
<div id="PayingBranchTypes" class="col-md-6 col-sm-6 col-xs-12">
    <div class="x_panel">
        <div class="x_title">
            <h2>Paying Branch Types</h2>
            <ul class="nav navbar-right panel_toolbox">
                <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
                        <i class="fa fa-gear"></i> Action</a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="#" alt="Add New" title="Add New Branch Type"><i class="fa fa-plus"></i> Add New</a></li>
                        <li><a id ="editButton" href="#" alt="Edit Branch Type" title="Edit the selected branch type"><i class="fa fa-edit"></i> Edit</a></li-->
                        <li><a id ="viewButton" href="#" alt="View Branch Type" title="View the selected branch type"><i class="fa fa-search"></i> View</a></li>
                        <li><a id ="deleteButton" href="#" alt="Delete Branch Types" title="Delete the selected branch types"><i class="fa fa-remove"></i> Delete</a></li>
                    </ul>
                </li>
            </ul>

            <div class="clearfix"></div>
        </div>
        <div class="x_content">
            <table class="table">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>English Name</th>
                        <th>Local Name</th>
                        <th>Rate</th>
                    </tr>
                </thead>
                <tbody>
                    <%for (int i = 0; i < branchTypes.size(); i++) {%>
                    <tr>
                        <td class='row'><%=branchTypes.get(i).getCode()%></td>
                        <td><%=branchTypes.get(i).getEnglishName()%></td>
                        <td><%=branchTypes.get(i).getLocalName()%></td>
                        <td><%=branchTypes.get(i).getScRate()%></td>
                    </tr>
                    <%}%>
                </tbody>
            </table>
        </div>
    </div>
</div>
<div id="PayingBranchRegions" class="col-md-6 col-sm-6 col-xs-12">
    <div class="x_panel">
        <div class="x_title">
            <h2>Paying Branch Regions</h2>
            <ul class="nav navbar-right panel_toolbox">
                <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
                        <i class="fa fa-gear"></i> Action</a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="#" alt="Add New" title="Add New Branch Region"><i class="fa fa-plus"></i> Add New</a></li>
                        <li><a id ="editButton" href="#" alt="Edit Branch Region" title="Edit the selected branch region"><i class="fa fa-edit"></i> Edit</a></li-->
                        <li><a id ="viewButton" href="#" alt="View Branch Region" title="View the selected branch region"><i class="fa fa-search"></i> View</a></li>
                        <li><a id ="deleteButton" href="#" alt="Delete Branch Regions" title="Delete the selected branch regions"><i class="fa fa-remove"></i> Delete</a></li>
                    </ul>
                </li>
            </ul>
            <div class="clearfix"></div>
        </div>
        <div class="x_content">
            <table class="table">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>English Name</th>
                        <th>Local Name</th>
                    </tr>
                </thead>
                <tbody>
                    <%for (int i = 0; i < branchRegions.size(); i++) {%>
                    <tr>
                        <td class='row'><%=branchRegions.get(i).getCode()%></td>
                        <td><%=branchRegions.get(i).getEnglishName()%></td>
                        <td><%=branchRegions.get(i).getLocalName()%></td>
                    </tr>
                    <%}%>
                </tbody>
            </table>
        </div>
    </div>
</div>
<div class="clearfix"></div>
<div class="ln_solid"></div>
