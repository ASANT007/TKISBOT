
/* login validation start*/
function checkenter(e)
{
    var charCode = (navigator.appName == "Netscape") ? e.which : e.keyCode;

    if (charCode == 13)
    {
        validateUser();

    }
    return true;
}
function validateUser(){
	
		//alert('validateUser');        //stop submit the form, we will post it manually.
        //event.preventDefault();
        var username = $('#username').val();//'ASANT';
        var password = $('#password').val();//'admin@123';
        var domain =  $('#domain').val();//'udhe'; 
        
        if($('#username').val() == ""){
		
			//alert('Enter User Id');
			//$('#errorDiv').show();
			$('#errorDiv').text('Enter User Id');
		
		}else if($('#password').val() == ""){
			
			//alert('Please Enter Password');
			//$('#errorDiv').show();
			$('#errorDiv').text('Please Enter Password');
			
		}else if($('#domain').val() == ""){
			
			//alert('Please Select Domain');
			//$('#errorDiv').show();
			$('#errorDiv').text('Please Select Domain');
			
		} else{
			$('#errorDiv').empty();
			loginUser(username, password, domain);
		}
		
		
}


/* login validation end*/

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

/* Table Creation start */
function getProjects(obj){
	
	if(checkSession().includes('valid')){	
		
		var deliverabletype = obj.value;
		if(checkNull(deliverabletype).length > 0){
			getProjectsServerCall(deliverabletype);
		}	
	}
	
	
}

function genrateTableStructure(){
	
	if(checkSession().includes('valid')){
		$("#result").html('');
		$("#errorDiv").html('');
		
		var fileName = "";
		var fileExt = ";"
		if($('#file')[0].files.length > 0  ){
			fileName = $('#file')[0].files[0].name;
			fileExt = fileName.substring(fileName.lastIndexOf("."), fileName.length);
			fileExt = fileExt.toLowerCase();
		}
		
		if($('#deliverableType').val().length == 0){
			$("#errorDiv").html('Please select Deliverable Type');
		}
		else if($('#projectName').val().length == 0){
			$("#errorDiv").html('Please select Project');
		}else if($('#tabletype').val().length == 0){
			$("#errorDiv").html('Please select Table Type');
		} else if($('#file')[0].files.length === 0  ){
			$("#errorDiv").html('Please select File');
		}else if($.inArray(fileExt,['.xls','.xlsx','.csv']) == -1){
			$("#errorDiv").html('Please select .xls or.xlsx or .csv file only');
		}else{
			$("#errorDiv").html('');
			loading('result');
			genrateTableStructureServerCall();
		}	
	}
	
}

//Saves table
var columnArray = [];
function saveTableStructure(){
	
	if(checkSession() == 'valid'){		
		
		if(isKeyFieldSelected() == false){
			
			$("#errorDiv").html('Please select at least one Key Field');
			
		}else{
				
			var tableName = checkNull($("#tablename").text());			
			var  keyField = checkNull(getSelectedKeyFieldVal());
			var projectId = $("#projectName option:selected").val();
			
			
			if(tableName.length == 0 || keyField.length == 0 || columnArray.length == 0){
				alert('Technical Issue Please Try After Some Time ');
			}else{
				$("#errorDiv").html('');			
				loading('result');	
				saveTableStructureServerCall(projectId,tableName, keyField, columnArray);
			}
			
		}
			
	}
	
}

function isKeyFieldSelected(){
	var isCheck = false;
	var checkedValue = null; 
		var inputElements = document.getElementsByName('keyfieldCheckBox');
		for(var i=0; inputElements[i]; ++i){
		      if(inputElements[i].checked){
		           checkedValue = inputElements[i].value;
				   isCheck = true;
		           break;
		      }
		}
		
	return isCheck;
}

function getSelectedKeyFieldVal(){
	columnArray = [];//empty Array
	var checkedValue = ''; 
		var inputElements = document.getElementsByName('keyfieldCheckBox');
		for(var i=0; inputElements[i]; ++i){
		      if(inputElements[i].checked){
		           checkedValue = checkedValue+','+inputElements[i].value;
		      }
		      columnArray.push(inputElements[i].value);
		}
	checkedValue = checkedValue.substring(1,checkedValue.length);
	
	return checkedValue;
}

