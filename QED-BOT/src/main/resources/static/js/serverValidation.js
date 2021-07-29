/* login validation start*/
function loginUser(username, password, domain){
	
	//alert('loginUser '+username+' '+password+' '+domain);
	
	$.ajax({
		type : "POST",
		url : "/validateUser",
		contentType : "application/x-www-form-urlencoded",
		data : {
			username : username,
			password : password,
			domain : domain
		},
		
		success : function (response){
			//alert('response '+response);
			var role = response;
			if(role == "user"){
				
				window.location.href = "userDashboard"
				
			}else if(role == "functionaladmin"){
				
				window.location.href = "functionalAdminHome"
				
			}else if(role == "superadmin"){
				
				window.location.href = "superAdminHome"
				
			}
			else if (role == "management"){
				
				window.location.href = "managementDashboard"
				
			}else{
				alert("Invalid User");
			}
			
		}
		
		
	});
}

/* login validation END*/

/* Table Creation START */

/* Table Creation END */