<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> --%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="resources/css/font-awesome.css" rel="stylesheet"
	media="screen">
<link href="resources/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="/cdb/dashboard"> Application - Computer Database </a>
		</div>
	</header>

	<section id="main">
		<c:if test="${errorMsg != null}">
			<div id="errorMsg" class="alert alert-danger">
				${errorMsgList}<br />${errorMsgCount}
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
		
	</section>

	<script src="resources/js/jquery.min.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>

</body>
</html>