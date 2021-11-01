
/*function ImageLoading(Id) {
	document.getElementById(Id).innerHTML = '<img alt="loading" src="images/loading.gif"/>';
}

function ImageLoadingForAdmin(Id) {
	document.getElementById(Id).innerHTML = '<img alt="loading" src="images/loading.gif"/>';
}*/
/* Validate Session Start*/
function checkSession(){
	
	var session = '';
	
	$.ajax({
		url : 'checkSession',
		type : 'POST',
		datatype : 'text',
		contentType : 'text/plain charset=UTF-8',
		async : false,
		success : function(response){
			//alert('session : '+response);
			if(response.includes('Invalid')){
				window.location.href="logout";
			}else{
				session = 'valid';
			}
			
		},error : function(response){
			$('#message').val(response);
			$('#message').addClass('alert alert-danger');
			
		}
	});
	//alert('session : '+session);
	return session;
}
/* Validate Session End*/

function checkenter(e) {
	var charCode = (navigator.appName == "Netscape") ? e.which : e.keyCode;
	if (charCode == 13) {
		login_validation();
	}
	return true;
}
function login_validation() {
	var username = document.getElementById('username').value.trim();
	var password = document.getElementById('password').value.trim();
	if (username == "") {
		document.getElementById("valid").innerHTML = "<div class='error-message'>Please enter username</div>";
		document.frm.username.focus();
	}
	else if (password == "") {
		document.getElementById("valid").innerHTML = "<div class='error-message'>Please enter password</div>";
		document.frm.password.focus();
	}
	else {
		document.getElementById("valid").innerHTML = "";
		checkLogin(username, password);
	}
}

function addDeliverableData() {

	if(checkSession().includes('valid')){
		document.getElementById('errormsg').style.color = "red";
		var shortName = document.getElementById("shortName").value.trim();
		var fullName = document.getElementById("fullName").value.trim();
	
		if (shortName == "") {
			document.getElementById("errormsg").innerHTML = "<div class='error-message'>Please enter short name</div>";
			document.frm.shortName.focus();
		} else if (fullName == "") {
			document.getElementById("errormsg").innerHTML = "<div class='error-message'>Please enter long name</div>";
			document.frm.fullName.focus();
		}
		else {
			document.getElementById("errormsg").innerHTML = "";
			checkDuplicateDeliverableType(shortName, fullName);
		}
	}
	
}

function editDeliverableData(deliverableTypeId) {

	if(checkSession().includes('valid')){
		document.getElementById('errormsg').style.color = "red";
		var fullName = document.getElementById("fullName").value.trim();
		var status = document.getElementById("status").value;
		if (fullName == "") {
			document.getElementById("errormsg").innerHTML = "<div class='error-message'>Please enter long name</div>";
			document.frm.fullName.focus();
		}
		else {
			document.getElementById("errormsg").innerHTML = "";
			isDuplicateDeliverableType(fullName, status, deliverableTypeId);
	
		}
	}
	
}

function editProjectData(projectMasterId) {
	if(checkSession().includes('valid')){
		document.getElementById('errormsg').style.color = "red";
		document.getElementById("errormsg").innerHTML = "";
		var projectName = document.getElementById("projectName").value.trim();
		var projectDescription = document.getElementById("projectDescription").value.trim();
		var status = document.getElementById("status").value;
		var filesPath =$('#filesPath').val();
	
		 if (projectName == "") {
			document.getElementById("errormsg").innerHTML = "<div class='error-message'>Please enter project name</div>";
			document.frm.projectName.focus();
		} else if (projectDescription == "") {
			document.getElementById("errormsg").innerHTML = "<div class='error-message'>Please enter project description</div>";
			document.frm.projectDescription.focus();
		}else if(checkNull(filesPath).length == 0){
			$('#errormsg').html("<div class='error-message'>Please enter files path</div>");
			$('#filesPath').focus();
		}
		else {
			document.getElementById("errormsg").innerHTML = "";
			isDuplicateProjectMaster(projectName, projectDescription, status, projectMasterId,filesPath);
	
		}
	}
	
}

