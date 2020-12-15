import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import {
  NgbActiveModal,
  NgbModal,
  NgbModalRef,
} from '@ng-bootstrap/ng-bootstrap';
import { SellModalComponent } from './../sell-modal/sell-modal.component';
@Component({
  selector: 'app-sell-button',
  templateUrl: './sell-button.component.html',
  styleUrls: ['./sell-button.component.css'],
})
export class SellButtonComponent implements OnInit {
  @Input() color: string;
  @Input() ticker: string;
  @Input() price: number;
  @Output() close: EventEmitter<string> = new EventEmitter();
  modalRef: NgbModalRef;
  btnClass: string;
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

  openSellModal(): void {
    this.modalRef = this.modalService.open(SellModalComponent);
    this.modalRef.componentInstance.price = this.price;
    this.modalRef.componentInstance.ticker = this.ticker;
    this.modalRef.componentInstance.close.subscribe((event) => {
      this.close.emit(event);
    });
  }
}
