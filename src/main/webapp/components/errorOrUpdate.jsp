<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<div class="fixed bottom-2 left-2 z-40">
  <c:if test="${result == 'registerError' || result == 'updateError'}">
    <div class="alert alert-error">
      <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 shrink-0 stroke-current" fill="none" viewBox="0 0 24 24">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
      </svg>
      <span>Ocorreu um erro, tente novamente.</span>
    </div>
  </c:if>
</div>

<script src="${pageContext.request.contextPath}/scripts/autoRemoveAlerts.js"></script>