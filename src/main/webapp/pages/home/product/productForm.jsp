<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="pt-br" data-theme="lofi">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/daisyui@4.12.13/dist/full.min.css" rel="stylesheet" type="text/css"/>
    <script src="https://cdn.tailwindcss.com"></script>
    <title>Cadastro de Produto - IFSales</title>
</head>
<body>
<div class="drawer lg:drawer-open">
    <input id="my-drawer-2" type="checkbox" class="drawer-toggle"/>
    <div class="drawer-content flex flex-col items-center">
        <jsp:include page="/components/navbar.jsp">
            <jsp:param name="title" value="Formulário Produto"/>
        </jsp:include>
        <main class="flex flex-col justify-center flex-1 w-full overflow-y-auto px-6 mt-8 lg:mt-0">
            <div class="sm:mx-auto sm:w-full sm:max-w-sm">
                <form id="form1" action="${pageContext.request.contextPath}/redirect" method="post" class="space-y-6">

                    <c:choose>
                        <c:when test="${product == null}">
                            <input type="hidden" name="id" value="0">
                        </c:when>
                        <c:when test="${product != null}">
                            <input type="hidden" name="id" value="${product.id}">
                        </c:when>
                    </c:choose>

                    <div>
                        <label for="brand" class="font-semibold">Marca<span class="text-error">*</span></label>
                        <c:choose>
                            <c:when test="${product == null}">
                                <input type="text" id="brand" name="brand" required
                                       class="input input-bordered w-full mt-2">
                            </c:when>
                            <c:when test="${product != null}">
                                <input type="text" id="brand" name="brand" required
                                       class="input input-bordered w-full mt-2" value="${product.brand}">
                            </c:when>
                        </c:choose>
                    </div>
                    <span id="error-name" class="text-error hidden"></span>

                    <div>
                        <label for="model" class="font-semibold">Modelo<span class="text-error">*</span></label>
                        <c:choose>
                            <c:when test="${product == null}">
                                <input type="text" id="model" name="model" required
                                       class="input input-bordered w-full mt-2">
                            </c:when>
                            <c:when test="${product != null}">
                                <input type="text" id="model" name="model" required
                                       class="input input-bordered w-full mt-2" value="${product.model}">
                            </c:when>
                        </c:choose>
                    </div>
                    <span id="error-name" class="text-error hidden"></span>

                    <div>
                        <label for="modelYear" class="font-semibold">Ano do Modelo<span class="text-error">*</span></label>
                        <c:choose>
                            <c:when test="${product == null}">
                                <input type="number" min="1920" max="${Calendar.getInstance().get(Calendar.YEAR)}" id="modelYear" name="modelYear" required
                                       class="input input-bordered w-full mt-2">
                            </c:when>
                            <c:when test="${product != null}">
                                <input type="number" id="modelYear" name="modelYear" required
                                       class="input input-bordered w-full mt-2" value="${product.modelYear}">
                            </c:when>
                        </c:choose>
                    </div>
                    <span id="error-name" class="text-error hidden"></span>

                    <div>
                        <label for="price" class="font-semibold">Preço<span class="text-error">*</span></label>
                        <c:choose>
                            <c:when test="${product == null}">
                                <input type="number" min="1" step="0.1" id="price" name="price" required
                                       class="input input-bordered w-full mt-2">
                            </c:when>
                            <c:when test="${product != null}">
                                <input type="number" id="price" name="price" required
                                       class="input input-bordered w-full mt-2" value="${product.price}">
                            </c:when>
                        </c:choose>
                    </div>
                    <span id="error-name" class="text-error hidden"></span>

                    <div>
                        <label for="category" class="font-semibold">Categoria<span class="text-error">*</span></label>
                        <select id="category" name="category" class="select select-bordered w-full mt-2">
                            <c:forEach var="category" items="${categories}">
                                <option value="${category.id}"
                                        <c:if test="${product != null and product.category.id == category.id}">selected</c:if>>
                                        ${category.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <span id="error-name" class="text-error hidden"></span>

                    <div class="space-y-2">
                        <button type="submit" name="action" value="saveProduct" class="btn btn-primary btn-block">
                            <c:choose>
                                <c:when test="${product == null}">
                                    Cadastrar
                                </c:when>
                                <c:when test="${product != null}">
                                    Atualizar
                                </c:when>
                            </c:choose>
                        </button>

                        <a href="${pageContext.request.contextPath}/pages/home/product/productTable.jsp" class="btn btn-outline btn-block">
                            Voltar
                        </a>
                    </div>
                </form>
            </div>
        </main>
    </div>

    <jsp:include page="/components/sidebar.jsp"/>
</div>

<jsp:include page="/components/sidebar.jsp"/>
</div>

<script defer src="${pageContext.request.contextPath}/scripts/validateSalespersonRegister.js"></script>
</body>
</html>