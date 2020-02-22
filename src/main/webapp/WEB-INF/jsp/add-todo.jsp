<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add a new Todo</title>
</head>
<body>
Hey <font color="red">${name}</font>, add a new Todo:
<br>
<form method="post">
Description: <input name="desc">
<br>
<input type="submit">
</form>
</body>
</html>