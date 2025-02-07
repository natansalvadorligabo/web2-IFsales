<%@ page import="br.com.ifsales.utils.DataSourceSearcher" %>
<%@ page import="br.com.ifsales.dao.RegionDao" %>
<%@ page import="java.util.List" %>
<%@ page import="br.com.ifsales.model.Region" %>
<%@ page import="java.sql.SQLException" %>
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
    <title>Cadastro de Loja - IFSales</title>
  </head>
  <body>
    <div class="drawer lg:drawer-open">
      <label for="my-drawer-2"></label>
      <input id="my-drawer-2" type="checkbox" class="drawer-toggle" />
      <div class="drawer-content flex flex-col items-center">
        <jsp:include page="/components/navbar.jsp">
          <jsp:param name="title" value="Formulário Loja" />
        </jsp:include>
        <main class="flex flex-col justify-center flex-1 w-full overflow-y-auto px-6 mt-8 lg:mt-0">
          <div class="sm:mx-auto sm:w-full sm:max-w-sm">
            <form id="form1" action="${pageContext.request.contextPath}/redirect?action=saveStore" method="post" class="space-y-6">
              <c:choose>
                <c:when test="${store == null}">
                  <input type="hidden" name="id" value="0">
                </c:when>
                <c:when test="${store != null}">
                  <input type="hidden" name="id" value="${store.id}">
                </c:when>
              </c:choose>

              <div>
                <label for="name" class="font-semibold">Nome<span class="text-error">*</span></label>
                <input type="text" id="name" name="name" required class="input input-bordered w-full mt-2" value="${store.name}">
              </div>
              <span id="error-name" class="text-error hidden"></span>

              <div>
                <label for="cnpj" class="font-semibold">CNPJ<span class="text-error">*</span></label>
                <input type="text" id="cnpj" name="cnpj" required class="input input-bordered w-full mt-2" value="${store.cnpj}">
              </div>
              <span id="error-cnpj" class="text-error hidden"></span>

              <div>
                <label class="font-semibold" for="regionId">
                  Região<span class="text-error">*</span>
                  <select name="regionId" id="regionId" required class="select select-bordered w-full mt-2">
                    <option value="" disabled <c:if test="${store == null}">selected</c:if>>Selecione uma região
                    </option>
                    <c:forEach var="region" items="${regions}">
                      <option value="${region.id}"
                              <c:if test="${store != null && store.region.id == region.id}">selected</c:if>>${region.name}</option>
                    </c:forEach>
                  </select>
                </label>
              </div>
              <span id="error-regionId" class="text-error hidden"></span>


              <div>
                <label for="address" class="font-semibold">Endereço<span class="text-error">*</span></label>
                <input type="text" id="address" name="address" required class="input input-bordered w-full mt-2" value="${store.address}">
              </div>
              <span id="error-address" class="text-error hidden"></span>

              <div>
                <label for="phone" class="font-semibold">Telefone<span class="text-error">*</span></label>
                <input type="text" id="phone" name="phone" required class="input input-bordered w-full mt-2" value="${store.phone}">
              </div>
              <span id="error-phone" class="text-error hidden"></span>

              <jsp:include page="/components/buttonRegisterAndUpdate.jsp">
                <jsp:param name="obj" value="${store == null}" />
                <jsp:param name="action" value="listStores" />
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