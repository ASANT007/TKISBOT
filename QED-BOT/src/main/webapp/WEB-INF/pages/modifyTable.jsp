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
	     response.sendRedirect("login");
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
      <li><a  href="/createTable">Create Table</a></li>
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
      		<li><a  href="/executeRule">Execute Rule</a></li>
      		<li><a  href="/viewRulePanel">View Rule</a></li>
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
  <h2>Modify Table</h2>  
  <div class="content-area">
  <!-- START -->
  <form id="modifyTableForm" name="modifyTableForm">
  <div class="row">
     <%-- <div class="col-md-3 align-self-center mdfProjectType">
      <% 
      		List<String> projectList = (List<String>) request.getAttribute("projectList");
      %>
			<label>Select Project : </label>
			<sup class="mandatory">*</sup>
			<select class="form-select tbl-select-opt" name="projectName" id="projectName" onchange="getAllTables(this)">
					<option value="" selected="selected" >-- Select Project--</option>
					<% for(String projectName : projectList){%>
						
						<option value="<%= projectName%>"><%= projectName%></option>
						
					<%} %>
					
					
			</select>
	 </div> --%>
	 <div class="col-md-3 align-self-center">
      		<% 
      			List<Object[]> deliverabletype = (List<Object[]>) request.getAttribute("deliverableType");
     		 %>
     		 <div class="select-action-createRule">
			<label>Select Deliverable Type : </label>
			<sup class="mandatory">*</sup>
			<select class="form-select" name="deliverableType" id="deliverableType" onchange="getProjects(this)">
					<option value="" selected="selected" >--Select Deliverable--</option>
					<% for(Object[] dt : deliverabletype){%>
						
						<option value="<%= (Number) dt[0]%>"><%= (String) dt[1]%></option>
						
					<%} %>
			</select>
	 		</div>
	  </div>
		  
       <div class="col-md-3 select-project align-self-center">
      
			<label>Select Project : </label> 
			<sup class="mandatory">*</sup>
			<select class="form-select tbl-select-opt" name="projectName" id="projectName" onchange="getTablesForSelectedProject(this)">
					<option value="" >--Select Project--</option>		
			</select>
	 </div>
	 <!-- 
	 <div class="col-md-3 align-self-center mfdTableType">
		  <lable>Select Table :</lable>	
		  <sup class="mandatory">*</sup>
		  <div class="selectDropDownDiv">
		  <select class="form-select selectDropDown tbl-select-opt" name="tableName" id="tableName" >
            <option value="" selected="selected" >--Select Table--</option>
           </select>
           </div>
	 </div> -->
	 
      	<div class="col-md-3 align-self-center">
		<div class="select-table-createRule">
  			<lable>Select Table :</lable>	
	  		<sup class="mandatory">*</sup>		  	
			  	<select class="form-select" name="tableName" id="tableName">
	            	<option value="" selected="selected" >--Select Table--</option>
	           	</select>
	  		</div>
	  	</div>
	  	
	  <div class="col-md-3 align-self-center mdfRadio">
	 	<input class="mx-2" type = "radio" id= "viewTableRdo" name = "viewRadioOption" value = "viewtable"/><label>View table</label>    
	  	<input class="mx-2" type = "radio" id= "viewFileRdo" name = "viewRadioOption"value = "viewfile" /><label>View file</label>
	 </div>
	 
	 </div>
	 
	 <div class="row">
	 <div class="col-md-3 align-self-center mdfButtons">
	 	<input class="btn btn-primary mx-2 " type = "button" value = "View" onClick="viewTable()"/>	    
	  	<a href="/modifyTable"><input class="btn btn-primary" type = "button" value = "Cancel" /></a>
	 </div>
  
  </div>
  
  <div class="row"> 
	   <div class="col-md-12">
	  	<div id="colAddResult" name="colAddResult" style="height:35px"></div>
	  </div>
  </div>
   <div class="row"> 
	   <div class="col-md-12">
	  	<div id="result" name="result"></div>
	  </div>
  </div>
  
  <input type='hidden' id="currentTableName" name="currentTableName" value="">
    
   <div class="row">
   	<div class="col-md-12">   		
   		<div id="errorDiv" name="errorDiv" class="pt-3" style="color: red;text-align:center"></div> 		
   	</div>
   </div> 
   
   <div class="row mt-2" style="display:none" id="fileNameUpdationRow" name="fileNameUpdationRow"> 
	   <div class="col-md-4">
	  		<label class="mx-2">Old file Name :</label>
	  		<label class="mx-2"id="oldFileName" name="oldFileName"></label>
	  	</div>
	  	
	  	<div class="col-md-4">
	  		<input class="form-control" type="text" id="newFileName" name="newFileName" placeholder="Please Enter File Name"/>
	  	</div>
	  	
	  	<div class="col-md-4">
  			<input type="button" value ="Update Name" id="updateFileNameBtn" name="updateFileNameBtn" class="btn btn-primary mx-2" onclick="getUpdatedFileName()">
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
