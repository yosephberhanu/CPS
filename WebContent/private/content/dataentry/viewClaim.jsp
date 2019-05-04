<%-- 
    Document   : addPayment - DataEntry
    Created on : Feb 22, 2017, 3:50:35 PM
    Author     : Yoseph Berhanu<yoseph@artisan.et> 
--%>
<%@page import="java.util.*"%>
<%@page import="et.artisan.cn.cps.util.*"%>
<%@page import="et.artisan.cn.cps.dto.*"%>
<%@page import="et.artisan.cn.cps.entity.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    CurrentUserDTO user = CommonStorage.getCurrentUser(request);
    Claim claim = (Claim) request.getAttribute("claim");
%>
<div class="">
    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>View Claim</h2>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <form class="form-horizontal form-label-left" novalidate action="#" id="viewClaimForm" method="POST" >
                        <div class="col-md-8 col-lg-8">
                            <div class="col-lg-10 col-md-10 col-sm-12">
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="claimNumber">Claim Document #
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="text" name="claimNumber" readonly="readonly" id="claimNumber" class="form-control col-md-7 col-xs-12" placeholder="Claim Document Reference Number" value="<%=claim.getClaimNumber()%>" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="claimCNNumber">Claim CN #
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="text" name="claimCNNumber" id="claimCNNumber" readonly="readonly" class="form-control col-md-7 col-xs-12" placeholder="Claim CN Number" value="<%=claim.getClaimCNNumber()%>" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="claimDate">Claim Date
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="date" name="claimDate" readonly="readonly" id="claimDate" class="form-control col-md-7 col-xs-12" placeholder="Date the claim was made" value="<%=claim.getClaimDate()%>" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="arrivalDate">Arrival Date
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="date" name="arrivalDate" readonly="readonly" id="arrivalDate" class="form-control col-md-7 col-xs-12" placeholder="Date the claim arrived" value="<%=claim.getArrivalDate()%>" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="payingBranch">Paying Branch </label>
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="text" readonly="readonly" id="payingBranch" class="form-control col-md-7 col-xs-12" placeholder="Paying Brnach Name" value="<%=claim.getPayingBranch().getName()%>" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="amount">Amount
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="number" name="amount" id="amount" readonly="readonly" class="form-control col-md-7 col-xs-12" placeholder="Total Amount of Claim" value="<%=claim.getAmount()%>" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-sm-3 col-xs-12" for="claimCount">Count
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="number" name="claimCount" id="claimCount" readonly="readonly" class="form-control col-md-7 col-xs-12" placeholder="Total Number of Claims" value="<%=claim.getCount()%>" >
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="client">Client <span class="required">*</span>
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <input type="text" readonly="readonly" name="clientName" id="clientName" class="form-control col-md-7 col-xs-12" placeholder="Client" value="<%=claim.getClient().getName()%>" >
                                    </div>
                                </div>
                                <%if (claim.getRemark() != null && !claim.getRemark().trim().isEmpty()) {%>
                                <div class="form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-4 col-xs-12" for="remark">Remark
                                    </label>
                                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                                        <p class="form-control" id='remark' name='remark' rows="3" placeholder="Additional Remark"><%=claim.getRemark()%></p>
                                    </div>
                                </div>
                                <%}%>
                            </div>
                            <div class='clearfix'></div>
                        </div>
                        <div class="col-md-4 col-lg-4 col-sm-0"> 
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>Preview - Attached Document</h2>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="x_content">
                                    <canvas id="previewDiv" style="border-style: outset;border-width: 0.2em"></canvas>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="x_footer">
                                    <a id="openDoc" href="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_GET_CLAIM_ATTACHMENT + "&i=" + claim.getId()%>" class="btn btn-link col-md-offset-10" target="_blank">Open</a>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                        </div>
                        <div class='clearfix'></div>
                        <div class="ln_solid"></div>
                        <div class="form-group row">
                            <div class="col-lg-1 col-md-1 col-sm-1 col-lg-offset-2 col-md-offset-2 col-sm-offset-2">
                                <a href="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_CLAIMS%>" class="btn btn-primary col-lg-12 col-md-12 col-sm-12" >Back</a>
                            </div>
                            <div class="col-lg-1 col-md-1 col-sm-1">
                                <a href="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_DELETE_CLAIM + "&i=" + claim.getId()%>" class="btn btn-danger col-lg-12 col-md-12 col-sm-12" >Delete</a>
                            </div>
                            <div class="col-lg-1 col-md-1 col-sm-1">
                                <a href="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_EDIT_CLAIM + "&i=" + claim.getId()%>" class="btn btn-success col-lg-12 col-md-12 col-sm-12" >Edit</a>
                            </div>
                            <div class="col-lg-1 col-md-1 col-sm-1">
                                <a href="<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_CLAIM_DETAILS + "&cl=" + claim.getId()%>" class="btn btn-info">Claim Details</a>
                            </div>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        PDFJS.getDocument('<%=request.getContextPath() + "/DataEntry?a=" + Constants.ACTION_DATAENTRY_GET_CLAIM_ATTACHMENT + "&i=" + claim.getId()%>').then(function (pdf) {
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
        setTitle("View Claim");
    });
</script>