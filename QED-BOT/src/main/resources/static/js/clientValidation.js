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

function validateTableStruc(){
	
	
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
	if(($('#projectName').val() == "select") || ($('#projectName').val() == "")){
		
		//alert('Please select Project');
		$("#errorDiv").html('Please select Project');
		
	}else if(($('#tabletype').val() == "select") || ($('#tabletype').val() == "")){
		
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
	
	
	var form = $('#fileUploadForm')[0];
 
    var data = new FormData(form);
	
	$.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "/uploadFile",
        data: data,
 
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
	
	var form = $('#fileUploadForm')[0];
 
    var data = new FormData(form);
	
	$.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "/createtable",
        data: data,
 
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

/* Table Creation end */