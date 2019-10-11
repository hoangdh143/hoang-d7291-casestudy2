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
    <form action="/transfer/confirm" method="post">
        <h1>Transfer Summary</h1>
        <div class="error-message">${error}</div>
        <p>Destination Account: ${transferSummary.destinationAccount}</p>
        <p>Transfer Amount: ${transferSummary.transferAmount} </p>
        <p>Reference Number: ${transferSummary.referenceNumber}</p>
        <p>Balance: ${transferSummary.balance}</p>
        <div><a class="btn btn-lg btn-primary" href="${pageContext.request.contextPath}/transaction" role="button">Make
            another transaction</a></div>
        <div><a class="btn btn-lg btn-primary" href="${pageContext.request.contextPath}/logout"
                role="button">Exit</a>
        </div>
    </form>

</div>

</body>

</html>