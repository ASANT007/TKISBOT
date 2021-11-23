function loading(Id){
	
	$('#'+Id).html('<div style="text-align: center"><img alt="loading" src="images/loading.gif" /></div>' );
}
/* login validation start*/
function loginUser(username, password, domain){
	
	//alert('loginUser '+username+' '+password+' '+domain);
	
	$.ajax({
		type : "POST",
		url : "validateUser",
		contentType : "application/x-www-form-urlencoded",
		data : {
			username : username,
			password : password,
			domain : domain
		},
		
		success : function (response){
			//alert('response '+response);
			var role = response;
			if(role == "User"){
				$('#errorDiv').empty();
				window.location.href = "userDashboard"
				
			}else if(role == "Functional Admin"){
				$('#errorDiv').empty();
				window.location.href = "functionalAdminHome"
				
			}
			else if (role == "Mgmt User"){
				$('#errorDiv').empty();
				window.location.href = "managementDashboard"
				
			}else if (role == "No Group"){
				$('#errorDiv').empty();
				$('#errorDiv').text('You are not authorized to access the application');
				
			}else{				
				$('#errorDiv').empty();
				$('#errorDiv').text('Invalid User Id / Password')
				
			}
			
		}
		
		
	});
}

/* login validation END*/

/* Table Creation START */
function getProjectsServerCall(deliverableTypeId){
	
	$.ajax({
        url: "getProjectsForDeliverableType",
        type: "POST",
        data: {
            deliverableTypeId : encodeURIComponent(deliverableTypeId)            
        },
        dataType: "json",
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		
		
        success: function(response, textStatus, jqXHR) {        	 
        	
			//alert('success ['+response+'] status['+JSON.stringify(textStatus)+'] jqXHR ['+JSON.stringify(jqXHR)+']');
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
				  	//alert('values'+values);
					$("#projectName").append("<option value='" + values[0] + "'>" +values[1] + "</option>");
				}
			}
        },
		error: function(response, textStatus, jqXHR){
			//alert('getProjectsServerCall error['+response+'] status['+JSON.stringify(textStatus)+'] jqXHR ['+JSON.stringify(jqXHR)+']');
			//window.location.href = "logout"
			$('#message').text(response);
			$('#message').addClass('alert alert-danger');
		}
    });
	
}

//Show table structure from uploaded file
function genrateTableStructureServerCall(){	
	
	var deliverableType = $("#deliverableType option:selected").text();
	
	var projectName = $("#projectName option:selected").text();
	
	var form = $('#fileUploadForm')[0];
 	
    var formdata = new FormData(form);
	formdata.append("deliverableTypeName",deliverableType);
	formdata.append("projectname",projectName);
	
	$.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "genrateTableStructure",
        data: formdata,
 		
        // prevent jQuery from automatically transforming the data into a query string        
        processData: false,
        contentType: false,
        cache: false,
        timeout: 1000000,
        success: function(data, textStatus, jqXHR) {
	 	
            $("#result").html(data);
            //console.log("SUCCESS : ", data);
           
           
        },
        error: function(jqXHR, textStatus, errorThrown) {  
 			
            $("#result").html(jqXHR.responseText);
            console.log("ERROR : ", jqXHR.responseText);
            
 
        }
        
    });
}


//Added on 13-10-2021 START

function saveTableStructureServerCall(projectId,tableName, keyField, columnArray){
			
			var form = $('#fileUploadForm')[0];
		 
		    var formdata = new FormData(form);			
			formdata.append("projectId",projectId);
			formdata.append("tablename",tableName);
			formdata.append("keyField",keyField);
			formdata.append("columnArray",columnArray);
			
			$.ajax({
		        type: "POST",
		        enctype: 'multipart/form-data',
		        url: "saveTableStructure",
		        data: formdata,
		 
		        // prevent jQuery from automatically transforming the data into a query string        
		        processData: false,
		        contentType: false,
		        cache: false,
		        timeout: 1000000,
		        success: function(data, textStatus, jqXHR) {			
					
					$("#result").html(data);
					$('#fileUploadForm')[0].reset();
		            console.log("SUCCESS : ", data);		           
		           
		        },
		        error: function(jqXHR, textStatus, errorThrown) {  			
		            $("#result").html(jqXHR.responseText);
		            console.log("ERROR : ", jqXHR.responseText);		            
		 
		        }
		    });	
}

//Added on 13-10-2021 END

/* Table Creation END */

/* Table Modification START*/
function getnewlyAddedColumn(tableName,columnName, keyField)
{	
	$.ajax({
        url: "alterTable",
        type: "POST",
        async: false,
        data: {
	
            tableName : encodeURIComponent(tableName),
			columnName : encodeURIComponent(columnName)
            
        },
        dataType: "json",		
		contentType : "application/x-www-form-urlencoded",
        success: function(response) 
		{
			
			$("#colAddResult").empty();
			$("#colAddResult").append("<div class='py-3 text-center'>Column <span style='color:green'>"+ response[response.length-1] +" </span> Added Successfully</div>");
			showAllCoumns(response, keyField);
			
				
        },error:function(response){
			$("#colAddResult").empty();
			$("#colAddResult").append("<div class='py-3 text-center'>  <span style='color:red'>"+response+" </span> </div>");
		}
    });
}

function showAllCoumns(response, keyField){
	
	$("#result").empty();	
	var srNo = 0;
	var trHTML = "";
	
	trHTML = trHTML+"<div style='width:50%; margin:20px auto 0 auto;'>";
	trHTML = trHTML+"<div class='row' style='border: 1px solid #ccc; padding: 22px 10px 4px 10px; margin-bottom: 15px;'><div class='col-md-8 mb-3'><input class ='form-control' type ='text' id ='newColumn' name = 'newColumn' /></div>";			
	trHTML = trHTML+"<div class='col-md-4 mb-3'><input style='float: right;' id ='addColumn' name = 'addColumn' class='btn btn-primary mx-2' type ='button' value ='Add Column' onClick='window.validateNewColumn()'/></div></div>";				
	trHTML = trHTML+"<div class='row'><div class='col-md-12 mb-3'><input style='float: right;' id ='updateKeyField' name = 'updateKeyField' class='btn btn-primary mx-2' type ='button' value ='Update KeyField' onClick='window.updateKeyField()'/></div>";				
	trHTML = trHTML+"<table id='currentTable' name='currentTable' class='table table-responsive table-bordered table-striped'><tr><th style='width:15%; text-align:center;'>Sr No</th><th>Field Name</th><th>Key Field</th></tr>";
	
	for (var i = 1; i < response.length; i++) 
	{	
		srNo++;	
		var checked = '';
		
		//keyFieldArray.forEach(element => alert(element == response[i]));		
		/*if(keyFieldArray.includes(response[i])){
			checked = 'checked';
		}*/	
		
		if(keyField.includes(',')){
			var array = keyField.split(',');		
			
			for(var j = 0; j < array.length; j++){	
				var data = array[j];		
				if( data == response[i]){
					//alert(data);
					checked = 'checked';
				}
			}	
		}else{
			if(keyField == response[i]){
				checked = 'checked';
			}
		}	
		
		trHTML += '<tr><td>'+srNo+'</td><td>'+response[i]+'</td><td><input type="checkbox" '+checked+' name="keyfieldCheckBox" id="keyfield_"'+srNo+' value='+response[i]+'></td></tr>';
	
	}
	trHTML = trHTML +"</table></div>";
	$("#result").append(trHTML);
}

