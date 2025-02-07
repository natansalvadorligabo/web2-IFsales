<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="pt-br" data-theme="lofi">
  <jsp:include page="/components/head.jsp">
    <jsp:param name="title" value="Cadastro de Produto - IFSales" />
  </jsp:include>
  <body>
    <div class="drawer lg:drawer-open">
      <input id="my-drawer-2" type="checkbox" class="drawer-toggle" />
      <div class="drawer-content flex flex-col items-center">
        <jsp:include page="/components/navbar.jsp">
          <jsp:param name="title" value="Formulário Produto" />
        </jsp:include>
        <main class="flex flex-col justify-center flex-1 w-full overflow-y-auto px-6 mt-8 lg:mt-0">
          <div class="sm:mx-auto sm:w-full sm:max-w-sm">
            <form id="form1" action="${pageContext.request.contextPath}/redirect?action=saveProduct" method="post" class="space-y-6">

              <c:choose>
                <c:when test="${product == null}">
                  <input type="hidden" name="id" value="0">
                </c:when>
                <c:when test="${product != null}">
                  <input type="hidden" name="id" value="${product.id}">
                </c:when>
              </c:choose>

              <c:choose>
                <c:when test="${product == null}">
                  <input type="hidden" name="totalSales" value="0">
                </c:when>
                <c:when test="${product != null}">
                  <input type="hidden" name="totalSales" value="${product.totalSales}">
                </c:when>
              </c:choose>

              <div>
                <label for="brand" class="font-semibold">Marca<span class="text-error">*</span></label>
                <input type="text" id="brand" name="brand" required class="input input-bordered w-full mt-2" value="${product.brand}">

              </div>
              <span id="error-brand" class="text-error hidden"></span>

              <div>
                <label for="model" class="font-semibold">Modelo<span class="text-error">*</span></label>
                <input type="text" id="model" name="model" required class="input input-bordered w-full mt-2" value="${product.model}">

              </div>
              <span id="error-model" class="text-error hidden"></span>

              <div>
                <label for="modelYear" class="font-semibold">Ano do Modelo<span class="text-error">*</span></label>
                <input type="number" id="modelYear" name="modelYear" required class="input input-bordered w-full mt-2" value="${product.modelYear}">
              </div>
              <span id="error-modelYear" class="text-error hidden"></span>

              <div>
                <label for="price" class="font-semibold">Preço<span class="text-error">*</span></label>
                <input type="number" id="price" name="price" required class="input input-bordered w-full mt-2" value="${product.price}">
              </div>
              <span id="error-price" class="text-error hidden"></span>

              <div>
                <label class="font-semibold" for="category">
                  Categoria<span class="text-error">*</span>
                  <select name="category" id="category" required class="select select-bordered w-full mt-2">
                    <option value="" disabled <c:if test="${product == null}">selected</c:if>>Selecione uma categoria
                    </option>
                    <c:forEach var="category" items="${categories}">
                      <option value="${category.id}"
                              <c:if test="${product != null && product.category.id == category.id}">selected</c:if>>${category.name}</option>
                    </c:forEach>
                  </select>
                </label>
              </div>
              <span id="error-category" class="text-error hidden"></span>

              <jsp:include page="/components/buttonRegisterAndUpdate.jsp">
                <jsp:param name="obj" value="${product == null}" />
                <jsp:param name="action" value="listProducts" />
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