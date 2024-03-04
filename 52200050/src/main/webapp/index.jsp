<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<style>
  button{
    padding:10px;
    background-color: aquamarine;
  }
  button a{
    text-decoration: none;
  }
</style>
<head>
  <title>JSP - Hello World</title>
</head>
<body>
<h2 style="background-color: aqua;padding:20px; text-align: center;">
  Welcome to our website
</h2>
<div class="header" style="background-color:gray; padding: 20px;display: flex;justify-content: space-around">
  <button >
    <a href="login.jsp">Login</a>
  </button>
  <button>
    <a href="register.jsp">Register</a>
  </button>
  <button>
    <a href="upload.jsp">Upload</a>
  </button>
</div>

</body>
</html>