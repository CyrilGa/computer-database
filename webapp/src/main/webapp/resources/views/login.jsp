<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="<c:url value="/css/bootstrap.min.css" />" rel="stylesheet"
	media="screen">
<link href="<c:url value="/css/font-awesome.css" />" rel="stylesheet"
	media="screen">
<link href="<c:url value="/css/main.css" />" rel="stylesheet"
	media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div id="container-header">
			<a class="navbar-brand" href="/cdb/dashboard"> <spring:message
					code="string.title" />
			</a> <span> <c:choose>
					<c:when test="${username != null}">
						<span id="glyph-user" class="glyphicon glyphicon-user">
							${username} </span>
						<a href="/cdb/LogoutProcess" id="log-link"> <span
							class="glyphicon glyphicon-log-out"></span> <spring:message
								code="string.logout.text" />
						</a>
					</c:when>
					<c:otherwise>
						<a href="/cdb/login" id="log-link"> <span
							class="glyphicon glyphicon-log-in"></span> <spring:message
								code="string.login.text" />
						</a>
					</c:otherwise>
				</c:choose> <a class="flag" href="?lang=fr"><img
					src="<c:url value="/img/fr.svg"/>"></img></a> <a class="flag"
				href="?lang=en"><img src="<c:url value="/img/gb.svg"/>"></img></a>
			</span>
		</div>
	</header>

	<section id="main">
		<div class="container-login-main">
			<c:if test="${\"true\".equals(error)}">
				<div id="errorMsg-container" class="alert alert-danger">
					<spring:message code="string.login.error" />
				</div>
			</c:if>


			<form id="searchForm" action="/cdb/LoginProcess" method="POST">
				<h1>
					<spring:message code="string.login.title" />
				</h1>
				<fieldset>
					<div class="form-group">
						<label for="username"> <spring:message
								code="string.login.username" />
						</label> <input type="text" id="username" name="username"
							class="form-control"
							placeholder="<spring:message code="string.login.username"/>" />
					</div>
					<div class="form-group">
						<label for="password"> <spring:message
								code="string.login.password" />
						</label> <input type="text" id="password" name="password"
							class="form-control"
							placeholder="<spring:message code="string.login.password"/>" />
					</div>
				</fieldset>
				<div class="actions pull-right">
					<input type="submit" id="btnSubmit"
						value="<spring:message code="string.submit"/>"
						class="btn btn-primary" />
				</div>
			</form>
		</div>
	</section>

	<script src="<c:url value="/js/jquery.min.js"/>"></script>
	<script src="<c:url value="/js/bootstrap.min.js"/>"></script>
	<script src="<c:url value="/js/hideErrorMsg.js"/>"></script>

</body>
</html>