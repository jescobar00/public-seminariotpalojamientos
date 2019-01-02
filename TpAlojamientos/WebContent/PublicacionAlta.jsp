<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Publicaci�n - Alta</title>
</head>
<body>
	<%@ include file="Banner.jsp"%>

	<div class="container">
		<h2>Publicaci�n</h2>
		<hr />
		<!--Horizantal line divider  <div class="page-header"/> -->
	</div>

	<div class="container" id="a_ubicacionDomicilio">
		<h4>Ubicaci�n - Zona del Domicilio</h4>

		<div class="row">
			<div class="col-md-4">
				<!-- Combos de Zona  -->
				<div class="form-group" style="">
					<form method="POST" action="PublicacionServlet">
						<label>Partido</label> <select class="form-control"
							name="cmbPartido" onchange="this.form.submit()">
							<option selected value="null">Seleccionar un partido</option>

							<c:forEach items="${listaPartidos}" var="item">
								<option value="${item.idPartido}">${item.nombre}</option>
							</c:forEach>
						</select>
						<noscript>
							<input type="submit">
						</noscript>
						<input type="hidden" name="actionPublicacion" value="cmbPartidoSubmit">
					</form>
				</div>

				<div class="form-group" style="">
					<label>Localidad</label> <select class="form-control"
						name="cmbLocalidad">
						<c:forEach items="${listaLocalidades}" var="item">
							<option value="${item.idLocalidad}">${item.nombre}</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group" style="">
					<label>Localidad</label><select class="form-control">
						<option value="value1">Text 1</option>
						<option value="value2">Text 2</option>
						<option value="value3">Text 3</option>
					</select>
				</div>
				<div class="form-group" style="">
					<label>Zona</label><select class="form-control">
						<option value="value1">Text 1</option>
						<option value="value2">Text 2</option>
						<option value="value3">Text 3</option>
					</select>
				</div>
			</div>

			<div class="col-md-4">
				<!-- Ubicaci�n en Mapa  -->
				<div class="form-group" align="center">
					<form class="form-horizontal" action="/action_page.php">
						<div class="form-group">
							<div class="col-sm-12">
								<button class="btn btn-info">Ver en Mapa</button>
							</div>
						</div>




					</form>
				</div>
			</div>
		</div>
	</div>

	<div class="container" id="a_servicios">
		<h3>Servicios - Facilidades</h3>
		<p>Seleccione los Servicios que ofrece su publicaci�n</p>
		<hr />
		<div class="row">
			<div class="col-md-4">
				<!--   -->
				<div class="col-md-6">
					<div class="form-group">
						<label class="checkbox"><input type="checkbox"
							name="fruit" value="">Checkbox 1</label>
					</div>
					<div class="form-group">
						<label class="checkbox"><input type="checkbox"
							name="fruit" value="">Checkbox 3</label>
					</div>
					<div class="form-group">
						<label class="checkbox"><input type="checkbox"
							name="fruit" value="">Checkbox 5</label>
					</div>

				</div>

				<div class="col-md-6">
					<div class="form-group">
						<label class="checkbox"><input type="checkbox"
							name="fruit" value="">Checkbox 2</label>
					</div>
					<div class="form-group">
						<label class="checkbox"><input type="checkbox"
							name="fruit" value="">Checkbox 4</label>
					</div>
					<div class="form-group">
						<label class="checkbox"><input type="checkbox"
							name="fruit" value="">Checkbox 6</label>
					</div>
				</div>
			</div>

			<div class="col-md-4">Instalaciones del edificio Gimnasio
				Pileta Sauna Parrilla Servicios del edificio Agua corriente Cocina
				Electricidad Gas natural Seguridad Telefono Mascotas; Fumadores;
				Amoblada; Desayuno; Ambientes del departamento Balcon Ba�o Baulera
				Cochera Cocina Dormitorio Jardin Living comedor Suite Vestidor</div>


			<div class="col-md-4"></div>

		</div>
	</div>
<input type="file" class="filestyle" data-buttonText="Find file">

	<div class="container" id="footer"></div>

</body>
</html>