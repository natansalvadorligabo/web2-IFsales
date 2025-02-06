<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="pt-br" data-theme="lofi">
<head>
  <meta charset="UTF-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link
          href="https://cdn.jsdelivr.net/npm/daisyui@4.12.13/dist/full.min.css"
          rel="stylesheet"
          type="text/css" />
  <script src="https://cdn.tailwindcss.com"></script>
  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
  <title>Dashboard - IFSales</title>
</head>
<body>
<div class="drawer lg:drawer-open">
  <input id="my-drawer-2" type="checkbox" class="drawer-toggle" />
  <div class="drawer-content flex flex-col items-center">
    <jsp:include page="/components/navbar.jsp">
      <jsp:param name="title" value="Dashboard" />
    </jsp:include>

    <main class="container mx-auto p-4 mt-8 flex-1 overflow-y-auto">
      <div class="stats stats-vertical sm:stats-horizontal bg-base-200 shadow-md w-full">
        <div class="stat">
          <div class="stat-title">Receita Total</div>
          <div class="stat-value text-primary">R$ 29.000,00</div>
          <div class="stat-desc">Total acumulado até o momento</div>
        </div>

        <div class="stat">
          <div class="stat-title">Ticket Médio</div>
          <div class="stat-value text-secondary">R$ 500,00</div>
          <div class="stat-desc">Valor médio por pedido</div>
        </div>

        <div class="stat">
          <div class="stat-value">392</div>
          <div class="stat-title">Vendas realizadas</div>
        </div>
      </div>

      <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4 mt-8">
        <div class="w-full">
          <!-- Gráfico Gênero (Pizza) -->
          <canvas id="genderChart"></canvas>
        </div>

        <div class="w-full">
          <!-- Gráfico Faixa Etária (Coluna) -->
          <canvas id="ageGroupChart"></canvas>
        </div>

        <div class="w-full">
          <!-- Gráfico Top 5 Vendedores (Coluna) -->
          <canvas id="topSellersChart"></canvas>
        </div>

        <div class="w-full">
          <!-- Gráfico Top 5 Lojas (Coluna) -->
          <canvas id="topStoresChart"></canvas>
        </div>
      </div>

      <!-- pegar os dados do banco dinamicamente -->

    </main>
  </div>

  <jsp:include page="/components/sidebar.jsp" />
</div>

<script>
  const genderData = {
    labels: ['Masculino', 'Feminino'],
    datasets: [{
      data: [60, 40],
      backgroundColor: ['#4caf50', '#f44336'],
    }]
  };

  const ageGroupData = {
    labels: ['18-25', '26-35', '36-45', '46-60', '60+'],
    datasets: [{
      label: 'Faixa Etária',
      data: [100, 150, 80, 40, 22],
      backgroundColor: '#4caf50',
      borderColor: '#4caf50',
      borderWidth: 1
    }]
  };

  const topSellersData = {
    labels: ['Vendedor 1', 'Vendedor 2', 'Vendedor 3', 'Vendedor 4', 'Vendedor 5'],
    datasets: [{
      label: 'Vendas',
      data: [150, 120, 100, 80, 50],
      backgroundColor: '#f44336',
      borderColor: '#f44336',
      borderWidth: 1
    }]
  };

  const topStoresData = {
    labels: ['Loja A', 'Loja B', 'Loja C', 'Loja D', 'Loja E'],
    datasets: [{
      label: 'Vendas',
      data: [200, 180, 160, 140, 120],
      backgroundColor: '#4caf50',
      borderColor: '#4caf50',
      borderWidth: 1
    }]
  };

  new Chart(document.getElementById('genderChart'), {
    type: 'pie',
    data: genderData,
  });

  new Chart(document.getElementById('ageGroupChart'), {
    type: 'bar',
    data: ageGroupData,
    options: {
      scales: {
        y: {
          beginAtZero: true
        }
      }
    }
  });

  new Chart(document.getElementById('topSellersChart'), {
    type: 'bar',
    data: topSellersData,
    options: {
      scales: {
        y: {
          beginAtZero: true
        }
      }
    }
  });

  new Chart(document.getElementById('topStoresChart'), {
    type: 'bar',
    data: topStoresData,
    options: {
      scales: {
        y: {
          beginAtZero: true
        }
      }
    }
  });
</script>
</body>
</html>