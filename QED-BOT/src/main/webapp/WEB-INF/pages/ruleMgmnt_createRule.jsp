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
	
	String message = checkNull((String) request.getAttribute("message"));
	
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
.result-left {
	border-top: 1px solid #ccc;
	border-bottom: 1px solid #ccc;
	border-left: 5px solid #00a0f0;
	margin-left: 1px solid #ccc;
	margin-left: 0px;
	background:  #f7fafd;
}
.result-btn {
	border-top: 1px solid #ccc;
	border-right: 1px solid #ccc;
	border-bottom: 1px solid #ccc;
	background: #f7fafd;
	vertical-align: middle;
}
#result {
	text-align: left;
	font-size: 13px;
	padding: 0px 0px 12px 0px;
}
.save-rule {
	background: #dae9ff;
	color: #0d6efd;
	border: none;
	font-weight: bold;
	margin: 22px;
}
.alert {
	padding: 5px;
	font-size: 13px;
	display: inline-block;
	width: 34%;
	margin-bottom: 5px;
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
<div class="container-fluid p-0"><div id="load_menu"></div></div>

<div class="container px-4">

  <div class="row" style="padding:5px 2px;">
    <div class="col-md-12 col-sm-12" >
      <div class="login_user">Welcome <span class="user-name"><%=userName %></span>. You  are logged in as <span  class="user-name"><%=role %></span> </div>
    </div>
  </div>
  
  <h2>Create Rule</h2>  
  <div class="content-area">
  <!-- START -->
  <%if(message.length() > 0) {%>
  <div class="row">
	  <div class="row text-center">
        <div id="message" name="message"  class="alert alert-danger mx-auto mt-3" role="alert"><%=message %></div>
      </div>
  </div>
  <% }else{%>
  <!-- <form   action ="createtable" method = "POST" enctype = "multipart/form-data" id="fileUploadForm" name="fileUploadForm"> -->
  <form id="createRuleFrm" name="createRuleFrm">
 
  <div class="row">
           	
	  <div class="col-md-4 align-self-center">
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
		  
       <div class="col-md-3 select-project align-self-center">      
			<label>Select Project : </label> 
			<sup class="mandatory">*</sup>
			<div class="select-project-003">
			<select class="form-select" name="projectName" id="projectName" onchange="getTablesForSelectedProject(this)">
					<option value="" >--Select Project--</option>		
			</select>
	 	</div>   
	  	</div>
	  	
	  	<div class="col-md-4 align-self-center">
	  	<label>Select Action :</label> 
			<sup class="mandatory">*</sup>
			<div class="select-action-createRule">
			<select class="form-select selectpicker" name="action" id="action" onchange="showHideFieldsForActions(this)">
					<option data-tokens="replace" value="" >-- Select Action--</option>
					<option value="replace">Replace</option>
					<option value="substring">Substring</option>
					<option value="delete">Delete</option>
					
			</select>
	    </div> 
		</div>
      
      </div>
      
      <div class="row mt-3">
      
      	<div class="col-md-3 align-self-center">		
  			<lable>Select Table :</lable>	
	  		<sup class="mandatory">*</sup>	<div class="select-table-createRule">		  	
			  	<select class="form-select" name="tableName" id="tableName" onchange="getTargetFields('','')">
	            	<option value="" selected="selected" >--Select Table--</option>
	           	</select>
	  		</div>	  	
	  	</div>
	  	
	  	<div class="col-md-4 align-self-center">	  	
  			<lable>Select Field :</lable>
  			<sup class="mandatory">*</sup><div class="select-field-createRule">
  			<select class="form-select" name="targetFieldName" id="targetFieldName" >
	            	<option value="" selected="selected" >--Select Field--</option>
	           	</select>	  		
	  	</div>	 
	  	</div>
	  

      </div>
      
      <div class="row mt-3">
      
      <div class="col-md-3 align-self-center" id="targetStringDiv" name="targetStringDiv" style="display:none">	  		  	
	  		<lable>Target String : </lable>	
	  		<sup class="mandatory">*</sup><div class="select-tarrgetString-createRule" >
	  		<input class="form-control" type="text" id="targetString" name="targetString" />	  
	  	</div>
       </div>
       
	  	<div class="col-md-3  align-self-center" id="replaceByDiv" name="replaceByDiv" style="display:none">		  	
		  	 <div class="replace">
		  		<lable>Replace by :</lable>	
		  		<sup class="mandatory">*</sup><div class="select-replace-createRule">
		  		<input class="form-control" type="text" id="replaceBy" name="replaceBy" />
		  	</div>
	  	</div> 
	  	</div>
	  	
	   <div class="col-md-3  align-self-center" id="sourceDiv" name="sourceDiv" style="display:none">	  		
		  		<label>Select Source : </label>
		  		<sup class="mandatory">*</sup>
		  		<div class="select-source-createRule" > 
		  		<select class="form-select" name="source" id="source" >
	            	<option value="" selected="selected" >--Select Source--</option>
	           	</select>
		  		</div>
	  	</div> 
	  	
	  	<div class="col-md-3  align-self-center" id="fromDiv" name="fromDiv" style="display:none">	  			  	
	  		<lable>Starting Position : </lable>	
	  		<sup class="mandatory">*</sup><div class="select-from-createRule" > 
	  		<input class="form-control" type="text" id="from" name="from" onkeypress="return onlyNumberKey(event)" />		  	
	  	</div> 
	  	</div>
	  	
	  	<div class="col-md-3  align-self-center" id="toDiv" name="toDiv" style="display:none">	  			  	
	  		<lable>Up To:</lable>	
	  		<sup class="mandatory">*</sup><div class="select-to-createRule" > 
	  		<input class="form-control" type="text" id="to" name="to" onkeypress="return onlyNumberKey(event)"/>		  
	  	</div> 
	  	</div> 
	  	
	  	<div class="col-md-3  align-self-center"  id="operatorDiv" name="operatorDiv" style="display:none">	  		  	
		  	<label>Operator : </label> 
				<sup class="mandatory">*</sup>
				<div class="select-to-createRule" > 
				<select class="form-select" name="operator" id="operator" >
						<option value="" >-- Select--</option>
						<option value="=">=</option>
						<option value=">">></option>
						<option value="<"><</option>
						<option value=">=">>=</option>
						<option value="<="><=</option>
						<option value="<>"><></option>					
						<option value="like">like</option>					
				</select>
		  	</div>
	  	</div>
	  	  	
	  	<div class="col-md-3  align-self-center" id="valueDiv" name="valueDiv" style="display:none">	  		  
		  		<lable>Value : </lable>	
		  		<sup class="mandatory">*</sup><div class="select-value-createRule" > 	
		  		<input class="form-control" type="text" id="value" name="value" />		  	
	  	</div> 
	  	</div>
	  	
	  	<div class="col-md-3  align-self-center">
          <input class="btn btn-primary " type = "button" value = "Create SQL" onClick="createRule()"/>
          <a href="createRule">
          <input class="btn btn-primary" type = "button" value = "Cancel" />
          </a>
		</div>	
			
      </div>
      
      <div class="row text-center">
        <div id="errorDiv" name="errorDiv"  class="alert alert-danger mx-auto mt-3" role="alert" style="display:none"></div>
      </div>
      
      <div class="row text-center">
        <div id="successDiv" name="successDiv"  class="alert alert-success mx-auto mt-3" role="alert" style="display:none"></div>
      </div>
      
      <div class="row mt-2  justify-content-center" id="saveRuleDiv" style="display:none">      
        <div class="col-md-9 mt-3 result-left">
          <div class="generateQuery">Generated Query:</div>
          <div id="result" name="result" ></div>
        </div>      
        <div class="col-md-2  mt-3 result-btn  justify-content-center">
          <input class="btn btn-primary mx-2  save-rule" type = "button" value = "Save Rule" onClick="saveRule()" />
        </div> 
             
      </div>
    
    </form>
	
  	 <% }%> 
    
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
