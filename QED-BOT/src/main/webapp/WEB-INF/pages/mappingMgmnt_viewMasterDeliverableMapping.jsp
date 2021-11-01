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
	
	if(role.equals("Functional Admin")){
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
		
		<body>
		<%@page import="java.util.List" %>
		<%
			//String userName = checkNull((String) session.getAttribute("DisplayName"));
			//String role =  checkNull((String) session.getAttribute("role"));
		%>
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

		<div class="container-fluid p-0"><div id="load_menu"></div></div>

		<div class="container px-4">
		  <div class="row" style="padding:5px 2px;">
		    <div class="col-md-12 col-sm-12" >
		      <div class="login_user">Welcome <span class="user-name"><%=userName %></span>. You  are logged in as <span  class="user-name"><%=role %></span> </div>
		    </div>
		  </div>
		  <h2>View Master Deliverable Mapping</h2>  
		  <div class="content-area">
		  <!-- START -->
	 <div class="row">
		   <div class="col-md-4 select-delivereable-type-createtable align-self-center">
      		<% 
      			List<Object[]> deliverabletype = (List<Object[]>) request.getAttribute("deliverableType");
     		 %>
     		 
			<label>Select Deliverable Type : </label>
			<sup class="mandatory">*</sup><div class="select-deliverable-type">
			<select class="form-select" name="deliverableType" id="deliverableType" onchange="getProjects(this)">
					<option value="" selected="selected" >--Select Deliverable--</option>
					<% for(Object[] dt : deliverabletype){%>
						
						<option value="<%= (Number) dt[0]%>"><%= (String) dt[1]%></option>
						
					<%} %>
			</select>
	 		</div>
		  </div>
			  
		  <div class="col-md-3 align-self-center">    
			<label>Select Project : </label> 
			<sup class="mandatory">*</sup><div class="select-project-003">		
			<select class="form-select tbl-select-opt" name="projectName" id="projectName" onchange="getTablesForSelectedProject(this)">
					<option value="" >--Select Project--</option>		
			</select></div> 	 	 	
	 	 </div>
		  
		<div class="col-md-4  align-self-center">
          	<input class="btn btn-primary " type = "button" value = "View" onClick="viewMappedMasterDeliverableData()"/>
          	<a href="viewMappedMasterDeliverablePage">
          	<input class="btn btn-primary" type = "button" value = "Cancel" />
          	</a>
		</div>
		  
	  </div>
	  <div class="row text-center my-3">
	  		<div class="mx-auto mt-1" name="message" id="message" style="width:37%;"></div>
	  </div>
	  
	  <div class="row mt-3" name="masterDeliverableMappingDiv" id="masterDeliverableMappingDiv" style="display:none">
	  <div style="text-align:right; margin-bottom:15px;">
	  	<input class="btn btn-primary" type="button" name="saveMDMappingChangeBtn" id="saveMDMappingChangeBtn" value="Remove Selected" onClick="removeMasterDeliverableMapping()" style="width:12%;display:none">
	  </div>
	  
	  <table class="table table-responsive table-bordered table-striped" id="MDMappingTable" name="MDMappingTable"><tbody><tr>
		<th style="width:5%; text-align:center;">Sr No</th><th>Master Table</th><th>Master Field</th><th>Deliverable Table</th><th>Deliverable Field</th><th>Remove Selected</th>
		</tr></tbody></table> 
	  </div>
	  
		  <!-- END -->
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
