<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
            <a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right" id="container-id">
                        id: ${id}
                    </div>
                    <h1 id="page-title">Edit Computer</h1>

                    <form id="validForm" action="/cdb/editComputer" method="POST">
                        <input type="hidden" value="${id}" name="id"/>
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name</label>
                                <input type="text" class="form-control" id="computerName" name="computerName"
                                	placeholder="Computer name" value="${name}">
                            </div>
                            <div class="form-group">
                                <label for="introducedDate">Introduced date</label>
                            	<div class="form-inline">
                                	<input type="date" class="form-control" id="introducedDate" name="introducedDate" 
                                		placeholder="Introduced date" value="${introducedDate}"
                                		min="0000-01-01" max="9999-01-01">
                               		<input type="time" class="form-control" id="introducedTime" name="introducedTime" 
                               			placeholder="Introduced date" value="${introducedTime}">
                            	</div>
                            </div>
                            <div class="form-group">
                                <label for="discontinuedDate">Discontinued date</label>
                                <div class="form-inline">
                                <input type="date" class="form-control" id="discontinuedDate" name="discontinuedDate"
                                	placeholder="Discontinued date" value="${discontinuedDate}"
                                	min="0000-01-01" max="9999-01-01">
                                <input type="time" class="form-control" id="discontinuedTime" name="discontinuedTime"
                                	placeholder="Discontinued date" value="${discontinuedTime}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="companyName">Company</label>
                                <select class="form-control" id="companyName" name="companyName" >
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
                                </select>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" id="btnSubmit" value="Edit" class="btn btn-primary">
                            &nbsp&nbsp or &nbsp&nbsp
                            <a href="/cdb/dashboard" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
	<script src="<c:url value="/js/bootstrap.min.js"/>"></script>
	<script src="<c:url value="/js/jquery.min.js"/>"></script>
	<script src="<c:url value="/js/jquery.validate.min.js"/>"></script>
	<script src="<c:url value="/js/validation.js"/>"></script>
</body>
</html>