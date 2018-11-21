<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

<!-- BOOTSTRAP ONLINE-->
<meta name="viewport" content="width=device-width, initial-scale=1">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!-- BOOTSTRAP ONLINE-->
<!-- BOOTSTRAP ONLINE Corregir-->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<!-- BOOTSTRAP ONLINE Corregir-->

<!-- Required meta tags-->
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="Colorlib Templates">
<meta name="author" content="Colorlib">
<meta name="keywords" content="Colorlib Templates">

<!-- Icons font CSS-->
<link href="vendor/mdi-font/css/material-design-iconic-font.min.css"
	rel="stylesheet" media="all">
<link href="vendor/font-awesome-4.7/css/font-awesome.min.css"
	rel="stylesheet" media="all">

<!-- Font special for pages-->
<link
	href="https://fonts.googleapis.com/css?family=Poppins:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Vendor CSS-->
<link href="vendor/select2/select2.min.css" rel="stylesheet" media="all">
<link href="vendor/datepicker/daterangepicker.css" rel="stylesheet"
	media="all">

<!-- Main CSS-->
<link href="css/main.css" rel="stylesheet" media="all">

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">




<title></title>
</head>
<body>
	<nav class="navbar navbar-inverse"> <!-- BEGIN: Men� Superior -->
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="home" title="Ir a la p�gina de Inicio"
				style="color: MEDIUMTURQUOISE;">OwnerRental</a>
		</div>
		<ul class="nav navbar-nav">


			<li class="dropdown"><a class="dropdown-toggle"
				data-toggle="dropdown"><span
					class="glyphicon glyphicon-chevron-down"></span> Publicaciones</a>
				<ul class="dropdown-menu">
					<li><a href="#"><span
							class="glyphicon glyphicon glyphicon-th" /></span> Mis publicaciones</a></li>
					<li><a href="#"><span class="glyphicon glyphicon-star" /></span>
							Favoritas</a></li>
					<li><a href="#"></a></li>
				</ul></li>
			<li><a href="#"><span class="glyphicon glyphicon-retweet" /></span>
					Solicitudes</a></li>
			<li><a href="#"><span class="glyphicon glyphicon-check" /></span>
					Reservas</a></li>

		</ul>
		<form class="navbar-form navbar-left" action="/action_page.php">
			<div class="input-group">
				<input type="text" class="form-control"
					placeholder="Buscar alojamiento en..." name="search">
				<div class="input-group-btn">
					<button class="btn btn-default" type="submit"
						title="Te permite realizar una b�squeda">
						<i class="glyphicon glyphicon-search"></i>
					</button>
				</div>
			</div>
		</form>
		<div class="nav navbar-nav navbar-right">
			<ul class="nav navbar-nav">
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown"><span class="glyphicon glyphicon-user"></span>
						Santiago</a>
					<ul class="dropdown-menu">
						<li><a href="#"><span class="glyphicon glyphicon-user" /></span>
								Mi Perfil</a></li>
						<li><a href="#"><span class="glyphicon glyphicon-cog" /></span>
								Configuraciones</a></li>
						<li><a href="#"><span class="glyphicon glyphicon-off" /></span>
								Salir</a></li>
					</ul></li>
		</div>
	</div>
	</nav>
	<!-- END: Men� Superior -->



	<!-- Jquery JS-->
	<script src="vendor/jquery/jquery.min.js"></script>
	<!-- Vendor JS-->
	<script src="vendor/select2/select2.min.js"></script>
	<script src="vendor/datepicker/moment.min.js"></script>
	<script src="vendor/datepicker/daterangepicker.js"></script>

	<!-- Main JS-->
	<script src="js/global.js"></script>
	<script src="js/JSValidar.js"></script>

</body>
</html>