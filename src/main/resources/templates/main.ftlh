<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

<table class="iksweb">
    <tbody>
    <tr>
        <td rowspan="3"></td>
        <td colspan="4">fact</td>
        <td colspan="4">forecast</td>
    </tr>
    <tr>
        <td colspan="2">Qliq</td>
        <td colspan="2">Qoil</td>
        <td colspan="2">Qliq</td>
        <td colspan="2">Qoil</td>
    </tr>
    <tr>
        <#list data as d>
            <td>${d}</td>
        </#list>
        <#list data as d>
            <td>${d}</td>
        </#list>
        <#list data as d>
            <td>${d}</td>
        </#list>
        <#list data as d>
            <td>${d}</td>
        </#list>
    </tr>
    <tr>
        <td>Total company1</td>
        <#list totalComp1 as total>
            <td>${total}</td>
        </#list>
    </tr>
    <tr>
        <td>Total company2</td>
        <#list totalComp2 as total>
            <td>${total}</td>
        </#list>
    </tr>
    </tbody>
</table>
</body>
</html>
