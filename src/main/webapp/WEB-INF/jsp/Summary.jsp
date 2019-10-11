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

    <link href="${contextPath}/static/css/transaction.css" rel="stylesheet">

</head>

<body class="text-center">
<div class="container">
    <h1>Transaction Summary</h1>
    <p>Date: <fmt:formatDate value="${transactionSummary.date}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
    <p>Withdraw: ${transactionSummary.withDraw}</p>
    <p>Balance: ${transactionSummary.balance}</p>
    <div><a class="btn btn-lg btn-primary" href="${pageContext.request.contextPath}/transaction" role="button">Make
        another Transaction</a></div>
    <div><a class="btn btn-lg btn-primary" href="${pageContext.request.contextPath}/logout" role="button">Exit</a></div>

</div>
</body>

</html>