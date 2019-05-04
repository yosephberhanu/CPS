<%-- 
    Document   : Administrator - Organizational Structure
    Created on : Sep 28, 2016, 11:56:28 AM
    Author     : Yoseph Berhanu<yoseph@artisan.et> 
--%>
<%@page import="et.artisan.cn.cps.entity.User"%>
<%@page import="et.artisan.cn.cps.util.Constants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="">
    <!--div class="page-title">
        <div class="title_left">
            <h3>Settings</h3>
        </div>
    </div-->
    <div class="clearfix"></div>

    <div class="row">

        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>System Settings</h2>
                    <div class="clearfix"></div>
                </div>
                <form class="form-horizontal form-label-left input_mask">
                    <div class="x_content">
                        <div class="row">
                            <div class="col-md-6 col-xs-12">
                                <div class="x_panel">
                                    <div class="x_title">
                                        <h2>Database Settings<small> configure where the data is stored</small></h2>
                                        <ul class="nav navbar-right panel_toolbox">
                                            <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                            </li>
                                            <li><a class="close-link"><i class="fa fa-close"></i></a>
                                            </li>
                                        </ul>
                                        <div class="clearfix"></div>
                                    </div>
                                    <div class="x_content">
                                        <br>
                                        <div class="form-group has-feedback row">
                                            <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12">Connection Type</label>
                                            <div class="col-lg-4 col-md-4">
                                                <div class="radio">
                                                    <label class="">
                                                        <div class="iradio_flat-green checked" style="position: relative;"><input class="flat" checked="" name="iCheck" style="position: absolute; opacity: 0;" type="radio"><ins class="iCheck-helper" style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255) none repeat scroll 0% 0%; border: 0px none; opacity: 0;"></ins></div> Glassfish
                                                    </label>
                                                </div>
                                            </div>
                                            <div class="col-lg-4 col-md-4">
                                                <div class="radio">
                                                    <label class="">
                                                        <div class="iradio_flat-green checked" style="position: relative;"><input class="flat" checked="" name="iCheck" style="position: absolute; opacity: 0;" type="radio"><ins class="iCheck-helper" style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255) none repeat scroll 0% 0%; border: 0px none; opacity: 0;"></ins></div> Direct
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="x_panel">
                                    <div class="x_title">
                                        <h2>Glassfish Configuration</h2>
                                        <ul class="nav navbar-right panel_toolbox">
                                            <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                            </li>
                                            <li><a class="close-link"><i class="fa fa-close"></i></a>
                                            </li>
                                        </ul>
                                        <div class="clearfix"></div>
                                    </div>
                                    <div class="x_content">
                                        <div class="form-group">
                                            <label for="poolName" class="control-label col-md-3 col-sm-3 col-xs-12">Pool Name</label>
                                            <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 has-feedback">
                                                <input class="form-control has-feedback-left" id="poolName" name="poolName" placeholder="Connection Pool Name " type="text">
                                                <span class="fa fa-chain form-control-feedback left" aria-hidden="true"></span>
                                                <!--input id="middle-name" class="form-control col-md-7 col-xs-12" name="middle-name" data-parsley-id="6908" type="text"><ul class="parsley-errors-list" id="parsley-id-6908"></ul-->
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="x_panel">
                                    <div class="x_title">
                                        <h2>Direct Database Connection</h2>
                                        <ul class="nav navbar-right panel_toolbox">
                                            <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                            </li>
                                            <li><a class="close-link"><i class="fa fa-close"></i></a>
                                            </li>
                                        </ul>
                                        <div class="clearfix"></div>
                                    </div>
                                    <div class="x_content">
                                        <div class="form-group">
                                            <label for="dbHostname" class="control-label col-md-3 col-sm-3 col-xs-12">Host Name</label>
                                            <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 has-feedback">
                                                <input class="form-control has-feedback-left" id="dbHostname" name="dbHostname" placeholder="localhost " type="text">
                                                <span class="fa fa-desktop form-control-feedback left" aria-hidden="true"></span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="dbPortNo" class="control-label col-md-3 col-sm-3 col-xs-12">Port Number </label>
                                            <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 has-feedback">
                                                <input class="form-control has-feedback-left" id="dbPortNo" name="dbPortNo" placeholder="5432 " type="number">
                                                <span class="fa fa-anchor form-control-feedback left" aria-hidden="true"></span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="dbName" class="control-label col-md-3 col-sm-3 col-xs-12">Database </label>
                                            <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 has-feedback">
                                                <input class="form-control has-feedback-left" id="dbName" name="dbName" placeholder="chmis " type="text">
                                                <span class="fa fa-database form-control-feedback left" aria-hidden="true"></span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="dbUsername" class="control-label col-md-3 col-sm-3 col-xs-12">User Name</label>
                                            <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 has-feedback">
                                                <input class="form-control has-feedback-left" id="dbUsername" name="dbUsername" placeholder="postgres " type="text">
                                                <span class="fa fa-user form-control-feedback left" aria-hidden="true"></span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="dbPassword" class="control-label col-md-3 col-sm-3 col-xs-12">Password</label>
                                            <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 has-feedback">
                                                <input class="form-control has-feedback-left" id="hostname" name="dbPassword" placeholder="Input password " type="password">
                                                <span class="fa fa-key form-control-feedback left" aria-hidden="true"></span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6 col-xs-12">
                                <div class="x_panel">
                                    <div class="x_title">
                                        <h2>General Settings<small>configure how various things look</small></h2>
                                        <ul class="nav navbar-right panel_toolbox">
                                            <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                            </li>
                                            <li><a class="close-link"><i class="fa fa-close"></i></a>
                                            </li>
                                        </ul>
                                        <div class="clearfix"></div>
                                    </div>
                                    <div class="x_content">
                                        <div class="form-group has-feedback">
                                            <label for="tableDisplaySize" class="control-label col-lg-3 col-md-3 col-sm-3 col-xs-12">Display Size</label>
                                            <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 has-feedback">
                                                <input class="form-control has-feedback-right" id="tableDisplaySize" name="tableDisplaySize" placeholder="Default Display Size " type="number" />
                                                <span class="fa fa-table form-control-feedback right" aria-hidden="true"></span>
                                                <!--input id="middle-name" class="form-control col-md-7 col-xs-12" name="middle-name" data-parsley-id="6908" type="text"><ul class="parsley-errors-list" id="parsley-id-6908"></ul-->
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <div class="ln_solid"></div>
                            <div class="col-md-12 col-sm-12 col-xs-12 col-md-offset-9">
                                <button type="submit" class="btn btn-primary">Cancel</button>
                                <button type="submit" class="btn btn-success">Submit</button>
                            </div>
                        </div>


                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        setTitle("Settings");
    });
</script>