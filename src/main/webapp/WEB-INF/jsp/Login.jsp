<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!doctype html>
<html lang="en">
<head>
    <jsp:include page="Header.jsp"></jsp:include>

    <title>Signin</title>

    <!-- Bootstrap core CSS -->
    <link href="${contextPath}/static/css/bootstrap-material-design.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${contextPath}/static/css/signin.css" rel="stylesheet">
</head>

<body class="text-center">
<form class="form-signin" method="post" action="${contextPath}/login">
    <img class="mb-4" src="${contextPath}/static/img/mitrais-logo.JPG" alt="" width="72" height="72">
    <h1 class="h3 mb-3 font-weight-normal">ATM Access</h1>
    <div class="error-message">${error}</div>

    <label for="inputEmail" class="sr-only">Account Number</label>
    <input type="text" name="accountNumber" id="inputEmail" class="form-control" placeholder="Account Number" required autofocus>

    <label for="pin" class="sr-only">PIN</label>
    <input type="password" name="pin" id="pin" class="form-control" placeholder="PIN" required>

    <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
    <p class="file-import">Or import accounts to database <a href="${pageContext.request.contextPath}/file_upload">here</a></p>
    <p class="mt-5 mb-3 text-muted">&copy; Mitrais 2019</p>
</form>

<script>
    if ( window.history.replaceState ) {
        window.history.replaceState( null, null, window.location.href );
    }
</script>
</body>
</html>