/* Table Creation end */

/*Table Modification START */
function getTableStructure(){
	
	if(checkSession() == 'valid'){
		
		$("#colAddResult").empty(); $("#result").empty();
		var deliverableType = $('#deliverableType').val();
		var projectName = $('#projectName').val();
		var tableName = $('#tableName option:selected').text();
		
		//Move belowcode in serverValidation.js
		
		if(checkNull(deliverableType).length == 0){
			
			$("#colAddResult").append("<div class='py-3 text-center'>  <span style='color:red'> Please Select Deliverable Type </span> </div>");		
			
		}
		else if(checkNull(projectName).length == 0){
			
			$("#colAddResult").append("<div class='py-3 text-center'>  <span style='color:red'> Please Select Project </span> </div>");
		}
		else if(checkNull($('#tableName').val()).length == 0){
			
			$("#colAddResult").append("<div class='py-3 text-center'>  <span style='color:red'> Please Select Table Name </span> </div>");
			
		}else if((!$("#viewTableRdo").is(":checked")) && (!$("#viewFileRdo").is(":checked")) ){
			
			$("#colAddResult").append("<div class='py-3 text-center'>  <span style='color:red'> Please Select View table OR View file name </span> </div>");
			
		}else{
			
			$("#colAddResult").empty();
			loading('colAddResult');
			$('#fileNameUpdationRow').hide();	
			
			if(($("#viewTableRdo").is(":checked"))){
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
						getTableStructureServerCall(tableName, keyField)	
			        }
			    });
			}else{
				$.ajax({
			        url: "getFileName",
			        type: "POST",
			        data: {
						repositoryId : encodeURIComponent($('#tableName').val())
			        },
			        dataType: "text",		
					contentType : "application/x-www-form-urlencoded",
			        success: function(response) 
					{		
						$("#colAddResult").empty(); $('#oldFileName').text(''); $('#newFileName').val('');
						$('#oldFileName').append(response);	$('#fileNameUpdationRow').show();	
			        },
					error: function(response) {
						$("#colAddResult").empty();	$("#colAddResult").append(response);
					}
			    });
			}
			
		}
	}
}

// updating file name start 	
function getUpdatedFileName(fileName,tableName){
	
	if(checkSession() == 'valid')
	{
		$("#colAddResult").empty();
		var newFileName = checkNull($("#newFileName").val());
		var fileExt = '';
		if(checkNull(newFileName).length > 0){
			if(newFileName.includes(".")){
				fileExt = newFileName.substring(newFileName.lastIndexOf("."), newFileName.length);
				fileExt = fileExt.toLowerCase();
			}
		}
		
		if( newFileName == ''){
			$("#colAddResult").append("<div class='py-3 text-center'>  <span style='color:red'> Please Enter New File Name </span> </div>");
			
		}else if(fileExt == ''){
			
			$("#colAddResult").append("<div class='py-3 text-center'>  <span style='color:red'> Please Enter File Name  with extension </span> </div>");
			
		}else if($.inArray(fileExt,['.xls','.xlsx','.csv']) == -1){		
			$("#colAddResult").append("<div class='py-3 text-center'>  <span style='color:red'> Only .xls or.xlsx or .csv files are allowed</span> </div>");	
		}else{
			
			$("#colAddResult").empty(); loading('colAddResult');
			
			$.ajax({
		        url: "updateFileName",
		        type: "POST",
		        data: {			
					fileName : encodeURIComponent(newFileName),            
					repositoryId : encodeURIComponent($('#tableName').val())
		        },
		        dataType: "text",		
				contentType : "application/x-www-form-urlencoded",
		        success: function(response) 
				{
					$("#colAddResult").empty();
					$("#colAddResult").append("<div class='py-3 text-center'>New File Name <span style='color:green'>"+ newFileName +" </span> Changed Successfully</div>");				
		        },
		        error:function(response){
					$("#colAddResult").empty();
					$("#colAddResult").append("<div class='py-3 text-center'>  <span style='color:red'>"+response+" </span> </div>");
				}
	    	});
		}	
	}
	
}

