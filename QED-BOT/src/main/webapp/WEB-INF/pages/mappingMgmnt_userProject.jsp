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
		
		<!-- Auto complete START 25-10-2021 START -->
		<link rel="stylesheet" href="//code.jquery.com/ui/1.13.0/themes/base/jquery-ui.css">
	  	<link rel="stylesheet" href="/resources/demos/style.css">
	  	<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
	  	<script src="https://code.jquery.com/ui/1.13.0/jquery-ui.js"></script>
	  	
	  	<script>
	  	
		  /* $( function() {
		    var availableTags = [
		      "ActionScript",
		      "AppleScript",
		      "Asp",
		      "BASIC",
		      "C",
		      "C++",
		      "Clojure",
		      "COBOL",
		      "ColdFusion",
		      "Erlang",
		      "Fortran",
		      "Groovy",
		      "Haskell",
		      "Java",
		      "JavaScript",
		      "Lisp",
		      "Perl",
		      "PHP",
		      "Python",
		      "Ruby",
		      "Scala",
		      "Scheme"
		    ];
		    $( "#mappedUser" ).autocomplete({
		      source: availableTags
		    });
		  } ); */
		  
		  /* var tags = [ "c++", "java", "php", "coldfusion", "javascript", "asp", "ruby" ];
		  $( "#mappedUser" ).autocomplete({
		    source: function( request, response ) {
		            var matcher = new RegExp( "^" + $.ui.autocomplete.escapeRegex( request.term ), "i" );
		            response( $.grep( tags, function( item ){
		                return matcher.test( item );
		            }) );
		        }
		  }); */
		  
		  /* $( "#mappedUser" ).autocomplete({
		      source: "search.php",
		      minLength: 2,
		      select: function( event, ui ) {
		        log( "Selected: " + ui.item.value + " aka " + ui.item.id );
		      }
		    }); */
		  
		    /* $( "#mappedUser" ).autocomplete({
		        source: function( request, response ) {
		          $.ajax( {
		            url: "getADUserGroupUsers",
		            type : 'POST',
		            dataType: "jsonp",
		            data: {
		              term: request.term
		            },
		            success: function( data ) {
		              response( data );
		            }
		          } );
		        },
		        minLength: 2,
		        select: function( event, ui ) {
		          log( "Selected: " + ui.item.value + " aka " + ui.item.id );
		        }
		      } ); */
  		</script>
	  	<!-- Auto complete START 25-10-2021 END -->
  
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
		  //Auto complete start
		  /* function getUserList(){
			  $.ajax({
				  url :'getADUserGroupUsers',
				  type : 'POST',
				  //data :,
				  datatype :'json',
				  success: function( data ) {
			            response( data );
			          }
			        } );
			      },
			      minLength: 2,
			      select: function( event, ui ) {
			        log( "Selected: Name " + ui.item.value + " ID " + ui.item.id );
			      }
			    } );
			  });
		  } */
		  //Auto complete end
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
		  <h2>User Project Mapping</h2>  
		  <div class="content-area">
		  <!-- START -->
	 <div class="row">
	 
	  <div class="col-md-4 select-delivereable-type-createtable align-self-center">
	      		<% 
	      			List<Object[]> deliverabletype = (List<Object[]>) request.getAttribute("deliverableType");
	     		 %>
	     		 
				<label>Select Deliverable Type : </label>
				<sup class="mandatory">*</sup><div class="select-deliverable-type">
				<select class="form-select width-auto" name="deliverableType" id="deliverableType" onchange="getProjects(this)">
						<option value="" selected="selected" >--Select Deliverable--</option>
						<% for(Object[] dt : deliverabletype){%>
							
							<option value="<%= (Number) dt[0]%>"><%= (String) dt[1]%></option>
							
						<%} %>
				</select>
		 		</div>
		  </div>
		  
		 <div class="col-md-4 align-self-center"  >
			 <label>User Name</label>
			 <sup class="mandatory">*</sup> 
			 	<div class="select-tarrgetString-createRule" > 
					<input class="form-control width-auto" type="text" id="mappedUser" name="mappedUser" />
					<input type="hidden" id="mappedUserId" name="mappedUserId" />
					<!-- Auto Complete START -->
					<script>
					$(function(){
						$("#mappedUser").autocomplete({
							source: function(request,response){
								if(checkNull(request.term))
								$.ajax({
									url:"getADUserGroupUsers",
									type:"POST",
									data:{
										term:request.term
									},
									dataType:"json",
									success: function(data){
										response(data);
									}
								});
							},
							select: function(event, ui){
								event.preventDefault();
								$("#mappedUser").val(ui.item.label);
								$("#mappedUser").attr("data-label",ui.item.label);
								
								$("#mappedUserId").val(ui.item.value);
								$("#mappedUserId").attr("data-value",ui.item.value);
							},
							focus:function(event, ui){
								event.preventDefault();
								$("#mappedUser").val(ui.item.label);
								$("#mappedUserId").val(ui.item.value);
							}
						});
					});
					</script>
					<!-- Auto Complete END -->
				</div>
		 </div>
			
		  	
		 
		  
		  <div class="col-md-4  align-self-center">
          	<input class="btn btn-primary " type = "button" value = "View" onClick="viewMappedProjects()"/>
          	<a href="viewUserProjectMappingPage">
          	<input class="btn btn-primary" type = "button" value = "Cancel" />
          	</a>
		</div>
		  
	  </div>
	  <!-- Added on 14-10-2021 END -->
	  <div class="row text-center my-3">
	  		<div class="mx-auto mt-1" name="message" id="message" style="width:37%;"></div>
	  </div>
	  <div class="row mt-3 table-responsive " name="userProjectMappingDiv" id="userProjectMappingDiv" style="display:none;width:50%; margin:20px auto 0 auto;">
	   <h2>Mapping for User :<span style="padding-left:10px;" id="mappingforuser"></span></h2>
	   <div style="text-align:right; margin-bottom:15px;">
	   	<input class="btn btn-primary" type="button" value="Remove Mapping" name="removeUPMappingChangeBtn" id="removeUPMappingChangeBtn" onclick="removeAllProjectMappingWithUser()" style="width:24%;">
	  	<input class="btn btn-primary" type="button" value="Save Mapping" name="saveUPMappingChangeBtn" id="saveUPMappingChangeBtn" onclick="saveUserProjectMapping()" style="width:21%;">
	  </div>
	  
	  <table class="table align-middle table-bordered table-striped" id="UPMappingTable" name="UPMappingTable"><tbody><tr>
		<th style="width:10%; text-align:center;">Sr No</th><th>Project Name</th><th style="width:25%;">Assign Project</th>																																									
		</tr></tbody></table>
	  
	  
	  
	  </div>
		  <!-- END -->
	  
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
