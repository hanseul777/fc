<%--
  Created by IntelliJ IDEA.
  User: hanseul
  Date: 2021/08/23
  Time: 5:21 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/board/modify.do" method="post">
    <input type="text" name="bno" value="${boardDTO.bno}" readonly>
    <input type="text" name="title" value="${boardDTO.title}">
    <input type="text" name="content" value="${boardDTO.content}">
    <input type="text" name="writer" value="${boardDTO.writer}" readonly>


    <button type="submit">수정</button>
</form>
</body>
</html>
