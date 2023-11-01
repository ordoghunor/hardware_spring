<html lang="Hu">

<head>
    <title>Hardwerek</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<h1>
    Hardwerek ohim2065:
</h1>

<form method="post" action="logout">
    <input type="submit" class="logout">
</form>

<ol>
    <#list hardwares as hardware>
        <li> id:${hardware.id} name:${hardware.name} price:${hardware.price} manufacturer:${hardware.manufacturer}</li>
    </#list>
</ol>
</body>
</html>
