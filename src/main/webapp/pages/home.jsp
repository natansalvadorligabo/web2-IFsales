<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="pt-br" data-theme="lofi">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/daisyui@4.12.13/dist/full.min.css" rel="stylesheet" type="text/css" />
    <script src="https://cdn.tailwindcss.com"></script>
    <title>Home - IFSales</title>
</head>
<body>
    <div class="drawer lg:drawer-open">
        <input id="my-drawer-2" type="checkbox" class="drawer-toggle" />
        <div class="drawer-content flex flex-col items-center">
            <jsp:include page="/components/navbar.jsp">
                <jsp:param name="title" value="Home"/>
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

    <script src="${pageContext.request.contextPath}/scripts/updateSalesPersonListener.js"></script>
</body>
</html>