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
function getProjectsServerCall(deliverableTypeId){
	
	$.ajax({
        url: "/getProjectsForDeliverableType",
        type: "POST",
        data: {
            deliverableTypeId : encodeURIComponent(deliverableTypeId)            
        },
        dataType: "json",
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		
        success: function(response) {        	 
        	 //alert(response);
        	$("#projectName").empty();
			if (response.length == 0) 
			{
				$("#projectName").attr("autocomplete", "OFF");				
				$("#projectName").append('<option value="" selected="selected" >--Select Project--</option>');
			} else 
			{
				$("#projectName").append('<option value="" selected="selected" >--Select Project--</option>');
				
				for (const property in response) {
				  console.log(`${property}: ${response[property]}`);
					var dt = response[property];
					let array = dt.toString();
				  	var values = array.split(',');					
					$("#projectName").append("<option value='" + values[0] + "'>" +values[1] + "</option>");
				}
			}
        },
		error: function(response){
			
		}
    });
	
}
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

/* Rule creaation Start */

	
/*function getTablesForSelectedProject(obj){
	var projectId = obj.value;
	//alert(projectId);
	//add validations
	//Move belowcode in serverValidation.js
	$.ajax({
        url: "/getAllTablesByProjectId",
        type: "POST",
        data: {
            projectId : projectId            
        },
        dataType: "json",
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
        success: function(response) {
        	 //response(data);
        	 //alert('success' +response);
        	 
        	$("#tableName").empty();
			if (response.length == 0) {
				$("#tableName").attr("autocomplete", "OFF");				
				$("#tableName").append('<option value="" selected="selected" >--Select Table--</option>');
			} else {
				var pattern = '_master_';
				var pat = '_deliverable_';				
				
				$("#tableName").append('<option value="" selected="selected" >--Select Table--</option>');
				
				for (var i = 0; i < response.length; i++) 
				{
					var displayTableName = '';
					var tbleName = response[i];
					
					if(tbleName.includes(pattern)){
						
						displayTableName = tbleName.substr(tbleName.indexOf(pattern)+8,tbleName.lenght);
						
					}else{
						
						displayTableName = tbleName.substr(tbleName.indexOf(pat)+13,tbleName.lenght);
					}
					
					$("#tableName").append("<option value='" + response[i] + "'>" + displayTableName + "</option>");

				}
				
			}
        },
		error: function(response) {
			//alert('error' +response);
			$('#errorDiv').html(response);
		}
    });

}*/

function getTablesForSelectedProject(obj){
	var projectId = obj.value;
	
	$.ajax({
        url: "/getAllTablesByProjectId",
        type: "POST",
        data: {
            projectId : projectId            
        },
        dataType: "json",
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
        success: function(response) {
        	 //response(data);
        	 //alert('success' +response);
        	 
        	$("#tableName").empty();
			if (response.length == 0) {
				
			} else {
				/*var pattern = '_master_';
				var pat = '_deliverable_';				
				
				$("#tableName").append('<option value="" selected="selected" >--Select Table--</option>');
				*/
				/*for (var i = 0; i < response.length; i++) 
				{
					var displayTableName = '';
					var tbleName = response[i];
					
					if(tbleName.includes(pattern)){
						
						displayTableName = tbleName.substr(tbleName.indexOf(pattern)+8,tbleName.lenght);
						
					}else{
						
						displayTableName = tbleName.substr(tbleName.indexOf(pat)+13,tbleName.lenght);
					}
					
					$("#tableName").append("<option value='" + response[i] + "'>" + displayTableName + "</option>");

				}*/
				var pattern = '_master_';
				var pat = '_deliverable_';				
				
				$("#tableName").append('<option value="" selected="selected" >--Select Table--</option>');
				
				response.forEach(obj => {
					var repositoryId = '';
					var tableName ='';
					var displayTableName = '';
					
			        Object.entries(obj).forEach(([key, value]) => {
			            console.log(`${key} ${value}`);
						if(`${key}` == 'repositoryId'){
							ruleId = `${value}`;
						}else if(`${key}` == 'tableName'){
							tableName = `${value}`;
							//Do it later 19-08-2021
							/*if(tableName.includes(pattern)){
						
								displayTableName = tableName.substr(tableName.indexOf(pattern)+8,tableName.lenght);
								
							}else{
								
								displayTableName = tableName.substr(tableName.indexOf(pat)+13,tableName.lenght);
							}*/
						}
						
			        });
		        $("#tableName").append("<option value='" +ruleId + "'>" + tableName + "</option>");
		    	});
			}
        },
		error: function(response) {
			//alert('error' +response);
			$('#errorDiv').html(response);
		}
    });

}
function getTargetFields(tableName,forModifyRule){
	//alert(obj);
	//var tableName = $('#tableName').val();
	
	if(forModifyRule == 'forModifyRule'){		
		
		tableName = tableName;
		
	}else{
		tableName = $('#tableName option:selected').text();
	}
	//var tableName = $('#tableName option:selected').text();
	//alert('getTargetFields'+tableName);
	$.ajax({
		        url: "/getTableStructure",
		        type: "POST",
				async: false,
		        data: {
			
		            tableName : encodeURIComponent(tableName)
		            
		        },
		        dataType: "json",		
				contentType : "application/x-www-form-urlencoded",
		        success: function(response) 
				{					
						$("#targetFieldName").empty();
						$("#source").empty();
						if (response.length == 0) {
							$("#targetFieldName").attr("autocomplete", "OFF");				
							$("#targetFieldName").append('<option value="" selected="selected" >--Select Table--</option>');
							$("#source").attr("autocomplete", "OFF");				
							$("#source").append('<option value="" selected="selected" >--Select Table--</option>');
						} 
						else
						{				
					
							$("#targetFieldName").append('<option value="">--Select Field--</option>');
							$("#source").append('<option value="">--Select Source--</option>');
							for (var i = 1; i < response.length; i++) 
							{								
								
								$("#targetFieldName").append("<option value='" + response[i] + "'>" + response[i] + "</option>");
								$("#source").append("<option value='" + response[i] + "'>" + response[i] + "</option>");
			
							}
					
						}
		        }
		    });
}


