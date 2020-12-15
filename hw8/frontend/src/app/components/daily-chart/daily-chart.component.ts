import { Component, OnInit, Input, OnChanges } from '@angular/core';
import * as Highcharts from 'highcharts/highstock';

@Component({
  selector: 'app-daily-chart',
  templateUrl: './daily-chart.component.html',
  styleUrls: ['./daily-chart.component.css'],
})
export class DailyChartComponent implements OnInit, OnChanges {
  @Input() prices: number[][];
  @Input() ticker: string;
  @Input() change: number;
  Highcharts: typeof Highcharts = Highcharts;
  chartOptions: Highcharts.Options = {};
  color: string = '';
  constructor() {}

  ngOnInit(): void {
    this.chartOptions = {
      navigator: {
        series: {
          color: this.color,
        },
      },
      plotOptions: {
        series: {
          color: this.color,
        },
      },
      rangeSelector: {
        selected: 4,
        inputEnabled: false,
        buttonTheme: {
          visibility: 'hidden',
        },
        labelStyle: {
          visibility: 'hidden',
        },
      },
      title: {
        text: `${this.ticker.toUpperCase()}`,
      },
      time: {
        useUTC: false,
      },
      series: [
        {
          type: 'line',
          name: `${this.ticker}`,
          data: this.prices,
          lineWidth: 3,
        },
      ],
    };
  }

  ngOnChanges(): void {
    this.color = this.change < 0 ? 'red' : this.change > 0 ? 'green' : '';
    this.updateOptions(this.prices, this.ticker);
  }

  updateOptions(prices: number[][], ticker: string) {
    this.chartOptions = {
      navigator: {
        series: {
          color: this.color,
        },
      },
      scrollbar: {
        enabled: false,
      },
      plotOptions: {
        series: {
          color: this.color,
        },
      },
      rangeSelector: {
        selected: 4,
        inputEnabled: false,
        buttonTheme: {
          visibility: 'hidden',
        },
        labelStyle: {
          visibility: 'hidden',
        },
      },
      title: {
        text: `${this.ticker.toUpperCase()}`,
      },
      time: {
        useUTC: false,
      },
      series: [
        {
          type: 'line',
          name: `${this.ticker}`,
          data: this.prices,
          lineWidth: 3,
        },
      ],
    };
  }
}
