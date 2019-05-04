<%-- 
    Document   : addPayment - DataEntry
    Created on : Feb 22, 2017, 3:50:35 PM
    Author     : Yoseph Berhanu<yoseph@artisan.et> 
--%>
<%@page import="et.artisan.cn.cps.entity.*"%>
<%@page import="java.util.*"%>
<%@page import="et.artisan.cn.cps.util.*"%>
<%@page import="et.artisan.cn.cps.entity.*"%>
<%@page import="et.artisan.cn.cps.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Payment payment = new Payment();
    if (request.getAttribute("payment") != null) {
        payment = (Payment) request.getAttribute("payment");
    } else if (request.getAttribute("lotNo") != null) {
        payment.setLotNo((Integer) request.getAttribute("lotNo"));
    }
    long projectId = 0;
    if (request.getAttribute("projectId") != null) {
        projectId = (Long) request.getAttribute("projectId");
    }
    long documentId = 0;
    if (request.getAttribute("documentId") != null) {
        documentId = (Long) request.getAttribute("documentId");
    }
%>
<div class="">

    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Payment Registration</h2>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <form class="form-horizontal form-label-left" novalidate action="<%=request.getContextPath() + "/DataEntry"%>" id="addPaymentForm" method="POST" >
                        <input type="hidden" name="a" value="<%=Constants.ACTION_DATAENTRY_SAVE_PAYMENT%>" />
                        <div class="col-md-8 col-lg-8">
                            <div class="col-lg-10 col-md-10 col-sm-12">
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="project">Project </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <select class="form-control selectpicker" data-live-search="true" id="project" name="project" data-value="<%=projectId%>">
                                            <option value="0">Select A Project</option>
                                            <%
                                                //ArrayList<Project> projectsList = CommonStorage.getRepository().getAllProjects();
                                                Project project = CommonStorage.getRepository().getProject(projectId);
                                                //for (int i = 0; i < projectsList.size(); i++) {
                                                out.println("<option value='" + project.getId() + "'>" + project.getCode() + " " + project.getName() + "</option>");
                                                //}
                                            %>
                                        </select>
                                    </div>
                                </div>
                                <%if (projectId > 0) {%>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="document">Document <span class="required">*</span>
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <%if (request.getAttribute("document") == null) {%>
                                        <select class="form-control selectpicker" data-live-search="true" id="document" name="document" data-value="<%=documentId%>">
                                            <option value="0">Select A Document</option>
                                            <%
                                                //ArrayList<Document> documents = CommonStorage.getRepository().getAllDocuments(projectId);
                                                Document document = CommonStorage.getRepository().getDocument(documentId);
                                                //for (int i = 0; i < documents.size(); i++) {
                                                //    out.println("<option value='" + documents.get(i).getId() + "'>" + documents.get(i).getProject().getCode() + "/" + documents.get(i).getInComingDocumentNo() + "/" + documents.get(i).getDocumentYear() + "</option>");
                                                out.println("<option value='" + document.getId() + "'>" + document.getProject().getCode() + "/" + document.getInComingDocumentNo() + "/" + document.getDocumentYear() + "</option>");
                                                //}
                                            %>
                                        </select>
                                        <%} else {
                                            Document document = (Document) request.getAttribute("document");
                                        %>
                                        <input type="hidden" name="document" id="document" class="form-control col-md-7 col-xs-12" placeholder="Client" value="<%=document.getId()%>" >
                                        <input type="text" readonly="readonly" name="documentRefNo" id="clientName" class="form-control col-md-7 col-xs-12" placeholder="Document" value="<%=document.getInComingDocumentNo()%>" >
                                        <%}%>
                                    </div>
                                </div>
                                <%if (documentId > 0) {%>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="lotNo">Lot No
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="number" name="lotNo" id="lotNo" class="form-control col-md-7 col-xs-12" placeholder="Lot #" value="<%=payment.getLotNo()%>" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="name">Name
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="text" name="name" id="name" class="form-control col-md-7 col-xs-12" placeholder="Full Name" value="<%=payment.getName()%>" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="amount">Amount
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="number" name="amount" id="amount" class="form-control col-md-7 col-xs-12" placeholder="Full Name" value="<%=payment.getAmount()%>" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4" for="restricted" >Restricted <span class="required">*</span>
                                    </label>
                                    <div class="item radio col-lg-4 col-md-4 col-sm-4 ">
                                        <label class="">
                                            <div class="iradio_flat-green " style="position: relative;"><input class="flat" name="restricted" value='yes'  style="position: absolute; opacity: 0;" type="radio"><ins class="iCheck-helper" style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255) none repeat scroll 0% 0%; border: 0px none; opacity: 0;"></ins></div> Yes
                                        </label>
                                    </div>
                                    <div class="item radio col-lg-4 col-md-4 col-sm-4">
                                        <label class="">
                                            <div class="iradio_flat-green checked" style="position: relative;"><input class="flat"  name="restricted" value='no' style="position: absolute; opacity: 0;" type="radio"><ins class="iCheck-helper" style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255) none repeat scroll 0% 0%; border: 0px none; opacity: 0;"></ins></div> No
                                        </label>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="remark">Remark
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <textarea class="form-control" id='remark' name='remark' rows="3" placeholder="Additional Remark"></textarea>
                                    </div>
                                </div>
                                <%}
                                    }%>
                            </div>
                            <div class='clearfix'></div>
                        </div>
                        <div class="col-md-4 col-lg-4 col-sm-0">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>Help - Payment Registration</h2>
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
                                <a href="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_PAYMENTS%>" class="btn btn-primary">Cancel</a>
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
       $("#name").focus();
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
    });
</script>