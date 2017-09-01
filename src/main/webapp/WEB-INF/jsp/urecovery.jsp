<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<%=request.getContextPath() %>/resources/bootstrap/css/bootstrap.css" rel="stylesheet" />

<title>Recipe Rex - Account Recovery</title>

</head>
<body>
<div class="container">
	<div class="row">
		<div class="col-skillsoft col-lg-4 col-lg-offset-4">
			<h2>Account Recovery</h2>
		</div>
	</div>
	<div class="row">
		<div class="col-skillsoft col-lg-4 col-lg-offset-4">
			<form:form modelAttribute="login" method="post" action="acctrecovery">
				<table>
					<tr>
						<td align="left">Enter the email address associated with this account:</td>
					</tr>
					<tr>
						<td><form:input type="text" name="email" path="email" /></td>
					</tr>
					<tr>
						<td><input type="submit" name="submit" /></td>
					</tr>
				</table>
			</form:form>
		</div>
	</div>
	<jsp:useBean id="login" class = "com.pcotten.rr17.model.User"/>
	<% 
		if (request.getAttribute("username") != null){
			request.setAttribute("recovered", "Your username is " + request.getAttribute("username")); 
		}
		%>
	
	<div class="row">
		<h3>${recovered}</h3>
	</div>
	
</div>
<script src="<%=request.getContextPath() %>/resources/jquery/jquery-3.2.1.js"></script>
<script src="<%=request.getContextPath() %>/resources/bootstrap/js/bootstrap.js"></script>
</body>
</html>