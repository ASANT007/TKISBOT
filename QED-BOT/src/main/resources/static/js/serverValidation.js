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
				alert("Invalid User Id / Password ");
			}
			
		}
		
		
	});
}

/* login validation END*/

/* Table Creation START */

/* Table Creation END */

/* Table Modification START*/

function getTableStructure(tableName){
	
	alert('getTableStructure');
	
	$.ajax({
        url: "/getTableStructure",
        type: "POST",
        data: {
	
            tableName : encodeURIComponent(tableName)
            
        },
        dataType: "json",
		//traditional : true,
		contentType : "application/x-www-form-urlencoded",
        success: function(response) 
		{
			//alert(response);
			$('#currentTableName').val(tableName);
			showAllCoumns(response);	
			//showTableData();
			//$("#result").append("</table></div>");	
        }
    });
}

function getnewlyAddedColumn(tableName,columnName){
	
	//alert(tableName);
	
	$.ajax({
        url: "/alterTable",
        type: "POST",
        data: {
	
            tableName : encodeURIComponent(tableName),
			columnName : encodeURIComponent(columnName)
            
        },
        dataType: "json",		
		contentType : "application/x-www-form-urlencoded",
        success: function(response) 
		{
			//alert(response);
			
			$("#colAddResult").append("<div class='py-3 text-center'>Column <span style='color:green'>"+ columnName +" </span> Added Successfully</div>");
			showAllCoumns(response);
			//setTimeout(clearColAddResult, 2000);	
				
        }
    });
}

function showAllCoumns(response){
	
	
	$("#result").empty();	
	//$("#result").append("<div style='width:50%; margin:0 auto;'><table class='table table-responsive table-bordered table-striped'><tr><th style='width:15%; text-align:center;'>Sr No</th><th>Field Name</th></tr>");
	var srNo = 0;
	var trHTML = "";
	
	trHTML = trHTML+"<div style='width:50%; margin:20px auto 0 auto;'>";
	trHTML = trHTML+"<div class='row'><div class='col-md-8 mb-3'><input class ='form-control' type ='text' id ='newColumn' name = 'newColumn' /></div>";			
	trHTML = trHTML+"<div class='col-md-4 mb-3' style='text-align:right'><input id ='addColumn' name = 'addColumn' class='btn btn-primary mx-2' type ='button' value ='Add Column' onClick='window.validateNewColumn()'/></div></div>";				
	trHTML = trHTML+"<table id='currentTable' name='currentTable' class='table table-responsive table-bordered table-striped'><tr><th style='width:15%; text-align:center;'>Sr No</th><th>Field Name</th></tr>";
	
	for (var i = 0; i < response.length; i++) 
	{	
		srNo++;		
		trHTML += '<tr><td>' + srNo + '</td><td>' + response[i] + '</td></tr>';
		
	
	}
	trHTML = trHTML +"</table></div>";
	$("#result").append(trHTML);
	
	
}
/* Table Modification END*/