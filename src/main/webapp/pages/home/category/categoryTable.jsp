<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="pt-br" data-theme="lofi">
  <jsp:include page="/components/head.jsp">
    <jsp:param name="title" value="Tabela de Categorias - IFSales" />
  </jsp:include>
  <body>
    <div class="drawer lg:drawer-open">
      <input id="my-drawer-2" type="checkbox" class="drawer-toggle" />

      <div class="drawer-content flex flex-col items-center">
        <jsp:include page="/components/navbar.jsp">
          <jsp:param name="title" value="Tabela de Categorias" />
        </jsp:include>

        <div class="container mx-auto p-4">
          <div class="flex items-center justify-between mb-6 mt-8">
            <h1 class="text-2xl font-bold">Categorias</h1>
            <a class="btn btn-success btn-circle" href="${pageContext.request.contextPath}/pages/home/category/categoryForm.jsp">
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
                    <th>Nome</th>
                    <th>Descrição</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach var="category" items="${categories}">
                    <tr class="hover">
                      <td>${category.id}</td>
                      <td>${category.name}</td>
                      <td>${category.description}</td>
                      <td class="p-1 flex">
                        <button onclick="document.getElementById('edit-${category.id}').showModal()" class="btn btn-outline btn-warning w-1/2">
                          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
                            <path stroke-linecap="round" stroke-linejoin="round" d="m16.862 4.487 1.687-1.688a1.875 1.875 0 1 1 2.652 2.652L10.582 16.07a4.5 4.5 0 0 1-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 0 1 1.13-1.897l8.932-8.931Zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0 1 15.75 21H5.25A2.25 2.25 0 0 1 3 18.75V8.25A2.25 2.25 0 0 1 5.25 6H10"></path>
                          </svg>
                          Editar
                        </button>
                        <button onclick="document.getElementById('delete-${category.id}').showModal()" class="btn btn-outline btn-error w-1/2">
                          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash-fill" viewBox="0 0 16 16">
                            <path d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5M8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5m3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0"></path>
                          </svg>
                          Deletar
                        </button>

                        <dialog id="edit-${category.id}" class="modal">
                          <div class="modal-box">
                            <h3 class="text-lg font-bold">Editar</h3>
                            <p class="py-4">Tem certeza que deseja editar "${category.name}"?</p>

                            <div class="modal-body flex justify-between">
                              <form method="dialog" class="w-1/2 mr-1">
                                <button class="btn btn-outline w-full">Cancelar</button>
                              </form>

                              <a class="btn btn-secondary w-1/2 ml-1" href="${pageContext.request.contextPath}/redirect?action=updateCategory&id=${category.id}">
                                Editar
                              </a>
                            </div>
                          </div>
                        </dialog>

                        <dialog id="delete-${category.id}" class="modal">
                          <div class="modal-box">
                            <h3 class="text-lg font-bold">Deletar</h3>
                            <p class="py-4">Tem certeza que deseja deletar "${category.name}"?</p>

                            <div class="modal-body flex justify-between">
                              <form method="dialog" class="w-1/2 mr-1">
                                <button class="btn btn-outline w-full">Cancelar</button>
                              </form>

                              <a class="btn btn-secondary w-1/2 ml-1" href="${pageContext.request.contextPath}/redirect?action=deleteCategory&id=${category.id}">
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
        <jsp:param name="registerSuccess" value="Categoria cadastrada com sucesso."/>
        <jsp:param name="updateSuccess" value="Categoria atualizada com sucesso."/>
        <jsp:param name="updateError" value="Ocorreu um erro ao atualizar a categoria, operação cancelada."/>
        <jsp:param name="deleteSuccess" value="Categoria deletada com sucesso."/>
        <jsp:param name="deleteError" value="Esta categoria não pode ser deletada, pois está associada a um ou mais produtos."/>
      </jsp:include>

      <jsp:include page="/components/sidebar.jsp"/>
  </body>
</html>