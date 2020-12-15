import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  OnDestroy,
} from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
@Component({
  selector: 'app-buy-modal',
  templateUrl: './buy-modal.component.html',
  styleUrls: ['./buy-modal.component.css'],
})
export class BuyModalComponent implements OnInit, OnDestroy {
  @Input() ticker: string;
  @Input() price: number;
  @Output() close: EventEmitter<string> = new EventEmitter();
  buyQuantity: number = 0;
  totalCost: number = 0;
  totalQuantity: number = 0;
  myStorage: Storage = window.localStorage;
  constructor(public activeModal: NgbActiveModal) {}

  ngOnInit(): void {
    this.buyQuantity = 0;
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
    if (this.buyQuantity === 0) {
      this.close.emit('same');
    }
  }

  updateQuantity(event) {
    this.buyQuantity = Number(event.target.value);
  }

  buy() {
    const newTotalCost = this.buyQuantity * this.price + this.totalCost;
    const newQuantity = this.buyQuantity + this.totalQuantity;
    // set cost and quantity
    this.myStorage.setItem(`${this.ticker} totalCost`, String(newTotalCost));
    this.myStorage.setItem(`${this.ticker} totalQuantity`, String(newQuantity));
    this.close.emit('change');
    this.activeModal.close('Close click');
  }
}
