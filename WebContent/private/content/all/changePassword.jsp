<%-- 
    Document   : changePassword
    Created on : Feb 11, 2016, 1:45:28 PM
    Author     : Yoseph Berhanu<yoseph@artisan.et>  
--%>
<%@page import="java.util.HashMap"%>
<%@page import="et.artisan.cn.cps.util.Constants"%>
<%@page import="et.artisan.cn.cps.entity.User"%>
<%@page import="java.util.ArrayList"%>
<%@page import="et.artisan.cn.cps.util.CommonStorage"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    User user = CommonStorage.getCurrentUser(request);
%>
<div class="">

    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Your Profile <small>CPS</small></h2>&nbsp;
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <form class="form-horizontal form-label-left" novalidate action="<%=request.getContextPath() + "/All"%>" id="changePasswordForm" method="POST" >
                        <input type="hidden" name="a" value="< %=Constants.ACTION_ALL_UPDATE_PASSWORD%>" />
                        <span class="section">Change Your Password</span>
                        <div class="item form-group">
                            <label for="oldpassword" class="control-label col-md-3">Current Password<span class="required">*</span></label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="oldpassword" name="oldpassword" data-validate-length="6,8" class="form-control col-md-7 col-xs-12" required="required" type="password">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label for="password" class="control-label col-md-3">New Password<span class="required">*</span></label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="password" name="password" data-validate-length="6,8" class="form-control col-md-7 col-xs-12" required="required" type="password">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Repeat Password <span class="required">*</span></label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="password2" name="password2" data-validate-linked="password" class="form-control col-md-7 col-xs-12" required="required" type="password">
                            </div>
                        </div>
                    <div class='clearfix'></div>
                    <div class="ln_solid"></div>   
                    <div class="form-group">
                        <div class="col-md-6 col-md-offset-3">
                            <a href="<%=request.getContextPath() + "/All?a=" + Constants.ACTION_ALL_EDIT_PROFILE%>" type="submit" class="btn btn-primary">Cancel </a>
                            <button id="submitBtn" type="submit" class="btn btn-success">Update</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</div>
<script src="<%=request.getContextPath()%>/assets/js/validator/validator.js"></script>
<script>
    // initialize the validator function
    validator.message['date'] = 'not a real date';

    // validate a field on "blur" event, a 'select' on 'change' event & a '.reuired' classed multifield on 'keyup':
    $('form')
            .on('blur', 'input[required], input.optional, select.required', validator.checkField)
            .on('change', 'select.required', validator.checkField)
            .on('keypress', 'input[required][pattern]', validator.keypress);

    $('.multi.required')
            .on('keyup blur', 'input', function () {
                validator.checkField.apply($(this).siblings().last()[0]);
            });

    $('form').submit(function (e) {
        e.preventDefault();
        var submit = true;
        // evaluate the form using generic validaing
        if (!validator.checkAll($(this))) {
            submit = false;
        }

        if (submit)
            this.submit();
        return false;
    });


    $('#alerts').change(function () {
        validator.defaults.alerts = (this.checked) ? false : true;
        if (this.checked)
            $('form .alert').remove();
    }).prop('checked', false);

    $("#photo").change(function (event) {
        if (event.target.files[0] == null) {
            $("#photoPreview").attr("src", "<%=request.getContextPath()%>/assets/images/defaultProfile.png");
        } else {
            $("#photoPreview").attr("src", URL.createObjectURL(event.target.files[0]));
        }
    });
    $("#signature").change(function (event) {
        if (event.target.files[0] == null) {
            $("#signaturePreview").attr("src", "<%=request.getContextPath()%>/assets/images/defaultSignature.png");
        } else {
            $("#signaturePreview").attr("src", URL.createObjectURL(event.target.files[0]));
        }
    });
</script>