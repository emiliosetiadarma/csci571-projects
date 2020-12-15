import { Component, Input, OnChanges, OnInit } from '@angular/core';
import { PriceData } from './../../services/price.service';

@Component({
  selector: 'app-price-table',
  templateUrl: './price-table.component.html',
  styleUrls: ['./price-table.component.css'],
})
export class PriceTableComponent {
  @Input() latestPriceData: PriceData;
  @Input() isOpen: boolean;
}
