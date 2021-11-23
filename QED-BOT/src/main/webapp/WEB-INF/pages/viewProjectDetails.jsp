<%@page import="org.json.simple.JSONArray"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.parser.JSONParser"%>
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
	
	
	int projectId = 0;
	String fiterKeyField = "", projectName = "";
	projectId = (Integer) session.getAttribute("projectId");
	projectName = (String) request.getAttribute("projectName");
		
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

.colors {
	display:none;
}
</style>
<script src="js/clientValidation.js"></script>
<script src="js/serverValidation.js"></script>

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

<div class="container-fluid p-0">
	<div id="load_menu"></div>
</div>

<div class="container-fluid px-4">
  <div class="row" style="font-size:16px; padding:5px 2px;">
    <div class="col-md-12 col-sm-12" >
      <div class="login_user">Welcome <span class="user-name"><%=userName %></span>. You  are logged in as <span  class="user-name"><%=role %></span> </div>
    </div>
  </div>
  <div class="row">
  
  <div class="col-md-12">  
  <div style="display:inline-block;vertical-align: middle;">
  <% 
      			List<Object[]> keyFieldList = (List<Object[]>) request.getAttribute("keyField");
     		 %>
 	<label>Select Key Field</label>  
  	<select name='dropDownkeyField' id='dropDownkeyField'  class="form-select width-auto" style="width:250px;margin:0 10px 10px 10px;display:inline-block" onchange="getMappedDeliverableFieldByKeyField('<%=projectId%>')">
  			<option value="" selected="selected" >--Select Key Field--</option>
					<% for(Object[] kfRow : keyFieldList){
						if(checkNull((String) kfRow[1]).length() >0){
							//Change below code
							if(checkNull(fiterKeyField).equals((String) kfRow[1])){ %>
							<option value="<%= (Number) kfRow[0]%>" selected><%= (String) kfRow[1]%></option>
							<% }else{ %>
							<option value="<%= (Number) kfRow[0]%>"><%= (String) kfRow[1]%></option>
							<%}	
						}
										
						
					} %>
           
        </select> 
  </div>
   
  
  	<div style="display:inline-block; vertical-align: middle;">	  	
  			<lable>Select Field :</lable>
  			<sup class="mandatory">*</sup>
  			<select class="form-select width-auto" style="display: inline-block;" name="filterDeliverableField" id="filterDeliverableField" >
	            	<option value="" selected="selected" >--Select Field--</option>
	        </select>	  		
	  		 
  	</div>
  
  
  <div style="display:inline-block;vertical-align: middle;">
  	<input id="goBttn" type="button" class="btn btn-primary done" value="Submit" onClick="getInConsistencyDataByFilter('<%=userId%>','<%=projectId%>')"/>
  </div>
  </div>
  </div>
  
  <div class="content-area mt-3" style="padding:10px 20px 10px 20px">
  <div> <h2>Inconsistency for Project: <span><%=projectName%></span> </h2>
  <div id="rec-report-table">
  <div class="content-inner" id="inconsistencyCheckDiv" name="inconsistencyCheckDiv">
   </div>
   </div>
 </div>
  
  
  </div>
</div>
<div class="container-fluid">
  <div class="row">
    <div class="footer"> &copy  thyssenkrupp Industrial Solutions India Pvt Ltd </div>
  </div>
</div>


<%if(role.equals("Functional Admin")){
System.out.println("role "+role);
%>

<script>
		        
		        $(document).ready(function () {
		            $.ajax({
		                url: "menu/menu.html", success: function (result) {

		                    $("#load_menu").html(result);

		                }
		            });
		        });			
				
				
	    </script>		
<%}else{%>
<script>
		        
		        $(document).ready(function () {
		            $.ajax({
		                url: "menu/usermenu.html", success: function (result) {

		                    $("#load_menu").html(result);

		                }
		            });
		        });			
				
				
	    </script>	
<% } %>



 <script>
$(function () {
    $("#goBttn").click(function () {
        $("#dropDown").find("option").each(function () {
            var div_id = $(this).val();
            $("." + div_id).each(function () {
                $(this).hide();
            });
        });
        $("." + $("#dropDown").val()).each(function () {
            $(this).show();
        });
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