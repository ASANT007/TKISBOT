
/* login validation start*/

function validateUser(){
	
		//alert('validateUser');        //stop submit the form, we will post it manually.
        //event.preventDefault();
        var username = $('#username').val();//'ASANT';
        var password = $('#password').val();//'admin@123';
        var domain =  $('#domain').val();//'udhe'; 
        
        if($('#username').val() == ""){
		
			alert('Enter User Id');
		
		}else if($('#password').val() == ""){
			
			alert('Please Enter Password');
			
		}else if($('#domain').val() == ""){
			
			alert('Please Select Domain');
			
		} else{
			
			loginUser(username, password, domain);
		}
		
		
}


/* login validation end*/

/* Table Creation start */
function getProjects(obj){
	
	var deliverabletype = obj.value;
	
	if(checkNull(deliverabletype).length > 0){
		
		getProjectsServerCall(deliverabletype);
	}
	
}
function validateTableStruc(){
	
	//var deliverableType = $("#deliverableType option:selected").text();
	
	//var projectName = $("#projectName option:selected").text();
	
	//var file = $('#file')[0].files[0];
	//alert(file);
	
	$("#result").html('');
	$("#errorDiv").html('');
	
	var fileName = "";
	var fileExt = ";"
	if($('#file')[0].files.length > 0  ){
		
		fileName = $('#file')[0].files[0].name;
		fileExt = fileName.substring(fileName.lastIndexOf("."), fileName.length);
		fileExt = fileExt.toLowerCase();
		
	}
	
	//alert(fileExt);
	if($('#deliverableType').val().length == 0){
		
		$("#errorDiv").html('Please select Deliverable Type');
	}
	else if($('#projectName').val().length == 0){
		
		//alert('Please select Project');
		$("#errorDiv").html('Please select Project');
		
	}else if($('#tabletype').val().length == 0){
		
		//alert('Please select Table Type');
		$("#errorDiv").html('Please select Table Type');
		
	} else if($('#file')[0].files.length === 0  ){
		
		//alert('Please select File');
		$("#errorDiv").html('Please select File');
	}
	else if($.inArray(fileExt,['.xls','.xlsx','.csv']) == -1){
		
		$("#errorDiv").html('Please select .xls or.xlsx or .csv file only');
	}
	else{
		
		$("#errorDiv").html('');
		
		showTable();
	}
}