function updateKeyFieldServerCall(tableName, keyField){
	
	$.ajax({
	        url: "updateKeyField",
	        type: "POST",
	        data: {
		        tableName : encodeURIComponent(tableName),
		        keyField : encodeURIComponent(keyField)
		    },
	        dataType: "text",		
			contentType : "application/x-www-form-urlencoded",
	        success: function(response) 
			{	
				$("#colAddResult").empty();						
				$("#colAddResult").append("<div class='py-3 text-center'>  <span style='color:green'> Key Field Updated Successfully</span> </div>");
	        },error:function(response){
				$("#colAddResult").empty();
				$("#colAddResult").append(response);
			}
	    });
}
/* Table Modification END*/

function getTablesForSelectedProject(obj){
	
	if(checkSession() == 'valid')
	{
	
		var projectId = obj.value;
		
		$.ajax({
	        url: "getAllTablesByProjectId",
	        type: "POST",
	        data: {
	            projectId : projectId            
	        },
	        dataType: "json",
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
	        success: function(response) {
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
				            //console.log(`${key} ${value}`);
							if(`${key}` == 'repositoryId'){
								ruleId = `${value}`;
							}else if(`${key}` == 'tableName'){
								tableName = `${value}`;
								//tableName ='Test AMOL';
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
}

function getTableStructureServerCall(tableName, keyField){
	$.ajax({
	        url: "getTableStructure",
	        type: "POST",
	        async: false,
	        data: {
		        tableName : encodeURIComponent(tableName)
		    },
	        dataType: "json",		
			contentType : "application/x-www-form-urlencoded",
	        success: function(response) 
			{	
				$("#colAddResult").empty(); $('#currentTableName').empty();	$('#currentTableName').val(tableName);
				showAllCoumns(response, keyField);	
	        },error: function(response) {
				alert('Error : '+response);
			}
	    });
}
// Calling from table modification and rule management module
function getTargetFields(tableName,forModifyRule){
	
	if(checkSession() == 'valid'){
		//Rule Management 
		//$('#tableName').css('width','auto');
		
		if(forModifyRule == 'forModifyRule'){
			tableName = tableName;
		}else{
			tableName = $('#tableName option:selected').text(); 
		}
		$.ajax({
			        url: "getTableStructure",
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
}

/* Rule Management Create Rule Start*/
function saveRuleServerCall(projectId, repositoryId, shortDesc, ruleType){	
	
	//alert('saveRuleServerCal : projectId ['+ projectId);
	//alert('saveRuleServerCal : repositoryId ['+ repositoryId);
	
	$.ajax({
        url: "saveRule",
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
				//$('#successDiv').show();
				$('#saveRuleBtn').prop('disabled',true);//Disable Button
				$('#successDiv').html('Rule Saved Successfully');				
			}else{
								
				//$('#errorDiv').show();
				$('#successDiv').hide();
				$('#errorDiv').html('');
				$('#errorDiv').html(response);
			}			
			
			
        },
		error : function(response){
			$('#successDiv').hide();
			$('#errorDiv').show();
			$('#errorDiv').html(response);
			
		}
    });
}
/* Rule Management Create Rule End*/

/* Rule Management View/ Modify Rule Start */
function viewRulesServerCall(projectId){
	
	
	$('#errorDiv').html(); $('#successDiv').html(''); $('#errorDiv').hide();
	$('#successDiv').hide(); $('#result').html(''); $('#saveRuleDiv').hide(); $('#ruleListDiv').empty();
	
	var tableAttr = "<table class='table table-responsive table-bordered table-striped'><tr><th style='width:5%; text-align:center;'>Sr No</th><th style='width:7%'>Rule Type</th><th>Rule Description</th><th style='width:6%'>Status</th><th style='width:19%'>Actions</th></tr>";
	
	$.ajax({
        url: "getRules",
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
				var ruleId = ''; var repositoryId = ''; var ruleType = ''; var statusAction =''; var statusVal = ''; var ruledesc = ''; var btnClass = '';
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
						if( checkNull(`${value}`) == 'Active'){
							
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
			
			$('#ruleListDiv').append(tableAttr);			
			
			
        },
		error : function(response){
			$('#ruleListDiv').hide();
			$('#errorDiv').show();
			$('#errorDiv').html(response);
			
		}
    });
}

function updateRuleDesc(ruleId){
	
	if(checkSession() == 'valid'){
		
		$('#modifyRuleBtn').show();
		
		$('#errorDiv').html(); $('#successDiv').html(''); $('#errorDiv').hide(); $('#successDiv').hide();
		$('#result').html(''); $('#saveRuleDiv').hide(); $('#ruleId').empty(); $('#ruleId').val(ruleId);
		
		var repoId = 'repositoryId_'+ruleId;	
		
		var repositoryId = $('#'+repoId).val();
		
		var actionId = 'ruleType_'+ruleId;
		var action = $('#'+actionId).text();
		
		$('#action').empty(); $('#action').val(action);
		
		var descId = 'rule_description_'+ruleId;
		var descText = $('#'+descId).text();
		var prvDesc = $('#ruledescId').val();
		
		if(checkNull(prvDesc).length > 0){
			$('#'+prvDesc).css('font-weight','normal');
		}
		
		$('#'+descId).css('font-weight','bold');
		$('#ruledescId').val(descId);
		
		var tableName = getTableNameFromRepositoryId(repositoryId);
		$('#tableName').empty();	
		$('#tableName').val(tableName);//Hidden field
		
		getTargetFields(tableName,'forModifyRule');	
		
		let from = descText.indexOf('[');
		let up = descText.indexOf(']');
	
		var targetFeild =  descText.substring(from+1,up);
		
		$('#targetFieldNameHidden').empty(); // Used to get Value 
		$('#targetFieldNameHidden').val(targetFeild);
		
		showHideActionPanelForModifyRules(action, descText);
	}
		
}

function getTableNameFromRepositoryId(repositoryId){
	
	var tableName = '';
	
	$.ajax({
		async: false,
        url: "getTableNameFromRepositoryId",
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
	
	return tableName
	
}

function showHideActionPanelForModifyRules (action,descText){
	
	var targetFieldName = '', targetString = '', replaceBy = '', source = '', from = '', to = '', operator = '', value = '';

	const filedArray  = descText.substring(descText.lastIndexOf("[")+1,descText.length-1).split(',');	
	
	$('#ruleTypeDiv').show(); $('#RuleType').empty(); $('#ruleType').val(action);
	
	$('#ruleName').empty();	$('#ruleName').text(action);//display rule type on UI added on 12-11-2021
	
	if(action == 'replace'){			
		
		var tar = filedArray[0].replaceAll("]","");
		$('#targetFieldNameDiv').show(); $('#targetStringDiv').show();	$('#replaceByDiv').show();
		$('#operatorDiv').hide(); $('#sourceDiv').hide();	$('#fromDiv').hide(); $('#toDiv').hide();		$('#valueDiv').hide();
		
		
		
		$("#targetFieldName > [value=" + tar + "]").attr("selected", "true");
		$('#targetString').val(filedArray[1].replaceAll("'",""));
		$('#replaceBy').val(filedArray[2].replaceAll("'","")); 
		 
	}else if(action == 'concatenate'){
		alert('Concatenate Not Done Yet');
	}
	else if(action == 'substring'){
		
		var source = filedArray[0].replaceAll("]","");
		
		$("#source option[value= "+source+"]").attr("selected",true);		
		$('#from').val(filedArray[1].replaceAll("'",""));
		$('#to').val(filedArray[2]);
		
		$('#targetFieldNameDiv').hide(); $('#targetStringDiv').hide();	$('#replaceByDiv').hide();
		$('#operatorDiv').hide();	$('#sourceDiv').show();		$('#fromDiv').show();		$('#toDiv').show();  $('#valueDiv').hide();
		
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
	
	if(checkSession() == 'valid'){
		
		$('#errorDiv').html(); $('#successDiv').html(''); $('#errorDiv').hide();
		$('#successDiv').hide(); $('#result').html(''); var action = $('#action').val();
		var tableName = $('#tableName').val(); var targetFieldName =''; var shortDesc = '';
		
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
			}else{
				$('#errorDiv').html('');$('#errorDiv').hide();			
				targetString = "'"+targetString+"'";
				replaceBy = "'"+replaceBy+"'";
				shortDesc = 'update '+tableName+' set ['+targetFieldName+'] = replace(['+targetFieldName+'],'+targetString+','+replaceBy+')';
				//update table tablename set [f1] = Replace([f1],'TERELAC','');
				$('#saveRuleDiv').show();
				$('#result').html(shortDesc);
				
			}	
		}else if( action =='concatenate'){
			$('#result').html('');	
		}else if( action =='substring'){
			var source = $('#source').val();
			var from = $('#from').val();
			var to = $('#to').val();
			
			if(checkNull(source).length == 0){
				$('#errorDiv').show();
				$('#errorDiv').html('Please Select Source Field');
			}else if(checkNull(from).length == 0){
				$('#errorDiv').show();
				$('#errorDiv').html('Please Enter Starting Position value');
			}else if(from > 750){	
				$('#errorDiv').show();
				$('#errorDiv').html('Invalid Starting Position value');
			} else if(checkNull(to).length == 0){
				$('#errorDiv').show();
				$('#errorDiv').html('Please Enter Up To value');
			}else if( to == 0){	
				$('#errorDiv').show();
				$('#errorDiv').html('Up To value can not be 0');		
				
			}else if((parseInt(from)+parseInt(to)) > 751){	
				$('#errorDiv').show();
				$('#errorDiv').html('Unreachanbe Up To value');		
				
			}else{
				$('#errorDiv').html('');
				$('#errorDiv').hide();
				shortDesc = 'update '+tableName+' set ['+targetFieldName+'] = substring (['+source+'],'+from+','+to+')';
				$('#saveRuleDiv').show();			
				$('#result').html(shortDesc);
			}
			
		}else if( action =='delete'){
			var operator = $('#operator').val();
			var value = $('#value').val();
			
			if(checkNull(operator).length == 0){
				$('#errorDiv').show();
				$('#errorDiv').html('Please Select Operator Field');
			}else if(checkNull(value).length == 0){
				$('#errorDiv').show();
				$('#errorDiv').html('Please Enter value');
			}else if(isNaN(value) && ($.inArray(operator,['like','=']) == -1)){
				
				$('#errorDiv').show(); $('#errorDiv').html('Please Enter Numeric Value');	
				
			}else{
				value = "'"+value+"'";
				$('#errorDiv').html(''); $('#errorDiv').hide();
				shortDesc = 'delete from '+tableName+' where ['+targetFieldName+'] '+operator+' '+value+'';	// For SQL Server 	
				//alert(shortDesc);
				$('#saveRuleDiv').show();			
				$('#result').html(shortDesc);
			}
			
		}
	}
	
}

function updateRuleDescServerCall(){
	
	if(checkSession() == 'valid'){
			
		var shortDesc = $('#result').text();
		var ruleId = $('#ruleId').val();
		
		$('#errorDiv').html('');
		$('#errorDiv').hide('');
		
		$('#successDiv').show();
		loading('successDiv');
		
		$.ajax({
	        url: "updateRuleDesc",
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
					//$('#successDiv').show();
					$('#successDiv').html('Rule Description Updated Successfully');	
					viewRules();
				}else{	
					$('#successDiv').hide();			
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
}

function updateRuleStatus(ruleId,status){
	
	if(checkSession() == 'valid'){
		
		var statusAction = ''; var btnClass = ''; var rmvBtnClass = '';
		
		if(status =='Active'){
			status = 'Inactive';
			
		}else{
			status = 'Active';		
		}
		$.ajax({
	        url: "updateRuleStatus",
	        type: "POST",
	        data: {
		
	            ruleId : encodeURIComponent(ruleId), status : encodeURIComponent(status)					
	            
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
}
/* Rule Management view/modify Rule END */

/* Rule Management Execute Rule Start */
function showRulesServerCall(projectId){
	
	$('#errorDiv').html(); $('#successDiv').html(''); $('#errorDiv').hide(); $('#successDiv').hide();
	$('#result').html(''); $('#saveRuleDiv').hide(); $('#ruleListDiv').empty();
	
	var tableAttr = "<table class='table table-responsive table-bordered table-striped'><tr><th style='width:5%; text-align:center;'>Sr No</th><th style='width:7%'>Rule Type</th><th>Rule Description</th><th><input type='checkbox' id='selectAllRules'  name = 'checkAllRule' onclick='selectAllCheckBoxes()'>Select All</th><th>Status</th><th>Status Description</th></tr>";
	
	$.ajax({
        url: "getrulesforexecute",
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
				var ruleId = ''; var repositoryId = ''; var ruleType = ''; var statusAction =''; var statusVal = ''; var ruledesc = ''; var btnClass = '';
				srno ++;
		        Object.entries(obj).forEach(([key, value]) => {		            
					
					if(`${key}` == 'ruleId'){
						ruleId = `${value}`;
					}else if(`${key}` == 'repositoryId'){
						repositoryId = `${value}`;
					}else if(`${key}` == 'ruleType'){
						ruleType = `${value}`;
					}else if(`${key}` == 'status'){					
						
					}else if(`${key}` == 'ruleDesc'){
						ruledesc = `${value}`;
					}
					
		        });

				tableAttr +="<tr><td align='center'>"+srno+"</td><td id='ruleType_"+ruleId+"'>"+ruleType+"<input type='hidden' value = '"+repositoryId+"' id='repositoryId_"+ruleId+"'></td><td id='rule_description_" + ruleId + "'>"+ruledesc+"</td><td><input type='checkbox' name = 'checkRule' id='selectRule_"+ruleId+"'  value='"+ruleId+"'></td><td id='ruleStatus_"+ruleId+"'></td><td id='ruleExeDesc_"+ruleId+"'></td></tr>";
		        
		    });
			
			if(srno == 0){
				tableAttr= tableAttr +"<tr><td colspan='6'>No Data Found</td></tr>";
			}else{
				
				$('#executeRuleDiv').show(); // Execute Rule Button
			}
			tableAttr = tableAttr +"</table>";
			$('#ruleListDiv').append(tableAttr);			
			
			
        },
		error : function(response){
			$('#ruleListDiv').hide();
			$('#errorDiv').show();
			$('#errorDiv').html(response);
			
		}
    });
	
}

function selectAllCheckBoxes(){
	
	if(checkSession() == 'valid'){
		
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
}

function executeRules(){
	
	if(checkSession() == 'valid'){
		
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
			      }
			}
			
			executeRulesServerCall(ruleArray);
			
		}
		
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
        url: "ruleExecution",
        type: "POST",
		async: false,
        data: {
				
			ruleArray : encodeURIComponent(ruleArray)	
		},
        dataType: "json",		
		contentType : "application/x-www-form-urlencoded",
        success: function(response) 
		{	
			displayRuleExecutionStatus(response);
        },
		error : function(response){	
			displayRuleExecutionStatus(response);
		}
    });
	
}

function displayRuleExecutionStatus(response){	
	
	//alert('displayRuleExecutionStatus');
	response.forEach(obj => {
		var ruleId = '';
		var message = '';
		var status = 'Executed';
		Object.entries(obj).forEach(([key, value]) => {
	        //console.log(`${key} ${value}`);

			if(`${key}` == 'ruleId'){
				ruleId = `${value}`;
				
			}else if(`${key}` == 'message'){
				message = `${value}`;
				if(checkNull(message).length > 0){					
					status = 'Execution Fail';
				}
			}
				
        });
        
        $('#ruleStatus_'+ruleId).text(status);
		$('#ruleExeDesc_'+ruleId).text(message);
	});
}
/* Rule Management Execute Rule End */

/*User Project Mapping START*/
function viewMappedProjectsServerCall(mappedUser,deliverableTypeId){

	$.ajax({
        url: "getUserProjectMapping",
        type: "POST",
		async: false,
        data: {
			mappedUser : encodeURIComponent(mappedUser),  
			deliverableTypeId : encodeURIComponent(deliverableTypeId)  
		},
		//data : JSON.stringify(postData),
        dataType: "json",		
		contentType : "application/x-www-form-urlencoded",
        success: function(response) 
		{	
			//alert('Success '+response);
			displayMappedUserProjects(response);
        },
		error : function(response){			
			//alert('error '+response);
			displayMappedUserProjects(response);
		}
    });
	
}

function displayMappedUserProjects(response){
	
			//$('#message').text('');
			var userName = $('#mappedUser').val();
			//alert(userName);
			$('#mappingforuser').text(userName);
			
			var lineNo = 1;
			
			var tableBody = $('table tbody');					
			var noOfRows = 	$("#UPMappingTable tr").length;//jQuery				
			console.log('noOfRows'+noOfRows);
			for(var i= noOfRows - 1; i > 0 ; i--){				
				UPMappingTable.deleteRow(i);
			}
							
			response.forEach(obj => {
						
				var projectName = ''; var projectId = '';var userId = '';	var checked = '';						
														
		        Object.entries(obj).forEach(([key, value]) => {
					
					if(`${key}` == 'PROJECT_NAME'){
						projectName = `${value}`;
					}else if(`${key}` == 'PROJECT_ID'){
						projectId = `${value}`;
					}else if(`${key}` == 'USER_ID'){
						userId = `${value}`;						
						if(checkNull(userId).length > 0){
							console.log(userId);
							checked = 'checked';
						}
					}
					
		        });
	        
		        var row = "<tr><td>"+lineNo+"</td><td id='projectname_"+lineNo+"'>"+projectName+"</td><td><input type='checkbox' "+checked +" name = 'checkProject' id='checkProject_"+lineNo+"' value= '"+projectId+"'></td></tr>";					
				tableBody.append(row);	
				lineNo++;	
						
	    	});
	    	
	    	if(lineNo == 1){
				var row = "<tr><td colspan='2' style='text-align: center'>No Data Found</td></tr>";
				//var tableBody = $('table tbody');	
				tableBody.append(row);
			}else{				
				$('#saveUPMappingChangeBtn').show();
			}
			$('#userProjectMappingDiv').show();
	
}

//function saveUserProjectMappingServerCall(addProjectList,removeProjectList,mappedUser){
function saveUserProjectMappingServerCall(addProjectList,mappedUser){
	$.ajax({
        url: "saveUserProjectMapping",
        type: "POST",
		async: false,
        data: {
			mappedUser : encodeURIComponent(mappedUser),
			addproject : encodeURIComponent(addProjectList)  
			//removeproject : encodeURIComponent(removeProjectList)
			
		},		
        dataType: "text",		
		contentType : "application/x-www-form-urlencoded",
        success: function(response) 
		{	
			$('#message').text(response);
			$('#message').addClass('alert alert-success');
			
			
        },
		error : function(response){				
			$('#message').text(response);
			$('#message').addClass('alert alert-danger');
		}
    });
}

function removeAllProjectMappingWithUserServerCall(removeProjectList,mappedUser){
	$.ajax({
        url: "removeAllProjectMappingWithUser",
        type: "POST",
		async: false,
        data: {
			mappedUser : encodeURIComponent(mappedUser),
			removeproject : encodeURIComponent(removeProjectList)
			
		},		
        dataType: "text",		
		contentType : "application/x-www-form-urlencoded",
        success: function(response) 
		{	
			$('#message').text(response);
			if(response.includes('Successfully')){
				$('#message').addClass('alert alert-success');
				viewMappedProjects();
			}else{
				$('#message').addClass('alert alert-danger');
			}
			
			
			
			
        },
		error : function(response){				
			$('#message').text(response);
			
		}
    });
}
/*User Project Mapping END*/

/*Master Deliverable Mappping START*/
function showMasterDeliverableTablesServerCall(projectId){
	
	$.ajax({
        url: "getAllTablesByProjectId",
        type: "POST",
        data: {
            projectId : projectId            
        },
        dataType: "json",
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		success: function(response) 
				{	
					$("#mastertables").empty(); $("#deliverabletables").empty();					
					
					
					if (response.length == 0) {
						$('#message').addClass('alert alert-danger');
						$('#message').text('No Tables found for selected project');
						//$('#message').css('color','red');						
						$('#masterDeliverableMappingDiv').hide();
						
					}else{
						$('#message').removeClass('alert alert-danger');						
						$('#masterDeliverabletableDiv').show();
						$('#masterDeliverablefieldDiv').show();
						$('#masterkeyfieldDiv').show();
						$('#deliverablekeyfieldDiv').show();
						
						$("#mastertables").append('<option value="">--Select Master Table--</option>');	
						$("#deliverabletables").append('<option value="">--Select Deliverable Table--</option>');
						
						response.forEach(obj => {
						
							var repositoryId = ''; var tableName ='';						
																	
					        Object.entries(obj).forEach(([key, value]) => {
					            
					            console.log(`${key} ${value}`);
					            
								if(`${key}` == 'repositoryId'){
									repositoryId = `${value}`;
								}else if(`${key}` == 'tableName'){
									tableName = `${value}`;
								}
								
					        });
				        
					        if(tableName.includes('_master_')){											
								$("#mastertables").append("<option value='" +tableName + "'>" + tableName + "</option>");
							}else{
								$("#deliverabletables").append("<option value='" +tableName + "'>" + tableName + "</option>");
							}	
											
				    	});
				    	
						getMappedMasterDeliverableDataServerCall(projectId);
					}
						
		        },error: function(response) {
					$('#message').text(response);
				}
		});
}

//On selection of master table, show master tables fileds in drop down.
function getMasterTableColumns(){
	
	if(checkSession() == 'valid'){
		$('#message').empty();	$('#message').removeClass('alert alert-danger'); $("#masterfields").empty();
		var	tableName = $('#mastertables option:selected').text();
		
		$.ajax({
		        url: "getTableStructure",
		        type: "POST",
				async: false,
		        data: {tableName : encodeURIComponent(tableName)},
		        dataType: "json",		
				contentType : "application/x-www-form-urlencoded",
		        success: function(response) 
				{	
					if (response.length == 0) {
						$("#masterfields").attr("autocomplete", "OFF");				
						$("#masterfields").append('<option value="" selected="selected" >--Select Master Field--</option>');
					} 
					else
					{	
						$("#masterfields").append('<option value="">--Select Master Field--</option>');							
						for (var i = 1; i < response.length; i++) 
						{	
							$("#masterfields").append("<option value='" + response[i] + "'>" + response[i] + "</option>");
						}
				
					}
						
		        },error: function(response) {
					$('#message').text(response);
				}
	    });	
	    getKeyFieldForMasterTable(tableName);
	}
		
}

function getKeyFieldForMasterTable(tableName){
	$.ajax({
	        url: "getkeyfield",
	        type: "POST",			        
	        data: {
		        tableName : encodeURIComponent(tableName)
		    },
	        dataType: "text",		
			contentType : "application/x-www-form-urlencoded",
	        success: function(keyField) 
			{	
				$('#masterTableKeyfield').text(keyField);	
	        }
	    });
}


function getKeyFieldForDeliverableTable(tableName){
	$.ajax({
	        url: "getkeyfield",
	        type: "POST",			        
	        data: {
		        tableName : encodeURIComponent(tableName)
		    },
	        dataType: "text",		
			contentType : "application/x-www-form-urlencoded",
	        success: function(keyField) 
			{	
				$('#deliverableTableKeyfield').text(keyField);	
	        }
	    });
}
//On selection of deliverable table, show deliverable tables fileds in drop down.
function getDeliverableTableColumns(){
	
	if(checkSession() == 'valid'){
	
		$('#message').empty();	$('#message').removeClass('alert alert-danger'); $("#deliverablefields").empty();	
		var	tableName = $('#deliverabletables option:selected').text();	
		$.ajax({
		        url: "getTableStructure",
		        type: "POST",
				async: false,
		        data: {tableName : encodeURIComponent(tableName)},
		        dataType: "json",		
				contentType : "application/x-www-form-urlencoded",
		        success: function(response) 
				{	
					if (response.length == 0) {
						$("#deliverablefields").attr("autocomplete", "OFF");				
						$("#deliverablefields").append('<option value="" selected="selected" >--Select Deliverable Field--</option>');
					} 
					else
					{	
						$("#deliverablefields").append('<option value="">--Select Deliverable Table--</option>');							
						for (var i = 1; i < response.length; i++) 
						{	
							$("#deliverablefields").append("<option value='" + response[i] + "'>" + response[i] + "</option>");
						}
				
					}
						
		        },error: function(response) {
					$('#message').text(response);
				}
		    });
		    getKeyFieldForDeliverableTable(tableName);
	}
		
}

//Calling from clientValidation
function isMappingPresent(projectId,masterTable,masterField,deliverableTable,deliverableField){
	var isNotPresent = false;
	$.ajax({
        url: "isMappingPresent",
        type: "POST",
		async: false,
        data: {
				
			projectId : projectId,	
			masterTable : encodeURIComponent(masterTable),	
			masterField : encodeURIComponent(masterField),	
			deliverableTable : encodeURIComponent(deliverableTable),	
			deliverableField : encodeURIComponent(deliverableField)	
		},
        dataType: "text",		
		contentType : "application/x-www-form-urlencoded",
        success: function(response) 
		{
			isNotPresent =  response;
        },error:function(response){
			
			$('#message').text(response);
			$('#message').addClass('alert alert-danger');
			
		}
    });
    
    return isNotPresent;
}


//Saving single mapping
function saveMasterDeliverableMappingServerCall(projectId,masterTable,masterField,deliverableTable,deliverableField){
	
	$.ajax({
        url: "saveMasterDeliverableMapping",
        type: "POST",
        data: {
				
			projectId : projectId,	
			masterTable : encodeURIComponent(masterTable),	
			masterField : encodeURIComponent(masterField),	
			deliverableTable : encodeURIComponent(deliverableTable),	
			deliverableField : encodeURIComponent(deliverableField)	
		},
        dataType: "text",		
		contentType : "application/x-www-form-urlencoded",
        success: function(response) 
		{	
			
			if(response.includes('Success')){
				$('#message').addClass('alert alert-success');
				getMappedMasterDeliverableDataServerCall(projectId);	
			}else{
				$('#message').addClass('alert alert-danger');
			}
			
			$('#message').text(response);
			
        },
		error : function(response){
					
			$('#message').text(response);		
			$('#message').addClass('alert alert-danger');
		}
    });
	
}

function getMappedMasterDeliverableDataServerCall(projectId){
	
	$.ajax({
        url: "viewMappedMasterDeliverableData",
        type: "POST",
		async: false,
        data: {
				
			projectId : projectId
		},
        dataType: "json",		
		contentType : "application/x-www-form-urlencoded",
        success: function(response) 
		{	
			
			$('#message').text('');
			var lineNo = 1;
			
			var tableBody = $('table tbody');					
			var noOfRows = 	$("#MDMappingTable tr").length;//jQuery
			//var noOfRows = 	MDMappingTable.rows.length;//JS			
			console.log('noOfRows'+noOfRows);
			for(var i= noOfRows - 1; i > 0 ; i--){				
				MDMappingTable.deleteRow(i);
			}		
			response.forEach(obj => {
						
				var mdMappingId = ''; var masterTable ='';	var masterField ='';	var deliverableTable ='';	var deliverableField ='';						
														
		        Object.entries(obj).forEach(([key, value]) => {
					if(`${key}` == 'mdMappingId'){
						mdMappingId = `${value}`;
					}else if(`${key}` == 'masterTable'){
						masterTable = `${value}`;
					}else if(`${key}` == 'masterField'){
						masterField = `${value}`;
					}else if(`${key}` == 'deliverableTable'){
						deliverableTable = `${value}`;
					}else if(`${key}` == 'deliverableField'){
						deliverableField = `${value}`;
					}
					
		        });
	        
		        var row = "<tr><td>"+lineNo+"</td><td id='masterTable_"+lineNo+"'>"+masterTable+"</td><td id='masterField_"+lineNo+"'>"+masterField+"</td><td id='deliverableTable_"+lineNo+"'>"+deliverableTable+"</td><td id='deliverableField_"+lineNo+"'>"+deliverableField+"</td><td><input type='checkbox' name = 'checkMapped' id='selectMapped_"+lineNo+"'  value='"+mdMappingId+"'></td></tr>";					
				tableBody.append(row);	
				lineNo++;	
						
	    	});
	    	
	    	if(lineNo == 1){
				var row = "<tr><td colspan='6' style='text-align: center'>No Data Found</td></tr>";
				//var tableBody = $('table tbody');	
				tableBody.append(row);
			}else{				
				$('#saveMDMappingChangeBtn').show();
			}
			$('#masterDeliverableMappingDiv').show();
        },
		error : function(response){	
			//alert(response);		
			$('#message').text(response);		
			$('#message').addClass('alert alert-danger');
		}
    });
}
function removeMasterDeliverableMappingServerCall(mappingIdList, projectId){
	
	$.ajax({
        url: "deactiveMasterDeliverableMapping",
        type: "POST",
		async: false,
        data: {
				
			mappingIdArray : encodeURIComponent(mappingIdList)	
		},
        dataType: "text",		
		contentType : "application/x-www-form-urlencoded",
        success: function(response){
			if(response.includes('success')){
				getMappedMasterDeliverableDataServerCall(projectId);										
				$('#message').text('Mapping removed successfully');
				$('#message').addClass('alert alert-success');	
			}else{
				$('#message').text(response);
				$('#message').addClass('alert alert-danger');
			}	
			
        },
		error : function(response){			
			$('#message').text(response);
			$('#message').addClass('alert alert-danger');
		}
    });
}
/* Master Deliverable Mappping END*/

/* InConsistency tracking 01-10-2021 START */
function getMappedDeliverableFieldByKeyField(projectId){
	
	if(checkSession() == 'valid'){
		
	var	filterKeyField = $('#dropDownkeyField option:selected').text();	
	console.log('projectId '+projectId); console.log('filterKeyField '+filterKeyField);
	
	$.ajax({
	        url: "getMappedDeliverableFieldByKeyField",
	        type: "POST",			
	        data: {
				projectId : encodeURIComponent(projectId),
				filterKeyField : encodeURIComponent(filterKeyField)
			},
	        dataType: "json",		
			contentType : "application/x-www-form-urlencoded",
	        success: function(response) 
			{	
				$("#filterDeliverableField").empty();
				if (response.length == 0) {
					$("#filterDeliverableField").attr("autocomplete", "OFF");				
					$("#filterDeliverableField").append('<option value="" selected="selected" >--Select Field--</option>');
				} 
				else
				{	
					$("#filterDeliverableField").append('<option value="">--Select Field --</option>');							
					for (var i = 0; i < response.length; i++) 
					{	
						$("#filterDeliverableField").append("<option value='" + response[i] + "'>" + response[i] + "</option>");
					}
			
				}
					
	        },error: function(response) {
				alert(response);
			}
	    });
		    
	}
}

function getInConsistencyDataByFilterServerCall(userId, projectId, filterKeyField, filterDeliverableField){
	$.ajax({
        url: "getInConsistencyDataByFilter",
        type: "POST",        
        data: {
			projectId : encodeURIComponent(projectId),	
            filterKeyField : encodeURIComponent(filterKeyField),
			deliverableColumn : encodeURIComponent(filterDeliverableField)
        },
        dataType: "json",		
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
        success: function(response) 
		{
			showInconsistentDataByFilter(response, userId, projectId);
	/*		var srno = 0;
			response.forEach(obj => {
				var mappingId = ""; var keyField = ""; var deliverableField = ""; var masterData = ""; 
				var deliverableData = ""; var consistencyFlag = ""; var remark = ""; 
	 
				srno ++;
		        Object.entries(obj).forEach(([key, value]) => {		            
					
					if(`${key}` == 'MAPPING_ID'){
						mappingId = `${value}`;
					}else if(`${key}` == 'KEY_FIELD'){
						keyField = `${value}`;						
					}else if(`${key}` == 'DELIVERABLE_NAME'){
						deliverableField = `${value}`;											
					}else if(`${key}` == 'MASTER_DATA'){					
						masterData = `${value}`;						
					}else if(`${key}` == 'DELIVERABLE_DATA'){
						deliverableData = `${value}`;											
					}else if(`${key}` == 'CONSISTENCY_FLAG'){					
						consistencyFlag = `${value}`;						
					}else if(`${key}` == 'REMARK'){					
						remark = `${value}`;						
					}
						
	     		});	
		        
     		});*/

        },
		error : function(response){	
			alert('Error '+response);		
			
		}
    });
}

function showInconsistentDataByFilter(response, userId, projectId){
	
	var tableAttr =  "";
	//trHTML = trHTML+ 
	tableAttr = tableAttr+"<table id='inconsistencyCheckTbl' name='inconsistencyCheckTbl' width='100%' border='0' align='left' cellpadding='0' cellspacing='0' class='table tbl-report table-bordered table-striped'>";
	tableAttr = tableAttr+"<thead style='position: sticky;top: 0' class='thead-dark'><tr>";				
	tableAttr = tableAttr+"<th style='width:50px;' align='center' valign='middle' class='table-heading header'>Sr.No</th>";				
	tableAttr = tableAttr+"<th align='center' valign='middle' class='table-heading header'>Key Field</th>";				
	tableAttr = tableAttr+"<th align='center' valign='middle' class='table-heading header'>Field Name</th>";				
	tableAttr = tableAttr+"<th align='center' valign='middle' class='table-heading header'>Master Data</th>";				
	tableAttr = tableAttr+"<th align='center' valign='middle' class='table-heading header'>Deliverable Data</th>";				
	tableAttr = tableAttr+"<th align='center' valign='middle' class='table-heading header'>Set flag as</th>";				
	tableAttr = tableAttr+"<th width=15% align='center' valign='middle' class='table-heading header'>Remarks</th>";				
	tableAttr = tableAttr+"<th align='center' valign='middle' class='table-heading header'>Action</th></tr></thead>";	
	
	// Iterate JSON HERE
	
	var srno = 0;	
	response.forEach(obj => {
			var mappingId = ""; var keyField = ""; var deliverableField = ""; var masterData = ""; 
			var deliverableData = ""; var consistencyFlag = ""; var remark = ""; 
 
			srno ++;
	        Object.entries(obj).forEach(([key, value]) => {		            
				
				if(`${key}` == 'MAPPING_ID'){
					mappingId = `${value}`;
				}else if(`${key}` == 'KEY_FIELD'){
					keyField = `${value}`;						
				}else if(`${key}` == 'DELIVERABLE_NAME'){
					deliverableField = `${value}`;											
				}else if(`${key}` == 'MASTER_DATA'){					
					masterData = `${value}`;						
				}else if(`${key}` == 'DELIVERABLE_DATA'){
					deliverableData = `${value}`;											
				}else if(`${key}` == 'CONSISTENCY_FLAG'){					
					consistencyFlag = `${value}`;						
				}else if(`${key}` == 'REMARK'){					
					remark = `${value}`;						
				}
					
     		});	
     
     tableAttr = tableAttr+"<tr><tr valign='top'>";          
     tableAttr = tableAttr+"<td class='text-center'>"+srno+"<input type='hidden' id='mappingId_"+srno+"' value='"+mappingId+"'></td>";          
     tableAttr = tableAttr+"<td id='keyField_"+srno+"'>"+keyField+"</td>";          
     tableAttr = tableAttr+"<td id='deliverableField_"+srno+"'>"+deliverableField+"</td>";          
     tableAttr = tableAttr+"<td id='masterData_"+srno+"'>"+masterData+"</td>";          
     tableAttr = tableAttr+"<td id='deliverableData_"+srno+"'>"+deliverableData+"</td>";          
     tableAttr = tableAttr+"<td><select class='form-select select-action'  id='consistencyFlag_"+srno+"' name='consistencyFlag_"+srno+"'>";          
     tableAttr = tableAttr+"<option value='' selected='selected' >Select Flag</option>";          
     tableAttr = tableAttr+"<option value='Mark as Alias'>Mark as Alias </option>";     
     /*tableAttr = tableAttr+"<option value='Ignore by Rule'>Ignore by Rule</option>"; */   
     tableAttr = tableAttr+"<option value='Ignore Manually'>Ignore Manually</option>"; 
     if( consistencyFlag == 'On Hold'){    
     	tableAttr = tableAttr+"<option value='On Hold' selected='selected' >On Hold</option>";  
     }else{   
     	tableAttr = tableAttr+"<option value='On Hold'>On Hold</option>";
     }     
     tableAttr = tableAttr+"</select></td>";     
     tableAttr = tableAttr+"<td><textarea class='form-control' id='consistencyRemark_"+srno+"'>"+remark+"</textarea></td>";     
     tableAttr = tableAttr+"<td><input id='submitBtn_"+srno+"' type='button' class='btn btn-primary done' value='Submit' onClick='saveConsistency("+srno+",\""+userId+"\",\""+projectId+"\")'>";     
          
     //tableAttr = tableAttr+"<td><input id='submitBtn_"+srno+"' type='button' class='btn btn-primary done' value='Submit' onClick='saveConsistency('"+srno+"')'>";     
     tableAttr = tableAttr+"</td></tr> "; 
 		
		        
     });
     	
     if(srno == 0){
		tableAttr = tableAttr +"<tr><tr valign='top'><td colspan='8' class='text-center'>No Data Found</td></tr>";
	 } 
     tableAttr = tableAttr+"</table>";     
     $('#inconsistencyCheckDiv').empty();
     $('#inconsistencyCheckDiv').append(tableAttr);	
     
}

function saveConsistencyServerCall(ConsistencyTracking, filterKeyField, projectId, userId){
	
	console.log(ConsistencyTracking);
	$.ajax({
        url: "saveConsistency",
        type: "POST",        
		data: JSON.stringify(ConsistencyTracking),
        dataType: "text",		
		contentType : "application/json",
        success: function(response){				
			alert('Data Submitted Successfully');
			//window.location.href = "viewProjectDetails"
			//document.forms["viewConsistencyFrm"].submit();
			$('#filterKeyField').val(filterKeyField);
			$('#projectId').val(projectId);
			//document.viewConsistencyFrm.submit();
			getInConsistencyDataByFilter(userId, projectId);
        },
		error : function(response){	
			alert(response);		
			$('#message').text(response);
			$('#message').addClass('alert alert-danger');
		}
    });
}

function redirectToViewProjectDetails(projectId, projectName){
	if(checkSession().includes('valid')){
				
		$.ajax({
        url: "setProjectIdName",
        type: "POST",
        data: {
	
            projectId : encodeURIComponent(projectId),
			projectName : encodeURIComponent(projectName)			
            
        },
        dataType: "text",		
		contentType : "application/x-www-form-urlencoded",
        success: function(response) 
		{
			//alert('success'+response);				
			window.location.href = "viewProjectDetails"
        },
		error : function(response){	
			alert('Error '+response);		
			
		}
    });	
		
	}
	
}

/* InConsistency tracking 01-10-2021 END */

/* Inconsistency Reports 03-11-2021 START*/
function getProjectWiseReportServerCall(deliverableTypeId, projectId){
	
	$.ajax({
        url: "getProjectWiseReportData",
        type: "POST",
        data: {
	
            deliverableTypeId : encodeURIComponent(deliverableTypeId),
			projectId : encodeURIComponent(projectId)		
            
        },
        dataType: "json",		
		contentType : "application/x-www-form-urlencoded",
        success: function(response) 
		{
			showProjectWiseReportData(response);
        },
		error : function(response){	
			alert('Error '+response);		
			
		}
    });	
    
}

function getFieldWiseReportServerCall(deliverableTypeId, projectId){
	
	$.ajax({
        url: "getFieldWiseReportData",
        type: "POST",
        data: {
	
         	//deliverableTypeId : encodeURIComponent(deliverableTypeId),
			projectId : encodeURIComponent(projectId)		
            
        },
        dataType: "json",		
		contentType : "application/x-www-form-urlencoded",
        success: function(response) 
		{
			showFieldWiseReportData(response);
        },
		error : function(response){	
			alert('Error '+response);		
			
		}
    });	
    
}

function getProjectAndDateWiseReportServerCall(deliverableTypeId, projectId){
	
	$.ajax({
        url: "getProjectAndDateWiseReportData",
        type: "POST",
        data: {
	
            deliverableTypeId : encodeURIComponent(deliverableTypeId)
			//projectId : encodeURIComponent(projectId)		
            
        },
        dataType: "json",		
		contentType : "application/x-www-form-urlencoded",
        success: function(response) 
		{			
			showProjectAndDateWiseReportData(response);
        },
		error : function(response){	
			alert('Error '+response);		
			
		}
    });	
    
}


function showProjectWiseReportData(response){
		
	var projectNameArray = [];
	var initialCountArray = [];
	var pendingCountArray = [];
	
	var projectList = response['PROJECT_NAME'];
	var projectListArray = projectList.split(",");
	for(var i = 0; i<projectListArray.length;i++){
		projectNameArray.push(projectListArray[i]);
	}
	
	var initialDataList = response['INITIAL_DATA'];
	var initialDataListArray = initialDataList.split(",");
	for(var i = 0; i<initialDataListArray.length;i++){
		initialCountArray.push(initialDataListArray[i]);
	}
	
	var pendingDataList = response['PENDING_DATA'];
	var pendingDataListArray = pendingDataList.split(",");
	for(var i = 0; i<pendingDataListArray.length;i++){
		pendingCountArray.push(pendingDataListArray[i]);
	}
	
	/*response.forEach(obj => {
				var projectId = ''; var projectName = ''; var initialInconsistency = ''; var pendingInconsistency =''; 
				//srno ++;
		        Object.entries(obj).forEach(([key, value]) => {		            
					
					if(`${key}` == 'PROJECT_ID'){
						projectId = `${value}`;
					}else if(`${key}` == 'PROJECT_NAME'){
						projectName = `${value}`;
						projectNameArray.push(projectName);
					}else if(`${key}` == 'INITIAL_INCONSISTENCY'){
						initialInconsistency = `${value}`;
						initialCountArray.push(initialInconsistency);						
					}else if(`${key}` == 'PENDING_INCONSISTENCY'){					
						pendingInconsistency = `${value}`;
						pendingCountArray.push(pendingInconsistency);
					}
					
		        });			
		        
		    });*/
	 
      var ctx = document.getElementById('myChart').getContext('2d');
      
      var myChart = new Chart(ctx, {
          type: 'bar',
          data: {
            //labels: ["PROJECT_001", "PROJECT_002", "PROJECT_003", "PROJECT_004", "PROJECT_005"],
            labels: projectNameArray,
            datasets: [ { 
                //data: [75,40,80,100,158],
                //data: ['+initialInconsistency+'],
                data: initialCountArray,
                label: "Total Inconsistency",
                borderColor: "#e74c3c",
                backgroundColor: "#e74c3c",
                borderWidth:2
              }, { 
                //data: [40,20,40,50,49],
                data: pendingCountArray,
                label: "Pending",
                borderColor: "#4499c3",
                backgroundColor:"#4499c3",
                borderWidth:2,
              }
            ]
          }
		  
        });
		    
}

function showFieldWiseReportData(response){
		
	var fieldNameArray = [];
	var consisChkDataArray = [];	
	
	var fieldList = response['FIELDS'];
	var fieldListArray = fieldList.split(",");
	for(var i = 0; i<fieldListArray.length;i++){
		fieldNameArray.push(fieldListArray[i]);
	}
	
	var consisDataList = response['CONSIS_CHK'];
	var consisDataListArray = consisDataList.split(",");
	for(var i = 0; i<consisDataListArray.length;i++){
		consisChkDataArray.push(consisDataListArray[i]);
	}
	
	var ctxb = document.getElementById('myChart2').getContext('2d');
	var myChart = new Chart(ctxb, {
          type: 'bar',
          data: {
            //labels: ["Size","Process Unit", "Speciality Tag", "Reporting requirement", "Piping Spec"],
            labels: fieldNameArray,
            datasets: [{ 
                //data: [75,40,80,100,158],
                data: consisChkDataArray,
                borderColor:[
                  "#fff",
                  "#fff",
                  "#fff",
				  "#fff",
				  "#fff",
                ],
                backgroundColor: [
                  "#2ecc71",
		       	  "#3498db",
		          "#95a5a6",
		          "#9b59b6",
		          "#f1c40f",      
                ],
                borderWidth:2,
              }]
          },
		 	options: {
	    		plugins: {
	        		legend: {
	            	display: false
	        		}
	    		}
			}
		});
}

function showProjectAndDateWiseReportData(response){
	
	//JSON START
	var projectInconDateWiseArray = [];
	var projectInconDataArray = [];
	
	
	
var inconChkDate = response['INCONSISTENCY_DATE'];
var inconsistencyCheckDates = inconChkDate.split(",");
for(var i = 0; i<inconsistencyCheckDates.length;i++){
	projectInconDateWiseArray.push(inconsistencyCheckDates[i]);
}

console.log('projectInconDataWiseArray'+projectInconDateWiseArray);

var inconDateWise = response['INCONSISTENCY_DATEWISE_DATE'];

inconDateWise.forEach(function(element) {
	var dates = element['DATE_OF_ENTRY'];
	var projectName = element['PROJECT_NAME'];
	var projectData = element['PROJECT_DATA'];
	
	var projectDataVal = projectData.split(",");
	var projectDataValArray = [];
	for(var i = 0; i<projectDataVal.length;i++){
		
		projectDataValArray.push(projectDataVal[i]);
	}
	console.log('Project Data '+dates+' '+projectName+' '+projectData);
	
	var projectDataJson = {
			"label" : projectName,
			"data" : projectDataValArray,
			"backgroundColor" : "blue",
			"borderColor" : "lightblue",
			"fill" : false,
			"lineTension" : 0,
			"pointRadius" : 5
	}
	projectInconDataArray.push(projectDataJson);
});

console.log(projectInconDataArray);
/*for (var i = 0; i < inconDateWise.length; i++) {
	var elem = inconDateWise[i];	
	console.log('elem '+elem);
}*/

	//JSON END
		//get canvas
	var ctx = $("#myChart3");

	var data = {
		//labels : ["10/05/2021", "17/05/2021", "27/05/2021", "02/06/2021", "15/06/2021"],
		//labels : ["2021-05-10", "2021-05-17", "2021-05-27", "2021-06-02", "2021-06-15"],
		labels : projectInconDateWiseArray,
		datasets : projectInconDataArray
		/*datasets : [
			{
				label : "PROJECT_001",
				data : [150, 121, 80, 40, 15],
				backgroundColor : "blue",
				borderColor : "lightblue",
				fill : false,
				lineTension : 0,
				pointRadius : 5
			},
			{
				label : "PROJECT_002",
				data : [135, 101, 75, 37, 7],
				backgroundColor : "green",
				borderColor : "lightgreen",
				fill : false,
				lineTension : 0,
				pointRadius : 5
			},
			{
				label : "PROJECT_003",
				data : [168, 127, 95, 55, 37],
				backgroundColor : "#f1c40f",
				borderColor : "#f1c40f",
				fill : false,
				lineTension : 0,
				pointRadius : 5
			}
		]*/
		
	};

	var options = {
		title : {
			display : true,
			position : "top",
			text : "Line Graph",
			fontSize : 18,
			fontColor : "#111"
		},
		legend : {
			display : true,
			position : "bottom"
		},
		scales: {
			yAxes: [{
				ticks: {
					max: 80,
					min: -10,
					stepSize: 10
				}
			}]
		},
	};

	var chart = new Chart( ctx, {
		type : "line",
		data : data,
		options : options
	} );
	
}