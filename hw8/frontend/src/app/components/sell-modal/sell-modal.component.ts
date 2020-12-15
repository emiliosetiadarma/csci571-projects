import {
  Component,
  EventEmitter,
  Input,
  OnDestroy,
  OnInit,
  Output,
} from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-sell-modal',
  templateUrl: './sell-modal.component.html',
  styleUrls: ['./sell-modal.component.css'],
})
export class SellModalComponent implements OnInit, OnDestroy {
  @Input() ticker: string;
  @Input() price: number;
  @Output() close: EventEmitter<string> = new EventEmitter();
  sellQuantity: number = 0;
  totalCost: number = 0;
  totalQuantity: number = 0;
  myStorage: Storage = window.localStorage;
  constructor(public activeModal: NgbActiveModal) {}

  ngOnInit(): void {
    this.sellQuantity = 0;
    this.totalCost =
      this.myStorage.getItem(`${this.ticker} totalCost`) == null
        ? 0
        : Number(this.myStorage.getItem(`${this.ticker} totalCost`));
    this.totalQuantity =
      this.myStorage.getItem(`${this.ticker} totalQuantity`) == null
        ? 0
        : Number(this.myStorage.getItem(`${this.ticker} totalQuantity`));
  }

  ngOnDestroy(): void {
    if (this.totalQuantity === 0) {
      this.close.emit('empty');
    } else {
      // there is still remainder
      if (this.sellQuantity === 0) {
        this.close.emit('same');
      } else {
        this.close.emit('change');
      }
    }
  }

  updateQuantity(event) {
    this.sellQuantity = Number(event.target.value);
  }

  sell() {
    const newTotalCost = this.totalCost - this.sellQuantity * this.price;
    const newTotalQuantity = this.totalQuantity - this.sellQuantity;
    this.totalCost = newTotalCost;
    this.totalQuantity = newTotalQuantity;
    // set cost and quantity
    if (newTotalQuantity === 0) {
      // remove item from local storage
      this.myStorage.removeItem(`${this.ticker} totalCost`);
      this.myStorage.removeItem(`${this.ticker} totalQuantity`);
    } else {
      this.myStorage.setItem(`${this.ticker} totalCost`, String(newTotalCost));
      this.myStorage.setItem(
        `${this.ticker} totalQuantity`,
        String(newTotalQuantity)
      );
    }
    this.activeModal.close('Close click');
  }
}
