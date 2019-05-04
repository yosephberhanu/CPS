<%-- 
    Document   : Welcome Page - All Logged In Users
    Created on : Dec 26, 2016, 09:15:35 PM
    Author     : Yoseph Berhanu<yoseph@artisan.et> 
--%>
<%@page import="et.artisan.cn.cps.util.CommonStorage"%>
<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>Welcome</h3>
        </div>
    </div>
    <div class="clearfix"></div>

    <div class="row">
        <div class="col-md-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>CN - CPS <small>Commercial Nominees Commission Payment Management System</small></h2>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">

                    <div class="col-md-7 col-lg-7 col-sm-7">
                        <!-- blockquote -->
                        <blockquote>
							<p class="text-justify"> Commission Payments Management System (CPS) is designed and developed to manage the outsourced payment service Commercial Nominees (CN) provides for its various clients such as Ethiopian Roads Authority and Ethiopian Railway Corporation. The system handles the entire process from contract singing with the client to payment requests registration to commission calculation and staling. It provided reporting and monitoring features for managerial and decision support.</p>
                            <p class="text-justify"> CN's CPS is a Web Application that runs in any modern browser on any operating system. This means that the system is supported on Windows, MAC OS, Linux and tablets of any architecture ? provided they have a web ? browser. The application is, however, optimized to run on desktop computers. The recommended browsers are Google Chrome and Mozilla Firefox. No extensions are required in order to use the application.</p>
                            <p class="text-justify"> The database runs on a server computer that also hosts the application server. The application server is Glassfish Version 4. The database software used is PostgreSQL 9.3.</p>
                        </blockquote>

                    </div>
                    <div class="col-md-5 col-lg-5 col-sm-5 center">
                        <h1 style='color:#C40000'>Artisan Technologies</h1>
                        <h2 style='color:#C40000'>Innovate. Create. Deliver.</h2>
                        <p class="text-justify">
                            <image src="<%=request.getContextPath()%>/assets/images/artisan_icon.png" style="height:8em; float:left    ;padding:.5em" />
                            Artisan Technologies, is a Software Development and Consultancy firm established on March 2015. The company was established to address the demand for high quality software by the Ethiopian IT industry. We plan to positively impact the local industry by delivering innovative, elegant IT solution on time and with a reasonable price.
                        </p>
                        <image src="<%=request.getContextPath()%>/assets/images/defaultSignature.png" style="height:5em; float:right;padding-right:3em" />
                    </div>
                    <div class="clearfix"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        setTitle("Welcome");
    });
</script>