<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="pt-br" data-theme="lofi">
  <jsp:include page="/components/head.jsp">
    <jsp:param name="title" value="Home - IFSales" />
  </jsp:include>
  <body>
    <div class="drawer lg:drawer-open">
      <input id="my-drawer-2" type="checkbox" class="drawer-toggle" />
      <div class="drawer-content flex flex-col items-center">
        <jsp:include page="/components/navbar.jsp">
          <jsp:param name="title" value="Home" />
        </jsp:include>

        <main class="flex-1 overflow-y-auto md:pt-4 pt-4 px-6">
          <div class="hero h-4/5">
            <div class="hero-content">
              <div class="max-w-md">
                <h1 class="text-2xl mt-8 font-bold">Sales Dashboard</h1>
              </div>
            </div>
          </div>
        </main>
      </div>

      <jsp:include page="/components/sidebar.jsp" />
    </div>


    <div class="fixed bottom-2 left-2 z-40">
      <c:if test="${result == 'registerSuccess'}">
        <div class="alert alert-success">
          <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 shrink-0 stroke-current" fill="none" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
          </svg>
          <span>Usuário cadastrado com sucesso, login feito automaticamente.</span>
        </div>
      </c:if>
    </div>


    <script src="${pageContext.request.contextPath}/scripts/autoRemoveAlerts.js"></script>
  </body>
</html>
