<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>


<!doctype html>
<html lang="en">
<head>
<link href="${contextPath}/static/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>

<p>${error}</p>

<form method="post" action="${pageContext.request.contextPath}/file_upload" enctype="multipart/form-data">
    <div class="form-group">
        <label for="exampleFormControlFile1">Example file input</label>
        <input type="file" class="form-control-file" id="exampleFormControlFile1">
    </div>
</form>

<form method="POST" action="${pageContext.request.contextPath}/file_upload" enctype="multipart/form-data">
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

<script src="${contextPath}/static/js/bootstrap.min.js"></script>
</body>

</html>