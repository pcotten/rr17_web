<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Recipe Rex</title>
</head>
<body>
<h1>Login successful</h1>
<h1>${test}</h1>
<table>
	<tr>
	<td align="left">username:</td><td>${user.username}</td>
	</tr>
	<tr>
	<td align="left">First Name:</td><td>${user.firstName}</td>
	</tr>
	<tr>
	<td align="left">Last Name:</td><td>${user.lastName}</td>
	</tr>
</table>
</body>
</html>