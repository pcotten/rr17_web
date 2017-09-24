<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create New Recipe</title>
 	<link href="<%=request.getContextPath() %>/resources/css/bootstrap.css" rel="stylesheet" />
 	<link href="<%=request.getContextPath() %>/resources/css/ui-bootstrap-csp.css" rel="stylesheet" />
 	<link href="<%=request.getContextPath() %>/resources/css/styles.css" rel="stylesheet" />
    <script src="<%=request.getContextPath() %>/resources/js/angular.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/angular-sanitize.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/angular-animate.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/angular-touch.js"></script>

    <script src="<%=request.getContextPath() %>/resources/js/controllers.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/services.js"></script>
    

</head>
 <body ng-include src="'<%=request.getContextPath() %>/resources/partials/newRecipeMain.html'">
 	
	<script src="<%=request.getContextPath() %>/resources/js/jquery-3.2.1.js"></script>
	<script src="<%=request.getContextPath() %>/resources/js/ui-bootstrap-tpls.js"></script>
</body>
</html>