import {
  Component,
  Input,
  OnInit,
  OnChanges,
  Output,
  EventEmitter,
  SimpleChanges,
} from '@angular/core';
import {
  NgbActiveModal,
  NgbModal,
  NgbModalRef,
} from '@ng-bootstrap/ng-bootstrap';
import { BuyModalComponent } from './../buy-modal/buy-modal.component';

@Component({
  selector: 'app-buy-button',
  templateUrl: './buy-button.component.html',
  styleUrls: ['./buy-button.component.css'],
})
export class BuyButtonComponent implements OnInit, OnChanges {
  @Input() color: string;
  @Input() ticker: string;
  @Input() price: number;
  @Output() close: EventEmitter<string> = new EventEmitter();
  modalRef: NgbModalRef;
  btnClass: string;
  open: boolean = false;
  constructor(private modalService: NgbModal) {}

  ngOnInit(): void {
    if (this.color === 'red') {
      this.btnClass = 'btn btn-danger';
    } else if (this.color === 'blue') {
      this.btnClass = 'btn btn-primary';
    } else if (this.color === 'green') {
      this.btnClass = 'btn btn-success';
    } else {
      this.btnClass = 'btn';
    }
  }

  openBuyModal(): void {
    this.modalRef = this.modalService.open(BuyModalComponent);
    this.open = true;
    this.modalRef.componentInstance.price = this.price;
    this.modalRef.componentInstance.ticker = this.ticker;
    this.modalRef.componentInstance.close.subscribe((event) => {
      this.open = false;
      this.close.emit(event);
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    // console.log(this.price);
    // console.log(this.ticker);

    if (this.modalRef && this.open) {
      for (const propName in changes) {
        if (changes.hasOwnProperty(propName)) {
          switch (propName) {
            case 'price':
              this.modalRef.componentInstance.price =
                changes['price'].currentValue;
              break;
            case 'ticker':
              this.modalRef.componentInstance.ticker =
                changes['ticker'].currentValue;
              break;
          }
        }
      }
    }
  }
}
