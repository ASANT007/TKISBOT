<%
	session.removeAttribute("userId");
	session.removeAttribute("DisplayName");
	session.removeAttribute("role");	
	session.invalidate();
	response.sendRedirect("./");

%>