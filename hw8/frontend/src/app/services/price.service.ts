import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface PriceData {
  prevClose: number;
  mid: number;
  lastSaleTimestamp: string;
  open: number;
  askPrice: number;
  low: number;
  ticker: string;
  timestamp: string;
  lastSize: number;
  tngoLast: number;
  last: number;
  high: number;
  askSize: number;
  quoteTimestamp: string;
  bidPrice: number;
  bidSize: number;
  volume: number;
}

export interface DailyPriceData {
  date: string;
  close: number;
  high: number;
  low: number;
  open: number;
  volume: number;
}

export interface HistoricalPriceData {
  date: string;
  close: number;
  high: number;
  low: number;
  open: number;
  volume: number;
  adjClose: number;
  adjHigh: number;
  adjLow: number;
  adjOpen: number;
  adjVolume: number;
  divCash: number;
  splitFactor: number;
}

@Injectable({
  providedIn: 'root',
})
export class PriceService {
  constructor(private http: HttpClient) {}
  getLatestPrice(ticker: string): Observable<PriceData[]> {
    const url = `https://node-stocksearch-backend-es-v5.wl.r.appspot.com/stock/latestprice/${ticker}`;
    return this.http.get<PriceData[]>(url);
  }

  getHistoricalChartData(
    ticker: string,
    months: number
  ): Observable<HistoricalPriceData[]> {
    const url = `https://node-stocksearch-backend-es-v5.wl.r.appspot.com/stock/historicaldata/${ticker}/${months}`;
    return this.http.get<HistoricalPriceData[]>(url);
  }

  getDailyChartData(
    ticker: String,
    date: string
  ): Observable<DailyPriceData[]> {
    const url = `https://node-stocksearch-backend-es-v5.wl.r.appspot.com/stock/chartdata/${ticker}/${date}`;
    return this.http.get<DailyPriceData[]>(url);
  }
}
