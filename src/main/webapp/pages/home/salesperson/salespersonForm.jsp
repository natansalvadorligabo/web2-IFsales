<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="pt-br" data-theme="lofi">
  <jsp:include page="/components/head.jsp">
    <jsp:param name="title" value="Cadastro de Vendedor - IFSales" />
  </jsp:include>
  <body>
    <div class="drawer lg:drawer-open">
      <input id="my-drawer-2" type="checkbox" class="drawer-toggle" />
      <div class="drawer-content flex flex-col items-center">
        <jsp:include page="/components/navbar.jsp">
          <jsp:param name="title" value="Formulário Vendedor" />
        </jsp:include>
        <main class="flex flex-col justify-center flex-1 w-full overflow-y-auto px-6 mt-8 lg:mt-0">
          <div class="sm:mx-auto sm:w-full sm:max-w-sm">
            <form id="form1" action="${pageContext.request.contextPath}/redirect?action=saveSalesperson" method="post" class="space-y-6">

              <c:choose>
                <c:when test="${salesperson == null}">
                  <input type="hidden" name="id" value="0">
                </c:when>
                <c:when test="${salesperson != null}">
                  <input type="hidden" name="id" value="${salesperson.id}">
                </c:when>
              </c:choose>

              <div>
                <label for="name" class="font-semibold">Nome<span class="text-error">*</span></label>
                <input type="text" id="name" name="name" required class="input input-bordered w-full mt-2" value="${salesperson.name}">
              </div>
              <span id="error-name" class="text-error hidden"></span>

              <div>
                <label for="email" class="font-semibold">E-mail<span class="text-error">*</span></label>
                <input type="email" id="email" name="email" required class="input input-bordered w-full mt-2" value="${salesperson.email}">
              </div>
              <span id="error-email" class="text-error hidden"></span>

              <div>
                <label for="phone" class="font-semibold">Telefone<span class="text-error">*</span></label>
                <input type="tel" id="phone" name="phone" required class="input input-bordered w-full mt-2" placeholder="(12) 34567-8910" value="${salesperson.phone}">
              </div>
              <span id="error-phone" class="text-error hidden"></span>

              <div class="flex items-center">
                <input type="checkbox" id="active" name="active" class="checkbox checkbox-bordered"
                        <c:if test="${salesperson.active}">checked</c:if>>
                <label for="active" class="ml-2 font-semibold">Ativo</label>
              </div>

              <jsp:include page="/components/buttonRegisterAndUpdate.jsp">
                <jsp:param name="obj" value="${salesperson == null}"/>
                <jsp:param name="action" value="listSalespersons"/>
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