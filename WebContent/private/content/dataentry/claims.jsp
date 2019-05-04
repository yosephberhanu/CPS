<%-- 
    Document   : Claims Find
    Created on : Feb 12, 2018, 03:22:15 PM
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
                    <h2>Claim Documents</h2>  
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <div class="col-md-6 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>Find Claim Documents</h2>
                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                    </li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <br>
                                <form class="form-horizontal form-label-left" novalidate action="<%=request.getContextPath() + "/DataEntry"%>" id="findClaimForm" method="GET">
                                    <input type="hidden" name="a" value="<%=Constants.ACTION_DATAENTRY_FIND_CLAIMS%>" />
                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-3">Claim ID</label>
                                        <div class="col-md-9 col-sm-9 col-xs-9">
                                            <input id="claimNumber" name = "claimNumber" class="form-control" type="text">
                                            <span class="fa fa-paint-brush form-control-feedback right" aria-hidden="true"></span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-3">Claim CN #</label>
                                        <div class="col-md-9 col-sm-9 col-xs-9">
                                            <input id="claimCNNumber" name = "claimCNNumber" class="form-control" type="text">
                                            <span class="fa fa-paint-brush form-control-feedback right" aria-hidden="true"></span>
                                        </div>
                                    </div>
                                    <div class="ln_solid"></div>

                                    <div class="form-group">
                                        <div class="col-md-3 col-md-offset-9">
                                            <button type="submit" class="btn btn-success">Find</button>
                                        </div>
                                    </div>

                                </form>
                            </div>
                        </div>
                    </div>        
                    <div class="col-md-6 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>Other Options</h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <div class="row">
                                    <a href="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_ADD_CLAIM%>" class="btn btn-primary col-md-3 col-lg-3">New</a>
                                    <a href="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_CLAIMS_LIST%>" class="btn btn-success col-md-3 col-lg-3">Active</a>
                                    <a href="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_CLAIMS_LIST + "&all=true"%>" class="btn btn-warning col-md-3 col-lg-3">All</a>
                                </div>
                            </div>
                        </div>
                    </div>        
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
            setTitle("Find Claim Documents");
        });
    </script>