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
<div class="">
    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Documents</h2>  
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <div class="col-md-6 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>Find Documents</h2>
                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                    </li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <br>
                                <form class="form-horizontal form-label-left" novalidate action="<%=request.getContextPath() + "/DataEntry"%>" id="findDocumentForm" method="GET">
                                        <input type="hidden" name="a" value="<%=Constants.ACTION_DATAENTRY_FIND_DOCUMENTS%>" />
                                        <div class="form-group">
                                            <label class="control-label col-md-3 col-sm-3 col-xs-3">Document ID</label>
                                            <div class="col-md-9 col-sm-9 col-xs-9">
                                                <input id="id" name = "id" class="form-control" data-inputmask="'mask': '999/999/999'" type="text">
                                                <span class="fa fa-book form-control-feedback right" aria-hidden="true"></span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-3 col-sm-3 col-xs-3">Amount</label>
                                            <div class="col-md-9 col-sm-9 col-xs-9">
                                                <input id="amount" name = "amount" class="form-control" type="number">
                                                <span class="fa fa-user form-control-feedback right" aria-hidden="true"></span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-3 col-sm-3 col-xs-3">CN Number</label>
                                            <div class="col-md-9 col-sm-9 col-xs-9">
                                                <input id="cnnumber" name = "cnnumber" class="form-control" type="text">
                                                <span class="fa fa-user form-control-feedback right" aria-hidden="true"></span>
                                            </div>
                                        </div>
                                        
                                        <!--div class="form-group">
                                            <label class="control-label col-md-3 col-sm-3 col-xs-3">CN No.</label>
                                            <div class="col-md-9 col-sm-9 col-xs-9">
                                                <input id="cnNo" name = "cnNo" class="form-control" type="text">
                                                <span class="fa fa-user form-control-feedback right" aria-hidden="true"></span>
                                            </div>
                                        </div-->
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
                                <h2>New Document </h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <br>
                                <a href="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_ADD_DOCUMENT%>" class="btn btn-primary">New</a>
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
            $("#viewButton").click(function () {
                window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_VIEW_DOCUMENT + "&i="%>" + $('#dataTable tr.selected').attr("data-id");
            });
            $("#editButton").click(function () {
                window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_EDIT_DOCUMENT + "&i="%>" + $('#dataTable tr.selected').attr("data-id");
            });
            $("#paymentButton").click(function () {
                window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_PAYMENTS + "&d="%>" + $('#dataTable tr.selected').attr("data-id");
            });
            $("#deleteButton").click(function () {
                bootbox.confirm("Are you sure you want to delete the selected document(s)?", function (result) {
                    if (result) {
                        var selectedRowsCount = $('#dataTable tr.selected').length;
                        var param = "";
                        if (selectedRowsCount >= 1) {
                            $("#dataTable tr.selected").each(function () {
                                param = param.concat("i=").concat($(this).attr('data-id')).concat("&");
                            });
                            param = param.substring(0, param.length - 1);
                            console.log(param);
                            window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_DELETE_DOCUMENT%>&" + param;
                        }
                    }

                });

            });
            setTitle("Documents");
        });
    </script>