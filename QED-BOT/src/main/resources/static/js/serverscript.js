/*function ImageLoading(Id) {
	document.getElementById(Id).innerHTML = '<img alt="loading" src="images/loading.gif"/>';
}

function ImageLoadingForAdmin(Id) {
document.getElementById(Id).innerHTML = '<img alt="loading" src="images/loading.gif"/>';
}*/

function checkLogin(username, password) {
	$.ajax({
		type: "POST",
		url: "checkAdminLogin",
		//traditional : true,
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		data: {
			username: username,
			password: password,
		},
		success: function(response) {

			//document.getElementById('valid').innerHTML = response;
			if (response == true) {
				document.getElementById('valid').innerHTML = "";
				window.location.href = "./deliverableDetails";
			} else if (response == false) {
				document.getElementById('valid').innerHTML = "<div class='error-message'>incorrect username or password</div>";
				document.frm.username.focus();
			}

		}
	});

}

//Session Check
function checkDuplicateDeliverableType(shortName, fullName) {
	document.getElementById('errormsg').style.color = "red";
	var mode = 'add';

	$.ajax({
		type: "POST",
		url: "checkDuplicateDeliverableType",
		// traditional : true,
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		data: {
			shortName: shortName,
			fullName: fullName,
			deliverableTypeId: 0,

		},
		success: function(response) {
			var result = response.split("||")[0];
			var duplicateField = response.split("||")[1];

			if (result == "true") {
				document.getElementById('errormsg').innerHTML = "<div class='error-message'>" + duplicateField + " already exists</div>";
			} else if (response == "false") {
				addDeliverableTypeDetails(shortName, fullName);
			} else {
				document.getElementById('errormsg').innerHTML = "<div class='error-message'>" + response + "</div>";
			}

		}
	});
}

//Session check
function isDuplicateDeliverableType(fullName, status, deliverableTypeId) {
	document.getElementById('errormsg').style.color = "red";
	var mode = 'edit';

	$.ajax({
		type: "POST",
		url: "checkDuplicateDeliverableTypeFullName",
		// traditional : true,
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		data: {
			fullName: fullName,
			deliverableTypeId: deliverableTypeId,
		},
		success: function(response) {
			var result = response.split("||")[0];
			var duplicateField = response.split("||")[1];

			if (result == "true") {
				document.getElementById('errormsg').innerHTML = "<div class='error-message'>" + duplicateField + " already exists</div>";
			} else if (response == "false") {
				editDeliverableTypeDetails(fullName, status, deliverableTypeId);
			} else {
				document.getElementById('errormsg').innerHTML = "<div class='error-message'>" + response + "</div>";
			}

		}
	});
}

//Session check
function addDeliverableTypeDetails(shortName, fullName) {
	document.getElementById('errormsg').style.color = "red";
	$.ajax({
		type: "POST",
		url: "addDeliverableTypeDetails",
		// traditional : true,
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		data: {
			shortName: shortName,
			fullName: fullName,
		},
		success: function(response) {

			var response = response;

			if (response == "true") {

				window.location.reload();
			} else if (response == "false") {
				document.getElementById('errormsg').innerHTML = "<div class='error-message'>Deliverable addition failed</div>";
			} else if (response == "invaliddata") {
				document.getElementById('errormsg').innerHTML = "<div class='error-message'>Invalid data found. Deliverable addition failed</div>";
			}

		}
	});

}

//Check Session
function editDeliverableTypeDetails(fullName, status, deliverableTypeId) {
	document.getElementById('errormsg').style.color = "red";
	$.ajax({
		type: "POST",
		url: "editDeliverableType",
		// traditional : true,
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		data: {
			deliverableTypeId: deliverableTypeId,
			fullName: fullName,
			status: status,
		},
		success: function(response) {
			//alert(response)
			var response = response;

			if (response == "true") {
				document.getElementById('errormsg').style.color = "green";
				document.getElementById('errormsg').innerHTML = "<div class='message'>Deliverable type details modified successfully</div>";
			} else if (response == "false") {
				document.getElementById('errormsg').innerHTML = "<div class='error-message'>Deliverable type modification failed</div>";
			} else if (response == "invaliddata") {
				document.getElementById('errormsg').innerHTML = "<div class='error-message'>Invalid data found. Deliverable type modification failed</div>";
			}

		}
	});

}



