import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { PriceData, PriceService } from './../../services/price.service';

@Component({
  selector: 'app-portfolio-item',
  templateUrl: './portfolio-item.component.html',
  styleUrls: ['./portfolio-item.component.css'],
})
export class PortfolioItemComponent implements OnInit {
  @Input() priceData: PriceData;
  @Input() name: string;
  @Output() change: EventEmitter<string> = new EventEmitter();
  myStorage: Storage = window.localStorage;
  priceChange: number = 0;
  totalQuantity: number = 0;
  totalCost: number = 0;
  avgCostPerShare: number = 0;
  marketValue: number = 0;
  constructor() {}

  ngOnInit(): void {
    this.calculateValues();
  }

  onChange(event): void {
    if (event === 'change') {
      this.calculateValues();
    }
    this.change.emit(event);
  }

  calculateValues(): void {
    const ticker = this.priceData.ticker.toUpperCase();
    this.totalQuantity = Number(
      this.myStorage.getItem(`${ticker} totalQuantity`)
    );
    this.totalCost = Number(this.myStorage.getItem(`${ticker} totalCost`));
    this.avgCostPerShare = Number(
      (this.totalCost / this.totalQuantity).toFixed(2)
    );
    this.marketValue = this.totalQuantity * this.priceData.last;
    this.priceChange = this.priceData.last - this.avgCostPerShare;
  }
}
