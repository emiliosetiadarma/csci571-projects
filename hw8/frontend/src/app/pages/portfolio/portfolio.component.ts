import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { PriceData, PriceService } from './../../services/price.service';
import {
  Description,
  DescriptionService,
} from './../../services/description.service';

@Component({
  selector: 'app-portfolio',
  templateUrl: './portfolio.component.html',
  styleUrls: ['./portfolio.component.css'],
})
export class PortfolioComponent implements OnInit, OnDestroy {
  isLoading: boolean = true;
  isEmpty: boolean = false;
  myStorage: Storage = window.localStorage;
  tickers: string[];
  prices: PriceData[] = [];
  descriptions: Description[];
  priceSubs: Subscription;
  descriptionSubs: Subscription;
  names: Object[] = [];
  constructor(
    private descriptionService: DescriptionService,
    private priceService: PriceService
  ) {}

  ngOnInit(): void {
    this.tickers = [];
    this.descriptions = [];
    Object.keys(this.myStorage).forEach((key) => {
      const keyArr = key.split(' ');
      const ticker = keyArr[0].toUpperCase();
      const status = keyArr[1];
      const value = this.myStorage.getItem(key);
      if (status === 'totalQuantity') {
        this.tickers.push(ticker);
        this.fetchDescription(ticker);
      }
    });

    if (this.tickers.length !== 0) {
      this.priceSubs = this.priceService
        .getLatestPrice(this.tickers.join(','))
        .subscribe(
          (result) => {
            this.prices = result;
            this.prices.forEach((price) => {
              this.fetchDescription(price.ticker.toUpperCase());
            });
          },
          (error) => {
            console.log(error);
          },
          () => {
            this.isLoading = false;
          }
        );
    } else {
      this.isLoading = false;
    }
  }

  ngOnDestroy(): void {
    if (this.priceSubs) {
      this.priceSubs.unsubscribe();
    }
    if (this.descriptionSubs) {
      this.descriptionSubs.unsubscribe();
    }
  }

  onChange(event: any, ticker: string): void {
    const status = event;
    if (status === 'empty') {
      this.prices = this.prices.filter((price) => price.ticker !== ticker);
      this.tickers = this.tickers.filter((value) => value !== ticker);
      this.myStorage.removeItem(`${ticker} totalCost`);
      this.myStorage.removeItem(`${ticker} totalQuantity`);
    }
  }

  fetchDescription(ticker: string): void {
    this.descriptionSubs = this.descriptionService
      .getDescription(ticker)
      .subscribe(
        (result) => {
          this.names[result.ticker.toUpperCase()] = result.name;
        },
        (error) => {
          console.log(error);
        },
        () => {}
      );
  }
}