function showTable(){
	
	var deliverableType = $("#deliverableType option:selected").text();
	
	var projectName = $("#projectName option:selected").text();
	
	var form = $('#fileUploadForm')[0];
 	
    var formdata = new FormData(form);
	formdata.append("deliverableTypeName",deliverableType);
	formdata.append("projectname",projectName);
	
	$.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "/uploadFile",
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



function createTable(){
	
	console.log('createTable');
	var deliverableType = $("#deliverableType option:selected").text();	
	var projectName = $("#projectName option:selected").text();
	
	var form = $('#fileUploadForm')[0];
 
    var formdata = new FormData(form);
	formdata.append("deliverableTypeName",deliverableType);
	formdata.append("projectname",projectName);
	
	$.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "/saveTable",
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

/* Table Creation end */

/* Table Modification START */

// Use getTablesForSelectedProject(),  it is present in serverValidation.js have changed on 16-08-2021
// change in modifyTable.jsp
//getTablesForSelectedProject() is Using
/*
function getAllTables(obj){

	
	var projectName = encodeURIComponent($('#projectName').val());
	
	//alert(projectName+''+tableType);
	
	//add validations
	//Move belowcode in serverValidation.js
	$.ajax({
        url: "/getTableNames",
        type: "POST",
        data: {
            projectName : projectName            
        },
        dataType: "json",
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
        success: function(response) {
        	 //response(data);
        	 //alert(response);
        	 
        	$("#tableName").empty();
			if (response.length == 0) {
				$("#tableName").attr("autocomplete", "OFF");				
				$("#tableName").append('<option value="" selected="selected" >--Select Table--</option>');
			} else {
				var pattern = '_master_';
				var pat = '_deliverable_';
				
				//$("#tableName").attr("multiple", "multiple");
				//$("#tableName").attr("size", 10);
				
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
        }
    });

}

*/

function viewTable(){
	
	//alert('viewTable');
	$("#colAddResult").empty();
	$("#result").empty();	
	var projectName = $('#projectName').val();
	//var tableName = $('#tableName').val();
	
	var tableName = $('#tableName option:selected').text();
	//alert(tableName);
	//Move belowcode in serverValidation.js
	if(projectName == ''){
		
		$("#colAddResult").append("<div class='py-3 text-center'>  <span style='color:red'> Please Select Project </span> </div>");
	}
	else if(tableName == ''){
		
		$("#colAddResult").append("<div class='py-3 text-center'>  <span style='color:red'> Please Select Table Name </span> </div>");
		
	}else if((!$("#viewTableRdo").is(":checked")) && (!$("#viewFileRdo").is(":checked")) ){
		
		$("#colAddResult").append("<div class='py-3 text-center'>  <span style='color:red'> Please Select View Table OR View File </span> </div>");
	}
	else{
		
		$("#colAddResult").empty();
		$('#fileNameUpdationRow').hide();	
		if(($("#viewTableRdo").is(":checked"))){
			$.ajax({
		        url: "/getTableStructure",
		        type: "POST",
		        data: {
			
		            tableName : encodeURIComponent(tableName)
		            
		        },
		        dataType: "json",		
				contentType : "application/x-www-form-urlencoded",
		        success: function(response) 
				{
					//alert('----------');
					//$("#colAddResult").empty();
					$('#currentTableName').empty();
					$('#currentTableName').val(tableName);
					showAllCoumns(response);	
					
		        }
		    });
		}else{
			$.ajax({
		        url: "/getFileName",
		        type: "POST",
		        data: {
			
		            //tableName : encodeURIComponent(tableName)
					//send repository id
					repositoryId : encodeURIComponent($('#tableName').val())
		            
		        },
		        dataType: "text",		
				contentType : "application/x-www-form-urlencoded",
		        success: function(response) 
				{
					//alert('success'+response);
					//$("#colAddResult").empty();
					//$('label[id*="oldFileName"]').text('');	
					$('#oldFileName').text('');
					$('#newFileName').val('');
					$('#oldFileName').append(response);		
					$('#fileNameUpdationRow').show();	
					
		        },
				error: function(response) {
					//alert('error'+response);
					
				}
		    });
			
			
		}
		
	}
	
	
}

// updating file name start 	
function getUpdatedFileName(fileName,tableName){
	
	$("#colAddResult").empty();
	
	//var oldFileName = $('#oldFileName').text();// IMP
	//var tableName = $('#tableName').val();
	
	var newFileName = checkNull($("#newFileName").val());
	
	var fileExt = '';
	
	//alert(newFileName);
	if(checkNull(newFileName).length > 0){
		
		if(newFileName.includes(".")){
			
			fileExt = newFileName.substring(newFileName.lastIndexOf("."), newFileName.length);
			fileExt = fileExt.toLowerCase();
		}
		
		//alert(oldFileName.substring(oldFileName.lastIndexOf("."), oldFileName.length));
		//newFileName = fileName+""+fileExt;
		
	}
	
	//alert('fileExt'+fileExt);
	
	if( newFileName == ''){
		
		$("#colAddResult").append("<div class='py-3 text-center'>  <span style='color:red'> Please Enter New File Name </span> </div>");
		
	}else if(fileExt == ''){
		
		$("#colAddResult").append("<div class='py-3 text-center'>  <span style='color:red'> Please Enter File Name  with extension </span> </div>");
		
	}else if($.inArray(fileExt,['.xls','.xlsx','.csv']) == -1){		
		$("#colAddResult").append("<div class='py-3 text-center'>  <span style='color:red'> Only .xls or.xlsx or .csv files are allowed</span> </div>");	
	}/*else if(oldFileName.localeCompare(newFileName) == 0){
		
		$("#colAddResult").append("<div class='py-3 text-center'>  <span style='color:red'> New File Name can not be same as Old file Name </span> </div>");
		
	}*/else{
		
		$("#colAddResult").empty();
		$.ajax({
        url: "/updateFileName",
        type: "POST",
        data: {
			
			fileName : encodeURIComponent(newFileName),
            //tableName : encodeURIComponent(tableName)
			repositoryId : encodeURIComponent($('#tableName').val())
			
            
        },
        dataType: "text",		
		contentType : "application/x-www-form-urlencoded",
        success: function(response) 
		{	
			alert(response);
			$("#colAddResult").append("<div class='py-3 text-center'>New File Name <span style='color:green'>"+ newFileName +" </span> Changed Successfully</div>");				
        }
    });
	}
	
	
}

// updating file name end 
function validateNewColumn(){
	
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
		getnewlyAddedColumn(tableName,columnName);
	}
	
	
	
	
}

function showTableData(columnName) {
        //document.getElementById('info').innerHTML = "";
		
		//alert(columnName);
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

function getnewlyAddedColumn(tableName,columnName)
{
		
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
			
			$("#colAddResult").append("<div class='py-3 text-center'>Column <span style='color:green'>"+ columnName +" </span> Added Successfully</div>");
			showAllCoumns(response);
				
        }
    });
}

