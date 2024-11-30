<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>
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
<div class="drawer lg:drawer-open">
    <input id="my-drawer-2" type="checkbox" class="drawer-toggle" />

    <div class="drawer-content flex flex-col items-center">
        <jsp:include page="/components/navbar.jsp">
            <jsp:param name="title" value="Tabelas de Vendedores"/>
        </jsp:include>

            <div class="container mx-auto p-4 mt-16">
                <h1 class="text-2xl font-bold mb-6 mt-8 ml-8">Vendedores</h1>

                <div class="stats stats-vertical shadow-md w-full">
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
                                    <td class="p-1">
                                        <button onclick="document.getElementById('${salesPerson.id}').showModal()" class="btn btn-outline btn-error w-28">
                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash-fill" viewBox="0 0 16 16">
                                                <path d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5M8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5m3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0"></path>
                                            </svg>
                                            Deletar
                                        </button>
                                    </td>

                                    <dialog id="${salesPerson.id}" class="modal">
                                        <div class="modal-box">
                                            <h3 class="text-lg font-bold">Deletar</h3>
                                            <p class="py-4">Tem certeza que deseja deletar ${salesPerson.name}?</p>

                                            <div class="modal-body flex flex-row justify-center gap-3">
                                                <a class="btn btn-error" href="${pageContext.request.contextPath}/redirect?action=deleteSalesPerson&id=${salesPerson.id}">
                                                    Deletar
                                                </a>

                                                <form method="dialog">
                                                    <button class="btn">Cancelar</button>
                                                </form>
                                            </div>
                                        </div>
                                    </dialog>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
    </div>

    <jsp:include page="/components/sidebar.jsp" />
</body>
</html>