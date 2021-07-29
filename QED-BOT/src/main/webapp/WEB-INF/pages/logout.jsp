<%
	session.removeAttribute("DisplayName");
	session.removeAttribute("role");	
	session.invalidate();
	response.sendRedirect("/");

%>