//Check Session
function checkDuplicateProjectMaster(deliverableTypeId, projectTag, projectName, projectDescription, filesPath) {
	document.getElementById('errormsg').style.color = "red";
	var mode = 'add';

	$.ajax({
		type: "POST",
		url: "checkDuplicateProjectMaster",
		// traditional : true,
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		data: {
			projectMasterId: 0,
			deliverableTypeId: deliverableTypeId,
			projectTag: projectTag,
			projectName: projectName,
			projectDescription: projectDescription,

		},
		success: function(response) {
			var result = response.split("||")[0];
			var duplicateField = response.split("||")[1];

			if (result == "true") {
				document.getElementById('errormsg').innerHTML = "<div class='error-message'>" + duplicateField + " already exists</div>";
			} else if (result == "false") {
				addProjectMaster(deliverableTypeId, projectTag, projectName, projectDescription,filesPath);
			} else {
				document.getElementById('errormsg').innerHTML = "<div class='error-message'>" + response + "</div>";
			}

		}
	});
}

//Check Session
function isDuplicateProjectMaster(projectName, projectDescription, status, projectMasterId, filesPath) {
	document.getElementById('errormsg').style.color = "red";
	var mode = 'add';

	$.ajax({
		type: "POST",
		url: "checkDuplicateProjectMaster",
		// traditional : true,
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		data: {
			projectMasterId: projectMasterId,
			deliverableTypeId: 0,
			projectTag: 0,
			projectName: projectName,
			projectDescription: projectDescription,

		},
		success: function(response) {
			var result = response.split("||")[0];
			var duplicateField = response.split("||")[1];

			if (result == "true") {
				document.getElementById('errormsg').innerHTML = "<div class='error-message'>" + duplicateField + " already exists</div>";
			} else if (response == "false") {
				editProjectMasterDetails(projectName, projectDescription, status, projectMasterId,filesPath);
			} else {
				document.getElementById('errormsg').innerHTML = "<div class='error-message'>" + response + "</div>";
			}

		}
	});
}

//Check Session
function addProjectMaster(deliverableTypeId, projectTag, projectName, projectDescription, filesPath) {
	document.getElementById('errormsg').style.color = "red";
	
	$.ajax({
		type: "POST",
		url: "addProjectMasterDetails",
		// traditional : true,
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		data: {
			deliverableTypeId: deliverableTypeId,
			projectTag: projectTag,
			projectName: projectName,
			projectDescription: projectDescription,
			filesPath: encodeURIComponent(filesPath)
		},		
		success: function(response) {

			var response = response;

			if (response == "true") {
				document.getElementById('errormsg').style.color = "green";
				document.getElementById("frm").reset();
				document.getElementById('errormsg').innerHTML = "<div class='error-message'>Project added successfully</div>";
			} else if (response == "false") {
				document.getElementById('errormsg').innerHTML = "<div class='error-message'>Project addition failed</div>";
			} else if (response == "invaliddata") {
				document.getElementById('errormsg').innerHTML = "<div class='error-message'>Invalid data found. Project addition failed</div>";
			}

		}
	});

}

//Check Session
function editProjectMasterDetails(projectName, projectDescription, status, projectMasterId, filesPath) {
	document.getElementById('errormsg').style.color = "red";

	$.ajax({
		type: "POST",
		url: "editProject",
		// traditional : true,
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		data: {
			projectMasterId: projectMasterId,
			projectName: projectName,
			projectDescription: projectDescription,
			status: status,
			filesPath: encodeURIComponent(filesPath)
		},
		success: function(response) {
			//alert(response)
			var response = response;

			if (response == "true") {
				document.getElementById('errormsg').style.color = "green";
				document.getElementById('errormsg').innerHTML = "<div class='message'>Project master details modified successfully</div>";
			} else if (response == "false") {
				document.getElementById('errormsg').innerHTML = "<div class='error-message'>Project master modification failed</div>";
			} else if (response == "invaliddata") {
				document.getElementById('errormsg').innerHTML = "<div class='error-message'>Invalid data found. Project master modification failed</div>";
			}

		}
	});

}

