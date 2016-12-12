﻿<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
                    <th>Point begin</th>
                    <th>Point end</th>
                    <th>Truck number</th>
                    <th>orders id</th>
                </tr>
                </thead>
                <tbody>



                </tbody>
            </table>
            <div class="panel-footer">
                <div class="row">

                    <div class="col-sm-4">
                        <a href="/cardCrew?action=add">
                            <button class="btn btn-primary">Add</button>
                        </a>
                    </div>

                    <div class="col-sm-4">
                        <a class="editHref" href="#">
                            <button class="btn btn-primary">Edit</button>
                        </a>
                    </div>

                    <div class="col-sm-4">
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