<%-- 
    Document   : All - Edit Profile
    Created on : Sep 26, 2016, 10:48:28 AM
    Author     : Yoseph Berhanu<yoseph@artisan.et> 
--%>
<%@page import="et.artisan.cn.cps.dto.CurrentUserDTO"%>
<%@page import="et.artisan.cn.cps.util.Constants"%>
<%@page import="et.artisan.cn.cps.entity.Role"%>
<%@page import="java.util.ArrayList"%>
<%@page import="et.artisan.cn.cps.util.CommonStorage"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    CurrentUserDTO currentUser = CommonStorage.getCurrentUser(request);

%>
<div class="">
    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Edit Your Profile</h2>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <form class="form-horizontal form-label-left" novalidate action="<%=request.getContextPath() + "/All"%>" id="editProfileForm" method="POST" enctype="multipart/form-data" >
                        <input type="hidden" name="a" value="<%=Constants.ACTION_ALL_UPDATE_PROFILE%>" />
                        <input type="hidden" name="i" value="<%=currentUser.getId()%>" />
                        <span class="section">Personal Information</span>
                        <div class="col-md-10 col-lg-10">
                            <div class="col-lg-6 col-md-6 col-sm-12">
                                <div class="item col-lg-12 col-md-12 col-sm-12 col-xs-12 form-group has-feedback">
                                    <input class="form-control has-feedback-left" id="firstName" placeholder="First Name *" type="text" name="firstName" required="required" value="<%=currentUser.getFirstName()%>" />
                                    <span class="fa fa-user form-control-feedback left" aria-hidden="true"></span>
                                </div>
                                <div class="item col-lg-12 col-md-12 col-sm-12 col-xs-12 form-group has-feedback">
                                    <input class="form-control has-feedback-left" id="fathersName" placeholder="Father's Name *" type="text" name="fathersName" data-validate-length-range="6" data-validate-words="1" required="required" value="<%=currentUser.getFathersName()%>" />
                                    <span class="fa fa-user form-control-feedback left" aria-hidden="true"></span>
                                </div>
                                <div class="item col-lg-12 col-md-12 col-sm-12 col-xs-12 form-group has-feedback">
                                    <input class="form-control has-feedback-left" id="grandfathersName" placeholder="Grandfather's Name *" type="text" name="grandfathersName" data-validate-length-range="6" data-validate-words="1" required="required" value="<%=currentUser.getGrandfathersName()%>" />
                                    <span class="fa fa-user form-control-feedback left" aria-hidden="true"></span>
                                </div>
                                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 form-group">
                                    <label class="control-label col-lg-2 col-md-2 col-sm-2" for="sex" style="text-align: left">Sex <span class="required">*</span>
                                    </label>
                                    <div class="item radio col-lg-4 col-md-4 col-sm-4 ">
                                        <label class="">
                                            <div class="iradio_flat-green" style="position: relative;"><input class="flat" <%=currentUser.getSex().equalsIgnoreCase("f") ? "checked='checked'" : ""%> name="sex" value='f'  style="position: absolute; opacity: 0;" type="radio"><ins class="iCheck-helper" style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255) none repeat scroll 0% 0%; border: 0px none; opacity: 0;"></ins></div> Female
                                        </label>
                                    </div>
                                    <div class="item radio col-lg-4 col-md-4 col-sm-4">
                                        <label class="">
                                            <div class="iradio_flat-green" style="position: relative;"><input class="flat" <%=currentUser.getSex().equalsIgnoreCase("m") ? "checked='checked'" : ""%> name="sex" value='m' style="position: absolute; opacity: 0;" type="radio"><ins class="iCheck-helper" style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255) none repeat scroll 0% 0%; border: 0px none; opacity: 0;"></ins></div> Male
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-offset-2 col-md-offset-2 col-md-4 col-lg-4 col-xs-12 form-group" id="profilePicSelection" style="width: 5em;height: 5em">
                                <div class="pic item form-group" style="width: 12em;height: 13em;border-radius: 10px;border:3px solid #fff">
                                    <a>
                                        <div class="picOverlay" style="border-radius: 10px;">
                                            <span class="fa-stack fa-lg">
                                                Click To Change Profile Picture
                                            </span>
                                        </div>
                                        <img src="<%=request.getContextPath() + "/All?a=" + Constants.ACTION_ALL_GET_PROFILE_PICTURE%>" alt="Profile Picture" id='photoPreview'  data-toggle="tooltip" data-placement="bottom" title="" data-original-title="Profile Picture" title="Profile Picture" style="width: 12em;height: 13em;border-radius: 10px;border:3px solid #fff">
                                    </a>
                                </div>
                            </div>
                            <input type="file" id='signature' name='signature' class="btn btn-primary col-md-12 col-xs-12 col-lg-12"  accept="image/*" style="visibility: collapse;height: 0"/>
                            <input type="file" id='photo' name='photo' class="btn btn-primary col-md-12 col-xs-12 col-lg-12" accept="image/*" style="visibility: collapse;height: 0"/>
                            <input type="hidden" id="changePassword" name="changePassword" value="false"/>
                            <input type="hidden" id='password' name='password' value=""/>

                            <div class='clearfix'></div>
                            <span class="section">Address Information</span>
                            <div class="item col-md-6 col-sm-6 col-xs-12 form-group has-feedback optional">
                                <input class="form-control has-feedback-left" id="primaryPhoneNo" placeholder="Primary Phone Number (Optional)" type="text" name="primaryPhoneNo" pattern="phone" value="<%=currentUser.getPrimaryPhoneNo()%>" />
                                <span class="fa fa-phone form-control-feedback left" aria-hidden="true"></span>
                            </div>
                            <div class="item col-md-6 col-sm-6 col-xs-12 form-group has-feedback">
                                <input class="form-control has-feedback-right" id="secondaryPhoneNo" placeholder="Secondary Phone Number (Optional)" type="text" name="secondaryPhoneNo" value="<%=currentUser.getSecondaryPhoneNo()%>" />
                                <span class="fa fa-phone form-control-feedback right" aria-hidden="true"></span>
                            </div>
                            <div class="item col-md-6 col-sm-6 col-xs-12 form-group has-feedback">
                                <input class="form-control has-feedback-left" id="email" placeholder="Email * " type="email" name="email" required="required" value="<%=currentUser.getEmail()%>" />
                                <span class="fa fa-envelope form-control-feedback left" aria-hidden="true"></span>
                            </div>
                            <div class="item col-md-6 col-sm-6 col-xs-12 form-group has-feedback optional">
                                <input class="form-control has-feedback-right optional" id="website" placeholder="Website (Optional)" type="url" name="website"  value="<%=currentUser.getWebsite()%>" />
                                <span class="fa fa-globe form-control-feedback right" aria-hidden="true"></span>
                            </div>
                            <div class="item col-md-6 col-sm-6 col-xs-12 form-group has-feedback">
                                <input class="form-control has-feedback-left" id="addressLine" placeholder="Address Line (Optional)" type="text" name="addressLine"  value="<%=currentUser.getAddressLine()%>" />
                                <span class="fa fa-map-marker form-control-feedback left" aria-hidden="true"></span>
                            </div>
                            <div class="item col-md-6 col-sm-6 col-xs-12 form-group has-feedback">
                                <input class="form-control has-feedback-right" id="city" placeholder="City (Optional)" type="text" name="city"  value="<%=currentUser.getCity()%>" />
                                <span class="fa fa-map-marker form-control-feedback right" aria-hidden="true"></span>
                            </div>
                            <div class="item col-md-6 col-sm-6 col-xs-12 form-group has-feedback">
                                <input class="form-control has-feedback-left" id="state" placeholder="State (Optional)" type="text" name="state"  value="<%=currentUser.getState()%>" />
                                <span class="fa fa-map-marker form-control-feedback left" aria-hidden="true"></span>
                            </div>
                            <div class="item col-md-6 col-sm-6 col-xs-12 form-group has-feedback">
                                <input class="form-control has-feedback-right" id="country" placeholder="Country (Optional)" type="text" name="country" value="<%=currentUser.getCountry()%>" />
                                <span class="fa fa-map-marker form-control-feedback right" aria-hidden="true"></span>
                            </div>

                            <div class="clearfix"></div>
                            <span class="section">Account Info</span>

                            <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                                <div class="row">
                                    <div class="item form-group has-feedback col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                        <input class="form-control has-feedback-left" id="username" placeholder="Username *" type="text" name="username" required="required" value="<%=currentUser.getUsername()%>" />
                                        <span class="fa fa-user form-control-feedback left" aria-hidden="true"></span>
                                    </div>
                                </div>
                                <div class="row form-group">
                                    <label class="control-label col-lg-4 col-md-4 col-sm-12" for="changePassword" >Change Password
                                    </label>
                                    <div class="item radio col-lg-4 col-md-4 col-sm-6 ">
                                        <label class="">
                                            <div class="iradio_flat-green" style="position: relative;"><input class="flat" name="changePassword" id="changePasswordYes" value='y' style="position: absolute; opacity: 0;" type="radio"><ins class="iCheck-helper" style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255) none repeat scroll 0% 0%; border: 0px none; opacity: 0;"></ins></div> Yes
                                        </label>
                                    </div>
                                    <div class="item radio col-lg-4 col-md-4 col-sm-6">
                                        <label class="">
                                            <div class="iradio_flat-green" style="position: relative;"><input class="flat" checked name="changePassword" id="changePasswordNo" value='n'  style="position: absolute; opacity: 0;" type="radio"><ins class="iCheck-helper" style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255) none repeat scroll 0% 0%; border: 0px none; opacity: 0;"></ins></div> No
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class=" form-group has-feedback col-lg-6 col-md-6 col-sm-12 col-xs-12">
                                <div class="form-group" style="width: 100%;padding: 0" id="signatureSelection">
                                    <div class="pic" style="width: 100%">
                                        <a>
                                            <div class="picOverlay">
                                                <span class="fa-stack fa-lg">
                                                    Click To Change Signature
                                                </span>
                                            </div>
                                            <img src="<%=request.getContextPath() + "/All?a=" + Constants.ACTION_ALL_GET_SIGNATURE%>" id="signaturePreview" name="signaturePreview" alt = 'signature' data-toggle="tooltip" data-placement="bottom" title="" data-original-title="Signature" title="Signature" class="form-control"  style="width:100%;height: 6em;padding: 0"/>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!--div class="col-md-4 col-lg-4 col-sm-0">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>Help</h2>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="x_content">
                                    <p class="text-justify"> Software designed to support the maintenance of Land Records from Systematic Registration (SLLC) at the Woreda level. This guide presents the operation of IWORLAIS system from registering users and booking transaction to accepting, attaching supporting documents and approving until the transaction is complete and finalized.</p>
                                    <p class="text-justify"> Quid securi etiam tamquam eu fugiat nulla pariatur. Morbi fringilla convallis sapien, id pulvinar odio volutpat. At nos hinc posthac, sitientis piros Afros. Inmensae subtilitatis, obscuris et malesuada fames. Morbi odio eros, volutpat ut pharetra vitae, lobortis sed nibh.</p>
                                    <p class="text-justify"> Morbi fringilla convallis sapien, id pulvinar odio volutpat. Inmensae subtilitatis, obscuris et malesuada fames. Quid securi etiam tamquam eu fugiat nulla pariatur. At nos hinc posthac, sitientis piros Afros. Inmensae subtilitatis, obscuris et malesuada fames. Morbi odio eros, volutpat ut pharetra vitae, lobortis sed nibh.</p>
                                    <p class="text-justify"> Inmensae subtilitatis, obscuris et malesuada fames. Morbi fringilla convallis sapien, id pulvinar odio volutpat.  Morbi fringilla convallis sapien, id pulvinar odio volutpat. Quid securi etiam tamquam eu fugiat nulla pariatur. At nos hinc posthac, sitientis piros Afros. Morbi odio eros, volutpat ut pharetra vitae, lobortis sed nibh.</p>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                        </div-->
                        <div class='clearfix'></div>
                        <div class="ln_solid"></div>   
                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-3">
                                <a href="<%=request.getContextPath() + "/Administrator?a=" + Constants.ACTION_ADMINISTRATOR_USERS%>" class="btn btn-primary">Cancel</a>
                                <button id="submitBtn" type="submit" class="btn btn-success">Submit</button>
                            </div>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>
