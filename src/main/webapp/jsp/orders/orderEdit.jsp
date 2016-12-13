<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>Title</title>
	<%--<link  type="text/css" href="css/main.css" rel="stylesheet">--%>
	<%--<link rel="stylesheet" type="text/css" href="/bootstrap/css/bootstrap.min.css">--%>
	<link rel="stylesheet" type="text/css" href="/css/style.css">
	<script src="js/jquery-3.1.1.min.js" type="text/javascript"></script>
	<script src="https://api-maps.yandex.ru/2.0/?load=package.standard&mode=debug&lang=ru-RU" type="text/javascript"></script>
</head>

<body>
<div class="content-wrapper">
	<%@ include file="/html/header.jsp" %>
	<div class="form">

		<form class="order-form" method="post" action="/order">

			<div class="form-fields">

				<div class="label"><label for="cargo">Name:</label></div>
				<input type="text" name="cargo" id="cargo" value="${order.cargo.name}" required autocomplete="off"></br>

				<div class="label"><label for="weight">Weight(kg):</label></div>
				<input type="text" pattern= "[0-9]{1,5}" name="weight" id="weight" value="${order.cargo.weight}" required autocomplete="off"></br>

				<div class="label"><label for="type">Type(max spead):</label></div>
				<select name="type" id="type" required autocomplete="off">
					<option value="Class 1">Class1 - 16km/ch</option>
					<option value="Class 2">Class2 - 40km/ch</option>
					<option value="Class 3">Class3 - 64km/ch</option>
					<option value="Class 4">Class4 - 97km/ch</option>
					<option value="Class 5">Class5 - 127km/ch</option>
				</select></br>

				<div class="label"><label for="delivery-class">Delivery class:</label></div>
				<select name="delivery" id="delivery-class" required autocomplete="off">
					<option value="Fast">Fast</option>
					<option value="Mid">Mid</option>
					<option value="Slow">Slow</option>
				</select><br>

				<div class="label"><label for="start-time">Start time:</label></div>
				<input style="width: 58%" type="datetime-local" name="time" id="start-time" required autocomplete="off" value="${order.timeTable.timeBegin}"></br>

				<div class="label"><label for="point-a">Point A:</label></div>
				<input type="text" name="point-a" id="point-a" required autocomplete="off" value="${order.road.pointBegin.x} ${order.road.pointBegin.y}"></br>

				<div c	lass="label"><label for="point-b">Point B:</label></div>
				<input type="text" name="point-b" id="point-b" required autocomplete="off" value="${order.road.pointEnd.x} ${order.road.pointEnd.y}"></br>

				<input type="hidden" name="id" id="id" value="${order.id}"></br>
				<input type="hidden" name="client" value="${sessionScope.user.id}">
				<button type="submit">Send order</button>

			</div>

			<div class="map">
				<div id="mapID" style="width: 800px; height: 500px"></div>
			</div>

		</form>

	</div>

	<script type="text/javascript">
		/* При успешной загрузке API выполняется
		 соответствующая функция */
		$(document).ready(function () {
			ymaps.ready(function () {
				myMap = new ymaps.Map("mapID", {
					center: [57.5262, 38.3061], // Углич
					zoom: 11,
					controls: ['zoomControl']
				}, {
					balloonMaxWidth: 200,
					searchControlProvider: 'yandex#search'
				});
				removeButton = new ymaps.control.Button("Сбросить");
				myMap.controls.add(removeButton, {float: 'right'});
				// Обработка события, возникающего при щелчке
				// левой кнопкой мыши в любой точке карты.
				// При возникновении такого события откроем балун.
				var count = 0;
				var placemarks = [];
				removeButton.events.add('click', function () {
					if (placemarks) {
						if (placemarks.length == 1) {
							myMap.geoObjects.remove(myPlacemark1);
						} else {
							myMap.geoObjects.remove(myPlacemark1);
							myMap.geoObjects.remove(myPlacemark2);
						}
						count = 0;
						$('#point-a').val('');
						$('#point-b').val('');
					}
				});
				var myPlacemark1, myPlacemark2;
				myMap.events.add('click', function (e) {
					if (!myMap.balloon.isOpen()) {
						var coords = e.get('coords');
						myMap.balloon.open(coords, {
							contentBody:
							'<p>Координаты: ' + [
								coords[0].toPrecision(6),
								coords[1].toPrecision(6)
							].join(', ') + '</p>'
						});
						if (count == 0) {
							$('#point-a').val(coords[0].toPrecision(6) + ' ' + coords[1].toPrecision(6));
							count++;
							myPlacemark1 = new ymaps.Placemark(coords, {
								balloonContentBody: [
									'<address>',
									'<strong>',
									coords[0].toPrecision(6),
									coords[1].toPrecision(6),
									'</strong>',
									'</address>'
								].join('')
							}, {
								preset: 'islands#redDotIcon'
							});
							myMap.geoObjects.add(myPlacemark1);
							placemarks.push(myPlacemark2);
						} else if (count == 1) {
							$('#point-b').val(coords[0].toPrecision(6) + ' ' + coords[1].toPrecision(6));
							count++;
							myPlacemark2 = new ymaps.Placemark(coords, {
								balloonContentBody: [
									'<address>',
									'<strong>',
									coords[0].toPrecision(6),
									coords[1].toPrecision(6),
									'</strong>',
									'</address>'
								].join('')
							}, {
								preset: 'islands#redDotIcon'
							});
							myMap.geoObjects.add(myPlacemark2);
							placemarks.push(myPlacemark2);
						}
					}
					else {
						myMap.balloon.close();
					}
				});
				// Обработка события, возникающего при щелчке
				// правой кнопки мыши в любой точке карты.
				// При возникновении такого события покажем всплывающую подсказку
				// в точке щелчка.
			});
		});
	</script>

	<%@ include file="/html/footer.html" %>
</div>

</body>

</html>