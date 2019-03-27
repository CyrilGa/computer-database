<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="<c:url value="/css/bootstrap.min.css" />" rel="stylesheet" media="screen">
<link href="<c:url value="/css/font-awesome.css" />" rel="stylesheet" media="screen">
<link href="<c:url value="/css/main.css" />" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
		<div id="container-header">
			<a class="navbar-brand" href="/cdb/dashboard">
				<spring:message code="string.title"/>
			</a>
			<span>
				<c:choose>
				<c:when test="${username != null}">
					<span id="glyph-user" class="glyphicon glyphicon-user"> ${username} </span>
					<a href="/cdb/LogoutProcess" id="log-link">
          				<span class="glyphicon glyphicon-log-out"></span>
          				<spring:message code="string.logout.text"/>
        			</a>
        		</c:when>
        		<c:otherwise>
        			<a href="/cdb/login" id="log-link">
          				<span class="glyphicon glyphicon-log-in"></span>
          				<spring:message code="string.login.text"/>
        			</a>
        		</c:otherwise>	
        		</c:choose>
				<a class="flag" href="?lang=fr"><img src="<c:url value="/img/fr.svg"/>"></img></a>
				<a class="flag" href="?lang=en"><img src="<c:url value="/img/gb.svg"/>"></img></a>
			</span>
		</div>
	</header>
    
    <section id="main">
    	<div id="errorMsg-container" class="container">
	    	<c:forEach items="${errorMsgs}" var="errorMsg">
				<div class="alert alert-danger">
					${errorMsg}
				</div>
			</c:forEach>
		</div>
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right" id="container-id">
                        id: ${id}
                    </div>
                    <h1 id="page-title"><spring:message code="string.editTitle"/></h1>

                    <form:form id="validForm" action="/cdb/editComputer" method="POST" modelAttribute="computerForm">
                        <form:input type="hidden" value="${id}" name="id" path="id"/>
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName"><spring:message code="string.computerName"/></label>
                                <form:input type="text" class="form-control" id="computerName" name="computerName"
                                	placeholder="Computer name" value="${name}" path="computerName"/>
                            </div>
                            <div class="form-group">
                                <label for="introducedDate"><spring:message code="string.introduced"/></label>
                            	<div class="form-inline">
                                	<form:input type="date" class="form-control" id="introducedDate" name="introducedDate" 
                                		value="${introducedDate}" min="0000-01-01" max="9999-01-01" path="introducedDate"/>
                               		<form:input type="time" class="form-control" id="introducedTime" name="introducedTime" 
                               			value="${introducedTime}" path="introducedTime"/>
                            	</div>
                            </div>
                            <div class="form-group">
                                <label for="discontinuedDate"><spring:message code="string.discontinued"/></label>
                                <div class="form-inline">
                                <form:input type="date" class="form-control" id="discontinuedDate" name="discontinuedDate"
                                	placeholder="Discontinued date" value="${discontinuedDate}" min="0000-01-01" max="9999-01-01"
                                	path="discontinuedDate"/>
                                <form:input type="time" class="form-control" id="discontinuedTime" name="discontinuedTime"
                                	value="${discontinuedTime}" path="discontinuedTime"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="companyName"><spring:message code="string.companyName"/></label>
                                <form:select class="form-control" id="companyName" name="companyName" path="companyName">
                                	<option value="select-option-default" selected disabled hidden="true">Choose here</option>
                                	<c:forEach items="${names}" var="pName">
                                		<c:choose>
                                			<c:when test="${pName.equals(companyName)}">
                                    			<option value="${pName}" selected>${pName}</option>
                                    		</c:when>
                                    		<c:otherwise>
                                    			<option value="${pName}">${pName}</option>
                                    		</c:otherwise>
                                    	</c:choose>
                                    </c:forEach>
                                </form:select>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" id="btnSubmit" value="<spring:message code="string.edit"/>" class="btn btn-primary">
                            &nbsp&nbsp or &nbsp&nbsp
                            <a href="/cdb/dashboard" class="btn btn-default"><spring:message code="string.cancel"/></a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </section>
	<script src="<c:url value="/js/bootstrap.min.js"/>"></script>
	<script src="<c:url value="/js/jquery.min.js"/>"></script>
	<script src="<c:url value="/js/jquery.validate.min.js"/>"></script>
	<script src="<c:url value="/js/validation.js"/>"></script>
	<script src="<c:url value="/js/hideErrorMsg.js"/>"></script>
</body>
</html>