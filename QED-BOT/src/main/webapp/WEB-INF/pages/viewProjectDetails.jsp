<%@page import="org.json.simple.JSONArray"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.parser.JSONParser"%>
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
	if(role.equals("User") || role.equals("Functional Admin")){
	String projectIdStr = "", fiterKeyField = "", visibility = "Display:none",  projectName = "", selectedkeyField = "";
	//projectIdStr = (String) request.getAttribute("projectId");
	/* if(checkNull(projectIdStr).length() >0){
		projectId = Integer.parseInt(projectIdStr);
	} */
	
	int projectId = 0;
	
	projectId = (Integer) session.getAttribute("projectId");
	projectName = (String) request.getAttribute("projectName");
	fiterKeyField = (String) request.getAttribute("fiterKeyField");
	
	System.out.println("JSP: projectId"+projectId+"fiterKeyField ["+fiterKeyField+"] projectName ["+projectName+"]");
	
	if(checkNull(fiterKeyField).length() > 0){
		visibility = "Display:block";
		//selectedkeyField = (String) request.getAttribute("selectedkeyField");
	}
	
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
<style>
.header {
	position: sticky;
	top:0;
}
body {
	height: 800px;
}

.colors {
	display:none;
}
</style>
<script src="js/clientValidation.js"></script>
<script src="js/serverValidation.js"></script>

</head>

<body>

<%@page import="java.util.List" %>

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

<div class="container-fluid p-0">
	<div id="load_menu"></div>
</div>

<div class="container-fluid px-4">
  <div class="row" style="font-size:16px; padding:5px 2px;">
    <div class="col-md-12 col-sm-12" >
      <div class="login_user">Welcome <span class="user-name"><%=userName %></span>. You  are logged in as <span  class="user-name"><%=role %></span> </div>
    </div>
  </div>
  <div class="row"><div class="col-md-12">
  			<% 
      			List<Object[]> keyFieldList = (List<Object[]>) request.getAttribute("keyField");
     		 %>
 	<label>Select Key Field</label>
  <form  name ="viewConsistencyFrm" action="viewConsistencyByKeyField" method="post" style="display:inline-block"> 
  	<select name='dropDownkeyField' id='dropDownkeyField'  class="form-select" style="width:250px;margin:0 10px 10px 10px;display:inline-block">
  			<option value="" selected="selected" >--Select Key Field--</option>
					<% for(Object[] kfRow : keyFieldList){
						if(checkNull((String) kfRow[1]).length() >0){
							if(checkNull(fiterKeyField).equals((String) kfRow[1])){ %>
							<option value="<%= (Number) kfRow[0]%>" selected><%= (String) kfRow[1]%></option>
							<% }else{ %>
							<option value="<%= (Number) kfRow[0]%>"><%= (String) kfRow[1]%></option>
							<%}	
						}
										
						
					} %>
           
        </select>
        <input type="hidden" id="filterKeyField" name="filterKeyField" val="<%=fiterKeyField%>"/>
        <input type="hidden" id="projectId" name="projectId" val="<%=projectId%>"/>
        <input id="goBttn" type="button" class="btn btn-primary done" value="Submit" onClick="viewConsistencyByKeyField('<%=projectId%>')"/>
  </form>
  </div>
  </div>
  <div class="content-area" style="padding:10px 20px 10px 20px">
  <div id="KeyField1" class="KeyField1" > <h2>Inconsistency for Project: <span><%=projectName%></span> </h2>
  <div id="rec-report-table" style="<%=visibility%>"><div class="content-inner">
      <table width="100%" border="0" align="left" cellpadding="0" cellspacing="0" class="table tbl-report table-bordered table-striped">
        <thead style="position: sticky;top: 0" class="thead-dark">
          <tr>
            <th style="width:50px;" align="center" valign="middle" class="table-heading header">Sr.No</th>
            <!-- <th align="center" valign="middle" class="table-heading header">Mapping Id</th> -->
            <th align="center" valign="middle" class="table-heading header">Key Field</th>
            <th  align="center" valign="middle" class="table-heading header">Field Name</th>
            <th  align="center" valign="middle" class="table-heading header">Master Data</th>
            <th  align="center" valign="middle" class="table-heading header">Deliverable Data</th>
            <th  align="center" valign="middle" class="table-heading header">Set flag as</th>
            <th width=15% align="center" valign="middle" class="table-heading header">Remarks</th>
            <th  align="center" valign="middle" class="table-heading header">Action</th>
          </tr>
        </thead>
        
        <% 
        String jsonResponse = (String) request.getAttribute("projectdata");
        
        JSONParser jsonParse = new JSONParser();
        
        if(checkNull(jsonResponse).length() > 0){
        	
        	JSONObject jsonObject = (JSONObject) jsonParse.parse(jsonResponse);
        	JSONArray jsonArray = (JSONArray) jsonObject.get("MAPPING_DATA");
        	System.out.println("***** Data Size "+jsonArray.size());
        	
        	if(jsonArray.size() > 0){
        		int srNo = 0;
            	//long mappingId = 0; 
        		String mappingId = "", keyField = "", deliverableField = "", masterData = "", deliverableData ="", consistencyFlag ="", remark=""; 
        		
            	for(int i = 0; i<jsonArray.size(); i++){
            		
            		//mappingId = 0; 
            		mappingId = "";keyField = ""; deliverableField = ""; masterData = ""; deliverableData ="";consistencyFlag =""; remark="";
            		
            		srNo = 1+i;
            		
            		JSONObject dataObject = (JSONObject) jsonArray.get(i);
            		
            		try{
            			
            			//mappingId = (Long) dataObject.get("MAPPING_ID");
            			mappingId = (String) dataObject.get("MAPPING_ID");
            			keyField = (String) dataObject.get("KEY_FIELD");
                		deliverableField = (String) dataObject.get("DELIVERABLE_NAME");
                		masterData = (String) dataObject.get("MASTER_DATA");
                		deliverableData = (String) dataObject.get("DELIVERABLE_DATA");
                		consistencyFlag = (String) dataObject.get("CONSISTENCY_FLAG");
                		remark = (String) dataObject.get("REMARK");
                				
            		}catch(Exception e){
            			e.printStackTrace();
            		}
            		
         		%>
            		
         <tr><tr valign="top">
                  <td class="text-center"><%=srNo %><input type='hidden' id='mappingId_<%=srNo %>' value="<%=mappingId %>"/></td>              
                  <%-- <td id='mappingId_<%=srNo %>'><%=mappingId %></td> --%>
                  <td id='keyField_<%=srNo %>'><%=keyField %></td>
    	          <td id='deliverableField_<%=srNo %>'><%=deliverableField %></td>
    	          <td id='masterData_<%=srNo %>'><%=masterData %></td>
    	          <td id='deliverableData_<%=srNo %>'><%=deliverableData %></td>
    	          
              <td><select class="form-select select-action"  id='consistencyFlag_<%=srNo %>' name='consistencyFlag_<%=srNo %>'>
                  <option value="" selected="selected" >Select Flag</option>
                  <option value="Mark as Alias">Mark as Alias </option>
                  <option value="Ignore by Rule">Ignore by Rule</option>
                  <option value="Ignore Manually">Ignore Manually</option>
                  <% if(checkNull(consistencyFlag).equals("On Hold")){%>
                  <option value="On Hold" selected="selected" >On Hold</option>
                  <%}else{%>
                  <option value="On Hold">On Hold</option>
                  <%}%>
                  
                </select></td>
                
              <td><textarea class="form-control" id='consistencyRemark_<%=srNo %>'><%=remark %></textarea></td>
              <td>
                <input id='submitBtn_<%=srNo %>' type="button" class="btn btn-primary done" value="Submit" onClick="saveConsistency('<%=srNo %>','<%=userId%>','<%=projectId%>')">
              </td>
            </tr>
            	 
            <%	
            	}//End of for
        	}// End of if
        	else{%>
        	<tr><tr valign="top">
            <td colspan="8" class="text-center">No Data Found</td></tr>
        <% }
        	
        }else{%>
        	<tr><tr valign="top">
            <td colspan="8" class="text-center">No Data Found</td></tr>
        <% }%>
        
      </table>
    </div></div></div>
  
  
  </div>
</div>
<div class="container-fluid">
  <div class="row">
    <div class="footer"> &copy  thyssenkrupp Industrial Solutions India Pvt Ltd </div>
  </div>
</div>


<%if(role.equals("Functional Admin")){
System.out.println("role "+role);
%>

<script>
		        
		        $(document).ready(function () {
		            $.ajax({
		                url: "menu/menu.html", success: function (result) {

		                    $("#load_menu").html(result);

		                }
		            });
		        });			
				
				
	    </script>		
<%}else{%>
<script>
		        
		        $(document).ready(function () {
		            $.ajax({
		                url: "menu/usermenu.html", success: function (result) {

		                    $("#load_menu").html(result);

		                }
		            });
		        });			
				
				
	    </script>	
<% } %>



 <script>
$(function () {
    $("#goBttn").click(function () {
        $("#dropDown").find("option").each(function () {
            var div_id = $(this).val();
            $("." + div_id).each(function () {
                $(this).hide();
            });
        });
        $("." + $("#dropDown").val()).each(function () {
            $(this).show();
        });
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