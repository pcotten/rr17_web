<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<%=request.getContextPath() %>/resources/bootstrap/css/bootstrap.css" rel="stylesheet" />

<title>Recipe Rex - Login</title>
<style>
	div.col-skillsoft {
		border: 1px solid white;
		padding-top: 10px;
		font-size: 10pt;
	}
	
	.row {
		padding: 3px;
		padding-top: 8px;
		padding-bottom: 8px;
		margin-bottom: 6px;
	}
	
	.top {
		margin-top: 50px;	
	}
	
	.login {
		padding-right: 15px;
		padding-left: 15px;
	}
	
	table {
		
		padding 3px;
	}
	
	td {
		
	}
	
</style>
</head>
<body>
<div class="container">
	<div class="row top">
		<div class="col-skillsoft col-lg-4 offset-lg-4">
			<h3>Sign In</h3>
		</div>
	</div>
	<div class="row">
		<div class="col-skillsoft offset-lg-4 login">
			<form:form modelAttribute="login" method="post" action="login">
				<table>
					<tr>
						<td align="right">Username:</td>
						<td><form:input type="text" name="username" path="username" /></td>
					</tr>
					<tr>
						<td align="right">Password:</td>
						<td><form:input type="password" name="password" path="password" /></td>
					</tr>
					<tr height=15px></tr>
					<tr>
						<td align="center" colspan=2><input type="submit" name="submit" value="Login"/></td>
					</tr>
				</table>
			</form:form>
		</div>
	</div>
	<div class="row">
		<div class="col-skillsoft col-lg-4 offset-lg-4">
			<p><a href="<%=request.getContextPath() %>/urecovery.html">Forgot username?</a></p>
			<p><a href="<%=request.getContextPath() %>/precovery.html">Forgot password?</a></p>
		</div>
	</div>
	<div class="row">
		<div class="col-skillsoft col-lg-4 offset-lg-4">
			<p>Don't have a user account yet?<a href="<%=request.getContextPath() %>/newAccount.html">  Create one for free!</a></p>
		</div>
	</div>
</div>
<script src="<%=request.getContextPath() %>/resources/jquery/jquery-3.2.1.js"></script>
<script src="<%=request.getContextPath() %>/resources/bootstrap/js/bootstrap.js"></script>
</body>
</html>