function clearColAddResult(){
	
	$('#colAddResult').empty();
}
/* Not working properly */
function showAllCoumns(response){
	
	alert('***********');
	
	$("#result").empty();	
	
	var srNo = 0;
	var trHTML = "";
	
	trHTML = trHTML+"<div style='width:50%; margin:20px auto 0 auto;'>";
	trHTML = trHTML+"<div class='row'><div class='col-md-8 mb-3'><input class ='form-control' type ='text' id ='newColumn' name = 'newColumn' /></div>";			
	trHTML = trHTML+"<div class='col-md-4 mb-3' style='text-align:right'><input id ='addColumn' name = 'addColumn' class='btn btn-primary mx-2' type ='button' value ='Add Column' onClick='window.validateNewColumn()'/></div></div>";				
	trHTML = trHTML+"<table id='currentTable' name='currentTable' class='table table-responsive table-bordered table-striped'><tr><th style='width:15%; text-align:center;'>Sr No</th><th>Field Name</th></tr>";
	
	for (var i = 1; i < response.length; i++) 
	{	
		
		srNo ++;		
		trHTML += '<tr><td>' + srNo + '</td><td>' + response[i] + '</td></tr>';
		
	
	}
	trHTML = trHTML +"</table></div>";
	$("#result").append(trHTML);
	
	
}
/* Table Modification END */


/* Rule Management  Start*/
/*
function showRuleMgmntPanel(obj){
	
	//var projectName = $(':selected').html();
	//alert(projectName);
	var projectId = $('#projectName').val();
	getTablesForSelectedProject(projectId);
	$('#ruleMgmntPanel').show();
	$('#createRuleBtnDiv').show();
}
*/

function showHideFieldsForActions(obj){
	
	//alert(checkNull(obj.value));
	
	var action = checkNull(obj.value);
	
	if(action =='replace'){
		
		$('#targetStringDiv').show();	$('#replaceByDiv').show();
		
		$('#operatorDiv').hide();	$('#sourceDiv').hide();		$('#fromDiv').hide();		$('#toDiv').hide();		$('#valueDiv').hide(); 
		
	}
	else if(action =='concatenate' || action =='mid' || action =='left' || action =='right' || action =='delete'){
		
		$('#targetStringDiv').hide();	$('#replaceByDiv').hide();
		
		if(action =='mid'){
			
			$('#operatorDiv').hide();	$('#sourceDiv').show();		$('#fromDiv').show();		$('#toDiv').show();  $('#valueDiv').hide(); 
			
		}else if(action =='left' || action =='right'){
						
			$('#operatorDiv').hide();	$('#sourceDiv').show();		$('#fromDiv').show();		$('#toDiv').hide();  $('#valueDiv').hide(); 
			
		}else if( action =='delete' ){
			
			$('#operatorDiv').show();	$('#sourceDiv').hide();		$('#fromDiv').hide();		$('#toDiv').hide();		$('#valueDiv').show(); 
			
		}
		
	}
	
	
}

