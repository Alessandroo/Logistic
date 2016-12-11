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
			<form class="form-horizontal" role="form" method="post" action="/orders" role="form" id="form">
				<div class="form-group">
					<label for="name" class="col-xs-3 control-label">Name</label>
					<div class="col-xs-9">
						<input class="form-control" placeholder="Name" id="login" name= "name" type="text" autofocus value="<c:out value="${order.client.login}"/> ">
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-xs-3 control-label">Point A</label>
					<div class="col-xs-9">
						<input class="form-control" placeholder="point_a" id="point_a" name= "point_a" type="text" autofocus value="<c:out value="${order.road.pointBegin}"/> ">
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-xs-3 control-label">Point B</label>
					<div class="col-xs-9">
						<input class="form-control" placeholder="point_b" id="point_b" name= "point_b" type="text" autofocus value="<c:out value="${order.road.pointEnd}"/> ">
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-xs-3 control-label">Time A</label>
					<div class="col-xs-9">
						<input class="form-control" placeholder="time_a" id="time_a" name= "time_a" type="text" autofocus value="<c:out value="${order.timeTable.timeBegin}"/> ">
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-xs-3 control-label">Time B</label>
					<div class="col-xs-9">
						<input class="form-control" placeholder="time_b" id="time_b" name= "time_b" type="text" autofocus value="<c:out value="${order.timeTable.timeEnd}"/> ">
					</div>
				</div>
				<input id="id" name= "id" type="text" hidden="" autofocus value="<c:out value="${order.id}"/> ">
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