// updating file name end 
function validateNewColumn(){
	
	if(checkSession() == 'valid')
	{
		$("#colAddResult").empty();
		var tableName = checkNull($('#currentTableName').val());
		var columnName = checkNull($('#newColumn').val());
		var validColName = getValidColumnName(columnName);
		$("#colAddResult").empty();
		
		if(columnName.length == 0){
			$("#colAddResult").append("<div class='py-3 text-center'>  <span style='color:red'> Please Enter New Column Name </span> </div>");
		}
		else if(columnName.length > 128){
			
			$("#colAddResult").append("<div class='row py-2'><div class='col-md-2'>Column Size is too large : </div><div class='col-md-10'> <span style='color:red'>"+columnName+"</span></div></div>");
			
		}else if(showTableData(validColName)){
			
			$("#colAddResult").append("<div class='py-3 text-center'> Column <span style='color:red'>"+ validColName +" </span> Already Present</div>");
			
		}else{
			
			$("#colAddResult").empty();
			loading('colAddResult');
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
						getnewlyAddedColumn(tableName,columnName, keyField);
			        }
			    });
			
		}
	}
	
}

function updateKeyField(){
	
	if(checkSession() == 'valid')
	{
		if(isKeyFieldSelected() == false){
						
			$("#colAddResult").append("<div class='py-3 text-center'>  <span style='color:red'> Please select at least one Key Field </span> </div>");
				
		}else{
				var tableName = checkNull($('#currentTableName').val());
				
				var  keyField = checkNull(getSelectedKeyFieldVal());
				
				if(tableName.length == 0 || keyField.length == 0 ){
					alert('Technical Issue Please Try After Some Time ');
				}else{
					$("#colAddResult").empty();
					loading('colAddResult');	
					updateKeyFieldServerCall(tableName, keyField);
				}
			
		}	
	}
	
}

function showTableData(columnName) {
        
        var myTab = document.getElementById('currentTable');

        // LOOP THROUGH EACH ROW OF THE TABLE AFTER HEADER.
        for (i = 1; i < myTab.rows.length; i++) {

            // GET THE CELLS COLLECTION OF THE CURRENT ROW.
            var objCells = myTab.rows.item(i).cells;

            // LOOP THROUGH EACH CELL OF THE CURENT ROW TO READ CELL VALUES.
            for (var j = 1; j < objCells.length; j++) {
                //alert(objCells.item(j).innerHTML);
				if(columnName == objCells.item(j).innerHTML){
					
					return true;
				}
            }
            //info.innerHTML = info.innerHTML + '<br />';     // ADD A BREAK (TAG).
        }
}

function getValidColumnName(columnName){
	
			columnName = columnName.toLowerCase();
				 	
		 	columnName = columnName.replaceAll(/[^a-zA-Z0-9]/gi, "_");
  
		 	var lastChar = columnName.length;
  
			if(columnName.endsWith("_")) {
		   
				columnName = columnName.substring(0, lastChar-1);
			}
			if(columnName.startsWith("_")){
		   	 
				columnName = columnName.substring(1, lastChar);
			}	
	return columnName;
}
function clearColAddResult(){
	
	$('#colAddResult').empty();
}
/* Table Modification END */


/* Rule Management  Start*/
//Call on action button click
function showHideFieldsForActions(obj){
	
	if(checkSession() == 'valid'){
		
		var action = checkNull(obj.value);
		
		if(action =='replace'){
			$('#targetStringDiv').show();	$('#replaceByDiv').show();		
			$('#operatorDiv').hide();	$('#sourceDiv').hide();		$('#fromDiv').hide();		$('#toDiv').hide();		$('#valueDiv').hide();
		}else if(action =='concatenate'){		
			//$('#targetStringDiv').hide();	$('#replaceByDiv').hide();
		}else if( action =='substring'){
			$('#targetStringDiv').hide();	$('#replaceByDiv').hide();
			$('#operatorDiv').hide();	$('#sourceDiv').show();		$('#fromDiv').show();		$('#toDiv').show();  $('#valueDiv').hide();
		}else if( action =='delete'){
			$('#targetStringDiv').hide();	$('#replaceByDiv').hide();
			$('#operatorDiv').show();	$('#sourceDiv').hide();		$('#fromDiv').hide();		$('#toDiv').hide();		$('#valueDiv').show();		
		}
	}
}

