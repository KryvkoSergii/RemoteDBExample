<%@page import="java.util.Date"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="index.css">

<title>Добавить новую запись:</title>
</head>
<body id="body">
<jsp:useBean id="bean" class="lesson5.servlet.SomeBean" scope="request"/>
<jsp:setProperty name="bean" property="version" value="1.0"/>

<p> <jsp:getProperty name="bean" property="version"/> </p>

Дата: <jsp:getProperty property="date" name="bean"/>
<%out.println(session.getId()); %>
     
<p>Добавить новую запись:</p>
<div id="mystyle">
<form action = "TestServlet" method = "POST">
<table>

<tr>
<td>Введите имя:</td>
<td> <input id="input" type="text" name = "name" size="30" required title="заполните поле"/> </td>
</tr>
<tr>
<td>Введите фамилию:</td>
<td> <input id="input" type=text name = "surname" size="30" required title="заполните поле"/> </td>
</tr>
<tr>
<td>Введите город:</td>
<td> <input id="input" type=text name = "city" size="30"> </td>
</tr>

<tr>
<td><input id="button" type="submit" name="add-user" value="добавить" class="button"> </td>
<td><input id="button" type="submit" name="some-where" value="куда нибудь" class="button"> </td>
</tr>
</table>
</form>
</div>
<%request.getRequestDispatcher("/message.jsp"); %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script type="text/javascript" src="main.js"></script>
</body>
</html>