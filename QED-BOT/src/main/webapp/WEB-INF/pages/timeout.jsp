<%
	session.removeAttribute("userId");
	session.removeAttribute("DisplayName");
	session.removeAttribute("role");	
	session.invalidate();
	
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

<script src="js/jquery.js"></script><script src="js/common.js"></script>

<script src="js/bootstrap.js"></script>

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

<!--header start-->
<div class="header-top">
<div class="container">

  <div class="row">
    <div class="col-md-6 col-sm-6 col-6 centerdiv">
    <div class="logo">
     <img src="images/thyssenkrupp-logo.jpg"  class="img-responsive logoimg"> 
     </div>
     </div>
    <div class="col-md-6 col-sm-6 col-6  heading">QED BOT</div></div>
    <!--header end-->
  </div>  </div>
  <div class="container-fluid p-0">
  	
  </div>


            
    <div class="container px-4">
 

<div class="row" style="font-size:16px; padding:5px 2px;">
				<div class="col-md-12 col-sm-12" ><div class="login_user"></span> <span  class="user-name"></span> </div>                               
				</div></div>


  <div class="content-area">
	<h1>Your Session is Expired</h1>
	<span><a  href="/">Click here to login</a></span>
	</div>

</div>
  <div class="container-fluid">

<div class="row"><div class="footer">
&copy  thyssenkrupp Industrial Solutions India Pvt Ltd
</div>
</div>
</div>
</body>
</html>
