<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!doctype html>
<html lang="en">
<head>
    <jsp:include page="Header.jsp"></jsp:include>

    <title>Transaction</title>

    <link href="${contextPath}/static/css/transaction.css" rel="stylesheet">
    <%
        if (null == session.getAttribute("account")) {
            session.invalidate();
            response.sendRedirect("/");
        }
    %>

</head>

<body class="text-center">
<div class="select-option">
    <h2>Welcome ${userName}</h2>
    <p>Please pick an option</p>
    <div><a class="btn btn-lg btn-primary" href="${pageContext.request.contextPath}/withdraw" role="button">Withdraw</a>
    </div>
    <div><a class="btn btn-lg btn-primary" href="${pageContext.request.contextPath}/transfer" role="button">Transfer</a>
    </div>
    <div><a class="btn btn-lg btn-primary" href="${pageContext.request.contextPath}/transaction_history" role="button">View
        Transaction History</a></div>
    <div><a class="btn btn-lg btn-primary" href="${pageContext.request.contextPath}/logout"
            role="button">Exit</a></div>
</div>
</body>

</html>