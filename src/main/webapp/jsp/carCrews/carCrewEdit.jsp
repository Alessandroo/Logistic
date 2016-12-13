<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="/css/style.css">
	<title>Edit</title>
</head>

<body>
<div class="content-wrapper">
	<%@ include file="/html/header.jsp" %>
	<div class="before-footer">
		<div class="panel panel-default">
			<div class="panel-heading">Edit</div>
			<form class="form-horizontal" role="form" method="post" action="/carCrew" role="form" id="form">
				<div class="form-group">
					<label for="driver1" class="col-xs-3 control-label">driver 1(login)</label>
					<div class="col-xs-9">
						<input class="form-control" placeholder="Name" id="driver1" name= "driver1" type="text" autofocus value="<c:out value="${carCrew.drivers[0].login}"/> ">
					</div>
				</div>
				<div class="form-group">
					<label for="driver2" class="col-xs-3 control-label">driver 2(login)</label>
					<div class="col-xs-9">
						<input class="form-control" placeholder="Name" id="driver2" name= "driver2" type="text" autofocus value="<c:out value="${carCrew.drivers[1].login}"/> ">
					</div>
				</div>
				<input type="hidden" name="id" id="id" value="${carCrew.id}"></br>
				<select name="truck" id="truck" required autocomplete="off">
					<option value="ISUZU ELF 3.5">ISUZU ELF 3.5</option>
					<option value="ISUZU ELF 5.2">ISUZU ELF 5.2</option>
					<option value="ISUZU ELF 7.5">ISUZU ELF 7.5</option>
					<option value="ISUZU ELF 9.5">ISUZU ELF 9.5</option>
					<option value="ISUZU FORWARD 12">ISUZU FORWARD 12</option>
					<option value="ISUZU FORWARD 18">ISUZU FORWARD 18</option>
					<option value="ISUZU GIGA 6x4">ISUZU GIGA 6x4</option>
				</select></br>
				<div class="form-group">
					<div class="col-xs-9">
						<input type="submit" class="btn btn-primary btn-lg btn-block" value="Yes">
					</div>
				</div>
			</form>
		</div>
	</div>
	<%@ include file="/html/footer.html" %>
</div>

</body>

</html>