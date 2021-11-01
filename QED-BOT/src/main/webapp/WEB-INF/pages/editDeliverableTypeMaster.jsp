<%@page import="com.tkis.qedbot.entity.DeliverabletypeMaster"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page  language="java"  autoFlush="true"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<jsp:useBean id="encdec" class="com.tkis.qedbot.FileEncryption"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
    <%
	    String userId = checkNull((String)session.getAttribute("userId"));
		String role = checkNull((String)session.getAttribute("role"));
		String userName = (String)session.getAttribute("DisplayName");

		response.setHeader("pragma","no-cache");//HTTP 1.1
		response.setHeader("Cache-Control","no-cache");
		response.setHeader("Cache-Control","no-store");
		response.addDateHeader("Expires", -1);
		response.setDateHeader("max-age", 0);
		response.setIntHeader ("Expires", -1); //prevents caching at the proxy server
		response.addHeader("cache-Control", "private");
		
		if(userId == null || userId.equals("-1") || userId.equals(""))
            { %>
			<script language="javascript" type="text/javascript">
			   alert("Your session has expired.")
			   window.location.href = "logout";
			</script>
        <%}else{
	                if(request.getMethod().equals("POST")){
					out.println("Access to this page has been restricted ...");
					return ;
			}
     if(role.equals("Functional Admin")){
%>

      <title>Modify  Deliverable Details</title>
      <meta charset="UTF-8">
      <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
      <link href="css/bootstrap.css" rel="stylesheet" type="text/css">
	<link href="css/custom.css" rel="stylesheet" type="text/css">
	<link href="css/style.css" rel="stylesheet" type="text/css">
	<link href="vendor/hover/effects.min.css" rel="stylesheet">
	<script src="js/jquery.js"></script>
	<script src="js/common.js"></script>
	<script src="js/bootstrap.js"></script>
      <script language="JavaScript" src="js/clientscript.js"></script>
      <script language="JavaScript" src="js/serverscript.js"></script>
      <%
      try{
               	
        		DeliverabletypeMaster deliverableTypeMasterInfo=(DeliverabletypeMaster)request.getAttribute("deliverableTypeMasterInfo");
                int deliverableTypeId=deliverableTypeMasterInfo.getDeliverableTypeId();                     
                String shortName=deliverableTypeMasterInfo.getDeliverableTypeShortname();
                String fullName=deliverableTypeMasterInfo.getDeliverableTypeName();
                String status=deliverableTypeMasterInfo.getStatus();
      %>       
      <script language="javascript">
         function closeWindow(){
            window.close();
            window.opener.location.reload();       
         }
        
         function RefreshParent() {
             if (window.opener != null && !window.opener.closed) {
                 window.opener.location.reload();
             }
         }
         window.onbeforeunload = RefreshParent;
      </script>
   </head>
   <body>
      <br/>
      <form name="frm">
         <input type="hidden" name="deliverableTypeId" value="<%=deliverableTypeId%>">
         <div class="container-fluid">
            <div class="row">
               <div class="col-md-12">
                  <h2 class="m-0">Modify  Deliverable Details</h2>
               </div>
            </div>
         </div>
         <div class="content-area  mx-3 my-3 pop-up-window">
            <div class="container-fluid">
               <div class="row">
                  
                  <div class="col-md-6 col-sm-6"><label>Short Name:</label></div>
                  <div class="col-md-6 col-sm-6"><label><%=shortName%></label></div>
                  <div class="col-md-6 col-sm-6"><label>Name:</label></div>
                  <div class="col-md-6 col-sm-6"><input type="text" id="fullName" name="fullName" class="form-control"  value="<%=fullName%>"/></div>
                  <div class="col-md-6 col-sm-6"><label>Status:</label></div>
                  <div class="col-md-6 col-sm-6">
                     <div class="select-popup">
                        <select id="status" class="form-select">
                           <%if(status.equals("Active")){%>
                           <option value="Active" selected>Active</option>
                           <option value="Inactive">Inactive</option>
                           <%}else if(status.equals("Inactive")){%>
                           <option value="Active">Active</option>
                           <option value="Inactive" selected>Inactive</option>
                           <%}%>
                        </select>
                     </div>
                  </div>
               </div>
            </div>
            <div class="row">
               <div class="col-md-12 text-center"><input type="button" value="Modify" class="btn-sm btn-primary " onclick="editDeliverableData(<%=deliverableTypeId%>)">
                  <input type="button" value="Close" class="btn-sm btn-primary " onclick="closeWindow()">
               </div>
            </div>
            <div class="row">
               <div class="col-md-12">
                  <div id="errormsg" style="color:red;" class="pt-2" align="center"></div>
               </div>
            </div>
         </div>
         <%}
            catch(NullPointerException ne){
                out.println("Nullpointer Exception "+ ne.getMessage());
            }
            catch(Exception e){
                out.println("General Exception "+ e.getMessage());
            }
            %>
      </form>
   </body>
   <%}}%>
</html>

<%!
	public String checkNull(String input)
	{
	    if(input == null || "null".equalsIgnoreCase(input) || "undefined".equalsIgnoreCase(input)) {
	    	
	    	input = "";
	    }
	    
	    return input.trim();    
	}
%>