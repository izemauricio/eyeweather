<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html>

<head>
	<title>Eyeweather</title>
	<link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js" type="text/javascript"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		function deleteAllList() {
			var parent = document.getElementById("list");
			while (parent.firstChild) {
				parent.removeChild(parent.firstChild);
			}
		}

		function createAllList(json) {
			console.log("createAllList:")
			console.log(json)

			deleteAllList();

			json.forEach(function (val, i) {
				console.log("val:")


				console.log(val)

				if (val.weather == null) {
					console.log("weather is null")
					return;
				}

				if (val.address == null) {
					console.log("address is null")
					return;
				}

				console.log("montando card")

				var weatherid = val.cardid;
				var address = val.address.fulladdr;
				var b_lat = val.weather.lat;
				var b_lon = val.weather.lon;
				var b_temp = val.weather.temp;
				var b_time = val.dateString;
				var b_relh = val.weather.humi;
				var b_wind = val.weather.windSpeed;
				var weat = "XX"
				var fore = "oi";

				var card = document.getElementById("HiddenCard").cloneNode(true);

				card.style.display = "block";

				card.setAttribute("id", "bloco" + i);

				var cardTitle = address;
				var cardBody = "Coordinates: (" + b_lat + "," + b_lon + ")<br>Time: " + b_time + "<br>Wind: " + b_wind + " Temperature: " + b_temp + " Humidity: " + b_relh + "<br>";

				card.getElementsByClassName("panel-title pull-left")[0].innerHTML = cardTitle;
				card.getElementsByClassName("panel-body")[0].innerHTML = cardBody;
				card.getElementsByClassName("hiddenweatherid")[0].value = weatherid;
				card.getElementsByClassName("btn btn-xs btn-danger")[0].setAttribute("onClick", "delete_json(\"" + weatherid + "\")");

				console.log("adicionando card na lista")
				document.getElementById("list").appendChild(card);
			});
		}

		get_json = function () {
			var userid = $('#hiddenuserid').val();

			$.ajax({
				url: 'users/' + userid + '/locations',
				type: 'GET',
				dataType: 'json',
				data: "",
				success: function (response) {
					console.log('Sucesso! response=');
					console.log(response)
					console.log('--fim response--');
					createAllList(response);
				},
				beforeSend: function (req) {
					req.setRequestHeader("Accept", "application/json");
					req.setRequestHeader("Content-Type", "application/json");
				},

				error: function (xhr, status, error) {
				}
			});
		}

		post_json = function () {
			document.getElementById("loading").style.display = "block";

			var latitude = $('#lat').val();
			var longitude = $('#lon').val();
			var userid = $('#hiddenuserid').val();

			if (!latitude)
				latitude = "43.81";

			if (!longitude)
				longitude = "-91.23";

			$.ajax({
				url: 'users/' + userid + '/locations',
				type: 'POST',
				dataType: 'json',
				data: {
					lat: latitude,
					lon: longitude
				},
				success: function (response) {
					document.getElementById("loading").style.display = "none";
					createAllList(response);
				},
				error: function (xhr, status, error) {
					document.getElementById("loading").style.display = "none";
				}
			});


		}

		delete_json = function (weatherid) {
			var userid = $('#hiddenuserid').val();

			$.ajax({
				url: 'users/' + userid + '/locations/' + weatherid,
				type: 'DELETE',
				dataType: 'json',
				data: {},
				success: function (response) {
					createAllList(response);
				},

				error: function (xhr, status, error) {
				}
			});
		}
	</script>
	<style>
		#loading {
			display: none;
			position: fixed;
			z-index: 1000;
			top: 0;
			left: 0;
			height: 100%;
			width: 100%;
			background: rgba(255, 255, 255, .8) url('http://i.stack.imgur.com/FhHRx.gif') 50% 50% no-repeat;
		}
	</style>
</head>

<body onLoad="get_json()">
	<div class="container" id="container">
		<nav class="navbar navbar-default" style="margin-top: 10px;">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="#">EyeWeather</a>
				</div>
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav navbar-right">
						<li><a href="#">Welcome ${user.username}</a></li>
						<li><a href="/eyeweather/logout">Logout</a></li>
					</ul>
				</div>
			</div>
		</nav>

		<div class="row" style="margin-top: 10px; margin-bottom: 20px;">
			<div class="col-md-12">
				<form class="form-inline" role="form" method="get" action="#">
					<div class="form-group">
						<input style="border-radius: 0px" name="lat" id="lat" type="text" class="form-control"
							placeholder="LATITUDE" autofocus>
					</div>
					<div class="form-group">
						<input style="border-radius: 0px" name="lon" id="lon" type="text" class="form-control"
							placeholder="LONGITUDE">
					</div>
					<input type="hidden" id="hiddenuserid" value="${user.id}">
					<button style="border-radius: 0px" type="button" class="btn btn-success" onClick="post_json()">Get
						card!</button>
					<button style="border-radius: 0px" type="button" class="btn btn-success"
						onClick="get_json()">Refresh list!</button>
				</form>
			</div>
		</div>

		<div id="HiddenCard" class="row" style="margin-top: 10px; display: none;">
			<div class="col-md-12">
				<div class="panel panel-default">
					<div class="panel-heading clearfix">
						<h3 class="panel-title pull-left" style="padding-top: 5px; font-weight: bold;">Head</h3>
						<div class="btn-group pull-right">
							<input type="hidden" class="hiddenweatherid" id="hiddenweatherid" value=""> <a
								class="btn btn-xs btn-danger" href="#">x</a>
						</div>
					</div>
					<div class="panel-body" id="corpo">Body</div>
				</div>
			</div>
		</div>
		<div id="list"></div>
	</div>
	<div id="loading"></div>
</body>

</html>