<!doctype html>
<html lang="en">
<head>
    <title>Welcome</title>
</head>

<body>
<form action="${pageContext.request.contextPath}/login" method="post">
    <table style="width: 50%">
        <tr>
            <td>Username</td>
            <td><input type="text" name="username"></td>
        </tr>
        <tr>
            <td>PIN</td>
            <td><input type="password" name="pin"></td>
        </tr>
    </table>
    <input type="submit" value="login">
</form>
</body>

</html>