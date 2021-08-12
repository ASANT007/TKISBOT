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
      
      <li>
      	<a  href="/functionalAdminHome">Mapping Management</a>
      </li>
      	
       <li>
      	<a  href="/functionalAdminHome">Rule Management</a>
      	<ul>
      		<li><a  href="/createRule">Create Rule</a></li>
      	</ul>
      </li>
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
  <h2>Create Rule</h2>  
  <div class="content-area">
  <!-- START -->
  <form   action ="createtable" method = "POST" enctype = "multipart/form-data" id="fileUploadForm" name="fileUploadForm">
  <div class="row">
           <div class="col-md-3 align-self-center mdfProjectType">
      		<% 
      		List<String> projectList = (List<String>) request.getAttribute("projectList");
     		 %>
			<label>Select Project : </label>
			<sup class="mandatory">*</sup>
			<select class="form-select tbl-select-opt" name="projectName" id="projectName" onchange="showRuleMgmntPanel(this)">
					<option value="" selected="selected" >-- Select Project--</option>
					<% for(String projectName : projectList){%>
						
						<option value="<%= projectName%>"><%= projectName%></option>
						
					<%} %>
					
					
			</select>
	 		</div>
	  </div>
	  <div class="row" id="ruleMgmntPanel" style = "display:none">	  	
	  	<div class="col-md-12 mt-3 align-self-center">
	  	<div class="select-action-createRule"><label>Select Action</label> 
			<sup class="mandatory">*</sup>
			<select class="form-select" name="action" id="action" onchange="getActionOperations(this)">
					<option value="" >-- Select Action--</option>
					<option value="replace">Replace</option>
					<option value="concatenate">Concatenate</option>
					<option value="mid">Mid</option>
					<option value="left">Left</option>
					<option value="right">Right</option>
					<option value="delete">Delete</option>					
			</select>
	    </div> 
		
		<div class="select-table-createRule">
  			<lable>Select Table :</lable>	
	  		<sup class="mandatory">*</sup>
		  	
			  	<select class="form-select" name="tableName" id="tableName" onchange="getTargetFields(this)">
	            	<option value="" selected="selected" >--Select Table--</option>
	           	</select>
	  		
	  	</div>
	  	
	  	<div class="select-field-createRule">
  			<lable>Select Field :</lable>	
	  		<sup class="mandatory">*</sup>
		  
			  	<select class="form-select" name="targetFieldName" id="targetFieldName" >
	            	<option value="" selected="selected" >--Select Field--</option>
	           	</select>
	  		
	  	</div>	 
	  	
	  	<div class="select-tarrgetString-createRule" >
	  		<div id="targetStringDiv" name="targetStringDiv" style="display:none">
	  		<lable>Target String</lable>	
	  		<sup class="mandatory">*</sup>
	  		<input class="form-control" type="text" id="targetString" name="targetString" />
	  	</div>
	  	
	  	<div class="select-replace-createRule" > 
		  	<div id="replaceByDiv" name="replaceByDiv" style="display:none">
		  		<lable>Replace by</lable>	
		  		<sup class="mandatory">*</sup>
		  		<input class="form-control" type="text" id="replaceBy" name="replaceBy" />
		  	</div>
	  	</div> 
	  	<!-- START -->
	  	
	  	<div class="select-to-createRule" > 
		  	<div id="operatorDiv" name="operatorDiv" style="display:none">
		  	<label>Operator</label> 
				<sup class="mandatory">*</sup>
				<select class="form-select" name="operator" id="operator" >
						<option value="" >-- Select--</option>
						<option value="=">=</option>
						<option value=">">></option>
						<option value="<"><</option>
						<option value=">=">>=</option>
						<option value="<="><=</option>
						<option value="<>"><></option>					
				</select>
		  	</div>
	  	</div>
	  	
	  	<div class="select-source-createRule" > 
		  	<div id="sourceDiv" name="sourceDiv" style="display:none">
		  		<lable>Select Source</lable>	
		  		<sup class="mandatory">*</sup>
		  		<select class="form-select" name="source" id="source" >
	            	<option value="" selected="selected" >--Select Source--</option>
	           	</select>
		  	</div>
	  	</div> 
	  	<div class="select-from-createRule" > 
		  	<div id="fromDiv" name="fromDiv" style="display:none">
		  		<lable>From</lable>	
		  		<sup class="mandatory">*</sup>
		  		<input class="form-control" type="text" id="from" name="from" />
		  	</div>
	  	</div> 
	  	
	  	<div class="select-to-createRule" > 
		  	<div id="toDiv" name="toDiv" style="display:none">
		  		<lable>To</lable>	
		  		<sup class="mandatory">*</sup>
		  		<input class="form-control" type="text" id="to" name="to" />
		  	</div>
	  	</div>   
		<!-- END -->	                   
	  	</div>
	  	
	  	
	  	
	  	
	  	
	  </div>
	  
	  <div class="row" style = "display:none" id="createRuleBtnDiv">
	  	<div class="col-md-12 align-self-center py-3" style="text-align:center;">
	 	<input class="btn btn-primary mx-2 " type = "button" value = "Create SQL" onClick="createRule()"/>	    
	  	<a href="/createRule"><input class="btn btn-primary" type = "button" value = "Cancel" /></a>
	  	
	 	</div>
	  </div>
	  
	  <div class="row mt-2 text-center">	  
	  	<div id="errorDiv" name="errorDiv" style="color:red"></div>	  	
	  </div>
	  
	 <div class="row mt-2" id="saveRuleDiv" style="display:none">
	   <div class="col-md-8 text-center">
	  	<div id="result" name="result"></div>	  	
	  </div>
	  <div class="col-md-4">
	  	<input class="btn btn-primary mx-2 " type = "button" value = "Save Rule" onClick="saveRule()" />
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
