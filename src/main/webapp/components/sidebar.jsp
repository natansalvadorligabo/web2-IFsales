<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<div class="drawer-side z-30">
  <label for="my-drawer-2" id="my-drawer-2" aria-label="close sidebar" class="drawer-overlay"></label>
  <ul class="menu bg-base-200 text-base-content min-h-full w-80 p-4 text-base-content">
    <li class="mb-4 font-semibold text-xl">
      <a class="font-semibold flex flex-col self-start" href="${pageContext.request.contextPath}/redirect?action=home">
        <img class="w-20" src="${pageContext.request.contextPath}/images/ifsales-logo.png" alt="ifsales logo">
        IFSales
      </a>
    </li>

    <li>
      <a class="font-semibold" href="${pageContext.request.contextPath}/redirect?action=dashboard">Dashboard</a>
    </li>

    <li>
      <details>
        <summary class="font-semibold">Tabelas</summary>
        <ul>
          <li>
            <a href="${pageContext.request.contextPath}/redirect?action=listSalespersons">Vendedores</a>
          </li>
          <li>
            <a href="${pageContext.request.contextPath}/redirect?action=listRegions">Regiões</a>
          </li>
          <li>
            <a href="${pageContext.request.contextPath}/redirect?action=listProducts">Produtos</a>
          </li>
          <li>
            <a href="${pageContext.request.contextPath}/redirect?action=listStores">Lojas</a>
          </li>
          <li>
            <a href="${pageContext.request.contextPath}/redirect?action=listCategories">Categorias</a>
          </li>
          <li>
            <a href="${pageContext.request.contextPath}/redirect?action=listCustomers">Clientes</a>
          </li>
          <li>
            <a href="${pageContext.request.contextPath}/redirect?action=listFunnels">Vendas</a>
          </li>
        </ul>
      </details>
    </li>
  </ul>
</div>

<div id="contextPath" data-contextPath="${pageContext.request.contextPath}"></div>

<script defer src="${pageContext.request.contextPath}/scripts/themeController.js"></script>
<script defer src="${pageContext.request.contextPath}/scripts/sidebar.js"></script>