function createRule(){
	
	if(checkSession() == 'valid'){
		
		$('#errorDiv').html(); $('#successDiv').html(''); $('#errorDiv').hide();
		$('#successDiv').hide(); $('#result').html('');
		
		var deliverableType = $('#deliverableType').val(); var projectName = $('#projectName').val();
		var action = $('#action').val(); var tableName =  $('#tableName option:selected').text();//Changed on 20-08-2021
		var targetFieldName = $('#targetFieldName').val();
		var shortDesc = '';
		
		if(checkNull(deliverableType).length == 0){
			$('#errorDiv').show();
			$('#errorDiv').html('Please Select Deliverable Type');
		}else if(checkNull(projectName).length == 0){
			$('#errorDiv').show();
			$('#errorDiv').html('Please Select Project');
		}else if(checkNull(action).length == 0){
			$('#errorDiv').show();
			$('#errorDiv').html('Please Select Action');
			
		}else if(checkNull(tableName).length == 0){
			$('#errorDiv').show();
			$('#errorDiv').html('Please Select Table');
			
		}else if(checkNull(targetFieldName).length == 0){
			$('#errorDiv').show();
			$('#errorDiv').html('Please Select Table Field');
			
		}else if( action =='replace'){
			
			var replaceBy = $('#replaceBy').val();		var targetString = $('#targetString').val();
			
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
			var source = $('#source').val(); var from = $('#from').val(); var to = $('#to').val();
			
			if(checkNull(source).length == 0){
				$('#errorDiv').show(); $('#errorDiv').html('Please Select Source Field');
				
				
			}else if(checkNull(from).length == 0){
				$('#errorDiv').show(); $('#errorDiv').html('Please Enter Starting Position value');
			}else if(from > 750){	
				$('#errorDiv').show(); $('#errorDiv').html('Invalid Starting Position value');
			} else if(checkNull(to).length == 0){	
				$('#errorDiv').show(); $('#errorDiv').html('Please Enter Up To value');	
			}else if( to == 0){	
				$('#errorDiv').show(); $('#errorDiv').html('Up To value can not be 0');	
			}else if((parseInt(from)+parseInt(to)) > 751){	
				$('#errorDiv').show(); $('#errorDiv').html('Unreachanbe Up To value');		
			}else{
				$('#errorDiv').html(''); $('#errorDiv').hide();
				shortDesc = 'update '+tableName+' set ['+targetFieldName+'] = substring(['+source+'],'+from+','+to+')';
				//update table tablename set [f2] = Mid([f3],14,1); start from 14 end take 1 char after 14
				// e.g. SELECT SUBSTRING('SQL Tutorial', 1, 3) AS ExtractString; // OP : SQL
				$('#saveRuleDiv').show();
				$('#result').html(shortDesc);
				
			}
			
		}
		else if( action =='delete'){
			
			var operator = $('#operator').val(); 
			var value = $('#value').val();			
			/*alert(isNaN(value));
			alert($.inArray(operator,['like','=']) == -1);*/
			
			if(checkNull(operator).length == 0){
				$('#errorDiv').show(); $('#errorDiv').html('Please Select Operator Field');	
			}else if(checkNull(value).length == 0){
				$('#errorDiv').show(); $('#errorDiv').html('Please Enter value');	
			}else if(isNaN(value) && ($.inArray(operator,['like','=']) == -1)){
				
				$('#errorDiv').show(); $('#errorDiv').html('Please Enter Numeric Value');	
				
			}else{
				value = "'"+value+"'";
				$('#errorDiv').html(''); $('#errorDiv').hide();
				shortDesc = 'delete from '+tableName+' where ['+targetFieldName+'] '+operator+' '+value+'';	// For SQL Server 	
				$('#saveRuleDiv').show();
				$('#result').html(shortDesc);			
				
			}
			
		}
	}
}

