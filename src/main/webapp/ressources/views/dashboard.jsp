<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="ressources/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="ressources/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="ressources/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="/cdb/dashboard"> Application - Computer Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">${count} Computers found</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="/cdb/dashboard" method="GET" class="form-inline">
						<input type="search" id="searchbox" name="search" class="form-control" 
							placeholder="Search name" value="${valueSearch}" />
						<input type="submit" id="searchsubmit" value="Filter by name" class="btn btn-primary" />
						<c:if test="${!\"\".equals(valueSearch)}">
							 <button class="btn btn-danger" onClick="$.fn.resetSearch();">
							 	<span class="glyphicon glyphicon-remove-sign"></span>
							 </button>
						</c:if>
					</form>
				</div>
				<div class="pull-left">
					<span class="btn" id="custom-p">e : toggle edit mode, del : delete selected computer(s)</span>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="/cdb/addComputer">Add Computer</a> 
					<a class="btn btn-default" id="editComputer" href="#" 
						onclick="$.fn.toggleEditMode();">Edit</a>
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
						<th>Computer name</th>
						<th>Introduced date</th>
						<!-- Table header for Discontinued Date -->
						<th>Discontinued date</th>
						<!-- Table header for Company -->
						<th>Company</th>

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

				<c:forEach items="${navigationPages}" var="page">
					<li><a href="/cdb/dashboard?page=${page}">${page}</a></li>
				</c:forEach>

				<li><a href="/cdb/dashboard?page=${page + 1}" aria-label="Next">
						<span aria-hidden="true">&raquo;</span>
				</a></li>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<a href="/cdb/dashboard?elements=10" class="btn btn-default">10</a>
				<a href="/cdb/dashboard?elements=50" class="btn btn-default">50</a>
				<a href="/cdb/dashboard?elements=100" class="btn btn-default">100</a>
			</div>
			
		</div>
	</footer>
	<script src="ressources/js/jquery.min.js"></script>
	<script src="ressources/js/bootstrap.min.js"></script>
	<script src="ressources/js/dashboard.js"></script>

</body>
</html>