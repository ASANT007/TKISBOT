<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Thyssenkrupp Industrial Solutions India Pvt Ltd</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="css/custom.css" rel="stylesheet" type="text/css">
<link href="css/style.css" rel="stylesheet" type="text/css">
<script src="js/jquery.js"></script>
<link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link href='https://fonts.googleapis.com/css?family=Josefin+Sans' rel='stylesheet' type='text/css'>
<link href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css
' rel='stylesheet' type='text/css'>
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Quicksand:wght@300;400;500;600;700&display=swap" rel="stylesheet">
<script src="js/clientValidation.js"></script>
<script src="js/serverValidation.js"></script>
</head>
<body>
<!--header start-->
<div class="container-fluid p-0">
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
            <p class="login-txt">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book</p>
          </div>
          <!-- <form class="form-horizontal" action="validateUser" method="post" id="login-form" > -->
          <form class="form-horizontal">
            <div class="form-user"><i class="fa fa-user-tie"></i></div>
            <h2 class="login-heading">Login Here</h2>
            <div class="form-group"> <span class="input-icon"><i class="fa fa-envelope"></i></span>
              <input type="text"  id="username" name = "username" class="form-control" placeholder="Login ID" autocomplete="off">
              <span style="color: red">${error}</span>
            </div>
            <div class="form-group"> <span class="input-icon"><i class="fa fa-lock"></i></span>
              <input class="form-control" id="password" name = "password" type="password" placeholder="Password">
            </div>
            <div class=""> 
             <select class="form-select" id="domain" name = "domain" >
              <option value="">--Select--</option>
              <option value="STMUDC02">STMUDC02</option>
              <option value="STMUDC15">STMUDC15</option>
              </select>
            </div>
            <div class="text-center">
              <button type="button" class="btn signin" onClick="validateUser()">Login</button>
              <button type="button" class="btn signin">Reset</button>
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
</body>
</html>
