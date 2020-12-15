import { Component, Input, OnInit } from '@angular/core';
import { News } from './../../services/news.service';

import {
  NgbActiveModal,
  NgbModal,
  NgbModalRef,
} from '@ng-bootstrap/ng-bootstrap';
import { NewsModalComponent } from './../news-modal/news-modal.component';

@Component({
  selector: 'app-news-item',
  templateUrl: './news-item.component.html',
  styleUrls: ['./news-item.component.css'],
})
export class NewsItemComponent implements OnInit {
  @Input() news: News;
  modalRef: NgbModalRef;
  constructor(private modalService: NgbModal) {}

  ngOnInit(): void {}

  openNewsModal(): void {
    this.modalRef = this.modalService.open(NewsModalComponent);
    this.modalRef.componentInstance.news = this.news;
  }
}
