<%@page import="java.util.List" %>
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
	if(role.equals("Mgmt User") || role.equals("Functional Admin")){
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
<script src="js/chart.min.js"></script>
<script src="js/bootstrap.js"></script>
<script src="js/clientValidation.js"></script>
<script src="js/serverValidation.js"></script>
<style>
.header {
	position: sticky;
	top:0;
}
body {
	height: 800px;
}
.hidden {
	display:none;
}
</style>
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

<div class="container-fluid p-0"><div id="load_menu"></div></div><!-- Dynamic Menu -->

<div class="container-fluid px-4">

  <div class="row" style="font-size:16px; padding:5px 2px;">
    <div class="col-md-12 col-sm-12" >
     <div class="login_user">Welcome <span class="user-name"><%=userName %></span>. You  are logged in as <span  class="user-name"><%=role %></span> </div>
    </div>
  </div>
   

   <!-- Added by AMOL S on 03-11-2021 START -->
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
					  
		<div class="col-md-4  align-self-center">
          	<input class="btn btn-primary " type = "button" value = "Submit" onClick="getProjectWiseReport()"/>
          	<a href="projectWiseReport">
          	<input class="btn btn-primary" type = "button" value = "Cancel" />
          	</a>
		</div>
		  
	  </div>
	  
	  <div class="row text-center mt-3">
        <div id="errorDiv" name="errorDiv"  class="alert alert-danger mx-auto mt-3 col-md-4  align-self-center" role="alert" style="display:none"></div>
      </div>
       
  <div id="projectWiseCanvasDiv" class="projectWiseCanvasDiv" >
    <div class="content-area">
      <h2 id ="heading" name="heading" style="display:none">Project Wise Inconsistency Report For Deliverable Type :<span id="deliverableName" style="margin-left: 10px;"></span></h2>
      <canvas  style="width:700px;margin:0 auto;" id="myChart"></canvas>
    </div>
  </div>  
</div>

<div class="container-fluid">
  <div class="row">
    <div class="footer"> &copy  thyssenkrupp Industrial Solutions India Pvt Ltd </div>
  </div>
</div>

<!-- <script>

      var ctx = document.getElementById('myChart').getContext('2d');
      var myChart = new Chart(ctx, {
          type: 'bar',
          data: {
            labels: ["PROJECT_001", "PROJECT_002", "PROJECT_003", "PROJECT_004", "PROJECT_005"],
            datasets: [ { 
                data: [75,40,80,100,158],
                label: "At Project start",
                borderColor: "#e74c3c",
                backgroundColor: "#e74c3c",
                borderWidth:2
              }, { 
                data: [40,20,40,50,49],
                label: "Pending",
                borderColor: "#4499c3",
                backgroundColor:"#4499c3",
                borderWidth:2,
              }
            ]
          }
		  
        });
		
		  var ctxa = document.getElementById('myChart2').getContext('2d');
      var myChart = new Chart(ctxa, {
          type: 'bar',
          data: {
            labels: ["PROJECT_001", "PROJECT_002", "PROJECT_003", "PROJECT_004", "PROJECT_005"],
            datasets: [ { 
                data: [75,40,80,100,158],
                label: "At Project start",
                borderColor: "#e74c3c",
                backgroundColor: "#e74c3c",
                borderWidth:2
              }, { 
                data: [40,20,40,50,49],
                label: "Pending",
                borderColor: "#4499c3",
                backgroundColor:"#4499c3",
                borderWidth:2,
              }
            ]
          }
		  
        });
 		  var ctxc = document.getElementById('myChart3').getContext('2d');
      var myChart = new Chart(ctxc, {
          type: 'bar',
          data: {
            labels: ["PROJECT_001", "PROJECT_002", "PROJECT_003", "PROJECT_004", "PROJECT_005"],
            datasets: [ { 
                data: [75,40,80,100,158],
                label: "At Project start",
                borderColor: "#e74c3c",
                backgroundColor: "#e74c3c",
                borderWidth:2
              }, { 
                data: [40,20,40,50,49],
                label: "Pending",
                borderColor: "#4499c3",
                backgroundColor:"#4499c3",
                borderWidth:2,
              }
            ]
          }
		  
        });
    </script>
 -->  
 
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
  <!-- Menu Dynamic START -->
	<script>
        
        $(document).ready(function () {
            $.ajax({
                url: "menu/menu.html", success: function (result) {

                    $("#load_menu").html(result);

                }
            });
        });			
		
		
   </script>
   <!-- Menu Dynamic END -->
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