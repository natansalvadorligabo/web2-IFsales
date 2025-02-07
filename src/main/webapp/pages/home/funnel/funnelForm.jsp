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
    <title>Cadastro de Venda - IFSales</title>
  </head>
  <body>
    <div class="drawer lg:drawer-open">
      <label for="my-drawer-2"></label>
      <input id="my-drawer-2" type="checkbox" class="drawer-toggle" />
      <div class="drawer-content flex flex-col items-center">
        <jsp:include page="/components/navbar.jsp">
          <jsp:param name="title" value="Formulário Venda" />
        </jsp:include>
        <main class="flex flex-col justify-center flex-1 w-full overflow-y-auto px-6 mt-8 lg:mt-0">
          <div class="sm:mx-auto sm:w-full mt-12 mb-12 sm:max-w-sm">
            <form id="form1" action="${pageContext.request.contextPath}/redirect?action=saveFunnel" method="post" class="space-y-6">
              <c:choose>
                <c:when test="${funnel == null}">
                  <input type="hidden" name="id" value="0">
                </c:when>
                <c:when test="${funnel != null}">
                  <input type="hidden" name="id" value="${funnel.id}">
                </c:when>
              </c:choose>

              <div>
                <label class="font-semibold" for="customer">
                  Comprador<span class="text-error">*</span>
                  <select name="customer" id="customer" required class="select select-bordered w-full mt-2">
                    <c:choose>
                      <c:when test="${funnel == null}">
                        <option value="" selected disabled>Selecione um comprador</option>
                        <c:forEach var="customer" items="${customers}">
                          <option value="${customer.id}">${customer.firstName} ${customer.lastName}</option>
                        </c:forEach>
                      </c:when>
                      <c:when test="${funnel != null}">
                        <option value="" disabled>Selecione um comprador</option>
                        <c:forEach var="customer" items="${customers}">
                          <c:choose>
                            <c:when test="${funnel.customer.id == customer.id}">
                              <option value="${customer.id}" selected>${customer.firstName} ${customer.lastName}</option>
                            </c:when>
                            <c:otherwise>
                              <option value="${customer.id}">${customer.firstName} ${customer.lastName}</option>
                            </c:otherwise>
                          </c:choose>
                        </c:forEach>
                      </c:when>
                    </c:choose>
                  </select>
                </label>
              </div>
              <span id="error-customer" class="text-error hidden"></span>

              <div>
                <label class="font-semibold" for="salesperson">
                  Vendedor<span class="text-error">*</span>
                  <select name="salesperson" id="salesperson" required class="select select-bordered w-full mt-2">
                    <c:choose>
                      <c:when test="${funnel == null}">
                        <option value="" selected disabled>Selecione um vendedor</option>
                        <c:forEach var="salesperson" items="${salespersons}">
                          <option value="${salesperson.id}">${salesperson.name}</option>
                        </c:forEach>
                      </c:when>
                      <c:when test="${funnel != null}">
                        <option value="" disabled>Selecione um vendedor</option>
                        <c:forEach var="salesperson" items="${salespersons}">
                          <c:choose>
                            <c:when test="${funnel.salesperson.id == salesperson.id}">
                              <option value="${salesperson.id}" selected>${salesperson.name}</option>
                            </c:when>
                            <c:otherwise>
                              <option value="${salesperson.id}">${salesperson.name}</option>
                            </c:otherwise>
                          </c:choose>
                        </c:forEach>
                      </c:when>
                    </c:choose>
                  </select>
                </label>
              </div>
              <span id="error-salesperson" class="text-error hidden"></span>

              <div>
                <label class="font-semibold" for="store">
                  Loja<span class="text-error">*</span>
                  <select name="store" id="store" required class="select select-bordered w-full mt-2">
                    <c:choose>
                      <c:when test="${funnel == null}">
                        <option value="" selected disabled>Selecione uma loja</option>
                        <c:forEach var="store" items="${stores}">
                          <option value="${store.id}">${store.name}</option>
                        </c:forEach>
                      </c:when>
                      <c:when test="${funnel != null}">
                        <option value="" disabled>Selecione uma loja</option>
                        <c:forEach var="store" items="${stores}">
                          <c:choose>
                            <c:when test="${funnel.store.id == store.id}">
                              <option value="${store.id}" selected>${store.name}</option>
                            </c:when>
                            <c:otherwise>
                              <option value="${store.id}">${store.name}</option>
                            </c:otherwise>
                          </c:choose>
                        </c:forEach>
                      </c:when>
                    </c:choose>
                  </select>
                </label>
              </div>
              <span id="error-store" class="text-error hidden"></span>

              <div>
                <label class="font-semibold" for="product">
                  Produto<span class="text-error">*</span>
                  <select name="product" id="product" required class="select select-bordered w-full mt-2">
                    <c:choose>
                      <c:when test="${funnel == null}">
                        <option value="" selected disabled>Selecione um produto</option>
                        <c:forEach var="product" items="${products}">
                          <option value="${product.id}">${product.brand} ${product.model}</option>
                        </c:forEach>
                      </c:when>
                      <c:when test="${funnel != null}">
                        <option value="" disabled>Selecione um produto</option>
                        <c:forEach var="product" items="${products}">
                          <c:choose>
                            <c:when test="${funnel.product.id == product.id}">
                              <option value="${product.id}" selected>${product.brand} ${product.model}</option>
                            </c:when>
                            <c:otherwise>
                              <option value="${product.id}">${product.brand} ${product.model}</option>
                            </c:otherwise>
                          </c:choose>
                        </c:forEach>
                      </c:when>
                    </c:choose>
                  </select>
                </label>
              </div>
              <span id="error-product" class="text-error hidden"></span>

              <div>
                <label for="paidDate" class="font-semibold">Data do pagamento<span class="text-error">*</span></label>
                <input type="date" id="paidDate" name="paidDate" required class="input input-bordered w-full mt-2" value="${funnel.paidDate}">
              </div>
              <span id="error-paidDate" class="text-error hidden"></span>

              <div>
                <label for="discount" class="font-semibold">Desconto(%)<span class="text-error">*</span></label>
                <input type="number" step="0.1" id="discount" name="discount" required class="input input-bordered w-full mt-2" value="${funnel.discount}">
              </div>
              <span id="error-discount" class="text-error hidden"></span>

              <div>
                <label for="productQuantity" class="font-semibold">Quantidade do produto<span class="text-error">*</span></label>
                <input type="number" step="1" id="productQuantity" name="productQuantity" required class="input input-bordered w-full mt-2" value="${funnel.productQuantity}">
              </div>
              <span id="error-productQuantity" class="text-error hidden"></span>

              <div class="space-y-2">
                <button type="submit" class="btn btn-primary btn-block">
                  <c:choose>
                    <c:when test="${funnel == null}">
                      Cadastrar
                    </c:when>
                    <c:when test="${funnel != null}">
                      Atualizar
                    </c:when>
                  </c:choose>
                </button>

                <a href="${pageContext.request.contextPath}/redirect?action=listFunnels" class="btn btn-outline btn-block">
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