function saveRuleServerCall(projectId, repositoryId, shortDesc, ruleType){	
	
	/*alert('saveRuleServerCal : projectId'+ projectId);
	alert('saveRuleServerCal : shortDesc'+ shortDesc);*/
	
	$.ajax({
        url: "/saveRule",
        type: "POST",
        data: {
	
            projectId : encodeURIComponent(projectId),
			repositoryId : encodeURIComponent(repositoryId),
			shortDesc : encodeURIComponent(shortDesc),	
			ruleType : encodeURIComponent(ruleType)				
            
        },
        dataType: "text",		
		contentType : "application/x-www-form-urlencoded",
        success: function(response) 
		{	
			
			if(checkNull(response) == 'sucess'){
				/*$('#result').html('Rule Saved Successfully');
				$('#result').css('color','green');*/
				$('#successDiv').show();
				$('#successDiv').html('Rule Saved Successfully');	
			}else{
								
				$('#errorDiv').show();
				$('#errorDiv').html(response);
			}			
			
			
        },
		error : function(response){
			$('#errorDiv').show();
			$('#errorDiv').html(response);
			
		}
    });
}

function viewRulesServerCall(projectId){
	
	//alert('viewRulesServerCall');
	$('#errorDiv').html();
	$('#successDiv').html('');
	$('#errorDiv').hide();
	$('#successDiv').hide();
	$('#result').html('');
	$('#saveRuleDiv').hide();
	$('#ruleListDiv').empty();
	var tableAttr = "<table class='table table-responsive table-bordered table-striped'><tr><th style='width:5%; text-align:center;'>Sr No</th><th style='width:7%'>Rule Type</th><th>Rule Description</th><th style='width:6%'>Status</th><th style='width:19%'>Actions</th></tr>";
	//var tableRow = "";
	$.ajax({
        url: "/getRules",
        type: "POST",
        data: {
	
            projectId : encodeURIComponent(projectId)					
            
        },
        dataType: "json",		
		contentType : "application/x-www-form-urlencoded",
        success: function(response) 
		{	
			
			var srno = 0;
			response.forEach(obj => {
				var ruleId = '';
				var repositoryId = '';
				var ruleType = '';
				var statusAction ='';
				var statusVal = '';
				var ruledesc = ''; 
				
				var btnClass = '';
				srno ++;
		        Object.entries(obj).forEach(([key, value]) => {
		            //console.log(`${key} ${value}`);
					
					
					if(`${key}` == 'ruleId'){
						ruleId = `${value}`;
					}else if(`${key}` == 'repositoryId'){
						repositoryId = `${value}`;
					}else if(`${key}` == 'ruleType'){
						ruleType = `${value}`;
					}else if(`${key}` == 'status'){
						
						statusVal = `${value}`;
						//alert(statusVal);
						if( checkNull(`${value}`) == 'active'){
							
							statusAction = 'Make Inactive';
							btnClass = 'btn btn-danger mx-1';
							
						}else{
							statusVal = `${value}`;
							statusAction = 'Make Active';
							btnClass = 'mdfRuleBtnClass btn btn-success mx-1';
						}
						
					}else if(`${key}` == 'ruleDesc'){
						ruledesc = `${value}`;
					}
					
		        });

				tableAttr +="<tr><td align='center'>"+srno+"</td><td id='ruleType_"+ruleId+"'>"+ruleType+"<input type='hidden' value = '"+repositoryId+"' id='repositoryId_"+ruleId+"'></td><td id='rule_description_" + ruleId + "'>"+ruledesc+"</td><td>"+statusVal+"</td><td><input class='btn btn-primary mx-1' type='button' value='Modify' onclick='updateRuleDesc("+ruleId+")'/><input id='statusActionBtn_" + ruleId + "' class='"+btnClass+" 'type='button' value='"+statusAction+"' onclick='updateRuleStatus("+ruleId+",\""+statusVal+"\")'/></td></tr>";
		        
		    });
			
			if(srno == 0){
				tableAttr= tableAttr +"<tr><td colspan='5'>No Data Found</td></tr>";
			}
			
			
			tableAttr = tableAttr +"</table>";
			
			$('#ruleListDiv').show();
			$('#ruleListDiv').append(tableAttr);			
			
			
        },
		error : function(response){
			$('#errorDiv').show();
			$('#errorDiv').html(response);
			
		}
    });
}


