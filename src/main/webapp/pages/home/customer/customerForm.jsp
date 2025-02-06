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
    <title>Cadastro de Cliente - IFSales</title>
  </head>
  <body>
    <div class="drawer lg:drawer-open">
      <label for="my-drawer-2"></label>
      <input id="my-drawer-2" type="checkbox" class="drawer-toggle" />
      <div class="drawer-content flex flex-col items-center">
        <jsp:include page="/components/navbar.jsp">
          <jsp:param name="title" value="Formulário Cliente" />
        </jsp:include>
        <main class="flex flex-col justify-center flex-1 w-full overflow-y-auto px-6 mt-8 lg:mt-0">
          <div class="sm:mx-auto sm:w-full mt-12 mb-12 sm:max-w-sm">
            <form id="form1" action="${pageContext.request.contextPath}/redirect?action=saveCustomer" method="post" class="space-y-6">
              <c:choose>
                <c:when test="${customer == null}">
                  <input type="hidden" name="id" value="0">
                </c:when>
                <c:when test="${customer != null}">
                  <input type="hidden" name="id" value="${customer.id}">
                </c:when>
              </c:choose>

              <div>
                <label for="cpf" class="font-semibold">Cpf<span class="text-error">*</span></label>
                <input type="text" id="cpf" minlength="14" maxlength="14" name="cpf" required class="input input-bordered w-full mt-2" value="${customer.cpf}">
              </div>
              <span id="error-cpf" class="text-error hidden"></span>

              <div>
                <label class="font-semibold" for="region">
                  Região<span class="text-error">*</span>
                  <select name="region" id="region" required class="select select-bordered w-full mt-2">
                    <c:choose>
                      <c:when test="${customer == null}">
                        <option value="" selected disabled>Selecione uma região</option>
                        <c:forEach var="region" items="${regions}">
                          <option value="${region.id}">${region.name}</option>
                        </c:forEach>
                      </c:when>
                      <c:when test="${customer != null}">
                        <option value="" disabled>Selecione uma região</option>
                        <c:forEach var="region" items="${regions}">
                          <c:choose>
                            <c:when test="${customer.region.id == region.id}">
                              <option value="${region.id}" selected>${region.name}</option>
                            </c:when>
                            <c:otherwise>
                              <option value="${region.id}">${region.name}</option>
                            </c:otherwise>
                          </c:choose>
                        </c:forEach>
                      </c:when>
                    </c:choose>
                  </select>
                </label>
              </div>
              <span id="error-region" class="text-error hidden"></span>

              <div>
                <label for="firstName" class="font-semibold">Nome<span class="text-error">*</span></label>
                <input type="text" id="firstName" name="firstName" required class="input input-bordered w-full mt-2" value="${customer.firstName}">
              </div>
              <span id="error-firstName" class="text-error hidden"></span>

              <div>
                <label for="lastName" class="font-semibold">Sobrenome<span class="text-error">*</span></label>
                <input type="text" id="lastName" name="lastName" required class="input input-bordered w-full mt-2" value="${customer.lastName}">
              </div>
              <span id="error-lastName" class="text-error hidden"></span>

              <div>
                <label for="birthDate" class="font-semibold">Data de Nascimento<span class="text-error">*</span></label>
                <input type="date" id="birthDate" name="birthDate" required class="input input-bordered w-full mt-2" value="${customer.birthDate}">
              </div>
              <span id="error-birthDate" class="text-error hidden"></span>

              <div>
                <label for="income" class="font-semibold">Renda<span class="text-error">*</span></label>
                <input type="number" step="0.01" id="income" name="income" required class="input input-bordered w-full mt-2" value="${customer.income}">
              </div>
              <span id="error-income" class="text-error hidden"></span>

              <div>
                <label for="mobile" class="font-semibold">Celular<span class="text-error">*</span></label>
                <input type="tel" id="mobile" name="mobile" required class="input input-bordered w-full mt-2" value="${customer.mobile}">
              </div>
              <span id="error-mobile" class="text-error hidden"></span>

              <div>
                <label for="professionalStatus" class="font-semibold">Status Profissional<span class="text-error">*</span></label>
                <input type="text" id="professionalStatus" name="professionalStatus" required class="input input-bordered w-full mt-2" value="${customer.professionalStatus}">
              </div>
              <span id="error-professionalStatus" class="text-error hidden"></span>

              <div class="space-y-2">
                <button type="submit" class="btn btn-primary btn-block">
                  <c:choose>
                    <c:when test="${customer == null}">
                      Cadastrar
                    </c:when>
                    <c:when test="${customer != null}">
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

    <jsp:include page="/components/errorOrUpdate.jsp" />
    <script defer src="${pageContext.request.contextPath}/scripts/validateForm.js"></script>

  </body>
</html>