<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!doctype html>
<html lang="en">
<head>
    <jsp:include page="Header.jsp"></jsp:include>

    <title>Transaction</title>

    <link href="${contextPath}/static/css/transaction-history.css" rel="stylesheet">

    <%
        if (null == session.getAttribute("account")) {
            session.invalidate();
            response.sendRedirect("/");
        }
    %>

</head>

<body class="text-center">
<div class="container">
    <h2>Welcome ${username}</h2>

    <form action="${pageContext.request.contextPath}/transaction_history" method="post">
        <label for="date">Find By Date</label>
        <input type="date" id="date" name="date">
        <input type="submit" value="Find">
    </form>
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
                <td>&dollar;${trans.debit}</td>
                <td>&dollar;${trans.credit}</td>
                <td>&dollar;${trans.balance}</td>
<%--                <td><fmt:formatDate value="${trans.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>--%>
            <td><javatime:format value="${trans.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            </tr>
        </c:forEach>
    </table>
    <div><a class="btn btn-lg btn-primary" href="${pageContext.request.contextPath}/transaction" role="button">Make
        another transaction</a></div>
    <div><a class="btn btn-lg btn-primary" href="${pageContext.request.contextPath}/logout"
            role="button">Exit</a></div>
</div>

<script>
    if ( window.history.replaceState ) {
        window.history.replaceState( null, null, window.location.href );
    }
</script>
</body>

</html>