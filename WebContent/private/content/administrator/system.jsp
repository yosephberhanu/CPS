<%@page import="et.artisan.cn.cps.util.CommonStorage"%>
<%@page import="et.artisan.cn.cps.util.Constants"%>
<%@page import="et.artisan.cn.cps.entity.*"%>
<%@page import="java.util.ArrayList"%>
<div class="col-lg-offset-2  col-md-offset-2  col-lg-8 col-md-8 col-xs-12">
    <div class="x_panel">
        <div class="x_title">
            <h2>About iWORLAIS</h2>
            <div class="clearfix"></div>
        </div>
        <div class="x_content">
            <div class="bs-example" data-example-id="simple-jumbotron">
                <div class="jumbotron">
                    <h1>Version 1.1.0 - <%=CommonStorage.getDatabaseVersion()%></h1>
                    <p> You may run update scripts provided by LIFT head office using the form provided below.</p>
                    <h4>Change Log</h4>
                    <ul>
                        <li>1.0.0 - First stable version</li>
                        <li>
                            1.1.0 - Aug 20,2016
                            <ul>
                                <li>Added Time bound transaction reporting</li>
                                <li>Added Script updating functionality</li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="clearfix"></div>
            <div class="ln_solid"></div>
            <form class="form-horizontal form-label-left input_mask" novalidate action="<%=request.getContextPath() + "/Administrator"%>" id="scriptUpdateForm" method="POST" enctype="multipart/form-data" >
                <input type="hidden" name="a" value="<%=Constants.ACTION_ADMINISTRATOR_UPDATE_SYSTEM%>" />
                <div class="form-group">
                    <label class="control-label col-lg-2 col-md-2 col-sm-2 col-xs-12">Update Script
                    </label>
                    <div class="col-lg-7 col-md-7 col-sm-7 col-xs-12">
                        <input class="form-control " required="required" type="file" id="updateFile" name="updateFile" accept="text/*"/>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-2 col-xs-12">
                        <button type="submit" class="btn btn-success">Upload</button>
                    </div>
                </div>
                <div class="ln_solid"></div>
            </form>
        </div>
    </div>
</div>
<div class="clearfix"></div>
<div class="ln_solid"></div>
