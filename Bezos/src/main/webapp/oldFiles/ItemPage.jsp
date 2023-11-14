<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link rel="stylesheet" href="styles/SearchStyle.css">
<title>Item Page</title>
<style>
/* CSS Reset */
body, h1, p, form, button, input {
	margin: 0;
	padding: 0;
	border: 0;
}

form {
	width: 100%;
	margin: 0;
	box-sizing: border-box;
	/* Include padding and border in the width */
	min-width: 100%;
	min-height: 100%;
}

body {
	font-family: Arial, sans-serif;
	display: flex;
	flex-direction: column;
	min-height: 100vh;
	margin: 0;
}

header {
	background-color: #ffffff;
	color: rgb(0, 0, 0);
	text-align: center;
	font-size: 24px;
	font-family: 'Jockey One', sans-serif;
	padding: 10px;
}

header h2 {
	position: absolute;
	top: 0px;
	right: 0%;
}

.flex-container {
	display: flex;
	flex: 1;
	min-height: 100vh;
}

.left, .right {
	flex: 1;
	padding: 20px;
	border: 1px solid #ccc;
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	background-color: #2B67F6;
}

.left h1, .right label, .right input[type="submit"] {
	color: white;
}

.left h1, h2 {
	font-size: 30px;
	font-family: 'Epilogue';
}

.right label {
	font-weight: bold;
	margin-bottom: 5px;
}

form {
	width: 100%;
	max-width: 600px;
	margin: 0 auto;
}

input[type="text"], input[type="number"], select {
	width: 100%;
	padding: 10px;
	margin: 8px 0;
	border: 1px solid #ccc;
	border-radius: 5px;
}

input[type="submit"] {
	background-color: #000000;
	color: white;
	padding: 10px 15px;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	margin-top: 15px;
}

input[type="submit"]:hover {
	background-color: #333333;
}

.back-button {
	background-color: #000000;
	color: white;
	padding: 10px 15px;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	position: absolute;
	top: 10px;
	left: 10px;
}
</style>
</head>
<body>
	<header>
		<h1>BEZOS</h1>
		<button onclick="window.location.href='Search.html'"
			class="back-button">BACK</button>
		<h2>Item Page</h2>
	</header>
	<form action="ItemServlet" method="post" autocomplete="off">

		<div class="flex-container">
			<div class="left">
				<h1>Item Information</h1>
				<label for="itemName">Item Name</label>
				<h2 id="itemName">${item.getName()}</h2>

				<label for="price">Price</label>
				<h2 id="price">${item.getCost()}</h2>

				<label for="endTime">End Date/Time for auction:</label>
				<h2 id="endName">${item.getDate()}</h2>

			</div>
			<div class="right">

				<input type="submit" value="Bid On Item">
			</div>

		</div>
	</form>
</body>
</html>