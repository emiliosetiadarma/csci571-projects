var myChart;
function getChartData(info) {
  data = { volume: [], price: [] };
  info.forEach((element) => {
    const date = new Date(element['date']);
    const timestamp = normalizeUTCDate(date).getTime();
    const close = element['close'];
    const volume = element['volume'];
    const newPriceElement = [timestamp, close];
    const newVolumeElement = [timestamp, volume];
    data['price'].push(newPriceElement);
    data['volume'].push(newVolumeElement);
  });
  return data;
}

function renderChartContainer() {
  return `
    <div id="charts" class="tab-content" style="display: none"></div>
  `;
}
function renderCharts(info) {
  if (info.length === 0) {
    const chart = document.getElementById('charts');
    chart.innerHTML = `
      <div class="error">
        <h4>Error: No chart data has been found, sorry.</h4>
      </div>
    `;
    return;
  }
  const data = getChartData(info);
  const price = data['price'];
  const volume = data['volume'];
  const ticker = document.getElementById('ticker').value;

  myChart = Highcharts.stockChart('charts', {
    title: {
      text: `Stock Price ${ticker.toUpperCase()} ${formatDate(new Date())}`
    },

    subtitle: {
      text:
        '<a style="text-decoration: underline; color: #0000EE;" href="https://api.tiingo.com">Source: Tiingo</a>'
    },

    xAxis: {
      gapGridLineWidth: 0
    },

    yAxis: [
      {
        // Primary yAxis
        labels: {
          format: '{value}',
          style: {
            color: Highcharts.getOptions().colors[1]
          }
        },
        title: {
          text: 'Stock Price',
          style: {
            color: Highcharts.getOptions().colors[1]
          }
        },
        opposite: false
      },
      {
        // Secondary yAxis
        labels: {
          style: {
            color: Highcharts.getOptions().colors[1]
          }
        },
        title: {
          text: 'Volume',
          style: {
            color: Highcharts.getOptions().colors[1]
          }
        }
      }
    ],

    tooltip: {
      shared: true
    },
    rangeSelector: {
      buttons: [
        {
          type: 'day',
          count: 7,
          text: '7d'
        },
        {
          type: 'day',
          count: 15,
          text: '15d'
        },
        {
          type: 'month',
          count: 1,
          text: '1m'
        },
        {
          type: 'month',
          count: 3,
          text: '3m'
        },
        {
          type: 'month',
          count: 6,
          text: '6m'
        }
      ],
      selected: 1,
      inputEnabled: false
    },

    series: [
      {
        name: 'Price',
        type: 'area',
        data: price,
        gapSize: 5,
        tooltip: {
          valueDecimals: 2
        },
        fillColor: {
          linearGradient: {
            x1: 0,
            y1: 0,
            x2: 0,
            y2: 1
          },
          stops: [
            [0, Highcharts.getOptions().colors[0]],
            [
              1,
              Highcharts.color(Highcharts.getOptions().colors[0])
                .setOpacity(0)
                .get('rgba')
            ]
          ]
        },
        threshold: null
      },
      {
        name: 'Volume',
        type: 'column',
        data: volume,
        yAxis: 1,
        pointWidth: 2.5,
        color: '#696969'
      }
    ]
  });
}
