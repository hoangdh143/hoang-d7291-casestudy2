<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!doctype html>
<html lang="en">
<head>
    <jsp:include page="Header.jsp"></jsp:include>

    <title>Transaction</title>

    <link href="${contextPath}/static/css/withdraw.css" rel="stylesheet">

    <%
        if (null == session.getAttribute("account")) {
            session.invalidate();
            response.sendRedirect("/");
        }
    %>

</head>

<body class="text-center">
<div class="select-option">
    <h2>Withdraw</h2>
    <div class="error-message">${error}</div>
    <p>Please select an amount to withdraw</p>
    <form action="${pageContext.request.contextPath}/withdraw" method="post">
        <input type="hidden" name="amount" value="10">
        <input class="btn btn-lg btn-primary" type="submit" value="$10"/>
    </form>

    <form action="${pageContext.request.contextPath}/withdraw" method="post">
        <input type="hidden" name="amount" value="50">
        <input class="btn btn-lg btn-primary" type="submit" value="$50"/>
    </form>

    <form action="${pageContext.request.contextPath}/withdraw" method="post">
        <input type="hidden" name="amount" value="100">
        <input class="btn btn-lg btn-primary" type="submit" value="$100"/>
    </form>

    <button class="btn btn-lg btn-primary" type="button" data-toggle="collapse" data-target="#other-amount">Other</button>

    <div class="collapse" id="other-amount">
    <form class="form-group" action="${pageContext.request.contextPath}/withdraw" method="post">
        <input class="form-control" type="number" name="amount" value="0">
        <input class="btn btn-lg btn-primary" type="submit" value="Withdraw"/>
    </form>
    </div>

</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>

</html>