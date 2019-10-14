<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!doctype html>
<html lang="en">
<head>
    <jsp:include page="Header.jsp"></jsp:include>

    <title>Transaction</title>

    <link href="${contextPath}/static/css/fund-transfer.css" rel="stylesheet">

    <%
        if (null == session.getAttribute("account")) {
            session.invalidate();
            response.sendRedirect("/");
        }
    %>

</head>

<body class="text-center">

<form class="form-group" action="${pageContext.request.contextPath}/transfer" method="post">

    <h1 class="h3 mb-3 font-weight-normal">Fund Transfer</h1>
    <div class="error-message">${error}</div>

    <label for="destinationAccount" class="form-label">Destination Account</label>
    <input type="text" class="form-control" name="destinationAccount" id="destinationAccount">

    <label for="amount" class="form-label">Transfer Amount</label>
    <input type="number" class="form-control" name="amount" id="amount">

    <a class="btn btn-lg btn-primary" href="${pageContext.request.contextPath}/transaction">Back</a>
    <input class="btn btn-lg btn-primary" type="submit" value="Continue"/>
</form>

<script>
    if ( window.history.replaceState ) {
        window.history.replaceState( null, null, window.location.href );
    }
</script>

</body>

</html>