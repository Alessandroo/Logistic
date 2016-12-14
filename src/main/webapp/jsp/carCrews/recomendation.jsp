<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script type="text/javascript" src="/js/selectEntity.js"></script>
    <script type="text/javascript" src="/js/carCrewPages/carCrewList.js"></script>

    <link rel="stylesheet" type="text/css" href="/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <title>Recomended path</title>
    <meta name="generator" content="Bootply" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

</head>

<body>
<div class="content-wrapper">
    <%@ include file="/html/header.jsp" %>
    <div class="before-footer">

        <div class="panel panel-default">
            <div class="panel-heading">Recomended path</div>
            <table class="selectable table table-striped" style="table-layout: auto">
                <thead>
                <tr>
                    <th>Client</th>
                    <th>Cargo</th>
                    <th>Point A</th>
                    <th>Point B</th>
                </tr>
                </thead>
                <tbody>


                <c:forEach items="${entityArray}" var="route">
                    <td class="unique" hidden="">${route.id}</td>
                    <td><c:out value="${route.order.client.login}" /></td>
                    <td><c:out value="${route.order.cargo.name}" /></td>
                    <td><c:out value="${route.road.pointBegin.y} ${route.road.pointBegin.x}" /></td>
                    <td><c:out value="${route.road.pointEnd.y} ${route.road.pointEnd.x}" /></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>


    </div>


    <%@ include file="/html/footer.html" %>
</div
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
</body>
</html>