//Calling from save btn of create rule page
function saveRule()
{
	if(checkSession() == 'valid'){
		var projectId = $('#projectName').val();	var shortDesc = $('#result').text();
		var repositoryId = $('#tableName').val(); var ruleType = $('#action option:selected').val();
		$('#errorDiv').html(''); 	$('#errorDiv').hide('');
		$('#successDiv').show(); 	loading('successDiv');
		saveRuleServerCall(projectId,repositoryId, shortDesc, ruleType);
	}
}

/* Rule Management Create Rule End*/

/* Rule Management View/ Modify Rule Start */
function viewRules()
{	
	if(checkSession() == 'valid'){
		$('#ruleListDiv').html('');
		var deliverableType = $('#deliverableType').val();	var projectId = $('#projectName').val();
		
		if(checkNull(deliverableType).length == 0)
		{
			$('#errorDiv').show(); $('#errorDiv').html('Please Select Deliverable Type');
			
		}else if(checkNull(projectId).length == 0)
		{
			$('#errorDiv').show();	$('#errorDiv').html('Please Select Project');
			
		}else
		{	
			$('#errorDiv').html('');	$('#errorDiv').hide();
			$('#ruleListDiv').show();	loading('ruleListDiv');		
			viewRulesServerCall(projectId);
		}
	}
}

/* Rule Management View/ Modify Rule End */

/* Rule Management Execute Rule Start */
function viewRulesForExecution(){
	
	if(checkSession() == 'valid'){
		$('#ruleListDiv').html('');	$('#executeRuleDiv').hide();//Execute rule button
		var deliverableType = $('#deliverableType').val();	var projectId = $('#projectName').val();
		if(checkNull(deliverableType).length == 0)
		{
			$('#errorDiv').show();	$('#errorDiv').html('Please Select Deliverable Type');
		}else if(checkNull(projectId).length == 0)
		{
			$('#errorDiv').show();	$('#errorDiv').html('Please Select Project');
		}else
		{	
			$('#errorDiv').html('');	$('#errorDiv').hide();	
			$('#ruleListDiv').show();	loading('ruleListDiv');	
			showRulesServerCall(projectId);
		}
	}
}
/* Rule Management Execute Rule End */

/*User Project Mapping START*/
function viewMappedProjects(){
	
	if(checkSession() == 'valid'){
		
		var mappedUserId = $('#mappedUserId').val();
		var deliverableType = $('#deliverableType').val();
		console.log('mappedUser'+mappedUser);
		console.log('deliverableType'+deliverableType);
		
		 if(checkNull(deliverableType).length == 0)
		{		
			$('#message').addClass('alert alert-danger');
			$('#message').text('Please select Deliverable Type');
			
		}else if(checkNull(mappedUserId).length == 0){
			$('#message').addClass('alert alert-danger');
			$('#message').text('Please select User');
		}
		else{
			$('#message').text('');
			$('#message').removeClass('alert alert-danger');
			loading('result');
			viewMappedProjectsServerCall(mappedUserId,deliverableType);
		}
		
		//viewMappedProjectsServerCall("10672205","1");
				
  	}
	
}

function saveUserProjectMapping(){
	if(checkSession() == 'valid'){
		
		//var mappedUser = $('#mappedUser').val();
		var mappedUserId = $('#mappedUserId').val();
		//alert(mappedUser);
		var addUserProjectMappingList = [];	
		//var removeUserProjectMappingList = [];		
		var checkedValue = null; 
		var inputElements = document.getElementsByName('checkProject');
		
		for(var i=0; inputElements[i]; ++i){
			
		      if(inputElements[i].checked){
				checkedValue = inputElements[i].value;	
				//alert(checkedValue);			   
				addUserProjectMappingList.push(checkedValue);
		      }/*else{
				checkedValue = inputElements[i].value;	
				//alert(checkedValue);
				removeUserProjectMappingList.push(checkedValue);
			}*/
		}
		if(addUserProjectMappingList.length == 0){
			$('#message').addClass('alert alert-danger');
			$('#message').text('Please select atleast one mapping');
			//alert('Please select atleast one mapping');
		}else{
			$('#message').text('');
			$('#message').removeClass('alert alert-danger');
			//saveUserProjectMappingServerCall(addUserProjectMappingList,removeUserProjectMappingList,mappedUserId)
			saveUserProjectMappingServerCall(addUserProjectMappingList,mappedUserId)
			
		}
	}
}

