<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> --%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="<c:url value="/css/bootstrap.min.css" />" rel="stylesheet" media="screen">
<link href="<c:url value="/css/font-awesome.css" />" rel="stylesheet" media="screen">
<link href="<c:url value="/css/main.css" />" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="/cdb/dashboard"> Application - Computer Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container-login-main">
			<c:if test="${\"true\".equals(error)}">
				<div id="errorMsg-container" class="alert alert-danger">
					Bad username or password
				</div>
			</c:if>
			
			<form id="searchForm" action="/loginProcess" method="POST">
				<fieldset>
					<div class="form-group">
						<label for="username">Username</label>
						<input type="text" id="username" name="username" class="form-control" 
							placeholder="username" />
						<label for="password">Password</label>
						<input type="text" id="password" name="password" class="form-control" 
							placeholder="password" />
					</div>
				</fieldset>
				<div class="actions pull-right">
					<input type="submit" id="btnSubmit" value="Submit"
						class="btn btn-primary"/>
				</div>
			</form>
		</div>
	</section>
	
	<script src="<c:url value="/js/jquery.min.js"/>"></script>
	<script src="<c:url value="/js/bootstrap.min.js"/>"></script>
	<script src="<c:url value="/js/hideErrorMsg.js"/>"></script>

</body>
</html>