function addProjectData() {

	if(checkSession().includes('valid')){
		document.getElementById('errormsg').style.color = "red";
		document.getElementById("errormsg").innerHTML = "";
		var deliverableTypeId = document.getElementById("deliverableTypeId").value;
		var projectTag = document.getElementById("projectTag").value.trim();
		var projectName = document.getElementById("projectName").value.trim();
		var projectDescription = document.getElementById("projectDescription").value.trim();
		var filesPath = $('#filesPath').val();// Added by AMOL S. on 26-10-2021 
		//alert(filesPath)
		if (deliverableTypeId == "select deliverable type") {
			document.getElementById("errormsg").innerHTML = "<div class='error-message'>Please select deliverable type</div>";
			document.frm.deliverableTypeId.focus();
		} else if (projectTag == "") {
			document.getElementById("errormsg").innerHTML = "<div class='error-message'>Please enter project tag</div>";
			document.frm.projectTag.focus();
		} else if (projectName == "") {
			document.getElementById("errormsg").innerHTML = "<div class='error-message'>Please enter project name</div>";
			document.frm.projectName.focus();
		} else if (projectDescription == "") {
			document.getElementById("errormsg").innerHTML = "<div class='error-message'>Please enter project description</div>";
			document.frm.projectDescription.focus();
		}else if(checkNull(filesPath).length == 0){
			$('#errormsg').html("<div class='error-message'>Please enter files path</div>");
			$('#filesPath').focus();
		}
		else {
			document.getElementById("errormsg").innerHTML = "";
			checkDuplicateProjectMaster(deliverableTypeId, projectTag, projectName, projectDescription,filesPath);
	
		}
	}
	

}

function addGroup() {

	if(checkSession().includes('valid')){
		document.getElementById('errormsg').style.color = "red";
		document.getElementById("errormsg").innerHTML = "";
		var groupName = document.getElementById("groupName").value.trim();
		var groupRole = document.getElementById("groupRole").value.trim();
	
		if (groupName == "") {
			document.getElementById("errormsg").innerHTML = "<div class='error-message'>Please enter group name</div>";
			document.frm.groupName.focus();
		} else if (groupRole == "select role") {
			document.getElementById("errormsg").innerHTML = "<div class='error-message'>Please select role</div>";
			document.frm.groupRole.focus();
		} else {
			document.getElementById("errormsg").innerHTML = "";
			checkDuplicateGroupMaster(groupName, groupRole);
	
		}
	}
	

}

function editGroupData(groupMasterId) {

	if(checkSession().includes('valid')){
		document.getElementById('errormsg').style.color = "red";
		var groupName = document.getElementById("groupName").value.trim();
		var groupRole = document.getElementById("groupRole").value.trim();
		var status = document.getElementById("status").value;
	
		if (groupName == "") {
			document.getElementById("errormsg").innerHTML = "<div class='error-message'>Please enter group name</div>";
			document.frm.groupName.focus();
		} else if (groupRole == "select role") {
			document.getElementById("errormsg").innerHTML = "<div class='error-message'>Please select role</div>";
			document.frm.groupRole.focus();
		}
		else {
			document.getElementById("errormsg").innerHTML = "";
			isDuplicateGroupMaster(groupName, groupRole, status, groupMasterId);
	
		}
	}
	
}


function editADServiceData(adServiceId) {

	if(checkSession().includes('valid')){
		document.getElementById('errormsg').style.color = "red";
		var userId = document.getElementById("userId").value.trim();
		var password = document.getElementById("password").value.trim();
		var ldapUrl = document.getElementById("ldapUrl").value.trim();
		var status = document.getElementById("status").value;
	
	
		if (userId == "") {
			document.getElementById("errormsg").innerHTML = "<div class='error-message'>Please enter user id</div>";
			document.frm.userId.focus();
		} else if (password == "") {
			document.getElementById("errormsg").innerHTML = "<div class='error-message'>Please enter password</div>";
			document.frm.password.focus();
		} else if (ldapUrl == "") {
			document.getElementById("errormsg").innerHTML = "<div class='error-message'>Please enter LDAP url</div>";
			document.frm.ldapUrl.focus();
		}
		else {
			document.getElementById("errormsg").innerHTML = "";
	
			$.ajax({
				type: "POST",
				url: "editADServiceDetails",
				// traditional : true,
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				data: {
					userId: userId,
					password: password,
					ldapUrl: ldapUrl,
					status: status,
					adServiceId: adServiceId,
				},
				success: function(response) {
	
					var response = response;
	
					if (response == "true") {
						document.getElementById('errormsg').style.color = "green";
						document.getElementById('errormsg').innerHTML = "<div class='message'>AD Service details modified successfully</div>";
					} else if (response == "false") {
						document.getElementById('errormsg').innerHTML = "<div class='error-message'>AD Service details modification failed</div>";
					} else if (response == "invaliddata") {
						document.getElementById('errormsg').innerHTML = "<div class='error-message'>Invalid data found. AD Service details modification failed</div>";
					}
	
				}
			});
	
		}

	}
	
}

