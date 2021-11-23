<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>thyssenkrupp Industrial Solutions India Pvt Ltd</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
<link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="../css/custom.css" rel="stylesheet" type="text/css">
<link href="../css/style.css" rel="stylesheet" type="text/css">
<script src="../js/jquery.js"></script>
<link href="../vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link href='https://fonts.googleapis.com/css?family=Josefin+Sans' rel='stylesheet' type='text/css'>
<link href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css
' rel='stylesheet' type='text/css'>
<link rel="preconnect" href="../https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Quicksand:wght@300;400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>
<!--header start-->
<div class="container-fluid p-0">
  <div class="header-top">
    <div class="container">
      <div class="row">
        <div class="col-md-6 col-sm-6 col-6 centerdiv">
          <div class="logo"> <img src="../images/thyssenkrupp-logo.jpg"  class="img-responsive logoimg"> </div>
        </div>
        <div class="col-md-6 col-sm-6 col-6  heading">QED BOT</div>
      </div>
      <!--header end-->
    </div>
  </div>
</div>

<div class="container-fluid p-0">
  <div class="top_nav" style="height:20px	">
  

</div>
  
  </div>


<div class="demo form-bg">
  <div class="container-fluid">
    <div class="row">
      <div class="col-lg-offset-2 col-lg-7 mx-auto col-md-offset-2 col-md-7">
        <div class="form-container">
          <div class="form-icon">
            <div class="welcome_text">Welcome thyssenkrupp
              <div class="center-block banner_border"></div>
            </div>
            <p class="login-txt">We are thyssenkrupp. We handle a lot of data. We strive to keep it consistent. We create a livable planet.</p>
          </div>
          <form class="form-horizontal" name="frm" id="frm">
            <div class="form-user"><i class="fa fa-user-tie"></i></div>
            <h2 class="login-heading">Login Here</h2>
            <div class="form-group"> <span class="input-icon"><i class="fa fa-envelope"></i></span>
              <input type="text"  id="username" name="username" class="form-control" placeholder="Login ID" autocomplete="off">
            </div>
            <div class="form-group"> <span class="input-icon"><i class="fa fa-lock"></i></span>
              <input class="form-control" type="password" id="password" name="password" placeholder="Password" onKeyPress="return checkenter(event)">
            </div>
            <div class="text-center">
              <button type="button" class="btn signin" onClick ='login_validation()'>Login</button>
              <button class="btn signin" type="reset">Reset</button>
               <div id="valid" style="color:red;"></div>
            </div>
           
          </form>
        </div>
        
      </div>
    </div>
  </div>
</div>
<div class="container-fluid">
  <div class="row">
    <div class="footer"> &copy thyssenkrupp Industrial Solutions India Pvt Ltd </div>
  </div>
</div>

<script language="JavaScript" src="../js/clientscript.js"></script>
<script language="JavaScript" src="../js/serverscript.js"></script>
</body>
</html>
