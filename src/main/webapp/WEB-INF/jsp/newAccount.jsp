<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
	.error{
		color:red;
		font-size:15px;
	}
</style>
<title>My Demo App</title>
</head>
<body>

	<h1>Enter Account Details:</h1>
	<form:form modelAttribute="newAcct" method="post" action="accountCreated">
		<table>
			<tr>
				<td align=right >Username:</td>
				<td><form:input path="username" type="text" name="username" />
				<form:errors path="username" cssClass="error" /></td>
			</tr>
			
			<tr>
				<td align=right>Password:</td>
				<td><form:input path="password" type="text" name="password" />
				<form:errors path="password" cssClass="error" /></td>
			</tr>
			
			<tr>
				<td align=right>First Name:</td> 
				<td><form:input path="firstName" type="text" name="firstname" />
				<form:errors path="firstName" cssClass="error" /></td>
			</tr>
			
			<tr>
				<td align=right>Last Name:</td>
				<td><form:input path="lastName" type="text" name="lastname" />
				<form:errors path="lastName" cssClass="error" /></td>
			</tr>
			
			<tr>
				<td align=right>Email:</td>
				<td><form:input path="email" type="text" name="email" />
				<form:errors path="email" cssClass="error" /></td>
			</tr>
			
			<tr>
				<td align=right>Age:</td>
				<td><form:input path="age" type="text" name="age" />
				<form:errors path="age" cssClass="error" /></td>
			</tr>
			
			<tr>
				<td align=right>Bio:</td>
				<td><form:input path="bio" type="text" name="bio" />
				<form:errors path="bio" cssClass="error" /></td>
			</tr>
			
			<tr>
				<td align=right>City:</td>
				<td><form:input path="city" type="text" name="city" />
				<form:errors path="city" cssClass="error" /></td>
			</tr>
			
			<tr>
				<td align=right>State:</td>
				<td><form:input path="state" type="text" name="state" />
				<form:errors path="state" cssClass="error" /></td>
			</tr>
			
			<tr>
				<td align=right>Country:</td>
				<td><form:input path="country" type="text" name="country" />
				<form:errors path="country" cssClass="error" /></td>
			</tr>
			
			<tr>
				<td align=right>Gender:</td>
				<td>
					<form:select path="gender" name="gender">
						<option value="M">Male</option>
						<option value="F">Female</option>
					</form:select>
				<form:errors path="gender" cssClass="error" /></td>
			</tr>
			
			<tr><td><input type="submit" value="Create Account" /></td></tr>
		</table>
	</form:form>


</body>
</html>