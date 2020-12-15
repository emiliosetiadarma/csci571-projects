import { Component, Input, OnInit } from '@angular/core';
import { HistoricalPriceData } from './../../services/price.service';
import * as Highcharts from 'highcharts/highstock';
import IndicatorsCore from 'highcharts/indicators/indicators';
import vbp from 'highcharts/indicators/volume-by-price';
IndicatorsCore(Highcharts);
vbp(Highcharts);
@Component({
  selector: 'app-historical-chart',
  templateUrl: './historical-chart.component.html',
  styleUrls: ['./historical-chart.component.css'],
})
export class HistoricalChartComponent implements OnInit {
  @Input() data: HistoricalPriceData[];
  @Input() ticker: string;
  Highcharts: typeof Highcharts = Highcharts;
  chartOptions: Highcharts.Options = {};
  constructor() {}

  ngOnInit(): void {
    var ohlc: number[][] = [],
      volume: number[][] = [],
      dataLength: number = this.data.length,
      // set the allowed units for data grouping
      groupingUnits: [string, number[]][] = [
        [
          'week', // unit name
          [1], // allowed multiples
        ],
        ['month', [1, 2, 3, 4, 6]],
      ];

    for (var i: number = 0; i < dataLength; i++) {
      const time: number = new Date(this.data[i].date).getTime(),
        open: number = this.data[i].open,
        high: number = this.data[i].high,
        low: number = this.data[i].low,
        close: number = this.data[i].close,
        vol: number = this.data[i].volume;
      ohlc.push([time, open, high, low, close]);
      volume.push([time, vol]);
    }
    this.ticker = this.ticker.toUpperCase();
    this.chartOptions = {
      rangeSelector: {
        selected: 2,
      },
      title: {
        text: `${this.ticker} Historical`,
      },
      subtitle: {
        text: 'With SMA and Volume by Price technical indicators',
      },
      yAxis: [
        {
          startOnTick: false,
          endOnTick: false,
          labels: {
            align: 'right',
            x: -3,
          },
          title: {
            text: 'OHLC',
          },
          height: '60%',
          lineWidth: 2,
          resize: {
            enabled: true,
          },
        },
        {
          labels: {
            align: 'right',
            x: -3,
          },
          title: {
            text: 'Volume',
          },
          top: '65%',
          height: '35%',
          offset: 0,
          lineWidth: 2,
        },
      ],
      tooltip: {
        split: true,
      },
      plotOptions: {
        series: {
          dataGrouping: {
            units: groupingUnits,
          },
        },
      },
      series: [
        {
          type: 'candlestick',
          name: `${this.ticker}`,
          id: `${this.ticker.toLowerCase()}`,
          zIndex: 2,
          data: ohlc,
        },
        {
          type: 'column',
          name: 'Volume',
          id: 'volume',
          data: volume,
          yAxis: 1,
        },
        {
          type: 'vbp',
          linkedTo: `${this.ticker.toLowerCase()}`,
          params: {
            volumeSeriesID: 'volume',
          },
          dataLabels: {
            enabled: false,
          },
          zoneLines: {
            enabled: false,
          },
        },
        {
          type: 'sma',
          linkedTo: `${this.ticker.toLowerCase()}`,
          zIndex: 1,
          marker: {
            enabled: false,
          },
        },
      ],
    };
  }
}
