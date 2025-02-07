<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-br" data-theme="lofi">
  <jsp:include page="/components/head.jsp">
    <jsp:param name="title" value="Login - IFSales" />
  </jsp:include>

  <body class="min-h-screen bg-base-200">

    <img src="${pageContext.request.contextPath}/images/ifsales-nobg-shadow.png" alt="IFSales logo" class="mx-auto w-64" />
    <div class="flex min-h-full flex-col justify-center px-6 py-12 lg:px-8">
      <div class="sm:mx-auto sm:w-full sm:max-w-sm">
        <h2 class="mb-4 text-2xl font-bold text-center text-secondary">
          Entrar
        </h2>
      </div>

      <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
        <form action="${pageContext.request.contextPath}/redirect?action=login" method="post" class="space-y-6">
          <div>
            <label for="email" class="font-semibold">
              E-mail<span class="text-error">*</span>
            </label>
            <input type="email" id="email" name="email" required class="input input-bordered w-full mt-2" />
          </div>
          <span id="error-email" class="text-error hidden"></span>

          <div>
            <label for="password" class="font-semibold">
              Senha<span class="text-error">*</span>
            </label>
            <input type="password" id="password" name="password" required class="input input-bordered w-full mt-2" />
          </div>
          <span id="error-password" class="text-error hidden"></span>

          <div class="space-y-2">
            <button type="submit" class="btn btn-primary btn-block">
              Entrar
            </button>

            <a href="${pageContext.request.contextPath}/pages/userRegister.jsp" class="btn btn-outline btn-block">
              Cadastre-se
            </a>
          </div>
        </form>
      </div>
    </div>

    <div class="fixed bottom-2 left-2">
      <c:choose>
        <c:when test="${result == 'loginError'}">
          <div class="alert alert-error">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 shrink-0 stroke-current" fill="none" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
            </svg>
            <span>Email ou senha incorretos.</span>
          </div>
        </c:when>
      </c:choose>
    </div>


    <script src="${pageContext.request.contextPath}/scripts/validateForm.js"></script>
    <script src="${pageContext.request.contextPath}/scripts/autoRemoveAlerts.js"></script>
  </body>
</html>
