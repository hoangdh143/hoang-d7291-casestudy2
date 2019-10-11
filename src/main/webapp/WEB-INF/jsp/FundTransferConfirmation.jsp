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

</head>

<body class="text-center">
<div class="container">
    <form action="${pageContext.request.contextPath}/transfer/confirm" method="post">
        <h1>Transfer Confirmation</h1>
        <div class="error-message">${error}</div>
        <p>Destination Account: ${transferConfirmation.destinationAccount}</p>
        <p>Transfer Amount: ${transferConfirmation.transferAmount}</p>
        <p>Reference Number: ${transferConfirmation.referenceNumber}</p>
        <div><input class="btn btn-lg btn-primary" type="submit" value="Confirm"></div>
        <div><a class="btn btn-lg btn-primary" href="${pageContext.request.contextPath}/transaction" role="button">Cancel</a>
        </div>
    </form>

</div>

</body>

</html>