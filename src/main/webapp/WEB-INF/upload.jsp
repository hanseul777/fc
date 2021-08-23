<%--
  Created by IntelliJ IDEA.
  User: hanseul
  Date: 2021/08/20
  Time: 2:40 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/upload" method="post" enctype="multipart/form-data">
    <!--파일첨부는 꼭 post로! enctype이 multipart/form-data로 설정해야 파일업로드가 가능  -->
    <input type="text" name="title" value="test....">
    <input type="file" name="uploadFiles" multiple><!--multiple : 여러개의 파일을 첨부 가능 -->
    <button type="submit">SUBMIT</button>
</form>
</body>
</html>
