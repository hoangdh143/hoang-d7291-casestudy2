<!doctype html>
<html lang="en">
<head>
</head>

<body>
    <div>Please choose withdraw amount</div>
    <form action="/withdraw">
    <input type="radio" name="amount" value="10"/> $10
    <input type="radio" name="amount" value="50"/> $50
    <input type="radio" name="amount" value="100"> $100
    <input type="number" /> Other
        <input type="submit" value="Submit">
        <input type="submit" value="Cancel">
    </form>
</body>

</html>