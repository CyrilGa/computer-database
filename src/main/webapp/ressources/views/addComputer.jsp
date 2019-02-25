<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="ressources/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="ressources/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="ressources/css/main.css" rel="stylesheet" media="screen">
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
                    <h1>Add Computer</h1>
                    <form id="validForm" action="/cdb/addComputer" method="POST">
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name</label>
                                <input type="text" class="form-control" id="computerName" name="computerName" 
                                	placeholder="Computer name">
                            </div>
                            <div class="form-group">
                                <label for="introducedDate">Introduced in:</label>
                                	<input type="date" class="form-control" id="introducedDate" name="introducedDate" 
                                		placeholder="Introduced date">
                               		<input type="time" class="form-control" id="introducedTime" name="introducedTime" 
                               			placeholder="Introduced time">
                            </div>
                            <div class="form-group">
                                <label for="discontinuedDate">Discontinued in:</label>
                                <input type="date" class="form-control" id="discontinuedDate" name="discontinuedDate"
                                	placeholder="Discontinued date">
                                <input type="time" class="form-control" id="discontinuedTime" name="discontinuedTime"
                                	placeholder="Discontinued time">
                            </div>
                            <div class="form-group">
                                <label for="companyName">Company</label>
                                <select class="form-control" id="companyName" name="companyName" >
                                	<option value="select-option-default" selected disabled hidden="true">Choose here</option>
                                	<c:forEach items="${names}" var="name">
                                    	<option value="${name}">${name}</option>
                                    </c:forEach>
                                </select>
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" id="btnSubmit" value="Add" class="btn btn-primary">
                            &nbsp&nbsp or &nbsp&nbsp
                            <a href="/cdb/dashboard" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
    <script src="ressources/js/jquery.min.js"></script>
    <script src="ressources/js/jquery.validate.min.js"></script>
    <script src="ressources/js/validation.js"></script>
</body>
</html>