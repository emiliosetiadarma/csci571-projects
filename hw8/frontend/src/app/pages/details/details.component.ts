import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { of, interval, Observable, Subscription, Subject } from 'rxjs';
import { tap, debounceTime } from 'rxjs/operators';
import {
  Description,
  DescriptionService,
} from './../../services/description.service';
import {
  PriceData,
  HistoricalPriceData,
  DailyPriceData,
  PriceService,
} from './../../services/price.service';
import { News, NewsService } from './../../services/news.service';
import {
  formatDate,
  formatDateAndTime,
  twoDecimals,
} from './../../functions/formatters';

export interface Subscriptions {
  description: Subscription;
  historicalChart: Subscription;
  dailyChart: Subscription;
  latestPrice: Subscription;
  news: Subscription;
  intervals: Subscription;
}

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css'],
})
export class DetailsComponent implements OnInit, OnDestroy {
  ticker: string = '';
  buyPrice: number = 0;
  validTicker: boolean = true;
  isLoading: boolean = true;
  isMarketOpen: boolean = false;
  subs: Subscriptions = {
    description: null,
    historicalChart: null,
    dailyChart: null,
    latestPrice: null,
    news: null,
    intervals: null,
  };
  descriptionData: Description = this.descriptionService.getEmptyDescription();
  historicalChartData: HistoricalPriceData[];
  dailyChartData: DailyPriceData[];
  dailyPrices: number[][] = [];
  news: News[];
  latestPriceData: PriceData;
  descriptionDataReady: boolean = false;
  historicalChartDataReady: boolean = false;
  dailyChartDataReady: boolean = false;
  latestPriceDataReady: boolean = false;
  newsReady: boolean = false;
  change: number = 0;
  changePercent: number = 0;
  changeString: string = '';
  changePercentString: string = '';
  timestamp: string = '';
  latestPriceTimestamp: string = '';
  inWatchlist: boolean;
  myStorage: Storage = window.localStorage;

  private _successWatchlist = new Subject<string>();
  staticAlertClosedWatchlist = false;
  successMessageWatchlist = '';

  private _successBuy = new Subject<string>();
  staticAlertClosedBuy = false;
  successMessageBuy = '';

  constructor(
    private route: ActivatedRoute,
    private descriptionService: DescriptionService,
    private priceService: PriceService,
    private newsService: NewsService
  ) {}

  ngOnInit(): void {
    this.ticker = this.route.snapshot.paramMap.get('ticker').toUpperCase();
    this.inWatchlist =
      this.myStorage.getItem(`${this.ticker} inWatchlist`) == null
        ? false
        : this.myStorage.getItem(`${this.ticker} inWatchlist`) === 'true';
    // flow: get latest price first
    // if market is open -> get chart data for that day
    // if market is closed -> get chart data for that last day when market was open
    // redo every 15 seconds if ticker is valid

    this.getInitialData(this.ticker);
    this.subs.intervals = interval(15000).subscribe((x) => {
      if (!this.validTicker) {
        this.subs.intervals.unsubscribe();
      } else {
        console.log('re-request latest price data');
        this.getLatestPriceData(this.ticker);
      }
    });
    this._successBuy.subscribe((message) => (this.successMessageBuy = message));
    this._successBuy
      .pipe(debounceTime(5000))
      .subscribe(() => (this.successMessageBuy = ''));
    this._successWatchlist.subscribe(
      (message) => (this.successMessageWatchlist = message)
    );
    this._successWatchlist
      .pipe(debounceTime(5000))
      .subscribe(() => (this.successMessageWatchlist = ''));
  }

  ngOnDestroy(): void {
    // unsubscribe from the observables
    for (const key in this.subs) {
      if (this.subs[key]) {
        this.subs[key].unsubscribe();
      }
    }
  }

  getInitialData(ticker: string): void {
    this.getDescription(ticker);
  }

  getDescription(ticker: string): void {
    this.subs.description = this.descriptionService
      .getDescription(this.ticker)
      .pipe(
        tap(() => {
          this.validTicker = false;
        })
      )
      .subscribe(
        (result) => {
          this.descriptionData = result;
          this.validTicker = Object.keys(this.descriptionData).length !== 0;
          // console.log(this.descriptionData);
          if (this.validTicker) {
            this.getLatestPriceData(this.ticker);
            this.getNews(this.ticker);
            this.getHistoricalChartData(this.ticker);
          }
        },
        (error) => {
          console.log(error);
          this.descriptionData = this.descriptionService.getEmptyDescription();
          this.validTicker = false;
        },
        () => {
          this.descriptionDataReady = true;
          this.checkLoading();
          if (!this.validTicker) {
            this.isLoading = false;
          }
          // console.log('Description Data successfully fetched');
        }
      );
  }

