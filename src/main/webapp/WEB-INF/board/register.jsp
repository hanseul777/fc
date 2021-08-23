<%--
  Created by IntelliJ IDEA.
  User: hanseul
  Date: 2021/08/23
  Time: 2:21 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Board Register Page</h1>

<form action="/board/register.do" method="post">
    <input type="text" name="title" value="제목">
    <textarea name="content">내용</textarea>
    <input type="text" name="writer" value="작성자">

    <button type="submit">등록</button>

</form>
</body>
</html>
