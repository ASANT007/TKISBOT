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
<script src="js/jquery.js"></script>
<script src="js/common.js"></script>
<script src="js/bootstrap.js"></script>
<style>
.header {
	position: sticky;
	top:0;
}
body {
	height: 800px;
}<%
	
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
%>
<!DOCTYPE HTML>
<html lang="en-US">
<head>
<title>Thyssenkrupp Industrial Solutions India Pvt Ltd</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
<link href="css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="css/custom.css" rel="stylesheet" type="text/css">
<link href="css/style.css" rel="stylesheet" type="text/css">
<link href="vendor/hover/effects.min.css" rel="stylesheet">
<script src="js/jquery.js"></script>
<script src="js/common.js"></script>
<script src="js/bootstrap.js"></script>
<style>
.header {
	position: sticky;
	top:0;
}
body {
	height: 800px;
}
</style>
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
        <div class="logo"> <img src="images/thyssenkrupp-logo.jpg"  class="img-responsive logoimg"> </div>
      </div>
      <div class="col-md-6 col-sm-6 col-6  heading">QED BOT</div>
    </div>
    <!--header end-->
  </div>
</div>
<div class="container-fluid p-0"><!-- #BeginLibraryItem "/Library/topnav.lbi" --><div class="top_nav"> <span class="top_nav_trigger">Menu</span>
  <nav class="top_nav_links">
    <ul>
      <li class="topnav-first"><a class="topnav_home" href="dashboard.html">Dashboard</a></li>
      <li><a  href="project-tracking.html">View Project Details</a></li>
      <li><a  href="login.html">Logout</a></li>
    </ul>
  </nav>

</div><!-- #EndLibraryItem --></div>
</div>
<div class="container px-4">
  <div class="row" style="padding:5px 2px;">
    <div class="col-md-12 col-sm-12" >
      <div class="login_user">Welcome <span class="user-name">Dhananjay
        Joshi</span>. You  are logged in as <span  class="user-name">User</span> </div>
    </div>
  </div>
  <h2>User Dashboard</h2>  <div class="content-area">
  <div id="rec-report-table" >
    <table border="0" align="center" cellpadding="0" cellspacing="0" class="table tbl-report table-bordered table-striped user-dashboard">
      <thead style="position: sticky;top: 0" class="thead-dark">
        <tr>
          <th style="width:50px;" align="center" valign="middle" class="table-heading header">Sr.No</th>
          <th align="center" valign="middle" class="table-heading header">Project</th>
          <th  width="32%" align="center" valign="middle" class="table-heading header">No.of inconsistencies</th>
        </tr>
      </thead>
      <tr>
      <tr valign="top">
        <td class="text-center">1</td>
        <td>PROJECT_001 </td>
        <td><a href="view-project-details.html">75</a></td>
      </tr>
      <tr valign="top">
        <td class="text-center">2</td>
        <td>PROJECT_002 </td>
        <td><a href="view-project-details.html">40</a></td>
      </tr>
      <tr valign="top">
        <td class="text-center">3</td>
        <td>PROJECT_003 </td>
        <td><a href="view-project-details.html">80</a></td>
      </tr> <tr valign="top">
        <td class="text-center">4</td>
        <td>PROJECT_004 </td>
        <td><a href="view-project-details.html">100</a></td>
      </tr> <tr valign="top">
        <td class="text-center">5</td>
        <td>PROJECT_005 </td>
        <td><a href="view-project-details.html">158</a></td>
      </tr>
    </table>  </div>
  </div>
</div>
<div class="container-fluid">
  <div class="row">
    <div class="footer"> &copy  thyssenkrupp Industrial Solutions India Pvt Ltd </div>
  </div>
</div>
</body>
</html>
<%!
	public String checkNull(String input)
	{
	    if(input == null || "null".equalsIgnoreCase(input) || "undefined".equalsIgnoreCase(input)) {
	    	
	    	input = "";
	    }
	    
	    return input.trim();    
	}
%>

</style>
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
        <div class="logo"> <img src="images/thyssenkrupp-logo.jpg"  class="img-responsive logoimg"> </div>
      </div>
      <div class="col-md-6 col-sm-6 col-6  heading">QED BOT</div>
    </div>
    <!--header end-->
  </div>
</div>
<div class="container-fluid p-0"><!-- #BeginLibraryItem "/Library/topnav.lbi" --><div class="top_nav"> <span class="top_nav_trigger">Menu</span>
  <nav class="top_nav_links">
    <ul>
      <li class="topnav-first"><a class="topnav_home" href="dashboard.html">Dashboard</a></li>
      <li><a  href="project-tracking.html">View Project Details</a></li>
      <li><a  href="login.html">Logout</a></li>
    </ul>
  </nav>

</div><!-- #EndLibraryItem --></div>
</div>
<div class="container px-4">
  <div class="row" style="padding:5px 2px;">
    <div class="col-md-12 col-sm-12" >
      <div class="login_user">Welcome <span class="user-name">Dhananjay
        Joshi</span>. You  are logged in as <span  class="user-name">User</span> </div>
    </div>
  </div>
  <h2>User Dashboard</h2>  <div class="content-area">
  <div id="rec-report-table" >
    <table border="0" align="center" cellpadding="0" cellspacing="0" class="table tbl-report table-bordered table-striped user-dashboard">
      <thead style="position: sticky;top: 0" class="thead-dark">
        <tr>
          <th style="width:50px;" align="center" valign="middle" class="table-heading header">Sr.No</th>
          <th align="center" valign="middle" class="table-heading header">Project</th>
          <th  width="32%" align="center" valign="middle" class="table-heading header">No.of inconsistencies</th>
        </tr>
      </thead>
      <tr>
      <tr valign="top">
        <td class="text-center">1</td>
        <td>PROJECT_001 </td>
        <td><a href="view-project-details.html">75</a></td>
      </tr>
      <tr valign="top">
        <td class="text-center">2</td>
        <td>PROJECT_002 </td>
        <td><a href="view-project-details.html">40</a></td>
      </tr>
      <tr valign="top">
        <td class="text-center">3</td>
        <td>PROJECT_003 </td>
        <td><a href="view-project-details.html">80</a></td>
      </tr> <tr valign="top">
        <td class="text-center">4</td>
        <td>PROJECT_004 </td>
        <td><a href="view-project-details.html">100</a></td>
      </tr> <tr valign="top">
        <td class="text-center">5</td>
        <td>PROJECT_005 </td>
        <td><a href="view-project-details.html">158</a></td>
      </tr>
    </table>  </div>
  </div>
</div>
<div class="container-fluid">
  <div class="row">
    <div class="footer"> &copy  thyssenkrupp Industrial Solutions India Pvt Ltd </div>
  </div>
</div>
<script>
        
        $(document).ready(function () {
            $.ajax({
                url: "menu/menu.html", success: function (result) {

                    $("#load_menu").html(result);

                }
            });
        });	
		
    </script>
</body>
</html>