function removeAllProjectMappingWithUser(){
	
	if(checkSession() == 'valid'){
		
			var mappedUserId = $('#mappedUserId').val();
			var removeUserProjectMappingList = [];		
			var checkedValue = null; 
			var inputElements = document.getElementsByName('checkProject');	
			for(var i=0; inputElements[i]; ++i){
				if(inputElements[i].checked){
					checkedValue = inputElements[i].value;
					removeUserProjectMappingList.push(checkedValue);
				}		      
				
			}		
			
			if(removeUserProjectMappingList.length == 0){
				$('#message').addClass('alert alert-danger');
				$('#message').text('Please select atleast one mapping');
			}else{
				if(confirm("Are you sure you want to remove all the mapping ?")){	
					removeAllProjectMappingWithUserServerCall(removeUserProjectMappingList,mappedUserId)
				}
			}
			
		}
	
}
/*User Project Mapping END*/

/*Master Deliverable Mappping START*/

function viewMasterDeliverableTables(){
	
	if(checkSession() == 'valid'){
		
		$('#message').empty();	$('#masterDeliverabletableDiv').hide();	$('#masterDeliverablefieldDiv').hide(); $('#keyfieldDiv').hide();
		
		var deliverableType = $('#deliverableType').val();	var projectId = $('#projectName').val();
		
		if(checkNull(deliverableType).length == 0){
			$('#message').addClass('alert alert-danger');	$('#message').text('Please Select Deliverable Type');
		}else if(checkNull(projectId).length == 0) {
			$('#message').addClass('alert alert-danger');	$('#message').text('Please Select Project');
		}else
		{
			showMasterDeliverableTablesServerCall(projectId);
		}
		
	}
	
}

function mapMasterDeliverableFileds(){
		
	if(checkSession() == 'valid'){
		
		$('#message').empty(); 
		var projectId = $('#projectName').val();
		var masterTable = $('#mastertables').val(); var masterField = $('#masterfields').val();
		var deliverableTable = $('#deliverabletables').val();	var deliverableField = $('#deliverablefields').val();
		
		if(checkNull(masterTable).length == 0) {
			$('#message').addClass('alert alert-danger');
			$('#message').text('Please Select Master Table');
		}else if(checkNull(masterField).length == 0) {
			$('#message').addClass('alert alert-danger');
			$('#message').text('Please Select Master Table Field');
		}else if(checkNull(deliverableTable).length == 0) {
			$('#message').addClass('alert alert-danger');
			$('#message').text('Please Select Deliverable Table');
		}else if(checkNull(deliverableField).length == 0) {
			$('#message').addClass('alert alert-danger');
			$('#message').text('Please Select Deliverable Table Field');
		}else {	
				//Check mapping present or not on database	
				if(isMappingPresent(projectId,masterTable, masterField,deliverableTable,deliverableField) == 'true'){
					$('#message').addClass('alert alert-danger');				
					$('#message').text('Already Mapped');
				}else{
					$('#message').removeClass('alert alert-danger')
					saveMasterDeliverableMappingServerCall(projectId,masterTable,masterField,deliverableTable,deliverableField)
						
				}
		}
		
	  }
	
}

function removeMasterDeliverableMapping(){
	
	if(checkSession() == 'valid'){
		
		var projectId = $('#projectName').val();
		var mappingIdList = [];		
		var checkedValue = null; 
		var inputElements = document.getElementsByName('checkMapped');
		
		for(var i=0; inputElements[i]; ++i){
			
		      if(inputElements[i].checked){
				checkedValue = inputElements[i].value;				   
				mappingIdList.push(checkedValue);
		      }
		}
		
		if(mappingIdList.length == 0){
			$('#message').addClass('alert alert-danger');
			$('#message').text('Please select atleast one mapping to remove');
		}else{
			$('#message').text('');
			$('#message').removeClass('alert alert-danger');
			//console.log(mappingIdList);
			if(confirm("Are you sure you want to remove the mapping ?")){
				$("#selectAllMapping").prop('checked', false);	
				removeMasterDeliverableMappingServerCall(mappingIdList,projectId)
			}
			
		}
		
	}
	
}

