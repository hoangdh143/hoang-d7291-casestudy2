<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!doctype html>
<html lang="en">
<head>
    <%--    <meta charset="utf-8">--%>
    <%--    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">--%>
    <%--    <meta name="description" content="">--%>
    <%--    <meta name="author" content="">--%>
    <jsp:include page="Header.jsp"></jsp:include>

    <title>Signin</title>

    <!-- Bootstrap core CSS -->
    <link href="${contextPath}/static/css/bootstrap-material-design.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${contextPath}/static/css/file-upload.css" rel="stylesheet">
</head>

<body class="text-center">

<form class="form-group" method="post" action="${contextPath}/file_upload" enctype="multipart/form-data">
    <h1 class="h3 mb-3 font-weight-normal">File Upload</h1>
    <div class="error-message">${error}</div>

    <input type="file" name="file" id="fileUpload" class="form-control" required autofocus>

    <button class="btn btn-lg btn-primary btn-block" type="submit">Upload</button>
</form>

<script>
    if ( window.history.replaceState ) {
        window.history.replaceState( null, null, window.location.href );
    }
</script>

<script src="${contextPath}/static/js/bootstrap.min.js"></script>
</body>

</html>