function updateRuleDesc(ruleId){
	
	$('#modifyRuleBtn').show();
	
	$('#errorDiv').html();
	$('#successDiv').html('');
	$('#errorDiv').hide();
	$('#successDiv').hide();
	$('#result').html('');
	$('#saveRuleDiv').hide();
	
	$('#ruleId').empty();
	$('#ruleId').val(ruleId);
	//
	var repoId = 'repositoryId_'+ruleId;
	/*alert(repoId);
	alert($('#'+repoId).val());*/
	
	var repositoryId = $('#'+repoId).val();
	
	var actionId = 'ruleType_'+ruleId;
	var action = $('#'+actionId).text();
	
	$('#action').empty();
	$('#action').val(action);
	
	var descId = 'rule_description_'+ruleId;
	var descText = $('#'+descId).text();
	
	var tableName = getTableNameFromRepositoryId(repositoryId);
	$('#tableName').empty();	
	$('#tableName').val(tableName);//Hidden field
	
	//alert('tableName '+tableName);
	
	getTargetFields(tableName,'forModifyRule');	
	
	let from = descText.indexOf('[');
	let up = descText.indexOf(']');

	var targetFeild =  descText.substring(from+1,up);
	
	$('#targetFieldNameHidden').empty(); // Used to get Value 
	$('#targetFieldNameHidden').val(targetFeild);
	
	//setDefaultValueToField(action,descText);
	showHideActionPanelForModifyRules(action, descText);
		
}

function getTableNameFromRepositoryId(repositoryId){
	//alert('repositoryId'+repositoryId);
	var tableName = '';
	
	$.ajax({
		async: false,
        url: "/getTableNameFromRepositoryId",
        type: "POST",
        data: {
	
            repositoryId : encodeURIComponent(repositoryId)					
            
        },
        dataType: "text",		
		contentType : "application/x-www-form-urlencoded",
        success: function(response) 
		{		
			//alert('response'+response);	
			tableName = response;
        },
		error : function(response){
			$('#errorDiv').show();
			$('#errorDiv').html(response);
			
		}
    });
	
	//alert('getTableNameFromRepositoryId '+tableName);
	return tableName
	
}

