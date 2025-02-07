<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="pt-br" data-theme="lofi">
  <jsp:include page="/components/head.jsp">
    <jsp:param name="title" value="Cadastro de Categoria - IFSales" />
  </jsp:include>
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

              <jsp:include page="/components/buttonRegisterAndUpdate.jsp">
                <jsp:param name="obj" value="${category == null}"/>
                <jsp:param name="action" value="listCategories"/>
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