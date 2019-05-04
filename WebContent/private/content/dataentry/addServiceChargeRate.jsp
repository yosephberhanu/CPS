<%-- 
    Document   : addServiceCharngeRate - DataEntry
    Created on : Jul 04, 2017, 1:59:35 PM
    Author     : Yoseph Berhanu<yoseph@artisan.et> 
--%>
<%@page import="et.artisan.cn.cps.entity.*"%>
<%@page import="java.util.*"%>
<%@page import="et.artisan.cn.cps.util.*"%>
<%@page import="et.artisan.cn.cps.entity.*"%>
<%@page import="et.artisan.cn.cps.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Client client = (Client) request.getAttribute("client");
    ClientServiceChargeRate scRate = new ClientServiceChargeRate();
    if (request.getAttribute("scRate") != null) {
        scRate = (ClientServiceChargeRate) request.getAttribute("scRate");
    }
    
%>
<div class="">

    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>New Service Charge Rate Registration</h2>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <form class="form-horizontal form-label-left" novalidate action="<%=request.getContextPath() + "/DataEntry"%>" id="addClientServiceChargeRateForm" method="POST" >
                        <input type="hidden" name="a" value="<%=Constants.ACTION_DATAENTRY_SAVE_CLIENT_RATE%>" />
                        <div class="col-md-8 col-lg-8">
                            <div class="col-lg-10 col-md-10 col-sm-12">
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="client">Client </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <select class="form-control selectpicker" data-live-search="true" id="client" name="client" data-value="<%=client.getId()%>">
                                            <option value="0">Select A Client</option>
                                            <%
                                                ArrayList<Client> clientsList = CommonStorage.getRepository().getAllClients();
                                                for (int i = 0; i < clientsList.size(); i++) {
                                                    out.println("<option value='" + clientsList.get(i).getId() + "'>" + clientsList.get(i).getName() + "</option>");
                                                }
                                            %>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="startDate">Start Date
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="text" name="startDate" id="startDate" class="form-control col-md-7 col-xs-12" placeholder="Starting date of applicability" value="<%=scRate.getStartDate()%>" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="endDate">End Date
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="text" name="endDate" id="endDate" class="form-control col-md-7 col-xs-12" placeholder="Ending date of applicability" value="<%=scRate.getEndDate()%>" >
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="rate">Rate
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="number" name="rate" id="rate" class="form-control col-md-7 col-xs-12" placeholder="Rate in percent" value="<%=scRate.getRate()%>" >
                                    </div>
                                </div>
                            </div>
                            <div class='clearfix'></div>
                        </div>
                        <div class="col-md-4 col-lg-4 col-sm-0">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>Help - Client Service Charge Rate</h2>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="x_content">
                                    <p class="text-justify"> Quid securi etiam tamquam eu fugiat nulla pariatur. Morbi fringilla convallis sapien, id pulvinar odio volutpat. At nos hinc posthac, sitientis piros Afros. Inmensae subtilitatis, obscuris et malesuada fames. Morbi odio eros, volutpat ut pharetra vitae, lobortis sed nibh.</p>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                        </div>
                        <div class='clearfix'></div>
                        <div class="ln_solid"></div>   
                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-3">
                                <a href="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_CLIENTS%>" class="btn btn-primary">Cancel</a>
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
        setTitle("Payment Regsitration");
        $('#project').on('change', function () {
            window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_ADD_PAYMENT + "&p="%>" + this.value;
        });
        $('#document').on('change', function () {
            window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_ADD_PAYMENT + "&d="%>" + this.value + "&p=" + $("#project").val();
        });
        startcal=new EthiopianDualCalendar("startDate");
        endcal=new EthiopianDualCalendar("endDate");
    });
</script>