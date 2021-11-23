<%@page contentType="text/html; charset=iso-8859-1" language="java"  errorPage="" %>
<%	
	session.invalidate();
	response.sendRedirect("./");
%>