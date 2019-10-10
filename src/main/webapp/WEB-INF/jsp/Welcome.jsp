<!doctype html>
<html lang="en">
<head>
    <title>Welcome</title>
</head>

<body>
<form action="${pageContext.request.contextPath}/login" method="post">
    <table>
        <tr>
            <td>Account number</td>
            <td><input type="text" name="accountNumber"></td>
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