function createRule(){
	$('#errorDiv').html();
	$('#successDiv').html('');
	$('#errorDiv').hide();
	$('#successDiv').hide();
	$('#result').html('');
	var projectName = $('#projectName').val();
	var action = $('#action').val();
	var tableName =  $('#tableName option:selected').text();//Changed on 20-08-2021
	var targetFieldName = $('#targetFieldName').val();
	
	var shortDesc = '';
	if(checkNull(projectName).length == 0){
		$('#errorDiv').show();
		$('#errorDiv').html('Please Select Project');
		
	}
	else if(checkNull(action).length == 0){
		$('#errorDiv').show();
		$('#errorDiv').html('Please Select Action');
		
	}else if(checkNull(tableName).length == 0){
		$('#errorDiv').show();
		$('#errorDiv').html('Please Select Table');
		
	}
	else if(checkNull(targetFieldName).length == 0){
		$('#errorDiv').show();
		$('#errorDiv').html('Please Select Table Field');
		
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
			//update table tablename set [f2] = Mid([f3],14,1); start from 14 end take 1 char after 14
			// e.g. SELECT SUBSTRING('SQL Tutorial', 1, 3) AS ExtractString; // OP : SQL
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
		
			if(action =='left'){
				
				shortDesc = 'update '+tableName+' set ['+targetFieldName+'] = Left(['+source+'],'+from+')';;
				//update table tablename set [f1]	= Left([f1/f2],13); 
				// e.g SELECT LEFT('SQL Tutorial', 3) AS ExtractString; // OP : SQL
			
			}else{
					
				shortDesc = 'update '+tableName+' set ['+targetFieldName+'] = Right(['+source+'],'+from+')';
			}
			
			$('#saveRuleDiv').show();
			//$('#result').html('Query : '+shortDesc);
			$('#result').html(shortDesc);
			//saveRule(projectId, shortDesc, executionSequence);
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
			
		}else{
			value = "'"+value+"'";
			$('#errorDiv').html('');
			$('#errorDiv').hide();
			shortDesc = 'delete from '+tableName+' where ['+targetFieldName+'] '+operator+' '+value+'';	// For SQL Server 	
			$('#saveRuleDiv').show();
			//$('#result').html('Query : '+shortDesc);
			$('#result').html(shortDesc);			
			
			
		}
		
		// delete * from table where sr_no = source;
		
	}
	
}
function saveRule()
{
	var projectId = $('#projectName').val();	
	var shortDesc = $('#result').text();
	var repositoryId = $('#tableName').val();
	var ruleType = $('#action option:selected').val();
	
	//alert('saveRule : projectId'+ projectId);//
	//alert('saveRule : shortDesc'+ shortDesc);
	
	$('#errorDiv').html('');
	$('#errorDiv').hide('');
	saveRuleServerCall(projectId,repositoryId, shortDesc, ruleType);
	
}

/* Rule Management Create Rule End*/

/* Rule Management Execute Rule Start */

/* Rule Management Execute Rule End */

/* Rule Management View/ Modify Rule Start */
function viewRules()
{
	//alert()
	var deliverableType = $('#deliverableType').val();
	var projectId = $('#projectName').val();
	if(checkNull(deliverableType).length == 0)
	{
		$('#errorDiv').show();
		$('#errorDiv').html('Please Select Deliverable Type');
		
	}else if(checkNull(projectId).length == 0)
	{
		$('#errorDiv').show();
		$('#errorDiv').html('Please Select Project');
	}else
	{	
		$('#errorDiv').html('');	
		$('#errorDiv').hide();			
		viewRulesServerCall(projectId);
	}
	
}

/* Rule Management View/ Modify Rule End */

function checkNull(value) {
    if (typeof value !== 'string') {
        return "";
    }
    
    if (value === undefined || value === null) {
        return "";
    }
    
    return value.trim();
}
