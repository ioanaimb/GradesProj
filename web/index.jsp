<%--
  Created by IntelliJ IDEA.
  User: Ioana
  Date: 2019-01-27
  Time: 19:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.imbisoft.grades.Grades"%>

<html>

<head>
  <title>Student Grades</title>
</head>

<body>
Hello

<jsp:useBean id="gradesBean" class="com.imbisoft.grades.Grades" scope="session">
</jsp:useBean>

<jsp:getProperty name="gradesBean" property="availTestNames"/><br>

</body><%@ page language="java" contentType="text/html;charset=UTF-8" %>
</html>