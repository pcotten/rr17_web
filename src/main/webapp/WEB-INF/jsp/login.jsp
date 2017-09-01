<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<%=request.getContextPath() %>/resources/bootstrap/css/bootstrap.css" rel="stylesheet" />

<title>Recipe Rex - Login</title>

</head>
<body>
<div class="container">
	<div class="row">
		<div class="col-skillsoft col-lg-4 col-lg-offset-4">
			<h2>Sign In</h2>
		</div>
	</div>
	<div class="row">
		<div class="col-skillsoft col-lg-4 col-lg-offset-4">
			<form:form modelAttribute="login" method="post" action="login">
				<table>
					<tr>
						<td align="left">Username:</td>
						<td><form:input type="text" name="username" path="username" /></td>
					</tr>
					<tr>
						<td align="left">Password:</td>
						<td><form:input type="password" name="password" path="password" /></td>
					</tr>
					<tr>
						<td><input type="submit" name="submit" /></td>
					</tr>
				</table>
			</form:form>
		</div>
	</div>
	<div class="row">
		<div class="col-skillsoft col-lg-4 col-lg-offset-4">
			<p><a href="<%=request.getContextPath() %>/urecovery.html">Forgot username or password?</a></p>
		</div>
	</div>
	<div class="row">
		<div class="col-skillsoft col-lg-4 col-lg-offset-4">
			<p>Don't have a user account yet?<a href="<%=request.getContextPath() %>/newAccount.html">  Create one for free!</a></p>
		</div>
	</div>
</div>
<script src="<%=request.getContextPath() %>/resources/jquery/jquery-3.2.1.js"></script>
<script src="<%=request.getContextPath() %>/resources/bootstrap/js/bootstrap.js"></script>
</body>
</html>