function selectAllMapping(){
	
	if(checkSession() == 'valid'){
		
		var ele=document.getElementsByName('checkMapped');
	    for(var i=0; i<ele.length; i++){  
	        if(ele[i].type=='checkbox'){
	            //ele[i].checked=true;
				if($("#selectAllMapping").prop('checked')){
					ele[i].checked=true;
				}else{
					ele[i].checked=false;
				}
	
			}
				  
	    } 
	    
	 }
}

/*Master Deliverable Mappping END*/

/* Consistency tracking 01-10-2021 START */

function viewConsistencyByKeyField(projectId){
	
	if(checkSession().includes('valid')){	
		
		
		if(checkNull($('#dropDownkeyField').val()).length == 0){
			alert('Please Select Key Field');
		}else{
			var filterKeyField = checkNull($('#dropDownkeyField option:selected').text());
			$('#filterKeyField').val(filterKeyField);
			//$('#projecId').val(projectId);			
			document.viewConsistencyFrm.submit();
			
		}
	}
}
function saveConsistency(srNo, userId, projectId){
	
	//alert(srNo);
	if(checkSession().includes('valid')){
		
		var filterKeyField = checkNull($('#dropDownkeyField option:selected').text());
		$('#filterKeyField').val(filterKeyField);
		$('#projecId').val(projectId);		
		
		//alert(filterKeyField+' -- '+projectId);
		var keyField = $('#keyField_'+srNo).text();
		var mappingId = $('#mappingId_'+srNo).val();
		var masterData = $('#masterData_'+srNo).text();
		var deliverableData = $('#deliverableData_'+srNo).text();
		var deliverableField = $('#deliverableField_'+srNo).text();
		var consistencyFlag = $('#consistencyFlag_'+srNo+' option:selected').val();
		var consistencyRemark = $('#consistencyRemark_'+srNo).val();
		
		
		if(checkNull(consistencyFlag).length == 0){
			alert('Please select consistency flag');
		}/*else if((checkNull(deliverableData).length == 0 || checkNull(masterData).length == 0) && checkNull(consistencyFlag) !== 'On Hold'){
			alert('The transcation can submit only On Hold');
		}*/else{
			
			//Sending single json object
			var ConsistencyTracking = {
			"keyField" : keyField,
			"mdMappingid" : mappingId,
			"masterFieldValue" : masterData,
			"deliverableFieldValue" : deliverableData,		
			"consistencyFlag" : consistencyFlag,
			"remarks" : consistencyRemark,
			"flaggedBy" : userId,
			"flaggedDate" : new Date().getTime()
			};
			
		//Sending array of json object
		/*
		var ConsistencyTracking = [];
		ConsistencyTracking.push(
			{
			
			"mdMappingid" : mappingId,
			"masterFieldValue" : masterData,
			"deliverableFieldValue" : deliverableData,		
			"consistencyFlag" : consistencyFlag,
			"remarks" : consistencyRemark,
			"flaggedBy" : userId,
			"flaggedDate" : new Date().getTime()
			},
			{
			
			"mdMappingid" : mappingId,
			"masterFieldValue" : masterData,
			"deliverableFieldValue" : deliverableData,		
			"consistencyFlag" : consistencyFlag,
			"remarks" : consistencyRemark,
			"flaggedBy" : userId,
			"flaggedDate" : new Date().getTime()
			},
			{
			
			"mdMappingid" : mappingId,
			"masterFieldValue" : masterData,
			"deliverableFieldValue" : deliverableData,		
			"consistencyFlag" : consistencyFlag,
			"remarks" : consistencyRemark,
			"flaggedBy" : userId,
			"flaggedDate" : new Date().getTime()
			}
		);*/
		$('#submitBtn_'+srNo).val('Submitting...');
		$('#submitBtn_'+srNo).prop('disabled',true);
		
		saveConsistencyServerCall(ConsistencyTracking, filterKeyField, projectId);
		
		}
	}
	
	
	
}
/* Consistency tracking 01-10-2021 END */

function onlyNumberKey(event){
	
	var ASCIICode = (event.which) ? event.which :event.keyCode
	if(ASCIICode > 31 &&(ASCIICode < 48 || ASCIICode > 57))
		return false;
	return true;
}

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
