<!doctype html>
<html lang="en">
<head>
</head>

<body>
<form action="${pageContext.request.contextPath}/fund_transfer" method="post">
    <label for="destinationAccount">Destination Account</label>
    <input type="text" name="destinationAccount" id="destinationAccount">
    <label for="amount">Transfer Amount</label>
    <input type="number" name="amount" id="amount">
    <input type="submit" value="Continue">
    <input type="button" value="Back">
</form>
</body>

</html>