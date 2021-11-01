<%
	
	response.setHeader("pragma","no-cache");//HTTP 1.1
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Cache-Control","no-store");
	response.addDateHeader("Expires", -1);
	response.setDateHeader("max-age", 0);
	response.setIntHeader ("Expires", -1); //prevents caching at the proxy server
	response.addHeader("cache-Control", "private");
	
	String userId = checkNull((String)session.getAttribute("userId"));
	String role = checkNull((String)session.getAttribute("role"));
	String userName = (String)session.getAttribute("DisplayName");
	
	if(userId == null || userId.equals("-1") || userId.equals("")) 
	{  
	     response.sendRedirect("logout");
	     return;
	}
	if(role.equals("User") || role.equals("Functional Admin")){
%>
<!DOCTYPE HTML>
<html lang="en-US">
<head>
<title>thyssenkrupp Industrial Solutions India Pvt Ltd</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
<link href="css/bootstrap.css" rel="stylesheet" type="text/css">

<link href="css/custom.css" rel="stylesheet" type="text/css">

<link href="css/style.css" rel="stylesheet" type="text/css">


<link href="vendor/hover/effects.min.css" rel="stylesheet">

<script src="js/jquery.js"></script><script src="js/common.js"></script>

<script src="js/bootstrap.js"></script>

<script src="js/clientValidation.js"></script>
<script src="js/serverValidation.js"></script>

<script>
$(document).ready(function () {
  var trigger = $('.hamburger'),
      overlay = $('.overlay'),
     isClosed = false;

    trigger.click(function () {
      hamburger_cross();      
    });

    function hamburger_cross() {

      if (isClosed == true) {          
        overlay.hide();
        trigger.removeClass('is-open');
        trigger.addClass('is-closed');
        isClosed = false;
      } else {   
        overlay.show();
        trigger.removeClass('is-closed');
        trigger.addClass('is-open');
        isClosed = true;
      }
  }
  
  $('[data-toggle="offcanvas"]').click(function () {
        $('#wrapper').toggleClass('toggled');
  });  
});
</script>
</head>
<!--<body onload="recommendation_summary();top_IFA_AUM_base();top_IFA_on_investors_base();topIFAonRecommadation()">-->
<body>

<!--header start-->
<div class="header-top">
<div class="container">

  <div class="row">
    <div class="col-md-6 col-sm-6 col-6 centerdiv">
    <div class="logo">
     <img src="images/thyssenkrupp-logo.jpg"  class="img-responsive logoimg"> 
     </div>
     </div>
    <div class="col-md-6 col-sm-6 col-6  heading">QED BOT</div></div>
    <!--header end-->
  </div>  </div>
  <div class="container-fluid p-0">
  	<div id="load_menu"></div>
  </div>
  
<div class="container px-4">
 

<div class="row" style="font-size:16px; padding:5px 2px;">
				<div class="col-md-12 col-sm-12" ><div class="login_user">Welcome <span class="user-name">Dhananjay
 Joshi</span>. You  are logged in as <span  class="user-name">User</span> </div>                               
				</div></div>
<h2>View Project Details</h2>

  <div class="content-area">


 <div id="rec-report-table">
 <table width="100%" border="0" align="left" cellpadding="0" cellspacing="0" class="table tbl-report table-bordered table-striped">
   <thead>    
   <tr valign=middle>
    <th style="width:60px;" rowspan="2">Sr    No</th>
    <th style="width:80px;" rowspan="2">Project</th>
    <th colspan="3">No.of    inconsistencies</th>
  </tr>
  <tr>
    <td class="table-head">At Project start</td>
    <td class="table-head">Resolved</td>
    <td class="table-head">Pending</td>
  </tr>
  
  </thead>
  <tr height="20">
    <td>1</td>
    <td style="width:500px;">PROJECT_001
</td>
    <td>75</td>
    <td>35</td>
    <td><a href="view-project-details.html">40</a></td>
  </tr>
  <tr>
    <td>2</td>
    <td>PROJECT_002
</td>
    <td>40</td>
    <td>20</td>
    <td><a href="view-project-details.html">20</a></td>
  </tr>
  <tr>
    <td>3</td>
    <td>PROJECT_003
</td>
    <td>80</td>
    <td>40</td>
    <td><a href="view-project-details.html">40</a></td>
  </tr>
  <tr>
    <td>4</td>
    <td>PROJECT_004
</td>
    <td>100</td>
    <td>50</td>
    <td><a href="view-project-details.html">50</a></td>
  </tr>
  <tr>
    <td>5</td>
    <td>PROJECT_005
</td>
    <td>158</td>
    <td>79</td>
    <td><a href="view-project-details.html">79</a></td>
  </tr>

  
</table>

</div></div>

</div>
  <div class="container-fluid">

<div class="row"><div class="footer">
&copy  thyssenkrupp Industrial Solutions India Pvt Ltd
</div>
</div>
</div>
<script>		        
       $(document).ready(function () {
           $.ajax({
               url: "menu/usermenu.html", success: function (result) {

                   $("#load_menu").html(result);

               }
           });
       });		
</script>
</body>
</html>
<%
	}else{
		
		out.println("You are not authorized to view this page");
	}%>
<%!
	public String checkNull(String input)
	{
	    if(input == null || "null".equalsIgnoreCase(input) || "undefined".equalsIgnoreCase(input)) {
	    	
	    	input = "";
	    }
	    
	    return input.trim();    
	}
%>
