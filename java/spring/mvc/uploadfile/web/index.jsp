<%--
  Created by IntelliJ IDEA.
  User: zhangjk
  Date: 2016/1/13
  Time: 20:57
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Upload File Request Page</title>
</head>
<body>

<h2>单文件上传</h2>
<hr />
<form method="post" action="uploadFile" enctype="multipart/form-data">
    选择文件： <input type="file" name="file" ><br />
    <input type="submit" value="上传">
</form>

<br />
<h2>多文件上传</h2>
<hr />
<form method="POST" action="uploadMultipleFile" enctype="multipart/form-data">
    文件1：<input type="file" name="file"><br />
    文件2: <input type="file" name="file"><br />
    <input type="submit" value="上传">
</form>

</body>
</html>