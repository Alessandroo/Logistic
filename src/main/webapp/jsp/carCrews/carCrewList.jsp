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
    <title>Car crew List</title>
    <meta name="generator" content="Bootply" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <style type="text/css">
        .col-xs-1-5,
        .col-sm-1-5,
        .col-md-1-5,
        .col-lg-1-5 {
            position: relative;
            min-height: 1px;
            padding-right: 10px;
            padding-left: 10px;
        }

        .col-xs-1-5 {
            width: 20%;
            float: left;
        }

        @media (min-width: 768px) {
            .col-sm-1-5 {
                width: 20%;
                float: left;
            }
        }

        @media (min-width: 992px) {
            .col-md-1-5 {
                width: 20%;
                float: left;
            }
        }

        @media (min-width: 1200px) {
            .col-lg-1-5 {
                width: 20%;
                float: left;
            }
        }
    </style>
</head>

<body>
<div class="content-wrapper">
    <%@ include file="/html/header.jsp" %>
    <div class="before-footer">

        <div class="panel panel-default">
            <div class="panel-heading">Car crew List</div>
            <table class="selectable table table-striped" style="table-layout: auto">
                <thead>
                <tr>
                    <th>Driver 1</th>
                    <th>Driver 2</th>
                    <th>Truck model</th>
                </tr>
                </thead>
                <tbody>


                <c:forEach items="${entityArray}" var="carCrew">
                    <td class="unique" hidden="">${carCrew.id}</td>
                    <td><c:out value="${carCrew.drivers[0].login}(${carCrew.drivers[0].name} ${carCrew.drivers[0].last_name})" /></td>
                    <td><c:out value="${carCrew.drivers[1].login}(${carCrew.drivers[1].name} ${carCrew.drivers[1].last_name})" /></td>
                    <td><c:out value="${carCrew.truck.model} +${carCrew.truck.model} " /></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="panel-footer">
                <div class="row">

                    <div class="col-sm-1-5">
                        <a href="/carCrew?action=add">
                            <button class="btn btn-primary">Add</button>
                        </a>
                    </div>

                    <div class="col-sm-1-5">
                        <a class="editHref" href="#">
                            <button class="btn btn-primary" >Add orders</button>
                        </a>
                    </div>

                    <div class="col-sm-1-5">
                        <a class="recomendHref" href="####">
                            <button class="btn btn-primary" >Show recomendations</button>
                        </a>
                    </div>

                    <div class="col-sm-1-5">
                        <a class="deleteHref" href="##">
                            <button class="btn btn-primary">Delete</button>
                        </a>
                    </div>



                </div>
            </div>
        </div>


    </div>


    <%@ include file="/html/footer.html" %>
</div
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
</body>
</html>
