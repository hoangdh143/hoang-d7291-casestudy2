<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!doctype html>
<html lang="en">
<head>
    <jsp:include page="Header.jsp"></jsp:include>

    <title>Transaction</title>

    <link href="${contextPath}/static/css/transaction-history.css" rel="stylesheet">


</head>

<body class="text-center">
<div class="container">
    <h2>Welcome ${userName}</h2>
    <table class="table table-bordered history-table">
        <tr>
            <th scope="col">Transaction Code</th>
            <th scope="col">Description</th>
            <th scope="col">Debit</th>
            <th scope="col">Credit</th>
            <th scope="col">Balance</th>
            <th scope="col">Created At</th>
        </tr>
        <c:forEach items="${transactionHistoryList}" var="trans">
            <tr>
                <td>${trans.transactionCode}</td>
                <td>${trans.description}</td>
                <td>${trans.debit}</td>
                <td>${trans.credit}</td>
                <td>${trans.balance}</td>
                <td><fmt:formatDate value="${trans.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            </tr>
        </c:forEach>
    </table>
    <div><a class="btn btn-lg btn-primary" href="${pageContext.request.contextPath}/transaction" role="button">Make
        another transaction</a></div>
    <div><a class="btn btn-lg btn-primary" href="${pageContext.request.contextPath}/logout"
            role="button">Exit</a></div>
</div>
</body>

</html>