<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

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
		<div>
			<a class="navbar-brand" href="/cdb/dashboard">
				<spring:message code="string.title"/>
			</a>
			<span>
				<a href="?lang=fr"><img src="<c:url value="/img/fr.svg"/>"></img></a>
				<a href="?lang=en"><img src="<c:url value="/img/gb.svg"/>"></img></a>
			</span>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<c:if test="${dashboardMsg != null}">
	            <div id="dashboardMsg" class="alert alert-warning">
	            ${dashboardMsg}
	            </div>
            </c:if>
            <c:if test="${errorMsg != null}">
	            <div id="errorMsg" class="alert alert-danger">
	            ${errorMsgList}<br/>${errorMsgCount}
	            </div>
            </c:if>
        </div>
		<div class="container">
			<div id="container-title">
				<h1 id="homeTitle">${count} <spring:message code="string.computerFound"/> </h1>
			</div>
			<div id="actions" class="">
				<div class="">
					<form id="searchForm" action="/cdb/dashboard" method="GET" class="form-inline">
						<i class="btn btn-secondary" onClick="$.fn.showSecondSearch();">
							<span id="spanSecondSearch" class="glyphicon glyphicon-plus-sign"></span>
						</i>
						<input type="search" id="searchbox" name="computerName" class="form-control" 
							placeholder="<spring:message code="string.computerName"/>" value="${computerName}" />
						<input type="submit" id="searchsubmit" value="<spring:message code="string.filter"/>" class="btn btn-primary" />
						<c:if test="${!\"\".equals(computerName) || !\"\".equals(companyName)}">
							 <button class="btn btn-danger" onClick="$.fn.resetSearch();">
							 	<span class="glyphicon glyphicon-remove-sign"></span>
							 </button>
						</c:if>
						<div id="secondDiv" class="form-inline">
							<a class="btn" class="hiddenElement">
							 	<span class="glyphicon glyphicon-remove-sign hiddenElement"></span>
							 </a>
							<input type="search" name="companyName" id="companyName" class="form-control" 
								placeholder="<spring:message code="string.companyName"/>" value="${companyName}" />	
						</div>
					</form>
				</div>
				<div class="">
					<span class="btn" id="custom-p"><spring:message code="string.commands"/></span>
				</div>
				<div class="">
					<a class="btn btn-success" id="addComputer" href="/cdb/addComputer"><spring:message code="string.add"/></a> 
					<a class="btn btn-default" id="editComputer" href="#" 
						onclick="$.fn.toggleEditMode();"><spring:message code="string.edit"/></a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="/cdb/dashboard" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 70px; height: 22px;">
							<input type="checkbox" id="selectall" /> 
							<span style="vertical-align: top;"> - <a href="#" id="deleteSelected" 
								onclick="$.fn.deleteSelected();">
									<i class="fa fa-trash-o fa-lg"></i> 
								</a>
							</span>
						</th>
						<c:forEach items="${ORDERBYNAME_AUTORISED}" var="orderName" varStatus="status">
						<c:choose>
							<c:when test="${orderName.equals(orderByName)}">
								<c:choose>
									<c:when test="${\"ASC\".equals(orderByOrder)}">
										<th>
											<a href="/cdb/dashboard?orderByName=${orderName}">
												<spring:message code="${tableNames[status.index]}"/>
											</a>
											<span class="glyphicon glyphicon-sort-by-attributes"></span>
										</th>
									</c:when>
									<c:otherwise>
										<th>
											<a href="/cdb/dashboard?orderByName=${orderName}">
												<spring:message code="${tableNames[status.index]}"/>
											</a>
											<span class="glyphicon glyphicon-sort-by-attributes-alt"></span>
										</th>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<th>
									<a href="/cdb/dashboard?orderByName=${orderName}">
										<spring:message code="${tableNames[status.index]}"/>
									</a>
								</th>
							</c:otherwise>
							</c:choose>
						</c:forEach>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${computers}" var="computer">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${computer.id}"></td>
							<td><a href="/cdb/editComputer?computerId=${computer.id}"
								onclick="">${computer.name}</a></td>
							<td>${computer.introduced}</td>
							<td>${computer.discontinued}</td>
							<td>${computer.companyName}</td>
						</tr>
					</c:forEach>

				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				<li><a href="/cdb/dashboard?page=${page - 1}"
					aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
				</a></li>

				<c:forEach items="${navigationPages}" var="navPage">
					<c:choose>
						<c:when test="${page == navPage}">
							<li class="active"><a href="/cdb/dashboard?page=${navPage}">${navPage}</a></li>
						</c:when>
						<c:otherwise>
							<li><a href="/cdb/dashboard?page=${navPage}">${navPage}</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>

				<li><a href="/cdb/dashboard?page=${page + 1}" aria-label="Next">
						<span aria-hidden="true">&raquo;</span>
				</a></li>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<c:forEach items="${ELEMENTS_AUTORISED}" var="elem">
					<c:choose>
						<c:when test="${elem == elements}">
							<a href="/cdb/dashboard?elements=${elem}" class="btn btn-primary">${elem}</a>
						</c:when>
						<c:otherwise>
							<a href="/cdb/dashboard?elements=${elem}" class="btn btn-default">${elem}</a>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
			
		</div>
	</footer>
	<script type="text/javascript">
		var edit="<spring:message code="string.js.edit"/>";
		var view="<spring:message code="string.js.view"/>";
		var alertMsg="<spring:message code="string.js.alertMsg"/>";
	</script>
	<script src="<c:url value="/js/jquery.min.js"/>"></script>
	<script src="<c:url value="/js/bootstrap.min.js"/>"></script>
	<script src="<c:url value="/js/dashboard.js"/>"></script>

</body>
</html>