<!doctype html>
<html lang="en">
<head>
</head>

<body>

<p>${error}</p>
<form method="POST" action="${pageContext.request.contextPath}/file_loader" enctype="multipart/form-data">
<table>
    <tr>
        <td><label>Select a file to upload</label></td>
        <td><input type="file" name="file" /></td>
    </tr>
    <tr>
        <td><input type="submit" value="Submit" /></td>
    </tr>
</table>
</form>

</body>

</html>