google.charts.load('current', {'packages':['corechart']});

google.charts.setOnLoadCallback(graficoPrincipal);

function graficoPrincipal() {
      $.ajax({
        url: "api/dashboard/pedidostotales",
        dataType: "json",
      }).done(function (jsonData) {
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'descripcion');
        data.addColumn('number', 'montototal');
        

        jsonData.forEach(function (row) {
          data.addRow([
            row.descripcion,
            row.montototal
          ]);
        });

        var options = {
          chart: {
            width: 600,
            height: 400,
            title: 'Reporte de  Ventas',
            legend: { position: 'top'}
          }
        };

        var formatter = new google.visualization.NumberFormat({fractionDigits: 2} );
        formatter.format(data, 1);

        var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
        chart.draw(data, options );
      }).fail(function (jq, text, err) {
        console.log(text + ' - ' + err);
      });
}
