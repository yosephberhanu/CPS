<%-- 
    Document   : import - DataEntry
    Created on : Feb 22, 2017, 3:50:35 PM
    Author     : Yoseph Berhanu<yoseph@artisan.et> 
--%>
<%@page import="et.artisan.cn.cps.dto.BranchesParamModel"%>
<%@page import="et.artisan.cn.cps.dto.ClientRegionsParamModel"%>
<%@page import="et.artisan.cn.cps.entity.*"%>
<%@page import="java.util.*"%>
<%@page import="et.artisan.cn.cps.util.*"%>
<%@page import="et.artisan.cn.cps.entity.*"%>
<%@page import="et.artisan.cn.cps.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    long clientId = 0;
    if (request.getAttribute("clientId") != null) {
        clientId = (Long) request.getAttribute("clientId");
    }
%>
<div class="">

    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Payment Import</h2>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <form class="form-horizontal form-label-left" novalidate action="<%=request.getContextPath() + "/DataEntry"%>" id="importForm" method="POST" enctype="multipart/form-data" >
                        <input type="hidden" name="a" value="<%=Constants.ACTION_DATAENTRY_SAVE_IMPORT%>" />
                        <div class="col-md-8 col-lg-8">
                            <div class="form-group">
                                <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="client">Client *</label>
                                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                    <select class="form-control selectpicker" data-live-search="true" id="client" name="client" data-value="<%=clientId%>">
                                        <option value="0">Select A Client</option>
                                        <%
                                            ArrayList<Client> cleintsList = CommonStorage.getRepository().getAllClients();
                                            for (int i = 0; i < cleintsList.size(); i++) {
                                                out.println("<option value='" + cleintsList.get(i).getId() + "'>" + cleintsList.get(i).getName() + "</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                            </div>
                            <%if (clientId != 0) {%>
                            <div class="form-group">
                                <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="clientRegion">Client Region *</label>
                                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                    <select class="form-control selectpicker" data-live-search="true" id="clientRegion" name="clientRegion" >
                                        <option value="0">Select A Client Region</option>
                                        <%
                                        	ClientRegionsParamModel param = new ClientRegionsParamModel();
                                			param.setClientId(clientId);
                                            ArrayList<ClientRegion> clientRegionList = CommonStorage.getRepository().getAllClientRegions(param);
                                            for (int i = 0; i < clientRegionList.size(); i++) {
                                                out.println("<option value='" + clientRegionList.get(i).getId() + "'>" + clientRegionList.get(i).getRegionName() + "</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                            </div>
                            <%}%>
                            <div class="form-group">
                                <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="payingBranch">Paying Branch *</label>
                                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                    <select class="form-control selectpicker" data-live-search="true" id="payingBranch" name="payingBranch" >
                                        <option value="0">Select A Paying Branch</option>
                                        <%
                                    		ArrayList<Branch> branchsList = CommonStorage.getRepository().getAllBranches(new BranchesParamModel());
                                            for (int i = 0; i < branchsList.size(); i++) {
                                                out.println("<option value='" + branchsList.get(i).getId() + "'>" + branchsList.get(i).getName() + "</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="document">The Document
                                </label>
                                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                    <input type="file" name="document" id="document" class="form-control col-md-7 col-xs-12" placeholder="The Document">
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4 col-lg-4 col-sm-0">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>Help - Data Import</h2>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="x_content">
                                    <p class="text-justify"> Quid securi etiam tamquam eu fugiat nulla pariatur. Morbi fringilla convallis sapien, id pulvinar odio volutpat. At nos hinc posthac, sitientis piros Afros. Inmensae subtilitatis, obscuris et malesuada fames. Morbi odio eros, volutpat ut pharetra vitae, lobortis sed nibh.</p>
                                    <p class="text-justify"> Morbi fringilla convallis sapien, id pulvinar odio volutpat. Inmensae subtilitatis, obscuris et malesuada fames. Quid securi etiam tamquam eu fugiat nulla pariatur. At nos hinc posthac, sitientis piros Afros. Inmensae subtilitatis, obscuris et malesuada fames. Morbi odio eros, volutpat ut pharetra vitae, lobortis sed nibh.</p>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                        </div>
                        <div class='clearfix'></div>
                        <div class="ln_solid"></div>   
                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-3">
                                <button id="submitBtn" type="submit" class="btn btn-success">Submit</button>
                            </div>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>
</div>
<script src="<%=request.getContextPath()%>/assets/js/validator/validator.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/select/select2.full.js"></script>
<script>
    $(document).ready(function () {
        message = {
            invalid: 'invalid input',
            empty: 'Please put something here',
            min: 'input is too short',
            max: 'input is too long',
            number_min: 'too low',
            number_max: 'too high',
            url: 'invalid URL',
            number: 'not a number',
            email: 'email address is invalid',
            email_repeat: 'emails do not match',
            password_repeat: 'passwords do not match',
            no_match: 'no match',
            complete: 'input is not complete',
            select: 'Please select an option'
        };
        $('form').submit(function (e) {
            e.preventDefault();
            var submit = true;
            // you can put your own custom validations below

            // check all the required fields
            if (validator.checkAll($(this))) {
                //if ($("#roles :selected").length < 1) {
                //validator.mark($("#roles"), "At least one role is expected");
                //} else if (!usernameValid) {
                //validator.mark($("#username"), "Username already taken");
                //} else {
                this.submit();
                //}

            }
            return false;
        });
        setTitle("Data Import");
        $('#client').on('change', function () {
            window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_ADD_IMPORT + "&c="%>" + this.value;
        });
    });
</script>