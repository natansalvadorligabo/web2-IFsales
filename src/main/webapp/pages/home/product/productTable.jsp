<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="pt-br" data-theme="lofi">
  <jsp:include page="/components/head.jsp">
    <jsp:param name="title" value="Tabelas de Produtos - IFSales" />
  </jsp:include>

  <body>
    <div class="drawer lg:drawer-open">
      <input id="my-drawer-2" type="checkbox" class="drawer-toggle" />

      <div class="drawer-content flex flex-col items-center">
        <jsp:include page="/components/navbar.jsp">
          <jsp:param name="title" value="Tabela de Produtos" />
        </jsp:include>

        <div class="container mx-auto p-4">
          <div class="flex items-center justify-between mb-6 mt-8">
            <h1 class="text-2xl font-bold">Produtos</h1>
            <a class="btn btn-success btn-circle" href="${pageContext.request.contextPath}/redirect?action=loadProductForm">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
                <path stroke-linecap="round" stroke-linejoin="round" d="M12 4.5v15m7.5-7.5h-15"></path>
              </svg>
            </a>
          </div>

          <div class="stats stats-vertical shadow-md w-full">
            <div class="overflow-x-auto">
              <table class="table table-zebra">
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Marca</th>
                    <th>Modelo</th>
                    <th>Ano</th>
                    <th>Preço</th>
                    <th>Categoria</th>
                    <th>Total de Vendas</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach var="product" items="${products}">
                    <tr class="hover">
                      <td>${product.id}</td>
                      <td>${product.brand}</td>
                      <td>${product.model}</td>
                      <td>${product.modelYear}</td>
                      <td class="price">${product.price}</td>
                      <td>${product.category.name}</td>
                      <td>${product.totalSales}</td>
                      <td class="p-1 flex">
                        <button onclick="document.getElementById('edit-${product.id}').showModal()" class="btn btn-outline btn-warning w-1/2">
                          Editar
                        </button>
                        <button onclick="document.getElementById('delete-${product.id}').showModal()" class="btn btn-outline btn-error w-1/2">
                          Deletar
                        </button>

                        <dialog id="edit-${product.id}" class="modal">
                          <div class="modal-box">
                            <h3 class="text-lg font-bold">Editar</h3>
                            <p class="py-4">Tem certeza que deseja editar "${product.model}"?</p>
                            <div class="modal-body flex justify-between">
                              <form method="dialog" class="w-1/2 mr-1">
                                <button class="btn btn-outline w-full">Cancelar</button>
                              </form>
                              <a class="btn btn-secondary w-1/2 ml-1" href="${pageContext.request.contextPath}/redirect?action=updateProduct&id=${product.id}">
                                Editar
                              </a>
                            </div>
                          </div>
                        </dialog>

                        <dialog id="delete-${product.id}" class="modal">
                          <div class="modal-box">
                            <h3 class="text-lg font-bold">Deletar</h3>
                            <p class="py-4">Tem certeza que deseja deletar "${product.model}"?</p>
                            <div class="modal-body flex justify-between">
                              <form method="dialog" class="w-1/2 mr-1">
                                <button class="btn btn-outline w-full">Cancelar</button>
                              </form>
                              <a class="btn btn-secondary w-1/2 ml-1" href="${pageContext.request.contextPath}/redirect?action=deleteProduct&id=${product.id}">
                                Deletar
                              </a>
                            </div>
                          </div>
                        </dialog>
                      </td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>

      <jsp:include page="/components/defaultErrors.jsp">
        <jsp:param name="registerSuccess" value="Produto cadastrado com sucesso."/>
        <jsp:param name="updateSuccess" value="Produto atualizado com sucesso."/>
        <jsp:param name="deleteSuccess" value="Produto deletado com sucesso."/>
        <jsp:param name="updateError" value="Erro ao atualizar o produto."/>
        <jsp:param name="deleteError" value="Este produto não pode ser deletado, pois está associado a um ou mais funis" />
      </jsp:include>

      <jsp:include page="/components/sidebar.jsp"/>
  </body>
</html>