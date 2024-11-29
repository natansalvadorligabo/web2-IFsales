<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="pt-br" data-theme="lofi">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/daisyui@4.12.13/dist/full.min.css" rel="stylesheet" type="text/css" />
    <script src="https://cdn.tailwindcss.com"></script>
    <title>Tabelas de Vendedores - IFSales</title>
</head>
<body>
    <div class="container mx-auto p-4 mt-8">
        <div class="stats stats-vertical shadow-md w-full">
            <h1 class="text-2xl  font-bold mb-6 mt-8">Vendedores</h1>
            <div class="overflow-x-auto">
                <table class="table table-zebra">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nome</th>
                            <th>Email</th>
                            <th>Telefone</th>
                            <th>Active</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="salesPerson" items="${salesPersons}">
                            <tr class="hover">
                                <td>${salesPerson.id}</td>
                                <td>${salesPerson.name}</td>
                                <td>${salesPerson.email}</td>
                                <td>${salesPerson.phone}</td>
                                <td>
                                    <c:choose>
                                        <c:when test = "${salesPerson.active}"><input type="checkbox" class="checkbox checkbox-success" disabled checked="checked" /></c:when>
                                        <c:otherwise><input type="checkbox" class="checkbox" disabled /></c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>
