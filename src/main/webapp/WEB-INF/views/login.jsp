<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html>
<head>
<link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js" type="text/javascript"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="<c:url value="/resources/login.css" />" type="text/css" />
<title>Login</title>
</head>
<body>
   <div class="container">
      <form class="form-signin" action="login" method="POST">
         <h2 class="form-signin-heading">Please sign in</h2>
         <label for="inputEmail" class="sr-only">Username</label> <input name="username" type="text" id="inputEmail" class="form-control" placeholder="Username" required autofocus> <label
            for="inputPassword" class="sr-only"
         >Password</label> <input name="password" type="password" id="inputPassword" class="form-control" placeholder="Password" required>
         <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
      </form>
   </div>
</body>
</html>
