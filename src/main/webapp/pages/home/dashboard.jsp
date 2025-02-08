<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="pt-br" data-theme="lofi">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link href="https://cdn.jsdelivr.net/npm/daisyui@4.12.13/dist/full.min.css" rel="stylesheet" type="text/css" />
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
              <div class="stat-value text-primary">${totalSales}</div>
              <div class="stat-desc">Total acumulado até o momento</div>
            </div>

            <div class="stat">
              <div class="stat-title">Ticket Médio</div>
              <div class="stat-value text-secondary">${averageTicket}</div>
              <div class="stat-desc">Valor médio por pedido</div>
            </div>

            <div class="stat">
              <div class="stat-title">Vendas realizadas</div>
              <div class="stat-value">${totalProductsSold}</div>
            </div>
          </div>

          <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-2 gap-4 mt-8">
            <div class="w-full">
              <canvas id="salesByCategory"></canvas>
            </div>
            <div class="w-full">
              <canvas id="salesByRegion"></canvas>
            </div>
            <div class="w-full col-span-2">
              <canvas id="salesByMonth"></canvas>
            </div>
          </div>
        </main>
      </div>

      <jsp:include page="/components/sidebar.jsp" />
    </div>

    <script>
      const categoryLabel = [];
      const categoryData = [];
      const formatteCategorydData = [];

      <c:forEach var="item" items="${salesByCategory}">
        categoryLabel.push('${item.category_name}');
        categoryData.push('${item.total_sales}');
        formatteCategorydData.push('${item.formatted_total_sales_category}');
      </c:forEach>

      const salesByCategoryData = {
        labels: categoryLabel,
        datasets: [{
          label: 'Receita por Categoria',
          data: categoryData,
          backgroundColor: [
            '#3357FF', // Azul intenso
            '#FF5733', // Laranja vibrante
            '#33FF57', // Verde neon
            '#F033FF', // Roxo brilhante
            '#FFD700', // Amarelo ouro
            '#00FFFF', // Ciano
            '#FF1493'  // Rosa choque
          ],
          borderColor: '#FFFFFF',
          borderWidth: 1
        }]
      };

      new Chart(document.getElementById('salesByCategory'), {
        type: 'bar',
        data: salesByCategoryData,
        options: {
          responsive: true,
          maintainAspectRatio: true,

          scales: {
            y: { beginAtZero: true }
          },
          plugins: {
            tooltip: {
              callbacks: {
                label: function(tooltipItem) {
                  return formatteCategorydData[tooltipItem.dataIndex];
                }
              }
            }
          }
        }
      });

      const monthLabel = [];
      const monthData = [];
      const monthFormattedData = [];

      <c:forEach var="item" items="${salesByMonth}">
        monthLabel.push('${item.sales_month}');
        monthData.push('${item.total_sales}');
        monthFormattedData.push('${item.formatted_total_sales_month}');
      </c:forEach>

      const salesByMonthData = {
        labels: monthLabel,
        datasets: [{
          label: 'Receita por Mês',
          data: monthData,
          borderColor: '#FF4500',
          backgroundColor: 'rgba(255, 69, 0, 0.2)',
          borderWidth: 2,
          tension: 0.4
        }]
      };

      new Chart(document.getElementById('salesByMonth'), {
        type: 'line',
        data: salesByMonthData,
        options: {
          responsive: true,
          maintainAspectRatio: false,

          scales: {
            y: { beginAtZero: true }
          },
          plugins: {
            tooltip: {
              callbacks: {
                label: function(tooltipItem) {
                  return monthFormattedData[tooltipItem.dataIndex];
                }
              }
            }
          }
        }
      });

      const regionLabel = [];
      const regionData = [];
      const formatteRegionData = [];

      <c:forEach var="item" items="${salesByRegion}">
      regionLabel.push('${item.region_name}');
      regionData.push('${item.total_sales}');
      formatteRegionData.push('${item.formatted_total_sales_region}');
      </c:forEach>

      const salesByRegionData = {
        labels: regionLabel,
        datasets: [{
          label: 'Receita por Região',
          data: regionData.map(value => parseFloat(value)), // Convertendo valores para número
          backgroundColor: [
            '#3357FF', '#FF5733', '#33FF57',
            '#F033FF', '#FFD700', '#00FFFF', '#FF1493'
          ],
          borderColor: '#FFFFFF',
          borderWidth: 1
        }]
      };

      new Chart(document.getElementById('salesByRegion'), {
        type: 'pie',
        data: salesByRegionData,
        options: {
          responsive: true,
          maintainAspectRatio: false, // Permite ajustar o tamanho manualmente
          layout: {
            padding: 10
          },
          plugins: {
            tooltip: {
              callbacks: {
                label: function(tooltipItem) {
                  return formatteRegionData[tooltipItem.dataIndex];
                }
              }
            }
          }
        }
      });

    </script>
  </body>
</html>