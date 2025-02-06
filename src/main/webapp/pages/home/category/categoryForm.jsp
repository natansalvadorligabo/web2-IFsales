<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="pt-br" data-theme="lofi">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/daisyui@4.12.13/dist/full.min.css" rel="stylesheet" type="text/css" />
    <script src="https://cdn.tailwindcss.com"></script>
    <title>Cadastro de Categoria - IFSales</title>
</head>
<body>
<div class="drawer lg:drawer-open">
    <label for="my-drawer-2"></label>
    <input id="my-drawer-2" type="checkbox" class="drawer-toggle" />
    <div class="drawer-content flex flex-col items-center">
        <jsp:include page="/components/navbar.jsp">
            <jsp:param name="title" value="Formulário Categoria" />
        </jsp:include>
        <main class="flex flex-col justify-center flex-1 w-full overflow-y-auto px-6 mt-8 lg:mt-0">
            <div class="sm:mx-auto sm:w-full sm:max-w-sm">
                <form id="form1" action="${pageContext.request.contextPath}/redirect?action=saveCategory" method="post" class="space-y-6">
                    <c:choose>
                        <c:when test="${category == null}">
                            <input type="hidden" name="id" value="0">
                        </c:when>
                        <c:when test="${category != null}">
                            <input type="hidden" name="id" value="${category.id}">
                        </c:when>
                    </c:choose>

                    <div>
                        <label for="name" class="font-semibold">Nome<span class="text-error">*</span></label>
                        <input type="text" id="name" name="name" required class="input input-bordered w-full mt-2" value="${category.name}">
                    </div>
                    <span id="error-name" class="text-error hidden"></span>

                    <div>
                        <label for="description" class="font-semibold">Descrição<span class="text-error">*</span></label>
                        <input type="text" id="description" name="description" required class="input input-bordered w-full mt-2" value="${category.description}">
                    </div>
                    <span id="error-description" class="text-error hidden"></span>

                    <div class="space-y-2">
                        <button type="submit" name="action" value="saveCategory" class="btn btn-primary btn-block">
                            <c:choose>
                                <c:when test="${category == null}">
                                    Cadastrar
                                </c:when>
                                <c:when test="${category != null}">
                                    Atualizar
                                </c:when>
                            </c:choose>
                        </button>

                        <a href="${pageContext.request.contextPath}/redirect?action=listCategories" class="btn btn-outline btn-block">
                            Voltar
                        </a>
                    </div>
                </form>
            </div>
        </main>
    </div>

    <jsp:include page="/components/sidebar.jsp" />
</div>

</body>
</html>