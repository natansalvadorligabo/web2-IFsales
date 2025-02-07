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
                <input type="text" id="name" name="name" required class="input input-bordered w-full mt-2" value="${region.name}">
              </div>
              <span id="error-name" class="text-error hidden"></span>

              <div>
                <label for="city" class="font-semibold">Cidade<span class="text-error">*</span></label>
                <input type="text" id="city" name="city" required class="input input-bordered w-full mt-2" value="${region.city}">
              </div>
              <span id="error-city" class="text-error hidden"></span>

              <div>
                <label for="state" class="font-semibold">Estado<span class="text-error">*</span></label>
                <input type="text" id="state" name="state" required class="input input-bordered w-full mt-2" value="${region.state}">
              </div>
              <span id="error-state" class="text-error hidden"></span>

              <jsp:include page="/components/buttonRegisterAndUpdate.jsp">
                <jsp:param name="obj" value="${region == null}"/>
                <jsp:param name="action" value="listRegions"/>
              </jsp:include>

            </form>
          </div>
        </main>
      </div>

      <jsp:include page="/components/sidebar.jsp" />
    </div>

    <jsp:include page="/components/errorOrUpdate.jsp" />
    <script defer src="${pageContext.request.contextPath}/scripts/validateForm.js"></script>
  </body>
</html>