/*
function setDefaultValueToField(action,descText){

	var targetFieldName = '', targetString = '', replaceBy = '', source = '', from = '', to = '', operator = '', value = '';

	const filedArray  = descText.substring(str.lastIndexOf("[")+1,str.length-1).split(',');	
	
	
	
	
	if(action == 'replace'){
		
		targetFieldName = filedArray[0];
		targetString = filedArray[1];
		replaceBy = filedArray[2];
	}
	else if(action == 'concatenate'){		
		
		
		
	}
	else if(action == 'mid'){
		
		source = filedArray[0];
		from = filedArray[1];
		to = filedArray[2];
	}
	else if(action == 'left' || action == 'right'){
		
		source = filedArray[0];
		from = filedArray[1];
		
	}
	else if(action == 'delete'){
		
		const array = descText.substring(str.lastIndexOf("[")+1,str.length-1).split(' ');
		source = array[1];
		operator = array[2].replacAll("'",'');
		
	}

}
*/

function showHideActionPanelForModifyRules (action,descText){
	
	var targetFieldName = '', targetString = '', replaceBy = '', source = '', from = '', to = '', operator = '', value = '';

	const filedArray  = descText.substring(descText.lastIndexOf("[")+1,descText.length-1).split(',');	
	
	
	$('ruleTypeDiv').show();
	$('#RuleType').empty();
	$('#RuleType').val(action);
	
	if(action == 'replace'){	
		
		//alert(filedArray[0].replaceAll("]",""));
		var tar = filedArray[0].replaceAll("]","");
		//$('#targetFieldName  option[value="+tar+"]').prop("selected", true);
		//$(`#targetFieldName option[value='${tar}']`).prop('selected', true);
		//$('#targetFieldName select').val(filedArray[0].replaceAll("]",""));
		//$('#targetFieldName').val(filedArray[0].replaceAll("]",""));
		//$('targetFieldName option[value= "+tar+"]').attr("selected",true);
		//$('#targetFieldName').val(filedArray[0].replaceAll("]",""));
			
		$('#targetFieldNameDiv').show(); $('#targetStringDiv').show();	$('#replaceByDiv').show();
		$('#operatorDiv').hide();	$('#sourceDiv').hide();		$('#fromDiv').hide();		$('#toDiv').hide();		$('#valueDiv').hide();
		//alert('Selected Val'+tar);
		$("#targetFieldName > [value=" + tar + "]").attr("selected", "true");
		$('#targetString').val(filedArray[1].replaceAll("'",""));
		$('#replaceBy').val(filedArray[2]); 
		 
	}else if(action == 'concatenate'){
		alert('Concatenate Not Done Yet');
	}
	else if(action == 'mid'){
		var source = filedArray[0].replaceAll("]","");
		//alert('Source Val'+source);
		$("#source option[value= "+source+"]").attr("selected",true);
		//$("#source > [value=" + source + "]").attr("selected", "true");
		$('#from').val(filedArray[1].replaceAll("'",""));
		$('#to').val(filedArray[2]);
		
		$('#targetFieldNameDiv').hide(); $('#targetStringDiv').hide();	$('#replaceByDiv').hide();
		$('#operatorDiv').hide();	$('#sourceDiv').show();		$('#fromDiv').show();		$('#toDiv').show();  $('#valueDiv').hide();
		
	}else if(action == 'left' || action == 'right'){
		
		var source = filedArray[0].replaceAll("]","");
		
		$('#source').val(source);
		$('#from').val(filedArray[1]);
		
		$('#targetFieldNameDiv').hide(); $('#targetStringDiv').hide();	$('#replaceByDiv').hide();
		$('#operatorDiv').hide();	$('#sourceDiv').show();		$('#fromDiv').show();		$('#toDiv').hide();  $('#valueDiv').hide();
		 
	}else if(action == 'delete'){	
			
		const array = descText.substring(descText.lastIndexOf("[")+1,descText.length-1).split(' ');
		 
		$('#operator').val(array[1]);
		
		$('#value').val(array[2].replaceAll("'",""));
		
		$('#targetFieldNameDiv').hide(); $('#targetStringDiv').hide();	$('#replaceByDiv').hide();
		$('#operatorDiv').show();	$('#sourceDiv').hide();		$('#fromDiv').hide();		$('#toDiv').hide();		$('#valueDiv').show(); 
	}
}

