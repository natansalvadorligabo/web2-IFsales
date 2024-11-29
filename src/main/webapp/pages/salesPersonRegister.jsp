<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="pt-br" data-theme="lofi">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link href="https://cdn.jsdelivr.net/npm/daisyui@4.12.13/dist/full.min.css" rel="stylesheet" type="text/css" />
  <script src="https://cdn.tailwindcss.com"></script>
  <title>Cadastro de Vendedor - IFSales</title>
</head>
<body class="min-h-screen bg-base-200">
<div class="flex min-h-full flex-col justify-center px-6 py-12 lg:px-8">
  <div class="sm:mx-auto sm:w-full sm:max-w-sm">
    <h2 class="mt-16 mb-4 text-2xl font-bold text-center text-secondary">Informações cadastrais</h2>
  </div>

  <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
    <form action="${pageContext.request.contextPath}/ControllerServlet" method="post" class="space-y-6">

      <c:choose>
        <c:when test="${salesPerson == null}">
          <input type="hidden" name="id" value="0">
        </c:when>
        <c:when test="${salesPerson != null}">
          <input type="hidden" name="id" value="${salesPerson.id}">
        </c:when>
      </c:choose>

      <div>
        <label for="name" class="font-semibold">Nome<span class="text-error">*</span></label>

        <c:choose>
          <c:when test="${salesPerson == null}">
            <input type="text" id="name" name="name" required class="input input-bordered w-full mt-2">
          </c:when>
          <c:when test="${salesPerson != null}">
            <input type="text" id="name" name="name" required class="input input-bordered w-full mt-2" value="${salesPerson.name}">
          </c:when>
        </c:choose>
      </div>
      <span id="error-name" class="text-error hidden"></span>

      <div>
        <label for="email" class="font-semibold">E-mail<span class="text-error">*</span></label>

        <c:choose>
          <c:when test="${salesPerson == null}">
            <input type="email" id="email" name="email" required class="input input-bordered w-full mt-2">
          </c:when>
          <c:when test="${salesPerson != null}">
            <input type="email" id="email" name="email" required class="input input-bordered w-full mt-2" value="${salesPerson.email}">
          </c:when>
        </c:choose>
      </div>
      <span id="error-email" class="text-error hidden"></span>

      <div>
        <label for="phone" class="font-semibold">Telefone<span class="text-error">*</span></label>

        <c:choose>
          <c:when test="${salesPerson == null}">
            <input type="tel" id="phone" name="phone" required class="input input-bordered w-full mt-2" pattern="^\(\d{1,2}\) \d{4,5}-\d{4}$"
                   placeholder="(12) 34567-8910">
          </c:when>
          <c:when test="${salesPerson != null}">
            <input type="tel" id="phone" name="phone" required class="input input-bordered w-full mt-2" pattern="^\(\d{1,2}\) \d{4,5}-\d{4}$"
                   placeholder="(12) 34567-8910" value="${salesPerson.phone}">
          </c:when>
        </c:choose>
      </div>
      <span id="error-phone" class="text-error hidden"></span>

      <div class="flex items-center">
        <c:choose>
          <c:when test="${salesPerson == null}">
            <input type="checkbox" id="active" name="active" class="checkbox checkbox-bordered">
          </c:when>
          <c:when test="${salesPerson != null}">
            <input type="checkbox" id="active" name="active" class="checkbox checkbox-bordered" <c:if test="${salesPerson.active}">checked</c:if>>
          </c:when>
        </c:choose>

        <label for="active" class="ml-2 font-semibold">Ativo</label>
      </div>

      <div class="space-y-2">
        <button type="submit" name="action" value="saveSalesPerson" class="btn btn-primary btn-block">
          <c:choose>
            <c:when test="${salesPerson == null}">
              Cadastrar
            </c:when>
            <c:when test="${salesPerson != null}">
              Atualizar
            </c:when>
          </c:choose>
        </button>

        <a href="${pageContext.request.contextPath}/pages/home.jsp" class="btn btn-outline btn-block">
          Voltar
        </a>
      </div>
    </form>
  </div>
</div>

<script defer src="${pageContext.request.contextPath}/scripts/validateSalesPersonRegister.js"></script>

</body>
</html>
