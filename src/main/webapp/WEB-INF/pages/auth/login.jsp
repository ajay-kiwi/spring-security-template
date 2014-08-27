<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Login</title>
	</head>

	<body class="login login-action-login gg-core-ui">
		<div id="login">
			<h1 class="align-center">LOGIN</h1>
	
			<form name="loginform" id="loginform"
				action='<c:url value="j_spring_security_check"/>' method="post">
				<p>
					<label for="user_login">Username<br /> 
					<input type="text" name="j_username" id="username" value="" size="20" />
					</label>
				</p>
				<p>
					<label for="user_pass">Password<br /> 
					<input type="password" name="j_password" id="password" value="" size="20" />
					</label>
				</p>
				<p>
					<label for="forgotPassword"> <a href="#">Forgot Password </a></label>
				</p>
				<p>
					<input type="submit" value="Log In" />
				</p>
				<p>
					<c:if test="${error}">
						${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
					</c:if>
				</p>
			</form>
		</div>
	</body>
</html>