function changePassword(adminSrNo) {
	
	if(checkSession().includes('valid')){
		document.getElementById('errormsg').style.color = "red";
		var oldPass = document.getElementById("oldPass").value.trim();
		var oldPassword = document.getElementById("oldPassword").value.trim();
	
		var newPassword = document.getElementById("newPassword").value.trim();
		var cnfmPassword = document.getElementById("cnfmPassword").value.trim();
	
		var lowerCaseLetters = /[a-z]/g;
		var upperCaseLetters = /[A-Z]/g;
		var numbers = /[0-9]/g;
		var specialCharacters = /[\!\@\#\$\%\^\&\*\)\(\+\=\.\<\>\{\}\[\]\:\;\'\"\|\~\`\_\-]/g;
		if (oldPassword == "") {
			document.getElementById("errormsg").innerHTML = "<div class='error-message'>Please enter existing password</div>";
			document.frm.oldPassword.focus();
		} else if (oldPass != oldPassword) {
			document.getElementById("errormsg").innerHTML = "<div class='error-message'>Please enter correct existing password</div>";
			document.frm.oldPassword.focus();
		} else if (newPassword == "") {
			document.getElementById("errormsg").innerHTML = "<div class='error-message'>Please enter new password</div>";
			document.frm.newPassword.focus();
		} else if (!newPassword.match(lowerCaseLetters)) {
			document.getElementById("errormsg").innerHTML = "<div class='error-message'>Password must contain at least one lowercase letter</div>";
			document.frm.newPassword.focus();
		} else if (!newPassword.match(upperCaseLetters)) {
			document.getElementById("errormsg").innerHTML = "<div class='error-message'>Password must contain at least one uppercase letter</div>";
			document.frm.newPassword.focus();
		} else if (!newPassword.match(numbers)) {
			document.getElementById("errormsg").innerHTML = "<div class='error-message'>Password must contain at least one number</div>";
			document.frm.newPassword.focus();
		} else if (!newPassword.match(specialCharacters)) {
			document.getElementById("errormsg").innerHTML = "<div class='error-message'>Password must contain at least one special character</div>";
			document.frm.newPassword.focus();
		} else if (newPassword.length < 8 || newPassword.length > 15) {
			document.getElementById("errormsg").innerHTML = "<div class='error-message'>Password length must be of minimum 8 characters and maximum of 15 characters</div>";
			document.frm.newPassword.focus();
		} else if (cnfmPassword == "") {
			document.getElementById("errormsg").innerHTML = "<div class='error-message'>Please enter confirm password</div>";
			document.frm.cnfmPassword.focus();
		} else if (newPassword != cnfmPassword) {
			document.getElementById("errormsg").innerHTML = "<div class='error-message'>password mismatch</div>";
			document.frm.cnfmPassword.focus();
		}
		else {
	
			document.getElementById("errormsg").innerHTML = "";
	
			$.ajax({
				type: "POST",
				url: "editAdminLoginDetails",
				// traditional : true,
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				data: {
					password: cnfmPassword,
					adminSrNo: adminSrNo,
				},
				success: function(response) {
					document.frm.reset();
					var response = response;
	
					if (response == "true") {
						document.getElementById("oldPass").value = cnfmPassword;
						document.getElementById('errormsg').style.color = "green";
						document.getElementById('errormsg').innerHTML = "<div class='message'>password modified successfully</div>";
					} else if (response == "false") {
						document.getElementById('errormsg').innerHTML = "<div class='error-message'>password modification failed</div>";
					} else if (response == "invaliddata") {
						document.getElementById('errormsg').innerHTML = "<div class='error-message'>Invalid data found. password modification failed</div>";
					}
	
				}
			});
	
		}
	}
	

}

//Added by AMOL S. on 26-10-2021 START
function checkNull(value) {
	//alert(value);
    if (typeof value !== 'string') {
        return "";
    }
    
    if (value === undefined || value === null || value == 'null' ) {
		//alert('Return');
        return "";
    }
    
    return value.trim();
}
//Added by AMOL S. on 26-10-2021 END