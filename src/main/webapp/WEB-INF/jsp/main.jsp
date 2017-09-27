<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="<%=request.getContextPath() %>/resources/css/bootstrap.css" rel="stylesheet" />
 	<link href="<%=request.getContextPath() %>/resources/css/ui-bootstrap-csp.css" rel="stylesheet" />
 	<link href="<%=request.getContextPath() %>/resources/css/styles.css" rel="stylesheet" />
    <script src="<%=request.getContextPath() %>/resources/js/angular.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/angular-sanitize.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/angular-animate.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/angular-touch.js"></script>

    <script src="<%=request.getContextPath() %>/resources/js/controllers.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/services.js"></script>
    
<title>My Recipe App</title>
</head>
<body>
    <div class="header">
        <div class="logo">
            <img src="<%=request.getContextPath() %>/resources/images/logo.jpg" height="45px" width="45px" text="Logo"/>
        </div>
        <div class="mainMenu">
            <ul>
                <li><a href="#" >PANTRY</a></li>
                <li><a href="#" >RECIPES</a></li>
                <li><a href="#" >COOKBOOKS</a></li>
                <li><a href="#" >MEALS</a></li>
            </ul>
        </div>
        <div class="headerlink">
            <a href="http://localhost:8080/rr17/login.html">Login</a> | <a href="http://localhost:8080/rr17/newAccount.html">Create account</a>
        </div>
        
        
    </div>
    
    <div class="main">
        <div class="mainContent" ng-include src="'<%=request.getContextPath() %>/resources/partials/newRecipeMain.html'">
            
        </div>
    </div>
    <div class="footer">
        <table class="copyright">
            <tr>
                <td>Copyright © 2017 Paul Cotten</td>  
            </tr>
        </table>
    </div>
    <script src="<%=request.getContextPath() %>/resources/js/jquery-3.2.1.js"></script>
	<script src="<%=request.getContextPath() %>/resources/js/ui-bootstrap-tpls.js"></script>
</body>
</html>