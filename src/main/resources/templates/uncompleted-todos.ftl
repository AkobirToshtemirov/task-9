<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Uncompleted Todos</title>
</head>
<body>
<h1>Uncompleted Todos:</h1>
<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Title</th>
        <th>Completed</th>
    </tr>
    </thead>
    <tbody>
    <#list todos as todo>
        <tr>
            <td>${todo.id}</td>
            <td>${todo.title}</td>
            <td>${todo.completed}</td>
        </tr>
    </#list>
    </tbody>
</table>
</body>
</html>
