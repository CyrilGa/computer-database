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
        <div class="container">
            <a class="navbar-brand" href="dashboard">
            	<spring:message code="string.title"/>
            </a>
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
                    <h1 id="page-title"><spring:message code="string.addTitle"/></h1>
                    <form:form id="validForm" action="/cdb/addComputer" method="POST" modelAttribute="computerForm">
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName"><spring:message code="string.computerName"/></label>
                                <spring:message code="string.computerName" var="stringComputerName"/>
                                <form:input type="text" class="form-control" id="computerName" name="computerName" 
                                	placeholder="${stringComputerName}" path="computerName"/>
                            </div>
                            <div class="form-group">
                                <label for="introducedDate"><spring:message code="string.introduced"/></label>
                            	<div class="form-inline">
                                	<form:input type="date" class="form-control" id="introducedDate" name="introducedDate" 
                                		min="0000-01-01" max="9999-01-01" path="introducedTime"/>
                               		<form:input type="time" class="form-control" id="introducedTime" name="introducedTime"
                               			path="introducedDate"/>
                            	</div>
                            </div>
                            <div class="form-group">
                                <label for="discontinuedDate"><spring:message code="string.discontinued"/></label>
                                <div class="form-inline">
                                <form:input type="date" class="form-control" id="discontinuedDate" name="discontinuedDate"
                                	min="0000-01-01" max="9999-01-01" path="discontinuedDate"/>
                                <form:input type="time" class="form-control" id="discontinuedTime" name="discontinuedTime"
                                	path="discontinuedTime"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="companyName"><spring:message code="string.companyName"/></label>
                                <form:select class="form-control" id="companyName" name="companyName" path="companyName">
                                	<option value="select-option-default" selected disabled hidden="true">Choose here</option>
                                	<c:forEach items="${names}" var="name">
                                    	<option value="${name}">${name}</option>
                                    </c:forEach>
                                </form:select>
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" id="btnSubmit" value="<spring:message code="string.add"/>"
                            	class="btn btn-primary"/>
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