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
<%
	String userName = checkNull((String) session.getAttribute("DisplayName"));
	String role =  checkNull((String) session.getAttribute("role"));
%>
<!--<body onload="recommendation_summary();top_IFA_AUM_base();top_IFA_on_investors_base();topIFAonRecommadation()">-->
<body>
<%@page import="java.util.List" %>
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
      <li>
      <a  href="/functionalAdminHome">Table Management</a>
      <ul>
      <li><a  href="/uploadExcel">Create Table</a></li>
      <li><a  href="/modifyTable">Modify Table</a></li>      
      </ul>
      </li>
      <li><a  href="">Mapping Management</a></li>
      <li><a  href="">Rule Management</a></li>
      <li><a  href="/logout">Logout</a></li>
      
    </ul>
  </nav>

</div><!-- #EndLibraryItem --></div>
</div>
<div class="container px-4">
  <div class="row" style="padding:5px 2px;">
    <div class="col-md-12 col-sm-12" >
      <div class="login_user">Welcome <span class="user-name"><%=userName %></span>. You  are logged in as <span  class="user-name"><%=role %></span> </div>
    </div>
  </div>
  <h2>Modify Table</h2>  
  <div class="content-area">
  <!-- START -->
  <form   action ="createtable" method = "POST" enctype = "multipart/form-data" id="fileUploadForm" name="fileUploadForm">
  <div class="row">
           <div class="col-md-3 align-self-center">
      <% 
      		List<String> projectList = (List<String>) request.getAttribute("projectList");
      %>
			<label>Select Project : </label> 
			<select class="form-select tbl-select-opt" name="projectName" id="projectName">
					<option value="select" >-- Select--</option>
					<% for(String projectName : projectList){%>
						
						<option value="<%= projectName%>"><%= projectName%></option>
						
					<%} %>
					
					
			</select>
	 </div>
	 
      <div class="col-md-3 align-self-center">
			<label>Table Type : </label> 
			<select class="form-select tbl-select-opt" name="tabletype" id="tabletype">
					<option value="select" >-- Select--</option>
					<option value="master">Master</option>
					<option value="deliverable">Deliverable</option>
			</select>
	 </div>
	 
	 <div class="col-md-3 align-self-center">
		  <lable></lable>	
		  <input type = "file" name = "file" id="file" value = "Browse File" accept=".xls,.xlsx,.csv"/>	  
	 </div>
	 
	 <div class="col-md-3 align-self-center">
	 	<input class="btn btn-primary mx-2 " type = "button" value = "View Structure" onClick="validateTableStruc()"/>	    
	  	<a href="/uploadExcel"><input class="btn btn-primary" type = "button" value = "Cancel" /></a>
	 </div>
  
  </div>
  
   <div class="row"> 
	   <div class="col-md-12">
	  	<div id="result" name="result"></div>
	  </div>
  </div>
  
    
   <div class="row">
   	<div class="col-md-12">   		
   		<div id="errorDiv" name="errorDiv" class="pt-3" style="color: red;text-align:center"></div> 		
   	</div>
   </div>     
  </div>
	 
        
  </form>
  <!-- END -->
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
