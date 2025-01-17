<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

    <div class="drawer-side z-30">
        <label for="my-drawer-2" id="my-drawer-2" aria-label="close sidebar" class="drawer-overlay"></label>
        <ul class="menu bg-base-200 text-base-content min-h-full w-80 p-4 text-base-content">
            <li class="mb-4 font-semibold text-xl">
                <a class="font-semibold" href="${pageContext.request.contextPath}/redirect?action=home">IFSales</a>
            </li>

            <li>
                <a class="font-semibold" href="#">Dashboard <span class="badge">Em breve</span></a>
            </li>

            <li>
                <details>
                    <summary class="font-semibold">Adicionar</summary>
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/pages/salespersonRegister.jsp">Vendedor</a></li>

                        <li><a>... <span class="badge">Em breve</span></a></li>
                    </ul>
                </details>
            </li>

            <li>
                <details>
                    <summary class="font-semibold">Atualizar Cadastro</summary>
                    <ul>
                        <li class="p-3">Vendedor</li>
                        <li>
                            <input type="email" id="search-salesperson-email" name="search-salesperson-email" class="input input-sm w-full max-w-xs cursor-default" placeholder="E-mail do vendedor">
                        </li>

                        <li><a>... <span class="badge">Em breve</span></a></li>
                    </ul>
                </details>
            </li>

            <li>
                <details>
                    <summary class="font-semibold">Tabelas</summary>
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/redirect?action=listSalespersons">Vendedor</a></li>

                        <li><a>... <span class="badge">Em breve</span></a></li>
                    </ul>
                </details>
            </li>
            <li><a href="#" class="font-semibold">Configurações <span class="badge">Em breve</span></a></li>

        </ul>
    </div>

<div id="contextPath" data-contextPath="${pageContext.request.contextPath}"></div>

<script defer src="${pageContext.request.contextPath}/scripts/updateSalespersonListener.js"></script>
<script defer src="${pageContext.request.contextPath}/scripts/themeController.js"></script>