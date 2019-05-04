<%-- 
    Document   : viewDocument - DataEntry
    Created on : Feb 18, 2017, 8:38:35 AM
    Author     : Yoseph Berhanu<yoseph@artisan.et> 
--%>
<%@page import="et.artisan.cn.cps.entity.*"%>
<%@page import="java.util.HashMap"%>
<%@page import="et.artisan.cn.cps.util.*"%>
<%@page import="java.util.ArrayList"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Document document = (Document) request.getAttribute("document");
%>
<div class="">

    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>View Document</h2>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <form class="form-horizontal form-label-left" novalidate action="#" id="viewDocumentForm" method="POST" >
                        <div class="col-md-7 col-lg-7">

                            <div class="form-group">
                                <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="client">Client
                                </label>
                                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                    <input type="text" readonly="readonly" class="form-control col-md-7 col-xs-12" value="<%=document.getClientRegion().getClient().getName()%>" >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="clientRegion">Client Region </label>
                                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                    <input type="text" readonly="readonly" class="form-control col-md-7 col-xs-12" value="<%=document.getClientRegion().getRegionName()%>" >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="project">Project </label>
                                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                    <input type="text" readonly="readonly" name="projectName" id="projectName" class="form-control col-md-7 col-xs-12" value="<%=document.getProject().getCode()%> - <%=document.getProject().getName()%>" >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="payingBranch">Paying Branch </label>
                                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                    <input type="text" readonly="readonly" class="form-control col-md-7 col-xs-12" value="<%=document.getBranch().getName()%>" >
                                </div>
                            </div>  
                            <div class="form-group">
                                <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="documentYear">Document Year
                                </label>
                                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                    <input type="text" readonly="readonly" class="form-control col-md-7 col-xs-12" value="<%=document.getDocumentYear()%>" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="inComingDocumentNo">In Coming Document #
                                </label>
                                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                    <input type="text" readonly="readonly" class="form-control col-md-7 col-xs-12" value="<%=document.getInComingDocumentNo()%>" >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="incomingDate">Incoming Date
                                </label>
                                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                    <input type="text" readonly="readonly" class="form-control col-md-7 col-xs-12" value="<%=document.getInComingDate()%>" >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="outGoingDocumentNo">Out Going Document #
                                </label>
                                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                    <input type="text" readonly="readonly" class="form-control col-md-7 col-xs-12" value="<%=document.getOutGoingDocumentNo()%>" >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="outGoingDate">Out Going Date
                                </label>
                                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                    <input type="text" readonly="readonly" class="form-control col-md-7 col-xs-12" value="<%=document.getOutGoingDate()%>" >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="paymentDue">Payment Due Date
                                </label>
                                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                    <input type="text" readonly="readonly" class="form-control col-md-7 col-xs-12" value="<%=document.getPaymentDue()%>" >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="totalAmount">Total Amount
                                </label>
                                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                    <p type="text" readonly="readonly" class="form-control col-md-7 col-xs-12"><%=CommonTasks.moneyFormat(document.getTotalAmount())%></p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="registeredAmount">Registered Amount
                                </label>
                                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                    <p type="text" readonly="readonly" class="form-control col-md-7 col-xs-12"><%=CommonTasks.moneyFormat(document.getRegisteredAmount())%></p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="paidAmount">Paid Amount
                                </label>
                                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                    <p type="text" readonly="readonly" class="form-control col-md-7 col-xs-12"><%=CommonTasks.moneyFormat(document.getPaidAmount())%></p>
                                </div>
                            </div>
                            <%if (document.getRemark() != null && !document.getRemark().trim().isEmpty()) {%>
                            <div class="form-group">
                                <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="remark">Remark
                                </label>
                                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                    <p id='remark' name='remark' ><%=document.getRemark()%></p>
                                </div>
                            </div>
                            <%}%>
                        </div>
                        <div class="col-md-5 col-lg-5 col-sm-0"> 
                            <div class="x_panel">
                                <%if(document.getScannedDocument()!=null){%>
                        		<div class="x_title">
                                    <h2>Preview - Attached Document</h2>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="x_content">
                                    <canvas id="previewDiv" style="border-style: outset;border-width: 0.2em"></canvas>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="x_footer">
                                    <a id="openDoc" href="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_GET_CLAIM_ATTACHMENT + "&i=" + document.getId()%>" class="btn btn-link col-md-offset-10" target="_blank">Open</a>
                                    <div class="clearfix"></div>
                                </div>
                                <%} %>
                            </div>
                        </div>
                        <div class='clearfix'></div>
                        <div class="ln_solid"></div>  
                        <div class="form-group row">
                            <div class="col-lg-1 col-md-1 col-sm-1 col-lg-offset-2 col-md-offset-2 col-sm-offset-2">
                                <a href="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_DOCUMENTS%>" class="btn btn-primary col-lg-12 col-md-12 col-sm-12" >Back</a>
                            </div>
                            <div class="col-lg-1 col-md-1 col-sm-1">
								<a href="#" class="btn btn-danger" id="deleteButton">Delete</a>
                            </div>
                            <div class="col-lg-1 col-md-1 col-sm-1">
                                <a href="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_EDIT_DOCUMENT + "&i=" + document.getId()%>" class="btn btn-success col-lg-12 col-md-12 col-sm-12" >Edit</a>
                            </div>
                            <div class="col-lg-1 col-md-1 col-sm-1">
                                <a href="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_PAYMENTS + "&p=" + document.getProject().getId() + "&d=" + document.getId()%>" class="btn btn-info" >Payments</a>
                            </div>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
    	<%if(document.getScannedDocument()!=null){%>
        PDFJS.getDocument('<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_GET_DOCUMENT_ATTACHMENT + "&i=" + document.getId()%>').then(function (pdf) {
            pdf.getPage(1).then(function (page) {
                var scale = 0.4;
                var viewport = page.getViewport(scale);

                var canvas = document.getElementById('previewDiv');
                var context = canvas.getContext('2d');
                canvas.height = viewport.height;
                canvas.width = viewport.width;

                var renderContext = {
                    canvasContext: context,
                    viewport: viewport
                };
                page.render(renderContext);
            });
        });
        <%}%>
        $("#deleteButton").click(function () {
            bootbox.confirm("Are you sure you want to delete this document?", function (result) {
                if (result) {
                	window.location = "<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_DELETE_DOCUMENT+"&i="+document.getId()%>";
                }
            });

        });
        setTitle("View Document Details");
    });
</script>