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
    <title>Cadastro de Região - IFSales</title>
  </head>
  <body>
    <div class="drawer lg:drawer-open">
      <input id="my-drawer-2" type="checkbox" class="drawer-toggle" />
      <div class="drawer-content flex flex-col items-center">
        <jsp:include page="/components/navbar.jsp">
          <jsp:param name="title" value="Formulário Região" />
        </jsp:include>
        <main class="flex flex-col justify-center flex-1 w-full overflow-y-auto px-6 mt-8 lg:mt-0">
          <div class="sm:mx-auto sm:w-full sm:max-w-sm">
            <form id="form1" action="${pageContext.request.contextPath}/redirect?action=saveRegion" method="post" class="space-y-6">
              <c:choose>
                <c:when test="${region == null}">
                  <input type="hidden" name="id" value="0">
                </c:when>
                <c:when test="${region != null}">
                  <input type="hidden" name="id" value="${region.id}">
                </c:when>
              </c:choose>

              <div>
                <label for="name" class="font-semibold">Nome<span class="text-error">*</span></label>
                <c:choose>
                  <c:when test="${region == null}">
                    <input type="text" id="name" name="name" required class="input input-bordered w-full mt-2">
                  </c:when>
                  <c:when test="${region != null}">
                    <input type="text" id="name" name="name" required class="input input-bordered w-full mt-2" value="${region.name}">
                  </c:when>
                </c:choose>
              </div>
              <span id="error-name" class="text-error hidden"></span>

              <div>
                <label for="city" class="font-semibold">Cidade<span class="text-error">*</span></label>
                <c:choose>
                  <c:when test="${region == null}">
                    <input type="text" id="city" name="city" required class="input input-bordered w-full mt-2">
                  </c:when>
                  <c:when test="${region != null}">
                    <input type="text" id="city" name="city" required class="input input-bordered w-full mt-2" value="${region.city}">
                  </c:when>
                </c:choose>
              </div>
              <span id="error-city" class="text-error hidden"></span>

              <div>
                <label for="state" class="font-semibold">Estado<span class="text-error">*</span></label>
                <c:choose>
                  <c:when test="${region == null}">
                    <input type="text" id="state" name="state" required class="input input-bordered w-full mt-2">
                  </c:when>
                  <c:when test="${region != null}">
                    <input type="text" id="state" name="state" required class="input input-bordered w-full mt-2" value="${region.state}">
                  </c:when>
                </c:choose>
              </div>
              <span id="error-state" class="text-error hidden"></span>

              <div class="space-y-2">
                <button type="submit" class="btn btn-primary btn-block">
                  <c:choose>
                    <c:when test="${region == null}">
                      Cadastrar
                    </c:when>
                    <c:when test="${region != null}">
                      Atualizar
                    </c:when>
                  </c:choose>
                </button>

                <a href="${pageContext.request.contextPath}/redirect?action=listRegions" class="btn btn-outline btn-block">
                  Voltar
                </a>
              </div>
            </form>
          </div>
        </main>
      </div>

      <jsp:include page="/components/sidebar.jsp" />
    </div>

    <div class="fixed bottom-2 left-2 z-40">
      <c:if test="${result == 'registerError' || result == 'updateError'}">
        <div class="alert alert-error">
          <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 shrink-0 stroke-current" fill="none" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
          </svg>
          <span>Ocorreu um erro, tente novamente.</span>
        </div>
      </c:if>
    </div>

    <script src="${pageContext.request.contextPath}/scripts/autoRemoveAlerts.js"></script>
    <script defer src="${pageContext.request.contextPath}/scripts/validateForm.js"></script>
  </body>
</html>