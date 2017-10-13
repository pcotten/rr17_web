<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="<%=request.getContextPath() %>/resources/css/bootstrap.css" rel="stylesheet" />
 	<link href="<%=request.getContextPath() %>/resources/css/ui-bootstrap-csp.css" rel="stylesheet" />
 	<link href="<%=request.getContextPath() %>/resources/css/primary_styles.css" rel="stylesheet" />
    <script src="<%=request.getContextPath() %>/resources/js/angular.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/angular-sanitize.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/angular-animate.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/angular-touch.js"></script>

    <script src="<%=request.getContextPath() %>/resources/js/controllers.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/pantryctl.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/services.js"></script>
    

    
<title>My Recipe App</title>
</head>
<body>
    <div id="mainWrapper" class="master">
            <div class="main_container">
                <div class="header">
                    <div class="header_logo">
                        
                    </div>
                    <div class="header_menu">
                        
                    </div>
                    <div class="header_login">
                        <span class="login_link">
                            <a href="">Login</a> | <a href="">Create account</a>
                        </span>
                    </div>
                </div>
                <div class="leftSidebar">
            	</div>
                <div class="main_content">
                   <div ng-include src="'<%=request.getContextPath() %>/resources/partials/pantryView2.html'" id="outerWrapper" class="mainContentMaster">
                    
                   </div>
                </div>
                <div class="rightSidebar">
                </div>
                <div class="footer">
                    Copyright © 2017 Paul Cotten
                </div>        
            </div>  
        </div>
    <script src="<%=request.getContextPath() %>/resources/js/jquery-3.2.1.js"></script>
	<script src="<%=request.getContextPath() %>/resources/js/ui-bootstrap-tpls.js"></script>
</body>
</html>