//Validate empty fileds and show generated SQL 
function createSQLModifyRule(){
	
	// Call Create rule method
	
	$('#errorDiv').html();
	$('#successDiv').html('');
	$('#errorDiv').hide();
	$('#successDiv').hide();
	$('#result').html('');
	
	var action = $('#action').val();
	var tableName = $('#tableName').val();
	var targetFieldName ='';
	var shortDesc = '';
	
	if(action =='replace'){
		
		targetFieldName = $('#targetFieldName').val();
		
	}else{
		
		targetFieldName = $('#targetFieldNameHidden').val();
	}
	
	if(checkNull(action).length == 0){
		
		$('#errorDiv').show();
		$('#errorDiv').html('Please Select Action');
		
	}else if(checkNull(tableName).length == 0){
		
		$('#errorDiv').show();
		$('#errorDiv').html('Please Select Table');
		
	}
	else if(action == 'replace' && checkNull(targetFieldName).length == 0){
		
		$('#errorDiv').show();
		$('#errorDiv').html('Please Select Field');
		
	}	
	else if( action =='replace'){
		
		var replaceBy = $('#replaceBy').val();		
		var targetString = $('#targetString').val();
		
		if(checkNull(targetString).length == 0){
			
			$('#errorDiv').show();
			$('#errorDiv').html('Please Enter Target String');
			
		}
		else if(checkNull(replaceBy).length == 0){	
			
			$('#errorDiv').show();	
			$('#errorDiv').html('Please Enter Replace by');
			
		}else{
			
			$('#errorDiv').html('');$('#errorDiv').hide();			
			targetString = "'"+targetString+"'";
			shortDesc = 'update '+tableName+' set ['+targetFieldName+'] = Replace(['+targetFieldName+'],'+targetString+','+replaceBy+')';
			//update table tablename set [f1] = Replace([f1],'TERELAC','');
			$('#saveRuleDiv').show();
			$('#result').html(shortDesc);
			
		}
		
		
		
	}else if( action =='concatenate'){
		
		$('#result').html('');
		
		
	}else if( action =='mid'){
		
		var source = $('#source').val();
		var from = $('#from').val();
		var to = $('#to').val();
		
		if(checkNull(source).length == 0){
			
			$('#errorDiv').show();
			$('#errorDiv').html('Please Select Source Field');
			
		}else if(checkNull(from).length == 0){
			
			$('#errorDiv').show();
			$('#errorDiv').html('Please Enter Starting Position value');
			
		} else if(checkNull(to).length == 0){	
			
			$('#errorDiv').show();
			$('#errorDiv').html('Please Enter Up To value');		
			
		}else{
			
			$('#errorDiv').html('');
			$('#errorDiv').hide();
			shortDesc = 'update '+tableName+' set ['+targetFieldName+'] = Mid(['+source+'],'+from+','+to+')';
			
			$('#saveRuleDiv').show();			
			$('#result').html(shortDesc);
			
		}
		
		
		
	}else if( action =='left' || action =='right' ){
		
		var source = $('#source').val();
		var from = $('#from').val();
		
		
		if(checkNull(source).length == 0){
			
			$('#errorDiv').show();
			$('#errorDiv').html('Please Select Source Field');
			
		}else if(checkNull(from).length == 0){
			
			$('#errorDiv').show();
			$('#errorDiv').html('Please Enter Starting Position value');
			
		}else{
			
			$('#errorDiv').html('');
			$('#errorDiv').hide();
		
			if(action =='Left'){
				
				shortDesc = 'update '+tableName+' set ['+targetFieldName+'] = Left(['+source+'],'+from+')';;
				
			
			}else{
					
				shortDesc = 'update '+tableName+' set ['+targetFieldName+'] = Right(['+source+'],'+from+')';
			}
			
			$('#saveRuleDiv').show();			
			$('#result').html(shortDesc);
			
		}
		
		
		
	}else if( action =='delete'){
		//alert(action);
		var operator = $('#operator').val();
		var value = $('#value').val();
		
		if(checkNull(operator).length == 0){
			
			$('#errorDiv').show();
			$('#errorDiv').html('Please Select Operator Field');
			
		}else if(checkNull(value).length == 0){
			
			$('#errorDiv').show();
			$('#errorDiv').html('Please Enter value');
			
		}else{
			
			value = "'"+value+"'";
			$('#errorDiv').html('');
			$('#errorDiv').hide();
			shortDesc = 'delete from '+tableName+' where ['+targetFieldName+'] '+operator+' '+value+'';	// For SQL Server 	
			//alert(shortDesc);
			$('#saveRuleDiv').show();			
			$('#result').html(shortDesc);			
			
			
		}
		
		
		
	}
	//END
	
}