</div>

<style type="text/css">
    .pic
    {
        display:inline-block !important;
        position:relative !important; 
        overflow: hidden;

    }
    .picOverlay {

        visibility:hidden;
        opacity:0;
        transition:visibility 0s linear 0.5s,opacity 0.5s linear;

    }
    .pic a{

        color:transparent;
    }
    .pic a:hover .picOverlay {
        visibility:visible;
        opacity:1;
        cursor: pointer;
        transition-delay:0s;
        text-align:center;
        position: absolute;
        background-color: rgba(0, 0, 50, 0.58);
        color: #fff;
        width:100%;
        height:100%;
        text-shadow: 0 1px 2px rgba(0, 0, 0, .6);

    }
    .picOverlay span {
        width:100%;
        margin: auto;
        position: absolute;
        top: 0; left: 0; bottom: 0; right: 0
    }
</style>
<script src="<%=request.getContextPath()%>/assets/js/validator/validator.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/select/select2.full.js"></script>
<script type="text/javascript">
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
    var dbox;
    $(document).ready(function () {
        $('form').submit(function (e) {
            e.preventDefault();
            var submit = true;
            // you can put your own custom validations below

            // check all the required fields
            if (validator.checkAll($(this))) {
                this.submit();
            }
            return false;
        });

        $("#photo").change(function (event) {
            if (event.target.files[0] === null) {
                $("#photoPreview").attr("src", "<%=request.getContextPath()%>/assets/images/defaultProfile.png");
            } else {
                $("#photoPreview").attr("src", URL.createObjectURL(event.target.files[0]));
            }
        });
        $("#signature").change(function (event) {
            if (event.target.files[0] === null) {
                $("#signaturePreview").attr("src", "<%=request.getContextPath()%>/assets/images/defaultSignature.png");
            } else {
                $("#signaturePreview").attr("src", URL.createObjectURL(event.target.files[0]));
            }
        });
        $("#signatureSelection").click(function () {
            $("#signature").click();
        });
        $("#profilePicSelection").click(function () {
            $("#photo").click();
        });
        var $a = $("#roles").select2({
            tags: true,
            minimumSelectionLength: 1,
            placeholder: "Please Select User Roles For this account",
            allowClear: true
        });
    
        $('input[name="changePassword"]').on('ifClicked', function (event) {
            var selectedValue = this.value;
            if (selectedValue === "y") {
                dbox = bootbox.dialog({
                    show: true,
                    onEscape: cancelChangePassword,
                    message: "<div id='changePasswordForm'><div class='row'>"
                            + "<div class='item form-group has-feedback col-lg-12 col-md-12 col-sm-12 col-xs-12'>"
                            + "<input class='form-control has-feedback-left' id='newPassword' placeholder='New Password *' type='password' name='newPassword' required='required'>"
                            + "<span class='fa fa-lock form-control-feedback left' aria-hidden='true'></span>"
                            + "</div>"
                            + "<div class='item form-group has-feedback col-lg-12 col-md-12 col-sm-12 col-xs-12'>"
                            + "<input class='form-control has-feedback-left' id='password2' name='newPasswordC' data-validate-linked='newPassword' class='form-control col-md-7 col-xs-12' required='required' type='password' placeholder='Confirm Password *' >"
                            + "<span class='fa fa-lock form-control-feedback left' aria-hidden='true'></span>"
                            + "</div></div></div>",
                    title: "Change Password",
                    buttons: {
                        change: {
                            label: "Change",
                            className: "btn-primary",
                            callback: function (e) {
                                if (validator.checkAll($("#changePasswordForm"))) {
                                    $("#changePassword").val("true");
                                    $("#password").val($("#newPassword").val());
                                    return true;
                                } else {
                                    return false;
                                }
                            }
                        },
                        cancel: {
                            label: "Cancel",
                            className: "btn-default",
                            callback: cancelChangePassword
                        }
                    }
                });

            } else {
                $("#changePassword").val("false");
                $("#password").val("");
                $('#changePasswordNo').iCheck('check');
                $("#newPassword").val("");
                $("#newPasswordC").val("");
                dbox.modal('hide');
            }
        });
        setTitle("Edit Profile");
    });
    function cancelChangePassword() {
        $("#changePassword").val("false");
        $("#password").val("");
        $('#changePasswordNo').iCheck('check');
        $("#newPassword").val("");
        $("#newPasswordC").val("");
        dbox.modal('hide');
    }
</script>