//Session Check
function checkDuplicateGroupMaster(groupName, groupRole) {
	document.getElementById('errormsg').style.color = "red";
	var mode = 'add';

	$.ajax({
		type: "POST",
		url: "checkDuplicateGroupMaster",
		// traditional : true,
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		data: {
			groupId: 0,
			groupName: groupName,
			groupRole: groupRole,

		},
		success: function(response) {
			var result = response.split("||")[0];
			var duplicateField = response.split("||")[1];

			if (result == "true") {
				document.getElementById('errormsg').innerHTML = "<div class='error-message'>" + duplicateField + " already exists</div>";
			} else if (response == "false") {
				addGroupMaster(groupName, groupRole);
			} else {
				document.getElementById('errormsg').innerHTML = "<div class='error-message'>" + response + "</div>";
			}

		}
	});
}

//Check Session
function isDuplicateGroupMaster(groupName, groupRole, status, groupMasterId) {
	document.getElementById('errormsg').style.color = "red";
	var mode = 'edit';

	$.ajax({
		type: "POST",
		url: "checkDuplicateGroupMaster",
		// traditional : true,
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		data: {

			groupId: groupMasterId,
			groupName: groupName,
			groupRole: groupRole,

		},
		success: function(response) {
			var result = response.split("||")[0];
			var duplicateField = response.split("||")[1];

			if (result == "true") {
				document.getElementById('errormsg').innerHTML = "<div class='error-message'>" + duplicateField + " already exists</div>";
			} else if (response == "false") {
				editGroupDetails(groupName, groupRole, status, groupMasterId);
			} else {
				document.getElementById('errormsg').innerHTML = "<div class='error-message'>" + response + "</div>";
			}

		}
	});
}


//Check Session
function addGroupMaster(groupName, groupRole) {
	document.getElementById('errormsg').style.color = "red";
	$.ajax({
		type: "POST",
		url: "addGroupMasterDetails",
		// traditional : true,
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		data: {
			groupName: groupName,
			groupRole: groupRole,
		},
		success: function(response) {

			var response = response;

			if (response == "true") {
				document.getElementById('errormsg').style.color = "green";
				document.getElementById("frm").reset();
				document.getElementById('errormsg').innerHTML = "<div class='error-message'>Group added successfully</div>";
			} else if (response == "false") {
				document.getElementById('errormsg').innerHTML = "<div class='error-message'>Group addition failed</div>";
			} else if (response == "invaliddata") {
				document.getElementById('errormsg').innerHTML = "<div class='error-message'>Invalid data found. Group addition failed</div>";
			}

		}
	});

}

//Check Session
function editGroupDetails(groupName, groupRole, status, groupMasterId) {
	document.getElementById('errormsg').style.color = "red";
	$.ajax({
		type: "POST",
		url: "editGroup",
		// traditional : true,
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		data: {
			groupName: groupName,
			groupRole: groupRole,
			groupId: groupMasterId,
			status: status,
		},
		success: function(response) {
			//alert(response)
			var response = response;


			if (response == "true") {
				document.getElementById('errormsg').style.color = "green";
				document.getElementById('errormsg').innerHTML = "<div class='message'>Group details modified successfully</div>";
			} else if (response == "false") {
				document.getElementById('errormsg').innerHTML = "<div class='error-message'>Group details modification failed</div>";
			} else if (response == "invaliddata") {
				document.getElementById('errormsg').innerHTML = "<div class='error-message'>Invalid data found. Group details modification failed</div>";
			}

		}
	});

}