function updateRuleDescServerCall(){
	
	var shortDesc = $('#result').text();
	var ruleId = $('#ruleId').val();
	
	$('#errorDiv').html('');
	$('#errorDiv').hide('');
	
	$.ajax({
        url: "/updateRuleDesc",
        type: "POST",
        data: {
	
            ruleId : encodeURIComponent(ruleId),
			shortDesc : encodeURIComponent(shortDesc)					
            
        },
        dataType: "text",		
		contentType : "application/x-www-form-urlencoded",
        success: function(response) 
		{	
			
			if(checkNull(response) == 'sucess'){				
				$('#successDiv').show();
				$('#successDiv').html('Rule Description Updated Successfully');	
			}else{				
				$('#errorDiv').show();
				$('#errorDiv').html(response);
			}			
			
			
        },
		error : function(response){
			$('#errorDiv').show();
			$('#errorDiv').html(response);
			
		}
    });
}

/*function updateRuleStatus(ruleId,status){
	
	alert(ruleId+''+status);
}
*/

function updateRuleStatus(ruleId,status){
	var statusAction = '';
	var btnClass = '';
	var rmvBtnClass = '';
	if(status =='active'){
		status = 'inactive';
		/*statusAction = 'Make Active';
		btnClass = 'mdfRuleBtnClass btn btn-success mx-1';
		rmvBtnClass = 'btn btn-danger mx-1';*/
	}else{
		status = 'active';
		/*statusAction = 'Make Inactive';
		btnClass = 'btn btn-danger mx-1';
		rmvBtnClass = 'mdfRuleBtnClass btn btn-success mx-1';*/
	}
	$.ajax({
        url: "/updateRuleStatus",
        type: "POST",
        data: {
	
            ruleId : encodeURIComponent(ruleId),
			status : encodeURIComponent(status)					
            
        },
        dataType: "text",		
		contentType : "application/x-www-form-urlencoded",
        success: function(response) 
		{	
			
			if(checkNull(response) == 'sucess'){				
				$('#successDiv').show();
				$('#successDiv').html('Rule Status Updated Successfully');	
				var projectId = $('#projectName').val();
				viewRulesServerCall(projectId);
				/*$('#statusActionBtn_'+ruleId).removeClass(rmvBtnClass);
				$('#statusActionBtn_'+ruleId).addClass(btnClass);
				$('#statusActionBtn_'+ruleId).val(statusAction);*/				
			}else{				
				$('#errorDiv').show();
				$('#errorDiv').html(response);
			}			
			
			
        },
		error : function(response){
			$('#errorDiv').show();
			$('#errorDiv').html(response);
			
		}
    });
}

/* Rule Management Execute Rule Start */

function showRulesServerCall(projectId){
	
	$('#errorDiv').html();
	$('#successDiv').html('');
	$('#errorDiv').hide();
	$('#successDiv').hide();
	$('#result').html('');
	$('#saveRuleDiv').hide();
	$('#ruleListDiv').empty();
	var tableAttr = "<table class='table table-responsive table-bordered table-striped'><tr><th style='width:5%; text-align:center;'>Sr No</th><th style='width:7%'>Rule Type</th><th>Rule Description</th><th><input type='checkbox' id='selectAllRules'  name = 'checkAllRule' onclick='selectAllCheckBoxes()'>Select All</th><th>Status</th><th>Status Description</th></tr>";
	//var tableRow = "";
	$.ajax({
        url: "/getRules",
        type: "POST",
        data: {
	
            projectId : encodeURIComponent(projectId)					
            
        },
        dataType: "json",		
		contentType : "application/x-www-form-urlencoded",
        success: function(response) 
		{	
			
			var srno = 0;
			response.forEach(obj => {
				var ruleId = '';
				var repositoryId = '';
				var ruleType = '';
				var statusAction ='';
				var statusVal = '';
				var ruledesc = ''; 
				
				var btnClass = '';
				srno ++;
		        Object.entries(obj).forEach(([key, value]) => {
		            //console.log(`${key} ${value}`);
					
					
					if(`${key}` == 'ruleId'){
						ruleId = `${value}`;
					}else if(`${key}` == 'repositoryId'){
						repositoryId = `${value}`;
					}else if(`${key}` == 'ruleType'){
						ruleType = `${value}`;
					}else if(`${key}` == 'status'){					
						//statusVal = `${value}`;
					}else if(`${key}` == 'ruleDesc'){
						ruledesc = `${value}`;
					}
					
		        });

				tableAttr +="<tr><td align='center'>"+srno+"</td><td id='ruleType_"+ruleId+"'>"+ruleType+"<input type='hidden' value = '"+repositoryId+"' id='repositoryId_"+ruleId+"'></td><td id='rule_description_" + ruleId + "'>"+ruledesc+"</td><td><input type='checkbox' name = 'checkRule' id='selectRule_"+ruleId+"'  value='"+ruleId+"'><label for='selectRule_"+ruleId+"'> Select </label></td><td id='ruleStatus_"+ruleId+"'></td><td id='ruleExeDesc_"+ruleId+"'></td></tr>";
		        
		    });
			
			if(srno == 0){
				tableAttr= tableAttr +"<tr><td colspan='6'>No Data Found</td></tr>";
			}
			
			
			tableAttr = tableAttr +"</table>";
			
			$('#ruleListDiv').show();
			$('#ruleListDiv').append(tableAttr);			
			
			
        },
		error : function(response){
			$('#errorDiv').show();
			$('#errorDiv').html(response);
			
		}
    });
	
}

