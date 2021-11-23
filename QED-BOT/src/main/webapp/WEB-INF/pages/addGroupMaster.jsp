<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE HTML>
<html lang="en-US">
<head>
<title>thyssenkrupp Industrial Solutions India Pvt Ltd</title>

<%
		String username = (String)session.getAttribute("username");
		String role = (String)session.getAttribute("role");

		response.setHeader("pragma","no-cache");//HTTP 1.1
		response.setHeader("Cache-Control","no-cache");
		response.setHeader("Cache-Control","no-store");
		response.addDateHeader("Expires", -1);
		response.setDateHeader("max-age", 0);
		response.setIntHeader ("Expires", -1); //prevents caching at the proxy server
		response.addHeader("cache-Control", "private");
		
		if(username == null || username.equals("-1") || username.equals(""))
            { %>
			<script language="javascript" type="text/javascript">
			   alert("Your session has expired.")
			   window.location.href = "superAdminLogout";
			</script>
        <%}else{
	                if(request.getMethod().equals("POST")){
					out.println("Access to this page has been restricted ...");
					return ;
					}
        if(role.equals("Administrator")){
%>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
<link href="../css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="../css/custom.css" rel="stylesheet" type="text/css">
<link href="../css/style.css" rel="stylesheet" type="text/css">
<link href="../vendor/hover/effects.min.css" rel="stylesheet">
<script src="../js/jquery.js"></script><script src="../js/common.js"></script>
<script src="../js/bootstrap.js"></script>


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
  
  


            
    <div class="container px-4">
 

<div class="row" style="font-size:16px; padding:5px 2px;">
				<div class="col-md-12 col-sm-12" ><div class="login_user">Welcome <span class="user-name"><%=username%>
</span>. You  are logged in as <span  class="user-name"><%=role%></span> </div>                               
				</div></div>
<h2>Add Group Details</h2>

<div class="content-area">
    <!-- START -->
    <form   id="frm" name="frm">
      <div class="row">
      
      <div class="col-md-4  align-self-center">
        
		  
		  		<label>Group Name : </label>	
		  		<sup class="mandatory">*</sup> <div class="projectName" > 
		  		<input class="form-control" type="text" id="groupName" name="groupName" />
		  
	  	</div> 
        </div>
      
        <div class="col-md-5 align-self-center">
          
            <label>Select Role : </label>
            <sup class="mandatory">*</sup><div class="select-action-createRule">
            <select class="form-select" name="groupRole" id="groupRole" >
              <option value="select role" selected="selected" >Select Role </option>
             <option value="Functional Admin">Functional Admin</option>
             <option value="Mgmt User">Mgmt User</option>
             <option value="User">User</option>
            </select>
          </div>
        </div>
        
        
      </div>
      <div class="row mt-3">
      
        <div class="col-md-3  align-self-center">
          <input class="btn btn-primary " type = "button" value = "Add Group" onClick="addGroup()"/>
         <input class="btn btn-primary" type = "reset" value = "Reset" onClick='document.getElementById("errormsg").innerHTML=""' />
         </div>
      </div>
      <div class="row"  id="createRuleBtnDiv">
        <div class="col-md-12 align-self-center py-3" style="text-align:center;"> </div>
      </div>
      <div class="row text-center">
       <div id="errormsg" style="color:red;"></div>
      </div>
     
    </form>
    <!-- END -->
  </div>

</div>
  <div class="container-fluid">

<div class="row"><div class="footer">
&copy  thyssenkrupp Industrial Solutions India Pvt Ltd
</div>
</div>
</div>
<script type="text/javascript">
function openwin(url){
    var newwindow=open(url,"win","height=225,width=660,left=100,top=100,status=1,scrollbars=yes");
}
</script>
<script language="JavaScript" src="../js/clientscript.js"></script>
<script language="JavaScript" src="../js/serverscript.js"></script>
<script>
        
        $(document).ready(function () {
            $.ajax({
                url: "../menu/superAdminMenu.html", success: function (result) {

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

