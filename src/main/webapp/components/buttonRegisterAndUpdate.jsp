<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<div class="space-y-2">
  <button type="submit" class="btn btn-primary btn-block"> ${param.obj == "true" ? "Cadastrar" : "Atualizar"} </button>

  <a href="${pageContext.request.contextPath}/redirect?action=${param.action}" class="btn btn-outline btn-block">
    Voltar
  </a>
</div>