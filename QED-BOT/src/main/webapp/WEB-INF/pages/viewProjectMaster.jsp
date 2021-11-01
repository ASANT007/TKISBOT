<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<jsp:useBean id="encdec" class="com.tkis.qedbot.FileEncryption"/>
<!DOCTYPE HTML>
<html lang="en-US">
<head>
<title>thyssenkrupp Industrial Solutions India Pvt Ltd</title>

<%
		String userId = checkNull((String)session.getAttribute("userId"));
		String role = checkNull((String)session.getAttribute("role"));
		String userName = (String)session.getAttribute("DisplayName");

		response.setHeader("pragma","no-cache");//HTTP 1.1
		response.setHeader("Cache-Control","no-cache");
		response.setHeader("Cache-Control","no-store");
		response.addDateHeader("Expires", -1);
		response.setDateHeader("max-age", 0);
		response.setIntHeader ("Expires", -1); //prevents caching at the proxy server
		response.addHeader("cache-Control", "private");
		
		if(userId == null || userId.equals("-1") || userId.equals(""))
            { %>
			<script language="javascript" type="text/javascript">
			   alert("Your session has expired.")
			   window.location.href = "logout";
			</script>
        <%}else{
	                if(request.getMethod().equals("POST")){
					out.println("Access to this page has been restricted ...");
					return ;
					}
   if(role.equals("Functional Admin")){
%>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
<link href="css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="css/custom.css" rel="stylesheet" type="text/css">
<link href="css/style.css" rel="stylesheet" type="text/css">
<link href="vendor/hover/effects.min.css" rel="stylesheet">
<script src="js/jquery.js"></script>
<script src="js/common.js"></script>
<script src="js/bootstrap.js"></script>


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
     <img src="../images/thyssenkrupp-logo.jpg"  class="img-responsive logoimg"> 
     </div>
     </div>
    <div class="col-md-6 col-sm-6 col-6  heading">QED BOT</div></div>
    <!--header end-->
  </div>  </div>
  
  <div class="container-fluid p-0"><div id="load_menu"></div></div>
  
  
</div>


            
    <div class="container px-4">
 

<div class="row" style="font-size:16px; padding:5px 2px;">
				<div class="col-md-12 col-sm-12" ><div class="login_user">Welcome <span class="user-name"><%=userName%>
</span>. You  are logged in as <span  class="user-name"><%=role%></span> </div>                               
				</div></div>
<h2>Manage project details</h2>

  <div class="content-area">


 <div id="rec-report-table">
 <table border="0" align="center" cellpadding="0" cellspacing="0" class="table tbl-report table-bordered table-striped user-dashboard">
 
      <thead style="position: sticky;top: 0" class="thead-dark">
        <tr>
          <th style="width:50px;" align="center" valign="middle" class="table-heading header">Sr.No</th>
          <th style="width:50px;" align="center" valign="middle" class="table-heading header">Deliverable Type</th>
          <th align="center" valign="middle" class="table-heading header">Project Tag</th>
          <th align="center" valign="middle" class="table-heading header">Project Name</th>
          <th align="center" valign="middle" class="table-heading header">Short Description</th>
          <th align="center" valign="middle" class="table-heading header">Files Path</th>
          <th align="center" valign="middle" class="table-heading header">Status</th>
        </tr>
      </thead>
      <c:if test="${not empty projectMasterList}">
      <tr>
      <c:forEach var="obj" items="${projectMasterList}" varStatus="theCount">
       <c:set var="projectMasterId" value="${obj[0]}"></c:set>
         <% String encProjectId=encdec.Encrypt(Integer.toString((int)pageContext.getAttribute("projectMasterId"))); %>
      <tr valign="top">
      	<td class="text-center"><a href=javascript:void(0) onClick="openwin('editProjectMaster?projectMasterId='+encodeURIComponent('<%=encProjectId%>'))">${theCount.index+1}</a></td>
        <td>${obj[11]}</td>
        <td>${obj[2]}</td>
        <td>${obj[3]}</td>
        <td>${obj[4]}</td>
        <td>${obj[10]}</td>
        <td>${obj[5]}</td>
        </tr>
	</c:forEach>
	</tr>
	</c:if>
	   <c:if test="${ empty projectMasterList}">
	    <td colspan="6" align="center"><div>No Data Found</div></td>
	   </c:if>
       

     
    </table> 

</div></div>

</div>
  <div class="container-fluid">

<div class="row"><div class="footer">
&copy  thyssenkrupp Industrial Solutions India Pvt Ltd
</div>
</div>
</div>
<script type="text/javascript">
function openwin(url){
    var newwindow=open(url,"win","height=410,width=800,left=100,top=100,status=1,scrollbars=no");
}
</script>
<script language="JavaScript" src="js/clientscript.js"></script>
<script language="JavaScript" src="js/serverscript.js"></script>
<script>
        
        $(document).ready(function () {
            $.ajax({
                url: "menu/menu.html", success: function (result) {

                    $("#load_menu").html(result);

                }
            });
        });
		
		
		
    </script>


 <%}
        else{
            out.println("You are not authorized to view this page");
	}
    }
%>

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