  getLatestPriceData(ticker: string): void {
    this.subs.latestPrice = this.priceService.getLatestPrice(ticker).subscribe(
      (result) => {
        this.latestPriceData = result.length !== 0 ? result[0] : null;
        if (this.latestPriceData) {
          this.getDailyChartData(ticker, this.latestPriceData.timestamp);
        }
        // console.log(this.latestPriceData);
      },
      (error) => {
        console.log(error);
      },
      () => {
        this.change =
          this.latestPriceData.last - this.latestPriceData.prevClose;
        this.changePercent =
          (this.change * 100) / this.latestPriceData.prevClose;
        this.changeString = twoDecimals(this.change);
        this.changePercentString = twoDecimals(this.changePercent);
        this.timestamp = formatDateAndTime(new Date());
        this.latestPriceTimestamp = formatDateAndTime(
          new Date(this.latestPriceData.timestamp)
        );
        this.checkMarket();
        this.latestPriceDataReady = true;
        this.checkLoading();
        this.buyPrice = this.latestPriceData.last
          ? this.latestPriceData.last
          : 0;
        // console.log('Latest Price Data successfully fetched');
      }
    );
  }

  getDailyChartData(ticker: string, date: string): void {
    this.subs.dailyChart = this.priceService
      .getDailyChartData(ticker, formatDate(date))
      .subscribe(
        (result) => {
          this.dailyChartData = result;
          // console.log(this.dailyChartData);
        },
        (error) => {
          console.log(error);
        },
        () => {
          this.dailyChartDataReady = true;
          this.checkLoading();
          var newPriceData: number[][] = [];
          this.dailyChartData.forEach((data) => {
            const time = new Date(data.date).getTime();
            newPriceData.push([time, data.close]);
          });
          this.dailyPrices = newPriceData;
          // console.log('Daily Chart Data successfully fetched');
        }
      );
  }

  getHistoricalChartData(ticker: string): void {
    this.subs.historicalChart = this.priceService
      .getHistoricalChartData(ticker, 24)
      .subscribe(
        (result) => {
          this.historicalChartData = result;
          // console.log(this.historicalChartData);
        },
        (error) => {
          console.log(error);
        },
        () => {
          this.historicalChartDataReady = true;
          this.checkLoading();
          // console.log('Historical Data successfully fetched');
        }
      );
  }

  getNews(ticker: string): void {
    this.subs.news = this.newsService.getNews(ticker).subscribe(
      (result) => {
        this.news = result;
        // console.log(this.news);
      },
      (error) => {
        console.log(error);
      },
      () => {
        this.newsReady = true;
        this.checkLoading();
        // console.log('News Data successfully fetched');
      }
    );
  }

  checkLoading(): void {
    this.isLoading = !(
      this.descriptionDataReady &&
      this.historicalChartDataReady &&
      this.dailyChartDataReady &&
      this.newsReady &&
      this.latestPriceDataReady
    );
  }
  checkMarket(): void {
    if (this.latestPriceData) {
      // // check time
      if (this.latestPriceData.timestamp) {
        const latestTimestamp = new Date(this.latestPriceData.timestamp);
        const currTimestamp = new Date();
        // difference in milliseconds
        const diffMS = currTimestamp.getTime() - latestTimestamp.getTime();
        if (diffMS >= 60000) {
          // console.log(currTimestamp);
          // console.log(latestTimestamp);
          // console.log('time decider');
          this.isMarketOpen = false;
          return;
        }
      }
      if (!this.latestPriceData.bidPrice) {
        // console.log('bidPrice decider');
        this.isMarketOpen = false;
        return;
      }
      if (!this.latestPriceData.bidSize) {
        // console.log('bidSize decider');
        this.isMarketOpen = false;
        return;
      }
      if (!this.latestPriceData.askPrice == null) {
        // console.log('askPrice decider');
        this.isMarketOpen = false;
        return;
      }
      if (!this.latestPriceData.askSize) {
        // console.log('askSize decider');
        this.isMarketOpen = false;
        return;
      }
    }
    this.isMarketOpen = true;
  }

  toggleWatchlist(): void {
    // invert star
    // console.log(this.myStorage);
    this.inWatchlist = !this.inWatchlist;
    if (this.inWatchlist) {
      this._successWatchlist.next(
        `${this.ticker.toUpperCase()} added to Watchlist.`
      );
      this.myStorage.setItem(
        `${this.ticker} inWatchlist`,
        this.inWatchlist.toString()
      );
    } else {
      this._successWatchlist.next(
        `${this.ticker.toUpperCase()} removed from Watchlist.`
      );
      this.myStorage.removeItem(`${this.ticker} inWatchlist`);
    }
  }

  toggleBuyAlert(event): void {
    if (event === 'change') {
      this._successBuy.next(
        `${this.ticker.toUpperCase()} bought successfully!`
      );
    }
  }
}
