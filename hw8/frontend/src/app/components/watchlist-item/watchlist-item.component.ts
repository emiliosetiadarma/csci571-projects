import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { PriceData } from './../../services/price.service';
import { twoDecimals } from './../../functions/formatters';
import { Router } from '@angular/router';

@Component({
  selector: 'app-watchlist-item',
  templateUrl: './watchlist-item.component.html',
  styleUrls: ['./watchlist-item.component.css'],
})
export class WatchlistItemComponent implements OnInit {
  @Input() priceData: PriceData;
  @Input() name: string;
  @Output() close: EventEmitter<string> = new EventEmitter();
  change: number = 0;
  changePercent: number = 0;
  changeString: string = '';
  changePercentString: string = '';
  constructor(private router: Router) {}

  ngOnInit(): void {
    // console.log(this.priceData);
    this.change = this.priceData.last - this.priceData.prevClose;
    this.changePercent = (this.change * 100) / this.priceData.prevClose;
    this.changeString = twoDecimals(this.change);
    this.changePercentString = twoDecimals(this.changePercent);
  }

  onClose(): void {
    this.close.emit(this.priceData.ticker);
  }

  onClick(): void {
    this.router.navigate(['/details', this.priceData.ticker.toUpperCase()]);
  }
}