function selectAllCheckBoxes(){
	
	//alert($("#selectAllRules").prop('checked'));
	
	
	var ele=document.getElementsByName('checkRule');  
    for(var i=0; i<ele.length; i++){  
        if(ele[i].type=='checkbox'){
            //ele[i].checked=true;
			if($("#selectAllRules").prop('checked')){
				ele[i].checked=true;
			}else{
				ele[i].checked=false;
			}

		}
			  
    }  

}

function executeRules(){
	//var checkedValue = document.querySelector('.selectAllRules:checked').value;
	//alert('executeRules');
	if(isRuleNotCheck() == false){
		$('#errorDiv').show();
		$('#errorDiv').html('Please Select Rule to Execute');
			
	}else{
		var ruleArray = [];
		$('#errorDiv').hide();
		$('#errorDiv').html('');
		var checkedValue = null; 
		var inputElements = document.getElementsByName('checkRule');
		for(var i=0; inputElements[i]; ++i){
		      if(inputElements[i].checked){
		           checkedValue = inputElements[i].value;
				   
					ruleArray.push(checkedValue);
				   //alert(checkedValue);
		           //break;
		      }
		}
		//alert(ruleArray);
		executeRulesServerCall(ruleArray);
		
	}
	
}

function isRuleNotCheck(){
	var isCheck = false;
	var checkedValue = null; 
		var inputElements = document.getElementsByName('checkRule');
		for(var i=0; inputElements[i]; ++i){
		      if(inputElements[i].checked){
		           checkedValue = inputElements[i].value;
				   isCheck = true;
		           break;
		      }
		}
	return isCheck;
}

function executeRulesServerCall(ruleArray){
	
	$.ajax({
        url: "/ruleExecution",
        type: "POST",
		async: false,
        data: {
				
				ruleArray : encodeURIComponent(ruleArray)	
		},
        dataType: "json",		
		contentType : "application/x-www-form-urlencoded",
        success: function(response) 
		{	
			alert('Success '+response);
			displayRuleExecutionStatus(response);
        },
		error : function(response){			
			alert('error '+response);
		}
    });
	
}

function displayRuleExecutionStatus(response){
	
	
	alert('displayRuleExecutionStatus');
	response.forEach(obj => {
		var ruleId = '';
		var message = '';
		
		Object.entries(obj).forEach(([key, value]) => {
	        console.log(`${key} ${value}`);

			if(`${key}` == 'ruleId'){
				ruleId = `${value}`;
				
			}else if(`${key}` == 'message'){
				message = `${value}`;
				if(checkNull(message).length > 0){
					$('#ruleStatus_'+ruleId).text('Execution Fail');
					$('#ruleExeDesc_'+ruleId).text(message);
				
				}else{
				$('#ruleStatus_'+ruleId).text('Executed');
				$('#ruleExeDesc_'+ruleId).text();
				}
			}
				
        });
	});
}


/* Rule Management Execute Rule End */
function checkNull(value) {
    if (typeof value !== 'string') {
        return "";
    }
    
    if (value === undefined || value === null) {
        return "";
    }
    
    return value.trim();
}